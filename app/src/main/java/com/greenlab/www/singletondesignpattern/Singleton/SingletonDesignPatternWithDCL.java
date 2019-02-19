package com.greenlab.www.singletondesignpattern.Singleton;

/*
   Singleton design pattern Double-checked locking – DCL for multi-threaded environment
 */

public class SingletonDesignPatternWithDCL {

    private static volatile SingletonDesignPatternWithDCL singletonDCL;

    private SingletonDesignPatternWithDCL() {

        //Prevent form the reflection api.
        if(singletonDCL!=null){
            //throw an exception when try to create object of this class using Reflection API
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static  SingletonDesignPatternWithDCL getInstance() {

        // double-checking lock
        if(singletonDCL==null){
            // synchronized block
            synchronized (SingletonDesignPatternWithDCL.class){
                if(singletonDCL==null){
                    singletonDCL = new SingletonDesignPatternWithDCL();
                }
            }
        }
        return singletonDCL;
    }
}
