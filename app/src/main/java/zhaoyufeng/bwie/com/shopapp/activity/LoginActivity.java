package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.LoginActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShowActivityPresenter;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter>{
    @Override
    public Class<LoginActivityPresenter> getClassPresenter() {
        return LoginActivityPresenter.class;
    }
}
