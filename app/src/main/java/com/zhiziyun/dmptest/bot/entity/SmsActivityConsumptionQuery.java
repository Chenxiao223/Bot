package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/2/9.
 * 短信活动消耗
 */

public class SmsActivityConsumptionQuery {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"activityId":"wqJlh0qSo1O","macCount":"2","smsCost":"2.40"}
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
         * activityId : wqJlh0qSo1O
         * macCount : 2
         * smsCost : 2.40
         */

        private String activityId;
        private String macCount;
        private String smsCost;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getMacCount() {
            return macCount;
        }

        public void setMacCount(String macCount) {
            this.macCount = macCount;
        }

        public String getSmsCost() {
            return smsCost;
        }

        public void setSmsCost(String smsCost) {
            this.smsCost = smsCost;
        }
    }
}
