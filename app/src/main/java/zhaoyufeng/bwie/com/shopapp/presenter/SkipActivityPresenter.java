package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.FristActivity;
import zhaoyufeng.bwie.com.shopapp.activity.MainActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SkipActivity;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;

public class SkipActivityPresenter extends AppDelage {

    private TextView miao;
    private int m=3;
    private SharedPreferences sp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_skip;
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
        miao = get(R.id.miao_skip);

        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        handler.sendEmptyMessageDelayed(100,1000);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
                if(sp.getBoolean("isfrist",false)){
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    ((SkipActivity)context).finish();
                }else{
                    Intent intent = new Intent(context, FristActivity.class);
                    context.startActivity(intent);
                    ((SkipActivity)context).finish();
                }
            }
        },R.id.tiao_skip);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                m--;
                miao.setText(m+"s");
                if(m<=0){
                    handler.removeCallbacksAndMessages(null);
                    if(sp.getBoolean("isfrist",false)){
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((SkipActivity)context).finish();
                    }else{
                        Intent intent = new Intent(context, FristActivity.class);
                        context.startActivity(intent);
                        ((SkipActivity)context).finish();
                    }
                    return;
                }
                handler.sendEmptyMessageDelayed(100,1000);
            }
        }
    };

}
