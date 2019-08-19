package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dinghu on 2019/8/16.
 */
public class Developer {

    /**
     * tId : 2
     * tUuid : oo9zG1Dstn7eED5rNqbvz_V938Vd
     * name : 王五
     * nick : 王五
     * sex : false
     * workId : 22
     * nid : 511181199602181924
     * birth : 1562515200000
     * hiredate : 1562596663000
     * phone : 18081314730
     * invCode : 112233
     */

    @SerializedName("tId")
    private int tId;
    @SerializedName("tUuid")
    private String tUuid;
    @SerializedName("name")
    private String name;
    @SerializedName("nick")
    private String nick;
    @SerializedName("sex")
    private boolean sex;
    @SerializedName("workId")
    private String workId;
    @SerializedName("nid")
    private String nid;
    @SerializedName("birth")
    private long birth;
    @SerializedName("hiredate")
    private long hiredate;
    @SerializedName("phone")
    private String phone;
    @SerializedName("invCode")
    private String invCode;

    @SerializedName("email")
    private String email;
    @SerializedName("place")
    private String place;
    @SerializedName("lastTime")
    private long lastTime;

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String gettUuid() {
        return tUuid;
    }

    public void settUuid(String tUuid) {
        this.tUuid = tUuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public int getTId() {
        return tId;
    }

    public void setTId(int tId) {
        this.tId = tId;
    }

    public String getTUuid() {
        return tUuid;
    }

    public void setTUuid(String tUuid) {
        this.tUuid = tUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public long getHiredate() {
        return hiredate;
    }

    public void setHiredate(long hiredate) {
        this.hiredate = hiredate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }
}
