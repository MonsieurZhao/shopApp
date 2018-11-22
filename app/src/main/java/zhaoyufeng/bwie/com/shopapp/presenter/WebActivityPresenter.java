package zhaoyufeng.bwie.com.shopapp.presenter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.activity.WebActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.model.BeanSearch;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class WebActivityPresenter extends AppDelage {

    private WebView webView;
    private BeanData.DataBean.ListBean list;
    private SharedPreferences sp;
    private BeanSearch.DataBean search;
    private String url;
    private int pid;
    private RelativeLayout layout;
    private SimpleDraweeView img;
    private TextView price,title;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
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
        //获取控件
        webView = get(R.id.web);
        layout = get(R.id.layout_web);
        img = get(R.id.img_web_layout);
        title = get(R.id.title_web_layout);
        price = get(R.id.price_web_layout);
        //获取跳转过来传来的数据
        Intent intent = ((WebActivity) context).getIntent();
        list = (BeanData.DataBean.ListBean) intent.getSerializableExtra("list");
        search = (BeanSearch.DataBean) intent.getSerializableExtra("search");
        if(list!=null){
            url = list.getDetailUrl();
            pid = list.getPid();
            String images = list.getImages();
            String[] split = images.split("\\|");
            img.setImageURI(split[0]);
            title.setText(list.getTitle());
            price.setText(list.getPrice()+"");
        }
        if(search!=null){
            url = search.getDetailUrl();
            pid = search.getPid();
            String images = search.getImages();
            String[] split = images.split("\\|");
            img.setImageURI(split[0]);
            title.setText(search.getTitle());
            price.setText(search.getPrice()+"");
        }
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintAnimator();
            }
        },R.id.close_web);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = sp.getString("uid", "");
                String token = sp.getString("token", "");
                if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();

                }else{
                    addShop(uid,pid,token);


                }
                hintAnimator();
            }
        },R.id.add_web_layout);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("shopById",2);
                context.startActivity(intent);

            }
        },R.id.shop_web);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showlayout();
            }
        },R.id.addshop_web);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        },R.id.buy_web);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        doData();

    }

    private void hintAnimator() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(layout, "translationY", 0, layout.getMeasuredHeight());
        translationY.setDuration(1000);
        translationY.start();
        translationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void showlayout( ) {

        ObjectAnimator translationY = ObjectAnimator.ofFloat(layout, "translationY", layout.getMeasuredHeight(), 0);
        translationY.setDuration(1000);
        translationY.start();
        layout.setVisibility(View.VISIBLE);

    }

    private void addShop(String uid,int pid,String token) {
        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("pid",pid+"");
        map.put("token",token);
        new HttpUtil().get("/product/addCart",map).result(new HttpUtil.HttpRxListener() {           @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if("0".equals(code)){
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    private void doData() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//支持插件
//        webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    }
}