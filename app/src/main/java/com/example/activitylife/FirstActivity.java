package com.example.activitylife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 概述summary：
 *      本人一直觉得生命周期稍微懂一点就行了。but！！！
 *      本人最近面试，真是各种日狗！遇到了各种平时没遇到过的生命周期问题，由于自己做项目可能并不广也少有关注，平时都是真要是有问题的时候，直接去打log看的。
 * 但是呢，你懂的记得再清楚，平时也有忘记的时候，这个时候就只能log了。但是面试嘛，面试官就各种问这种问题，反正我觉得记下下面这些，对于生命周期这种
 * 终于画了一个小时，统统都弄懂了，简单的“沾了屎的钱”（不捡可惜，捡了又恶心）基本没问题。
 *
 * 重点掌握：
 *      下面这些点中，5、7和总结一，好好看看，反正我平时根本没关注，但是被问及了，就呵呵哒了。TMD现在总结出来了，尽管你们问。
 *
 * 史上最完整的对Android生命周期的各种情况的测试
 * 1.从Launcher启动Act：onCreate -> onStart -> onResume
 * 2.进入不完全覆盖界面（比如dialog）-> 暂停状态 -> onPause -> 恢复Act -> onResume
 * 从第1到第2个Act的生命周期【1onPause -> 2onCreate -> 2onStart -> 2onResume】
 * 点击退出Dialog【2onPause -> 1onResume -> 2onStop -> 2onDestroy】
 * 3.进入完全覆盖界面 -> 停止状态 -> onStop -> 恢复Act -> onRestart -> onStart -> onResume
 * 从第1到第2个Act的生命周期【1onPause -> 2onCreate -> 2onStart -> 2onResume -> 1onStop】（注意：onStop是在第二个界面启动完成之后才启动的，书上是不会告诉你的）
 * 从第2返回到第1界面的生命周期【2onPause -> 1onRestart -> 1onStart -> 1onResume -> 2onStop -> 2onDestroy】（按键盘退出或执行finish()返回之后，都执行了onDestroy方法）
 * 4.按Home键退出 -> onPause -> onStop -> 再返回App（直接从屏幕点进入或者按住功能键点进去都一样） -> onRestart -> onStart -> onResume
 * 5.按功能键预览多个App界面 -> onPause -> onStop -> 选中再次回到App -> onRestart -> onStart -> onResume
 * 6.按退出键 -> onPause -> onStop -> onDestroy -> 再返回App（直接从屏幕点进入或者按住功能键点进去都一样）-> onCreate -> onStart -> onResume
 * 7.横竖屏切换的生命周期:
 *      情况a(没任何设置的情况）会销毁再重新启动一次-> onPause -> onStop -> onDestroy -> onCreate -> onStart -> onResume
 *      情况b（AndroidManifest.xml里面设置android:configChanges="keyboardHidden|orientation|screenSize"的时候）：就没有销毁和重新运行任何生命周期方法（网人<网上的人>觉得这个节省资源。好像也是）
 * 8.屏幕180°旋转生命周期：注意，屏幕不是横竖屏切换，而是手机180°旋转，然后屏幕方向旋转180°，不会运行任何生命周期方法，不管AndroidManifest.xml里面有没有设置。
 *
 * 总结：
 * 一.停止状态onStop有3种情况会产生？：1.跳转到第二个界面，自己完全被覆盖 2.按功能预览多个app界面的时候 3.按home键的时候，其实没有onDestroy只是onStop了
 *    运行onRestart火onStart的情况？：也是上面3个情况的时候回运行onRestart
 *
 * 二.按手机退出键和按手机home键退出app，然后再返回生命周期是不一样的。（手机退出键执行了onDestroy，home只执行了onStop没有onDestroy）
 *    退出后执行了onDestroy再次恢复的时候，就是onDestroy/onCreate/onStart/onResume    home在恢复：onStop/onRestart/onStart/onResume
 *
 * 面试题：
 * 1.运行onRestart的情况？（停止状态再次恢复：1.2.3看总结一，就这三种情况）
 * 2.运行onStart的情况？（这个就多了，这个问题真是残忍）
 * 3.横竖屏切换时的生命周期？（上面的两种）
 * 4.屏幕180°旋转的生命周期？（哈哈，自己想出来的，总之有些问题不是难，个人觉得有点丧心病狂）
 *
 * 《疯狂Android讲义3》《第一行代码》貌似都没有提及，真是。。。呵呵哒。
 */
public class FirstActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    Class<?>[] clazzs = {FullCoverActivity.class, NoFullCoverActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_first);

        findViewById(R.id.btn_fullCoverActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this,clazzs[0]));
            }
        });

        findViewById(R.id.btn_noFullCoverActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this,clazzs[1]));
            }
        });
    }

    // 停止状态再次恢复才会运行（Act完全不可见再次恢复）
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
