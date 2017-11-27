package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 * 访客详情实体类
 */

public class Visitorsselfparticulars {

    /**
     * did : B9F4C270162055550550619591A220F3
     * probemac : 18fe34cb9448
     * dtype : imei
     * mac : 000822470f32
     * visittime : 2017-11-27 00:04:25
     * creattime : 2017-11-27 00:04:25
     * updatetime : 2017-11-27 00:10:20
     * basic : []
     * population : []
     * app : [{"name":"工作","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"商旅出行","weight":5.26,"recency":1508989111353,"platform":"td"},{"name":"车主服务","weight":6.3,"recency":1508989111353,"platform":"td"},{"name":"影音","weight":4.59,"recency":1508989111353,"platform":"td"},{"name":"优惠券","weight":2.93,"recency":1508989111353,"platform":"td"},{"name":"团购","weight":0.8,"recency":1508989111353,"platform":"td"},{"name":"房产","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"网上商城","weight":6.3,"recency":1508989111353,"platform":"td"},{"name":"借贷","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"金融理财","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"房屋咨询","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"社交","weight":4.59,"recency":1508989111353,"platform":"td"},{"name":"生活","weight":6.3,"recency":1508989111353,"platform":"td"},{"name":"便民服务","weight":0.8,"recency":1508989111353,"platform":"td"},{"name":"电台","weight":0.5,"recency":1508989111353,"platform":"td"},{"name":"网购","weight":6.3,"recency":1508989111353,"platform":"td"},{"name":"外卖订餐","weight":2.93,"recency":1508989111353,"platform":"td"},{"name":"卖房","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"视频","weight":3.8,"recency":1508989111353,"platform":"td"},{"name":"交友\\社区","weight":3.8,"recency":1508989111353,"platform":"td"},{"name":"支付","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"招聘求职","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"积分活动","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"快递","weight":1.33,"recency":1508989111353,"platform":"td"},{"name":"打车","weight":5.26,"recency":1508989111353,"platform":"td"}]
     * game : [{"name":"连连看","weight":0.5,"recency":1510272016002,"platform":"td"},{"name":"麻将","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"纸牌","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"宝石消除","weight":2.06,"recency":1510272016002,"platform":"td"},{"name":"方块","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"斗地主","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"扑克棋牌","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"益智","weight":0.5,"recency":1510272016002,"platform":"td"},{"name":"桌游","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"休闲时间","weight":0.5,"recency":1510272016002,"platform":"td"},{"name":"棋类","weight":0.68,"recency":1510272016002,"platform":"td"},{"name":"卡通","weight":0.5,"recency":1510272016002,"platform":"td"}]
     * shopping : []
     * topic : []
     * mostused_apps : ["1049","1048","1004","1043","1042","1031","1041","1074","1073"]
     * probe_log : [{"visit_count":1,"probe_mac":"18fe34cb9448","visit":[{"visit_date":"20171127","visit_length":9}],"last_date":"20171127"}]
     */

    private String did;
    private String probemac;
    private String dtype;
    private String mac;
    private String visittime;
    private String creattime;
    private String updatetime;
    private List<?> basic;
    private List<?> population;
    private List<AppBean> app;
    private List<GameBean> game;
    private List<?> shopping;
    private List<?> topic;
    private List<String> mostused_apps;
    private List<ProbeLogBean> probe_log;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getProbemac() {
        return probemac;
    }

    public void setProbemac(String probemac) {
        this.probemac = probemac;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public List<?> getBasic() {
        return basic;
    }

    public void setBasic(List<?> basic) {
        this.basic = basic;
    }

    public List<?> getPopulation() {
        return population;
    }

    public void setPopulation(List<?> population) {
        this.population = population;
    }

    public List<AppBean> getApp() {
        return app;
    }

    public void setApp(List<AppBean> app) {
        this.app = app;
    }

    public List<GameBean> getGame() {
        return game;
    }

    public void setGame(List<GameBean> game) {
        this.game = game;
    }

    public List<?> getShopping() {
        return shopping;
    }

    public void setShopping(List<?> shopping) {
        this.shopping = shopping;
    }

    public List<?> getTopic() {
        return topic;
    }

    public void setTopic(List<?> topic) {
        this.topic = topic;
    }

    public List<String> getMostused_apps() {
        return mostused_apps;
    }

    public void setMostused_apps(List<String> mostused_apps) {
        this.mostused_apps = mostused_apps;
    }

    public List<ProbeLogBean> getProbe_log() {
        return probe_log;
    }

    public void setProbe_log(List<ProbeLogBean> probe_log) {
        this.probe_log = probe_log;
    }

    public static class AppBean {
        /**
         * name : 工作
         * weight : 1.33
         * recency : 1508989111353
         * platform : td
         */

        private String name;
        private double weight;
        private long recency;
        private String platform;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public long getRecency() {
            return recency;
        }

        public void setRecency(long recency) {
            this.recency = recency;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
    }

    public static class GameBean {
        /**
         * name : 连连看
         * weight : 0.5
         * recency : 1510272016002
         * platform : td
         */

        private String name;
        private double weight;
        private long recency;
        private String platform;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public long getRecency() {
            return recency;
        }

        public void setRecency(long recency) {
            this.recency = recency;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
    }

    public static class ProbeLogBean {
        /**
         * visit_count : 1
         * probe_mac : 18fe34cb9448
         * visit : [{"visit_date":"20171127","visit_length":9}]
         * last_date : 20171127
         */

        private int visit_count;
        private String probe_mac;
        private String last_date;
        private List<VisitBean> visit;

        public int getVisit_count() {
            return visit_count;
        }

        public void setVisit_count(int visit_count) {
            this.visit_count = visit_count;
        }

        public String getProbe_mac() {
            return probe_mac;
        }

        public void setProbe_mac(String probe_mac) {
            this.probe_mac = probe_mac;
        }

        public String getLast_date() {
            return last_date;
        }

        public void setLast_date(String last_date) {
            this.last_date = last_date;
        }

        public List<VisitBean> getVisit() {
            return visit;
        }

        public void setVisit(List<VisitBean> visit) {
            this.visit = visit;
        }

        public static class VisitBean {
            /**
             * visit_date : 20171127
             * visit_length : 9
             */

            private String visit_date;
            private int visit_length;

            public String getVisit_date() {
                return visit_date;
            }

            public void setVisit_date(String visit_date) {
                this.visit_date = visit_date;
            }

            public int getVisit_length() {
                return visit_length;
            }

            public void setVisit_length(int visit_length) {
                this.visit_length = visit_length;
            }
        }
    }
}
