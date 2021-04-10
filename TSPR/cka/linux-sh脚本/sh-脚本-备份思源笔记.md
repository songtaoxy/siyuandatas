# 说明
{: id="20210409004913-b3c5yov" updated="20210409005107"}

备份思源笔记数据到 ((20210409005342-g5iw9wj "{{.text}}"))
{: id="20210409005107-qcdvuqw" updated="20210409152546"}

# 脚本文件, 内容
{: id="20210409004914-ysgi0pa" updated="20210409005101"}

/Users/songtao/stsh/sy.sh
{: id="20210409004912-ctmlajj" updated="20210410005519"}

```shell
#!/bin/bash
# 备份siyuan 思源笔记数据.
# 备份格式: DevonThink-databackup-20190711222217.tar.gz

printf "............................................................................\n"
export LANG="zh_CN.UTF-8"
export LC_ALL="zh_CN.UTF-8"

export SIYUAN_HOME="/Users/songtao/personaldriveMac/siyuandatas"

export size=`du -h -d 0 $SIYUAN_HOME`
printf "[%-26s] [%-3s %s]\n" size: $size

printf "[%-26s] [%s]\n" "start" "starting backup datas ..."

# 日期,年月日时分秒
export DATE=$(date +%Y%m%d%H%M%S)


##############################################################################
#                           siyuan-backup                                    #
##############################################################################

export siyuan_target_dir="/Users/songtao/iCloud/SiYuan_Data/backup-$(date +%Y%m%d)"

if [ -x "$siyuan_target_dir" ]; then
  rm -rf "$siyuan_target_dir"
fi

if [ ! -x "$siyuan_target_dir" ]; then
  mkdir "$siyuan_target_dir"
fi


export siyuan="siyuan"
export siyuan_backup_name="$siyuan-backup-$DATE.tar.gz"
export siyuan_target_path="$siyuan_target_dir/$siyuan_backup_name"

export siyuan_src_path="/Users/songtao/personaldriveMac/siyuandatas"

tar -zPcf $siyuan_target_path $siyuan_src_path
#tar -zcf $siyuan_target_path $siyuan_src_path


export siyuan_backup_twoDaysAgo="/Users/songtao/iCloud/SiYuan_Data/backup-$(date -v -2d +%Y%m%d)"
if [ -d "$siyuan_backup_twoDaysAgo" ]; then
  rm -rf "$siyuan_backup_twoDaysAgo"
fi

# 注意
# *.tar.gz 使用betterzip打开时,可能出现乱码.using keka in macos.

printf "[%-26s] [%s]\n" "siyuan src_path:" "$siyuan_src_path"
printf "[%-26s] [%s]\n" "siyuan target_path:" "$siyuan_target_path"

printf "[%-26s] [%s]\n" "end" "backup successfully."

printf "[%-26s] [%s}\n" "backup the data further." "@BaiduNetdisk"
printf "............................................................................\n"

```
{: id="20210409004859-slo7a53" name="sh-脚本-备份思源笔记-tar-参数P"}

运行结果:
{: id="20210409004859-0iqxhdv" updated="20210409164832"}

```bash
$ sy.sh
............................................................................
[size:                     ] [14M /Users/songtao/personaldriveMac/siyuandatas]
[start                     ] [starting backup datas ...]
[siyuan src_path:          ] [/Users/songtao/personaldriveMac/siyuandatas]
[siyuan target_path:       ] [/Users/songtao/iCloud/SiYuan_Data/backup-20210410/siyuan-backup-20210410100912.tar.gz]
[end                       ] [backup successfully.]
[backup the data further.  ] [@BaiduNetdisk}
............................................................................

```
{: id="20210409164832-26c4vh6" updated="20210409164851"}

# Related
{: id="20210409164933-fjsix09" updated="20210409165210"}

((20210409165116-id4rcx0 "{{.text}}")), 如此脚本中用到的:
{: id="20210409165211-wa2hcb4" updated="20210409165358"}

```
echo "size ::= `du -h -d 0 $SIYUAN_HOME`"
```
{: id="20210409165218-da3npo2" updated="20210409165220"}

{: id="20210409165216-vfsrcr1" updated="20210409165216"}


{: id="20210409004809-x1l48mr" type="doc"}
