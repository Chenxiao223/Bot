package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/5/31.
 */

public class QueryCrowd {

    /**
     * obj : {"id":1198,"name":"iOS测试一下自己","description":null,"beginTime":"2018-05-29 09:31:48","endTime":"2018-05-29 15:59:59","tags":"","esCondition":"{\"query\":{\"filtered\":{\"filter\":{\"and\":[{\"or\":[{\"nested\":{\"path\":\"probe_log\",\"query\":{\"and\":[{\"match\":{\"probe_log.probe_mac\":\"a020a61114ff\"}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.entering_time\":{\"gte\":\"20180529\",\"lte\":\"20180529\"}}}}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.entering_rssi\":{\"lte\":99}}}}},{\"nested\":{\"path\":\"probe_log.visit\",\"query\":{\"range\":{\"probe_log.visit.visit_length\":{\"gte\":0,\"lte\":10}}}}}]}}}]},{\"match\":{}},{\"or\":[{\"match\":{\"basic.name\":\"VIVO\"}}]}]}}}}","siteId":"0zoTLi29XRgq","createTime":"2018-05-29 17:33:49","updateTime":"2018-05-29 17:33:49","status":true,"probes":"a020a61114ff","activity":"","adCustomMobileGroupId":"z70za0REYfu","visitorType":-1,"count":null,"hasTransForDev":false,"hasTranForPhone":true,"probeDistance":100,"os":"imei","phoneBrand":"VIVO","stayDuration":0,"lastSyncTime":null}
     * success : true
     * msg :
     */

    private ObjBean obj;
    private boolean success;
    private String msg;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ObjBean {
        /**
         * id : 1198
         * name : iOS测试一下自己
         * description : null
         * beginTime : 2018-05-29 09:31:48
         * endTime : 2018-05-29 15:59:59
         * tags :
         * esCondition : {"query":{"filtered":{"filter":{"and":[{"or":[{"nested":{"path":"probe_log","query":{"and":[{"match":{"probe_log.probe_mac":"a020a61114ff"}},{"nested":{"path":"probe_log.visit","query":{"range":{"probe_log.visit.entering_time":{"gte":"20180529","lte":"20180529"}}}}},{"nested":{"path":"probe_log.visit","query":{"range":{"probe_log.visit.entering_rssi":{"lte":99}}}}},{"nested":{"path":"probe_log.visit","query":{"range":{"probe_log.visit.visit_length":{"gte":0,"lte":10}}}}}]}}}]},{"match":{}},{"or":[{"match":{"basic.name":"VIVO"}}]}]}}}}
         * siteId : 0zoTLi29XRgq
         * createTime : 2018-05-29 17:33:49
         * updateTime : 2018-05-29 17:33:49
         * status : true
         * probes : a020a61114ff
         * activity :
         * adCustomMobileGroupId : z70za0REYfu
         * visitorType : -1
         * count : null
         * hasTransForDev : false
         * hasTranForPhone : true
         * probeDistance : 100
         * os : imei
         * phoneBrand : VIVO
         * stayDuration : 0
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
        private String adCustomMobileGroupId;
        private int visitorType;
        private Object count;
        private boolean hasTransForDev;
        private boolean hasTranForPhone;
        private int probeDistance;
        private String os;
        private String phoneBrand;
        private int stayDuration;
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

        public String getAdCustomMobileGroupId() {
            return adCustomMobileGroupId;
        }

        public void setAdCustomMobileGroupId(String adCustomMobileGroupId) {
            this.adCustomMobileGroupId = adCustomMobileGroupId;
        }

        public int getVisitorType() {
            return visitorType;
        }

        public void setVisitorType(int visitorType) {
            this.visitorType = visitorType;
        }

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
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

        public int getProbeDistance() {
            return probeDistance;
        }

        public void setProbeDistance(int probeDistance) {
            this.probeDistance = probeDistance;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getPhoneBrand() {
            return phoneBrand;
        }

        public void setPhoneBrand(String phoneBrand) {
            this.phoneBrand = phoneBrand;
        }

        public int getStayDuration() {
            return stayDuration;
        }

        public void setStayDuration(int stayDuration) {
            this.stayDuration = stayDuration;
        }

        public Object getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(Object lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }
    }
}
