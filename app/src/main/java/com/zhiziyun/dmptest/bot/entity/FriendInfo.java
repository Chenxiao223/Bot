package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class FriendInfo {


    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"tabItem1":{"wechatActivityId":20563237,"wechatActivityName":"安卓测试七","productType":"LINK_WECHAT","dailyBudget":2000,"startDate":"2018-08-06","endDate":"2018-08-07","timeSeries":"111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000"},"tabItem2":{"bidAmount":200,"targeting":{"age":["25~60"],"gender":["FEMALE"],"education":["JUNIOR","DOCTOR","MASTER","PRIMARY"],"relationshipStatus":["NEWLY_MARRIED","SINGLE","MARRIED","PARENTING"],"geoLocation":[110109,110108,110229,110105,110115],"customeAudiences":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io","zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO","AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"],"cityLevel":"CITY_LEVEL_CORE","probeTagIds":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io"],"wifiTagIds":["zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO"],"adclickTagIds":["AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"]}},"tabItem3":{"templateId":311,"image_list":"http://pgdt.gtimg.cn/gdt/0/DAAefl_AMgAMgAA3BbZ_0MB7ji6HSV.jpg/0?ck=bea358a6cbb7ce0ee47dbe1654af4bf6","title":"JJ哦默默","destinationUrl":"http://www.zhiziyun.com","productRefsId":"","shareInfo":{"shareDescription":"你来我们","shareTitle":"军女特警"}}}
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
         * tabItem1 : {"wechatActivityId":20563237,"wechatActivityName":"安卓测试七","productType":"LINK_WECHAT","dailyBudget":2000,"startDate":"2018-08-06","endDate":"2018-08-07","timeSeries":"111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000"}
         * tabItem2 : {"bidAmount":200,"targeting":{"age":["25~60"],"gender":["FEMALE"],"education":["JUNIOR","DOCTOR","MASTER","PRIMARY"],"relationshipStatus":["NEWLY_MARRIED","SINGLE","MARRIED","PARENTING"],"geoLocation":[110109,110108,110229,110105,110115],"customeAudiences":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io","zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO","AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"],"cityLevel":"CITY_LEVEL_CORE","probeTagIds":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io"],"wifiTagIds":["zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO"],"adclickTagIds":["AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"]}}
         * tabItem3 : {"templateId":311,"image_list":"http://pgdt.gtimg.cn/gdt/0/DAAefl_AMgAMgAA3BbZ_0MB7ji6HSV.jpg/0?ck=bea358a6cbb7ce0ee47dbe1654af4bf6","title":"JJ哦默默","destinationUrl":"http://www.zhiziyun.com","productRefsId":"","shareInfo":{"shareDescription":"你来我们","shareTitle":"军女特警"}}
         */

        private TabItem1Bean tabItem1;
        private TabItem2Bean tabItem2;
        private TabItem3Bean tabItem3;

        public TabItem1Bean getTabItem1() {
            return tabItem1;
        }

        public void setTabItem1(TabItem1Bean tabItem1) {
            this.tabItem1 = tabItem1;
        }

        public TabItem2Bean getTabItem2() {
            return tabItem2;
        }

        public void setTabItem2(TabItem2Bean tabItem2) {
            this.tabItem2 = tabItem2;
        }

        public TabItem3Bean getTabItem3() {
            return tabItem3;
        }

        public void setTabItem3(TabItem3Bean tabItem3) {
            this.tabItem3 = tabItem3;
        }

        public static class TabItem1Bean {
            /**
             * wechatActivityId : 20563237
             * wechatActivityName : 安卓测试七
             * productType : LINK_WECHAT
             * dailyBudget : 2000
             * startDate : 2018-08-06
             * endDate : 2018-08-07
             * timeSeries : 111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000111111111111111100000000000000000000000000000000
             */

            private int wechatActivityId;
            private String wechatActivityName;
            private String productType;
            private int dailyBudget;
            private String startDate;
            private String endDate;
            private String timeSeries;
            private String systemStatus;

            public String getSystemStatus() {
                return systemStatus;
            }

            public void setSystemStatus(String systemStatus) {
                this.systemStatus = systemStatus;
            }

            public int getWechatActivityId() {
                return wechatActivityId;
            }

            public void setWechatActivityId(int wechatActivityId) {
                this.wechatActivityId = wechatActivityId;
            }

            public String getWechatActivityName() {
                return wechatActivityName;
            }

            public void setWechatActivityName(String wechatActivityName) {
                this.wechatActivityName = wechatActivityName;
            }

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public int getDailyBudget() {
                return dailyBudget;
            }

            public void setDailyBudget(int dailyBudget) {
                this.dailyBudget = dailyBudget;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getTimeSeries() {
                return timeSeries;
            }

            public void setTimeSeries(String timeSeries) {
                this.timeSeries = timeSeries;
            }
        }

        public static class TabItem2Bean {
            /**
             * bidAmount : 200
             * targeting : {"age":["25~60"],"gender":["FEMALE"],"education":["JUNIOR","DOCTOR","MASTER","PRIMARY"],"relationshipStatus":["NEWLY_MARRIED","SINGLE","MARRIED","PARENTING"],"geoLocation":[110109,110108,110229,110105,110115],"customeAudiences":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io","zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO","AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"],"cityLevel":"CITY_LEVEL_CORE","probeTagIds":["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io"],"wifiTagIds":["zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO"],"adclickTagIds":["AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"]}
             */

            private int bidAmount;
            private TargetingBean targeting;

            public int getBidAmount() {
                return bidAmount;
            }

            public void setBidAmount(int bidAmount) {
                this.bidAmount = bidAmount;
            }

            public TargetingBean getTargeting() {
                return targeting;
            }

            public void setTargeting(TargetingBean targeting) {
                this.targeting = targeting;
            }

            public static class TargetingBean {
                /**
                 * age : ["25~60"]
                 * gender : ["FEMALE"]
                 * education : ["JUNIOR","DOCTOR","MASTER","PRIMARY"]
                 * relationshipStatus : ["NEWLY_MARRIED","SINGLE","MARRIED","PARENTING"]
                 * geoLocation : [110109,110108,110229,110105,110115]
                 * customeAudiences : ["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io","zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO","AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"]
                 * cityLevel : CITY_LEVEL_CORE
                 * probeTagIds : ["AFYJd0uD0fS","AFYX10Twmgu","AFYhl0gVNzq","AFWD30ZLi3C","AFWvc0Ip5io"]
                 * wifiTagIds : ["zhgne0GnySY","zbuUI0gpdOU","zbrMS0uatMs","zbpQ60VvBLO"]
                 * adclickTagIds : ["AHSCs0Ba54Q","AHToC038nsI","AHTOZ0MJaes","AIQua0PnbeU"]
                 */

                private String cityLevel;
                private List<String> age;
                private List<String> gender;
                private List<String> education;
                private List<String> relationshipStatus;
                private List<Integer> geoLocation;
                private List<String> customeAudiences;
                private List<String> probeTagIds;
                private List<String> wifiTagIds;
                private List<String> adclickTagIds;

                public String getCityLevel() {
                    return cityLevel;
                }

                public void setCityLevel(String cityLevel) {
                    this.cityLevel = cityLevel;
                }

                public List<String> getAge() {
                    return age;
                }

                public void setAge(List<String> age) {
                    this.age = age;
                }

                public List<String> getGender() {
                    return gender;
                }

                public void setGender(List<String> gender) {
                    this.gender = gender;
                }

                public List<String> getEducation() {
                    return education;
                }

                public void setEducation(List<String> education) {
                    this.education = education;
                }

                public List<String> getRelationshipStatus() {
                    return relationshipStatus;
                }

                public void setRelationshipStatus(List<String> relationshipStatus) {
                    this.relationshipStatus = relationshipStatus;
                }

                public List<Integer> getGeoLocation() {
                    return geoLocation;
                }

                public void setGeoLocation(List<Integer> geoLocation) {
                    this.geoLocation = geoLocation;
                }

                public List<String> getCustomeAudiences() {
                    return customeAudiences;
                }

                public void setCustomeAudiences(List<String> customeAudiences) {
                    this.customeAudiences = customeAudiences;
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
            }
        }

        public static class TabItem3Bean {
            /**
             * templateId : 311
             * image_list : http://pgdt.gtimg.cn/gdt/0/DAAefl_AMgAMgAA3BbZ_0MB7ji6HSV.jpg/0?ck=bea358a6cbb7ce0ee47dbe1654af4bf6
             * title : JJ哦默默
             * destinationUrl : http://www.zhiziyun.com
             * productRefsId :
             * shareInfo : {"shareDescription":"你来我们","shareTitle":"军女特警"}
             */

            private int templateId;
            private String image_list;
            private String title;
            private String destinationUrl;
            private String productRefsId;
            private ShareInfoBean shareInfo;

            public int getTemplateId() {
                return templateId;
            }

            public void setTemplateId(int templateId) {
                this.templateId = templateId;
            }

            public String getImage_list() {
                return image_list;
            }

            public void setImage_list(String image_list) {
                this.image_list = image_list;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDestinationUrl() {
                return destinationUrl;
            }

            public void setDestinationUrl(String destinationUrl) {
                this.destinationUrl = destinationUrl;
            }

            public String getProductRefsId() {
                return productRefsId;
            }

            public void setProductRefsId(String productRefsId) {
                this.productRefsId = productRefsId;
            }

            public ShareInfoBean getShareInfo() {
                return shareInfo;
            }

            public void setShareInfo(ShareInfoBean shareInfo) {
                this.shareInfo = shareInfo;
            }

            public static class ShareInfoBean {
                /**
                 * shareDescription : 你来我们
                 * shareTitle : 军女特警
                 */

                private String shareDescription;
                private String shareTitle;

                public String getShareDescription() {
                    return shareDescription;
                }

                public void setShareDescription(String shareDescription) {
                    this.shareDescription = shareDescription;
                }

                public String getShareTitle() {
                    return shareTitle;
                }

                public void setShareTitle(String shareTitle) {
                    this.shareTitle = shareTitle;
                }
            }
        }
    }
}
