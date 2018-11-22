package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.SetAddressActivityPresenter;

public class SetAddressActivity extends BaseActivityPresenter<SetAddressActivityPresenter> {
    @Override
    public Class<SetAddressActivityPresenter> getClassPresenter() {
        return SetAddressActivityPresenter.class;
    }


}
