package com.zhiziyun.dmptest.bot.entity;


import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class ChooseSms {


    /**
     * total : 27
     * rows : [{"entityId":"zE2Ww0pwSuk","name":"2001测试","siteId":"0zoTLi29XRgq","content":"路路通","type":0,"updateTime":"2018-06-20 11:02:07","adVerifyStatus":"NOTPOST","signature":"智子云"},{"entityId":"zE2r706dGJW","name":"2053测试","siteId":"0zoTLi29XRgq","content":"测试","type":0,"updateTime":"2018-06-20 10:54:22","adVerifyStatus":"NOTPOST","signature":"智子云"},{"entityId":"zDWT10HXJRu","name":"测试新渠道-药品","siteId":"0zoTLi29XRgq","content":"年货节来啦，每晚20:00积分福利社200积分限抢50或100优惠券呦，戳一戳{c.tb.cn/c.qP5u}","type":0,"updateTime":"2018-06-20 09:29:36","adVerifyStatus":"VERIFIED","signature":"智子云","smsCategoryId":"28"},{"entityId":"zDWPq1345TG","name":"测试老渠道-页游","siteId":"0zoTLi29XRgq","content":"年货节来啦，每晚20:00积分福利社200积分限抢50或100优惠券呦，戳一戳{c.tb.cn/c.qP5u}","type":0,"updateTime":"2018-06-20 09:28:43","adVerifyStatus":"VERIFIED","signature":"智子云","smsCategoryId":"11"},{"entityId":"zvqHp0vrUv6","name":"t-0614-7","siteId":"0zoTLi29XRgq","content":"123","type":0,"updateTime":"2018-06-14 19:01:42","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"11"},{"entityId":"zvmJ60qngGI","name":"预览","siteId":"0zoTLi29XRgq","content":"预览","type":0,"updateTime":"2018-06-14 18:01:01","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"11"},{"entityId":"zvmB50WaW9q","name":"t-0614-6","siteId":"0zoTLi29XRgq","content":"t-0614-6","type":0,"updateTime":"2018-06-14 17:59:03","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"13"},{"entityId":"zvmyD11b6fK","name":"t-0614-5","siteId":"0zoTLi29XRgq","content":"t-0614-5","type":0,"updateTime":"2018-06-14 17:58:27","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"12"},{"entityId":"zvmwT0fgwDu","name":"t-0614-4","siteId":"0zoTLi29XRgq","content":"t-0614-","type":0,"updateTime":"2018-06-14 17:58:01","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"11"},{"entityId":"zvco40OMUoM","name":"t-0614-3","siteId":"0zoTLi29XRgq","content":"ssss","type":0,"updateTime":"2018-06-14 15:23:06","adVerifyStatus":"NOTPOST","signature":"智子云","smsCategoryId":"11"}]
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
         * entityId : zE2Ww0pwSuk
         * name : 2001测试
         * siteId : 0zoTLi29XRgq
         * content : 路路通
         * type : 0
         * updateTime : 2018-06-20 11:02:07
         * adVerifyStatus : NOTPOST
         * signature : 智子云
         * smsCategoryId : 28
         */

        private String entityId;
        private String name;
        private String siteId;
        private String content;
        private int type;
        private String updateTime;
        private String adVerifyStatus;
        private String signature;
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

        public String getSmsCategoryId() {
            return smsCategoryId;
        }

        public void setSmsCategoryId(String smsCategoryId) {
            this.smsCategoryId = smsCategoryId;
        }
    }
}
