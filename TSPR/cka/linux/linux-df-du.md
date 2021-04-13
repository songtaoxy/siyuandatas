# Linux-df-du
{: id="20210410020807-yw8gurd" updated="20210410020831"}

# df
{: id="20210410020824-vvkl6j1" updated="20210410020828"}

df -- display free disk ((20210410111150-a0uhgoe "{{.text}}"))
{: id="20210410111101-6cag4zj" updated="20210410111214"}

显示当前系统的空间使用情况
{: id="20210410020907-baqh00c" updated="20210410020931"}

```
$ df -h
Filesystem       Size   Used  Avail Capacity iused      ifree %iused  Mounted on
/dev/disk1s5s1  233Gi   14Gi   44Gi    25%  563932 2447537388    0%   /
devfs           344Ki  344Ki    0Bi   100%    1190          0  100%   /dev
/dev/disk1s4    233Gi  4.0Gi   44Gi     9%       6 2448101314    0%   /System/Volumes/VM
/dev/disk1s2    233Gi  271Mi   44Gi     1%     785 2448100535    0%   /System/Volumes/Preboot
/dev/disk1s6    233Gi  512Ki   44Gi     1%      15 2448101305    0%   /System/Volumes/Update
/dev/disk1s1    233Gi  171Gi   44Gi    80% 1942757 2446158563    0%   /System/Volumes/Data
map auto_home     0Bi    0Bi    0Bi   100%       0          0  100%   /System/Volumes/Data/home
/dev/disk2s1    263Mi  236Mi   27Mi    90%     877 4294966402    0%   /Volumes/SiYuan 1.1.83
/dev/disk3s1    229Mi  205Mi   24Mi    90%     279 4294967000    0%   /Volumes/RemNote 1.2.9
/dev/disk4s1     77Mi   77Mi   41Ki   100%       0          0  100%   /Volumes/T5_SETUP

```
{: id="20210410020829-nwjdlj9" updated="20210410020848"}

# du
{: id="20210410020850-txy7me4" updated="20210410020945"}

du -- display disk  ((20210410111223-s8h8oet "{{.text}}")) statistics
{: id="20210410111113-b55r6v9" updated="20210410111227"}

## 查看某个目录的大小
{: id="20210410020945-upbiowx" updated="20210410021033"}

基本命令如下, 具体使用, see ((20210409004809-x1l48mr "{{.text}}"))
{: id="20210410021026-vhq55ta" updated="20210410021248"}

- {: id="20210410021320-ll8os0b"}-h  大小单位. 人类容易识别的方式显示
  {: id="20210410021320-a42dhay" updated="20210410021347"}
- {: id="20210410021347-6yef2fq"}-d 目录深度
  {: id="20210410021347-bafgk7y" updated="20210410021356"}
{: id="20210410021319-ljxh9ue" updated="20210410021320"}

```
export size=`du -h -d 0 $SIYUAN_HOME`
```
{: id="20210410021230-yipqbmn" updated="20210410021231"}

{: id="20210410021252-tm3uxsf"}


{: id="20210410020807-24nr5s1" type="doc"}
