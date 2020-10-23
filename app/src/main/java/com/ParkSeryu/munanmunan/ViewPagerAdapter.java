package com.ParkSeryu.munanmunan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 각 포지션 값이 들어오면 미리 구현한 프래그먼트를 열어주는 함수.
        switch (position) {
            case 0:
                return new FragmentBucketList();
            case 1:
                return new FragmentMain();
        }
        return null;
    }

    @Override // 뷰 페이저 안에 들어가는 page의 갯수
    public int getCount() {
        return 2;
    }
}
