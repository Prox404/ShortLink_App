package com.prox.shortlink;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("error")
    private ErrorData error;

    public ErrorData getError() {
        return error;
    }

    public class ErrorData {
        @SerializedName("name")
        private String name;

        @SerializedName("message")
        private String message;

        @SerializedName("expiredAt")
        private String expiredAt;

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }

        public String getExpiredAt() {
            return expiredAt;
        }
    }
}
