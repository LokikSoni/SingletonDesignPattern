package com.greenlab.www.singletondesignpattern.Singleton;

/*
   Singleton design pattern with Lazy Instantiation
 */
public class SingletonWithLazyInitialization {

    private static SingletonWithLazyInitialization singletonWithLazyInitialization;

    private SingletonWithLazyInitialization(){
    }

    public static SingletonWithLazyInitialization getInstance(){

        if(singletonWithLazyInitialization ==null){ //if there is no instance available... create new one
          singletonWithLazyInitialization = new SingletonWithLazyInitialization();
        }
        return singletonWithLazyInitialization;
    }
}
