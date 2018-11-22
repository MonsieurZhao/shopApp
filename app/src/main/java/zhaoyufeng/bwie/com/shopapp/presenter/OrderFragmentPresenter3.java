package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterMyOrder;
import zhaoyufeng.bwie.com.shopapp.model.BeanOrder;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class OrderFragmentPresenter3 extends AppDelage {
    private RecyclerView recyclerView;
    private MyAdapterMyOrder myAdapterMyOrder;
    private SharedPreferences sp;
    private int page=1;
    private List<BeanOrder.DataBean> list = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void resume() {
        doHttp();
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
        recyclerView = get(R.id.recyler_order);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        myAdapterMyOrder = new MyAdapterMyOrder(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterMyOrder);
        doHttp();
    }

    private void doHttp() {
        Map<String,String> map = new HashMap<>();
        map.put("uid",sp.getString("uid", ""));
        map.put("page",page+"");
        new HttpUtil().get("/product/getOrders",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanOrder beanOrder = gson.fromJson(data, BeanOrder.class);
                List<BeanOrder.DataBean> data1 = beanOrder.getData();
                list.clear();
                for (int i = 0; i < data1.size(); i++) {
                    if(data1.get(i).getStatus()==2){
                        list.add(data1.get(i));
                    }
                }
                myAdapterMyOrder.setList(list);
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
