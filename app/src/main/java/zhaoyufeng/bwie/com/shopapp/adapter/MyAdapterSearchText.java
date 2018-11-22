package zhaoyufeng.bwie.com.shopapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.model.BeanData;
import zhaoyufeng.bwie.com.shopapp.model.BeanSearch;

public class MyAdapterSearchText extends BaseAdapter {
    private Context context;
    public MyAdapterSearchText(Context context){
        this.context=context;
    }
    private List<BeanSearch.DataBean> list = new ArrayList<>();
    public void setList(List<BeanSearch.DataBean> list){
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
            view = View.inflate(context, R.layout.search_text,null);
            viewHodler.textView = view.findViewById(R.id.search_text);

            view.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) view.getTag();
        }
        viewHodler.textView.setText(list.get(i).getTitle());
        return view;
    }
    private class ViewHodler {
        private TextView textView;
    }
}
