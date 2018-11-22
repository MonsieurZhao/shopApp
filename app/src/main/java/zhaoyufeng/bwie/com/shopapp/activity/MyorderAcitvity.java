package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.MyorderAcitvityPresenter;

public class MyorderAcitvity extends BaseActivityPresenter<MyorderAcitvityPresenter> {
    @Override
    public Class<MyorderAcitvityPresenter> getClassPresenter() {
        return MyorderAcitvityPresenter.class;
    }
}
