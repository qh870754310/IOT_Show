package com.mapscene.iot_show.controller;

import com.mapscene.iot_show.service.HomeService;
import com.mapscene.iot_show.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qh
 * @Date: 2018/10/27 15:56
 * @Description:
 */
@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getData")
    public Result getData() {
        try {
            //1、查询红外移动侦测状态
            int state_1 = (int) homeService.queryDeviceInfo(1);
            //2、漏水侦测
            int state_2 = (int) homeService.queryDeviceInfo(2);
            //3、二氧化碳
            Object value_1 =  homeService.queryDeviceInfo(3);
            //4、湿度
            double value_2 = (double) homeService.queryDeviceInfo(4);
            //5、温度
            double value_3 = (double) homeService.queryDeviceInfo(5);
            //6、ph
            double value_4 = (double) homeService.queryDeviceInfo(6);
            //查询风扇状态
            int state_3 = (int) homeService.queryDeviceInfo(7);
            //查询报警器状态
            int state_4 = (int) homeService.queryDeviceInfo(8);
            Map<String, Object> map = new HashMap<>();
            map.put("state_1", state_1 == 1 ? "正常":"异常");
            map.put("state_2", state_2 == 1 ? "正常":"异常");
            map.put("state_3", state_3 == 1 ? "运转":"停止");
            map.put("state_4", state_4 == 1 ? "异常":"正常");
            map.put("value_1", value_1);
            map.put("value_2", value_2);
            map.put("value_3", value_3);
            map.put("value_4", value_4);
            return Result.ok().put("result", map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 开关控制
     * @param type 开关类型
     * @param flag 操作类型
     * @return
     */
    @RequestMapping(value = "/open")
    @ResponseBody
    public Result openSwtich(Integer type, String flag) {
        try {
            boolean state = homeService.openSwtich(type, flag);
            return Result.ok().put("state", state);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/query")
    public Result query() {
        return homeService.query();
    }
}
