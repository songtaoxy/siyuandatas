# 到底什么是`classpath`？
{: id="20210411204808-rqbqdn7" updated="20210411235211"}

`classpath`是JVM用到的一个环境变量，它用来指示JVM如何搜索`class`。
{: id="20210411204808-ox5wx7d"}

因为Java是编译型语言，源码文件是`.java`，而编译后的`.class`文件才是真正可以被JVM执行的字节码。因此，JVM需要知道，如果要加载一个`abc.xyz.Hello`的类，应该去哪搜索对应的`Hello.class`文件。
{: id="20210411204808-la086et" updated="20210411235235"}

{: id="20210411235237-5ghr6u8"}

所以，`classpath`就是一组目录的集合，它设置的搜索路径与操作系统相关。例如，在Windows系统上，用`;`分隔，带空格的目录用`""`括起来，可能长这样：
{: id="20210411204808-hfo1tp6"}

```
C:\work\project1\bin;C:\shared;"D:\My Documents\project1\bin"
```
{: id="20210411204808-goxbnht"}

在Linux系统上，用`:`分隔，可能长这样：
{: id="20210411204808-49oglu0"}

```
/usr/shared:/usr/local/bin:/home/liaoxuefeng/bin
```
{: id="20210411204808-q6eopxf"}

{: id="20210411235252-e7pz8di"}

现在假设`classpath`是`.;C:\work\project1\bin;C:\shared`，当JVM在加载`abc.xyz.Hello`这个类时，会依次查找：
{: id="20210411204808-rt1kqhd" updated="20210411235252"}

* {: id="20210411204808-uc1meaf"}<当前目录>\abc\xyz\Hello.class
  {: id="20210411204808-hsjk00y"}
* {: id="20210411204808-jozti2g"}C:\work\project1\bin\abc\xyz\Hello.class
  {: id="20210411204808-aycs3xc"}
* {: id="20210411204808-37jlixp"}C:\shared\abc\xyz\Hello.class
  {: id="20210411204808-ykseler"}
{: id="20210411204808-5whz1un"}

注意到`.`代表当前目录。如果JVM在某个路径下找到了对应的`class`文件，就不再往后继续搜索。如果所有路径下都没有找到，就报错。
{: id="20210411204808-0oq8tiv" updated="20210412001946"}

# 如何设置`classpath`?
{: id="20210411204808-95tiei0" updated="20210411235341"}

## 如何设置`classpath`? 方式一
{: id="20210411235345-5ov1pul" updated="20210411235410"}

在系统环境变量中设置`classpath`环境变量，不推荐；
{: id="20210411204808-m4a0mkq"}

比如, win中, 设置环境变量; 或者, Linux中, 在.bashrc中设置classpath
{: id="20210411235622-7ohbhss" updated="20210411235646"}

强烈*不推荐* 在系统环境变量中设置`classpath`，那样会污染整个系统环境。在运行Java程序时设置`classpath`变量才是推荐的做法。
{: id="20210412000448-zivoy7e"}

## 如何设置`classpath`? 方式二
{: id="20210411235415-iyimdtd" updated="20210411235458"}

在运行Java程序时设置`classpath`变量，推荐。
{: id="20210411204808-duwd8q5" updated="20210412000520"}

强烈*不推荐* 在系统环境变量中设置`classpath`，那样会污染整个系统环境。在运行Java程序时设置`classpath`变量才是推荐的做法。
{: id="20210411204808-fgj110m" updated="20210412000502"}

{: id="20210412011849-dujsjzz"}

写脚本，在脚本里设置classpath，只在当前脚本内有效，不会污染系统. 参考((20210412011909-tui4ioh "{{.text}}")) ，((20210412011921-51aam5t "{{.text}}")) 这些大型项目的启动运行，都是在脚本里设置classpath的. 以及((20210412011949-2mo8857 "{{.text}}")) 工具, 也是通过脚本来执行程序的, see ((20210411201036-nk3yx38 "{{.text}}"))
{: id="20210412011851-lvxmy1c" updated="20210412012025"}


### 命令行中, 如何设置classpath?
{: id="20210412000506-jzqgmn9" updated="20210412011206"}

具体做法: 给`java`命令传入`-classpath`或`-cp`参数即可(cp是claaapath的缩写), see((20210411201036-nk3yx38 "{{.text}}"))：
{: id="20210412000454-6juhxjr" updated="20210412011111"}

```
java -classpath .;C:\work\project1\bin;C:\shared abc.xyz.Hello
```
{: id="20210411204808-v4k9ijn"}

或者使用`-cp`的简写：
{: id="20210411204808-8n29awr"}

```
java -cp .;C:\work\project1\bin;C:\shared abc.xyz.Hello
```
{: id="20210411204808-o4jv5jx"}

没有设置系统环境变量，也没有传入`-cp`参数，那么JVM默认的`classpath`为`.`，即当前目录：
{: id="20210411204808-zmq4jv5"}

```
java abc.xyz.Hello
```
{: id="20210411204808-1d1pv5c"}

上述命令告诉JVM只在当前目录搜索`Hello.class`。
{: id="20210411204808-u8pbkzv"}

### 脚本中,如何设置classpath?
{: id="20210411235502-fdu4ceu" updated="20210412011223"}

同((20210412000506-jzqgmn9 "{{.text}}"))
{: id="20210412011227-8c5mij3" updated="20210412011241"}

### idea中, 如何设置classpath?
{: id="20210412011247-vtf87mo" updated="20210412011303"}

see ((20210412010647-zkenhj5 "{{.text}}")), and ((20210411201036-nk3yx38 "{{.text}}"))
{: id="20210412011304-su1sdfx" updated="20210412011319"}

在IDE中运行Java程序，IDE自动传入的`-cp`参数是当前工程的`bin`目录和引入的jar包。see ((20210411201036-nk3yx38 "{{.text}}"))
{: id="20210411204808-m8g0mhk" updated="20210411235807"}

#  ((20210412020155-twx181m "{{.text}}"))
{: id="20210412020125-d8adfzq" updated="20210412020212"}

# 设置JDK时, 是否需要带上JDK?
{: id="20210411235812-p5766wr" updated="20210412000849"}

通常，自己编写的`class`中，会引用Java核心库的`class`，例如，`String`、`ArrayList`等。这些`class`应该上哪去找？
{: id="20210411204808-uoosd21" updated="20210412000859"}

很多“如何设置classpath”的文章会告诉你把JVM自带的`rt.jar`放入`classpath`，但事实上，根本不需要告诉JVM如何去Java核心库查找`class`，JVM怎么可能笨到连自己的核心库在哪都不知道？
{: id="20210411204808-8p84quj" updated="20210412000910"}

不要把任何Java核心库添加到classpath中！JVM根本不依赖classpath加载核心库！
{: id="20210411204808-jjapehv"}

为什么? 因为,一般的环境设置是(Linux环境):
{: id="20210412001511-08l99n6" updated="20210412001519"}

- {: id="20210412001047-33rhp1k"}如果是自己开发程序, 则自己本地中会安装JDK,在`.zshrc`设置JDK路径. 比如,  `export JDK8_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/"`
  {: id="20210412001047-u5feukj" updated="20210412001615"}
{: id="20210412001045-pl5pz0u" updated="20210412001047"}

- {: id="20210412001408-6bkfr7w"}如果将程序部署到某个生产环境, 该环境肯定要安装JDK, 并在`.zshr`中设置JDK路径. 同上.
  {: id="20210412001414-hs59qx1" updated="20210412001627"}
- {: id="20210412001408-8i485mg"}如果是自己开发程序, 开发工具是idea, idea会要求指定project或module的JDK版本. idea在运行程序时, 也会在`-cp`中自动加入JDK路径. see ((20210411201036-nk3yx38 "{{.text}}"))
  {: id="20210412001408-d0pjd0a" updated="20210412003233"}
{: id="20210412001019-9p5fje4" updated="20210412001414"}

# ((20210412003115-4idwdz6 "{{.text}}"))
{: id="20210412002007-duzqyxb" updated="20210412003226"}

# jar包
{: id="20210411204808-bxi9r58" updated="20210412003220"}

如果有很多`.class`文件，散落在各层目录中，肯定不便于管理。如果能把目录打一个包，变成一个文件，就方便多了。
{: id="20210411204808-knd419a"}

{: id="20210411235917-f8i617a"}

jar包就是用来干这个事的，它可以把`package`组织的目录层级，以及各个目录下的所有文件（包括`.class`文件和其他文件）都打成一个jar文件，这样一来，无论是备份，还是发给客户，就简单多了。
{: id="20210411204808-9vtyi2s"}

jar包实际上就是一个zip格式的压缩文件，而jar包相当于目录。如果我们要执行一个jar包的`class`，就可以把jar包放到`classpath`中：
{: id="20210411204808-vja0o1l"}

```
java -cp ./hello.jar abc.xyz.Hello
```
{: id="20210411204808-w37jhpd"}

这样JVM会自动在`hello.jar`文件里去搜索某个类。
{: id="20210411204808-h2q46xj" updated="20210411235945"}

那么问题来了：如何创建jar包？
{: id="20210411204808-a3w9org"}

## 如何创建jar包？
{: id="20210411235946-tsx9prp" updated="20210411235958"}

因为jar包就是zip包，所以，直接在资源管理器中，找到正确的目录，点击右键，在弹出的快捷菜单中选择“发送到”，“压缩(zipped)文件夹”，就制作了一个zip文件。然后，把后缀从`.zip`改为`.jar`，一个jar包就创建成功。
{: id="20210411204808-e8q5vmv"}

{: id="20210412000107-l2en8xl"}

假设编译输出的目录结构是这样：
{: id="20210411204808-hn6kmbw"}

```bash
package_sample
└─ bin
   ├─ hong
   │  └─ Person.class
   │  ming
   │  └─ Person.class
   └─ mr
      └─ jun
         └─ Arrays.class
```
{: id="20210411204808-w41ewex"}

需要注意的是，jar包里的第一层目录，不能是`bin`，而应该是`hong`、`ming`、`mr`。
{: id="20210411204808-pcsfh9c" updated="20210412000116"}

否则, 说明打包打得有问题，JVM仍然无法从jar包中查找正确的`class`，原因是`hong.Person`必须按`hong/Person.class`存放，而不是`bin/hong/Person.class`。
{: id="20210411204808-ai2k2kw" updated="20210411234629"}

![image.png](assets/image-20210411234605-swdi5cb.png)
{: id="20210411234530-mdi5l7o" updated="20210411234605"}

jar包还可以包含一个特殊的`/META-INF/MANIFEST.MF`文件，`MANIFEST.MF`是纯文本，可以指定`Main-Class`和其它信息。JVM会自动读取这个`MANIFEST.MF`文件，如果存在`Main-Class`，我们就不必在命令行指定启动的类名，而是用更方便的命令：
{: id="20210411204808-d80g50z"}

```

java -jar hello.jar
```
{: id="20210411204808-i2173c3" updated="20210411234744"}

jar包还可以包含其它jar包，这个时候，就需要在`MANIFEST.MF`文件里配置`classpath`了。
{: id="20210411204808-zp35quh"}

在大型项目中，不可能手动编写`MANIFEST.MF`文件，再手动创建zip包。Java社区提供了大量的开源构建工具，如((20210411234850-qt7gmao "{{.text}}")) 等, 可以非常方便地创建jar包。
{: id="20210411204808-c2e74oy" updated="20210411234853"}

# 小结
{: id="20210411204808-l38g1va" updated="20210411235729"}

- {: id="20210412000141-jh74gw2"}JVM通过环境变量`classpath`决定搜索`class`的路径和顺序；
  {: id="20210412000141-10eaxtb"}
{: id="20210411204808-8kpra0e" updated="20210412000141"}

- {: id="20210412000142-x4071jn"}不推荐设置系统环境变量`classpath`，始终建议通过`-cp`命令传入, see ((20210411201036-nk3yx38 "{{.text}}")) ；
  {: id="20210412000142-k5vk5p5"}
{: id="20210411204808-9zcvnvd" updated="20210412000142"}

- {: id="20210412000147-scteq8w"}jar包相当于目录，可以包含很多`.class`文件，方便下载和使用；
  {: id="20210412000147-nmvecmq"}
{: id="20210411204808-k137dvh" updated="20210412000147"}

- {: id="20210412000151-vb3nz0t"}`MANIFEST.MF`文件可以提供jar包的信息，如`Main-Class`，这样可以直接运行jar包。
  {: id="20210412000151-tu7foat"}
{: id="20210411204808-vwrt3oy" updated="20210412000151"}

{: id="20210411235730-mmj7knu"}


{: id="20210411195419-kdb9xri" type="doc"}
