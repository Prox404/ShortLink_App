package com.prox.shortlink;

import com.google.gson.annotations.SerializedName;

public class OverviewResponse {
    @SerializedName("data")
    private OverviewData data;

    public OverviewData getData() {
        return data;
    }
}
