package cn.edu.gzy.qqdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.chapter02.R;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gzy.qqdemo.adapters.QQFragmentPagerAdapter;
import cn.edu.gzy.qqdemo.fragments.QQContactFragment;
import cn.edu.gzy.qqdemo.fragments.QQMessageFragment;

public class QQMainActivity extends FragmentActivity {
    private ViewPager2 vp2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqmain);
        vp2 = findViewById(R.id.viewPager);
        QQFragmentPagerAdapter adapter = new QQFragmentPagerAdapter(this,getFragmentsList());
        vp2.setAdapter(adapter);
        RadioButton rbMessage = findViewById(R.id.rbMessage);
        RadioButton rbContact = findViewById(R.id.rbContact);
        RadioButton rbPulgin = findViewById(R.id.rbPulgin);
        RadioGroup radioGroup = findViewById(R.id.radioGroup2);
        // ViewPage2 使用 registerOnPageChangeCallback 而不是像ViewPage 中使用 addOnPageChangeListener
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbMessage.setChecked(true);
                        break;
                    case 1:
                        rbContact.setChecked(true);
                        break;
                    case 2:
                        rbPulgin.setChecked(true);
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        radioGroup.setOnCheckedChangeListener((group,checkId)->{
            switch (checkId){
                case R.id.rbMessage:
                    vp2.setCurrentItem(0);
                    break;
                case R.id.rbContact:
                    vp2.setCurrentItem(1);
                    break;
                case R.id.rbPulgin:
                    vp2.setCurrentItem(2);
                    break;
                default:
                    break;
            }
        });
    }

    private List<Fragment> getFragmentsList() {
        QQMessageFragment msgFragment = new QQMessageFragment();
        QQContactFragment contactFragment = new QQContactFragment();
        List<Fragment> data = new ArrayList<Fragment>();
        data.add(msgFragment);
        data.add(contactFragment);
        return data;
    }
}
