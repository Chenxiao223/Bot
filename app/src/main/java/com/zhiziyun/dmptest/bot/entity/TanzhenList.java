package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class TanzhenList {

    /**
     * total : 1
     * rows : [{"id":1387,"mac":"5ccf7f61dafd","siteId":"0zoTLi29XRgq","signalStrength":85,"floorArea":1963.5,"longitude":121.549785,"latitude":31.282463,"storeId":754,"active":true,"valid":true,"name":"智子2号-前台左","docked":true,"createTime":"2018-07-06 11:39:37","updateTime":"2018-07-09 16:40:59","bindTime":"2018-07-06 11:39:37"}]
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
         * id : 1387
         * mac : 5ccf7f61dafd
         * siteId : 0zoTLi29XRgq
         * signalStrength : 85
         * floorArea : 1963.5
         * longitude : 121.549785
         * latitude : 31.282463
         * storeId : 754
         * active : true
         * valid : true
         * name : 智子2号-前台左
         * docked : true
         * createTime : 2018-07-06 11:39:37
         * updateTime : 2018-07-09 16:40:59
         * bindTime : 2018-07-06 11:39:37
         */

        private String id;
        private String mac;
        private String siteId;
        private int signalStrength;
        private String floorArea;
        private double longitude;
        private double latitude;
        private int storeId;
        private boolean active;
        private String valid;
        private String name;
        private boolean docked;
        private String createTime;
        private String updateTime;
        private String bindTime;

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

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String isValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
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

        public String getBindTime() {
            return bindTime;
        }

        public void setBindTime(String bindTime) {
            this.bindTime = bindTime;
        }
    }
}
