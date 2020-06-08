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

    public static class Builder {
        private String tag;
        private int postCount;

        public Builder withTagName(String tagName) {
            this.tag = tagName;
            return this;
        }

        public Builder withPostCount(int postCount) {
            this.postCount = postCount;
            return this;
        }

        public TagCloudElement build() {
            return new TagCloudElement(tag, postCount);
        }
    }
}
