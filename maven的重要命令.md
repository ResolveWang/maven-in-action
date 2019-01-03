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
  - provided: 编译和测试有效，但是运行时无效，即并不会打包到jar中
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
  - `-Dtest=RandomGeneratorTest,AccountCaptchaServiceTest`: 只测试指定的用例，还可以用正则表达式，比如`Random*test`

3.mvn clean package 打包项目主代码

4.mvn clean install 将项目安装到maven本地仓库中

5.mvn dependency:list 列出所有依赖

6.mvn dependency:tree　列出依赖树

7.mvn dependency:analyze 分析依赖，可找出项目中未显示声明的依赖

8.mvn archetype:generate 自动生成maven项目骨架

9.mvn clean deploy site-deploy 将项目构建部署到配置对应的远程仓库并将生成的项目站点发布到服务器

10.mvn clean install 
  - -pl account-email,account-persist 通过`-pl`命令指定构建某几个模块, `am`、`amd`和`rf`等参数也很重要
  - Pdev `-P`参数表示在命令行中激活一个`profile`,`dev`是该profile的id

11.mvn package -DskipTests 跳过测试


### 重要插件
1.`maven-shade-plugin`: maven打包插件，会将main方法的类信息写入到`manifest`中

2.`maven-jar-plugin`: 将主代码或测试类打包

3.`maven-site-plugin`: 站点生成插件，同时支持`DAV`、`FTP`、`SCP`等协议进行站点部署

4.`maven-javadoc-plugin`: Java文档生成插件

5.`maven-jxr-plugin`: 将源代码在网页中展现出来

6.`maven-checkstyle-plugin`: 生成`CheckStyle`报告，还可以自定义编码风格规则。在聚合报告中需要同时在`build`和`reporting`中设置

7.`maven-pmd-plugin`: 代码优化插件，还可以支持自定义分析规则。在聚合报告中只需要在`reporting`中设置

8.`maven-changelog-plugin`: 基于版本控制系统生成就近的变更记录报告

9.`cibertura-maven-plugin`: 生成测试覆盖率报告，目前不支持聚合报告


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

3.内置特性
  - 属性: 作用在于消除重复，通过${属性名}来引用。有六类属性：内置属性、POM属性、自定义属性、Settings属性、Java系统属性和环境变量属性
    ```
    <properties>
    <springframework.version>2.5.6</springframework.version>
    </properties>
    ```
  - 不同构建环境下使用不同profile,通过`-P`指定激活，也可以通过`settgings.xml`指定默认激活的`profile`。一般该配置放到`src/main/resources`目录下
    ```
    <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <db.driver>com.mysql.jdbc.Driver</db.driver>
                <db.url>jdbc.mysql://127.0.0.1:3306/test</db.url>
                <db.username>dev</db.username>
                <db.password>dev-pwd</password>
            </properties>
        </profile>
    </profiles>
    ```
  - 在`src/main/resources`目录下的文件中，由于它不是`POM`，Maven解析`${db.usernmae}`需要使用maven-resources-plugin插件，在`POM`
  中配置如下。同理，对`war`的`src/main/webapp/`等web文件过滤的方法，使用`maven-war-plugin`插件，方法类似下面的写法
  ```
  <resources>
      <resource>
          <directory>${porject.basedir}/src/main/resources</directory>
          <filtering>true</filtering>
      </resource>
  </resources>
  <testResources>
      <testResource>
          <directory>${porject.basedir}/src/main/resources</directory>
          <filtering>true</filtering>
      </testResource>
  </testResources>
  ```
  - profile的激活方式。通过`mvn help:activate-profiles`可以查看当前激活的`profile`
    - 命令行`-P`指定
    - settings文件显示激活
    - 系统属性激活
    - 操作系统环境激活
    - 文件存在与否激活
    - 默认激活
  - profile的种类和范围
    - pom.xml: 对当前项目生效
    - 用户目录下`.m2/settings.xml`: 对本机当前用户的所有Maven项目生效
    - 全局settings.xml，即`Maven安装目录/conf/settings.xml`： 对本机所有Maven项目有效