package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.FristFragment;
import zhaoyufeng.bwie.com.shopapp.activity.ListFragment;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.activity.MyFragment;
import zhaoyufeng.bwie.com.shopapp.activity.ShopFragment;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;

public class MainActivityPresenter extends AppDelage implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView img1, img2, img3, img4;
    private List<Fragment> list = new ArrayList<>();
    private TextView text1, text2, text3, text4;
    private MyAdapterFragment myAdapterFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void resume() {

    }

    @Override
    public void restart() {

    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void initData() {
        super.initData();
        viewPager = get(R.id.view_main);
        init();
        myAdapterFragment = new MyAdapterFragment(((MainActivity) context).getSupportFragmentManager());
        viewPager.setAdapter(myAdapterFragment);
        shop();
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void shop() {
        Intent intent = ((MainActivity) context).getIntent();
        int shopById = intent.getIntExtra("shopById",0);
        if(shopById>0){
            setTab(shopById);
        }

    }

    private void init() {
        img1 = get(R.id.iv_img1_main);
        img2 = get(R.id.iv_img2_main);
        img3 = get(R.id.iv_img3_main);
        img4 = get(R.id.iv_img4_main);
        get(R.id.layout1).setOnClickListener(this);
        get(R.id.layout2).setOnClickListener(this);
        get(R.id.layout3).setOnClickListener(this);
        get(R.id.layout4).setOnClickListener(this);
        text1 = get(R.id.tv_text1_main);
        text2 = get(R.id.tv_text2_main);
        text3 = get(R.id.tv_text3_main);
        text4 = get(R.id.tv_text4_main);
        list.add(new FristFragment());
        list.add(new ListFragment());
        list.add(new ShopFragment());
        list.add(new MyFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout1:
               setTab(0);
                break;
            case R.id.layout2:
                setTab(1);
                break;
            case R.id.layout3:
                setTab(2);
                break;
            case R.id.layout4:
                setTab(3);
                break;
        }
    }

    private class MyAdapterFragment extends FragmentPagerAdapter {


        public MyAdapterFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
    public void setTab(int position){
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                img1.setImageResource(R.drawable.index_yes);
                img2.setImageResource(R.drawable.list_no);
                img3.setImageResource(R.drawable.car_no);
                img4.setImageResource(R.drawable.me_no);
                text1.setTextColor(context.getResources().getColor(R.color.colorAccent));
                text2.setTextColor(context.getResources().getColor(R.color.color0000));
                text3.setTextColor(context.getResources().getColor(R.color.color0000));
                text4.setTextColor(context.getResources().getColor(R.color.color0000));
                break;
            case 1:
                viewPager.setCurrentItem(1);
                img1.setImageResource(R.drawable.index_no);
                img2.setImageResource(R.drawable.list_yes);
                img3.setImageResource(R.drawable.car_no);
                img4.setImageResource(R.drawable.me_no);
                text1.setTextColor(context.getResources().getColor(R.color.color0000));
                text2.setTextColor(context.getResources().getColor(R.color.colorAccent));
                text3.setTextColor(context.getResources().getColor(R.color.color0000));
                text4.setTextColor(context.getResources().getColor(R.color.color0000));
                break;
            case 2:
                viewPager.setCurrentItem(2);
                img1.setImageResource(R.drawable.index_no);
                img2.setImageResource(R.drawable.list_no);
                img3.setImageResource(R.drawable.car_yes);
                img4.setImageResource(R.drawable.me_no);
                text1.setTextColor(context.getResources().getColor(R.color.color0000));
                text2.setTextColor(context.getResources().getColor(R.color.color0000));
                text3.setTextColor(context.getResources().getColor(R.color.colorAccent));
                text4.setTextColor(context.getResources().getColor(R.color.color0000));
                break;
            case 3:
                viewPager.setCurrentItem(3);
                img1.setImageResource(R.drawable.index_no);
                img2.setImageResource(R.drawable.list_no);
                img3.setImageResource(R.drawable.car_no);
                img4.setImageResource(R.drawable.me_yes);
                text1.setTextColor(context.getResources().getColor(R.color.color0000));
                text2.setTextColor(context.getResources().getColor(R.color.color0000));
                text3.setTextColor(context.getResources().getColor(R.color.color0000));
                text4.setTextColor(context.getResources().getColor(R.color.colorAccent));
                break;
        }
    }

}
