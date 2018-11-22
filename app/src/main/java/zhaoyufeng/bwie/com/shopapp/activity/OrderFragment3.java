package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.OrderFragmentPresenter3;

public class OrderFragment3 extends BaseFragmentPresenter<OrderFragmentPresenter3> {
    @Override
    public Class<OrderFragmentPresenter3> getClassPresenter() {
        return OrderFragmentPresenter3.class;
    }

}
