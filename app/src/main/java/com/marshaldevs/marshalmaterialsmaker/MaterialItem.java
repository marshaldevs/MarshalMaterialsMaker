package com.marshaldevs.marshalmaterialsmaker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialItem {

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("hashTags")
    private String tags;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("mainUrl")
    private String cannonicalUrl;

    @Expose
    @SerializedName("imageUrl")
    private String imageUrl;

    public MaterialItem() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCannonicalUrl() {
        return cannonicalUrl;
    }

    public void setCannonicalUrl(String cannonicalUrl) {
        this.cannonicalUrl = cannonicalUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
