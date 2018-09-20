package com.zhiziyun.dmptest.bot.mode.acount.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/9/17.
 */

public class BFinancialResult implements Serializable {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"data":[{"fee":10.01,"settleType":"充值"},{"fee":10.01,"settleType":"退款"},{"fee":200,"settleType":"席位结算"}],"total":3}
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

    public static class ResponseBean implements Serializable {
        /**
         * data : [{"fee":10.01,"settleType":"充值"},{"fee":10.01,"settleType":"退款"},{"fee":200,"settleType":"席位结算"}]
         * total : 3
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

        public static class DataBean implements Serializable {
            /**
             * fee : 10.01
             * settleType : 充值
             */

            private double fee;
            private String settleType;

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public String getSettleType() {
                return settleType;
            }

            public void setSettleType(String settleType) {
                this.settleType = settleType;
            }
        }
    }
}

