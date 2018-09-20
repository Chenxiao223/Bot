package com.zhiziyun.dmptest.bot.mode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhiziyun on 2018/9/6.
 */

public class BTestdata implements Serializable {
    private ArrayList<detailbean> mDetailbeen;
    private String mString;

    public ArrayList<detailbean> getDetailbeen() {
        return mDetailbeen;
    }

    public void setDetailbeen(ArrayList<detailbean> detailbeen) {
        mDetailbeen = detailbeen;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public static class detailbean implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
