package com.sung.mvvmframe;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.sung.common.CommonApi;
import com.sung.common.Constants;
import com.sung.mvvmframe.base.BaseApplication;
import com.sung.net.NetApi;
import com.sung.uikit.UikitApi;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class Application extends BaseApplication {

    private static class Holder {
        private static Application holder = new Application();
    }

    public static Application getInstance() {
        return Holder.holder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        FrescoInit(getContext());

        try {
            // 组件化初始
            CommonApi.getInstance().init(getContext());
            NetApi.getInstance().init(getContext());
            UikitApi.getInstance().init(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化操作，建议在子线程中进行
     *
     * @param context
     */
    private void FrescoInit(final Context context) {
        try {
            DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                    //最大缓存
                    .setMaxCacheSize(com.sung.common.Constants.Config.CONFIG_FRESCO_CACHE_SIZE * 1024 * 1024)
                    //子目录
                    .setBaseDirectoryName(Constants.Config.CONFIG_FRESCO_CACHE_DIR)
                    .setBaseDirectoryPathSupplier(() -> {
                        //还是推荐缓存到应用本身的缓存文件夹,这样卸载时能自动清除,其他清理软件也能扫描出来
                        return context.getCacheDir();
                    })
                    .build();
            ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                    .setMainDiskCacheConfig(diskCacheConfig)
                    //.setImageCacheStatsTracker(imageCacheStatsTracker)
                    .setDownsampleEnabled(true)
                    //Downsampling，要不要向下采样,它处理图片的速度比常规的裁剪scaling更快，
                    // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                    .setBitmapsConfig(Bitmap.Config.RGB_565)
                    //如果不是重量级图片应用,就用这个省点内存吧.默认是RGB_888
                    .build();
            Fresco.initialize(context, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
