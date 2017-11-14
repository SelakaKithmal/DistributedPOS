package com.distributedpos.app.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError extends Throwable {
    @SerializedName("code")
    @Expose
    private String errorCode;
    @SerializedName("message")
    @Expose
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}