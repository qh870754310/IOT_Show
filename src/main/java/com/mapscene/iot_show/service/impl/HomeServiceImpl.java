package com.mapscene.iot_show.service.impl;

import com.mapscene.iot_show.service.HomeService;
import com.mapscene.iot_show.util.Constant;
import com.mapscene.iot_show.util.ModbusHelper;
import com.mapscene.iot_show.util.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: qh
 * @Date: 2018/10/27 17:13
 * @Description:
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Override
    public boolean openSwtich(Integer type, String flag) throws Exception {
        boolean state = false;
        if ("open".equals(flag)) {
            if (type == 1) { //打开风扇
                state = (boolean) ModbusHelper.open(Constant.host_1, Constant.port_1, Constant.open_message_1);
            } else if (type == 2) { //打开报警器
                state = (boolean) ModbusHelper.open(Constant.host_1, Constant.port_1,Constant.open_message_2);
            }
        } else if ("close".equals(flag)) {
            if (type == 1) { //关闭风扇
                state = (boolean) ModbusHelper.open(Constant.host_1, Constant.port_1,Constant.close_message_1);
            } else if (type == 2) { //关闭报警器
                state = (boolean) ModbusHelper.open(Constant.host_1, Constant.port_1,Constant.close_message_2);
            }
        }
        return state;
    }

    @Override
    public Object queryDeviceInfo(int type) {
        Object content = 0;
        if (type == 1) { //红外移动侦测
            content = ModbusHelper.queryDeviceInfo(Constant.host_1, Constant.port_1, Constant.message_3, type);
        } else if (type == 2) { //漏水侦测
            content = ModbusHelper.queryDeviceInfo(Constant.host_1, Constant.port_1, Constant.message_4, type);
        } else if (type == 3) { //二氧化碳
            content = ModbusHelper.queryDeviceInfo(Constant.host_2, Constant.port_2, Constant.message_5, type);
        } else if (type == 4) { //湿度
            content = ModbusHelper.queryDeviceInfo(Constant.host_2, Constant.port_2, Constant.message_6, type);
        } else if (type == 5) { //温度
            content = ModbusHelper.queryDeviceInfo(Constant.host_2, Constant.port_2, Constant.message_7, type);
        } else if (type == 6) { //PH值
            content = ModbusHelper.queryDeviceInfo(Constant.host_2, Constant.port_2, Constant.message_8, type);
        } else if (type == 7) { //风扇状态
            content = ModbusHelper.queryDeviceInfo(Constant.host_1, Constant.port_1, Constant.message_9, type);
        } else if (type == 8) { //报警器状态
            content = ModbusHelper.queryDeviceInfo(Constant.host_1, Constant.port_1, Constant.message_10, type);
        }
        return content;
    }

    @Override
    public Result query() {
        Socket socket = null;
        Socket socket1 = null;
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            //建立长连接
            socket = ModbusHelper.openSocket(Constant.host_1, Constant.port_1);
            if (socket != null) {
                //循环发送消息
                Map<String, Object> messages = new LinkedHashMap<>();
                messages.put("1", Constant.message_3);
                messages.put("2", Constant.message_4);
                messages.put("7", Constant.message_9);
                messages.put("8", Constant.message_10);
                map.putAll(ModbusHelper.query(socket, messages));
            }

            socket1 = ModbusHelper.openSocket(Constant.host_2, Constant.port_2);
            if (socket1 != null) {
                Map<String, Object> messages = new LinkedHashMap<>();
                messages.put("3", Constant.message_5);
                messages.put("4", Constant.message_6);
                messages.put("5", Constant.message_7);
                messages.put("6", Constant.message_8);
                map.putAll(ModbusHelper.query(socket1, messages));
            }

            return Result.ok().put("result", map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("连接超时!");
        } finally {
            try {
                socket.close();
                socket1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
