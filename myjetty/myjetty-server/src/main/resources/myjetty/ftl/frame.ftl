<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="zh-CN" http-equiv="Content-Language" />
	<meta content="IE=EmulateIE7" http-equiv="X-UA-Compatible" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>MyJetty</title>
    
	<!--[if gt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <![endif]-->
    
    <link href="file.leo?f=css/stylesheets.css" rel="stylesheet" type="text/css" />  
      
    <!--[if lt IE 8]>
        <link href="file.leo?f=css/ie7.css" rel="stylesheet" type="text/css" />
    <![endif]-->            
    <link rel='stylesheet' type='text/css' href='file.leo?f=css/fullcalendar.print.css' media='print' />
    
    <script type='text/javascript' src='file.leo?f=js/plugins/jquery/jquery-1.9.1.js'></script>
    <script type='text/javascript' src='file.leo?f=js/plugins/jquery/jquery-ui-1.10.1.custom.min.js'></script>
    <script type='text/javascript' src='file.leo?f=js/plugins/jquery/jquery-migrate-1.1.1.min.js'></script>
    
    <script type='text/javascript' src='file.leo?f=js/plugins/cookie/jquery.cookies.2.2.0.min.js'></script>
    
    <script type='text/javascript' src='file.leo?f=js/plugins/fancybox/jquery.fancybox.pack.js'></script>

    <script type='text/javascript' src='file.leo?f=js/cookies.js'></script>
    <script type='text/javascript' src='file.leo?f=js/actions.js'></script>
    <script type='text/javascript' src='file.leo?f=js/settings.js'></script>
    <script type='text/javascript' src='file.leo?f=js/jtest.js'></script>

    <link href="file.leo?f=css/monitor.css" rel="stylesheet" type="text/css" />

</head>
<body>
    <div class="wrapper"> 
		<div class="header">
            <a class="logo" href="/workbench/" style="color:#fff;font-size:20px;">MyJetty(Leopard Server) - 使移动运维更加方便</a> 
            <ul class="header_menu">
                <li class="list_icon"><a href="/workbench/">&nbsp;</a></li>
                <li class="xxx">
                	
                </li>
            </ul>    
        </div>
		<div class="menu">                
            <div class="dr"><span></span></div>

            <ul class="navigation">
            	<li class="active"><a href="/workbench/"><span class="isw-grid"></span><span class="text">首页</span></a></li>
			    <li><a href="webapp"><span class="isw-text_document"></span><span class="text">Webapp管理</span></a></li>
			    <li><a href="beanlist.leo"><span class="isw-text_document"></span><span class="text">启停记录</span></a></li>
			    <li><a href="api"><span class="isw-text_document"></span><span class="text">服务器API</span></a></li>
			    <li><a href="beanlist.leo?category=service"><span class="isw-text_document"></span><span class="text">调试系统</span></a></li>
			    
			    <li class="openable active">
			        <a href="index.html#"><span class="isw-documents"></span><span class="text">常用链接</span></a>
			        <ul>
			            <li><a href="/apidoc/index.leo" target="_blank"><span class="icon-info-sign"></span><span class="text">webapp接口文档</span></a></li>
			            <li><a href="/topnb/index.leo" target="_blank"><span class="icon-user"></span><span class="text">性能监控系统</span></a></li>
			        </ul>
			    </li>		    
			</ul>

            <div class="dr"><span></span></div>
        </div>
        <div class="content">
			<div class="breadLine">

                <ul class="buttons">
                	<li>
                        &nbsp;&nbsp;<span class="icon-user"></span><span class="text"><@serverInfo/></span>&nbsp;&nbsp;
                    </li>
                </ul>
            </div>
            
            <div class="workplace">
                <div class="row-fluid">
                   <@templateBody template_name="${template_name}"/>
                </div>
                <div class="dr"><span></span></div>
            </div>

        </div>   
    </div>

</body>
</html>
