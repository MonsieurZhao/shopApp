package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;

public class MyAdapterShop extends BaseAdapter {
    private Context context;
    public MyAdapterShop(Context context){
        this.context=context;
    }
    private List<BeanData.DataBean> list = new ArrayList<>();
    public void setList(List<BeanData.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodler viewHodler=null;
        if(view==null){
            viewHodler=new ViewHodler();
            view = View.inflate(context, R.layout.list_item_data,null);
            viewHodler.textView = view.findViewById(R.id.text_data);
            viewHodler.checkBox = view.findViewById(R.id.checkbox_shangjia);
            viewHodler.recyclerView = view.findViewById(R.id.recyler_data);
            view.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) view.getTag();
        }
        final List<BeanData.DataBean.ListBean> list1 = this.list.get(i).getList();
        viewHodler.textView.setText(list.get(i).getSellerName());
        viewHodler.checkBox.setVisibility(View.VISIBLE);
        viewHodler.checkBox.setChecked(list.get(i).isIschceked());

        final ViewHodler finalViewHodler = viewHodler;
        viewHodler.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(finalViewHodler.checkBox.isChecked()){
                    list.get(i).setIschceked(true);
                    for (int j = 0; j <list1.size() ; j++) {
                        list1.get(j).setIschceked(true);
                    }
                }else{
                    list.get(i).setIschceked(false);
                    for (int j = 0; j <list1.size() ; j++) {
                        list1.get(j).setIschceked(false);
                    }
                }
                listLeftListener.setShopListData(list);
            }
        });
        MyAdapterShopList myAdapterShopList = new MyAdapterShopList(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHodler.recyclerView.setLayoutManager(linearLayoutManager);
        viewHodler.recyclerView.setAdapter(myAdapterShopList);
        myAdapterShopList.setList(list.get(i).getList());
        myAdapterShopList.result(new MyAdapterShopList.ShopDataListener() {
            @Override
            public void setShopList() {
                int num1=0;
                int num2=0;
                for (int j = 0; j < list1.size(); j++) {
                     num1++;
                    if(list1.get(j).isIschceked()){
                     num2++;
                    }

                }
                if(num1>num2){
                   list.get(i).setIschceked(false);
                }else{
                    list.get(i).setIschceked(true);
                }
                listLeftListener.setShopListData(list);
            }
        });
        return view;
    }
    private class ViewHodler {
        private CheckBox checkBox;
        private TextView textView;
        private RecyclerView recyclerView;
    }
    private ShopListener listLeftListener;
    public void result(ShopListener listLeftListener){
        this.listLeftListener=listLeftListener;
    }
    public interface ShopListener{
        void setShopListData(List<BeanData.DataBean> list);
    }
}
