package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.OrderFragmentPresenter2;

public class OrderFragment2 extends BaseFragmentPresenter<OrderFragmentPresenter2> {
    @Override
    public Class<OrderFragmentPresenter2> getClassPresenter() {
        return OrderFragmentPresenter2.class;
    }


}
