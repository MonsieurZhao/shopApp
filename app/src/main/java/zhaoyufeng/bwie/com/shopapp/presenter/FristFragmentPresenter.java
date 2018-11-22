package zhaoyufeng.bwie.com.shopapp.presenter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SearchActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterData;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterJiu;
import zhaoyufeng.bwie.com.shopapp.model.BeanBanner;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanJiu;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.NetworkUtils;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

import static android.app.Activity.RESULT_OK;

public class FristFragmentPresenter extends AppDelage {
    private ViewPager viewPager,viewPager2;
    private RecyclerView listView;
    private MyAdapterBanner myAdapterBanner;
    private List<BeanBanner.DataBean> data1=new ArrayList<>();
    private List<BeanJiu.DataBean> list1=new ArrayList<>();
    private List<BeanJiu.DataBean> list2=new ArrayList<>();
    private int num=1;
    private MyAdapterData myAdapterData;
    private BeanData beanData;
    private User user;
    private LinearLayout linearLayout;
    private TextView pao,textBanner;
    private List<String> paos=new ArrayList<>();
    int positionPao=0;
    private AnimatorSet animatorSet;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout layout_banner;
    private TextView mMiaoshaTimeTv,mMiaoshaShiTv,mMiaoshaMinterTv,mMiaoshaSecondTv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_frist;
    }

    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        viewPager = get(R.id.vp_view_frist);
        viewPager2 = get(R.id.vp_view2_frist);
        listView = get(R.id.lv_list_frist);
        textBanner = get(R.id.text_frist_banner);
        linearLayout = get(R.id.linearLayou1);
        layout_banner = get(R.id.layout_banner);
        pao = get(R.id.pao_frist);
        mMiaoshaShiTv = get(R.id.tv_miaosha_shi);
        mMiaoshaTimeTv = get(R.id.tv_miaosha_time);
        mMiaoshaMinterTv = get(R.id.tv_miaosha_minter);
        mMiaoshaSecondTv = get(R.id.tv_miaosha_second);
        refreshLayout = get(R.id.refresh);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
            }
        },R.id.title_frist);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaptureActivity.class);
                ((MainActivity)context).startActivityForResult(intent,1001);
            }
        },R.id.scan_frist);

        UserDaoData.getGetDao().init(context);
        doHttpBanner();
        doHttpJiu();
        handler1.sendEmptyMessage(0);
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                NewImage(position%data1.size());
                textBanner.setText(data1.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myAdapterData = new MyAdapterData(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(myAdapterData);
        doHttpData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doHttpData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void doPao() {
        for (int i = 0; i <data1.size() ; i++) {
            String title = data1.get(i).getTitle();
            paos.add(title);
        }
        pao.setText(paos.get(positionPao));
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(pao, "translationY", 0,0);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(pao, "translationY", 0,-50);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(pao, "translationY", 50,0);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator1,objectAnimator2,objectAnimator3);
        animatorSet.setDuration(2000);
        animatorSet.start();
        objectAnimator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                positionPao++;
                positionPao=positionPao%paos.size();
                pao.setText(paos.get(positionPao));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animatorSet.setDuration(2000);
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void NewImage(int position) {
        linearLayout.removeAllViews();
        for (int i = 0; i < num; i++) {
            ImageView imageView = new ImageView(context);
            if (position == i) {
                imageView.setImageResource(R.drawable.start_time);
            } else {
                imageView.setImageResource(R.drawable.start_time_bai);
            }
            linearLayout.addView(imageView);
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            ll.width = 15;
            ll.height = 15;
            ll.leftMargin = 8;
            imageView.setLayoutParams(ll);
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void restart() {

    }

    private void doHttpData() {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            map.put("uid","71");
            map.put("source","android");
            new HttpUtil().get("/product/getCarts",map).result(new HttpUtil.HttpRxListener() {

                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttpData();
                        return;
                    }
                    user = new User();
                    user.setType("data");
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    beanData = gson.fromJson(data, BeanData.class);
                    List<BeanData.DataBean> data2 = beanData.getData();
                    for (int i = 0; i <data2.size() ; i++) {
                        List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
                        if(list.size()==0||list==null){
                            Log.i("aaa",i+"=");
                            data2.remove(i);
                        }
                    }
                    myAdapterData.setList(data2);
                }

                @Override
                public void fail(String error) {

                }
            });
        }else{
            User query = UserDaoData.getGetDao().query("data");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            beanData = gson.fromJson(data3, BeanData.class);
            List<BeanData.DataBean> data2 = beanData.getData();
            for (int i = 0; i <data2.size() ; i++) {
                if(data2.get(i).getList().size()==0||data2.get(i).getList()==null){
                    data2.remove(i);
                }
            }
            myAdapterData.setList(data2);
        }

    }

    private void doHttpJiu() {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            new HttpUtil().get("/product/getCatagory",map).result(new HttpUtil.HttpRxListener() {
                private List<BeanJiu.DataBean> data2;
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttpJiu();
                        return;
                    }
                    User user = new User();
                    user.setType("jiu");
                    user.setData(data);
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanJiu beanJiu = gson.fromJson(data, BeanJiu.class);

                    data2 = beanJiu.getData();
                    for (int i = 0; i <data2.size() ; i++) {
                        if(i<10){
                            list1.add(data2.get(i));
                        }else{
                            list2.add(data2.get(i));
                        }
                    }
                    if(list2.size()!=0){
                        num++;
                    }
                    MyAdapterJiuVp myAdapterJiuVp = new MyAdapterJiuVp();
                    viewPager2.setAdapter(myAdapterJiuVp);
                    NewImage(0);
                }

                @Override
                public void fail(String error) {

                }
            });

        }else{
            User query = UserDaoData.getGetDao().query("jiu");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanJiu beanJiu = gson.fromJson(data3, BeanJiu.class);

            List<BeanJiu.DataBean> data2 = beanJiu.getData();
            for (int i = 0; i <data2.size() ; i++) {
                if(i<10){
                    list1.add(data2.get(i));
                }else{
                    list2.add(data2.get(i));
                }
            }
            if(list2.size()!=0){
                num++;
            }
            MyAdapterJiuVp myAdapterJiuVp = new MyAdapterJiuVp();
            viewPager2.setAdapter(myAdapterJiuVp);

        }

    }



    private void doHttpBanner() {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            new HttpUtil().get("/ad/getAd",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttpBanner();
                        return;
                    }

                    User user = new User();
                    user.setData(data);
                    user.setType("banner");
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanBanner beanBanner = gson.fromJson(data, BeanBanner.class);
                    data1 = beanBanner.getData();
                    NewImage(data1.size()*10000%data1.size());
                    handler.sendEmptyMessage(1000);
                    myAdapterBanner = new MyAdapterBanner();
                    viewPager.setAdapter(myAdapterBanner);
                    myAdapterBanner.notifyDataSetChanged();
                    viewPager.setCurrentItem(data1.size()*10000);
//                    handler.sendEmptyMessageDelayed(1000,3000);
                    doPao();
                }

                @Override
                public void fail(String error) {

                }
            });

        }else{
            User query = UserDaoData.getGetDao().query("banner");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanBanner beanBanner = gson.fromJson(data3, BeanBanner.class);
            data1 = beanBanner.getData();
            myAdapterBanner = new MyAdapterBanner();
            viewPager.setAdapter(myAdapterBanner);
            myAdapterBanner.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(1000,3000);
            doPao();
        }

    }
    private class MyAdapterJiuVp extends PagerAdapter {

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context,R.layout.jiu_grid,null);
            RecyclerView recyclerView = view.findViewById(R.id.recyler_jiu);
            createAdapter(recyclerView,position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void createAdapter(RecyclerView recyclerView, int position) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,5);
        recyclerView.setLayoutManager(gridLayoutManager);
        //适配器
        MyAdapterJiu myAdapterJiu=null;
        if(position==0){
            myAdapterJiu = new MyAdapterJiu(context);
            recyclerView.setAdapter(myAdapterJiu);
            myAdapterJiu.setList(list1);
        }else{
             myAdapterJiu = new MyAdapterJiu(context);
            recyclerView.setAdapter(myAdapterJiu);
            myAdapterJiu.setList(list2);
        }
    }

    private class MyAdapterBanner extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context,R.layout.banner_view,null);
            SimpleDraweeView imageView=view.findViewById(R.id.banner_img);
            imageView.setImageURI(data1.get(position%data1.size()).getIcon());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    //使用handler用于更新UI
    private Handler handler1 = new Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countDown();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    /**
     * 秒杀
     */ private void countDown() {
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date curDate = new Date(System.currentTimeMillis());
         String format = df.format(curDate);
         StringBuffer buffer = new StringBuffer();
         String substring = format.substring(0, 11);
         buffer.append(substring);
         Log.d("ccc", substring);
         Calendar calendar = Calendar.getInstance();
         int hour = calendar.get(Calendar.HOUR_OF_DAY);
         if (hour % 2 == 0) {
             mMiaoshaTimeTv.setText(hour + "点场");
         buffer.append((hour + 2)); buffer.append(":00:00");
         } else {
             mMiaoshaTimeTv.setText((hour - 1) + "点场");
             buffer.append((hour + 1));
             buffer.append(":00:00");
         }
         String totime = buffer.toString();
         try {
             java.util.Date date = df.parse(totime);
             java.util.Date date1 = df.parse(format);
             long defferenttime = date.getTime() - date1.getTime();
             long days = defferenttime / (1000 * 60 * 60 * 24);
             long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
             long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
             long seconds = defferenttime % 60000; long second = Math.round((float) seconds / 1000);
             mMiaoshaShiTv.setText("0" + hours + ""); if (minute >= 10) { mMiaoshaMinterTv.setText(minute + "");
             } else { mMiaoshaMinterTv.setText("0" + minute + "");
             } if (second >= 10) {
                 mMiaoshaSecondTv.setText(second + "");
             } else {
                 mMiaoshaSecondTv.setText("0" + second + "");
             }
         } catch (ParseException e) {

             e.printStackTrace(); }
    }
    private Handler handler = new Handler(){

        private int currentItem;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1000){
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                handler.sendEmptyMessageDelayed(1000,3000);
            }
        }
    };

}
