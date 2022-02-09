package com.ghostcat.hello.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ApiModel
public class HelloResponseVO<T> {
    @Builder.Default
    private String code = "0";

    @Builder.Default
    private String msg = "success";

    private T data;

    public static <T> HelloResponseVO<T> ok() {
        HelloResponseVO<T> r = new HelloResponseVO<>();
        return r;
    }

    public static <T> HelloResponseVO<T> ok(T data) {
        HelloResponseVO<T> r = ok();
        r.setData(data);
        return r;
    }

    public static <T> HelloResponseVO<T> fail() {
        HelloResponseVO<T> r = new HelloResponseVO<>();
        r.setCode("-1");
        r.setMsg("fail");
        return r;
    }

    public static <T> HelloResponseVO<T> fail(T data) {
        HelloResponseVO<T> r = fail();
        r.setData(data);
        return r;
    }
}
