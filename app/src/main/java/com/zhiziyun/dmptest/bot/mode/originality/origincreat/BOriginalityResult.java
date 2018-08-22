package com.zhiziyun.dmptest.bot.mode.originality.origincreat;

import java.io.Serializable;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public class BOriginalityResult implements Serializable {

        private boolean status;
        private String errorcode;
        private String errormsg;
        private OriginalityBean response;
        public void setStatus(boolean status) {
            this.status = status;
        }
        public boolean getStatus() {
            return status;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }
        public String getErrorcode() {
            return errorcode;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }
        public String getErrormsg() {
            return errormsg;
        }

        public void setResponse(OriginalityBean response) {
            this.response = response;
        }
        public OriginalityBean getResponse() {
            return response;
        }


}
