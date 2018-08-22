package com.zhiziyun.dmptest.bot.mode.originality.friend;

import java.io.Serializable;

/**
 * Created by zhiziyun on 2018/7/31.
 */

public class TradeDetail implements Serializable {
    /**
     * /**
     * Auto-generated: 2018-07-31 14:47:23
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

    private String fee;
    private String settleType;
    private String settleDate;

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleDate() {
        return settleDate;
    }


}
