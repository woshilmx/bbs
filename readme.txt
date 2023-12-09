技术选型
前端：html+css+js+jqery
后端：javaweb（springBoot+mybatis plus）

运行：
修改src/main/resources/application.yml 文件
    url: jdbc:sqlserver://localhost:1433;databaseName=bbs;encrypt=false
    username: sa
    password: lmx200wx
修改以上内容为自己的sqlserver2008数据库信息
运行 src/main/java/com/bbs/project/BbsApplication.java 文件



访问:http://127.0.0.1:8080/index.html