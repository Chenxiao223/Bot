package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/27.
 */

public class QueryStatus {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"qualificationStatus":"未提交","qualificationFailReason":"该通话资质未提交","qualifications":[{"qualificationName":"语音服务开通承诺函","qualificationUrl":"http://images.test.zhiziyun.com/creative/BgsH40nS2ZP_qualification.jpg"}]}
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
         * qualificationStatus : 未提交
         * qualificationFailReason : 该通话资质未提交
         * qualifications : [{"qualificationName":"语音服务开通承诺函","qualificationUrl":"http://images.test.zhiziyun.com/creative/BgsH40nS2ZP_qualification.jpg"}]
         */

        private String qualificationStatus;
        private String qualificationFailReason;
        private List<QualificationsBean> qualifications;

        public String getQualificationStatus() {
            return qualificationStatus;
        }

        public void setQualificationStatus(String qualificationStatus) {
            this.qualificationStatus = qualificationStatus;
        }

        public String getQualificationFailReason() {
            return qualificationFailReason;
        }

        public void setQualificationFailReason(String qualificationFailReason) {
            this.qualificationFailReason = qualificationFailReason;
        }

        public List<QualificationsBean> getQualifications() {
            return qualifications;
        }

        public void setQualifications(List<QualificationsBean> qualifications) {
            this.qualifications = qualifications;
        }

        public static class QualificationsBean {
            /**
             * qualificationName : 语音服务开通承诺函
             * qualificationUrl : http://images.test.zhiziyun.com/creative/BgsH40nS2ZP_qualification.jpg
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
