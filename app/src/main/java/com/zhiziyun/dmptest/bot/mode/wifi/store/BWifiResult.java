package com.zhiziyun.dmptest.bot.mode.wifi.store;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/8/30.
 */

public class BWifiResult implements Serializable {

    /**
     * total : 17
     * rows : [{"id":405,"name":"828salon","siteId":"0zoTLi29XRgq","mac":"487d2ec8e42f,f8da0c29c877,9cd21e875adb,08ea40f78737,30b49ea197db,903c923c918a,7085c22aa621,64273770fc5a,4c348849291f,785860785034,3ca61610015a,48a19543aee8,18f1d8f39726,9c0cdf05cd2f,9800c6136336,7014a6eafcf4,a01828dc8312,1c77f6329512,9487e0300a64,946372c86ef3,28c63fd5d0c7,18f1d8f14a65,3c2ef917e1d3,b8c1118240d2,e49adc9c9803,","createTime":"2018-08-28 15:50:21","updateTime":"2018-08-28 15:50:21","adCustomMobileGroupId":"BlgH50Ibo40","count":25,"hasTransForPhone":null,"taskStatus":null},{"id":404,"name":"zzymeetingroom828","siteId":"0zoTLi29XRgq","mac":"487d2ec8e42f,f8da0c29c877,9cd21e875adb,08ea40f78737,30b49ea197db,903c923c918a,7085c22aa621,64273770fc5a,4c348849291f,785860785034,48a19543aee8,18f1d8f39726,9c0cdf05cd2f,9800c6136336,7014a6eafcf4,a01828dc8312,1c77f6329512,946372c86ef3,28c63fd5d0c7,18f1d8f14a65,3c2ef917e1d3,","createTime":"2018-08-28 15:43:34","updateTime":"2018-08-28 15:43:34","adCustomMobileGroupId":"BlgfA09b320","count":21,"hasTransForPhone":null,"taskStatus":null},{"id":403,"name":"ZZY-MeetingRoom828","siteId":"0zoTLi29XRgq","mac":"487d2ec8e42f,f8da0c29c877,9cd21e875adb,08ea40f78737,30b49ea197db,7085c22aa621,64273770fc5a,785860785034,48a19543aee8,9c0cdf05cd2f,a01828dc8312,1c77f6329512,28c63fd5d0c7,18f1d8f14a65,903c923c918a,7014a6eafcf4,946372c86ef3,3c2ef917e1d3,","createTime":"2018-08-28 15:01:19","updateTime":"2018-08-28 15:01:19","adCustomMobileGroupId":"Bldu40Gr6Vi","count":18,"hasTransForPhone":null,"taskStatus":null},{"id":400,"name":"ZZY828","siteId":"0zoTLi29XRgq","mac":"30b49ec76cb7,584822a73420,04d6aa23a3fd,acd1b8777fbd,5c1dd96bc9db,308841450af0,e49adc991577,946372c86ef1,08e689817054,fc64ba54b348,946372c387fb,e4029ba90d03,886ab16b431c,acd1b87792a9,0c84dc6f72a9,785860785035,e4029b5dbcc7,6cab31d0e01e,6ce873bf8867,e4029b4c5b5d,30b49eac8c5b,30b49ec4e66b,b49cdfd994b9,0857000e9124,2c337a856d01,30b49e84983b,30f7726355b7,94bf2d81c14f,c49ded947978,7802f82ef5e9,9487e01b09f7,3088416715f6,441ca82838c9,ecd09fbc0a8b,8c85904d3094,30b49e54b6e6,68ef43ebc75f,30b49eac758a,8c25054e04f5,e09467ef1ca3,e4029ba627a5,3c2ef917e1d3,9453303cbd7d,308841c2abad,84dbaca99535,d0a637ea4af5,b0481a66754e,4c49e3df2c6f,34de1aa515b3,bc4cc4db3e8d,14c6976b04f1,a4d18cc93e4a,7862567ee30c,54137973432d,14bd61dcc9b2,30b49e73963d,183da2f7bcdc,84ef18d8db0a,5800e34b8e1d,00db7049087f,e4029b58dbad,708a09ed99ac,c8ff286095b5,68dfdda6b738,5800e34ead15,780cb842b8a0,2c6e854d9ed2,30b49e9a9592,308841bd3536,e4029b4e2ca8,7c6b9c653563,c4f0818ffd81,5cadcf521578,30b49ec4de2b,00c2c6f2cb23,30b49ebb2578,780cb82b8e05,30b49ec76962,30b49e74c3e5,b40b44ebd432,2c337a605a69,347c25729fef,3408bc596127,c8ff285c86f7,3088419f876e,e4029b4e14d4,48a195bcd4eb,4c74bf122934,2c337a5f9b8b,308841c2b0a3,a4db30270b33,e4029b5afae1,449ef95beb11,d0534957f7e3,cc2db72bdb64,482ca0643ffe,acc1ee3091dd,40b83713481b,dcefca7dc805,0857003a0ced,b8634d091cf3,bc4cc4f309d6,30b49ec4de2a,c8ff285c875d,9c0cdf05d4ef,a45e60c48d7b,5800e34bad41,90489af42efb,f470ab1798fe,640980c51833,30b49ec72b9d,707781a2e1c1,9ce33f7de858,8828b32c0d71,","createTime":"2018-08-28 10:02:53","updateTime":"2018-08-28 10:02:53","adCustomMobileGroupId":"BkTWI0gAZJ6","count":114,"hasTransForPhone":null,"taskStatus":null}]
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

    public static class RowsBean implements Serializable {
        /**
         * id : 405
         * name : 828salon
         * siteId : 0zoTLi29XRgq
         * mac : 487d2ec8e42f,f8da0c29c877,9cd21e875adb,08ea40f78737,30b49ea197db,903c923c918a,7085c22aa621,64273770fc5a,4c348849291f,785860785034,3ca61610015a,48a19543aee8,18f1d8f39726,9c0cdf05cd2f,9800c6136336,7014a6eafcf4,a01828dc8312,1c77f6329512,9487e0300a64,946372c86ef3,28c63fd5d0c7,18f1d8f14a65,3c2ef917e1d3,b8c1118240d2,e49adc9c9803,
         * createTime : 2018-08-28 15:50:21
         * updateTime : 2018-08-28 15:50:21
         * adCustomMobileGroupId : BlgH50Ibo40
         * count : 25
         * hasTransForPhone : null
         * taskStatus : null
         */

        private String id;
        private String name;
        private String siteId;
        private String mac;
        private String createTime;
        private String updateTime;
        private String adCustomMobileGroupId;
        private int count;
        private Object hasTransForPhone;
        private Object taskStatus;
        private boolean checked;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
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

        public String getAdCustomMobileGroupId() {
            return adCustomMobileGroupId;
        }

        public void setAdCustomMobileGroupId(String adCustomMobileGroupId) {
            this.adCustomMobileGroupId = adCustomMobileGroupId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getHasTransForPhone() {
            return hasTransForPhone;
        }

        public void setHasTransForPhone(Object hasTransForPhone) {
            this.hasTransForPhone = hasTransForPhone;
        }

        public Object getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(Object taskStatus) {
            this.taskStatus = taskStatus;
        }
    }
}
