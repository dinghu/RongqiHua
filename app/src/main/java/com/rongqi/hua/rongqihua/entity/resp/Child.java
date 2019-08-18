package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinghu on 2019/8/16.
 */
public class Child {

    /**
     * major : 软件工程
     * phone : 18081314730
     * sex : false
     * name : 李四
     * nid : 511181199602181923
     * type : 学生
     */



    @SerializedName("major")
    private String major;
    @SerializedName("phone")
    private String phone;
    @SerializedName("sex")
    private boolean sex;
    @SerializedName("name")
    private String name;
    @SerializedName("nid")
    private String nid;
    @SerializedName("type")
    private String type;
    /**
     * graduate : string
     * sId : 0
     */

    private String graduate;
    private int sId;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }
}
