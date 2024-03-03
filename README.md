# 《基于Android Studio 和 Spring Boot 框架的互动提问箱应用程序开发》

此为前端仓库

后端：https://github.com/Jiangt333/AndroidDevJava



**使用教程：**

1、下载**Android Studio**应用，配置相关**环境**；

2、打开Android Studio，并从GitHub中下载**前端**代码：具体的仓库链接为https://github.com/Jiangt333/AndroidDev；

3、在common文件中**更新本机的IP地址**（命令行ipconfig获取）；

4、下载并打开可以运行Java语言的其他IDE（如Vscode、Eclipse等），**配置maven springboot 环境**；

5、从GitHub中下载**后端**代码（即本仓库）：具体的仓库链接为（注意与前端链接位置不同）https://github.com/Jiangt333/AndroidDevJava；

6、下载并打开MySQL应用，设置相关信息；

7、在后端代码的**application.properties更新(6中设置的)数据库信息**，如数据库密码等。

8、分别运行前后端代码。




**注意事项：**

1、由于工信部制定的规则限制，“注册”版块中的短信验证需要跟踪至项目本身，在我们打包的APK中是可以实现的（前提是连接正确的数据库、更新正确的IP，这个功能我们已对老师演示过）。

但如需在新的项目中实现该功能，必须前往MobTech注册账号，获取App Key和App Secret，替换原位置的key和secret（具体位置见项目文档的代码实现的第1部分），并获取新项目MD5签名和包名，添加至MobTech后台（配置文件已经加入项目中，注意进行sync）。

由于本过程繁琐，建议直接在数据库中添加用户信息。

2、后端图片文件存储路径请根据实际情况修改。
