package com.zhiziyun.dmptest.bot.entity;

import com.google.gson.annotations.SerializedName;

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
     * response : {"activityId":"z0XNk0nE6Pu","activityName":"iOS计划测","activityType":"1","startTime":"2018-05-25 18:08:45","endTime":"2018-05-25 18:08:56","targetHours":{"2":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"3":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"4":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"5":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"6":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"]},"budget":-1,"dailyBudget":"1.00","pricingType":1,"price":"2.00","pacingType":2,"tagIds":["yE0gx13Tfhe","yMLRm0UUUww","yDVdV0PUBvW","yJXZY07ZZlK","yOxcS0exaLu","yOiE40D2ZCU"],"probeTagIds":["yMLRm0UUUww","yOxcS0exaLu","yOiE40D2ZCU"],"wifiTagIds":["yE0gx13Tfhe","yDVdV0PUBvW"],"adclickTagIds":["yJXZY07ZZlK"],"creativeIds":["yF8EO10WJ44"]}
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
         * activityId : z0XNk0nE6Pu
         * activityName : iOS计划测
         * activityType : 1
         * startTime : 2018-05-25 18:08:45
         * endTime : 2018-05-25 18:08:56
         * targetHours : {"2":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"3":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"4":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"5":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"],"6":["09","08","07","06","05","04","03","02","01","00","19","18","17","16","15","14","13","12","11","10","23","22","21","20"]}
         * budget : -1
         * dailyBudget : 1.00
         * pricingType : 1
         * price : 2.00
         * pacingType : 2
         * tagIds : ["yE0gx13Tfhe","yMLRm0UUUww","yDVdV0PUBvW","yJXZY07ZZlK","yOxcS0exaLu","yOiE40D2ZCU"]
         * probeTagIds : ["yMLRm0UUUww","yOxcS0exaLu","yOiE40D2ZCU"]
         * wifiTagIds : ["yE0gx13Tfhe","yDVdV0PUBvW"]
         * adclickTagIds : ["yJXZY07ZZlK"]
         * creativeIds : ["yF8EO10WJ44"]
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
        private String price;
        private int pacingType;
        private List<String> tagIds;
        private List<String> probeTagIds;
        private List<String> wifiTagIds;
        private List<String> adclickTagIds;
        private List<String> creativeIds;

        public Hashtable<String, List<String>> getTargetHours() {
            return targetHours;
        }

        public void setTargetHours(Hashtable<String, List<String>> targetHours) {
            this.targetHours = targetHours;
        }

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

        public List<String> getProbeTagIds() {
            return probeTagIds;
        }

        public void setProbeTagIds(List<String> probeTagIds) {
            this.probeTagIds = probeTagIds;
        }

        public List<String> getWifiTagIds() {
            return wifiTagIds;
        }

        public void setWifiTagIds(List<String> wifiTagIds) {
            this.wifiTagIds = wifiTagIds;
        }

        public List<String> getAdclickTagIds() {
            return adclickTagIds;
        }

        public void setAdclickTagIds(List<String> adclickTagIds) {
            this.adclickTagIds = adclickTagIds;
        }

        public List<String> getCreativeIds() {
            return creativeIds;
        }

        public void setCreativeIds(List<String> creativeIds) {
            this.creativeIds = creativeIds;
        }

    }
}
