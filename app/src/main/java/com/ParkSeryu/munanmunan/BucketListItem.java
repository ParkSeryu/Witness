package com.ParkSeryu.munanmunan;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class BucketListItem {

    private String bucketListContent;
    private int bucketListclear;
    private Drawable bucketListModified;
    private Drawable bucketListDelete;

    public void setBucketListContent(String bucketListContent) {
        this.bucketListContent = bucketListContent;
    }

    public void setBucketListClear(int bucketListClear){
        if(bucketListClear == 1)
        this.bucketListclear = 16;
        else
            this.bucketListclear = 0;
        Log.d("test333", ""+bucketListclear);
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

    public int getBucketListclear() { return  this.bucketListclear; }

    public Drawable getBucketListModified() {
        return this.bucketListModified;
    }

    public Drawable getBucketListDelete() {
        return this.bucketListDelete;
    }
}
