package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.SetNameAcitivity;
import zhaoyufeng.bwie.com.shopapp.activity.ShowActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtiles;

public class SetNameAcitivityPresenter extends AppDelage {

    private EditText text;
    private SharedPreferences sp;
    private String uid;
    private String token;
    private String result;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setname;
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
        text = get(R.id.set_name);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        token = sp.getString("token", "");
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SetNameAcitivity)context).finish();
            }
        },R.id.img_setname);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setname();
            }
        },R.id.confirm_setname);
    }

    private void setname() {
        result = text.getText().toString();
        if(TextUtils.isEmpty(result)){
            Toast.makeText(context, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Map<String,String> map = new HashMap<>();
            map.put("uid",uid);
            map.put("nickname",result);
            new HttpUtil().get("/user/updateNickName",map).result(new HttpUtil.HttpRxListener() {
                @Override
                public void success(String data) {
                    Gson gson = new Gson();
                    BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                    if(!"0".equals(beanLogin.getCode())){
                        return;
                    }
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    sp.edit().putString("nickname", result).commit();
                }

                @Override
                public void fail(String error) {

                }
            });
            new HttpUtiles().get("http://www.zhaoapi.cn/user/updateNickName?uid="+uid+"&nickname="+ result ).result(new HttpUtiles.HttpListener() {
                @Override
                public void success(String data) {

                }

                @Override
                public void fail(String error) {

                }
            });
        }
        Intent intent = new Intent(context, ShowActivity.class);
        context.startActivity(intent);
        ((SetNameAcitivity)context).finish();
    }

}
