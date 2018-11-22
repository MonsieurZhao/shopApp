package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.InsertActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShowActivityPresenter;

public class InsertActivity extends BaseActivityPresenter<InsertActivityPresenter>{
    @Override
    public Class<InsertActivityPresenter> getClassPresenter() {
        return InsertActivityPresenter.class;
    }
}
