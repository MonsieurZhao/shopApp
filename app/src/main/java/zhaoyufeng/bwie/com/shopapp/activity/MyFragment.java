package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.FristFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.MyFragmentPresenter;

public class MyFragment extends BaseFragmentPresenter<MyFragmentPresenter> {
    @Override
    public Class<MyFragmentPresenter> getClassPresenter() {
        return MyFragmentPresenter.class;
    }
}
