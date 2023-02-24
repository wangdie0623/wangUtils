package cn.wang.custom.web.api.aspects;

import cn.wang.custom.web.api.beans.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationAspect {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String handleValidationExceptionsBase(
            BindException ex) {
        StringBuilder builder = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            builder.append(fieldName+":"+errorMessage+",");
        }
        builder.deleteCharAt(builder.length()-1);
        return JsonResult.sysErr(builder.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return handleValidationExceptionsBase(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public String handleValidationExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> item : ex.getConstraintViolations()) {
            String message = item.getMessage();
            String property = item.getPropertyPath().toString();
            String name = item.getRootBeanClass().getName();
            errors.put(name+":"+property,message);
        }
        return JsonResult.sysErr(JSON.toJSONString(errors));
    }
}
