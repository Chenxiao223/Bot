package com.zhiziyun.dmptest.bot.entity;


import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class ChooseSms {


    /**
     * total : 53
     * rows : [{"entityId":"AHC140NeJOg","name":"180802-1","siteId":"0zoTLi29XRgq","content":"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww ","type":0,"updateTime":"2018-08-08 15:41:42","adVerifyStatus":"REJECTED","signature":"智子云","verifyMessage":"22223333","smsCategoryId":"4"},{"entityId":"AHm4515oB68","name":"测试短信类型","siteId":"0zoTLi29XRgq","content":"afdsfsaf ","type":2,"updateTime":"2018-08-02 09:51:10","adVerifyStatus":"ONPROGRESS","signature":"智子云","smsCategoryId":"22"},{"entityId":"AyxrV0DEybu","name":"长度检查","siteId":"0zoTLi29XRgq","content":"西安交大附属生物医药职业学校，初中生选五年制大专或三年制高考班，建西安市学籍,学习高中文化课详询13609155264 ","type":0,"updateTime":"2018-07-30 10:31:07","adVerifyStatus":"ONPROGRESS","signature":"智子云","smsCategoryId":"5"},{"entityId":"AxaTM0QwTpm","name":"短信测试","siteId":"0zoTLi29XRgq","content":"这是一封测试短信","type":0,"updateTime":"2018-07-26 17:10:18","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"11"},{"entityId":"AtXZb0c7PTq","name":"3124测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":1,"updateTime":"2018-07-24 14:31:58","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"22"},{"entityId":"AtGqC0PXlBu","name":"0324测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":0,"updateTime":"2018-07-24 10:03:47","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"13"},{"entityId":"AtGoa0MJ8VW","name":"0224测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":1,"updateTime":"2018-07-24 10:03:11","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"22"},{"entityId":"AtFDA0gTxJe","name":"5124测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":1,"updateTime":"2018-07-24 09:51:42","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"22"},{"entityId":"AtFxn0feDQc","name":"4924测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":1,"updateTime":"2018-07-24 09:50:11","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"22"},{"entityId":"AtFow065Xna","name":"4724测试","siteId":"0zoTLi29XRgq","content":"测试 ","type":1,"updateTime":"2018-07-24 09:48:00","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"22"}]
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
         * entityId : AHC140NeJOg
         * name : 180802-1
         * siteId : 0zoTLi29XRgq
         * content : wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
         * type : 0
         * updateTime : 2018-08-08 15:41:42
         * adVerifyStatus : REJECTED
         * signature : 智子云
         * verifyMessage : 22223333
         * smsCategoryId : 4
         */

        private String entityId;
        private String name;
        private String siteId;
        private String content;
        private int type;
        private String updateTime;
        private String adVerifyStatus;
        private String signature;
        private String verifyMessage;
        private String smsCategoryId;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getAdVerifyStatus() {
            return adVerifyStatus;
        }

        public void setAdVerifyStatus(String adVerifyStatus) {
            this.adVerifyStatus = adVerifyStatus;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getVerifyMessage() {
            return verifyMessage;
        }

        public void setVerifyMessage(String verifyMessage) {
            this.verifyMessage = verifyMessage;
        }

        public String getSmsCategoryId() {
            return smsCategoryId;
        }

        public void setSmsCategoryId(String smsCategoryId) {
            this.smsCategoryId = smsCategoryId;
        }
    }
}
