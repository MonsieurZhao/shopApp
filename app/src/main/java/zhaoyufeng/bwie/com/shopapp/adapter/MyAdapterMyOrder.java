package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.AddaddressActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SetAddressActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanLogin;
import zhaoyufeng.bwie.com.shopapp.model.BeanOrder;
import zhaoyufeng.bwie.com.shopapp.model.BeanSetAddress;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

/**
 * author:AbnerMing
 * date:2018/10/22
 */
public class MyAdapterMyOrder extends RecyclerView.Adapter<MyAdapterMyOrder.MyViewHolderOrder>{
    private  List<BeanOrder.DataBean> list=new ArrayList<>();
    private Context context;
    public MyAdapterMyOrder(Context context ) {
        this.context=context;

    }
    public void setList(List<BeanOrder.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    private  SharedPreferences sp;

    @NonNull
    @Override
    public MyViewHolderOrder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.recyler_order_item,null);
        MyViewHolderOrder myViewHolder=new MyViewHolderOrder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderOrder myViewHolder, final int i) {
        myViewHolder.name.setText(list.get(i).getTitle());
        myViewHolder.price.setText(list.get(i).getPrice()+"");
        myViewHolder.orderid.setText(list.get(i).getOrderid()+"");
        myViewHolder.createtime.setText(list.get(i).getCreatetime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolderOrder extends RecyclerView.ViewHolder {

         TextView name,price,orderid,createtime;

        public MyViewHolderOrder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.text_order);
            price=(TextView)itemView.findViewById(R.id.price_order);
            orderid=(TextView)itemView.findViewById(R.id.orderid_order);
            createtime=(TextView)itemView.findViewById(R.id.createtime_order);
        }
    }
}
