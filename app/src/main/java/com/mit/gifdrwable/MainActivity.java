package com.mit.gifdrwable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    ImageView gifImageView;
    private boolean first = true;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifImageView = findViewById(R.id.image);
        context = MainActivity.this;

//        DownloadUtils downloadUtils = new DownloadUtils();
//        downloadUtils.download("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=83715faefddcd100cdc9f02547bb6b26/8c1001e93901213f00b8bb7f51e736d12e2e958e.jpg", getExternalCacheDir().getPath() + "/1.gif");
//
//        downloadUtils.setOnDownloadListener(new DownloadUtils.OnDownloadListener() {
//            @Override
//            public void onDownloadUpdate(int percent) {
//                Log.d("tag", "onDownloadUpdate:" + percent);
//            }
//
//            @Override
//            public void onDownloadError(Exception e) {
//                Log.d("tag", "onDownloadError:" + e.getMessage());
//            }
//
//            @Override
//            public void onDownloadConnect(int filesize) {
//                Log.d("tag", "onDownloadConnect:" + (filesize / 1024) + "KB");
//            }
//
//            @Override
//            public void onDownloadByte(byte[] bytes) {
//
//                Log.d("tag", "onDownloadByte:" + (bytes.length / 1024) + "KB");
//            }
//
//            @Override
//            public void onDownloadComplete(Object result) {
//                Log.d("tag", "onDownloadComplete:" + result.toString());
//                try {
//                    GifDrawable gifDrawable = new GifDrawable(getExternalCacheDir().getPath() + "/1.gif");
//                    gifImageView.setBackgroundDrawable(gifDrawable);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


//        LoadGifUtils loadGifUtils = new LoadGifUtils();
//        loadGifUtils.setListener(new LoadGifUtils.onCompltedListener() {
//            @Override
//            public void onComplted(byte[] bt) {
//                try {
//                    Log.d("tag", "1count:" + (bt.length / 1024));
//                    GifDrawable drawable = new GifDrawable(bt);
//                    gifImageView.setBackgroundDrawable(drawable);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onLoading(byte[] bt) {
//                try {
//                    Log.d("tag", "2count:" + (bt.length / 1024));
//                    if (first) {
//                        GifDrawable drawable = new GifDrawable(bt);
//                        gifImageView.setBackgroundDrawable(drawable);
//                        first = false;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        loadGifUtils.loadGif("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547198071040&di=d185097dc4f438c351305322f3f35aad&imgtype=0&src=http%3A%2F%2Fimage001.tlzhao.com%2F20170605%2F62db21b2ee3accad42308aa6bbdb9ee9.gif");


        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                final Bitmap bitmap = loadGifFirstBitmap("http://ww3.sinaimg.cn/mw1024/0073tLPGgy1fz20rfhklbg306i09xe81.gif");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        try {
//                            byte[] bytes = getBytesByBitmap(bitmap);
//                            Log.d("tag", "5count:" + (bytes.length / 1024));
//                            GifDrawable gifDrawable = new GifDrawable(bytes);
//                            gifImageView.setBackgroundDrawable(gifDrawable);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        gifImageView.setImageBitmap(bitmap);
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public static Bitmap loadGifFirstBitmap(final String url) {
        Bitmap bitmap = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream is = urlConnection.getInputStream();
            Movie movie = Movie.decodeStream(is);
            //Bitmap.Config.ARGB_8888 这里是核心，如果出现图片显示不正确，就换编码试试
            bitmap = Bitmap.createBitmap(movie.width(), movie.height(), Bitmap.Config.ARGB_8888);
            int count = bitmap.getByteCount();
            Log.d("tag","count:"+count);
            Canvas canvas = new Canvas(bitmap);
            movie.draw(canvas, 0, 0);
            canvas.save();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bitmap;
        }

    }

    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }


}
