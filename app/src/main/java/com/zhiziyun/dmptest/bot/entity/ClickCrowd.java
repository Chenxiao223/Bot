package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/5/22.
 */

public class ClickCrowd {

    /**
     * obj : {"id":2,"name":"测试人群","siteId":"0zoTLi29XRgq","plan":"v3OYz0SwV9e","beginTime":"2018-05-01 18:08:31","endTime":"2018-05-11 18:08:31","createTime":"2018-05-09 18:08:31","updateTime":"2018-05-09 18:08:31","adCustomMobileGroupId":"yCDmJ0y08qQ","hasTransForPhone":null,"lastSyncTime":null,"count":0,"status":true}
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
         * id : 2
         * name : 测试人群
         * siteId : 0zoTLi29XRgq
         * plan : v3OYz0SwV9e
         * beginTime : 2018-05-01 18:08:31
         * endTime : 2018-05-11 18:08:31
         * createTime : 2018-05-09 18:08:31
         * updateTime : 2018-05-09 18:08:31
         * adCustomMobileGroupId : yCDmJ0y08qQ
         * hasTransForPhone : null
         * lastSyncTime : null
         * count : 0
         * status : true
         */

        private int id;
        private String name;
        private String siteId;
        private String plan;
        private String beginTime;
        private String endTime;
        private String createTime;
        private String updateTime;
        private String adCustomMobileGroupId;
        private Object hasTransForPhone;
        private Object lastSyncTime;
        private int count;
        private boolean status;

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

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
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

        public String getAdCustomMobileGroupId() {
            return adCustomMobileGroupId;
        }

        public void setAdCustomMobileGroupId(String adCustomMobileGroupId) {
            this.adCustomMobileGroupId = adCustomMobileGroupId;
        }

        public Object getHasTransForPhone() {
            return hasTransForPhone;
        }

        public void setHasTransForPhone(Object hasTransForPhone) {
            this.hasTransForPhone = hasTransForPhone;
        }

        public Object getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(Object lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
