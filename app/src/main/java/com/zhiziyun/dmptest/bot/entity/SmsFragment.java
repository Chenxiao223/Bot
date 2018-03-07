package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class SmsFragment {


    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"total":10,"data":[{"activityId":"wnMeB0YPaTu","activityName":"测试1111","activityStatus":"待发送","sendTime":"","sendNum":0,"smsStatus":"未提交"},{"activityId":"vRRny0rJSYo","activityName":"短信活动1","activityStatus":"已发送","sendTime":"2018-02-06 17:46:02","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc7Na0ZXIMU","activityName":"test013001","activityStatus":"已发送","sendTime":"2018-01-30 18:37:27","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc61q0zzUUU","activityName":"0130-6","activityStatus":"已发送","sendTime":"2018-01-30 18:04:42","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc5DR0OuDks","activityName":"0130-5","activityStatus":"已发送","sendTime":"2018-01-30 17:58:55","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbSw009Vsas","activityName":"0130-4","activityStatus":"已发送","sendTime":"2018-01-30 14:38:28","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbQGL0q3Qe4","activityName":"0130-1","activityStatus":"已发送","sendTime":"2018-01-30 14:10:32","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbDO30OtZU4","activityName":"0130","activityStatus":"已发送","sendTime":"2018-01-30 13:55:59","sendNum":0,"smsStatus":"未提交"},{"activityId":"vSg0N0nv8Pe","activityName":"APP广告活动测试180117-07","activityStatus":"已发送","sendTime":"2018-01-26 12:00:05","sendNum":10,"smsStatus":"未提交"},{"activityId":"vS6ZN0lRcwE","activityName":"0117-2","activityStatus":"已发送","sendTime":"2018-01-17 14:51:47","sendNum":0,"smsStatus":"未提交"}]}
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
         * total : 10
         * data : [{"activityId":"wnMeB0YPaTu","activityName":"测试1111","activityStatus":"待发送","sendTime":"","sendNum":0,"smsStatus":"未提交"},{"activityId":"vRRny0rJSYo","activityName":"短信活动1","activityStatus":"已发送","sendTime":"2018-02-06 17:46:02","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc7Na0ZXIMU","activityName":"test013001","activityStatus":"已发送","sendTime":"2018-01-30 18:37:27","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc61q0zzUUU","activityName":"0130-6","activityStatus":"已发送","sendTime":"2018-01-30 18:04:42","sendNum":0,"smsStatus":"未提交"},{"activityId":"wc5DR0OuDks","activityName":"0130-5","activityStatus":"已发送","sendTime":"2018-01-30 17:58:55","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbSw009Vsas","activityName":"0130-4","activityStatus":"已发送","sendTime":"2018-01-30 14:38:28","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbQGL0q3Qe4","activityName":"0130-1","activityStatus":"已发送","sendTime":"2018-01-30 14:10:32","sendNum":0,"smsStatus":"未提交"},{"activityId":"wbDO30OtZU4","activityName":"0130","activityStatus":"已发送","sendTime":"2018-01-30 13:55:59","sendNum":0,"smsStatus":"未提交"},{"activityId":"vSg0N0nv8Pe","activityName":"APP广告活动测试180117-07","activityStatus":"已发送","sendTime":"2018-01-26 12:00:05","sendNum":10,"smsStatus":"未提交"},{"activityId":"vS6ZN0lRcwE","activityName":"0117-2","activityStatus":"已发送","sendTime":"2018-01-17 14:51:47","sendNum":0,"smsStatus":"未提交"}]
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
             * activityId : wnMeB0YPaTu
             * activityName : 测试1111
             * activityStatus : 待发送
             * sendTime :
             * sendNum : 0
             * smsStatus : 未提交
             */

            private String activityId;
            private String activityName;
            private String activityStatus;
            private String sendTime;
            private int sendNum;
            private String smsStatus;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public String getActivityStatus() {
                return activityStatus;
            }

            public void setActivityStatus(String activityStatus) {
                this.activityStatus = activityStatus;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public int getSendNum() {
                return sendNum;
            }

            public void setSendNum(int sendNum) {
                this.sendNum = sendNum;
            }

            public String getSmsStatus() {
                return smsStatus;
            }

            public void setSmsStatus(String smsStatus) {
                this.smsStatus = smsStatus;
            }
        }
    }
}
