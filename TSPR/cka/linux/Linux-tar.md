# Linux-tar
{: id="20210409165552-ja5nnyk" updated="20210409165648"}

# Used Frequently
{: id="20210409165649-mi3q1oq" updated="20210409171717"}

## 最佳实践
{: id="20210409171518-9inmp2p" updated="20210409171543"}

```
# 压缩
tar -Pzcvf x.tar.gz f1 f2

# 解压至指定目录
tar -zxvf pdf.tar.gz -C ~/downloads/ps-works

```
{: id="20210409171543-m1xddy4"}

## 只归档
{: id="20210409171334-g9jbybd" updated="20210409171339"}

```
tar -cvf x.tar.gz <f1> <f2>
tar -tvf x.tar.gz 
tar -xvf x.tar.gz -C 
```
{: id="20210409165930-bvvx9i1" updated="20210409171145"}

## 归档, 并压缩
{: id="20210409171347-0thgnrc" updated="20210409171401"}

```
# -z：有gzip属性的; 归档, 并压缩
tar -zcvf x.tar.gz f1 f2
tar -Pzcvf x.tar.gz f1 f2

tar -ztvf x.tar.gz
tar -zxvf x.tar.gz  # 模式解压在当前目录
```
{: id="20210409171420-pu3p4fr" updated="20210409171430"}

## 解压至指定目录
{: id="20210409171401-ysf5m74" updated="20210409171412"}

```
# 使用-C, 则可以解压至指定的目录
tar -zxvf pdf.tar.gz -C ~/downloads/ps-works # 将其解压至downloads/ps-works

```
{: id="20210409171351-sjo6um6" updated="20210409171509"}

# Issue
{: id="20210409165648-v0r3ybn" updated="20210409171347"}

## 错误: 场景, 现象
{: id="20210409170437-lu5q3j3" updated="20210409170619"}

### 脚本
{: id="20210409170620-d632vm1" updated="20210409170834"}

```
tar -zcf $siyuan_target_path $siyuan_src_path
```
{: id="20210409171018-6908kw5" updated="20210409171029"}

### 结果, 错误现象
{: id="20210409170645-44wns2t" updated="20210409170846"}

脚本, 或直接命令时, 出现如下多余命令: `tar: Removing leading '/' from member names`
{: id="20210409165958-tewuxyt" updated="20210409170435"}

```
$ ./backup_sy.sh
............................................................................
size ::= 8.7M	/Users/songtao/personaldriveMac/siyuandatas
starting backup datas ...
**tar: Removing leading '/' from member names**
siyuan src_path ::= /Users/songtao/personaldriveMac/siyuandatas
siyuan target_path ::= /Users/songtao/iCloud/SiYuan_Data/backup-20210409/siyuan-backup-20210409170315.tar.gz
请将该备份数据进一步备份, 以防丢失!
............................................................................
```
{: id="20210409165944-l1r3em2" name="sh-error" updated="20210409170024"}

## 原因
{: id="20210409170742-5jel68p" updated="20210409170753"}

tar默认为相对路径，使用绝对路径的话就回报这个错，可以使用-P参数（注意大写）解决这个问题. example in ((20210409004809-x1l48mr "{{.text}}"))
{: id="20210409170033-fs6ip06" updated="20210409191051"}

## 方案
{: id="20210409170759-cgficxu" updated="20210409170809"}

方案, 见((20210409170742-5jel68p "{{.text}}"))
{: id="20210409170859-qsee8tj" updated="20210409170908"}

### 脚本
{: id="20210409170812-j6mf3hp" updated="20210409170817"}

```
tar -zPcf $siyuan_target_path $siyuan_src_path
```
{: id="20210409165944-xj5uy30" updated="20210409170129"}

### 结果
{: id="20210409170131-vwcm7xg" updated="20210409170825"}

```
$ ./backup_sy.sh
............................................................................
size ::= 8.7M	/Users/songtao/personaldriveMac/siyuandatas
starting backup datas ...
siyuan src_path ::= /Users/songtao/personaldriveMac/siyuandatas
siyuan target_path ::= /Users/songtao/iCloud/SiYuan_Data/backup-20210409/siyuan-backup-20210409170953.tar.gz
请将该备份数据进一步备份, 以防丢失!
............................................................................

```
{: id="20210409170917-a3cvwsw" updated="20210409171002"}

{: id="20210409191746-d5g3kdb" updated="20210409204322"}

{: id="20210409191836-ad47vqh"}


{: id="20210409165552-tjf4pvw" type="doc"}
