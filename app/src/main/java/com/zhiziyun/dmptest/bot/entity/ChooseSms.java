package com.zhiziyun.dmptest.bot.entity;


import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class ChooseSms {


    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":24,"data":[{"smsId":"vbxgs0egDHq","smsName":"测试短信-0","smsContent":"上海智子信息科技股份有限公司测试短信-0,上海智子信息科技股份有限公司测试短信-0","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgB0vmFvq","smsName":"测试短信-2","smsContent":"上海智子信息科技股份有限公司测试短信-2,上海智子信息科技股份有限公司测咯短信-2","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgC04hpqo","smsName":"测试短信-3","smsContent":"上海智子信息科技股份有限公司测试短信-3,上海智子信息科技股份有限公司测试短信-3","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgy0hwPaU","smsName":"测试短信-1","smsContent":"上海智子信息科技股份有限公司测试短信-1,上海智子信息科技股份有限公司测试短信-1","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3cs01gYm4","smsName":"测试短信-4","smsContent":"测试短信-4测试短信-4测试短信-4测试短信-4测试短信-4测试短信-4","updateDate":"2018-01-30","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3dA0DQ87u","smsName":"测试短信-5","smsContent":"测试短信-5测试短信-5测试短信-5测试短信-5测试短信-5测试短信-5","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3es0H4HqU","smsName":"测试短信-6","smsContent":"测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vkmfC0YNxgA","smsName":"测试20171226","smsContent":"测试发送短信","updateDate":"2017-12-26","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vnr8P0uVCRW","smsName":"短信-171228-1","smsContent":"uuuu，。/激活方法././/（）*（*(&(nnhyddg","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vnrtk0PmuUU","smsName":"12345678901234567890","smsContent":"1234567890123456789012345678901234567890123456789012345678901234567890","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"}]}
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
         * total : 24
         * data : [{"smsId":"vbxgs0egDHq","smsName":"测试短信-0","smsContent":"上海智子信息科技股份有限公司测试短信-0,上海智子信息科技股份有限公司测试短信-0","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgB0vmFvq","smsName":"测试短信-2","smsContent":"上海智子信息科技股份有限公司测试短信-2,上海智子信息科技股份有限公司测咯短信-2","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgC04hpqo","smsName":"测试短信-3","smsContent":"上海智子信息科技股份有限公司测试短信-3,上海智子信息科技股份有限公司测试短信-3","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vbxgy0hwPaU","smsName":"测试短信-1","smsContent":"上海智子信息科技股份有限公司测试短信-1,上海智子信息科技股份有限公司测试短信-1","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3cs01gYm4","smsName":"测试短信-4","smsContent":"测试短信-4测试短信-4测试短信-4测试短信-4测试短信-4测试短信-4","updateDate":"2018-01-30","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3dA0DQ87u","smsName":"测试短信-5","smsContent":"测试短信-5测试短信-5测试短信-5测试短信-5测试短信-5测试短信-5","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vg3es0H4HqU","smsName":"测试短信-6","smsContent":"测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6测试短信-6","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vkmfC0YNxgA","smsName":"测试20171226","smsContent":"测试发送短信","updateDate":"2017-12-26","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vnr8P0uVCRW","smsName":"短信-171228-1","smsContent":"uuuu，。/激活方法././/（）*（*(&(nnhyddg","updateDate":"2018-02-02","smsStatus":"未提交","smsSignature":"智子云"},{"smsId":"vnrtk0PmuUU","smsName":"12345678901234567890","smsContent":"1234567890123456789012345678901234567890123456789012345678901234567890","updateDate":"2018-01-29","smsStatus":"未提交","smsSignature":"智子云"}]
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
             * smsId : vbxgs0egDHq
             * smsName : 测试短信-0
             * smsContent : 上海智子信息科技股份有限公司测试短信-0,上海智子信息科技股份有限公司测试短信-0
             * updateDate : 2018-02-02
             * smsStatus : 未提交
             * smsSignature : 智子云
             */

            private String smsId;
            private String smsName;
            private String smsContent;
            private String updateDate;
            private String smsStatus;
            private String smsSignature;

            public String getSmsId() {
                return smsId;
            }

            public void setSmsId(String smsId) {
                this.smsId = smsId;
            }

            public String getSmsName() {
                return smsName;
            }

            public void setSmsName(String smsName) {
                this.smsName = smsName;
            }

            public String getSmsContent() {
                return smsContent;
            }

            public void setSmsContent(String smsContent) {
                this.smsContent = smsContent;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getSmsStatus() {
                return smsStatus;
            }

            public void setSmsStatus(String smsStatus) {
                this.smsStatus = smsStatus;
            }

            public String getSmsSignature() {
                return smsSignature;
            }

            public void setSmsSignature(String smsSignature) {
                this.smsSignature = smsSignature;
            }
        }
    }
}
