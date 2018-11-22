package zhaoyufeng.bwie.com.shopapp.activity;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.FristFragmentPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ListFragmentPresenter;

public class ListFragment extends BaseFragmentPresenter<ListFragmentPresenter> {
    @Override
    public Class<ListFragmentPresenter> getClassPresenter() {
        return ListFragmentPresenter.class;
    }
}
