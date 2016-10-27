package com.newthread.ntp_yuyinzhushou.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 张浩 on 2016/10/10.
 */

public class YunYinBean {

    /**
     * sn : 1
     * ls : false
     * bg : 0
     * ed : 0
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"测试"}]},{"bg":0,"cw":[{"sc":0,"w":"下"}]},{"bg":0,"cw":[{"sc":0,"w":"命令"}]}]
     */

    private String sn;
    private boolean ls;
    private int bg;
    private int ed;
    /**
     * bg : 0
     * cw : [{"sc":0,"w":"测试"}]
     */

    private List<WsBean> ws;

    public static YunYinBean objectFromData(String str) {

        return new Gson().fromJson(str, YunYinBean.class);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public List<WsBean> getWs() {
        return ws;
    }

    public void setWs(List<WsBean> ws) {
        this.ws = ws;
    }

    public static class WsBean {
        private int bg;
        /**
         * sc : 0
         * w : 测试
         */

        private List<CwBean> cw;

        public static WsBean objectFromData(String str) {

            return new Gson().fromJson(str, WsBean.class);
        }

        public int getBg() {
            return bg;
        }

        public void setBg(int bg) {
            this.bg = bg;
        }

        public List<CwBean> getCw() {
            return cw;
        }

        public void setCw(List<CwBean> cw) {
            this.cw = cw;
        }

        public static class CwBean {
            private int sc;
            private String w;

            public static CwBean objectFromData(String str) {

                return new Gson().fromJson(str, CwBean.class);
            }

            public int getSc() {
                return sc;
            }

            public void setSc(int sc) {
                this.sc = sc;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }
        }
    }
}
