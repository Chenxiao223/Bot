package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class Tanzhen {

    /**
     * total : 0
     * rows : [{"id":285,"mac":"6001941c2d66","siteId":"0zoTLi29XRgq","signalStrength":70,"floorArea":100,"longitude":121.542,"latitude":31.2997,"storeId":3,"name":"智子云2017招商会1","docked":true,"createTime":"2018-01-04 15:02:45","updateTime":"2018-01-04 15:02:45"},{"id":286,"mac":"a020a61114ff","siteId":"0zoTLi29XRgq","signalStrength":70,"floorArea":100,"longitude":121.542,"latitude":31.2997,"storeId":3,"name":"智子云2017招商会2","docked":true,"createTime":"2017-12-11 11:44:47","updateTime":"2017-12-11 11:44:47"}]
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
         * id : 285
         * mac : 6001941c2d66
         * siteId : 0zoTLi29XRgq
         * signalStrength : 70
         * floorArea : 100.0
         * longitude : 121.542
         * latitude : 31.2997
         * storeId : 3
         * name : 智子云2017招商会1
         * docked : true
         * createTime : 2018-01-04 15:02:45
         * updateTime : 2018-01-04 15:02:45
         */

        private int id;
        private String mac;
        private String siteId;
        private int signalStrength;
        private double floorArea;
        private double longitude;
        private double latitude;
        private int storeId;
        private String name;
        private boolean docked;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public double getFloorArea() {
            return floorArea;
        }

        public void setFloorArea(double floorArea) {
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
