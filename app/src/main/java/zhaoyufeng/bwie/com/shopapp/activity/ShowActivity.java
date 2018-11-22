package zhaoyufeng.bwie.com.shopapp.activity;


import android.content.Intent;
import android.support.annotation.Nullable;

import zhaoyufeng.bwie.com.shopapp.mvp.presenter.BaseActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.MainActivityPresenter;
import zhaoyufeng.bwie.com.shopapp.presenter.ShowActivityPresenter;

public class ShowActivity extends BaseActivityPresenter<ShowActivityPresenter>{
    @Override
    public Class<ShowActivityPresenter> getClassPresenter() {
        return ShowActivityPresenter.class;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        daleagt.onActivityResult(requestCode,resultCode,data);
    }
}
