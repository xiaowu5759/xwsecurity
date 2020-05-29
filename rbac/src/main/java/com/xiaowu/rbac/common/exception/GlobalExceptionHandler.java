package com.xiaowu.rbac.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Enumeration;

import static org.springframework.http.HttpStatus.NOT_EXTENDED;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    /**
     * 通用的接口映射异常处理方
     */
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
//                                                             HttpStatus status, WebRequest request) {
//        if (ex instanceof HttpRequestMethodNotSupportedException){
//
//        }
//        if (ex instanceof MethodArgumentNotValidException) {
//            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
//            return new ResponseEntity<>(new ErrorResponseEntity(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
//        }
//        if (ex instanceof MethodArgumentTypeMismatchException) {
//            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
////            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName() + ",信息：" + exception.getLocalizedMessage());
//            return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
//        }
//        return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
//    }
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
////        return super.handleExceptionInternal(ex, body, headers, status, request);
//        System.out.println("错误");
//
//        return new ResponseEntity<Object>("出错了", NOT_EXTENDED);
//    }

//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        switch (status) {
//            case METHOD_NOT_ALLOWED:
//                return getMethodNotAllowedResponse(request);
//            default:
//                return ResponseEntity.ok(new Result<>(status.value(), status.getReasonPhrase(), null));
//        }
//    }
//
//    public ResponseEntity getMethodNotAllowedResponse(WebRequest request) {
//        String uri = "";
//        if (request instanceof ServletWebRequest) {
//            uri = ((ServletWebRequest) request).getRequest().getRequestURI();
//        }
//        Result<Object> result = new Result<>();
//        result.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
//        result.setMessage(uri + " 请求方式不正确");
//        return ResponseEntity.ok(result);
//    }

//    /**
//     * 数据找不到异常
//     * @param ex
//     * @param request
//     * @return
//     * @throws IOException
//     */
//    @ExceptionHandler({DataNotFoundException.class})
//    public ResponseEntity<Object> handleDataNotFoundException(RuntimeException ex, WebRequest request) throws IOException {
//        return getResponseEntity(ex,request,ReturnStatusCode.DataNotFoundException);
//    }
//
//    /**
//     * 根据各种异常构建 ResponseEntity 实体. 服务于以上各种异常
//     * @param ex
//     * @param request
//     * @param specificException
//     * @return
//     */
//    private ResponseEntity<Object> getResponseEntity(RuntimeException ex, WebRequest request, ReturnStatusCode specificException) {
//
//        ReturnTemplate returnTemplate = new ReturnTemplate();
//        returnTemplate.setStatusCode(specificException);
//        returnTemplate.setErrorMsg(ex.getMessage());
//
//        return handleExceptionInternal(ex, returnTemplate,
//                new HttpHeaders(), HttpStatus.OK, request);
//    }

    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");
//        if(getUser() != null)
//            logger.error("当前用户id是" + getUser().getUserId());
        logger.error(ex);
        logger.error("请求地址：" + request.getRequestURL());
        Enumeration enumeration = request.getParameterNames();
        logger.error("请求参数");
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            logger.error(name + "---" + request.getParameter(name));
        }

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }
        logger.error("************************异常结束*******************************");
    }
}
