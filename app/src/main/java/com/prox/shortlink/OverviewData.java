package com.prox.shortlink;

import com.google.gson.annotations.SerializedName;

public class OverviewData {
    @SerializedName("numberLinks")
    private int numberLinks;

    @SerializedName("numberUser")
    private int numberUser;

    @SerializedName("numberLinkOfUser")
    private int numberLinkOfUser;

    public int getNumberLinks() {
        return numberLinks;
    }

    public int getNumberUser() {
        return numberUser;
    }

    public int getNumberLinkOfUser() {
        return numberLinkOfUser;
    }
}
