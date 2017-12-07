package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class TanzhenList {

    /**
     * total : 5
     * rows : [{"id":2,"mac":"18fe34cb9430","siteId":"0zoTLi29XRgq","signalStrength":75,"floorArea":300,"longitude":121.5728,"latitude":31.21314,"storeId":1,"name":"智子云品牌部","docked":true,"createTime":"2017-11-08 16:51:49","updateTime":"2017-11-02 14:08:32"},{"id":4,"mac":"a020a610bee1","siteId":"0zoTLi29XRgq","signalStrength":73,"floorArea":200,"longitude":121.51108,"latitude":31.301428,"storeId":1,"name":"1705-FQ","docked":true,"createTime":"2017-11-08 16:51:49","updateTime":"2017-10-24 13:07:46"},{"id":11,"mac":"a020a61114fd","siteId":"0zoTLi29XRgq","signalStrength":77,"floorArea":400,"longitude":121.54248,"latitude":31.29876,"storeId":1,"name":"1701-前台","docked":true,"createTime":"2017-11-08 16:51:49","updateTime":"2017-10-24 13:08:46"},{"id":27,"mac":"a020a61114ff","siteId":"0zoTLi29XRgq","signalStrength":70,"floorArea":100,"longitude":121.48115,"latitude":31.240252,"storeId":1,"name":"1705-MT","docked":true,"createTime":"2017-11-08 16:51:49","updateTime":"2017-10-31 12:12:23"},{"id":29,"mac":"a020a61114fe","siteId":"0zoTLi29XRgq","signalStrength":70,"floorArea":100,"longitude":121.47454,"latitude":31.239573,"storeId":1,"name":"a020a61114fe","docked":false,"createTime":"2017-11-08 16:51:49","updateTime":"2017-11-06 10:14:11"}]
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
         * id : 2
         * mac : 18fe34cb9430
         * siteId : 0zoTLi29XRgq
         * signalStrength : 75
         * floorArea : 300.0
         * longitude : 121.5728
         * latitude : 31.21314
         * storeId : 1
         * name : 智子云品牌部
         * docked : true
         * createTime : 2017-11-08 16:51:49
         * updateTime : 2017-11-02 14:08:32
         */

        private String id;
        private String mac;
        private String siteId;
        private int signalStrength;
        private String floorArea;
        private double longitude;
        private double latitude;
        private int storeId;
        private String name;
        private boolean docked;
        private String createTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public int getSignalStrength() {
            return signalStrength;
        }

        public void setSignalStrength(int signalStrength) {
            this.signalStrength = signalStrength;
        }

        public String getFloorArea() {
            return floorArea;
        }

        public void setFloorArea(String floorArea) {
            this.floorArea = floorArea;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDocked() {
            return docked;
        }

        public void setDocked(boolean docked) {
            this.docked = docked;
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
    }
}
