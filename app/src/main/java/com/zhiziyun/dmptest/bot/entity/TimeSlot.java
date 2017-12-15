package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 * 时段实体类
 */

public class TimeSlot {

    /**
     * obj : [{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":20,"uv":4,"hour":0,"totalPV":465,"totalUV":125},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":25,"uv":4,"hour":1,"totalPV":410,"totalUV":108},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":23,"uv":5,"hour":2,"totalPV":425,"totalUV":97},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":23,"uv":5,"hour":3,"totalPV":391,"totalUV":92},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":24,"uv":4,"hour":4,"totalPV":396,"totalUV":83},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":23,"uv":4,"hour":5,"totalPV":404,"totalUV":103},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":24,"uv":4,"hour":6,"totalPV":468,"totalUV":146},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":24,"uv":5,"hour":7,"totalPV":714,"totalUV":355},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":54,"uv":23,"hour":8,"totalPV":1404,"totalUV":640},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":138,"uv":62,"hour":9,"totalPV":2526,"totalUV":708},{"siteId":"0zoTLi29XRgq","statDate":"2017-12-11","pv":112,"uv":40,"hour":10,"totalPV":2640,"totalUV":775}]
     * success : true
     * msg : 查询成功
     */

    private boolean success;
    private String msg;
    private List<ObjBean> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * siteId : 0zoTLi29XRgq
         * statDate : 2017-12-11
         * pv : 20
         * uv : 4
         * hour : 0
         * totalPV : 465
         * totalUV : 125
         */

        private String siteId;
        private String statDate;
        private String pv;
        private String uv;
        private String hour;
        private String totalPV;
        private String totalUV;

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getStatDate() {
            return statDate;
        }

        public void setStatDate(String statDate) {
            this.statDate = statDate;
        }

        public String getPv() {
            return pv;
        }

        public void setPv(String pv) {
            this.pv = pv;
        }

        public String getUv() {
            return uv;
        }

        public void setUv(String uv) {
            this.uv = uv;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getTotalPV() {
            return totalPV;
        }

        public void setTotalPV(String totalPV) {
            this.totalPV = totalPV;
        }

        public String getTotalUV() {
            return totalUV;
        }

        public void setTotalUV(String totalUV) {
            this.totalUV = totalUV;
        }
    }
}
