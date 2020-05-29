package com.xiaowu.rbac.common.result;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    public static ResultEntity genResult(int status, int code,String msg){

        return new ResultEntity(status,code,msg);
    }

    public static ResultEntity genSucessResult(){
        ResultEntity result = new ResultEntity();
        result.setStatus(ResultStatus.OK.getValue());
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static ResultEntity genFailureResult(){
        ResultEntity result = new ResultEntity();
        result.setStatus(ResultStatus.OK.getValue());
        result.setCode(ResultCode.FAILURE.getCode());
        result.setMsg(ResultCode.FAILURE.getMsg());
        return result;
    }

    public static ResultEntity genErrorResult(){
        ResultEntity result = new ResultEntity();
        result.setStatus(ResultStatus.OK.getValue());
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMsg());
        return result;
    }


}
