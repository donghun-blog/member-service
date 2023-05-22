package me.donghun.memberservice.adapter.input.api;

import lombok.extern.slf4j.Slf4j;
import me.donghun.memberservice.adapter.input.api.dto.BaseResponse;
import me.donghun.memberservice.domain.exception.MemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String FIELD_ERROR_MESSAGE = "필드 유효성 검사에 실패하였습니다.";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<MultiValueMap<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidExceptionHandler: ", e);
        MultiValueMap<String, String> errors = new LinkedMultiValueMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.add(fieldError.getField(), fieldError.getDefaultMessage());
        }

        BaseResponse<MultiValueMap<String, String>> messageBody = BaseResponse.error(FIELD_ERROR_MESSAGE, errors);
        return ResponseEntity.status(BAD_REQUEST)
                             .body(messageBody);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<BaseResponse<?>> memberExceptionHandler(MemberException e) {
        log.error("MemberException: ", e);

        return ResponseEntity.status(BAD_REQUEST)
                             .body(BaseResponse.error(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> exceptionHandler(Exception e) {
        log.error("Exception: ", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                             .body(BaseResponse.error("서버 에러가 발생하였습니다."));
    }
}
