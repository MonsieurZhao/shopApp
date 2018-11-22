package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.AddaddressActivityPresenter;

public class AddaddressActivity extends BaseActivityPresenter<AddaddressActivityPresenter>{
    @Override
    public Class<AddaddressActivityPresenter> getClassPresenter() {
        return AddaddressActivityPresenter.class;
    }
}
