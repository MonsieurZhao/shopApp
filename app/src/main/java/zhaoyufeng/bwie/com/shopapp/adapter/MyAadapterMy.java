package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.MyorderAcitvity;
import zhaoyufeng.bwie.com.shopapp.model.BeanMy;

/**
 * author:AbnerMing
 * date:2018/10/22
 */
public class MyAadapterMy extends RecyclerView.Adapter<MyAadapterMy.MyViewHolderMy>{
    private  List<BeanMy> listBean=new ArrayList<>();
    private Context context;
    public MyAadapterMy(Context context, List<BeanMy> listBean) {
        this.context=context;
        this.listBean=listBean;
    }

    @NonNull
    @Override
    public MyViewHolderMy onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context, R.layout.recyler_my_item,null);
        MyViewHolderMy myViewHolder=new MyViewHolderMy(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMy myViewHolder, final int i) {
        myViewHolder.mAccountTitle.setText(listBean.get(i).getName());
        myViewHolder.mLeftImage.setImageResource(listBean.get(i).getImage());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==1){
                    Intent intent = new Intent(context, MyorderAcitvity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBean.size();
    }

    public class MyViewHolderMy extends RecyclerView.ViewHolder {

         TextView mAccountTitle;
        ImageView mLeftImage;

        public MyViewHolderMy(@NonNull View itemView) {
            super(itemView);
            mLeftImage=(ImageView)itemView.findViewById(R.id.my_left_image);
            mAccountTitle=(TextView)itemView.findViewById(R.id.my_title);
        }
    }
}
