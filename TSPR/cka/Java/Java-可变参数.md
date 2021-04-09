## Java 可变参数
{: id="20210408143813-raj1qdz"}

在 Java 5 中提供了变长参数，允许在调用方法时传入不定长度的参数。变长参数是 Java 的一个语法糖，本质上还是基于数组的实现：
{: id="20210408143813-t8gr2c2"}

{: id="20210409160452-1e836eg"}

```
void foo(String... args);
void foo(String[] args);
```
{: id="20210408143813-x46vsd3"}

```
//方法签名 
([Ljava/lang/String;)V // public void foo(String[] args)
```
{: id="20210408143813-ol3thrn"}

---
{: id="20210408143813-kdzblt8"}

## 定义方法
{: id="20210408143813-ylas3fp"}

在定义方法时，在最后一个形参后加上三点 **…**，就表示该形参可以接受多个参数值，多个参数值被当成数组传入。上述定义有几个要点需要注意：
{: id="20210408143813-0kne9g0"}

* {: id="20210408143813-4owen9t"}可变参数只能作为函数的最后一个参数，但其前面可以有也可以没有任何其他参数
  {: id="20210408143813-3y3rdai"}
* {: id="20210408143813-5ypbxdb"}由于可变参数必须是最后一个参数，所以一个函数最多只能有一个可变参数
  {: id="20210408143813-07dnqi5"}
* {: id="20210408143813-dr3z2i5"}Java的可变参数，会被编译器转型为一个数组
  {: id="20210408143813-soey96h"}
* {: id="20210408143813-ost87f2"}变长参数在编译为字节码后，在方法签名中就是以数组形态出现的。这两个方法的签名是一致的，不能作为方法的重载。如果同时出现，是不能编译通过的。可变参数可以兼容数组，反之则不成立
  {: id="20210408143813-v5telyy"}

  ```
  public void foo(String...varargs){}

  foo("arg1", "arg2", "arg3");

  //上述过程和下面的调用是等价的
  foo(new String[]{"arg1", "arg2", "arg3"});
  ```
  {: id="20210408143813-apj9qcg"}
* {: id="20210408143813-ovfnfqf"}J2SE 1.5 中新增了**"泛型"** 的机制，可以在一定条件下把一个类型参数化。例如，可以在编写一个类的时候，把一个方法的形参的类型用一个标识符（如T）来代表， 至于这个标识符到底表示什么类型，则在生成这个类的实例的时候再行指定。这一机制可以用来提供更充分的代码重用和更严格的编译时类型检查。不过泛型机制却不能和个数可变的形参配合使用。如果把一个能和不确定个实参相匹配的形参的类型，用一个标识符来代表，那么编译器会给出一个 **"generic array creation"** 的错误
  {: id="20210408143813-i67i6k5"}

  ```
  public class Varargs {

      public static void test(String... args) {
          for(String arg : args) {//当作数组用foreach遍历
              System.out.println(arg);
          }
      }
      //Compile error
      //The variable argument type Object of the method must be the last parameter
      //public void error1(String... args, Object o) {}
      //public void error2(String... args, Integer... i) {}

      //Compile error
      //Duplicate method test(String...) in type Varargs
      //public void test(String[] args){}
  }
  ```
  {: id="20210408143813-8k3gs6o"}
{: id="20210408143813-v5h4i2h"}

---
{: id="20210408143813-xr8sb99"}

## 可变参数方法的调用
{: id="20210408143813-xz9ltqz"}

调用可变参数方法，可以给出零到任意多个参数，编译器会将可变参数转化为一个数组。也可以直接传递一个数组，示例如下:
{: id="20210408143813-uo3df0o"}

```
public class Varargs {

    public static void test(String... args) {
        for(String arg : args) {
            System.out.println(arg);
        }
    }

    public static void main(String[] args) {
        test();//0个参数
        test("a");//1个参数
        test("a","b");//多个参数
        test(new String[] {"a", "b", "c"});//直接传递数组
    }
}
```
{: id="20210408143813-ag0iia8"}

---
{: id="20210408143813-fd7rum6"}

## 方法重载
{: id="20210408143813-87mirh5" updated="20210408143932"}

### 优先匹配固定参数
{: id="20210408143813-k0o07h8"}

调用一个被重载的方法时，如果此调用既能够和固定参数的重载方法匹配，也能够与可变长参数的重载方法匹配，则选择固定参数的方法:
{: id="20210408143813-fmq6bha" updated="20210408143920"}

```java
public class Varargs {

    public static void test(String... args) {
        System.out.println("version 1");
    }

    public static void test(String arg1, String arg2) {
        System.out.println("version 2");
    }
    public static void main(String[] args) {
        test("a","b");//version 2 优先匹配固定参数的重载方法
                test();//version 1
    }
}
```
{: id="20210408143813-tzgbucv" updated="20210408143846"}

### 匹配多个可变参数
{: id="20210408143813-4dftsts" updated="20210408143942"}

调用一个被重载的方法时，如果此调用既能够和两个可变长参数的重载方法匹配，则编译出错:
{: id="20210408143813-mb3fd1b"}

```
public class Varargs {

    public static void test(String... args) {
        System.out.println("version 1");
    }

    public static void test(String arg1, String... arg2) {
        System.out.println("version 2");
    }
    public static void main(String[] args) {
        test("a","b");//Compile error
    }
}
```
{: id="20210408143813-wc0xwci"}

---
{: id="20210408143813-k2ghsww"}

## 方法重写
{: id="20210408143813-0ahzuta" updated="20210408144053"}

### 避免带有变长参数的方法重载
{: id="20210408143813-vu8lwrj"}

即便编译器可以按照优先匹配固定参数的方式确定具体的调用方法，但在阅读代码的依然容易掉入陷阱。要慎重考虑变长参数的方法重载。
{: id="20210408143813-4eud6a5"}

### 别让 null 值和空值威胁到变长方法
{: id="20210408143813-blmfhlx"}

```
public class Client {  
     public void methodA(String str,Integer... is){   
     }  

     public void methodA(String str,String... strs){  
     }  

     public static void main(String[] args) {  
           Client client = new Client();  
           client.methodA("China", 0);  
           client.methodA("China", "People");  
           client.methodA("China");  //compile error
           client.methodA("China",null);  //compile error
     }  
}
```
{: id="20210408143813-7ztrlf0"}

修改如下：
{: id="20210408143813-fysgomn"}

```
public static void main(String[] args) {  
     Client client = new Client();  
     String[] strs = null;  
     client.methodA("China",strs);  
}
```
{: id="20210408143813-1lg4h41"}

让编译器知道这个null值是String类型的，编译即可顺利通过，也就减少了错误的发生。
{: id="20210408143813-funnmeh"}

### 覆写变长方法也要循规蹈矩
{: id="20210408143813-6zngzds"}

```
package com;
public class VarArgsTest2 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 向上转型
        Base base = new Sub();
        base.print("hello");
        // 不转型
        Sub sub = new Sub();
        sub.print("hello");//compile error
    }
}
// 基类
class Base {
    void print(String... args) {
        System.out.println("Base......test");
    }
}
// 子类，覆写父类方法
class Sub extends Base {
    @Override
    void print(String[] args) {
        System.out.println("Sub......test");
    }
}
```
{: id="20210408143813-o8q5o4c"}

第一个能编译通过，这是为什么呢？事实上，base 对象把子类对象 sub 做了向上转型，形参列表是由父类决定的，当然能通过。而看看子类直接调用的情况，这时编译器看到子类覆写了父类的 print 方法，因此肯定使用子类重新定义的 print 方法，尽管参数列表不匹配也不会跑到父类再去匹配下，因为找到了就不再找了，因此有了类型不匹配的错误。
{: id="20210408143813-vfdcu2q"}

这是个特例，覆写的方法参数列表竟然可以与父类不相同，这违背了覆写的定义，并且会引发莫名其妙的错误。
{: id="20210408143813-9g5jk9h"}

这里，总结下覆写必须满足的条件：
{: id="20210408143813-834jr0k"}

* {: id="20210408143813-5ds6ica"}覆写方法不能缩小访问权限
  {: id="20210408143813-fvm6e6i"}
* {: id="20210408143813-kyx6bwj"}参数列表必须与被覆写方法相同（包括显示形式）
  {: id="20210408143813-ojmifxy"}
* {: id="20210408143813-oxxbqx9"}返回类型必须与被覆写方法的相同或是其子类
  {: id="20210408143813-svxm70c"}
* {: id="20210408143813-615fu22"}覆写方法不能抛出新的检查异常，或者超出父类范围的异常，但是可以抛出更少、更有限的异常，或者不抛出异常
  {: id="20210408143813-iqaj7u3"}
{: id="20210408143813-b4pvc1w"}

---
{: id="20210408143813-48q7ie8"}

## 可能出现的问题
{: id="20210408143813-zjvmeqr"}

使用 **Object…** 作为变长参数：
{: id="20210408143813-g8xmsvy"}

```
public void foo(Object... args) {
    System.out.println(args.length);
}

foo(new String[]{"arg1", "arg2", "arg3"}); //3
foo(100, new String[]{"arg1", "arg1"}); //2

foo(new Integer[]{1, 2, 3}); //3
foo(100, new Integer[]{1, 2, 3}); //2
foo(1, 2, 3); //3
foo(new int[]{1, 2, 3}); //1
```
{: id="20210408143813-jldp5c1"}

**int[]** 无法转型为 **Object[]**, 因而被当作一个单纯的数组对象 ; **Integer[]** 可以转型为 **Object[]**, 可以作为一个对象数组。
{: id="20210408143813-7g0i0tc"}

---
{: id="20210408143813-4ruhn0m"}

## 反射方法调用时的注意事项
{: id="20210408143813-jef6be5"}

```
public class Test {
    public static void foo(String... varargs){
        System.out.println(args.length);
    }

    public static void main(String[] args){
        String[] varArgs = new String[]{"arg1", "arg2"};
        try{
            Method method = Test.class.getMethod("foo", String[].class);
            method.invoke(null, varArgs);
            method.invoke(null, (Object[])varArgs);
            method.invoke(null, (Object)varArgs);
            method.invoke(null, new Object[]{varArgs});
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
```
{: id="20210408143813-ce5y2ey"}

上面的四个调用中，前两个都会在运行时抛出 **java.lang.IllegalArgumentException: wrong number of arguments** 异常，后两个则正常调用。
{: id="20210408143813-myuukp7"}

反射是运行时获取的，在运行时看来，可变长参数和数组是一致的，因而方法签名为：
{: id="20210408143813-he6zxxk"}

```
//方法签名
([Ljava/lang/String;)V // public void foo(String[] varargs)
```
{: id="20210408143813-deunu9v"}

再来看一下 Method 对象的方法声明：
{: id="20210408143813-8wstwx8"}

```
Object invoke(Object obj, Object... args)
```
{: id="20210408143813-rnunzfl"}

args 虽然是一个可变长度的参数，但是 args 的长度是受限于该方法对象代表的真实方法的参数列表长度的，而从运行时签名来看，([Ljava/lang/String;)V 实际上只有一个形参，即 String[] varargs，因而 invoke(Object obj, Object… args) 中可变参数 args 的实参长度只能为1
{: id="20210408143813-ywmpvx6"}

```
//Object invoke(Object obj, Object... args)
//String[] varArgs = new String[]{"arg1", "arg2"};
method.invoke(null, varArgs); //varArgs长度为2，错误
method.invoke(null, (Object[])varArgs); //将String[]转换为Object[],长度为2的，错误
method.invoke(null, (Object)varArgs);//将整个String[] 转为Object，长度为1，符合
method.invoke(null, new Object[]{varArgs});//Object[]长度为1，正确。上一个和这个是等价的
```
{: id="20210408143813-1q0wv9c"}

---
{: id="20210408143813-54zy5t0"}

## 什么时候使用可变长参数？
{: id="20210408143813-vcxie1d"}

[Stack Overflow](http://stackoverflow.com/questions/766559/when-do-you-use-varargs-in-java) 上有个关于变长参数使用的问题。简单地说，
在不确定方法需要处理的对象的数量时可以使用可变长参数，会使得方法调用更简单，无需手动创建数组 **new T[]{…}
{: id="20210408143813-u8tgwik" updated="20210409162341"}

{: id="20210409162342-djs1b34"}

{: id="20210409162342-vm3gkeu" updated="20210409162342"}


{: id="20210408143806-bb9z0iy" type="doc"}
