package com.greenlab.www.singletondesignpattern.Singleton;

/*
   Singleton design pattern with Eager initialization:
 */
public class SingletonWithEagerInitialization {

   // In eager initialization, the instance of Singleton Class is created at the time of class loading.
    private static final SingletonWithEagerInitialization ourInstance = new SingletonWithEagerInitialization();

    private SingletonWithEagerInitialization() {
    }

    public static String getString(){
        return "Instance is created even we are not required it."+ourInstance.hashCode();
    }

    public static SingletonWithEagerInitialization getInstance() {
        return ourInstance;
    }
}
