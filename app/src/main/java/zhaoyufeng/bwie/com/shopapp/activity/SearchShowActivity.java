package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.SearchShowActivityPresenter;

public class SearchShowActivity extends BaseActivityPresenter<SearchShowActivityPresenter>{
    @Override
    public Class<SearchShowActivityPresenter> getClassPresenter() {
        return SearchShowActivityPresenter.class;
    }
}
