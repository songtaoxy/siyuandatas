# Java-instanceof
{: id="20210408234607-1d02rr8" updated="20210408234711"}

instanceof 是 Java 的一个二元操作符，类似于 ==，>，< 等操作符。
{: id="20210408234607-791e72m" updated="20210409145451"}

instanceof 是 Java 的保留关键字。它的作用是测试它左边的对象是否是它右边的类的实例，返回 boolean 的数据类型。
{: id="20210408234607-syryttp" updated="20210409145404"}

以下实例创建了 displayObjectClass() 方法来演示 Java instanceof 关键字用法：
{: id="20210408234607-pf7qmoy"}

## Main.java 文件代码：
{: id="20210408234607-raxsxjm"}

```java
/*
 author by runoob.com
 Main.java
 */
import java.util.ArrayList;
import java.util.Vector;
 
public class Main {
 
public static void main(String[] args) {
   Object testObject = new ArrayList();
      displayObjectClass(testObject);
   }
   public static void displayObjectClass(Object o) {
      if (o instanceof Vector)
      System.out.println("对象是 java.util.Vector 类的实例");
      else if (o instanceof ArrayList)
      System.out.println("对象是 java.util.ArrayList 类的实例");
      else
      System.out.println("对象是 " + o.getClass() + " 类的实例");
   }
}

```
{: id="20210408234638-ofdj3oe"}

以上代码运行输出结果为：
{: id="20210408234607-1l5ugmn"}

```
对象是 java.util.ArrayList 类的实例
```
{: id="20210408234607-y5xmd0c"}

{: id="20210409151102-jivy760"}

{: id="20210409145456-bjyw1i9" updated="20210409150641"}

{: id="20210409145500-6hi5b53"}

{: id="20210409145459-0mut4r4" updated="20210409145504"}

{: id="20210409145506-l8k0z5j"}


{: id="20210408234606-l5bwlpr" type="doc"}
