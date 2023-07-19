package com.prox.shortlink;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Link implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("short_link")
    private String shortLink;

    @SerializedName("privacy")
    private String privacy;

    @SerializedName("watch")
    private int watch;

    @SerializedName("password")
    private String password;

    @SerializedName("auto_redirect")
    private boolean autoRedirect;

    @SerializedName("link")
    private String link;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Getter và Setter cho các thuộc tính
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutoRedirect() {
        return autoRedirect;
    }

    public void setAutoRedirect(boolean autoRedirect) {
        this.autoRedirect = autoRedirect;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Các phương thức khác (nếu cần)

    public String getDomainName(){
        try {
            URL uri = new URL(link);
            String domain = uri.getHost();
            if (domain != null && domain.startsWith("www.")) {
                domain = domain.substring(4);
            }
            return domain;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTimeAgo(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime dateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME);

        Duration duration = Duration.between(dateTime, currentTime);

        long years = duration.toDays() / 365;
        long months = duration.toDays() / 30;
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();

        if (years > 0) {
            return years + (years == 1 ? " year ago" : " years ago");
        } else if (months > 0) {
            return months + (months == 1 ? " month ago" : " months ago");
        } else if (days > 0) {
            return days + (days == 1 ? " day ago" : " days ago");
        } else if (hours > 0) {
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else if (minutes > 0) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else {
            return "a few seconds ago";
        }
    }
}
