package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class CrowdList {

    /**
     * total : 21
     * rows : [{"id":81,"name":"417短信测试","description":null,"beginTime":"2018-04-17 00:00:00","endTime":"2018-04-17 23:59:59","tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"20180417\",\"lte\":\"20180417\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-17 15:03:05","updateTime":"2018-04-17 15:03:05","status":false,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":82,"name":"移动端测试","description":null,"beginTime":"2018-04-17 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-17 15:56:37","updateTime":"2018-04-18 10:50:19","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":0,"lastSyncTime":null},{"id":83,"name":"移动端测试iOS","description":null,"beginTime":"2018-04-17 00:00:00","endTime":null,"tags":"1001|1002|1004|1103|1267|1268","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"match_phrase\":{\"game.name\":\"休闲时间\"}},{\"match_phrase\":{\"game.name\":\"切东西\"}},{\"match_phrase\":{\"game.name\":\"减压\"}}]},{\"or\":[{\"match_phrase\":{\"app.name\":\"网购\"}}]},{\"or\":[{\"match_phrase\":{\"population.name\":\"性别-女\"}},{\"match_phrase\":{\"population.name\":\"年龄-19-25岁\"}}]},{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-17 17:34:36","updateTime":"2018-04-18 10:48:26","status":false,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":84,"name":"移动端测试iOS2","description":null,"beginTime":"2018-04-17 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-17 18:10:04","updateTime":"2018-04-18 10:48:24","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":85,"name":"移动测试iOS3","description":null,"beginTime":"2018-04-17 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-17 18:20:46","updateTime":"2018-04-18 10:48:21","status":false,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":86,"name":"1808测试","description":null,"beginTime":"2018-04-18 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-18 08:57:04","updateTime":"2018-04-18 08:57:04","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":0,"lastSyncTime":null},{"id":87,"name":"移动测试iOS0418","description":null,"beginTime":"2018-04-18 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-18 09:56:03","updateTime":"2018-04-18 10:48:17","status":false,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":88,"name":"移动测试iOS04","description":null,"beginTime":"2018-04-18 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-18 10:03:06","updateTime":"2018-04-18 10:43:00","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":89,"name":"测试iOS","description":null,"beginTime":"2018-04-18 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-18 10:07:14","updateTime":"2018-04-18 11:27:39","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"taskStatus":1,"lastSyncTime":null},{"id":90,"name":"测试iOS04","description":null,"beginTime":"2018-04-18 00:00:00","endTime":null,"tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_date\":{\"gte\":\"ZZDATEZZ\"}}}}}]}}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-04-18 10:58:28","updateTime":"2018-04-18 10:58:28","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":null,"visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":false,"taskStatus":null,"lastSyncTime":null}]
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 81
         * name : 417短信测试
         * description : null
         * beginTime : 2018-04-17 00:00:00
         * endTime : 2018-04-17 23:59:59
         * tags :
         * esCondition : {"query":{"filtered":{"filter":{"and":[{"or":[{"nested":{"path":"probe_log","query":{"and":[{"match":{"probe_log.probe_mac":"a020a61114ff"}},{"nested":{"path":"probe_log.visit","query":{"range":{"probe_log.visit.visit_date":{"gte":"20180417","lte":"20180417"}}}}}]}}}]}]}}}}
         * siteId : 0zoTLi29XRgq
         * createTime : 2018-04-17 15:03:05
         * updateTime : 2018-04-17 15:03:05
         * status : false
         * probes : a020a61114ff
         * activity :
         * adCustomMobileGroupId : null
         * visitorType : -1
         * count : null
         * hasTransForDev : false
         * hasTranForPhone : true
         * taskStatus : 1
         * lastSyncTime : null
         */

        private int id;
        private String name;
        private Object description;
        private String beginTime;
        private String endTime;
        private String tags;
        private String esCondition;
        private String siteId;
        private String createTime;
        private String updateTime;
        private boolean status;
        private String probes;
        private String activity;
        private Object adCustomMobileGroupId;
        private int visitorType;
        private int count;
        private boolean hasTransForDev;
        private boolean hasTranForPhone;
        private String taskStatus;
        private Object lastSyncTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getEsCondition() {
            return esCondition;
        }

        public void setEsCondition(String esCondition) {
            this.esCondition = esCondition;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getProbes() {
            return probes;
        }

        public void setProbes(String probes) {
            this.probes = probes;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public Object getAdCustomMobileGroupId() {
            return adCustomMobileGroupId;
        }

        public void setAdCustomMobileGroupId(Object adCustomMobileGroupId) {
            this.adCustomMobileGroupId = adCustomMobileGroupId;
        }

        public int getVisitorType() {
            return visitorType;
        }

        public void setVisitorType(int visitorType) {
            this.visitorType = visitorType;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isHasTransForDev() {
            return hasTransForDev;
        }

        public void setHasTransForDev(boolean hasTransForDev) {
            this.hasTransForDev = hasTransForDev;
        }

        public boolean isHasTranForPhone() {
            return hasTranForPhone;
        }

        public void setHasTranForPhone(boolean hasTranForPhone) {
            this.hasTranForPhone = hasTranForPhone;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public Object getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(Object lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }
    }
}
