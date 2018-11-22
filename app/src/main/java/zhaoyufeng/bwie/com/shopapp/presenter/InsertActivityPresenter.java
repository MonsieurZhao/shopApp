package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.LoginActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class InsertActivityPresenter extends AppDelage {
    private Button insert;
    private EditText password,name;
    private String mName;
    private String mPassword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_insert;
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
        name = get(R.id.et_name_insert);
        password = get(R.id.et_password_insert);
        insert = get(R.id.bt_insert_insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dologin();
            }
        });


    }

    private void dologin() {
        mName = name.getText().toString().trim();
        mPassword = this.password.getText().toString();
        if(TextUtils.isEmpty(mName)||TextUtils.isEmpty(mPassword)){
            Toast.makeText(context, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("mobile",mName);
        map.put("password",mPassword);
        new HttpUtil().get("/user/reg",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if("0".equals(code)){
                    Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "该账号已存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }
}
