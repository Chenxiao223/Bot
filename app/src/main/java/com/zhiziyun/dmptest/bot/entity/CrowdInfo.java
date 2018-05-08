package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class CrowdInfo {

    /**
     * total : 200
     * rows : [{"id":"bfb2756d10ffb039c93c16f07274fe68","encryptPhone":"fedf43d5dd8d37bbb38e1c5baf9d8a1a","tailPhone":"0579","mark":2,"type":0,"name":"Er","siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 18:00:54","smsCount":0,"hasDial":true,"phoneNumber":18562800579},{"id":"d0f5d85a7fdc65e9c77fd61e64424596","encryptPhone":"087b6a8bee4b15acf37d7de8dfa9f51c","tailPhone":"8578","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 17:57:53","smsCount":0,"hasDial":null,"phoneNumber":15762278578},{"id":"11a7fd4002802e24f81b15a7ce9a08c1","encryptPhone":"c98ea0b18156b516be68052af21a3cb7","tailPhone":"3152","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":18816533152},{"id":"139b61de565dbfebd8389b519e260767","encryptPhone":"4ad040aff85bf5ce33e8131bfb5cedaf","tailPhone":"6135","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13061736135},{"id":"195702f3be40caa1462e93fedd9f97f7","encryptPhone":"1dd3408c28ecb38394dcb2f9cfb1d446","tailPhone":"3587","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13761563587},{"id":"210d22ed0fb10d4d7ac9641794c288d0","encryptPhone":"1eb1c8aed69e001ce955aa67a88cc87c","tailPhone":"6988","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13671966988},{"id":"231c9732d87af8b1a00a8b018b93e4eb","encryptPhone":"dcde9a1abd375a0973a073d064edffc3","tailPhone":"9773","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13524079773},{"id":"297879c7854734d873cf5fe2db582c89","encryptPhone":"d24cfd144c7b5374a96653086ce1b691","tailPhone":"4565","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13801864565},{"id":"37246b39f6934dc78a65e92067d9261b","encryptPhone":"0e466c5e147df6a3d40f8f048991e836","tailPhone":"0663","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":15021550663},{"id":"47198df09b35c5931f16785a50625692","encryptPhone":"8b6954d061e036e1c40adea3ec42bfba","tailPhone":"3540","mark":0,"type":0,"name":null,"siteId":"0zoTLi29XRgq","desc":null,"charger":null,"createTime":"2018-04-24 12:00:32","smsCount":0,"hasDial":null,"phoneNumber":13621623540}]
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
         * id : bfb2756d10ffb039c93c16f07274fe68
         * encryptPhone : fedf43d5dd8d37bbb38e1c5baf9d8a1a
         * tailPhone : 0579
         * mark : 2
         * type : 0
         * name : Er
         * siteId : 0zoTLi29XRgq
         * desc : null
         * charger : null
         * createTime : 2018-04-24 18:00:54
         * smsCount : 0
         * hasDial : true
         * phoneNumber : 18562800579
         */

        private String id;
        private String encryptPhone;
        private String tailPhone;
        private int mark;
        private int type;
        private String name;
        private String siteId;
        private String desc;
        private String charger;
        private String createTime;
        private int smsCount;
        private boolean hasDial;
        private long phoneNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEncryptPhone() {
            return encryptPhone;
        }

        public void setEncryptPhone(String encryptPhone) {
            this.encryptPhone = encryptPhone;
        }

        public String getTailPhone() {
            return tailPhone;
        }

        public void setTailPhone(String tailPhone) {
            this.tailPhone = tailPhone;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCharger() {
            return charger;
        }

        public void setCharger(String charger) {
            this.charger = charger;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSmsCount() {
            return smsCount;
        }

        public void setSmsCount(int smsCount) {
            this.smsCount = smsCount;
        }

        public boolean isHasDial() {
            return hasDial;
        }

        public void setHasDial(boolean hasDial) {
            this.hasDial = hasDial;
        }

        public long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
