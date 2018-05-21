# CICD-team### 本项目为任老师课程第一次团队作业项目——应用平台来进行持续集成与持续交付### 小组成员：姚子航，应邦豪，徐天强，王鑫伟，励颖 ## 目录### 1. CI/CD 介绍### 2. CI/CD 实践### 3. CI/CD 思考## 1. CI/CD 介绍### 1.1 名次解释&#8194;&#8194; CI: Continuous Integration，即持续集成。&#8194;&#8194; CD: Continuous Delivery，Continuous Deployment，即持续交付、持续部署。&#8194;&#8194;构建：将源代码放在一起，并验证软件可以作为一个一致的单元运行的过程；验证活动一般包括编译、测试、审查和部署。&#8194;&#8194;集成：狭义的集成指代码与代码之间的集成，保证代码合并不冲突；广义的集成指软件各个过程的集成，包括开发、部署、测试等。### 1.2 持续集成的定义&#8194;&#8194;持续集成 是一种软件开发实践，即团队开发成员经常集成他们的工作。通常，每个成员每天至少集成一次，也就意味着每天可能发生多次集成。每次集成都通过自动化的构建来验证，从而尽早地发现集成错误。### 1.3 持续交付与持续部署&#8194;&#8194;持续交付在持续集成的基础上，将集成后的代码部署到更贴近真实运行环境的「类生产环境」中进行更多的测试。如果代码没有问题，可以继续手动部署到生产环境中。&#8194;&#8194;持续部署就是把部署到生产环境的过程自动化。### 1.4 持续集成与持续部署的优点* 提高软件质量* 节约时间，缩短项目发布周期* 便捷部署* 建立团队开发产品的信心* 增强项目的可见性* 解决项目管理的困惑#### 1.5 持续集成与持续部署的原则* 维护唯一的代码仓库* 让构建能够自测* 每天执行多次构建，每次构建都要生成可发布的产品* 每次提交都在主线集成的机器上做构建* 开发人员每天至少向版本控制库提交一次代码### 1.6 持续集成与持续部署的工具* Github  ![Github](https://s1.ax2x.com/2018/05/21/2FZUz.png)* Jenkins  ![Jenkins](https://s1.ax2x.com/2018/05/21/2FhgS.png)* Tomcat  ![ Tomcat](https://s1.ax2x.com/2018/05/21/2FyQh.png)* Travis CI  ![Travis CI](https://s1.ax2x.com/2018/05/21/2FPcH.png)## 2. CI/CD 实践### 2.1 持续集成：搭建Jenkins环境&#8194;&#8194;&#8194;&#8194;从Jenkins官网https://jenkins.io/下载最新的war包。&#8194;&#8194;&#8194;&#8194;方法一：将Jenkins作为一个Windows服务，用命令行运行war包启动Jenkins，并为其分配https端口。  ![Method 1](https://s1.ax2x.com/2018/05/21/2Fe9O.png)&#8194;&#8194;&#8194;&#8194;方法二：基于Web容器（Tomcat）安装，将jenkins.war复制到%TOMCAT_HOME%\webapps目录下，启动Tomcat，本次搭建采用此方法。  ![Method 2](https://s1.ax2x.com/2018/05/21/2FUOq.png)### 2.2 持续集成：利用Jenkins新建任务* 步骤一：在Jenkins中安装Maven插件，并新建一个Maven项目。Jenkins会利用pom文件，从而大大减轻构建配置。![Step 1](https://s1.ax2x.com/2018/05/21/2FafN.png)* 步骤二：在配置页中，源码管理选择Git，填入地址。默认使用master分支。如果需要口令，在Credentials中添加用户名/口令，或者使用SSH Key  ![Step 2](https://s1.ax2x.com/2018/05/21/2FrXu.png)* 步骤三：配置构建触发器，定时检查版本库，发现有新的提交就触发构建。这种方式对git、SVN等所有版本管理系统都是通用的。![Step 3](https://s1.ax2x.com/2018/05/21/2FCZ9.png)* 步骤四：配置构建选项，本项目直接使用maven的pom.xml进行构建，需要配置好pom.xml文件路径以及配置参数，这里的参数是跳过test选项。在pom.xml里的build标签添加<defaultGoal> 标签，值为package阶段，这样build结束后才会生成可运行jar文件  ![Step 4](https://s1.ax2x.com/2018/05/21/2FEIA.png)* 步骤五：github配置webhook，每当master分支发生变动，merge pull request 或有新的commit，就会将信息push到jenkins平台上，并自动构建。### 2.3 持续集成：Jenkins任务构建结果&#8194;&#8194;&#8194;&#8194;S（上次的构建状态），蓝色代表Success，红色代表Failed&#8194;&#8194;&#8194;&#8194;W（编译晴雨表）：最近五次构建的成功率。  ![Result 1](https://s1.ax2x.com/2018/05/21/2FIiY.png)  ![Result 2](https://s1.ax2x.com/2018/05/21/2FlZy.png)  ### 2.4 持续集成：持续集成效果演示### 2.5 持续集成：问题总结* JDK版本问题&#8194;&#8194;&#8194;&#8194;Jenkins 似乎对 JDK8 以上的版本存在不兼容的问题，重新安装 JDK8 并在全局系统配置中更改 JDK 配置之后问题解决。* Maven项目设置&#8194;&#8194;&#8194;&#8194;Local Maven Repository 应当设置为 Local to workspace。* BuildTest不通过&#8194;&#8194;&#8194;&#8194;有时在构建时会出现maventest不通过的情况，可以通过添加Dskiptests参数暂时跳过test解决问题* pom.xml更改&#8194;&#8194;&#8194;&#8194;ide生成的pom.xml并没有指定defaultGoal，需要自己在pom.xml中修改### 2.6 持续部署&#8194;&#8194;&#8194;&#8194; 本次作业并没有实现自动部署，而是手动部署SpringBoot项目。  ![CD](https://s1.ax2x.com/2018/05/21/2F8NX.png)&#8194;&#8194;&#8194;&#8194;在jenkins的workspace下，可以在/target目录找到jar包，在命令行输入“java -jar <filename>”运行，显示运行成功。&#8194;&#8194;&#8194;&#8194;服务地址：47.106.75.68### 2.7 监控&#8194;&#8194;&#8194;&#8194;Application的监控使用了阿里巴巴的druid监控组件。提供了SQL监控、Session监控、URL监控等功能，方便了开发者根据监控情况调整项目。  ![Monitor 1](https://s1.ax2x.com/2018/05/21/2FKUe.png)  ![Monitor 2](https://s1.ax2x.com/2018/05/21/2FtQR.png)  ![Monitor 3](https://s1.ax2x.com/2018/05/21/2F6cr.png)  ## 3 CI/CD 思考### 3.1 衡量技术债务&#8194;&#8194;&#8194;&#8194;在应用中引入的任何变化都会增加系统复杂和无序性被称之为“技术债务”，当新代码引入系统时会增加技术债务，如果被忽视太久，越来越多的新功能被紧凑的最后期限所填满，此时想要在可控范围内几乎不可能，这将对应用的生产效率和可维护性造成负面影响。&#8194;&#8194;&#8194;&#8194;迭代开发、自动化检测、和CI的使用来监控每个嵌入技术债务是控制技术债务的唯一方法。&#8194;&#8194;&#8194;&#8194;如SonarQube工具，采用识别代码质量和跟踪技术债务的开源平台，可以轻松地集成到任何CI服务器中，为技术债务提供实时数据。### 3.2 度量代码质量&#8194;&#8194;&#8194;&#8194;对于开发者来说，引入CI在另一个重要的方面非常具有价值——可以衡量代码质量——语义及常见的反模式。&#8194;&#8194;&#8194;&#8194;静态代码工具可以作为CI过程的一部分，以深入了解代码的健康度，历史数据也可以存储，从而在一段时间内提供质量度量。可以为两个提交作比较，确定每个提交的债务度量标准。&#8194;&#8194;&#8194;&#8194;很多工具可以静态地分析代码并计算质量度量的多样性，同时可以被集成到CI服务器中自动运行,例如：* Checkstyle* Findbugs* Sonar### 3.2 代码评审&#8194;&#8194;&#8194;&#8194;代码分析工具（如：Sonar）可以在CI服务器中执行自动代码检测，然后每个开发者负责处理提交时生成的评论注释。&#8194;&#8194;&#8194;&#8194;自动代码审查基于一组预定义的规则，是发现潜在技术问题很好的方法，这些规则可以从主流的开发语言中下载。&#8194;&#8194;&#8194;&#8194;解决了自动化评审意见，会进行同行代码评审，捕捉那些不能用自动化审查的问题，如验证业务需求、体系结构问题、代码易读性、扩展性等。&#8194;&#8194;&#8194;&#8194;如果有一定数量的人没对代码进行审查，CI服务器可以配置成阻止对主线分支任何的提交，常见办法是在主线分支中强制要求每个提交至少2个审核员，Java的项目中，常见工具是Gerrit。