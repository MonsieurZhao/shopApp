package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.SearchActivityPresenter;

public class SearchActivity extends BaseActivityPresenter<SearchActivityPresenter>{
    @Override
    public Class<SearchActivityPresenter> getClassPresenter() {
        return SearchActivityPresenter.class;
    }
}
