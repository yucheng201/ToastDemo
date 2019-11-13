package com.minglin.android.toastdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View text;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        text.post(new Runnable() {
            @Override
            public void run() {
                int height = text.getHeight();
                Log.i("MainActivity", "绘制时间------" + (System.currentTimeMillis() - startTime) + "  height = " + height);
            }
        });
    }

    /**
     * 异常,仅在7.0上报错
     * WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@b7c964a is not valid; is your activity running?
     *  并且异常是发生在我们的下一个 UI 线程消息中，因此我们在上一个 ui 线程消息中加入 try-catch 是没有意义的
     * @param view
     */
    public void testException(View view) {
        try {
            Toast.makeText(MainActivity.this, "测试一下", Toast.LENGTH_LONG).show();
            Thread.sleep(10_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCatchException(View view) {
        try {
            ToastUtils.showToast(this, "测试一下", Toast.LENGTH_LONG);
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {

            @Override
            public void doFrame(long l) {
                startTime = System.currentTimeMillis();
                int height = text.getHeight();
                Log.i("MainActivity", "绘制开始------" + "  height = " + height);
            }


        });
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        startTime = System.currentTimeMillis();
        int height = text.getHeight();
        Log.i("MainActivity", "onAttachedToWindow------" + "  height = " + height);
    }
}
