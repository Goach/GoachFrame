package com.data.lib.db;

import java.util.List;

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */

public class JsonCity {
    private String area;
    private List<JsonCity> cities;
    private String code;
    private String level;
    private String name;
    private String prefix;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<JsonCity> getCities() {
        return cities;
    }

    public void setCities(List<JsonCity> cities) {
        this.cities = cities;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
