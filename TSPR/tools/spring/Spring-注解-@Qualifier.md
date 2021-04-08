## Issue
{: id="20210408134420-tdz9ges" updated="20210408135051"}

实际工作中, 遇到下面的注解, 最开始没有懂:
{: id="20210408134812-fzwnjm1" updated="20210408135000"}

* {: id="20210408134906-jvn9t4b"}`@Service("mddDefaultReportService")`
  {: id="20210408134859-2mcuggw" updated="20210408134922"}
* {: id="20210408134907-vecv4ji"}`@Qualifier("reportMetadataImpl-service")`
  {: id="20210408134922-r9wvk6g" updated="20210408134936"}
{: id="20210408134906-3xznc33" updated="20210408134934"}

service
{: id="20210408134723-tf8ztpa" updated="20210408134731"}

```java
// /Users/songtao/personaldriveMac/Projects/yyprojects/ucf-mdf-pub-report/mdd-report-app/src/main/java/com/yonyou/ucf/mdf/app/service/impl/DefaultReportService.java

@Slf4j
@Service("mddDefaultReportService")
public class DefaultReportService implements IReportBaseService {

```
{: id="20210408134735-6878egr" updated="20210408134757"}

controller.java
{: id="20210408134434-5repk87" updated="20210408134645"}

```java
// /Users/songtao/personaldriveMac/Projects/yyprojects/ucf-mdf-pub-report/mdd-report-app/src/main/java/com/yonyou/ucf/mdf/app/controller/reportform/YonqlReportController.java

 @Autowired
 @Qualifier("reportMetadataImpl-service")
 ReportMetadataService reportMetadataService;

```
{: id="20210408134649-fvci5mk" updated="20210408134716"}

## 1. 概述
{: id="20210408134159-i493dlj" updated="20210408134420"}

今天带你了解一下 **Spring** 框架中的 `@Qualifier` 注解，它解决了哪些问题，以及如何使用它。我们还将了解它与 `@Primary` 注解的不同之处。更多的技术解析请访问 [felord.cn](https://felord.cn)
{: id="20210408134159-gkmsob2"}

## 2. 痛点
{: id="20210408134159-tnj6a2e"}

使用 `@Autowired` 注解是 **Spring** 依赖注入的绝好方法。但是有些场景下仅仅靠这个注解不足以让Spring知道到底要注入哪个 **bean** 。
默认情况下，`@Autowired` 按类型装配 **Spring Bean** 。
如果容器中有多个相同类型的 **bean** ，则框架将抛出 `NoUniqueBeanDefinitionException`，  以提示有多个满足条件的 **bean** 进行自动装配。程序无法正确做出判断使用哪一个，下面就是个鲜活的例子：
{: id="20210408134159-311o9gn"}

```
    @Component("fooFormatter")
    public class FooFormatter implements Formatter {
        public String format() {
            return "foo";
        }
    }

    @Component("barFormatter")
    public class BarFormatter implements Formatter {
        public String format() {
            return "bar";
        }
    }

    @Component
    public class FooService {
        @Autowired
        private Formatter formatter;
    
        //todo 
    }
复制代码
```
{: id="20210408134159-hhsn2xy"}

如果我们尝试将 `FooService` 加载到我们的上下文中，**Spring** 框架将抛出 `NoUniqueBeanDefinitionException`。这是因为 **Spring** 不知道要注入哪个 **bean** 。为了避免这个问题，有几种解决方案。那么我们本文要讲解的 `@Qualifier` 注解就是其中之一。跟着小胖哥的节奏往下走。
{: id="20210408134159-xyl9w95"}

## 3. @Qualifier
{: id="20210408134159-w7508is"}

通过使用 `@Qualifier` 注解，我们可以消除需要注入哪个 **bean** 的问题。让我们重新回顾一下前面的例子，看看我们如何通过包含 `@Qualifier` 注释来指出我们想要使用哪个 **bean** 来解决问题：
{: id="20210408134159-ff2dm33"}

```
    @Component
    public class FooService {
        @Autowired
        @Qualifier("fooFormatter")
        private Formatter formatter;
    
        //todo 
    }
复制代码
```
{: id="20210408134159-4vjus63"}

通过将 `@Qualifier` 注解与我们想要使用的特定 **Spring bean** 的名称一起进行装配，**Spring** 框架就能从多个相同类型并满足装配要求的 **bean** 中找到我们想要的，避免让Spring脑裂。我们需要做的是@Component或者@Bean注解中声明的value属性以确定名称。
其实我们也可以在 `Formatter` 实现类上使用 `@Qualifier` 注释，而不是在 `@Component` 或者 `@Bean` 中指定名称，也能达到相同的效果：
{: id="20210408134159-djbovaj"}

```
     @Component
     @Qualifier("fooFormatter")
     public class FooFormatter implements Formatter {
         public String format() {
             return "foo";
         }
     }
 
     @Component
     @Qualifier("barFormatter")
     public class BarFormatter implements Formatter {
         public String format() {
             return "bar";
         }
     }
复制代码
```
{: id="20210408134159-82m2hz7"}

## 4. @Qualifier  VS  @Primary
{: id="20210408134159-aw01yq8"}

还有另一个名为 `@Primary` 的注解，我们也可以用来发生依赖注入的歧义时决定要注入哪个 **bean** 。当存在多个相同类型的 **bean** 时，此注解定义了首选项。除非另有说明，否则将使用与 `@Primary` 注释关联的 **bean** 。
我们来看一个例子：
{: id="20210408134159-a2b1ag2"}

```
    @Bean
    public Employee tomEmployee() {
        return new Employee("Tom");
    }

    @Bean
    @Primary
    public Employee johnEmployee() {
        return new Employee("john");
    }
复制代码
```
{: id="20210408134159-u01xl48"}

在此示例中，两个方法都返回相同的 `Employee`类型。**Spring** 将注入的 **bean** 是方法 `johnEmployee` 返回的 **bean** 。这是因为它包含 `@Primary` 注解。当我们想要指定默认情况下应该注入特定类型的 **bean** 时，此注解很有用。
如果我们在某个注入点需要另一个 **bean** ，我们需要专门指出它。我们可以通过 `@Qualifier` 注解来做到这一点。例如，我们可以通过使用 `@Qualifier` 注释来指定我们想要使用 `tomEmployee` 方法返回的 **bean** 。
值得注意的是，如果 `@Qualifier` 和 `@Primary` 注释都存在，那么 `@Qualifier` 注释将具有优先权。基本上，`@Primary` 是定义了默认值，而 `@Qualifier` 则非常具体。
当然`@Component` 也可以使用`@Primary` 注解，这次使用的还是上面3的示例：
{: id="20210408134159-amc2c29"}

```
     @Component
     @Primary
     public class FooFormatter implements Formatter {
         public String format() {
             return "foo";
         }
     }
 
     @Component
     public class BarFormatter implements Formatter {
         public String format() {
             return "bar";
         }
     }
复制代码
```
{: id="20210408134159-dk0ppkn"}

在这种情况下，`@Primary` 注解指定了默认注入的是 `FooFormatter`，消除了场景中的注入歧义。
{: id="20210408134159-dmo7484"}

## 5. 通过名称来自动注入
{: id="20210408134159-9j86e0w"}

在使用 `@Autowired` 进行自动装配时，如果 **Spring** 没有其他提示，将会按照需要注入的变量名称来寻找合适的 **bean** 。也可以解决依赖注入歧义的问题。让我们看一些基于我们最初的例子的代码：
{: id="20210408134159-uq85le2"}

```
    @Component
    public class FooService {
        @Autowired
        private Formatter fooFormatter;
    
        //todo 
    }
复制代码
```
{: id="20210408134159-5ucedjr"}

在这种情况下，**Spring** 将确定要注入的 **bean** 是 `FooFormatter`，因为字段名称与我们在该 **bean** 的 `@Component`或者 `@Bean` 注解中使用的值(默认 `@Bean` 使用方法名)相匹配。
{: id="20210408134159-z9eqr2k"}

## 6. 总结
{: id="20210408134159-pwopjzd"}

通过对 `@Qualifier` 的探讨，我们知道该注解是用来消除依赖注入冲突的。这种在日常开发，比如 **Rabbtimq** 的队列声明中很常见。小胖哥也通过该注解和其他上述注解的组合使用和对比中展示了一些常用的用法。这将有助于你对 **Spring** 的依赖注入机制的了解
{: id="20210408134159-blbud4x"}

{: id="20210408134159-276w6kb" updated="20210408134258"}


{: id="20210408134155-z5zg22l" type="doc"}
