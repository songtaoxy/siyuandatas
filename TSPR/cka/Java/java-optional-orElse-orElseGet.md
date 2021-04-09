{: id="20210408145910-bld2q64" updated="20210408145924"}

# Java中orElse()和orElseGet()的区别
{: id="20210408145926-gikxtmv" updated="20210408145942"}

# Conclusion
{: id="20210408150035-e6x1yj9" updated="20210408150314"}

orElse: optional有值, 或没有值, 都会执行
{: id="20210408150111-m7nvbft" updated="20210408150141"}

orElseGet: optional有值, 不执行; optional为空时，才执行. #Java# 
{: id="20210408150154-km56t8g" updated="20210409152617"}

{: id="20210408151131-q0kyb2f"}

# 基本使用: orElse, orElseGet 
{: id="20210408151131-0kgybeq" updated="20210409160236"}

两者之间的区别细微, 但是却在某些场景下显的很重要.
首先, 这是两个在 java.util.Optional 类中的方法: 源码非常简单,
{: id="20210408151239-55hkj5g"}

```
package java.util;
public final class Optional<T> {
   ...

// 函数式借口, 有个抽象方法: get
@FunctionalInterface
public interface Supplier<T> {

    T get();
}



// set 值的很是
// 构造器私有, 不能被外界调用. 如果想构造, 使用of || ofNullable
private Optional(T value) {
        this.value = Objects.requireNonNull(value);
    }

   
public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }  

  
public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }  



// 获取值的方式 

public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }  

 

public T orElse(T other) {
        return value != null ? value : other;
    }

  
public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }  


```
{: id="20210408151239-13iye1u" updated="20210408152311"}

* {: id="20210408151239-eiufmex"}`Optional`相当于一个容器, 可以一个接收的对象`value`, 用泛型指定类型, 并且该对象有可能为空.
  {: id="20210408151239-mf091lo"}
* {: id="20210408151239-tk2z2n0"}`Supplier`是一个函数式接口, 只定义了一个返回值为`T`类型的`get()`方法, 用于产生 (提供) 对象, 可以使用 lambda 表达式定义实现
  {: id="20210408151239-lyobbvk"}
* {: id="20210408151239-dd1zhg5"}`orElse(T other)` 不论容器是否为空, 只要调用该方法, 则对象`other`一定存在
  {: id="20210408151239-entvndz"}
* {: id="20210408151239-748kvvi"}`orElseGet(Supplier<? extends T> supplier)` 只有当容器为空时, 才调用`supplier.get()`方法产生对象
  {: id="20210408151239-qdw25cd"}
{: id="20210408151239-91sww8v"}

请想象以下场景:
{: id="20210408151239-vhc81ms"}

> 在分布式开发中, 有一个方法需要接收前台数据, 但该数据可能为空,
> 当该数据为空时, 需要远程调用一个方法为其设置默认值
> {: id="20210408151239-5h3wu47" updated="20210408152603"}
{: id="20210408151239-jozmh10"}

```
    public String getDefaultValue(){  //远程方法调用
        System.out.println("我被调用了!");
        return "我是默认值";
    }


    @Test
    public void testSupplier(){
        Optional<String> opt = Optional.of("前端数据");

        String x = opt.orElse( getDefaultValue() );  
        System.out.println("---以上为orElse调用,以下为orElseGet调用---");



     // lambda表达式: 
     // 匿名
     // 没有类的声明等要素, 只是提供了方法
        String y = opt.orElseGet( ()->getDefaultValue() );
    }


```
{: id="20210408151239-mehtgwl" updated="20210408152935"}

注意上面((20210408152753-x63rgak "{{.text}}")) , `String y = opt.orElseGet( ()->getDefaultValue() );`
{: id="20210408152624-hp7593d" updated="20210408152914"}

运行结果
{: id="20210408151239-ilkhtjm" updated="20210408152624"}

```
我被调用了!
---以上为orElse调用,以下为orElseGet调用---


```
{: id="20210408151239-ur2f4nt"}

结果为`orElse`调用方法,`orElseGet`没有调用方法
{: id="20210408151239-luteyk2"}

* {: id="20210408151239-r17x6vd"}可以看到, 虽然`opt`不为空, 但是`orElse()`依然调用了远程方法, 并产生了一个 String 对象
  {: id="20210408151239-dgixr43"}
* {: id="20210408151239-anc0k5d"}`orElseGet`并没有调用方法, 也没有产生任何对象
  {: id="20210408151239-qt6crro"}
{: id="20210408151239-zgqdmar"}

所以可见`orElseGet()`更优, 但代价就是需要传入一个`Supplier<T>`类型的参数, 相对会麻烦一些.
{: id="20210408151239-n7vbl84" updated="20210408151314"}

# orElse()
{: id="20210408145926-j0q5hsl" updated="20210408150317"}

当optional值不存在时，调用orElse()返回orElse()的参数，如果optional的值存在时返回optional的值
{: id="20210408145926-bxqar6o"}

# orElseGet()
{: id="20210408145926-fkpiil9" updated="20210408150319"}

当optional值不存在时，调用orElseGet()中接口调用的返回值，如果optional的值存在时返回optional的值，例子如下：
{: id="20210408145926-mhrfrht"}

```swift
import java.util.Optional;

public class Main {
    public static void main(String[] args){
        String nullValue = null;
        String optional = Optional.ofNullable(nullValue).orElse("Su");
        System.out.println(optional);
        String optionalGet = Optional.ofNullable(nullValue).orElseGet(() -> "Xiao");
        System.out.println(optionalGet);
        String nonNullOptional = Optional.ofNullable("Susan").orElse("Su");
        System.out.println(nonNullOptional);
        String nonNullOptionalGet = Optional.ofNullable("Molly").orElseGet(() -> "Xiao");
        System.out.println(nonNullOptionalGet);
    }
}

```
{: id="20210408145926-a0njyhy"}

结果：
{: id="20210408145926-lm8zu44"}

```undefined
Su
Xiao
Susan
Molly
```
{: id="20210408145926-1ajpurm"}

# orElse()和orElseGet()区别
{: id="20210408145926-nz7pbmd" updated="20210408150322"}

首先我们举例比较一下在optional有值和不存在值使用两种方法的样例：
{: id="20210408145926-ti4tiw4"}

## optional有值
{: id="20210408145926-4bcgq6c" updated="20210408150347"}

```swift
import java.util.Arrays;
import java.util.List;

public class orElseOrElseGetComparation {
    public static void main(String[] args){
        List<Integer> list = Arrays.asList(23,1,3);
        int myElse = list.stream().reduce(Integer::sum).orElse(get("myElse"));
        int myElseGet = list.stream().reduce(Integer::sum).orElseGet(() -> get("myElseGet"));
        System.out.println("myElse的值"+myElse);
        System.out.println("myElseGet的值"+myElseGet);

    }
    public static int get(String name){
        System.out.println(name+"执行了该方法");
        return 1;
    }
}

```
{: id="20210408145926-6fh08z0"}

结果：
{: id="20210408145926-vx4ch1g"}

```undefined
myElse执行了该方法
myElse的值27
myElseGet的值27
```
{: id="20210408145926-jmu7jcr"}

## optional为空值
{: id="20210408145926-7sg73lk" updated="20210408150351"}

```swift
import java.util.Arrays;
import java.util.List;

public class orElseOrElseGetComparation {
    public static void main(String[] args){
        List<Integer> list = Arrays.asList();
        int myElse = list.stream().reduce(Integer::sum).orElse(get("myElse"));
        int myElseGet = list.stream().reduce(Integer::sum).orElseGet(() -> get("myElseGet"));
        System.out.println("myElse的值"+myElse);
        System.out.println("myElseGet的值"+myElseGet);

    }
    public static int get(String name){
        System.out.println(name+"执行了该方法");
        return 1;
    }
}

```
{: id="20210408145926-stn0i30"}

结果：
{: id="20210408145926-i1zesad"}

```undefined
myElse执行了该方法
myElseGet执行了该方法
myElse的值1
myElseGet的值1
```
{: id="20210408145926-nop1mwx"}

## 结论
{: id="20210408150357-5xio8sl" updated="20210408150413"}

从上面optional为空值和有值的情况的例子可以看到orElse在不论optional有没有值的时候都会执行，在optional为空值的情况下orElse和orElseGet都会执行，当optional不为空时，orElseGet不会执行。
{: id="20210408145926-ukj953o" updated="20210408150357"}

{: id="20210408145926-kdcbvg1"}


{: id="20210408145906-woz7y67" type="doc"}
