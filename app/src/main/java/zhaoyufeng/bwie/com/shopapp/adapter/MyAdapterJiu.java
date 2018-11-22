package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.model.BeanJiu;

public class MyAdapterJiu extends RecyclerView.Adapter<MyAdapterJiu.MyViewHodlerJiu> {
    private Context context;
    public MyAdapterJiu(Context context){
        this.context=context;
    }
    private List<BeanJiu.DataBean> list = new ArrayList<>();
    public void setList(List<BeanJiu.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHodlerJiu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.grid_item_jiu,null);
        MyViewHodlerJiu myViewHodler = new MyViewHodlerJiu(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerJiu holder, int position) {
        holder.img.setImageURI(list.get(position).getIcon());
        holder.textViewt.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHodlerJiu extends RecyclerView.ViewHolder{

        private SimpleDraweeView img;
        private  TextView textViewt;

        public MyViewHodlerJiu(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_jiu);
            textViewt = itemView.findViewById(R.id.text_jiu);
        }
    }
}
