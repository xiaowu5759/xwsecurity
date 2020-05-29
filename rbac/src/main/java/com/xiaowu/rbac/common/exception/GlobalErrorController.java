package com.xiaowu.rbac.common.exception;

import com.xiaowu.rbac.common.result.ResultEntity;
import com.xiaowu.rbac.common.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@RestController
//public class GlobalErrorController implements ErrorController {
//    private final String PATH = "/error";
//
//    @Autowired
//    private ErrorAttributes errorAttributes;
//
//    private MyDefaultErrorAttributes myDefaultErrorAttributes = new MyDefaultErrorAttributes();
//    @Override
//    public String getErrorPath() {
//        return PATH;
//    }
//
//    @RequestMapping(value = PATH,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
//    public ResultEntity handleError(WebRequest request){
//        Map<String,Object> attributesMap =myDefaultErrorAttributes.getErrorAttributes(request,true);
//        return ResultGenerator.genResult(400,Integer.valueOf(attributesMap.get("code").toString()),attributesMap.get("msg").toString());
//
//    }
//
//
//}
