package com.bd19liu.app.model;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudugang
 * @Date: 2019/1/7  17:56
 */
public class IpAddress implements Serializable{

    private static final Long serializable = 1L;
    private String country;
    private String city;
    private String province;
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
}
