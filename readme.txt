待开发的功能:
	1、URL扩展名规范.
	2、字段名称(下划线、驼峰)配置
	3、AutoUnit-web：对标准接口在线自动测试。目前是根据Leopard2E生成测试代码，AutoUnit-web可以做到连测试代码都不用生成.
	4、apidoc-todo:每个接口还有哪些TODO?
	5、webserver管理，Jetty扩展.
	6、启动性能优化
	
	7、最近访问记录
	
	



leopard-lang
	leopard-json:简化Json使用，可以在Jackson、FastJson切换.
	httpnb
	leopard-timer:定时器
	leopard-command:命令行程序支持
	leopard-servlet:Cookie、ProxyIP、RequestURI获取等.
	
JavaHost:JVM DNS

AutoUnit:“全”自动化单元测试框架

leopard-data:数据源操作
	leopard-jdbc、leopard-redis、leopard-memcache、leopard-cache、leopard-memdb
	
leopard-test
	leopard-mock、leopard-test、leopard-jetty
	
leopard-debug:调试模块
	leopard-delay:页面随机延迟、报错
	leopard-avgtime:
	
leopard-mvc:MVC
	leopard-session:分布式session
	leopard-passport:通行证验证、登陆等
	leopard-captcha:验证码
	leopard-frequency:访问频率限制 
	leopard-trynb:细到URL级别的异常处理
	leopard-xparam:请求特殊参数
	leopard-proxy:webserver代理.
	
leopard-security
	leopard-allow、leopard-admin
	
nobug：安全框架
	nobug-csrf、nobug-xss

topnb:监控框架
	topnb-profiler、topnb-web

apidoc:接口文档生成系统
	apidoc-web、apidoc-maven-plugin
	