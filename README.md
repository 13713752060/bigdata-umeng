# bigdata-umeng
大数据日志处理
* 该项目是一个大数据一站式日志处理项目。
* 项目架构：Hadoop, Kafka, Flume, Hive, Echarts...
* 业务层采用的是SSM 架构。

### 注意：详细笔记见文档笔记目录


----

#### git push 代码到github 上出现如下异常

``` 
fatal: remote error: 
  You can't push to git://github.com/niexiaobo/remote.git
  Use https://github.com/niexiaobo/remote.git

主要可能是clone 代码的时候用的是 git:// 协议，
而提价代码则不能用 https:// 协议。 需要修改远程信息为https:// 协议即可。

```

* 解决方案：最好鼠标右键打开(Git Bash Here) 执行命令
```
     1、用命令行，切换到项目 checkout的文件夹

        c://windows/deskTop> cd /Users/mac/Desktop/SHiosProject/gitMangerfiles/KVO 

     2、删除原有远程信息

        c://windows/deskTop> git remote rm origin

     3、新增刚才报错的链接

        c://windows/deskTop> git remote add origin https://github.com/xxx/xxx
 
```