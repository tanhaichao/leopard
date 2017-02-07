<script type="text/javascript">



function request(uri) {
	var xmlhttp= new XMLHttpRequest();
	
	var logpanel = document.getElementById("logpanel");
	logpanel.innerHTML = "";
	xmlhttp.onreadystatechange = function() {
	    if ((xmlhttp.readyState==3 || xmlhttp.readyState==4) && xmlhttp.status==200){
	    	logpanel.innerHTML = xmlhttp.responseText;
	    	logpanel.scrollTop = logpanel.scrollHeight;
	    }
	}
	xmlhttp.open("GET",uri,true);
	xmlhttp.setRequestHeader('If-Modified-Since', '0'); 
	xmlhttp.send(null);
	
	
	if (true) {
		return;
	}
	$.ajax({
		url:uri,
		cache:false,
		async:true,

		success: function(msg) {
			alert(msg);
		}
	});
	
}
</script>
                    <div class="span12">                    
                        <div class="head clearfix">
                            <div class="isw-grid"></div>
                            <h1>Webapp管理 >> (${projectId})</h1>    
                            <!--   
                            <ul class="buttons">
                            	<li><a href="webapp?projectId=olla" class="isw-tab" style="width:132px; text-align:center">webapp2</a></li>
                            	<li><a href="webapp?projectId=zhongcao" class="isw-tab" style="width:132px; text-align:center">Leopard官网</a></li>
                            </ul>
                             -->       
                        </div>
                        <div class="block-fluid">
                            <table cellpadding="0" cellspacing="0" width="100%" class="table">
                                <thead>
                                	<tr style="height:130px;">                             
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="一键部署" processing="部署中..." url="webapp/deploy?projectId=${projectId}"/></th>
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="更新" processing="更新中..." url="webapp/update?projectId=${projectId}"/></th>
                                        <th rowspan="4" nowrap="nowrap" style="text-align: left;vertical-align: middle; ">
                                        		<pre style="overflow:scroll;height:520px;" id="logpanel">
                                        		<!-- 
2016-01-06 13:04:55,878 INFO [,main] - [com.mchange.v2.log.MLog] - <MLog clients using log4j logging.>
2016-01-06 13:04:59,206 INFO [,main] - [io.leopard.web.mvc.LeopardDispatcherServlet] - <FrameworkServlet 'web': initialization started>
2016-01-06 13:04:59,305 INFO [,main] - [io.leopard.web.mvc.LeopardDispatcherServlet] - <FrameworkServlet 'web': initialization completed in 99 ms>
java.lang.NullPointerException
        at io.leopard.jdbc.JdbcMysqlImpl.toStatementParameter(JdbcMysqlImpl.java:587)
        at io.leopard.jdbc.JdbcMysqlImpl.query(JdbcMysqlImpl.java:124)
        at io.leopard.jdbc.test.JdbcThreadImpl.query(JdbcThreadImpl.java:145)
        at com.zhongcao.app.sms.CaptchaDaoMysqlImpl.last(CaptchaDaoMysqlImpl.java:33)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:497)
        at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:302)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)
        at io.leopard.topnb.methodtime.MethodTimeInterceptor.invoke(MethodTimeInterceptor.java:73)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
        at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:208)
        at com.sun.proxy.$Proxy137.last(Unknown Source)
        at com.zhongcao.app.sms.CaptchaServiceImpl.last(CaptchaServiceImpl.java:72)
        at com.zhongcao.app.sms.CaptchaServiceImpl.lastSecurityCode(CaptchaServiceImpl.java:63)
        at com.zhongcao.app.sms.CaptchaServiceImpl.sendSignupSecurityCode(CaptchaServiceImpl.java:33)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:497)
        at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:302)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)
        at io.leopard.topnb.methodtime.MethodTimeInterceptor.invoke(MethodTimeInterceptor.java:73)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
        at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:208)
        at com.sun.proxy.$Proxy138.sendSignupSecurityCode(Unknown Source)
        at com.zhongcao.app.web.controller.SignupController.getSecurityCode(SignupController.java:123)
        at com.zhongcao.app.web.controller.SignupController$$FastClassBySpringCGLIB$$3af9c995.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:720)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157)
        at io.leopard.topnb.methodtime.MethodTimeInterceptor.invoke(MethodTimeInterceptor.java:73)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:655)
        at com.zhongcao.app.web.controller.SignupController$$EnhancerBySpringCGLIB$$e97aeae4.getSecurityCode(<generated>)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)                                        		
                                        		 -->
                                        		</pre>
                                        
                                        </th>
                                    </tr>
                                	<tr style="height:130px;">
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="启动" processing="启动..." url="webapp/start?projectId=${projectId}"/></th>
                                        <th nowrap="nowrap"  style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="停止" processing="停止..." url="webapp/stop?projectId=${projectId}"/></th>
                                        
                                    </tr>
                                    <tr style="height:130px;">                             
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="打包" processing="打包中..." url="webapp/packaging?projectId=${projectId}"/></th>
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="重启" processing="重启中..." url="webapp/restart?projectId=${projectId}"/></th>
                                    </tr>
                                    <tr style="height:130px;">
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;"><@ajax_button value="编译" processing="编译中..." url="webapp/compile?projectId=${projectId}"/></th>
                                        <th nowrap="nowrap" style="text-align: center;vertical-align: middle;width:130px;">回滚至1.1<br/>回滚至1.0</th>
                                    </tr>
                                    
    
                                </thead>
                                
                                
                            </table>
                        </div>
                    </div>