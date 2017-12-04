package com.zhiziyun.dmptest.bot.entity;


import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 * 访客详情实体类
 */

public class Visitorsselfparticulars {

    /**
     * did : 590835B1-133C-439B-B596-4407F965A3C8
     * probemac : 6001941c2d66
     * dtype : idfa
     * mac : 903c924b9e89
     * visittime : 2017-12-04 10:33:12
     * creattime : 2017-11-27 10:59:25
     * updatetime : 2017-12-04 10:39:00
     * basic : [{"name":"standardModel-iPhone 6 plus","weight":100,"recency":1511751697759,"platform":"td"},{"name":"price-2000-3999","weight":100,"recency":1511751697759,"platform":"td"},{"name":"functionType-音乐","weight":100,"recency":1511751697759,"platform":"td"},{"name":"deviceType-手机","weight":100,"recency":1511751697759,"platform":"td"},{"name":"screen-5.5-5.0英寸","weight":100,"recency":1511751697759,"platform":"td"},{"name":"standardBrand-iPhone","weight":100,"recency":1511751697759,"platform":"td"},{"name":"originModel-iPhone7,1","weight":100,"recency":1511751697759,"platform":"td"},{"name":"cnBrand-苹果","weight":100,"recency":1511751697759,"platform":"td"},{"name":"hardwareType-陀螺仪,NFC芯片,指纹识别,2核","weight":100,"recency":1511751697759,"platform":"td"},{"name":"timeTomarket-2014年09月","weight":100,"recency":1511751697759,"platform":"td"}]
     * population : [{"name":"性别-女","weight":86,"recency":1511751698540,"platform":"td"},{"name":"婚姻-已婚","weight":100,"recency":1506144003521,"platform":"td"},{"name":"准爸准妈","platform":"td","recency":1506155409100,"weight":0.3}]
     * app : [{"name":"拍照摄影","weight":0.5,"recency":1510270793666,"platform":"td"},{"name":"便民服务","weight":67.58,"recency":1510270793666,"platform":"td"},{"name":"网购","weight":67.58,"recency":1510270793666,"platform":"td"},{"name":"生活","weight":67.58,"recency":1510270793666,"platform":"td"},{"name":"快递","weight":67.58,"recency":1510270793666,"platform":"td"},{"name":"网上商城","weight":67.58,"recency":1510270793666,"platform":"td"}]
     * game : [{"name":"卡通","weight":0.3,"recency":1510207973769,"platform":"td"},{"name":"宝石消除","weight":0.3,"recency":1510207973769,"platform":"td"}]
     * shopping : [{"name":"医疗","weight":0.71,"recency":1510413363381,"platform":"td"}]
     * topic : [{"name":"医疗","weight":0.71,"recency":1510413363381,"platform":"td"}]
     * mostused_apps : ["1004","1003","1002","1073","1072","1071"]
     * probe_log : [{"visit_count":6,"probe_mac":"6001941c2d66","visit":[{"visit_date":"20171127","visit_length":221},{"visit_date":"20171128","visit_length":127},{"visit_date":"20171129","visit_length":207},{"visit_date":"20171130","visit_length":152},{"visit_date":"20171201","visit_length":121},{"visit_date":"20171204","visit_length":18}],"last_date":"20171204"},{"visit_count":2,"probe_mac":"a020a610bee1","visit":[{"visit_date":"20171127","visit_length":9},{"visit_date":"20171130","visit_length":53}],"last_date":"20171130"},{"visit_count":3,"probe_mac":"a020a61114ff","visit":[{"visit_date":"20171127","visit_length":28},{"visit_date":"20171201","visit_length":19},{"visit_date":"20171204","visit_length":9}],"last_date":"20171204"},{"visit_count":4,"probe_mac":"a020a61114fd","visit":[{"visit_date":"20171128","visit_length":138},{"visit_date":"20171129","visit_length":238},{"visit_date":"20171130","visit_length":243},{"visit_date":"20171201","visit_length":105}],"last_date":"20171201"}]
     */

    private String did;
    private String probemac;
    private String dtype;
    private String mac;
    private String visittime;
    private String creattime;
    private String updatetime;
    private List<BasicBean> basic;
    private List<PopulationBean> population;
    private List<AppBean> app;
    private List<GameBean> game;
    private List<ShoppingBean> shopping;
    private List<TopicBean> topic;
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

    public List<BasicBean> getBasic() {
        return basic;
    }

    public void setBasic(List<BasicBean> basic) {
        this.basic = basic;
    }

    public List<PopulationBean> getPopulation() {
        return population;
    }

    public void setPopulation(List<PopulationBean> population) {
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

    public List<ShoppingBean> getShopping() {
        return shopping;
    }

    public void setShopping(List<ShoppingBean> shopping) {
        this.shopping = shopping;
    }

    public List<TopicBean> getTopic() {
        return topic;
    }

    public void setTopic(List<TopicBean> topic) {
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

    public static class BasicBean {
        /**
         * name : standardModel-iPhone 6 plus
         * weight : 100.0
         * recency : 1511751697759
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

    public static class PopulationBean {
        /**
         * name : 性别-女
         * weight : 86.0
         * recency : 1511751698540
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

    public static class AppBean {
        /**
         * name : 拍照摄影
         * weight : 0.5
         * recency : 1510270793666
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
         * name : 卡通
         * weight : 0.3
         * recency : 1510207973769
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

    public static class ShoppingBean {
        /**
         * name : 医疗
         * weight : 0.71
         * recency : 1510413363381
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

    public static class TopicBean {
        /**
         * name : 医疗
         * weight : 0.71
         * recency : 1510413363381
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
         * visit_count : 6
         * probe_mac : 6001941c2d66
         * visit : [{"visit_date":"20171127","visit_length":221},{"visit_date":"20171128","visit_length":127},{"visit_date":"20171129","visit_length":207},{"visit_date":"20171130","visit_length":152},{"visit_date":"20171201","visit_length":121},{"visit_date":"20171204","visit_length":18}]
         * last_date : 20171204
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
             * visit_length : 221
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
