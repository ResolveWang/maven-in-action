### 基础概念
1.maven坐标
  - groupId: 定义当前maven项目隶属的实际项目，往往是和Java包名表达方式类似
  - artifactId: 实际的maven项目，一般使用项目名
  - version: 定义maven项目当前所处的版本
  - packaging: 定义maven项目的打包方式，比如`jar`或者`war`
  - classifier: 定义构建输出的一些附属构建，比如`javadoc`和`sources`

2.`pom.xml`文件中的`scope`
  - compile: 编译依赖范围
  - test: 测试依赖范围
  - provided: 编译和测试有效，但是运行时无效
  - runtime: 运行或者测试时有效，编译时无效
  - system: 该类依赖不通过maven仓库解析，而是与本机系统绑定，可能造成构建的不可移植性
  
3.maven主要的生命周期
  - clean: 包括了`pre-clean`,`clean`和`post-clean`
  - default: 包括了`validate`,`initialize`,`compile`...`test`,`install`等
  - site: 包括了`pre-site`,`site`,`post-site`和`site-deploy`

4.maven超级POM
  - 任何一个Maven项目都隐式继承自该POM
  - maven3中该POM位于`$MAVEN_HOME/lib/maven-model-builder-x.x.x.jar`中的`org/apache/maven/model/pom-4.0.0.xml`路径下

5.maven中不允许出现循环依赖

### 常用命令
1.mvn clean compile 编译项目主代码

2.mvn clean test 测试项目主代码

3.mvn clean package 打包项目主代码

4.mvn clean install 将项目安装到maven本地仓库中

5.mvn dependency:list 列出所有依赖

6.mvn dependency:tree　列出依赖树

7.mvn dependency:analyze 分析依赖，可找出项目中未显示声明的依赖

8.mvn archetype:generate 自动生成maven项目骨架

9.mvn clean deploy site-deploy 将项目构建部署到配置对应的远程仓库并将生成的项目站点发布到服务器

10.mvn clean install -pl account-email,account-persist 通过`-pl`命令指定构建某几个模块, `am`、`amd`和`rf`等参数也很重要

11.mvn package -DskipTests 跳过测试

### 重要插件
1.`maven-shade-plugin`: maven打包插件，会将main方法的类信息写入到`manifest`中

### 特性
1.聚合，注意`packaging`的值和`module`路径

2.继承，可继承的元素，常见的如下
  - groupId
  - version
  - description
  - dependencies
  - dependencyManagement
  - build
  - reporting
  - properties
  - url
