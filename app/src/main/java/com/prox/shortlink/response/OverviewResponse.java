package com.prox.shortlink.response;

import com.google.gson.annotations.SerializedName;
import com.prox.shortlink.OverviewData;

public class OverviewResponse {
    @SerializedName("data")
    private OverviewData data;

    public OverviewData getData() {
        return data;
    }
}
