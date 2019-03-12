package com.mapscene.iot_show.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qh
 * @Date: 2018/10/27 16:58
 * @Description: 返回数据实体类
 */

public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 0);
    }

    public Result(String code, String msg) {
        put("code", code);
        put("msg", msg);
    }

    public static Result error() {
        return new Result(Constant.RESULT.CODE_NO.getValue(), Constant.RESULT.MSG_NO.getValue());
    }

    public static Result error(String msg) {
        return error(Constant.RESULT.CODE_NO.getValue(), msg);
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.put("code",code);
        result.put("msg", msg);
        return result;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result(Constant.RESULT.CODE_YES.getValue(),Constant.RESULT.MSG_YES.getValue());
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
