package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class TransactionDetails {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"data":[{"fee":1,"settleType":"充值","settleDate":"2015-07-10"},{"fee":3000,"settleType":"充值","settleDate":"2016-09-23"},{"fee":59.09,"settleType":"PC结算","settleDate":"2017-05-05"},{"fee":4.16,"settleType":"移动结算","settleDate":"2017-05-05"},{"fee":42.62,"settleType":"PC结算","settleDate":"2017-05-06"},{"fee":2.67,"settleType":"移动结算","settleDate":"2017-05-06"},{"fee":95.44,"settleType":"PC结算","settleDate":"2017-04-19"},{"fee":28.65,"settleType":"移动结算","settleDate":"2017-04-19"},{"fee":37.78,"settleType":"PC结算","settleDate":"2017-05-07"},{"fee":2.35,"settleType":"移动结算","settleDate":"2017-05-07"}],"total":402}
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
         * data : [{"fee":1,"settleType":"充值","settleDate":"2015-07-10"},{"fee":3000,"settleType":"充值","settleDate":"2016-09-23"},{"fee":59.09,"settleType":"PC结算","settleDate":"2017-05-05"},{"fee":4.16,"settleType":"移动结算","settleDate":"2017-05-05"},{"fee":42.62,"settleType":"PC结算","settleDate":"2017-05-06"},{"fee":2.67,"settleType":"移动结算","settleDate":"2017-05-06"},{"fee":95.44,"settleType":"PC结算","settleDate":"2017-04-19"},{"fee":28.65,"settleType":"移动结算","settleDate":"2017-04-19"},{"fee":37.78,"settleType":"PC结算","settleDate":"2017-05-07"},{"fee":2.35,"settleType":"移动结算","settleDate":"2017-05-07"}]
         * total : 402
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
             * fee : 1.0
             * settleType : 充值
             * settleDate : 2015-07-10
             */

            private String fee;
            private String settleType;
            private String settleDate;

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getSettleType() {
                return settleType;
            }

            public void setSettleType(String settleType) {
                this.settleType = settleType;
            }

            public String getSettleDate() {
                return settleDate;
            }

            public void setSettleDate(String settleDate) {
                this.settleDate = settleDate;
            }
        }
    }
}
