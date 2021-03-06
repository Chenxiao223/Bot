package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class Visitorsself {

    /**
     * total : 2184
     * rows : [{"probemac":"a020a61114ff","probeName":null,"did":"3AE72FB68BDB80BBA9B1CE64A7DCAC9D","dtype":"imei","mac":"34ce0006f9a6","gender":"","brand":"小米","model":"","rssi":83,"distance":null,"visittime":"2018-05-15 23:59:53","is_new":false,"isShown":null},{"probemac":"a020a61114ff","probeName":null,"did":"9C1595EFCEBA7C770024690A63A40080","dtype":"imei","mac":"3cbd3e78ad39","gender":"","brand":"小米","model":"","rssi":84,"distance":null,"visittime":"2018-05-15 23:59:53","is_new":false,"isShown":null},{"probemac":"5ccf7f61dafd","probeName":null,"did":"2D82040F0FFB2C18BE1AAF803498047F","dtype":"imei","mac":"f0b429d755b1","gender":"","brand":"小米","model":"","rssi":81,"distance":null,"visittime":"2018-05-15 23:59:47","is_new":false,"isShown":null},{"probemac":"5ccf7f61dafd","probeName":null,"did":"D0107C5E7B33C10C8EAD82D0B82053AF","dtype":"imei","mac":"74d21db051a3","gender":"","brand":"华为","model":"Mate 10 Pro","rssi":82,"distance":null,"visittime":"2018-05-15 23:59:41","is_new":false,"isShown":null},{"probemac":"a020a61114ff","probeName":null,"did":"545F775715EDF94FF8DA54E793AC6FD6","dtype":"imei","mac":"44c34608c6a5","gender":"","brand":"华为","model":"","rssi":99,"distance":null,"visittime":"2018-05-15 23:59:36","is_new":true,"isShown":null},{"probemac":"a020a61114ff","probeName":null,"did":"48EBA05B3A5AF1802AB5787FAD5FB3BB","dtype":"imei","mac":"149d09c94466","gender":"女","brand":"华为","model":"TRT-AL00","rssi":85,"distance":null,"visittime":"2018-05-15 23:59:36","is_new":false,"isShown":null},{"probemac":"5ccf7f61dafd","probeName":null,"did":"9C1595EFCEBA7C770024690A63A40080","dtype":"imei","mac":"3cbd3e78ad39","gender":"","brand":"小米","model":"","rssi":82,"distance":null,"visittime":"2018-05-15 23:59:29","is_new":false,"isShown":null},{"probemac":"a020a61114ff","probeName":null,"did":"D64B688DA0681449065310184C68D555","dtype":"imei","mac":"b0594734ecb5","gender":"","brand":"奇虎360","model":"","rssi":95,"distance":null,"visittime":"2018-05-15 23:59:20","is_new":false,"isShown":null},{"probemac":"a020a61114ff","probeName":null,"did":"06B8F58C244A2FD5627B7568EFC9D9FD","dtype":"idfa","mac":"20ab3765e586","gender":"","brand":"苹果","model":"","rssi":99,"distance":null,"visittime":"2018-05-15 23:59:20","is_new":true,"isShown":null},{"probemac":"5ccf7f61dafd","probeName":null,"did":"48EBA05B3A5AF1802AB5787FAD5FB3BB","dtype":"imei","mac":"149d09c94466","gender":"女","brand":"华为","model":"TRT-AL00","rssi":83,"distance":null,"visittime":"2018-05-15 23:59:08","is_new":false,"isShown":null}]
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
         * probemac : a020a61114ff
         * probeName : null
         * did : 3AE72FB68BDB80BBA9B1CE64A7DCAC9D
         * dtype : imei
         * mac : 34ce0006f9a6
         * gender :
         * brand : 小米
         * model :
         * rssi : 83
         * distance : null
         * visittime : 2018-05-15 23:59:53
         * is_new : false
         * isShown : null
         */

        private String probemac;
        private String probeName;
        private String did;
        private String dtype;
        private String mac;
        private String gender;
        private String brand;
        private String model;
        private int rssi;
        private String distance;
        private String visittime;
        private boolean is_new;
        private boolean isShown;

        public String getProbemac() {
            return probemac;
        }

        public void setProbemac(String probemac) {
            this.probemac = probemac;
        }

        public String getProbeName() {
            return probeName;
        }

        public void setProbeName(String probeName) {
            this.probeName = probeName;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getDtype() {
            return dtype;
        }

        public void setDtype(String dtype) {
            this.dtype = dtype;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getRssi() {
            return rssi;
        }

        public void setRssi(int rssi) {
            this.rssi = rssi;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getVisittime() {
            return visittime;
        }

        public void setVisittime(String visittime) {
            this.visittime = visittime;
        }

        public boolean isIs_new() {
            return is_new;
        }

        public void setIs_new(boolean is_new) {
            this.is_new = is_new;
        }

        public boolean getIsShown() {
            return isShown;
        }

        public void setIsShown(boolean isShown) {
            this.isShown = isShown;
        }
    }
}
