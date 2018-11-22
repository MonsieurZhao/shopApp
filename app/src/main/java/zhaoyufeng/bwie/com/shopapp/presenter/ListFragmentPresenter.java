package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.SearchActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterListLeft;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterListRight;
import zhaoyufeng.bwie.com.shopapp.model.BeanListLeft;
import zhaoyufeng.bwie.com.shopapp.model.BeanListRight;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.NetworkUtils;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

public class ListFragmentPresenter extends AppDelage {

    private RecyclerView recylerLeft,recylerRight;
    private MyAdapterListRight myAdapterListRight;
    private MyAdapterListLeft myAdapterListLeft;
    private List<BeanListLeft.DataBean> data2=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
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
        recylerLeft = get(R.id.recyler_left);
        recylerRight = get(R.id.recyler_right);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
            }
        },R.id.title_list);
        myAdapterListLeft = new MyAdapterListLeft(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerLeft.setLayoutManager(manager);
        recylerLeft.setAdapter(myAdapterListLeft);
        ListLeft();
        myAdapterListRight = new MyAdapterListRight(context);
        LinearLayoutManager manager1 = new LinearLayoutManager(context);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recylerRight.setLayoutManager(manager1);
        recylerRight.setAdapter(myAdapterListRight);
        myAdapterListLeft.result(new MyAdapterListLeft.ListLeftListener() {
            @Override
            public void setId(int id) {
                ListRight(id);
            }

            @Override
            public void setposition(int position) {
                listColor(position);
            }
        });
    }

    private void listColor(int position) {
        for (int i = 0; i <data2.size() ; i++) {
            if(i==position){
                data2.get(i).setIschceked(true);
            }else{
                data2.get(i).setIschceked(false);
            }
        }
        myAdapterListLeft.setList(data2);
    }

    private void ListLeft() {

        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            new HttpUtil().get("/product/getCatagory",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        ListLeft();
                    }
                    User user = new User();
                    user.setType("ListLeft");
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanListLeft beanData = gson.fromJson(data, BeanListLeft.class);
                    data2 = beanData.getData();
                    myAdapterListLeft.setList(data2);
                    data2.get(0).setIschceked(true);
                    int cid = data2.get(0).getCid();
                    ListRight(cid);
                }

                @Override
                public void fail(String error) {

                }
            });

        }else{
            User query = UserDaoData.getGetDao().query("ListLeft");
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanListLeft beanData = gson.fromJson(data3, BeanListLeft.class);
            List<BeanListLeft.DataBean> data2 = beanData.getData();
            myAdapterListLeft.setList(data2);


        }

    }

    private void ListRight(final int id) {
        if(NetworkUtils.isConnected(context)){
            Map<String,String> map = new HashMap<>();
            map.put("cid",id+"");
            new HttpUtil().get("/product/getProductCatagory",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    if(data.contains("<")){
                        ListRight(id);
                    }
                    User user = new User();
                    user.setType("ListRight"+id);
                    user.setData(data.toString());
                    UserDaoData.getGetDao().addUser(user);
                    Gson gson = new Gson();
                    BeanListRight beanData = gson.fromJson(data, BeanListRight.class);
                    List<BeanListRight.DataBean> data2 = beanData.getData();
                    myAdapterListRight.setList(data2);
                }

                @Override
                public void fail(String error) {

                }
            });


        }else{
            User query = UserDaoData.getGetDao().query("ListRight"+id);
            if(query==null){
                return;
            }
            String data3 = query.getData();
            Gson gson = new Gson();
            BeanListRight beanData = gson.fromJson(data3, BeanListRight.class);
            List<BeanListRight.DataBean> data2 = beanData.getData();
            myAdapterListRight.setList(data2);
        }

    }
}
