package com.xiaowu.rbac.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class HttpErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    // 采用单例模式
    private static HttpErrorController httpErrorController;

    @Autowired
    private ErrorAttributes errorAttributes;

    public HttpErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    public HttpErrorController(){
        if(httpErrorController == null){
            httpErrorController = new HttpErrorController(errorAttributes);
        }
    }


//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        Map<String, Object> body = getErrorAttributes(request,
//                isIncludeStackTrace(request, MediaType.ALL));
//        HttpStatus status = getStatus(request);
//        return new ResponseEntity<>(body, status);
//    }
    @RequestMapping(value = ERROR_PATH,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if(status == 401){
            System.out.println();
            return "/401";
        }else if(status == 404){
            return "/404";
        }else if(status == 403){
            return "/403";
        }else{
            return "/500";
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
