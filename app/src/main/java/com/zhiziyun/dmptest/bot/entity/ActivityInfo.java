package com.zhiziyun.dmptest.bot.entity;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ActivityInfo {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"activityId":"vZOdC0a6Xss","activityName":"APP活动测试-180122-01","activityType":"1","startTime":"2018-01-22 00:00:00","endTime":"2018-01-25 00:00:00","targetHours":{"5":["13","12","11"],"2":["3","2","1","0"],"7":["23","22","21","20"]},"budget":-1,"dailyBudget":"80.00","pricingType":1,"price":4,"pacingType":2,"tagIds":["vZHNO047bq0","vZHMu0iY19e","vZHmh05XPC8"],"creativeIds":["vZIgL0QgQda","vZGnK0bU86Y","vZIdo0GtnI4"]}
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
         * activityId : vZOdC0a6Xss
         * activityName : APP活动测试-180122-01
         * activityType : 1
         * startTime : 2018-01-22 00:00:00
         * endTime : 2018-01-25 00:00:00
         * targetHours : {"5":["13","12","11"],"2":["3","2","1","0"],"7":["23","22","21","20"]}
         * budget : -1
         * dailyBudget : 80.00
         * pricingType : 1
         * price : 4.0
         * pacingType : 2
         * tagIds : ["vZHNO047bq0","vZHMu0iY19e","vZHmh05XPC8"]
         * creativeIds : ["vZIgL0QgQda","vZGnK0bU86Y","vZIdo0GtnI4"]
         */

        private String activityId;
        private String activityName;
        private String activityType;
        private String startTime;
        private String endTime;
        private Hashtable<String,List<String>> targetHours;
        private int budget;
        private String dailyBudget;
        private int pricingType;
        private double price;
        private int pacingType;
        private List<String> tagIds;
        private List<String> creativeIds;

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

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Hashtable<String, List<String>> getTargetHours() {
            return targetHours;
        }

        public void setTargetHours(Hashtable<String, List<String>> targetHours) {
            this.targetHours = targetHours;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getDailyBudget() {
            return dailyBudget;
        }

        public void setDailyBudget(String dailyBudget) {
            this.dailyBudget = dailyBudget;
        }

        public int getPricingType() {
            return pricingType;
        }

        public void setPricingType(int pricingType) {
            this.pricingType = pricingType;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPacingType() {
            return pacingType;
        }

        public void setPacingType(int pacingType) {
            this.pacingType = pacingType;
        }

        public List<String> getTagIds() {
            return tagIds;
        }

        public void setTagIds(List<String> tagIds) {
            this.tagIds = tagIds;
        }

        public List<String> getCreativeIds() {
            return creativeIds;
        }

        public void setCreativeIds(List<String> creativeIds) {
            this.creativeIds = creativeIds;
        }
    }
}
