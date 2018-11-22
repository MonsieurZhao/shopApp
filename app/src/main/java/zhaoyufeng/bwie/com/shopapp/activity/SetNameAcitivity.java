package zhaoyufeng.bwie.com.shopapp.activity;


import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.SetNameAcitivityPresenter;

public class SetNameAcitivity extends BaseActivityPresenter<SetNameAcitivityPresenter>{
    @Override
    public Class<SetNameAcitivityPresenter> getClassPresenter() {
        return SetNameAcitivityPresenter.class;
    }
}
