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

    /**
     * 抽奖策略模式：1单项概率、2总体概率
     */
    public enum StrategyMode {

        SINGLE(1, "单项概率"),
        ENTIRETY(2, "总体概率");

        private Integer code;
        private String info;

        StrategyMode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖
     */
    public enum DrawState {

        FAIL(0,"未中奖"),
        SUCCESS(1, "已中奖"),
        Cover(2,"兜底奖");

        private Integer code;
        private String info;

        DrawState(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }
}
