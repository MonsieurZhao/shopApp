package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.WebActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanJiu;

public class MyAdapterDataList extends RecyclerView.Adapter<MyAdapterDataList.MyViewHodlerDataList> {
    private Context context;
    public MyAdapterDataList(Context context){
        this.context=context;
    }
    private List<BeanData.DataBean.ListBean> list = new ArrayList<>();
    public void setList(List<BeanData.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHodlerDataList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyler_item_data,null);
        MyViewHodlerDataList myViewHodler = new MyViewHodlerDataList(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerDataList holder, final int position) {
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        holder.img.setImageURI(split[0]);
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("价格：￥:"+list.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                BeanData.DataBean.ListBean listBean = list.get(position);
                intent.putExtra("list",listBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }



    public class MyViewHodlerDataList extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private  TextView price,title;

        public MyViewHodlerDataList(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_data);
            price = itemView.findViewById(R.id.price_recy_data);
            title = itemView.findViewById(R.id.title_recy_data);
        }
    }
}
