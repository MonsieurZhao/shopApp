package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.AddaddressActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SetAddressActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterAddress;
import zhaoyufeng.bwie.com.shopapp.model.BeanSetAddress;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class SetAddressActivityPresenter extends AppDelage {

    private RecyclerView recyclerView;
    private MyAdapterAddress myAdapterAddress;
    private SharedPreferences sp;

    @Override
    public int getLayoutId() {
        return R.layout.acitvity_setaddress;
    }

    @Override
    public void resume() {
        dohttp();
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
        recyclerView = get(R.id.recyler_setaddress);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                news();
            }
        },R.id.new_address);
        myAdapterAddress = new MyAdapterAddress(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapterAddress);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        dohttp();
        myAdapterAddress.result(new MyAdapterAddress.SetAddressListener() {
            @Override
            public void setAddress() {
                dohttp();
            }
        });
    }

    private void news() {
        Intent intent = new Intent(context, AddaddressActivity.class);
        intent.putExtra("type","add");
        context.startActivity(intent);
        ((SetAddressActivity)context).finish();
    }

    private void dohttp() {
        Map<String,String> map = new HashMap<>();
        map.put("uid",sp.getString("uid",""));
        map.put("token",sp.getString("token",""));
        new HttpUtil().get("/user/getAddrs",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanSetAddress beanAddress = gson.fromJson(data, BeanSetAddress.class);
                String code = beanAddress.getCode();

                if("0".equals(code)){
                    List<BeanSetAddress.DataBean> data1 = beanAddress.getData();

                    if(data1.size()==0){
                        return;
                    }else{
                        myAdapterAddress.setList(data1);
                    }

                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
