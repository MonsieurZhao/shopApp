package zhaoyufeng.bwie.com.shopapp.net;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpUtil {

    private final BaseService baseService;
    private Observable<ResponseBody> observable;

    public HttpUtil(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://www.zhaoapi.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        baseService = retrofit.create(BaseService.class);
    }
    public HttpUtil get(String url, Map<String,String> map){
        if(map==null){
            map = new HashMap<>();
        }
        observable = baseService.get(url, map);
        setObserver();
        return this;
    }
    public HttpUtil post(String url, Map<String,String> map){
        if(map==null){
            map = new HashMap<>();
        }
        observable = baseService.post(url, map);
        setObserver();
        return this;
    }
    public HttpUtil part(String url, Map<String,String> map, MultipartBody.Part part){
        if(map==null){
            map = new HashMap<>();
        }
        observable = baseService.part(url, map,part);
        setObserver();
        return this;
    }
    private void setObserver() {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private Observer observer = new Observer<ResponseBody>(){

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                string = string.replace("https","http");
                listener.success(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String message = e.getMessage();
            listener.fail(message);
        }

        @Override
        public void onComplete() {

        }
    };
    private HttpRxListener listener;
    public void result(HttpRxListener listener){
        this.listener=listener;
    }

    public interface HttpRxListener{
        void success(String data);
        void fail(String error);
    }
}
