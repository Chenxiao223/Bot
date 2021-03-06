package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 * 投广告实体类
 */

public class Advertising {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":28,"data":[{"activityId":"wr2um0cOsww","activityName":"头像大师-定向设备号","activityStatus":"投放中","activityOffer":"16000.00","activityOfferType":"CPM","dailyBudget":"800.00","startDate":"2018-02-09","endDate":"2018-12-31","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B38LA0nkhb2","activityName":"flhf0816","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-22","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B38VY0M92co","activityName":"hf64885","activityStatus":"已结束","activityOffer":"10.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-19","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B39gt0iU6u4","activityName":"58566","activityStatus":"已结束","activityOffer":"1.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-18","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B3b910SctGw","activityName":"wiaiakka","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"1.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B4cJr0PQArm","activityName":"wxmnnnnhh","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"1.00","startDate":"2018-08-17","endDate":"2018-08-17","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"zbC6V0gOzn2","activityName":"61测","activityStatus":"已结束","activityOffer":"50.00","activityOfferType":"CPM","dailyBudget":"5.00","startDate":"2018-06-01","endDate":"2018-06-01","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B2ROC0LCZt6","activityName":"Drrgu","activityStatus":"未投放","activityOffer":"111.00","activityOfferType":"CPM","dailyBudget":"111.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B30m90Ko4HC","activityName":"Gghjjj","activityStatus":"未投放","activityOffer":"55.00","activityOfferType":"CPM","dailyBudget":"1556.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B2TFu0Bngsk","activityName":"Xinxiliu","activityStatus":"未投放","activityOffer":"111.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"}]}
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
         * total : 28
         * data : [{"activityId":"wr2um0cOsww","activityName":"头像大师-定向设备号","activityStatus":"投放中","activityOffer":"16000.00","activityOfferType":"CPM","dailyBudget":"800.00","startDate":"2018-02-09","endDate":"2018-12-31","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B38LA0nkhb2","activityName":"flhf0816","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-22","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B38VY0M92co","activityName":"hf64885","activityStatus":"已结束","activityOffer":"10.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-19","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B39gt0iU6u4","activityName":"58566","activityStatus":"已结束","activityOffer":"1.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-18","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B3b910SctGw","activityName":"wiaiakka","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"1.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B4cJr0PQArm","activityName":"wxmnnnnhh","activityStatus":"已结束","activityOffer":"2.00","activityOfferType":"CPM","dailyBudget":"1.00","startDate":"2018-08-17","endDate":"2018-08-17","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"zbC6V0gOzn2","activityName":"61测","activityStatus":"已结束","activityOffer":"50.00","activityOfferType":"CPM","dailyBudget":"5.00","startDate":"2018-06-01","endDate":"2018-06-01","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B2ROC0LCZt6","activityName":"Drrgu","activityStatus":"未投放","activityOffer":"111.00","activityOfferType":"CPM","dailyBudget":"111.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B30m90Ko4HC","activityName":"Gghjjj","activityStatus":"未投放","activityOffer":"55.00","activityOfferType":"CPM","dailyBudget":"1556.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"activityId":"B2TFu0Bngsk","activityName":"Xinxiliu","activityStatus":"未投放","activityOffer":"111.00","activityOfferType":"CPM","dailyBudget":"100.00","startDate":"2018-08-16","endDate":"2018-08-16","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * activityId : wr2um0cOsww
             * activityName : 头像大师-定向设备号
             * activityStatus : 投放中
             * activityOffer : 16000.00
             * activityOfferType : CPM
             * dailyBudget : 800.00
             * startDate : 2018-02-09
             * endDate : 2018-12-31
             * spend : 0.00
             * cpc : 0.00
             * cpm : 0.00
             * exposure : 0
             * click : 0
             * clickRate : 0.00
             */

            private String activityId;
            private String activityName;
            private String activityStatus;
            private String activityOffer;
            private String activityOfferType;
            private String dailyBudget;
            private String startDate;
            private String endDate;
            private String spend;
            private String cpc;
            private String cpm;
            private String exposure;
            private String click;
            private String clickRate;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public String getActivityStatus() {
                return activityStatus;
            }

            public void setActivityStatus(String activityStatus) {
                this.activityStatus = activityStatus;
            }

            public String getActivityOffer() {
                return activityOffer;
            }

            public void setActivityOffer(String activityOffer) {
                this.activityOffer = activityOffer;
            }

            public String getActivityOfferType() {
                return activityOfferType;
            }

            public void setActivityOfferType(String activityOfferType) {
                this.activityOfferType = activityOfferType;
            }

            public String getDailyBudget() {
                return dailyBudget;
            }

            public void setDailyBudget(String dailyBudget) {
                this.dailyBudget = dailyBudget;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getSpend() {
                return spend;
            }

            public void setSpend(String spend) {
                this.spend = spend;
            }

            public String getCpc() {
                return cpc;
            }

            public void setCpc(String cpc) {
                this.cpc = cpc;
            }

            public String getCpm() {
                return cpm;
            }

            public void setCpm(String cpm) {
                this.cpm = cpm;
            }

            public String getExposure() {
                return exposure;
            }

            public void setExposure(String exposure) {
                this.exposure = exposure;
            }

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getClickRate() {
                return clickRate;
            }

            public void setClickRate(String clickRate) {
                this.clickRate = clickRate;
            }
        }
    }
}
