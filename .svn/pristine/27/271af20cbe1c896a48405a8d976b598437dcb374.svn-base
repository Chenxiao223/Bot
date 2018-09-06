package com.zhiziyun.dmptest.bot.mode.wifi.store;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/8/30.
 */

public class BStoreResult implements Serializable {


    /**
     * total : 1
     * rows : [{"shop_id":17193887,"shop_name":"美陈广场","ssid":"ZZY","ssid_list":["ZZY","ZZY-MeetingRoom","ZZY-5G"],"protocol_type":31,"sid":"","poi_id":"489192207"}]
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
         * shop_id : 17193887
         * shop_name : 美陈广场
         * ssid : ZZY
         * ssid_list : ["ZZY","ZZY-MeetingRoom","ZZY-5G"]
         * protocol_type : 31
         * sid :
         * poi_id : 489192207
         */

        private String shop_id;
        private String shop_name;
        private String ssid;
        private int protocol_type;
        private String sid;
        private String poi_id;
        private List<String> ssid_list;
        private boolean checked;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public int getProtocol_type() {
            return protocol_type;
        }

        public void setProtocol_type(int protocol_type) {
            this.protocol_type = protocol_type;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getPoi_id() {
            return poi_id;
        }

        public void setPoi_id(String poi_id) {
            this.poi_id = poi_id;
        }

        public List<String> getSsid_list() {
            return ssid_list;
        }

        public void setSsid_list(List<String> ssid_list) {
            this.ssid_list = ssid_list;
        }
    }
}
