package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.LoginActivity;
import zhaoyufeng.bwie.com.shopapp.activity.ShowActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAadapterMy;
import zhaoyufeng.bwie.com.shopapp.model.BeanMy;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;

public class MyFragmentPresenter extends AppDelage {

    private SimpleDraweeView img;
    private TextView textView;
    private SharedPreferences sp;

    private List<BeanMy> listBean=new ArrayList<>();
    private RecyclerView relativeLayout1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void resume() {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        login();
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
        setData();
        img = get(R.id.img_my);
        textView = get(R.id.text_my);
        relativeLayout1 = get(R.id.recycler_my);
        MyAadapterMy accountAdapter=new MyAadapterMy(context,listBean);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        relativeLayout1.setLayoutManager(linearLayoutManager);
        relativeLayout1.setAdapter(accountAdapter);

        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        login();
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp.getBoolean("islogin",false)){
                    Intent intent = new Intent(context, ShowActivity.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        },R.id.img_my,R.id.text_my);
    }

    private void login() {
        if(sp.getBoolean("islogin",false)){
            String nickname = sp.getString("nickname", "");
            if(TextUtils.isEmpty(nickname)){
                String name = sp.getString("name", "");
                textView.setText(name);

            }else{
                textView.setText(nickname);
            }
            String icon = sp.getString("icon", "");
            if(TextUtils.isEmpty(icon)){
                img.setImageResource(R.mipmap.logo);
            }else{
                img.setImageURI(icon);
            }

        }else{
            textView.setText("请先登录");
        }
    }

    //设置数据
    private void setData() {
        BeanMy bean=new BeanMy();
        bean.setImage(R.drawable.car);
        bean.setName("物流");
        listBean.add(bean);
        bean=new BeanMy();
        bean.setImage(R.drawable.order);
        bean.setName("订单");
        listBean.add(bean);
        bean=new BeanMy();
        bean.setImage(R.drawable.quan);
        bean.setName("优惠券");
        listBean.add(bean);
        bean=new BeanMy();
        bean.setImage(R.drawable.shared);
        bean.setName("分享");
        listBean.add(bean);
        bean=new BeanMy();
        bean.setImage(R.drawable.feed_back);
        bean.setName("反馈");
        listBean.add(bean);
        bean=new BeanMy();
        bean.setImage(R.drawable.setting);
        bean.setName("设置");
        listBean.add(bean);
    }

}
