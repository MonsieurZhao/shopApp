package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.OrderFragmentPresenter;

public class OrderFragment extends BaseFragmentPresenter<OrderFragmentPresenter> {
    @Override
    public Class<OrderFragmentPresenter> getClassPresenter() {
        return OrderFragmentPresenter.class;
    }

    public void setList() {

    }
}
