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
     * response : {"totalCost":"0.00","adCost":"0.00","smsCost":"0.00","callCost":"0.00","wechatCost":"0.00"}
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
         * totalCost : 0.00
         * adCost : 0.00
         * smsCost : 0.00
         * callCost : 0.00
         * wechatCost : 0.00
         */

        private String totalCost;
        private String adCost;
        private String smsCost;
        private String callCost;
        private String wechatCost;

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
        }

        public String getAdCost() {
            return adCost;
        }

        public void setAdCost(String adCost) {
            this.adCost = adCost;
        }

        public String getSmsCost() {
            return smsCost;
        }

        public void setSmsCost(String smsCost) {
            this.smsCost = smsCost;
        }

        public String getCallCost() {
            return callCost;
        }

        public void setCallCost(String callCost) {
            this.callCost = callCost;
        }

        public String getWechatCost() {
            return wechatCost;
        }

        public void setWechatCost(String wechatCost) {
            this.wechatCost = wechatCost;
        }
    }
}
