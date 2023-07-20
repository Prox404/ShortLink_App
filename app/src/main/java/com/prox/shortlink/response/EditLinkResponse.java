package com.prox.shortlink.response;

import com.google.gson.annotations.SerializedName;
import com.prox.shortlink.Link;

public class EditLinkResponse {
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
