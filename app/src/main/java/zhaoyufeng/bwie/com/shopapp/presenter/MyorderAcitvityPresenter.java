package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MyorderAcitvity;
import zhaoyufeng.bwie.com.shopapp.activity.OrderFragment;
import zhaoyufeng.bwie.com.shopapp.activity.OrderFragment1;
import zhaoyufeng.bwie.com.shopapp.activity.OrderFragment2;
import zhaoyufeng.bwie.com.shopapp.activity.OrderFragment3;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;

public class MyorderAcitvityPresenter extends AppDelage {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myorder;
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
        this.context=context;
    }
    private String[] strings = {"全部","待支付","已支付","已取消"};
    private List<Fragment> list = new ArrayList<>();
    @Override
    public void initData() {
        super.initData();
        tabLayout = get(R.id.tab_order);
        viewPager = get(R.id.view_order);
        list.add(new OrderFragment());
        list.add(new OrderFragment1());
        list.add(new OrderFragment2());
        list.add(new OrderFragment3());
        MyAdapterOrder myAdapterOrder = new MyAdapterOrder(((MyorderAcitvity) context).getSupportFragmentManager());
        viewPager.setAdapter(myAdapterOrder);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

    }
    private class MyAdapterOrder extends FragmentPagerAdapter{

        public MyAdapterOrder(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = list.get(i);
            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
