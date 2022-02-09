package com.ghostcat.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * Get response data, if access token expires, remove it from redis
 *
 * @author linpz
 */
@Slf4j
public class CustomResponseDecorator extends ServerHttpResponseDecorator {

    public CustomResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataBufferFactory bufferFactory = getDelegate().bufferFactory();

            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

            return super.writeWith(fluxBody.map(dataBuffer -> {
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);
                try {
                    bout.write(content);
                } catch (Exception e) {
                    log.error("", e);
                }
                DataBufferUtils.release(dataBuffer);

                return bufferFactory.wrap(content);
            }).doOnComplete(() -> {
                // Get response data, if access token expires, remove it from redis
                try {
                    String data = new String(bout.toByteArray(), Charset.forName("UTF-8"));
                    log.debug("response data : {}", data);
                } catch(Exception e) {
                    log.warn(" error.", e);
                }finally {
                    IOUtils.closeQuietly(bout);
                }
            }));
        }  else {
            return super.writeWith(body);
        }
    }

}
