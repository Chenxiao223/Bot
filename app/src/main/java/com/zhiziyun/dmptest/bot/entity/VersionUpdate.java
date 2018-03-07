package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/3/5.
 */

public class VersionUpdate {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"needUpdate":false,"version":"预览版1.2","downloadUrl":"zhiziyun.com","title":"发布预览版1.2","message":"新增功能","needForcedUpdate":false}
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
         * needUpdate : false
         * version : 预览版1.2
         * downloadUrl : zhiziyun.com
         * title : 发布预览版1.2
         * message : 新增功能
         * needForcedUpdate : false
         */

        private boolean needUpdate;
        private String version;
        private String downloadUrl;
        private String title;
        private String message;
        private boolean needForcedUpdate;

        public boolean isNeedUpdate() {
            return needUpdate;
        }

        public void setNeedUpdate(boolean needUpdate) {
            this.needUpdate = needUpdate;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isNeedForcedUpdate() {
            return needForcedUpdate;
        }

        public void setNeedForcedUpdate(boolean needForcedUpdate) {
            this.needForcedUpdate = needForcedUpdate;
        }
    }
}
