package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;

public class FristActivityPresenter extends AppDelage {

    private ViewPager viewPager;
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();
    private Button tiyan;
    private SharedPreferences sp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_frist;
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

    @Override
    public void initData() {
        super.initData();
        viewPager = get(R.id.view_frist);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isfrist",true).commit();
        addlist();

        MyAdapterFrist myAdapterFrist = new MyAdapterFrist();
        viewPager.setAdapter(myAdapterFrist);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(list1.size()-1==position){
                    tiyan.setVisibility(View.VISIBLE);
                    tiyan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }else{
                    tiyan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void addlist() {
        list1.add(R.drawable.welcome1_top);
        list1.add(R.drawable.welcome2_top);
        list1.add(R.drawable.welcome3_top);
        list2.add(R.drawable.welcome1_buttom);
        list2.add(R.drawable.welcome2_buttom);
        list2.add(R.drawable.welcome3_bottom_bg);
    }

    public class MyAdapterFrist extends PagerAdapter {

        @Override
        public int getCount() {
            return list1.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context,R.layout.activity_frist1,null);
            ImageView img_top =view.findViewById(R.id.img_top_frist);
            ImageView img_bottom =view.findViewById(R.id.img_bottom_frist);
            tiyan = view.findViewById(R.id.tiyan_frist);
            img_top.setImageResource(list1.get(position));
            img_bottom.setImageResource(list2.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
