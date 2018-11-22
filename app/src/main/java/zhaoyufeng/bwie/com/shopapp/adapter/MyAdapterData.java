package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;

public class MyAdapterData extends RecyclerView.Adapter<MyAdapterData.MyVIewHodlerData> {
    private Context context;
    public MyAdapterData(Context context){
        this.context=context;
    }
    private List<BeanData.DataBean> list = new ArrayList<>();
    public void setList(List<BeanData.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public MyVIewHodlerData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.list_item_data,null);
        MyVIewHodlerData myVIewHodlerData = new MyVIewHodlerData(view);
        return myVIewHodlerData;
    }

    @Override
    public void onBindViewHolder(MyVIewHodlerData holder, int position) {
        holder.textView.setText(list.get(position).getSellerName());
        MyAdapterDataList myAdapterDataList = new MyAdapterDataList(context);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(myAdapterDataList);
        myAdapterDataList.setList(list.get(position).getList());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyVIewHodlerData extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;

        public MyVIewHodlerData(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_data);
            recyclerView = itemView.findViewById(R.id.recyler_data);
        }
    }
}
