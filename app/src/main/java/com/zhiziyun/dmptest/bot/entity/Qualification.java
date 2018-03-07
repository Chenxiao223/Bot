package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 * 资质实体类
 */

public class Qualification {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"smsQualificationStatus":"审核失败","smsQualificationFailReason":"测试失败","qualifications":[{"qualificationName":"法人代表身份证照片","qualificationUrl":"http://images.test.zhiziyun.com/creative/wpG5F0VUYNz_qualification.jpg"},{"qualificationName":"ICP备案","qualificationUrl":"http://images.test.zhiziyun.com/creative/woa2r0YRyP6_qualification.jpg"},{"qualificationName":"营业执照","qualificationUrl":"http://images2.zhiziyun.com/creative/9WR5v0R1Jza_qualificationContent9WR5v0R1Jza.jpg"}]}
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
         * smsQualificationStatus : 审核失败
         * smsQualificationFailReason : 测试失败
         * qualifications : [{"qualificationName":"法人代表身份证照片","qualificationUrl":"http://images.test.zhiziyun.com/creative/wpG5F0VUYNz_qualification.jpg"},{"qualificationName":"ICP备案","qualificationUrl":"http://images.test.zhiziyun.com/creative/woa2r0YRyP6_qualification.jpg"},{"qualificationName":"营业执照","qualificationUrl":"http://images2.zhiziyun.com/creative/9WR5v0R1Jza_qualificationContent9WR5v0R1Jza.jpg"}]
         */

        private String smsQualificationStatus;
        private String smsQualificationFailReason;
        private List<QualificationsBean> qualifications;

        public String getSmsQualificationStatus() {
            return smsQualificationStatus;
        }

        public void setSmsQualificationStatus(String smsQualificationStatus) {
            this.smsQualificationStatus = smsQualificationStatus;
        }

        public String getSmsQualificationFailReason() {
            return smsQualificationFailReason;
        }

        public void setSmsQualificationFailReason(String smsQualificationFailReason) {
            this.smsQualificationFailReason = smsQualificationFailReason;
        }

        public List<QualificationsBean> getQualifications() {
            return qualifications;
        }

        public void setQualifications(List<QualificationsBean> qualifications) {
            this.qualifications = qualifications;
        }

        public static class QualificationsBean {
            /**
             * qualificationName : 法人代表身份证照片
             * qualificationUrl : http://images.test.zhiziyun.com/creative/wpG5F0VUYNz_qualification.jpg
             */

            private String qualificationName;
            private String qualificationUrl;

            public String getQualificationName() {
                return qualificationName;
            }

            public void setQualificationName(String qualificationName) {
                this.qualificationName = qualificationName;
            }

            public String getQualificationUrl() {
                return qualificationUrl;
            }

            public void setQualificationUrl(String qualificationUrl) {
                this.qualificationUrl = qualificationUrl;
            }
        }
    }
}
