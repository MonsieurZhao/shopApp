package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zhaoyufeng.bwie.com.shopapp.R;
import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.model.BeanListLeft;

public class MyAdapterListLeft extends RecyclerView.Adapter<MyAdapterListLeft.MyViewHodlerLeft> {
    private Context context;
    public MyAdapterListLeft(Context context){
        this.context=context;
    }
    private List<BeanListLeft.DataBean> list = new ArrayList<>();
    public void setList(List<BeanListLeft.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHodlerLeft onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.list_left_item,null);
        MyViewHodlerLeft myViewHodler = new MyViewHodlerLeft(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(final MyViewHodlerLeft holder, final int position) {
        if(list.get(position).isIschceked()){
            holder.textView.setBackgroundResource(R.color.colorAccent);
            holder.textView.setTextColor(Color.WHITE);
        }else{
            holder.textView.setBackgroundResource(R.color.colore8e8e8);
            holder.textView.setTextColor(Color.BLACK);
        }
        holder.textView.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listLeftListener.setId(list.get(position).getCid());
                listLeftListener.setposition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerLeft extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHodlerLeft(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_left);

        }
    }
    private ListLeftListener listLeftListener;
    public void result(ListLeftListener listLeftListener){
        this.listLeftListener=listLeftListener;
    }
    public interface ListLeftListener{
        void setId(int id);
        void setposition(int position);
    }
}
