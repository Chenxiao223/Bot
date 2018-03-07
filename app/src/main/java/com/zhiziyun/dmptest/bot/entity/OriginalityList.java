package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 * 创意列表实体类
 */

public class OriginalityList {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":21,"data":[{"creativeId":"oGg2I0YL9p6","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_oGg2I0YL9p6_1490839643025.jpg","spend":"0.00","cpc":"0.90","cpm":"12.54","exposure":144,"click":2,"clickRate":"1.39"},{"creativeId":"omyWc0QADQI","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_omyWc0QADQI_1489717773676.jpg","spend":"0.00","cpc":"0.86","cpm":"11.31","exposure":152,"click":2,"clickRate":"1.32"},{"creativeId":"omzXV0t5lHq","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_omzXV0t5lHq_1492495194749.jpg","spend":"0.00","cpc":"0.00","cpm":"12.34","exposure":149,"click":0,"clickRate":"0.00"},{"creativeId":"ot62D06edCE","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_ot62D06edCE_1490089050827.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNBYA0nsk6I","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNBYA0nsk6I_1498300660063.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNCMR0Q42wo","creativeUrl":"https://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNCMR0Q42wo_1498301397547.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNDi40mZ53W","creativeUrl":"https://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNDi40mZ53W_1498301862386.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qQenK0VgaCk","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQenK0VgaCk_1498449499395.jpg","spend":"0.00","cpc":"0.67","cpm":"11.20","exposure":60,"click":1,"clickRate":"1.67"},{"creativeId":"qQeo011g4FG","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQeo011g4FG_1498449499595.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qQeo10QoX0k","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQeo10QoX0k_1498449499861.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"}]}
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
         * total : 21
         * data : [{"creativeId":"oGg2I0YL9p6","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_oGg2I0YL9p6_1490839643025.jpg","spend":"0.00","cpc":"0.90","cpm":"12.54","exposure":144,"click":2,"clickRate":"1.39"},{"creativeId":"omyWc0QADQI","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_omyWc0QADQI_1489717773676.jpg","spend":"0.00","cpc":"0.86","cpm":"11.31","exposure":152,"click":2,"clickRate":"1.32"},{"creativeId":"omzXV0t5lHq","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_omzXV0t5lHq_1492495194749.jpg","spend":"0.00","cpc":"0.00","cpm":"12.34","exposure":149,"click":0,"clickRate":"0.00"},{"creativeId":"ot62D06edCE","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_ot62D06edCE_1490089050827.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNBYA0nsk6I","creativeUrl":"http://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNBYA0nsk6I_1498300660063.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNCMR0Q42wo","creativeUrl":"https://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNCMR0Q42wo_1498301397547.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qNDi40mZ53W","creativeUrl":"https://images2.zhiziyun.com/creative/mobile/0zoTLi29XRgq_qNDi40mZ53W_1498301862386.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qQenK0VgaCk","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQenK0VgaCk_1498449499395.jpg","spend":"0.00","cpc":"0.67","cpm":"11.20","exposure":60,"click":1,"clickRate":"1.67"},{"creativeId":"qQeo011g4FG","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQeo011g4FG_1498449499595.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"},{"creativeId":"qQeo10QoX0k","creativeUrl":"http://images2.zhiziyun.com/creative/0zoTLi29XRgq_qQeo10QoX0k_1498449499861.jpg","spend":"0.00","cpc":"0.00","cpm":"0.00","exposure":0,"click":0,"clickRate":"0.00"}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * creativeId : oGg2I0YL9p6
             * creativeUrl : http://images2.zhiziyun.com/creative/0zoTLi29XRgq_oGg2I0YL9p6_1490839643025.jpg
             * spend : 0.00
             * cpc : 0.90
             * cpm : 12.54
             * exposure : 144
             * click : 2
             * clickRate : 1.39
             */

            private String creativeId;
            private String creativeUrl;
            private String spend;
            private String cpc;
            private String cpm;
            private String exposure;
            private String click;
            private String clickRate;

            public String getCreativeId() {
                return creativeId;
            }

            public void setCreativeId(String creativeId) {
                this.creativeId = creativeId;
            }

            public String getCreativeUrl() {
                return creativeUrl;
            }

            public void setCreativeUrl(String creativeUrl) {
                this.creativeUrl = creativeUrl;
            }

            public String getSpend() {
                return spend;
            }

            public void setSpend(String spend) {
                this.spend = spend;
            }

            public String getCpc() {
                return cpc;
            }

            public void setCpc(String cpc) {
                this.cpc = cpc;
            }

            public String getCpm() {
                return cpm;
            }

            public void setCpm(String cpm) {
                this.cpm = cpm;
            }

            public String getExposure() {
                return exposure;
            }

            public void setExposure(String exposure) {
                this.exposure = exposure;
            }

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getClickRate() {
                return clickRate;
            }

            public void setClickRate(String clickRate) {
                this.clickRate = clickRate;
            }
        }
    }
}
