package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShowActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.WebActivityPresenter;

public class WebActivity extends BaseActivityPresenter<WebActivityPresenter>{
    @Override
    public Class<WebActivityPresenter> getClassPresenter() {
        return WebActivityPresenter.class;
    }
}
