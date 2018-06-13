package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class AdCrowd {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":43,"data":[{"customMobileGroupId":"z5ow00DHXbi","customMobileGroupName":"iOS game crashes"},{"customMobileGroupId":"z5lre0iYq4w","customMobileGroupName":"加油"},{"customMobileGroupId":"z5imx0rakRW","customMobileGroupName":"iOS到店1"},{"customMobileGroupId":"z51gR0gT6qk","customMobileGroupName":"iOS到店测"},{"customMobileGroupId":"yPNB608PaH6","customMobileGroupName":"短信测试0518"},{"customMobileGroupId":"yOxcS0exaLu","customMobileGroupName":"短信测试"},{"customMobileGroupId":"yOiE40D2ZCU","customMobileGroupName":"fsafsafsafsdfsdaf"},{"customMobileGroupId":"yMLRm0UUUww","customMobileGroupName":"sfdsa"},{"customMobileGroupId":"yMLCW0gFySI","customMobileGroupName":"fdsafsd"},{"customMobileGroupId":"yMKcU0GXiH6","customMobileGroupName":"fdsafsdfsadfd"}]}
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
         * total : 43
         * data : [{"customMobileGroupId":"z5ow00DHXbi","customMobileGroupName":"iOS game crashes"},{"customMobileGroupId":"z5lre0iYq4w","customMobileGroupName":"加油"},{"customMobileGroupId":"z5imx0rakRW","customMobileGroupName":"iOS到店1"},{"customMobileGroupId":"z51gR0gT6qk","customMobileGroupName":"iOS到店测"},{"customMobileGroupId":"yPNB608PaH6","customMobileGroupName":"短信测试0518"},{"customMobileGroupId":"yOxcS0exaLu","customMobileGroupName":"短信测试"},{"customMobileGroupId":"yOiE40D2ZCU","customMobileGroupName":"fsafsafsafsdfsdaf"},{"customMobileGroupId":"yMLRm0UUUww","customMobileGroupName":"sfdsa"},{"customMobileGroupId":"yMLCW0gFySI","customMobileGroupName":"fdsafsd"},{"customMobileGroupId":"yMKcU0GXiH6","customMobileGroupName":"fdsafsdfsadfd"}]
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
             * customMobileGroupId : z5ow00DHXbi
             * customMobileGroupName : iOS game crashes
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
