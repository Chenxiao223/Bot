package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/1/11.
 * 花费统计
 */

public class ExpenditureStatistics {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"totalConsumption":168.88,"creativeConsumption":100.23,"smsConsumption":68.65}
     */

    private boolean status;
    private String errorcode;
    private String errormsg;
    private ResponseBean response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * totalConsumption : 168.88
         * creativeConsumption : 100.23
         * smsConsumption : 68.65
         */

        private String totalConsumption;
        private String creativeConsumption;
        private String smsConsumption;

        public String getTotalConsumption() {
            return totalConsumption;
        }

        public void setTotalConsumption(String totalConsumption) {
            this.totalConsumption = totalConsumption;
        }

        public String getCreativeConsumption() {
            return creativeConsumption;
        }

        public void setCreativeConsumption(String creativeConsumption) {
            this.creativeConsumption = creativeConsumption;
        }

        public String getSmsConsumption() {
            return smsConsumption;
        }

        public void setSmsConsumption(String smsConsumption) {
            this.smsConsumption = smsConsumption;
        }
    }
}
