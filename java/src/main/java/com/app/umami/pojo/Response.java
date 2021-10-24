package com.app.umami.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private Integer code;
    private String message;
    private T body;

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(T body, Integer code) {
        this.body = body;
        this.code = code;
    }
}
