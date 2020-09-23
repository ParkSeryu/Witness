package com.example.munanmunan;

import android.graphics.drawable.Drawable;

public class BucketListViewItem {

    private String bucketListContent;
    private Drawable bucketListModified;
    private Drawable bucketListDelete;

    public void setBucketListContent(String bucketListContent) {
        this.bucketListContent = bucketListContent;
    }

    public void setBucketListModified(Drawable bucketListModified) {
        this.bucketListModified = bucketListModified;
    }

    public void setBucketListDelete(Drawable bucketListDelete) {
        this.bucketListDelete = bucketListDelete;
    }

    public String getBucketListContent() {
        return this.bucketListContent;
    }

    public Drawable getBucketListModified() {
        return this.bucketListModified;
    }

    public Drawable getBucketListDelete() {
        return this.bucketListDelete;
    }
}
