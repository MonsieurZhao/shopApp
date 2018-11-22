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
import zhaoyufeng.bwie.com.shopapp.activity.SearchShowActivity;
import zhaoyufeng.bwie.com.shopapp.model.BeanListRight;

public class MyAdapterListRightItem extends RecyclerView.Adapter<MyAdapterListRightItem.MyViewHodlerRight> {
    private Context context;
    public MyAdapterListRightItem(Context context){
        this.context=context;
    }
    private List<BeanListRight.DataBean.ListBean> list = new ArrayList<>();
    public void setList(List<BeanListRight.DataBean.ListBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHodlerRight onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.list_right_recyler_item,null);
        MyViewHodlerRight myViewHodler = new MyViewHodlerRight(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerRight holder, final int position) {
        holder.textView.setText(list.get(position).getName());
        holder.img.setImageURI(list.get(position).getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchShowActivity.class);
                intent.putExtra("pscid",list.get(position).getPscid()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerRight extends RecyclerView.ViewHolder{

        private TextView textView;
        private  SimpleDraweeView img;

        public MyViewHodlerRight(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_right_item);
            textView = itemView.findViewById(R.id.text_right_item);
        }
    }
}
