package com.zhiziyun.dmptest.bot.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public class FlagInfo {


    /**
     * total : 0
     * rows : [{"id":1265,"name":"人口属性标签","code":"03","parentId":0,"isLeaf":0,"dtype":"population","children":[{"id":1266,"name":"性别-男","code":"030101","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1267,"name":"性别-女","code":"030102","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1268,"name":"年龄-19-25岁","code":"030207","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1269,"name":"年龄-26-35岁","code":"030208","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1270,"name":"年龄-36-45岁","code":"030209","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1271,"name":"年龄-46-55岁","code":"030210","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1272,"name":"年龄-55岁以上","code":"030211","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1273,"name":"年龄-19岁以下","code":"030212","parentId":1265,"isLeaf":1,"dtype":"population","children":[]}]}]
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
         * id : 1265
         * name : 人口属性标签
         * code : 03
         * parentId : 0
         * isLeaf : 0
         * dtype : population
         * children : [{"id":1266,"name":"性别-男","code":"030101","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1267,"name":"性别-女","code":"030102","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1268,"name":"年龄-19-25岁","code":"030207","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1269,"name":"年龄-26-35岁","code":"030208","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1270,"name":"年龄-36-45岁","code":"030209","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1271,"name":"年龄-46-55岁","code":"030210","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1272,"name":"年龄-55岁以上","code":"030211","parentId":1265,"isLeaf":1,"dtype":"population","children":[]},{"id":1273,"name":"年龄-19岁以下","code":"030212","parentId":1265,"isLeaf":1,"dtype":"population","children":[]}]
         */

        private int id;
        private String name;
        private String code;
        private int parentId;
        private int isLeaf;
        private String dtype;
        private List<ChildrenBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getIsLeaf() {
            return isLeaf;
        }

        public void setIsLeaf(int isLeaf) {
            this.isLeaf = isLeaf;
        }

        public String getDtype() {
            return dtype;
        }

        public void setDtype(String dtype) {
            this.dtype = dtype;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 1266
             * name : 性别-男
             * code : 030101
             * parentId : 1265
             * isLeaf : 1
             * dtype : population
             * children : []
             */

            private int id;
            private String name;
            private String code;
            private int parentId;
            private int isLeaf;
            private String dtype;
            private List<?> children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getIsLeaf() {
                return isLeaf;
            }

            public void setIsLeaf(int isLeaf) {
                this.isLeaf = isLeaf;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}
