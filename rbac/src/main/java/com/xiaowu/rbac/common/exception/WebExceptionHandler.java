package com.xiaowu.rbac.common.exception;

import com.xiaowu.rbac.common.result.ResultCode;
import com.xiaowu.rbac.common.result.ResultEntity;
import com.xiaowu.rbac.common.result.ResultGenerator;
import com.xiaowu.rbac.common.result.ResultStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * web全局抛出异常
 * 全局异常处理
 * 返回json格式数据
 */
//@RestControllerAdvice
//  extends ResponseEntityExceptionHandler
//public class WebExceptionHandler{

    /**
     * 发生未知错误，请联系系统管理员
     * @param e
     * @return
     */
//    @ExceptionHandler
//    public ResultEntity  unknownException(Exception e){
//        return ResultGenerator.genErrorResult();
//    }

    // 异常情况重复，会报bean错误
//    @ExceptionHandler
//    public ResultEntity defaultErrorHandler(HttpServletRequest request, Exception e){
//        if(e instanceof NoHandlerFoundException){
//            return ResultGenerator.genFailureResult();
//        }
//        return ResultGenerator.genErrorResult();
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }

    // spring security 异常，采用handler集体处理
//    @ExceptionHandler(value = UsernameNotFoundException.class)
//    public String UsernameNotFoundExceptionHandler(UsernameNotFoundException e,HttpServletRequest request){
//        System.out.println("enter UsernameNotFoundExceptionHandler");
//        request.setAttribute("javax.servlet.error.status_code", 200);
//        request.setAttribute("code", 1);
//        request.setAttribute("msg", "参数校验失败, xxx");
//        return "/error";
//    }

//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ResultEntity AccessDeniedExceptionHandler(AccessDeniedException e) throws Exception {
//        System.out.println("enter AccessDeniedExceptionHandler");
//        return ResultGenerator.genResult(ResultStatus.OK.getValue(),ResultCode.USERNAME_NOT_FOUND.getCode(),e.getMessage());
//    }
//}
