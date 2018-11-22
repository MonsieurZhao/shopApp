package zhaoyufeng.bwie.com.shopapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhaoyufeng.bwie.com.shopapp.R;
import zhaoyufeng.bwie.com.shopapp.activity.Flowlayout;
import zhaoyufeng.bwie.com.shopapp.activity.SearchActivity;
import zhaoyufeng.bwie.com.shopapp.activity.SearchShowActivity;
import zhaoyufeng.bwie.com.shopapp.adapter.MyAdapterSearchText;
import zhaoyufeng.bwie.com.shopapp.model.BeanSearch;
import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;
import zhaoyufeng.bwie.com.shopapp.net.HttpUtil;
import zhaoyufeng.bwie.com.shopapp.net.User;
import zhaoyufeng.bwie.com.shopapp.net.UserDaoData;

import static android.app.Activity.RESULT_OK;

public class  SearchActivityPresenter extends AppDelage {

    private SearchView text;
    private Flowlayout flowlayout;
    private List<String> lists = new ArrayList<>();
    private ListView listView;
    private List<String> strings = new ArrayList<>();
    private MyAdapterSearchText myAdapterSearchText;
    private RelativeLayout layout;
    private List<BeanSearch.DataBean> data2;

    @Override
    public int getLayoutId() {
        return R.layout.search;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {
        super.initData();
        text = get(R.id.title_search);
        flowlayout = get(R.id.flow_search);
        listView = get(R.id.list_search);
        layout = get(R.id.layout_search_text);
        UserDaoData.getGetDao().init(context);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        },R.id.empty_search);
        setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaptureActivity.class);
                ((SearchActivity)context).startActivityForResult(intent,1002);
            }
        },R.id.scan_search);
        querList();
        myAdapterSearchText = new MyAdapterSearchText(context);
        listView.setAdapter(myAdapterSearchText);
        //listview启动过滤
        listView.setTextFilterEnabled(false);
        //一开始不显示
        listView.setVisibility(View.GONE);
        //搜索框不自动缩小为一个搜索图标，而是match_parent
        text.setIconifiedByDefault(false);
        //显示搜索按钮
        text.setSubmitButtonEnabled(true);
        //默认提示文本
        text.setQueryHint("查找");

        text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i("aaa",s);
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    listView.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    listView.clearTextFilter();
                }
                else {
                    listView.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                    doHttp(s);
                    listView.setFilterText(s);
                }
                return false;
            }

    });
        flowlayout.result(new Flowlayout.FlowListener() {
            @Override
            public void setList() {
                querList();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search(data2.get(i).getTitle());
            }
        });
}

    private void doHttp(final String result) {
        Map<String,String> map = new HashMap<>();
        map.put("keywords",result);
        new HttpUtil().get("/product/searchProducts",map).result(new HttpUtil.HttpRxListener() {
            @Override
            public void success(String data) {
                if(data.contains("<")){
                    doHttp(result);
                    return;
                }
                Gson gson = new Gson();
                BeanSearch beanData = gson.fromJson(data, BeanSearch.class);
                data2 = beanData.getData();
                myAdapterSearchText.setList(data2);
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void delete() {
        List<User> users = UserDaoData.getGetDao().queryList("searchListString");
        for (User u:users) {
            UserDaoData.getGetDao().deleteByUser(u);
        }
        querList();
    }

    private void search(String result) {

        if(TextUtils.isEmpty(result)){
            Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
        }else{
            User user = new User();
            user.setType("searchListString");
            user.setData(result);
            UserDaoData.getGetDao().addUserList(user);
            querList();
            Intent intent = new Intent(context, SearchShowActivity.class);
            intent.putExtra("title",result);
            context.startActivity(intent);
        }

    }

    private void querList() {
        List<User> users = UserDaoData.getGetDao().queryList("searchListString");
        flowlayout.setList(users);

    }


    @Override
    public void resume() {

    }

    @Override
    public void restart() {

    }

}
