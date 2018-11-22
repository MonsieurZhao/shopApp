package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.FristFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShopFragmentPresenter;

public class ShopFragment extends BaseFragmentPresenter<ShopFragmentPresenter> {
    @Override
    public Class<ShopFragmentPresenter> getClassPresenter() {
        return ShopFragmentPresenter.class;
    }
    @Override
    public void onResume() {
        super.onResume();
        intdata();
    }

    private void intdata() {
    }
}
