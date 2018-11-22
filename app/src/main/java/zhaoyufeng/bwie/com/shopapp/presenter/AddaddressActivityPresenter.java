package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.AddaddressActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SetAddressActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.model.BeanSetAddress;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class AddaddressActivityPresenter extends AppDelage {

    private EditText mName,mMobile,mAddress;
    private SharedPreferences sp;
    private String type;
    private Intent intent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_addaddress;
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
        mName = get(R.id.name_addaddress);
        mMobile = get(R.id.mobile_addaddress);
        mAddress = get(R.id.address_addaddress);
        intent = ((AddaddressActivity) context).getIntent();
        type = intent.getStringExtra("type");
        if("add".equals(type)){

        }else if("type".equals(type)){
            BeanSetAddress.DataBean list = (BeanSetAddress.DataBean) intent.getSerializableExtra("list");
            mName.setText(list.getName());
            mMobile.setText(list.getMobile());
            mAddress.setText(list.getAddr());
        }
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               news();
            }
        },R.id.preservation_add);
    }

    private void news() {
        String name = mName.getText().toString();
        String mobile = mMobile.getText().toString();
        String address = mAddress.getText().toString();
        if("add".equals(type)){
            Map<String,String> map = new HashMap<>();
            map.put("uid",sp.getString("uid",""));
            map.put("addr",address);
            map.put("mobile",mobile);
            map.put("name",name);
            map.put("token",sp.getString("token",""));
            new HttpUtil().get("/user/addAddr",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    Gson gson = new Gson();
                    BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                    String code = beanLogin.getCode();
                    if("0".equals(code)){
                        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void fail(String error) {

                }
            });
        }else if("update".equals(type)){
            BeanSetAddress.DataBean list = (BeanSetAddress.DataBean) intent.getSerializableExtra("list");
            Map<String,String> map = new HashMap<>();
            map.put("uid",sp.getString("uid",""));
            map.put("addrid",list.getAddrid()+"");
            map.put("addr",address);
            map.put("mobile",mobile);
            map.put("name",name);
            map.put("token",sp.getString("token",""));
            new HttpUtil().get("/user/updateAddr",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    Gson gson = new Gson();
                    BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                    String code = beanLogin.getCode();
                    if("0".equals(code)){
                        Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void fail(String error) {

                }
            });
        }

        Intent intent = new Intent(context, SetAddressActivity.class);
        context.startActivity(intent);
        ((AddaddressActivity)context).finish();
    }
}
