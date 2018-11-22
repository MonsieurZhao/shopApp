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
import zhaoyufeng.bwie.com.shopapp.model.BeanSetAddress;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;

/**
 * author:AbnerMing
 * date:2018/10/22
 */
public class MyAdapterAddress extends RecyclerView.Adapter<MyAdapterAddress.MyViewHolderAddress>{
    private  List<BeanSetAddress.DataBean> list=new ArrayList<>();
    private Context context;
    public MyAdapterAddress(Context context ) {
        this.context=context;

    }
    public void setList(List<BeanSetAddress.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    private  SharedPreferences sp;

    @NonNull
    @Override
    public MyViewHolderAddress onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.recyler_setaddress_item,null);
        MyViewHolderAddress myViewHolder=new MyViewHolderAddress(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAddress myViewHolder, final int i) {
        myViewHolder.name.setText(list.get(i).getName());
        myViewHolder.mobile.setText(list.get(i).getMobile());
        myViewHolder.address.setText(list.get(i).getAddr());
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        if(list.get(i).getStatus()==1){
            myViewHolder.img.setImageResource(R.drawable.correct);
        }else{
            myViewHolder.img.setImageResource(R.drawable.blank);
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qiehuan(i);
            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, AddaddressActivity.class);
                intent.putExtra("type","update");
                intent.putExtra("list",list.get(i));
                context.startActivity(intent);
                ((SetAddressActivity)context).finish();
                return true;
            }
        });
    }

    private void qiehuan(int i) {
        list.get(i).setStatus(1);
        Map<String,String> map = new HashMap<>();
        map.put("uid",sp.getString("uid",""));
        map.put("addrid",list.get(i).getAddrid()+"");
        map.put("status",list.get(i).getStatus()+"");
        new HttpUtil().get("/user/setAddr",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BeanLogin beanLogin = gson.fromJson(data, BeanLogin.class);
                String code = beanLogin.getCode();
                if("0".equals(code)){
                    Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                    listener.setAddress();
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

    public class MyViewHolderAddress extends RecyclerView.ViewHolder {

         TextView name,mobile,address;
        ImageView img;

        public MyViewHolderAddress(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.setaddress_img);
            name=(TextView)itemView.findViewById(R.id.setaddress_name);
            mobile=(TextView)itemView.findViewById(R.id.setaddress_mobile);
            address=(TextView)itemView.findViewById(R.id.setaddress_address);
        }
    }
    private SetAddressListener listener;
    public void result(SetAddressListener listener){
        this.listener=listener;
    }
    public interface SetAddressListener{
        void setAddress();
    }
}
