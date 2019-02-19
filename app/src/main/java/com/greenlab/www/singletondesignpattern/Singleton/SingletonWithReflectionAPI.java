package com.greenlab.www.singletondesignpattern.Singleton;

/*
   Singleton design pattern with Reflection API
 */
public class SingletonWithReflectionAPI {

    private static SingletonWithReflectionAPI singletonWithReflectionAPI;

    private SingletonWithReflectionAPI(){

        //Prevent form the reflection api.
        if(singletonWithReflectionAPI !=null){
            //throw an exception when try to create object of this class using Reflection API
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SingletonWithReflectionAPI getInstance(){

        if(singletonWithReflectionAPI ==null){ //if there is no instance available... create new one
            singletonWithReflectionAPI = new SingletonWithReflectionAPI();
        }
        return singletonWithReflectionAPI;
    }
}
