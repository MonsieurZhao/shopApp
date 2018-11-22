package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter>{
    @Override
    public Class<MainActivityPresenter> getClassPresenter() {
        return MainActivityPresenter.class;
    }
}
