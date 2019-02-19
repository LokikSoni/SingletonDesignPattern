package com.greenlab.www.singletondesignpattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.greenlab.www.singletondesignpattern.Singleton.SingletonDesignPatternWithDCL;
import com.greenlab.www.singletondesignpattern.Singleton.SingletonWithEagerInitialization;
import com.greenlab.www.singletondesignpattern.Singleton.SingletonWithLazyInitialization;
import com.greenlab.www.singletondesignpattern.Singleton.SingletonWithReflectionAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSingletonThread,btnSingletonReflection,btnSingletonEager;
    Thread t1,t2;
    public String TAG="Singleton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSingletonThread=findViewById(R.id.btnSingletonThread);
        btnSingletonEager=findViewById(R.id.btnSingletonEager);
        btnSingletonReflection=findViewById(R.id.btnSingletonReflection);
        btnSingletonThread.setOnClickListener(this);
        btnSingletonEager.setOnClickListener(this);
        btnSingletonReflection.setOnClickListener(this);


        //In Singleton class we have created a private constructor to avoid using below code
        // new SingletonWithLazyInitialization();
       // new SingletonDesignPatternWithDCL();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnSingletonThread){
            checkInMultiThread(v);

        }else if(v.getId()==R.id.btnSingletonEager){
            checkWithEagerInitialization(v);

        } else if(v.getId()==R.id.btnSingletonReflection){
            checkWithReflectionAPI(v);
        }
    }

    private void checkWithEagerInitialization(View view) {
        Log.d(TAG, "------------------Eager Initialization------------------");
        Log.d(TAG," "+SingletonWithEagerInitialization.getString());
        view.setEnabled(false);
    }


    private void checkInMultiThread(View view) {

        Log.d(TAG, "------------------In multi-thread environment------------------");
        //thread 1
        t1= new Thread(new Runnable() {
            @Override
            public void run() {
                SingletonWithLazyInitialization instance1= SingletonWithLazyInitialization.getInstance();
                SingletonDesignPatternWithDCL instanceDCL1= SingletonDesignPatternWithDCL.getInstance();

                Log.d(TAG, "First Object from SingletonWithLazyInitialization class: " + instance1.hashCode());
                Log.d(TAG, "First Object from SingletonDesignPatternWithDCL class: " +instanceDCL1.hashCode());
            }
        });

        //thread 2
        t2= new Thread(new Runnable() {
            @Override
            public void run() {
                SingletonWithLazyInitialization instance2= SingletonWithLazyInitialization.getInstance();
                SingletonDesignPatternWithDCL instanceDCL2= SingletonDesignPatternWithDCL.getInstance();

                Log.d(TAG, "Second Object from SingletonWithLazyInitialization class: " + instance2.hashCode());
                Log.d(TAG, "Second Object from SingletonDesignPatternWithDCL class: " + instanceDCL2.hashCode());
            }
        });

        //in Multi-thread environment
        t1.start();
        t2.start();
        view.setEnabled(false);
    }



    private void checkWithReflectionAPI(View view) {

        Log.d(TAG, "------------------Singleton with Reflection API------------------");
        //Create the 1st instance
        SingletonWithReflectionAPI instance1=SingletonWithReflectionAPI.getInstance();
        Log.d(TAG, "First Object from SingletonWithReflectionAPI class: " + instance1.hashCode()+" Reflection API");

        //Create 2nd instance using Java Reflection API.
        try {
            //get the Singleton class constructor
            Constructor<SingletonWithReflectionAPI> constructor=SingletonWithReflectionAPI.class.getDeclaredConstructor();
            //make the constructor public
            constructor.setAccessible(true);
            //create the 2nd instance using that constructor
            SingletonWithReflectionAPI instance2=constructor.newInstance();
            Log.d(TAG, "Second Object from SingletonWithReflectionAPI class: " + instance2.hashCode()+" Reflection API");

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        view.setEnabled(false);
    }
}
