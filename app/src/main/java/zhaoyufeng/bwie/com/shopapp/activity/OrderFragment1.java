package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.OrderFragmentPresenter1;

public class OrderFragment1 extends BaseFragmentPresenter<OrderFragmentPresenter1> {
    @Override
    public Class<OrderFragmentPresenter1> getClassPresenter() {
        return OrderFragmentPresenter1.class;
    }


}
