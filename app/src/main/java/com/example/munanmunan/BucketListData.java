package com.example.munanmunan;

public class BucketListData {
    private String remainDay;
    private String whenDay;
    private String dDay;

    public BucketListData(String remainDay, String whenDay, String dDay){
        this.remainDay = remainDay;
        this.whenDay = whenDay;
        this.dDay = dDay;
    }

    public String getRemainDay()
    {
        return this.remainDay;
    }

    public String getWhenDay()
    {
        return this.whenDay;
    }

    public String getdDay()
    {
        return this.dDay;
    }
}
