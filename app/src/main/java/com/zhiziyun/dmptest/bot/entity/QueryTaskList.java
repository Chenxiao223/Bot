package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/29.
 */

public class QueryTaskList {


    /**
     * total : 1
     * rows : [{"entityId":"ByZcH0e9VCg","createTime":"2018-09-06 16:21:37","updateTime":null,"siteId":"0zoTLi29XRgq","name":"0906-2","macs":["b4:a3:82:34:7c:15,c4:86:e9:7e:8b:da,9c:d2:1e:87:5a:db"],"linksToBrowse":["http://test2.zhiziyun.com/box/wxwifi/generate.action?appid=wxd1bd34720ca4d325&shopId=17193887&mac=c4:86:e9:7e:8b:da&ssid=ZZY&secretkey=5b4545e1034bf8188ef5fbddd52b1db6"],"linksDone":null,"total":1,"count":null,"status":0,"show":true,"deleted":false,"valid":false}]
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
         * entityId : ByZcH0e9VCg
         * createTime : 2018-09-06 16:21:37
         * updateTime : null
         * siteId : 0zoTLi29XRgq
         * name : 0906-2
         * macs : ["b4:a3:82:34:7c:15,c4:86:e9:7e:8b:da,9c:d2:1e:87:5a:db"]
         * linksToBrowse : ["http://test2.zhiziyun.com/box/wxwifi/generate.action?appid=wxd1bd34720ca4d325&shopId=17193887&mac=c4:86:e9:7e:8b:da&ssid=ZZY&secretkey=5b4545e1034bf8188ef5fbddd52b1db6"]
         * linksDone : null
         * total : 1
         * count : null
         * status : 0
         * show : true
         * deleted : false
         * valid : false
         */

        private String entityId;
        private String createTime;
        private String updateTime;
        private String siteId;
        private String name;
        private Object linksDone;
        private int total;
        private int count;
        private int status;
        private boolean show;
        private boolean deleted;
        private boolean valid;
        private List<String> macs;
        private List<String> linksToBrowse;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
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

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getLinksDone() {
            return linksDone;
        }

        public void setLinksDone(Object linksDone) {
            this.linksDone = linksDone;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public List<String> getMacs() {
            return macs;
        }

        public void setMacs(List<String> macs) {
            this.macs = macs;
        }

        public List<String> getLinksToBrowse() {
            return linksToBrowse;
        }

        public void setLinksToBrowse(List<String> linksToBrowse) {
            this.linksToBrowse = linksToBrowse;
        }
    }
}
