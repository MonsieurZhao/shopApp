package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.InsertActivity;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

public class LoginActivityPresenter extends AppDelage {

    private EditText name;
    private EditText password;
    private Button login;
    private String mPassword;
    private String mName;
    private SharedPreferences sp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        name = get(R.id.et_name_login);
        password = get(R.id.et_password_login);
        login = get(R.id.bt_login_login);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        get(R.id.tv_insert_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InsertActivity.class);
                context.startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
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
        new HttpUtil().get("/user/login",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();

                if("0".equals(code)){
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    String token = beanLogin.getData().getToken();
                    String nickname = (String) beanLogin.getData().getNickname();
                    String icon = (String) beanLogin.getData().getIcon();
                    int uid = beanLogin.getData().getUid();
                    sp.edit().putBoolean("islogin",true).putString("name",mName)
                            .putString("password",mPassword).putString("uid",uid+"")
                            .putString("token",token).putString("nickname",nickname)
                            .putString("icon",icon).putString("age","").commit();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "账号或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
