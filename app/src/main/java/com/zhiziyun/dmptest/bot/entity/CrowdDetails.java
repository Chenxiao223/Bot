package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 * 客户详情实体类
 */

public class CrowdDetails {


    /**
     * obj : {"entityId":"ytpRm0t9nvq","createTime":"2018-05-03 16:38:56","updateTime":null,"segmentId":1241,"budget":500,"name":"贵州大数据-客户转化","status":1,"macSet":[],"count":241,"siteId":"0zoTLi29XRgq","agentId":1,"type":1,"dupBySite":0,"dupByAgent":0,"deductFee":null,"deductDspFee":null,"lastSyncTime":"2018-05-10 16:43:36","customMobileGroupId":null,"unitPrice":0.5,"effectiveDevCount":20021,"completedDevCount":660,"dupDevCount":0,"addedGuestCount":null,"expectedGuestCount":7302,"valid":false,"deleted":false}
     * success : true
     * msg : 查询成功
     */

    private ObjBean obj;
    private boolean success;
    private String msg;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ObjBean {
        /**
         * entityId : ytpRm0t9nvq
         * createTime : 2018-05-03 16:38:56
         * updateTime : null
         * segmentId : 1241
         * budget : 500
         * name : 贵州大数据-客户转化
         * status : 1
         * macSet : []
         * count : 241
         * siteId : 0zoTLi29XRgq
         * agentId : 1
         * type : 1
         * dupBySite : 0
         * dupByAgent : 0
         * deductFee : null
         * deductDspFee : null
         * lastSyncTime : 2018-05-10 16:43:36
         * customMobileGroupId : null
         * unitPrice : 0.5
         * effectiveDevCount : 20021
         * completedDevCount : 660
         * dupDevCount : 0
         * addedGuestCount : null
         * expectedGuestCount : 7302
         * valid : false
         * deleted : false
         */

        private String entityId;
        private String createTime;
        private Object updateTime;
        private int segmentId;
        private int budget;
        private String name;
        private int status;
        private int count;
        private String siteId;
        private int agentId;
        private int type;
        private int dupBySite;
        private int dupByAgent;
        private Object deductFee;
        private Object deductDspFee;
        private String lastSyncTime;
        private Object customMobileGroupId;
        private double unitPrice;
        private int effectiveDevCount;
        private int completedDevCount;
        private int dupDevCount;
        private int addedGuestCount;
        private int expectedGuestCount;
        private boolean valid;
        private boolean deleted;
        private List<?> macSet;

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getSegmentId() {
            return segmentId;
        }

        public void setSegmentId(int segmentId) {
            this.segmentId = segmentId;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDupBySite() {
            return dupBySite;
        }

        public void setDupBySite(int dupBySite) {
            this.dupBySite = dupBySite;
        }

        public int getDupByAgent() {
            return dupByAgent;
        }

        public void setDupByAgent(int dupByAgent) {
            this.dupByAgent = dupByAgent;
        }

        public Object getDeductFee() {
            return deductFee;
        }

        public void setDeductFee(Object deductFee) {
            this.deductFee = deductFee;
        }

        public Object getDeductDspFee() {
            return deductDspFee;
        }

        public void setDeductDspFee(Object deductDspFee) {
            this.deductDspFee = deductDspFee;
        }

        public String getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(String lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }

        public Object getCustomMobileGroupId() {
            return customMobileGroupId;
        }

        public void setCustomMobileGroupId(Object customMobileGroupId) {
            this.customMobileGroupId = customMobileGroupId;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getEffectiveDevCount() {
            return effectiveDevCount;
        }

        public void setEffectiveDevCount(int effectiveDevCount) {
            this.effectiveDevCount = effectiveDevCount;
        }

        public int getCompletedDevCount() {
            return completedDevCount;
        }

        public void setCompletedDevCount(int completedDevCount) {
            this.completedDevCount = completedDevCount;
        }

        public int getDupDevCount() {
            return dupDevCount;
        }

        public void setDupDevCount(int dupDevCount) {
            this.dupDevCount = dupDevCount;
        }

        public int getAddedGuestCount() {
            return addedGuestCount;
        }

        public void setAddedGuestCount(int addedGuestCount) {
            this.addedGuestCount = addedGuestCount;
        }

        public int getExpectedGuestCount() {
            return expectedGuestCount;
        }

        public void setExpectedGuestCount(int expectedGuestCount) {
            this.expectedGuestCount = expectedGuestCount;
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

        public List<?> getMacSet() {
            return macSet;
        }

        public void setMacSet(List<?> macSet) {
            this.macSet = macSet;
        }
    }
}
