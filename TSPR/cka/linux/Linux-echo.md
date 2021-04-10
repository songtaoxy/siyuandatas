# Linux-printf-echo
{: id="20210409204855-i9d0m5r" updated="20210410014126"}

{: id="20210410014836-gvno5rx"}

# printf, 基本使用
{: id="20210409204909-2sbfi4h" updated="20210410015429"}

printf 命令模仿 C 程序库（library）里的 printf() 程序。
{: id="20210410014903-w2f2yqf"}

printf 由 POSIX 标准所定义，因此使用 printf 的脚本比使用 echo 移植性好。
{: id="20210410014903-bkq38yw"}

printf 使用引用文本或空格分隔的参数，外面可以在 printf 中使用格式化字符串，还可以制定字符串的宽度、左右对齐方式等。
{: id="20210410014903-gv2mvdg"}

默认 printf 不会像 echo 自动添加换行符，可以手动添加 \n。
{: id="20210410014910-ujmiwol" updated="20210410014921"}

```
$ echo "Hello, Shell"
Hello, Shell

$ printf "Hello, Shell"
Hello, Shell%

# 添加换行符号. \n
$ printf "Hello, Shell\n"
Hello, Shell
```
{: id="20210410014846-zto5hj4" updated="20210410014847"}

# printf, 格式化输出
{: id="20210409204853-z20y91z" updated="20210410014203"}

## Example
{: id="20210410014445-byb26a1" updated="20210410014519"}

printf一个很重要的特点是，可以格式化输出，使显示结果相对工整. see also ((20210409004809-x1l48mr "{{.text}}"))
{: id="20210410014502-q8ob18o" updated="20210410014708"}

```
#!/bin/bash
 
printf "%-10s %-8s %-4s\n" 姓名 性别 体重kg  
printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234
printf "%-10s %-8s %-4.2f\n" 杨过 男 48.6543
printf "%-10s %-8s %-4.2f\n" 郭芙 女 47.9876

# result
姓名     性别   体重kg
郭靖     男      66.12
杨过     男      48.65
郭芙     女      47.99

```
{: id="20210409204913-xvlurpr" updated="20210409204924"}

## 数据类型
{: id="20210410014532-0oz0yve" updated="20210410014628"}

{: id="20210410014628-g0jtup8"}

- {: id="20210410015321-uv6457t"}%s %c %d %f 都是格式替代符，**％s** 输出一个字符串，**％d** 整型输出，**％c** 输出一个字符，**％f** 输出实数，以小数形式输出。
  {: id="20210410015321-kus80sc" updated="20210410015358"}
{: id="20210410015316-iqufeeh" updated="20210410015321"}

- {: id="20210410015324-y11uo9c"}**%-10s** 指一个宽度为 10 个字符（**-** 表示左对齐，没有则表示右对齐），任何字符都会被显示在 10 个字符宽的字符内，如果不足则自动以空格填充，超过也会将内容全部显示出来。
  {: id="20210410015324-2lurs55"}
{: id="20210410015316-9mccmcr" updated="20210410015324"}

- {: id="20210410015328-od02d7q"}**%-4.2f** 指格式化为小数，其中 **.2** 指保留2位小数
  {: id="20210410015328-uzqdsb2" updated="20210410015347"}
{: id="20210410015316-n5gnrw3" updated="20210410015328"}

```
#!/bin/bash
# author:菜鸟教程
# url:www.runoob.com
 
# format-string为双引号
printf "%d %s\n" 1 "abc"

# 单引号与双引号效果一样
printf '%d %s\n' 1 "abc"

# 没有引号也可以输出
printf %s abcdef

# 格式只指定了一个参数，但多出的参数仍然会按照该格式输出，format-string 被重用
printf %s abc def

printf "%s\n" abc def

printf "%s %s %s\n" a b c d e f g h i j

# 如果没有 arguments，那么 %s 用NULL代替，%d 用 0 代替
printf "%s and %d \n"
```
{: id="20210410015641-a9h153e" updated="20210410015642"}

result
{: id="20210410015652-ngdze7g" updated="20210410015656"}

```
1 abc
1 abc
abcdefabcdefabc
def
a b c
d e f
g h i
j  
 and 0
```
{: id="20210410015656-6brsfca" updated="20210410015657"}

## 对齐
{: id="20210410014523-fm28r0n" updated="20210410015652"}

- {: id="20210410014545-m5uhj2t"}右对齐. 默认
  {: id="20210410014545-lmqfbxz" updated="20210410014549"}
{: id="20210410014544-q2g4bm3" updated="20210410014545"}

- {: id="20210410014536-1ev4k7t"}左对齐
  {: id="20210410014536-kdzz3wk" updated="20210410014552"}
- {: id="20210410014603-pewpqgw"}居中. bash不支持.
  {: id="20210410014603-1jj8cnt" updated="20210410014614"}
{: id="20210410014535-gdyn435" updated="20210410014536"}

## 填充字符
{: id="20210410014141-c710wmu" updated="20210410014528"}

默认填充空格,
{: id="20210410014530-nfs02mv" updated="20210410020309"}

自定义填充字符, 只支持数字
{: id="20210410020244-0yumk5a" updated="20210410020331"}

自定义填充字符, 不支持字符串. 如果需要, 则使用((20210410014244-r3r48ca "{{.text}}")), ((20210410014434-m07lx7m "{{.text}}")) 等命令联合使用实现
{: id="20210410020324-sgqoln3" updated="20210410020356"}

```
# songtao @ st in ~/stsh [1:49:40]
$ printf "%010d\n" 12
0000000012


# songtao @ st in ~/stsh [2:01:58]
$ printf "%010s\n" a
         a
```
{: id="20210410020244-waodeg6" updated="20210410020245"}

## 转义序列
{: id="20210409204926-750ays2" updated="20210410015416"}

| 序列  | 说明                                                                                                                                                                         |
| ------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| \a    | 警告字符，通常为ASCII的BEL字符                                                                                                                                               |
| \b    | 后退                                                                                                                                                                         |
| \c    | 抑制（不显示）输出结果中任何结尾的换行字符（只在%b格式指示符控制下的参数字符串中有效），而且，任何留在参数里的字符、任何接下来的参数以及任何留在格式字符串中的字符，都被忽略 |
| \f    | 换页（formfeed）                                                                                                                                                             |
| \n    | 换行                                                                                                                                                                         |
| \r    | 回车（Carriage return）                                                                                                                                                      |
| \t    | 水平制表符                                                                                                                                                                   |
| \v    | 垂直制表符                                                                                                                                                                   |
| \\     | 一个字面上的反斜杠字符                                                                                                                                                       |
| \ddd  | 表示1到3位数八进制值的字符。仅在格式字符串中有效                                                                                                                             |
| \0ddd | 表示1到3位的八进制值字符                                                                                                                                                     |
{: id="20210410015122-wmmi4sq"}

```
$ printf "a string, no processing:<%s>\n" "A\nB"
a string, no processing:<A\nB>

$ printf "a string, no processing:<%b>\n" "A\nB"
a string, no processing:<A
B>

$ printf "www.runoob.com \a"
www.runoob.com $                  #不换行
```
{: id="20210410015126-2mhr7ue" updated="20210410015133"}

{: id="20210410015135-de00wi5"}

## 格式字符
{: id="20210410015933-co1805d" updated="20210410020000"}

<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td><strong>格式符</strong></td><td><strong>说明</strong></td><td><strong>格式符</strong></td><td><strong>说明</strong></td></tr><tr><td>%a</td><td>将参数按 double 解释，并以 C99 十六进制浮点常量形式打印</td><td>%i</td><td>与 %d 相同</td></tr><tr><td>%A</td><td>与 %a 相同，但打印的格式化后字符串中字母大写</td><td>%n</td><td><p>将到目前为止打印的字符数赋值给对应参数中指定的变量。不能指定数组索引。如果指定的变量是数组，则会将值赋值给数组的第 0 个元素。</p></td></tr><tr><td>%b</td><td>将参数中支持的反斜杠转义字符转以后再打印</td><td>%o</td><td>将参数按无符号八进制数打印</td></tr><tr><td>%c</td><td>将参数按 char 解释，只打印参数的第一个字符</td><td>%q</td><td>将参数按可以做为 shell 输入重用的格式打印</td></tr><tr><td>%d</td><td>将参数按有符号十进制整数打印</td><td>%s</td><td>将参数按字面意思解释为字符串</td></tr><tr><td>%e</td><td>将参数按 double 解释，并以科学计数法的形式打印</td><td>%(FORMAT)T</td><td>输出使用 strftime(3) 格式化字符串 FORMAT 格式化后的日期 - 时间字符串。参数为自纪元起的时间秒数，此外有两个特殊值：-1 表示当前时间，-2 表示 shell 启动时间。如果没有提供参数，则默认使用当前时间。strftime(3) 日期时间格式字符串支持除 %N、%:z、%::z、%:::z 外的所有 <a href="https://blog.csdn.net/asty9000/article/details/86554824">date 命令日期时间输出格式符</a>。</td></tr><tr><td>%E</td><td>与 %e 相同，但打印的格式化后字符串中字母大写</td><td>%u</td><td>将参数按无符号小数打印</td></tr><tr><td>%f</td><td>将参数按浮点数打印</td><td>%x</td><td>将参数按无符号十六进制数打印</td></tr><tr><td>%g</td><td>将参数按 double 解释，并以 %e 或 %f 的形式打印</td><td>%X</td><td>与 %x 相同，但打印的格式化后字符串中字母大写</td></tr><tr><td>%G</td><td>与 %G 相同，但是 %E 的形式打印</td><td>%%</td><td>不做任何转换，输出百分号 %</td></tr></tbody></table>

{: id="20210410015933-orc4n1u"}

## 格式修饰符
{: id="20210410015933-s6ywdf5" updated="20210410020026"}

<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td><strong>修饰符</strong></td><td><strong>说明</strong></td></tr><tr><td><N></td><td>指定域最小宽度，如果打印的文本较短，则用空格填充，如果打印的文本较长，则正常打印。</td></tr><tr><td>.</td><td>与域宽度一起使用，当打印的文本较长时会截断文本。如果 “.” 后没有指定宽度，默认为 0，则不会打印文本。如果 “.” 前也指定了宽度，则会在截断文本后补足到指定的宽度。如：printf "%2.1s\n" abc。</td></tr><tr><td>*</td><td>宽度在字符串或数字之前通过参数指定，可以动态的指定打印文本的宽度。如：printf "%*s\n" 20 "test"。</td></tr><tr><td>#</td><td>数字格式选择符，见后面数字格式选择。</td></tr><tr><td><code>-</code></td><td>左对齐，即右侧填充字符，默认为右对齐。如：printf "%-*s\n" 20 "test"。</td></tr><tr><td>0</td><td>用零填充数字，而不是空格。如：printf "%010d\n" 123。</td></tr><tr><td>（空格）</td><td>如果杀死正数，前面加空格，如果是负数前面加 “-”。如：printf "% d\n" 123。</td></tr><tr><td>+</td><td>打印数字的符号，正数前加 “+”，负数前加 “-”。如：printf "%+d\n" 123。</td></tr><tr><td>'</td><td>将十进制数按当前 LC_NUMERIC 将千位分组分隔符应用于输出的整数部分，如：printf "%'d\n" 12345567890。</td></tr></tbody></table>

{: id="20210410015933-t3mjh6a"}

## 数字格式选择
{: id="20210410015933-5jnr44m" updated="20210410020034"}

<table border="1" cellpadding="1" cellspacing="1"><tbody><tr><td><strong>格式</strong></td><td><strong>说明</strong></td></tr><tr><td>%#o</td><td>八进制数总是以 0 开头，除非它本身是零，如：printf "%o\n%#o\n"   123 123。</td></tr><tr><td>%#x、%#X</td><td>十六进制数总是以 “0x”/“0x” 开头，除非它是零，如：printf "%x\n%#x\n"   123 123。</td></tr><tr><td>%#g、%#G</td><td>打印的浮点数后面跟随 0，直到满足精度所需的位数为止，如 printf "%g\n%#g\n"   123 123。</td></tr></tbody></table>

{: id="20210410015933-z0mbmd7"}

小数格式的精度是通过 “.” 后面跟着精度位数来定义的，如 printf "%.4f\n" 1.23。还可以通过 “.*” 来从参数中获取精度，然后再打印小数。如：printf "%.*f\n" 10 1.23。
{: id="20210410015933-32ypqp4"}


{: id="20210409204853-n7xc6aq" type="doc"}
