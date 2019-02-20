![singleton](https://user-images.githubusercontent.com/43207796/53027593-40933b00-348b-11e9-9039-f9dd7fae351c.png)

Que:- **What is the purpose of Singleton?**   
Ans:- *A Singleton class is a class which ensure that only one object(instance) will be created for that class at a time which will
      be used in whole application.*
      
- Eager initialization
- Lazy Instantiation
- Double-checked locking – DCL
- Reflection API     

**-------------------------------------------------How it works------------------------------------------------------------**      
![process](https://user-images.githubusercontent.com/43207796/53080430-aa105980-351e-11e9-93ad-5f3509bed0c4.JPG)

# Eager initialization
- *In eager initialization, the instance of Singleton Class is created at the time of class loading,
this is the easiest method to create a singleton class.*

**-----------------------------------------------------------Example-----------------------------------------------------------**
```ruby
public class SingletonWithEagerInitialization {

   // In eager initialization, the instance of Singleton Class is created at the time of class loading.
    private static final SingletonWithEagerInitialization ourInstance = new SingletonWithEagerInitialization();

    private SingletonWithEagerInitialization() {
    }

    public static String getString(){
        return "Instance is created even we are not be using it by getInstance method."+ourInstance.hashCode();
    }

    public static SingletonWithEagerInitialization getInstance() {
        return ourInstance;
    }
}
```

**-----------------------------------------------------------Problem-----------------------------------------------------------**   
- *May lead to resource wastage. Because instance of class is created always, whether it is required or not.*   
- *CPU time is also wasted in creation of instance if it is not required.*

![eager](https://user-images.githubusercontent.com/43207796/53080449-b85e7580-351e-11e9-9860-301ed90c586b.PNG)

**-----------------------------------------------------------Solution-----------------------------------------------------------**   
- *The solution is to create the new instance of the Singleton class, when needed.
This can be achieved by Lazy Initialization.*

# Lazy Instantiation(single-threaded environment)

**This getInstance() method will check if there is any instance of that class is already created?**
- If yes, then our method (getInstance()) will return that old instance.
- if not then it creates a new instance of the singleton class in JVM and returns that instance.
- Note:- The Singleton instance is not created until we call getInstance() method.
This is called lazy instantiation.

**-----------------------------------------------------------Example-----------------------------------------------------------**   

![single](https://user-images.githubusercontent.com/43207796/53080569-f8bdf380-351e-11e9-9b28-dc2fb81b44d6.JPG)   

**-----------------------------------------------------------Problem-----------------------------------------------------------**   
- *In the above example if two or more threads calls the getInstance() method at same time,
then singleton==null condition will be true for both the thread. So, two different instances
of the same class will be created.That will break the singleton principle.
That means our singleton class is not Thread safe.*

![lazy](https://user-images.githubusercontent.com/43207796/53080464-c2807400-351e-11e9-9130-40a93ed8fbaf.PNG)   
There is two object created for same class(see hashCode).It break the singleton principle.

**-----------------------------------------------------------Solution-----------------------------------------------------------**   
- **To overcome this problem, we need to create getInstance() method synchronized**

# Lazy Instantiation(multi-threaded environment)

- *Synchronized method allows only one thread to run at a time.
When Thread 1 access synchronized method it acquires the lock.
Now the thread 2 has to wait till Thread 1 comes out of the synchronized method and releases the lock.
Now after releasing the lock by thread 1 the thread 2 find that object is already created so it will
return object created by thread 1
That means our singleton class is now Thread safe.*

**-----------------------------------------------------------Example-----------------------------------------------------------**   
```ruby
public class SingletonInMultiThread{
// Step 1:
    private static Singleton singleton;

// Step 2:
    private Singleton(){
    }

// Step 3:
    public synchronized static Singleton getInstance(){
        if(singleton==null){
          singleton= new Singleton();
        }
        return singleton;
    }
}
```

**-----------------------------------------------------------Problem-----------------------------------------------------------**   
*As we know only one Thread can enter synchronised block at a time.
So in the above example if two or more Threads calls the getInstance() method at same time,
then only one Thread at a time can access the getInstance() method another Thread 2 has to wait until one
Thread 1 complete it work and releases the lock.which result into following issue:--*

- *Slow performance because whether the Instance is null or not,the Thread has to wait.
It’s a waste of time for Threads when (Instance!=null)*  
- *Unnecessary synchronization that is not required once the object is initialized.*

**-----------------------------------------------------------Solution-----------------------------------------------------------**   
- **We can overcome this issue if we use Double check locking method to create the singleton**


# Singleton(Double-checked locking–DCL)

- *Thread 1 and Thread 2 enters the getInstance() method in paralleled.They find instance variable is null(instance==null) at first time.
Now Thread 1 and Thread 2 try to enters the synchronised block.But as we know only one thread can enter synchronised block at a time.
So Consider Thread 1 has entered the synchronised block and acquires the lock.
If no thread has passed the synchronized block earlier, the instance variable is null (instance==null).
Then Thread 1 create the new instance.
At that time Thread 2 also trying to entering the synchronized block.Since Thread 1 has acquired the lock,
Thread 2 waits till Thread 1 completes it’s execution and releases the lock.
When Thread 1 releases the lock.
Thread 2 enters Synchronized block.
Now Thread 2 finds Thread 1 has already create the new instance. So instance variable for Thread 2 is not null (instance!=null).
So,Thread 2 does not enter into if(instance==null) block and get the same instance as created by Thread 1.*

**-----------------------------------------------------------Example-------------------------------------------------------------------**   
```ruby
class SingletonDesignPatternWithDCL {
// Step 1:
    private static volatile SingletonDesignPatternWithDCL singletonDCL;

    // Step 2:
    private SingletonDesignPatternWithDCL() {
    }
    // Step 3:
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
```   
![dcl](https://user-images.githubusercontent.com/43207796/53080515-df1cac00-351e-11e9-9874-77cd2d35b5bc.PNG)   
There is same object created for the Singleton class(see hashCode).

# What is Java Reflection API.
- *Reflection is an API which is used to examine or modify the behavior of methods, classes, interfaces at runtime.*

**---------------------------------------------------------------Example---------------------------------------------------------------**
```ruby
public class SingletonWithLazyInitialization {

    private static SingletonWithLazyInitialization singletonWithLazyInitialization;

    private SingletonWithLazyInitialization(){

        //Prevent form the reflection api.
        if(singletonWithLazyInitialization!=null){
            //throw an exception when try to create object of this class using Reflection API
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SingletonWithLazyInitialization getInstance(){

        if(singletonWithLazyInitialization ==null){ //if there is no instance available... create new one
          singletonWithLazyInitialization = new SingletonWithLazyInitialization();
        }
        return singletonWithLazyInitialization;
    }
}
```

**----------------------------------------------------------Problem----------------------------------------------------------------**
- Singleton with Java Reflection API   
*We can create more than one instance of a Singleton class, by changing the visibility of constructor as public
at run-time and create new instance using that constructor.That will break the singleton principle.*

![reflection](https://user-images.githubusercontent.com/43207796/53080496-d4621700-351e-11e9-9bde-76da3f676735.PNG)

**-----------------------------------------------------------Solution-------------------------------------------------------------**   
- *To prevent singleton failure due to reflection we have to throw a run-time exception in constructor,
if the constructor is already initialized and some class try to initialize it again.*

# Where we use Singleton Design Pattern.
- Database Connection   
This FirebaseDatabase uses a Singleton Design Pattern
FirebaseDatabase.getInstance() Ex of Singleton
