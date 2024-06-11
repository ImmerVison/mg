package io.goji.biz.maner.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(name = "BaseResponse", description = "基础响应")
@Data
@AllArgsConstructor
public class BaseResponse<T> {

    @Schema(description = "响应信息", requiredMode = REQUIRED, example = "成功")
    private String msg;
    @Schema(description = "响应数据", example = "data")
    private T data;


    public BaseResponse() {
        this.msg = "请求成功"; // 默认消息
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public static <T> BaseResponse<T> response(HttpStatus status, BaseResponse<T> response) {
        return new BaseResponse<>(status.getReasonPhrase(), response.getData());
    }

    public static <T> BaseResponse<T> success() {
        return response(HttpStatus.OK, new BaseResponse<>());
    }

    public static <T> BaseResponse<T> success(T data) {
        return response(HttpStatus.OK, new BaseResponse<>("请求成功", data));
    }

    public static <T> BaseResponse<T> success(String msg, T data) {
        return response(HttpStatus.OK, new BaseResponse<>(msg, data));
    }

    public static <T> BaseResponse<T> fail() {
        return response(HttpStatus.BAD_REQUEST, new BaseResponse<>("请求失败"));
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return response(HttpStatus.BAD_REQUEST, new BaseResponse<>(msg));
    }

    public static <T> BaseResponse<T> fail(String msg, T data) {
        return response(HttpStatus.BAD_REQUEST, new BaseResponse<>(msg, data));
    }

    public static <T> BaseResponse<T> fail(HttpStatus status, String msg, T data) {
        return response(status, new BaseResponse<>(msg, data));
    }

}
