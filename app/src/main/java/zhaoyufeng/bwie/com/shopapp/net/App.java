package zhaoyufeng.bwie.com.shopapp.net;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 高级初始化：
        Fresco.initialize(this);
    }
}
