package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class CallRecordC {

    /**
     * total : 1
     * rows : [{"entityId":"A2168X10X0302241636-11-0-FLCS-GXI","createTime":null,"updateTime":"2018-04-12 13:47:14","phoneA":15201870052,"phoneB":9714,"phoneX":13241690052,"binded":false,"bindTime":1523504156076,"unbindTime":1523504166478,"siteId":"0zoTLi29XRgq","deductFee":0.1,"deductDspFee":0.1,"dialTime":"2018-04-12 11:35:58","answerTime":"2018-04-12 11:36:06","handupTime":"2018-04-12 11:36:41","guestId":"0402b9dd80d807af97f32cb8b41225e9","calltime":null,"valid":false,"deleted":false}]
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
         * entityId : A2168X10X0302241636-11-0-FLCS-GXI
         * createTime : null
         * updateTime : 2018-04-12 13:47:14
         * phoneA : 15201870052
         * phoneB : 9714
         * phoneX : 13241690052
         * binded : false
         * bindTime : 1523504156076
         * unbindTime : 1523504166478
         * siteId : 0zoTLi29XRgq
         * deductFee : 0.1
         * deductDspFee : 0.1
         * dialTime : 2018-04-12 11:35:58
         * answerTime : 2018-04-12 11:36:06
         * handupTime : 2018-04-12 11:36:41
         * guestId : 0402b9dd80d807af97f32cb8b41225e9
         * calltime : null
         * valid : false
         * deleted : false
         */

        private String entityId;
        private Object createTime;
        private String updateTime;
        private long phoneA;
        private int phoneB;
        private long phoneX;
        private boolean binded;
        private long bindTime;
        private long unbindTime;
        private String siteId;
        private double deductFee;
        private double deductDspFee;
        private String dialTime;
        private String answerTime;
        private String handupTime;
        private String guestId;
        private String calltime;
        private boolean valid;
        private boolean deleted;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public long getPhoneA() {
            return phoneA;
        }

        public void setPhoneA(long phoneA) {
            this.phoneA = phoneA;
        }

        public int getPhoneB() {
            return phoneB;
        }

        public void setPhoneB(int phoneB) {
            this.phoneB = phoneB;
        }

        public long getPhoneX() {
            return phoneX;
        }

        public void setPhoneX(long phoneX) {
            this.phoneX = phoneX;
        }

        public boolean isBinded() {
            return binded;
        }

        public void setBinded(boolean binded) {
            this.binded = binded;
        }

        public long getBindTime() {
            return bindTime;
        }

        public void setBindTime(long bindTime) {
            this.bindTime = bindTime;
        }

        public long getUnbindTime() {
            return unbindTime;
        }

        public void setUnbindTime(long unbindTime) {
            this.unbindTime = unbindTime;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public double getDeductFee() {
            return deductFee;
        }

        public void setDeductFee(double deductFee) {
            this.deductFee = deductFee;
        }

        public double getDeductDspFee() {
            return deductDspFee;
        }

        public void setDeductDspFee(double deductDspFee) {
            this.deductDspFee = deductDspFee;
        }

        public String getDialTime() {
            return dialTime;
        }

        public void setDialTime(String dialTime) {
            this.dialTime = dialTime;
        }

        public String getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public String getHandupTime() {
            return handupTime;
        }

        public void setHandupTime(String handupTime) {
            this.handupTime = handupTime;
        }

        public String getGuestId() {
            return guestId;
        }

        public void setGuestId(String guestId) {
            this.guestId = guestId;
        }

        public String getCalltime() {
            return calltime;
        }

        public void setCalltime(String calltime) {
            this.calltime = calltime;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }
    }
}
