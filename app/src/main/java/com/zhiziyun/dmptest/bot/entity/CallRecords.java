package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 * 通话记录实体类
 */

public class CallRecords {

    /**
     * total : 0
     * rows : [{"transId":"xXiSb0ab8CA","transType":2,"fee":0.1,"transTime":"2018-04-12 13:47:16","note":null,"userId":0,"dspAccountId":100092,"comment":"callTimePrice:0.10,count:1","settleType":6,"settleDate":"2018-04-12"}]
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
         * transId : xXiSb0ab8CA
         * transType : 2
         * fee : 0.1
         * transTime : 2018-04-12 13:47:16
         * note : null
         * userId : 0
         * dspAccountId : 100092
         * comment : callTimePrice:0.10,count:1
         * settleType : 6
         * settleDate : 2018-04-12
         */

        private String transId;
        private int transType;
        private double fee;
        private String transTime;
        private String note;
        private int userId;
        private int dspAccountId;
        private String comment;
        private int settleType;
        private String settleDate;

        public String getTransId() {
            return transId;
        }

        public void setTransId(String transId) {
            this.transId = transId;
        }

        public int getTransType() {
            return transType;
        }

        public void setTransType(int transType) {
            this.transType = transType;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getTransTime() {
            return transTime;
        }

        public void setTransTime(String transTime) {
            this.transTime = transTime;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getDspAccountId() {
            return dspAccountId;
        }

        public void setDspAccountId(int dspAccountId) {
            this.dspAccountId = dspAccountId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getSettleType() {
            return settleType;
        }

        public void setSettleType(int settleType) {
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
