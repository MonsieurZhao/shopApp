package zhaoyufeng.bwie.com.shopapp.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhaoyufeng.bwie.com.shopapp.mvp.view.AppDelage;


public abstract class BaseFragmentPresenter<T extends AppDelage> extends Fragment{
    private T daleagt;

    public abstract Class<T> getClassPresenter();
    public BaseFragmentPresenter(){
        try {
            daleagt = getClassPresenter().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        daleagt.create(getLayoutInflater(),container,savedInstanceState);
        return daleagt.view();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        daleagt.getContext(getActivity());
        daleagt.initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        daleagt.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daleagt.destry();
        daleagt=null;
    }
}
