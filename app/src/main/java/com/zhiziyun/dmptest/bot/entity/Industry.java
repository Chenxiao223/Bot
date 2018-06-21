package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14.
 */

public class Industry {

    /**
     * total : 0
     * rows : [{"entityId":"3","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"裤子","parentId":"1","signature":"1231","leaf":true,"valid":false,"deleted":false},{"entityId":"4","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"电脑","parentId":"1","signature":"1","leaf":true,"valid":false,"deleted":false},{"entityId":"5","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"美食","parentId":"1","signature":"123","leaf":true,"valid":false,"deleted":false},{"entityId":"10","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"游戏","parentId":"1","signature":"1231","leaf":false,"valid":false,"deleted":false},{"entityId":"14","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"服装","parentId":"1","signature":"1231","leaf":false,"valid":false,"deleted":false},{"entityId":"18","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"旅游","parentId":"1","signature":"12312","leaf":false,"valid":false,"deleted":false},{"entityId":"11","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"页游","parentId":"10","signature":"1231","leaf":true,"valid":false,"deleted":false},{"entityId":"12","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"端游","parentId":"10","signature":"1","leaf":true,"valid":false,"deleted":false},{"entityId":"13","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"手游","parentId":"10","signature":"asdsa","leaf":true,"valid":false,"deleted":false},{"entityId":"15","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"上衣","parentId":"14","signature":"","leaf":false,"valid":false,"deleted":false},{"entityId":"16","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"裤子","parentId":"14","signature":"","leaf":false,"valid":false,"deleted":false},{"entityId":"17","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"短裤","parentId":"14","signature":"","leaf":false,"valid":false,"deleted":false},{"entityId":"19","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"国内游","parentId":"18","signature":"asdsa","leaf":true,"valid":false,"deleted":false},{"entityId":"20","createTime":null,"updateTime":null,"channelId":"5b1f91e4e4b0ad079ecac932","name":"国际游","parentId":"18","signature":"asdsa","leaf":true,"valid":false,"deleted":false}]
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
         * entityId : 3
         * createTime : null
         * updateTime : null
         * channelId : 5b1f91e4e4b0ad079ecac932
         * name : 裤子
         * parentId : 1
         * signature : 1231
         * leaf : true
         * valid : false
         * deleted : false
         */

        private String entityId;
        private String createTime;
        private String updateTime;
        private String channelId;
        private String name;
        private String parentId;
        private String signature;
        private boolean leaf;
        private boolean valid;
        private boolean deleted;

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

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }
    }
}
