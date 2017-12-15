package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class AppImage {

    /**
     * app图标实体类
     * total : 8
     * rows : [{"id":1002,"category":"网购","name":"51返利","icon":"http://static.zhiziyun.com/images/appicons/gouwu/bijia/fanli.png"},{"id":1003,"category":"网购","name":"聚划算","icon":"http://static.zhiziyun.com/images/appicons/gouwu/tuan/juhuasuan.png"},{"id":1012,"category":"教育","name":"妈妈帮","icon":"http://static.zhiziyun.com/images/appicons/jiaoyu/mamabang.png"},{"id":1013,"category":"教育","name":"校园搜题","icon":"http://static.zhiziyun.com/images/appicons/jiaoyu/xiaoyuansouti.png"},{"id":1017,"category":"阅读","name":"内涵段子","icon":"http://static.zhiziyun.com/images/appicons/yuedu/neihanduanzi.png"},{"id":1018,"category":"阅读","name":"起点小说","icon":"http://static.zhiziyun.com/images/appicons/yuedu/qidianxiaoshuo.png"},{"id":1045,"category":"影音","name":"西瓜视频","icon":"http://static.zhiziyun.com/images/appicons/yinying/xiguashipin.png"},{"id":1101,"category":"母婴","name":"宝宝树","icon":"http://static.zhiziyun.com/images/appicons/muying/baobaoshu.png"}]
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 1002
         * category : 网购
         * name : 51返利
         * icon : http://static.zhiziyun.com/images/appicons/gouwu/bijia/fanli.png
         */

        private int id;
        private String category;
        private String name;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
