package com.leverx.model;

public class TagCloudElement {
    private String tag;
    private long postCount;

    public TagCloudElement(String tag, long postCount) {
        this.tag = tag;
        this.postCount = postCount;
    }

    public String getTag() {
        return tag;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }

    public static class Builder {
        private String tag;
        private long postCount;

        public Builder withTagName(String tagName) {
            this.tag = tagName;
            return this;
        }

        public Builder withPostCount(long postCount) {
            this.postCount = postCount;
            return this;
        }

        public TagCloudElement build() {
            return new TagCloudElement(tag, postCount);
        }
    }
}
