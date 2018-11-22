package zhaoyufeng.bwie.com.shopapp.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterShopList;

public class NumView extends RelativeLayout {

    private EditText mNum;

    public NumView(Context context) {
        super(context);
        init(context);
    }

    public NumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = View.inflate(context, R.layout.numview,null);
        mNum = view.findViewById(R.id.num_view);

        mNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result1 = editable.toString();
                int parseInt = Integer.parseInt(result1);

                if(listLeftListener!=null){
                    listLeftListener.setNumView(parseInt);
                }

            }

        });
        view.findViewById(R.id.add_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                listLeftListener.setNumView(num);
            }
        });
        view.findViewById(R.id.jian_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num>1){
                    num--;
                    listLeftListener.setNumView(num);
                }else{
                    Toast.makeText(context, "最少有一件商品", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addView(view);
    }


    private int num;
    public void setNum(int num) {
        this.num = num;
        mNum.setText(num+"");
    }

    private NumViewListener listLeftListener;
    public void result(NumViewListener listLeftListener){
        this.listLeftListener=listLeftListener;
    }
    public interface NumViewListener{
        void setNumView(int num);
    }
}
