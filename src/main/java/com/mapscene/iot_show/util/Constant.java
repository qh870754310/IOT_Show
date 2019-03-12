package com.mapscene.iot_show.util;

/**
 * @Author: qh
 * @Date: 2018/10/27 17:06
 * @Description:
 */
public class Constant {

    public static final String host_1 = "192.168.1.204";

    public static final int port_1 = 4196;

    public static final String host_2 = "192.168.1.205";

    public static final int port_2 = 8899;

    /**
     * 打开风扇
     */
    public static final String open_message_1 = "01 05 00 12 FF 00";

    /**
     * 关闭风扇
     */
    public static final String close_message_1 = "01 05 00 12 00 00";


    /**
     * 打开报警器
     */
    public static final String open_message_2 = "01 05 00 13 FF 00";

    /**
     * 关闭报警器
     */
    public static final String close_message_2 = "01 05 00 13 00 00";

    /**
     * 红外移动侦测
     */
    public static final String message_3 = "01 01 00 00 00 01";

    /**
     * 漏水侦测
     */
    public static final String message_4 = "01 01 00 01 00 01";

    /**
     * 二氧化碳
     */
    public static final String message_5 = "01 03 00 00 00 02";

    /**
     * 湿度
     */
    public static final String message_6 = "02 03 00 00 00 02";

    /**
     * 温度
     */
    public static final String message_7 = "02 03 00 01 00 02";

    /**
     * PH值
     */
    public static final String message_8 = "03 03 00 00 00 02";

    /**
     * 风扇状态
     */
    public static final String message_9 = "01 01 00 12 00 01";

    /**
     * 报警器状态
     */
    public static final String message_10 = "01 01 00 13 00 01";

    /**
     * 返回状态值
     */
    public enum RESULT{
        /**
         * 成功
         */
        CODE_YES("0"),
        /**
         * 失败
         */
        CODE_NO("-1"),
        /**
         * 失败msg
         */
        MSG_YES("操作成功"),
        /**
         * 失败msg
         */
        MSG_NO("操作失败");
        private String value;

        private RESULT(String value){
            this.value=value;
        }

        public String getValue(){
            return value;
        }
    }
}
