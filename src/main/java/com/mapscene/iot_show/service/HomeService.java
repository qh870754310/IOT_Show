package com.mapscene.iot_show.service;

import com.mapscene.iot_show.util.Result;

/**
 * @Author: qh
 * @Date: 2018/10/27 17:12
 * @Description:
 */
public interface HomeService {

    boolean openSwtich(Integer type, String flag) throws Exception;

    /**
     * 查询设备的信息
     * @param type
     * @return
     */
    Object queryDeviceInfo(int type);

    Result query();
}
