package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.SearchShowActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterSearch;
import zhaoyufeng.bwie.com.shopapp.model.BeanSearch;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.NetworkUtils;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

public class SearchShowActivityPresenter extends AppDelage {

    private RecyclerView recyclerView;
    private MyAdapterSearch myAdapterSearch;

    @Override
    public int getLayoutId() {
        return R.layout.search_show;
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
        recyclerView = get(R.id.recyler_search_show);
        Intent intent = ((SearchShowActivity) context).getIntent();
        String title = intent.getStringExtra("title");
        String pscid = intent.getStringExtra("pscid");
        myAdapterSearch = new MyAdapterSearch(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterSearch);
        if(!TextUtils.isEmpty(title)){
            doHttpSearch(title);
        }
        if(!TextUtils.isEmpty(pscid)){
            doHttpShow(pscid);
        }
    }

    private void doHttpShow(final String pscid) {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            map.put("pscid",pscid);
            new HttpUtil().get("/product/getProducts",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttpSearch(pscid);
                        return;
                    }
                    if(data.length()==0||data==""){
                        Toast.makeText(context, "没有该类商品信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = new User();
                    user.setType("listshow");
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanSearch beanData = gson.fromJson(data, BeanSearch.class);
                    List<BeanSearch.DataBean> data2 = beanData.getData();
                    myAdapterSearch.setList(data2);
                }

                @Override
                public void fail(String error) {

                }
            });


        }else{
            User query = UserDaoData.getGetDao().query("listshow");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanSearch beanData = gson.fromJson(data3, BeanSearch.class);
            List<BeanSearch.DataBean> data2 = beanData.getData();
            myAdapterSearch.setList(data2);
        }
    }

    private void doHttpSearch(final String result) {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            map.put("keywords",result);
            new HttpUtil().get("/product/searchProducts",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        doHttpSearch(result);
                        return;
                    }
                    if(data.length()==0||data==""){
                        Toast.makeText(context, "没有该类商品信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = new User();
                    user.setType("search");
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanSearch beanData = gson.fromJson(data, BeanSearch.class);
                    List<BeanSearch.DataBean> data2 = beanData.getData();
                    myAdapterSearch.setList(data2);
                }

                @Override
                public void fail(String error) {

                }
            });

        }else{
            User query = UserDaoData.getGetDao().query("search");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanSearch beanData = gson.fromJson(data3, BeanSearch.class);
            List<BeanSearch.DataBean> data2 = beanData.getData();
            myAdapterSearch.setList(data2);
        }
    }
}
