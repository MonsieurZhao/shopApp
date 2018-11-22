package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.FristActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.MainActivityPresenter;

public class FristActivity extends BaseActivityPresenter<FristActivityPresenter>{
    @Override
    public Class<FristActivityPresenter> getClassPresenter() {
        return FristActivityPresenter.class;
    }
}
