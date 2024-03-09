package com.glimound.lottery.common;

/**
 * 枚举信息定义
 * @author Glimound
 */
public class Constants {
    public enum ResponseCode {

        SUCCESS("0000", "成功"),
        UNKNOWN_ERROR("0001", "未知错误"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        INDEX_DUPLICATE("0003", "主键冲突");

        private String code;
        private String info;

        ResponseCode(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }
}
