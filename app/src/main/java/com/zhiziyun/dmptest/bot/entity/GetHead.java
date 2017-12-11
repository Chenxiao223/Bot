package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2017/12/11.
 */

public class GetHead {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"logoUrl":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq_logo_1511418277858.jpg"}
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
         * logoUrl : http://images.test.zhiziyun.com/creative/0zoTLi29XRgq_logo_1511418277858.jpg
         */

        private String logoUrl;

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }
}
