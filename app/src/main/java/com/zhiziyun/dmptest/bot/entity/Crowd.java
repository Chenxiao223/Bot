package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 * 人群实体类
 */

public class Crowd {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":2,"data":[{"customMobileGroupId":"vzCzt0KlHzy","customMobileGroupName":"1705测试20180105"},{"customMobileGroupId":"vzDEO0sSb9m","customMobileGroupName":"1705测试20180104"}]}
     */

    private boolean status;
    private String errorcode;
    private String errormsg;
    private ResponseBean response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * total : 2
         * data : [{"customMobileGroupId":"vzCzt0KlHzy","customMobileGroupName":"1705测试20180105"},{"customMobileGroupId":"vzDEO0sSb9m","customMobileGroupName":"1705测试20180104"}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * customMobileGroupId : vzCzt0KlHzy
             * customMobileGroupName : 1705测试20180105
             */

            private String customMobileGroupId;
            private String customMobileGroupName;

            public String getCustomMobileGroupId() {
                return customMobileGroupId;
            }

            public void setCustomMobileGroupId(String customMobileGroupId) {
                this.customMobileGroupId = customMobileGroupId;
            }

            public String getCustomMobileGroupName() {
                return customMobileGroupName;
            }

            public void setCustomMobileGroupName(String customMobileGroupName) {
                this.customMobileGroupName = customMobileGroupName;
            }
        }
    }
}
