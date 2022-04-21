package com.armoomragames.denketa.Utils;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class RModel_onFailureError {


    private int code;

    private String status;

    private String message;

    private String data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return this.data;
    }
}
