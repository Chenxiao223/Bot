package com.zhiziyun.dmptest.bot.entity;


import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class ChooseSms {

    /**
     * total : 16
     * rows : [{"entityId":"wR5ie12VvIA","name":"线上审核发布v1","siteId":"0zoTLi29XRgq","content":"智子营销云新功能上线，戳http://t.cn/RTtPRhW 进行体验产品，揭秘大数据营销魅力。","type":0,"updateTime":"2018-02-26 17:06:23","adVerifyStatus":"VERIFIED","signature":"智子云"},{"entityId":"wTGAx12veWQ","name":"228短信测试","siteId":"0zoTLi29XRgq","content":"智子营销云新功能上线，戳http://t.cn/RTtPRhW进行体验产品，揭秘大数据营销魅力。","type":0,"updateTime":"2018-02-28 10:05:29","adVerifyStatus":"VERIFIED","signature":"智子云"},{"entityId":"wVFG5028txK","name":"测试20180301","siteId":"0zoTLi29XRgq","content":"智子营销云新功能上线，戳http://t.cn/RTtPRhW进行体验产品，揭秘大数据营销魅力。","type":0,"updateTime":"2018-03-01 17:25:38","adVerifyStatus":"VERIFIED","signature":"智子云"},{"entityId":"y8dAt0aYTZu","name":"智子云420上线测试","siteId":"0zoTLi29XRgq","content":"智子营销云新功能上线，戳http://t.cn/RTtPRhW进行体验产品，揭秘大数据营销魅力.","type":0,"updateTime":"2018-04-26 10:56:15","adVerifyStatus":"VERIFIED","signature":"智子云"},{"entityId":"ysYqt0lHByU","name":"短信测试0503-1","siteId":"0zoTLi29XRgq","content":"短信测试0503-1短信测试0503-1短信测试0503-1","type":0,"updateTime":"2018-05-03 09:39:54","adVerifyStatus":"ONPROGRESS","signature":"智子云"},{"entityId":"ysYB205tupi","name":"短信测试0503-2","siteId":"0zoTLi29XRgq","content":"短信测试0503-1短信测试0503-1短信测试0503-1短信测试0503-12222222","type":0,"updateTime":"2018-05-03 09:43:31","adVerifyStatus":"ONPROGRESS","signature":"智子云"},{"entityId":"ysYJi01pBBK","name":"短信测试0503-3","siteId":"0zoTLi29XRgq","content":"短信测试0503-1短信测试0503-3短信测试0503-333","type":0,"updateTime":"2018-05-03 09:45:26","adVerifyStatus":"ONPROGRESS","signature":"智子云"},{"entityId":"yzuPv0yvpC0","name":"短信测试0507-1","siteId":"0zoTLi29XRgq","content":"短信测试0507-1短信测试0507-1短信测试0507-1短信测试0507-1","type":0,"updateTime":"2018-05-07 16:37:02","adVerifyStatus":"ONPROGRESS","signature":"智子云"},{"entityId":"yUTvq0za6u4","name":"闪信","siteId":"0zoTLi29XRgq","content":"闪信测试13211111","type":1,"updateTime":"2018-05-23 17:38:09","adVerifyStatus":"NOTPOST","signature":"智子云"},{"entityId":"yVWf3083bNe","name":"闪信0522","siteId":"0zoTLi29XRgq","content":"智子营销云新功能上线，戳http://t.cn/RTtPRhW进行体验产品，揭秘大数据营销魅力。","type":1,"updateTime":"2018-05-22 10:50:25","adVerifyStatus":"VERIFIED","signature":"智子云"}]
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
         * entityId : wR5ie12VvIA
         * name : 线上审核发布v1
         * siteId : 0zoTLi29XRgq
         * content : 智子营销云新功能上线，戳http://t.cn/RTtPRhW 进行体验产品，揭秘大数据营销魅力。
         * type : 0
         * updateTime : 2018-02-26 17:06:23
         * adVerifyStatus : VERIFIED
         * signature : 智子云
         */

        private String entityId;
        private String name;
        private String siteId;
        private String content;
        private int type;
        private String updateTime;
        private String adVerifyStatus;
        private String signature;

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
    }
}
