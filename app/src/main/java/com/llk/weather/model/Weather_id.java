package com.llk.weather.model;

/**
 * 天气
 */
public class Weather_id {
    /**
     * 如果fa和fb不想等说明该天气为组合天气
     */
    private String fa;
    private String fb;

    public void setFa(String fa) {
        this.fa = fa;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getFa() {
        return fa;
    }

    public String getFb() {
        return fb;
    }
}
