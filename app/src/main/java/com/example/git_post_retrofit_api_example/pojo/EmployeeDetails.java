package com.example.git_post_retrofit_api_example.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeeDetails implements Serializable {

    @SerializedName("FullName")
    String FullName = "";
    @SerializedName("Designation")
    String Designation = "";
    @SerializedName("UserGuid")
    String UserGuid = "";
    @SerializedName("Image")
    String Image = "";


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getUserGuid() {
        return UserGuid;
    }

    public void setUserGuid(String userGuid) {
        UserGuid = userGuid;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
