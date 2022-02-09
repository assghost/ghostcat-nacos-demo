package com.ghostcat.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Get response data, if access token expires, remove it from redis
 *
 * @author linpz
 */
@Slf4j
public class CustomRequestDecorator extends ServerHttpRequestDecorator {

    //缓存requestBody，解决body只能读一次问题。
    private AtomicReference<String> requestBodyCache = new AtomicReference<>("");;

    public CustomRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        Flux<DataBuffer> bodyFlux = super.getBody();
        try {
            ServerHttpRequest delegate = getDelegate();
            MediaType contentType = delegate.getHeaders().getContentType();
            if (!MediaType.APPLICATION_JSON.isCompatibleWith(contentType)){
                return bodyFlux;
            }

            return bodyFlux.map(dataBuffer -> {
                byte[] content = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(content);
                requestBodyCache.set(new String(content, Charset.forName("UTF-8")));
                DataBufferUtils.release(dataBuffer);

                DataBuffer newDataBuffer = dataBuffer.factory().wrap(content);
                return newDataBuffer;
            }).doOnComplete(() -> {
                String requestBodyStr = requestBodyCache.get();
                log.debug("request body: {}", requestBodyStr);
            });
        } catch (Exception e) {
            log.error("", e);
            return bodyFlux;
        }
    }
}
