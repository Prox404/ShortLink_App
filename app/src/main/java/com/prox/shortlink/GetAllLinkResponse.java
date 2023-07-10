package com.prox.shortlink;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class GetAllLinkResponse {

    @SerializedName("data")
    private List<Link> links;

    @SerializedName("current")
    private int currentPage;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("total")
    private int totalLinks;

    //Getters and Setters

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
    }
}
