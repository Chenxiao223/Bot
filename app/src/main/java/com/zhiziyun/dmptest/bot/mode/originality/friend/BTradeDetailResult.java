package com.zhiziyun.dmptest.bot.mode.originality.friend;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/7/31.
 */

public class BTradeDetailResult implements Serializable {

    private boolean status;
    private String errorcode;
    private String errormsg;
    private BTradeBean response;

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

    public BTradeBean getResponse() {
        return response;
    }

    public void setResponse(BTradeBean response) {
        this.response = response;
    }

}
