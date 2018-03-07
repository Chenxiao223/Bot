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
     * response : {"total":757,"data":[{"activityId":"t4Qvn0ZJlfy","activityName":"信息流0925","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"taT3o0d2qCA","activityName":"1","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":0.09,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"u6W6o0v64gm","activityName":"171106","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"utFZa0AmA9O","activityName":"企创自动提交","activityStatus":"未投放","activityOffer":0.05,"activityOfferType":"CPM","dailyBudget":9.09,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uEiQJ0MzwS4","activityName":"同步BID-PC-1128","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uEgcd0fcPpm","activityName":"同步BID-腾讯-1128","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uXZOc0b0sog","activityName":"测试同步ip组","activityStatus":"未投放","activityOffer":12,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vkQRb0v0sSc","activityName":"测试同步ip组-copy","activityStatus":"未投放","activityOffer":12,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vkTrZ0oYMcU","activityName":"测试同步ip组-copy-2","activityStatus":"未投放","activityOffer":2,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vvBuT0sRaGk","activityName":"测试同步人群-180102-2","activityStatus":"未投放","activityOffer":2,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0}]}
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
         * total : 757
         * data : [{"activityId":"t4Qvn0ZJlfy","activityName":"信息流0925","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"taT3o0d2qCA","activityName":"1","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":0.09,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"u6W6o0v64gm","activityName":"171106","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"utFZa0AmA9O","activityName":"企创自动提交","activityStatus":"未投放","activityOffer":0.05,"activityOfferType":"CPM","dailyBudget":9.09,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uEiQJ0MzwS4","activityName":"同步BID-PC-1128","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uEgcd0fcPpm","activityName":"同步BID-腾讯-1128","activityStatus":"未投放","activityOffer":1,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"uXZOc0b0sog","activityName":"测试同步ip组","activityStatus":"未投放","activityOffer":12,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vkQRb0v0sSc","activityName":"测试同步ip组-copy","activityStatus":"未投放","activityOffer":12,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vkTrZ0oYMcU","activityName":"测试同步ip组-copy-2","activityStatus":"未投放","activityOffer":2,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0},{"activityId":"vvBuT0sRaGk","activityName":"测试同步人群-180102-2","activityStatus":"未投放","activityOffer":2,"activityOfferType":"CPM","dailyBudget":90.91,"spend":0,"cpc":0,"cpm":0,"exposure":0,"click":0,"clickRate":0}]
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
             * activityId : t4Qvn0ZJlfy
             * activityName : 信息流0925
             * activityStatus : 未投放
             * activityOffer : 1.0
             * activityOfferType : CPM
             * dailyBudget : 90.91
             * spend : 0.0
             * cpc : 0.0
             * cpm : 0.0
             * exposure : 0
             * click : 0
             * clickRate : 0.0
             */

            private String activityId;
            private String activityName;
            private String activityStatus;
            private String activityOffer;
            private String activityOfferType;
            private String dailyBudget;
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
