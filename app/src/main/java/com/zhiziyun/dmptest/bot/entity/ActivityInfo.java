package com.zhiziyun.dmptest.bot.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ActivityInfo {


    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"activityId":"AHZxl0ZJeBG","activityName":"12313","activityType":"1","startTime":"2018-07-31 17:04:17","endTime":"2018-07-31 17:04:19","targetHours":{},"budget":-1,"dailyBudget":"1223.00","pricingType":1,"price":"1313.00","pacingType":2,"tagIds":["zbpWk0kJine","zbuUI0gpdOU","AhMO512OAfu"],"probeTagIds":[],"wifiTagIds":["zbpWk0kJine","zbuUI0gpdOU","AhMO512OAfu"],"adclickTagIds":[],"templatePackageSaveInfo":{"templatePackageId":"Agmiz07vws0","templatePackageUserParam":{"logo":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778512.jpg","description":"1323","mainimage":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778303.jpg","111":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778460.jpg"},"templatePackagePreviewImages":["http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHKOc0ezh1S-1533210778979.jpg","http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHVSl06GusM-1533210779146.jpg"],"targetUrl":"13231123"}}
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
         * activityId : AHZxl0ZJeBG
         * activityName : 12313
         * activityType : 1
         * startTime : 2018-07-31 17:04:17
         * endTime : 2018-07-31 17:04:19
         * targetHours : {}
         * budget : -1
         * dailyBudget : 1223.00
         * pricingType : 1
         * price : 1313.00
         * pacingType : 2
         * tagIds : ["zbpWk0kJine","zbuUI0gpdOU","AhMO512OAfu"]
         * probeTagIds : []
         * wifiTagIds : ["zbpWk0kJine","zbuUI0gpdOU","AhMO512OAfu"]
         * adclickTagIds : []
         * templatePackageSaveInfo : {"templatePackageId":"Agmiz07vws0","templatePackageUserParam":{"logo":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778512.jpg","description":"1323","mainimage":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778303.jpg","111":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778460.jpg"},"templatePackagePreviewImages":["http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHKOc0ezh1S-1533210778979.jpg","http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHVSl06GusM-1533210779146.jpg"],"targetUrl":"13231123"}
         */

        private String activityId;
        private String activityName;
        private String activityType;
        private String startTime;
        private String endTime;
        private Hashtable<String, List<String>> targetHours;
        private int budget;
        private String dailyBudget;
        private int pricingType;
        private String price;
        private int pacingType;
        private TemplatePackageSaveInfoBean templatePackageSaveInfo;
        private List<String> tagIds;
        private List<String> probeTagIds;
        private List<String> wifiTagIds;
        private List<String> adclickTagIds;

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

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Hashtable<String, List<String>> getTargetHours() {
            return targetHours;
        }

        public void setTargetHours(Hashtable<String, List<String>> targetHours) {
            this.targetHours = targetHours;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getDailyBudget() {
            return dailyBudget;
        }

        public void setDailyBudget(String dailyBudget) {
            this.dailyBudget = dailyBudget;
        }

        public int getPricingType() {
            return pricingType;
        }

        public void setPricingType(int pricingType) {
            this.pricingType = pricingType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getPacingType() {
            return pacingType;
        }

        public void setPacingType(int pacingType) {
            this.pacingType = pacingType;
        }

        public TemplatePackageSaveInfoBean getTemplatePackageSaveInfo() {
            return templatePackageSaveInfo;
        }

        public void setTemplatePackageSaveInfo(TemplatePackageSaveInfoBean templatePackageSaveInfo) {
            this.templatePackageSaveInfo = templatePackageSaveInfo;
        }

        public List<String> getTagIds() {
            return tagIds;
        }

        public void setTagIds(List<String> tagIds) {
            this.tagIds = tagIds;
        }

        public List<String> getProbeTagIds() {
            return probeTagIds;
        }

        public void setProbeTagIds(List<String> probeTagIds) {
            this.probeTagIds = probeTagIds;
        }

        public List<String> getWifiTagIds() {
            return wifiTagIds;
        }

        public void setWifiTagIds(List<String> wifiTagIds) {
            this.wifiTagIds = wifiTagIds;
        }

        public List<String> getAdclickTagIds() {
            return adclickTagIds;
        }

        public void setAdclickTagIds(List<String> adclickTagIds) {
            this.adclickTagIds = adclickTagIds;
        }

        public static class TargetHoursBean {
        }

        public static class TemplatePackageSaveInfoBean {
            /**
             * templatePackageId : Agmiz07vws0
             * templatePackageUserParam : {"logo":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778512.jpg","description":"1323","mainimage":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778303.jpg","111":"http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778460.jpg"}
             * templatePackagePreviewImages : ["http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHKOc0ezh1S-1533210778979.jpg","http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-AHVSl06GusM-1533210779146.jpg"]
             * targetUrl : 13231123
             */

            private String templatePackageId;
            private TemplatePackageUserParamBean templatePackageUserParam;
            private String targetUrl;
            private List<String> templatePackagePreviewImages;

            public String getTemplatePackageId() {
                return templatePackageId;
            }

            public void setTemplatePackageId(String templatePackageId) {
                this.templatePackageId = templatePackageId;
            }

            public TemplatePackageUserParamBean getTemplatePackageUserParam() {
                return templatePackageUserParam;
            }

            public void setTemplatePackageUserParam(TemplatePackageUserParamBean templatePackageUserParam) {
                this.templatePackageUserParam = templatePackageUserParam;
            }

            public String getTargetUrl() {
                return targetUrl;
            }

            public void setTargetUrl(String targetUrl) {
                this.targetUrl = targetUrl;
            }

            public List<String> getTemplatePackagePreviewImages() {
                return templatePackagePreviewImages;
            }

            public void setTemplatePackagePreviewImages(List<String> templatePackagePreviewImages) {
                this.templatePackagePreviewImages = templatePackagePreviewImages;
            }

            public static class TemplatePackageUserParamBean {
                /**
                 * logo : http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778512.jpg
                 * description : 1323
                 * mainimage : http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778303.jpg
                 * 111 : http://images.test.zhiziyun.com/creative/0zoTLi29XRgq-Agmiz07vws0-1533210778460.jpg
                 */

                private String logo;
                private String description;
                private String mainimage;
                @SerializedName("111")
                private String _$111;

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getMainimage() {
                    return mainimage;
                }

                public void setMainimage(String mainimage) {
                    this.mainimage = mainimage;
                }

                public String get_$111() {
                    return _$111;
                }

                public void set_$111(String _$111) {
                    this._$111 = _$111;
                }
            }
        }
    }
}
