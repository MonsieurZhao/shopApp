package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShowActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.SkipActivityPresenter;

public class SkipActivity extends BaseActivityPresenter<SkipActivityPresenter>{
    @Override
    public Class<SkipActivityPresenter> getClassPresenter() {
        return SkipActivityPresenter.class;
    }
}
