package cn.edu.gzy.qqdemo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class QQFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragments;

    public QQFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public QQFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> mFragments){
        super(fragmentActivity);
        this.mFragments = mFragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
