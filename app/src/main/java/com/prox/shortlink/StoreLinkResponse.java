package com.prox.shortlink;

import com.google.gson.annotations.SerializedName;

public class StoreLinkResponse {
    @SerializedName("link")
    private Link link;

    @SerializedName("error")
    private String error;

    public Link getLink() {
        return link;
    }

    public String getError() {
        return error;
    }
}
