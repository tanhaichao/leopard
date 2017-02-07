# TopNB
Leopard监控系统，包含方法耗时监控、请求耗时监控、线程数量统计等功能。

方法耗时监控：可以按入口(URL)查看统计数据。

官网使用文档<http://leopard.io/modules/topnb>

# Leopard官网的TopNB例子
<http://leopard.io/topnb/index.leo>

# pom.xml配置

```
	<dependencies>
		...
		<dependency>
			<groupId>io.leopard.topnb</groupId>
			<artifactId>topnb-profiler</artifactId>
			<version>0.0.2</version>
		</dependency>
		<dependency>
		    <groupId>javax.servlet</groupId>
    		<artifactId>javax.servlet-api</artifactId>
    		<version>3.1.0</version>
    	</dependency>
		...
	</dependencies>
```

#启用方法耗时监控

在Spring配置加入
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
...
	<import resource="classpath:/topnb/config.xml" />
...
</beans>
```

#如果你的webserver不支持@WebServlet、@WebListener
请在web.xml加入
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
		 http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">
	...
	<absolute-ordering>
		<name>topnb_webfragment</name>
		<others />
	</absolute-ordering>
	...
</web-app>
```


#查看统计数据
<http://localhost/topnb/index.leo>

