package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.WebActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanSearch;

public class MyAdapterSearch extends RecyclerView.Adapter<MyAdapterSearch.MyViewHodlerSearch> {
    private Context context;
    public MyAdapterSearch(Context context){
        this.context=context;
    }
    private List<BeanSearch.DataBean> list = new ArrayList<>();
    public void setList(List<BeanSearch.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHodlerSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.search_show_recyler,null);
        MyViewHodlerSearch myViewHodler = new MyViewHodlerSearch(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerSearch holder, final int position) {
        String images = list.get(position).getImages();
        if(images.contains("|")){
            String[] split = images.split("\\|");
            holder.img.setImageURI(split[0]);
        }else{
            holder.img.setImageURI(images);
        }

        holder.title.setText(list.get(position).getTitle());
        holder.price.setText("价格：￥:"+list.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                BeanSearch.DataBean dataBean = list.get(position);
                intent.putExtra("search",dataBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHodlerSearch extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private  TextView price,title;

        public MyViewHodlerSearch(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_search);
            price = itemView.findViewById(R.id.price_recy_search);
            title = itemView.findViewById(R.id.title_recy_search);
        }
    }
}
