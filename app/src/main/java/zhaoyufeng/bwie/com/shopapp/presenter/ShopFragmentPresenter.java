package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterShop;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.NetworkUtils;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

public class ShopFragmentPresenter extends AppDelage {

    private MyAdapterShop myAdapterShop;
    private ListView listView;
    private CheckBox checkBox;
    private TextView mPrice,mNum,mAll;
    private SharedPreferences sp;
    private List<BeanData.DataBean> data2=new ArrayList<>();
    private String uid;
    private String token;
    private TextView mEdit;
    private Boolean type=false;
    private LinearLayout layoutBuy;
    private LinearLayout layoutDel;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void resume() {
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }else{
            doHttp(uid, token);
        }
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
        listView = get(R.id.lv_list_shop);
        checkBox = get(R.id.checkbox_shop);
        mPrice = get(R.id.price_shop);
        mNum= get(R.id.num_shop);
        mAll= get(R.id.all_shop);
        layoutBuy = get(R.id.buyshop);
        layoutDel = get(R.id.delshop);
        mEdit = get(R.id.edit_shop);
        refreshLayout = get(R.id.swipe_refersh_shop);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        myAdapterShop = new MyAdapterShop(context);
        listView.setAdapter(myAdapterShop);
        uid = sp.getString("uid", "");
        token = sp.getString("token", "");
        if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }else{
            doHttp(uid, token);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    doHttp(uid, token);
                }
                refreshLayout.setRefreshing(false);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    allcheck();
                }

            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    delete();
                }

            }
        });

        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    delall();
                }
            }
        },R.id.del_shop);
        myAdapterShop.result(new MyAdapterShop.ShopListener() {
            @Override
            public void setShopListData(List<BeanData.DataBean> list) {
                int num1=0;
                int num2=0;
                int num=0;
                double price =0;
                for (int i = 0; i <list.size() ; i++) {
                    List<BeanData.DataBean.ListBean> list1 = list.get(i).getList();
                    for (int j=0;j<list1.size();j++){
                        num1++;
                        if(list1.get(j).isIschceked()){
                            num2++;
                            num+=list1.get(j).getNum();
                            price+=list1.get(j).getPrice()*list1.get(j).getNum();
                        }
                    }
                }
                if(num1>num2){
                    checkBox.setChecked(false);
                }else{
                    checkBox.setChecked(true);
                }
                if(!type){
                    if(num>0){
                        mPrice.setText("合计:"+price);
                        mNum.setText("去结算（"+num+"）");
                    }else{
                        mPrice.setText("合计:00.00");
                        mNum.setText("去结算（0）");
                    }
                }

                myAdapterShop.setList(list);
            }
        });

    }

    private void delall() {
        int num2=0;
        for (int i = 0; i <data2.size() ; i++) {
            List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).isIschceked()) {
                    num2++;
                }
            }
        }
        if(num2==0){
            Toast.makeText(context, "请选择商品", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除");
        builder.setMessage("你确定要删除这些商品嘛");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delData();
                doHttp(uid,token);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false).create();
        builder.show();

    }

    private void delData() {
        for (int i = 0; i <data2.size() ; i++) {
            List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
            for (int j=0;j<list.size();j++){
                if(list.get(j).isIschceked()){
                    int pid = list.get(j).getPid();
                    Map<String,String> map = new HashMap<>();
                    map.put("uid",uid);
                    map.put("pid",pid+"");
                    map.put("token",token);
                    new HttpUtil().get("/product/deleteCart",map).result(new HttpUtil.HttpRxListener() {
                        @Override
                        public void success(String data) {
                            Gson gson = new Gson();
                            BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                            String code = beanLogin.getCode();
                            if("0".equals(code)){
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void fail(String error) {

                        }
                    });

                }
            }
        }
    }

    private void alldelcheck() {
        for (int i = 0; i <data2.size() ; i++) {
            List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
            if(checkBox.isChecked()){
                data2.get(i).setIschceked(true);
            }else{
                data2.get(i).setIschceked(false);
            }
            for (int j=0;j<list.size();j++){
                if(checkBox.isChecked()){
                    list.get(j).setIschceked(true);

                }else{
                    list.get(j).setIschceked(false);
                }
            }
        }
        myAdapterShop.setList(data2);
    }

    private void delete() {
        String result = mEdit.getText().toString();
        type=!type;
        if(type){
            for (int i = 0; i <data2.size() ; i++) {
                data2.get(i).setIschceked(false);
                List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
                for (int j=0;j<list.size();j++){
                    list.get(j).setIschceked(false);
                }
                checkBox.setChecked(false);
            }
            myAdapterShop.setList(data2);
            mEdit.setText("完成");
            layoutBuy.setVisibility(View.GONE);
            layoutDel.setVisibility(View.VISIBLE);
        }else {
            mEdit.setText("编辑");
            layoutBuy.setVisibility(View.VISIBLE);
            layoutDel.setVisibility(View.GONE);
            allcheck();
        }
    }

    private void allcheck() {
        int num=0;
        double price=0;
        for (int i = 0; i <data2.size() ; i++) {
            List<BeanData.DataBean.ListBean> list = data2.get(i).getList();
            if(checkBox.isChecked()){
                data2.get(i).setIschceked(true);
            }else{
                data2.get(i).setIschceked(false);
            }
            for (int j=0;j<list.size();j++){
                if(checkBox.isChecked()){
                    list.get(j).setIschceked(true);
                    num+=list.get(j).getNum();
                    price+=list.get(j).getPrice()*list.get(j).getNum();
                }else{
                    list.get(j).setIschceked(false);
                }
            }
        }
        if(!type){
            if(num>0){
                mPrice.setText("合计:"+price);
                mNum.setText("去结算（"+num+"）");
            }else{
                mPrice.setText("合计:00.00");
                mNum.setText("去结算（0）");
            }
        }

        myAdapterShop.setList(data2);
    }

    private void doHttp(final String uid, final String token) {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            map.put("uid",uid);
            map.put("token",token);
            new HttpUtil().get("/product/getCarts",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttp(uid, token);
                        return;
                    }
                    if("null".equals(data)){
                        return;
                    }
                    User user = new User();
                    user.setType("shop");
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanData beanData = gson.fromJson(data, BeanData.class);

                    data2 = beanData.getData();
                    if(data2.size()==0){
                        return;
                    }
                    for (int i = 0; i < data2.size() ; i++) {
                        if(data2.get(i).getList().size()    ==0|| data2.get(i).getList()==null){
                            data2.remove(i);
                        }
                    }
                    myAdapterShop.setList(data2);
                }

                @Override
                public void fail(String error) {

                }
            });
        }else{
            User query = UserDaoData.getGetDao().query("shop");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanData beanData = gson.fromJson(data3, BeanData.class);
            List<BeanData.DataBean> data2 = beanData.getData();
            for (int i = 0; i <data2.size() ; i++) {
                if(data2.get(i).getList().size()==0||data2.get(i).getList()==null){
                    data2.remove(i);
                }
            }
            myAdapterShop.setList(data2);
        }

    }

//    @Override
//    public void init() {
////        doHttp(uid, token);
//    }
}
