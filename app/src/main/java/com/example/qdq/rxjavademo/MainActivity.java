package com.example.qdq.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test1();
        test2();
        test3();
        test4();
    }

    private void test1() {
        //创建一个Observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("你好吗？");
                subscriber.onCompleted();
            }
        });
        //创建一个Subscriber对象 订阅observable
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }
        };
        //订阅
        observable.subscribe(subscriber);
    }

    private void test2() {
        //创建Observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("订阅一个事件");
            }
        });
        Action1<String> nextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "nextAction " + s);
            }
        };
        Action1<Throwable> errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        Action0 completeAction = new Action0() {
            @Override
            public void call() {
                Log.e(TAG, "completeAction");
            }
        };
//        observable.subscribe(nextAction);
//        observable.subscribe(nextAction,errorAction);
        observable.subscribe(nextAction, errorAction, completeAction);
    }

    private void test3() {
        //map操作符进行链式转换 map操作符会返回一个新的数据类型的observable对象
        Observable.just("100")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s) + 100;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer num) {
                        return num + "正邦";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        Log.e(TAG, "call: " + result);
                    }
                });
    }
    private void test4(){
        final Subscription subscription=Observable.just(10,10)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
//                        return Integer.parseInt("数字格式化错误");
                        return integer+20;
                    }
                })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer+60;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: "+integer );
                    }
                });
    }
    private void test5(){

    }
}
