<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="ThemeBucket">
  <link rel="shortcut icon" href="tabs-accordions.html#" type="image/png">

  <title>MyJetty(Leopard Server)</title>

  <link href="file.leo?f=/css/style.css" rel="stylesheet">
  <link href="file.leo?f=/css/style-responsive.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="file.leo?f=/js/html5shiv.js"></script>
  <script src="file.leo?f=/js/respond.min.js"></script>
  <![endif]-->
</head>

<body class="sticky-header">

<section>
    <!-- left side start-->
    <div class="left-side sticky-left-side">

        <!--logo and iconic logo start-->
        <div class="logo text-center">
            <a href="index.html">MyJetty&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
        </div>

        <div class="logo-icon text-center">
            <a href="index.html">MyJetty</a>
        </div>
        <!--logo and iconic logo end-->


        <div class="left-side-inner">

            <!-- visible to small devices only -->
            <div class="visible-xs hidden-sm hidden-md hidden-lg">
                <div class="media logged-user">
                    <img alt="" src="file.leo?f=/images/photos/user-avatar.png" class="media-object">
                    <div class="media-body">
                        <h4><a href="tabs-accordions.html#">John Doe</a></h4>
                        <span>"Hello There..."</span>
                    </div>
                </div>

                <h5 class="left-nav-title">Account Information</h5>
                <ul class="nav nav-pills nav-stacked custom-nav">
                    <li><a href="tabs-accordions.html#"><i class="fa fa-user"></i> <span>Profile</span></a></li>
                    <li><a href="tabs-accordions.html#"><i class="fa fa-cog"></i> <span>Settings</span></a></li>
                    <li><a href="tabs-accordions.html#"><i class="fa fa-sign-out"></i> <span>Sign Out</span></a></li>
                </ul>
            </div>

            <!--sidebar nav start-->
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li><a href="index"><i class="fa fa-home"></i> <span><strong>Dashboard</strong></span></a></li>
                <li class="active"><a href="webapp"><i class="fa fa-bullhorn"></i> <span><strong>Webapp管理</strong></span></a></li>
                <li class=""><a href="webapp"><i class="fa fa-bullhorn"></i> <span><strong>启停记录</strong></span></a></li>
                
                <li class="menu-list"><a href="tabs-accordions.html"><i class="fa fa-cogs"></i> <span><strong>调试系统</strong></span></a>
                    <ul class="sub-menu-list">
                        <li><a href="grids.html"> Grids</a></li>
                        <li><a href="gallery.html"> Media Gallery</a></li>
                        <li><a href="calendar.html"> Calendar</a></li>
                        <li><a href="tree_view.html"> Tree View</a></li>
                        <li><a href="nestable.html"> Nestable</a></li>

                    </ul>
                </li>

                

                <li class="menu-list nav-active"><a href="tabs-accordions.html"><i class="fa fa-envelope"></i> <span style="color:#fff"><strong>常用链接</strong></span></a>
                    <ul class="sub-menu-list">
                        <li><a href="/apidoc/index.leo" target="_blank"> 后端接口文档</a></li>
                        <li><a href="/topnb/index.leo" target="_blank"> 性能监控系统</a></li>
                    </ul>
                </li>

            </ul>
            <!--sidebar nav end-->

        </div>
    </div>
    <!-- left side end-->
    
    <!-- main content start-->
    <div class="main-content" >

        <!-- header section start-->
        <div class="header-section">

        <!--toggle button start-->
        <a class="toggle-btn"><i class="fa fa-bars"></i></a>
        <!--toggle button end-->

        <!--search start-->
        <form class="searchform" action="index.html" method="post">
            <input type="text" class="form-control" name="keyword" placeholder="Search here..." />
        </form>
        <!--search end-->

        <!#include file="notification.inc.ftl"/>

        </div>
        <!-- header section end-->

        
    	<@templateBody template_name="${template_name}"/>

        <!--footer section start-->
        <footer class="sticky-footer">
            2015 &copy; MyJetty by Leopard
        </footer>
        <!--footer section end-->

    </div>
    
    <!-- main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="file.leo?f=/js/jquery-1.10.2.min.js"></script>
<script src="file.leo?f=/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="file.leo?f=/js/jquery-migrate-1.2.1.min.js"></script>
<script src="file.leo?f=/js/bootstrap.min.js"></script>
<script src="file.leo?f=/js/modernizr.min.js"></script>
<script src="file.leo?f=/js/jquery.nicescroll.js"></script>

<!--common scripts for all pages-->
<script src="file.leo?f=/js/scripts.js"></script>

</body>
</html>
