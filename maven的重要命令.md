### 基础概念
1.maven坐标
  - groupId: 定义当前maven项目隶属的实际项目，往往是和Java包名表达方式类似
  - artifactId: 实际的maven项目，一般使用项目名
  - version: 定义maven项目当前所处的版本
  - packaging: 定义maven项目的打包方式，比如`jar`或者`war`

2.`pom.xml`文件中的`scope`
  - compile: 编译依赖范围
  - test: 测试依赖范围
  - provided: 编译和测试有效，但是运行时无效
  - runtime: 运行或者测试时有效，编译时无效
  - system: 该类依赖不通过maven仓库解析，而是与本机系统绑定，可能造成构建的不可移植性

### 常用命令
1.mvn clean compile 编译项目主代码
2.mvn clean test 测试项目主代码
3.mvn clean package 打包项目主代码
4.mvn clean install 将项目安装到maven本地仓库中
5.mvn dependency:list 列出所有依赖
6.mvn dependency:tree　列出依赖树
7.mvn dependency:analyze 分析依赖，可找出项目中未显示声明的依赖
8.mvn archetype:generate 自动生成maven项目骨架

