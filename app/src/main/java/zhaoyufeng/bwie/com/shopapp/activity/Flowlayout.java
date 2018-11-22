package zhaoyufeng.bwie.com.shopapp.activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

public class Flowlayout extends RelativeLayout {
    private LinearLayout layout_v;
    private String result;

    public Flowlayout(Context context) {
        super(context);
        init(context);
    }

    public Flowlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Flowlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context; //创建一个垂直的线性布局
        View view = View.inflate(context, R.layout.layout_v,null);
        layout_v = view.findViewById(R.id.ll_layout_v); addView(view); }
        public void setList(final List<User> list){
        //清空布局里面的视图
            layout_v.removeAllViews();
            // 创建一个水平的线性布局
            LinearLayout layout_h = (LinearLayout) View.inflate(context,R.layout.layout_h,null);
            //添加到垂直线性布局中
            layout_v.addView(layout_h);
            layout_h.removeAllViews();
            int len=0;
            //循环遍历数据
            for (int i = 0; i < list.size(); i++) {
                //获取当前字符串的长度
                int length = list.get(i).getData().length();
                //字符串总长度
                len+=length;
                //当前水平线性布局字符串的总长度大于22时，重新创建一个水平的线性布局，并添加到垂直布局中
                if(len>22){
                    layout_h = (LinearLayout) View.inflate(context,R.layout.layout_h,null);
                layout_v.addView(layout_h);
                len=0;
                }
                // 创建TextView控件，赋值并添加到当前水平线性布局中
                View view = View.inflate(context, R.layout.layout_view, null);
                final TextView textView = view.findViewById(R.id.tv_text_view);
                textView.setText(list.get(i).getData()); layout_h.addView(view);
                //设置TextView父布局
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.weight=1;
                layoutParams.leftMargin=3;
                layoutParams.rightMargin=3;
                layoutParams.topMargin=3;
                layoutParams.bottomMargin=3;
                view.setLayoutParams(layoutParams);
                result = textView.getText().toString().trim();
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, SearchShowActivity.class);
                        intent.putExtra("title", result);
                        context.startActivity(intent);
                    }
                });
                textView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        List<User> users = UserDaoData.getGetDao().queryList("searchListString");
                        for (int i=0;i<users.size();i++) {
                            if(result.equals(users.get(i).getData())){
                                UserDaoData.getGetDao().deleteByUser(users.get(i));
                                listener.setList();
                            }
                        }
                        return true;
                    }
                });
            }
    }
    private FlowListener listener;
    public void result(FlowListener listener){
        this.listener=listener;
    }
    public interface FlowListener{
        void setList();
    }
}