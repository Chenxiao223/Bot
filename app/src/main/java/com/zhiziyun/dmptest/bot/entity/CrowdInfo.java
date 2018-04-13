package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class CrowdInfo {


    /**
     * total : 207
     * rows : [{"id":"00f4990da0e7b50c865fc87177c6d8f0","encryptPhone":null,"mark":1,"type":0,"name":"我想知道什么的好","siteId":"0zoTLi29XRgq","desc":"陆家嘴滨江新城小区内","charger":"智商的东西是真的","createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"138****4587","phoneNumber":null},{"id":"066830067a041504624278cccdc0201e","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"138****5235","phoneNumber":null},{"id":"093264e0d0e517f0b7b6e764076d4927","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"135****5759","phoneNumber":null},{"id":"0b60839e3a22ae0c8c85bda539443b88","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"138****2648","phoneNumber":null},{"id":"144a5eb4554a7f1707821a1e68ee9fab","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"150****8997","phoneNumber":null},{"id":"14a4c28208dc3400a1f8f5cf55ff00a4","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"136****9922","phoneNumber":null},{"id":"195702f3be40caa1462e93fedd9f97f7","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"137****3587","phoneNumber":null},{"id":"1c575232ec19b44b829fd7589d77fc80","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"139****6199","phoneNumber":null},{"id":"1d0f6b88a317103c4ccd62e9f8d07f56","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"158****8898","phoneNumber":null},{"id":"240938dbae9292f5db210ccba907f3fe","encryptPhone":null,"mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-12 10:32:33","smsCount":0,"hidePhoneNumber":"152****7273","phoneNumber":null}]
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
         * id : 00f4990da0e7b50c865fc87177c6d8f0
         * encryptPhone : null
         * mark : 1
         * type : 0
         * name : 我想知道什么的好
         * siteId : 0zoTLi29XRgq
         * desc : 陆家嘴滨江新城小区内
         * charger : 智商的东西是真的
         * createTime : 2018-04-12 10:32:33
         * smsCount : 0
         * hidePhoneNumber : 138****4587
         * phoneNumber : null
         */

        private String id;
        private Object encryptPhone;
        private int mark;
        private int type;
        private String name;
        private String siteId;
        private String desc;
        private String charger;
        private String createTime;
        private int smsCount;
        private String hidePhoneNumber;
        private Object phoneNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getEncryptPhone() {
            return encryptPhone;
        }

        public void setEncryptPhone(Object encryptPhone) {
            this.encryptPhone = encryptPhone;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCharger() {
            return charger;
        }

        public void setCharger(String charger) {
            this.charger = charger;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSmsCount() {
            return smsCount;
        }

        public void setSmsCount(int smsCount) {
            this.smsCount = smsCount;
        }

        public String getHidePhoneNumber() {
            return hidePhoneNumber;
        }

        public void setHidePhoneNumber(String hidePhoneNumber) {
            this.hidePhoneNumber = hidePhoneNumber;
        }

        public Object getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(Object phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
