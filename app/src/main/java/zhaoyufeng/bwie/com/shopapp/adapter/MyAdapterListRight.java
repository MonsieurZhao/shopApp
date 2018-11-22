package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.model.BeanListLeft;
import zhaoyufeng.bwie.com.shopapp.model.BeanListRight;

public class MyAdapterListRight extends RecyclerView.Adapter<MyAdapterListRight.MyViewHodlerRight> {
    private Context context;
    public MyAdapterListRight(Context context){
        this.context=context;
    }
    private List<BeanListRight.DataBean> list = new ArrayList<>();
    public void setList(List<BeanListRight.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHodlerRight onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.list_right_item,null);
        MyViewHodlerRight myViewHodler = new MyViewHodlerRight(view);
        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(MyViewHodlerRight holder, int position) {
        holder.textView.setText(list.get(position).getName());
        MyAdapterListRightItem myAdapterListRightItem = new MyAdapterListRightItem(context);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(myAdapterListRightItem);
        myAdapterListRightItem.setList(list.get(position).getList());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodlerRight extends RecyclerView.ViewHolder{

        private TextView textView;
        private RecyclerView recyclerView;

        public MyViewHodlerRight(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_right);
            recyclerView = itemView.findViewById(R.id.recyler_right_re);
        }
    }
}
