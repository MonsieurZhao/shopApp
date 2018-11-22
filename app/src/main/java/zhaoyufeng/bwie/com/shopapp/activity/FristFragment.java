package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.FristFragmentPresenter;

public class FristFragment extends BaseFragmentPresenter<FristFragmentPresenter> {
    @Override
    public Class<FristFragmentPresenter> getClassPresenter() {
        return FristFragmentPresenter.class;
    }
}
