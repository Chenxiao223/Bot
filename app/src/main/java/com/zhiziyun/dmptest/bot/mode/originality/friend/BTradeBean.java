package com.zhiziyun.dmptest.bot.mode.originality.friend;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/7/31.
 */

public class BTradeBean implements Serializable {

    /**
     * Auto-generated: 2018-07-31 14:47:23
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */


    private List<TradeDetail> data;
    private int total;

    public void setData(List<TradeDetail> data) {
        this.data = data;
    }

    public List<TradeDetail> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }


}
