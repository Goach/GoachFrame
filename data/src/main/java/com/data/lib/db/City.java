package com.data.lib.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */

public class City extends RealmObject {
    @PrimaryKey
    private String code;
    private String area;
    private String level;
    private String name;
    private String prefix;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
