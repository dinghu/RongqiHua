package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinghu on 2019/8/16.
 */
public class NewsItem {

    /**
     * teacherName : 陈光绪
     * studentName : 周柳依
     * insertDate : Fri Aug 16 11:18:55 CST 2019
     * type : 2
     */

    @SerializedName("teacherName")
    private String teacherName;
    @SerializedName("studentName")
    private String studentName;
    @SerializedName("insertDate")
    private String insertDate;
    @SerializedName("type")
    private int type;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
