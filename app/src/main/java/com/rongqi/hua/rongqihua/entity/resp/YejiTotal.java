package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dinghu on 2019/8/20.
 */
public class YejiTotal {

    /**
     * sub : []
     * phone :
     * sex : false
     * name : 周柳依
     * nid : 412721199608135809
     * place :
     * type : 合伙人
     * workId : 600134883
     * sup : 徐炳华
     */

    @SerializedName("phone")
    private String phone;
    @SerializedName("sex")
    private boolean sex;
    @SerializedName("name")
    private String name;
    @SerializedName("nid")
    private String nid;
    @SerializedName("place")
    private String place;
    @SerializedName("type")
    private String type;
    @SerializedName("workId")
    private String workId;
    @SerializedName("sup")
    private String sup;
    @SerializedName("major")
    private String major;
    @SerializedName("graduate")
    private String graduate;

    private List<YejiTotal> sub;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public List<YejiTotal> getSub() {
        return sub;
    }

    public void setSub(List<YejiTotal> sub) {
        this.sub = sub;
    }
}
