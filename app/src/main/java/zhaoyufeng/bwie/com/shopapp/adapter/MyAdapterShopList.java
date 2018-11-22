package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.NumView;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtiles;

public class MyAdapterShopList extends RecyclerView.Adapter<MyAdapterShopList.MyViewHodlerDataList> {
    private Context context;
    private SharedPreferences sp;

    public MyAdapterShopList(Context context) {
        this.context = context;
    }

    private List<BeanData.DataBean.ListBean> list = new ArrayList<>();


    public void setList(List<BeanData.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHodlerDataList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyler_item_shop, null);
        MyViewHodlerDataList myViewHodler = new MyViewHodlerDataList(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerDataList holder, final int position) {
        holder.checkBox.setChecked(list.get(position).isIschceked());
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        holder.img.setImageURI(split[0]);
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("价格：￥:" + list.get(position).getPrice());
        holder.numView.setNum(list.get(position).getNum());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isIschceked()) {
                    list.get(position).setIschceked(false);
                } else {
                    list.get(position).setIschceked(true);
                }
                notifyItemChanged(position);
                listLeftListener.setShopList();

            }
        });

        holder.numView.result(new NumView.NumViewListener() {
            @Override
            public void setNumView(int num) {
                list.get(position).setNum(num);
                updateData(position);

                listLeftListener.setShopList();

            }
        });
    }

    private void updateData(int position) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String uid = sp.getString("uid", "");
        String token = sp.getString("token", "");
        int num1 = list.get(position).getNum();
        int sellerid = list.get(position).getSellerid();
        int pid = list.get(position).getPid();
        int selected = list.get(position).getSelected();
        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("sellerid",sellerid+"");
        map.put("pid",pid+"");
        map.put("num",num1+"");
        map.put("selected",selected+"");
        map.put("token",token);
        new HttpUtil().get("/product/updateCarts",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if(!"0".equals(code)){
                    return;
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHodlerDataList extends RecyclerView.ViewHolder {

        private SimpleDraweeView img;
        private TextView price, title;
        private CheckBox checkBox;
        private NumView numView;

        public MyViewHodlerDataList(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox_shop_item);
            img = itemView.findViewById(R.id.img_recy_shop);
            price = itemView.findViewById(R.id.price_recy_shop);
            title = itemView.findViewById(R.id.title_recy_shop);
            numView = itemView.findViewById(R.id.numview_shop);
        }
    }

    private ShopDataListener listLeftListener;

    public void result(ShopDataListener listLeftListener) {
        this.listLeftListener = listLeftListener;
    }

    public interface ShopDataListener {
        void setShopList();
    }
}
