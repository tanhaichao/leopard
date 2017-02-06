
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,minimum-scale=1, maximum-scale=1">
    <title>登录</title>
    <meta name="Keywords" content="">
    <meta name="Description"content="">
    
    <meta name="apple-itunes-app" content="app-id=1048541582">

    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">

	<link rel="stylesheet" href="/css/main.min.css">
</head>

<body>

<div class="main-container">

<div class="view-container"   >
<div id="account_login" class="account-box-wrapper">
    <div class="account-box">
        <form class="login-form" action="/passport/login.leo" method="post">
        	<input type="hidden" name="url" value="${url}"/>
        	<input type="hidden" name="type" value="${type!}"/>
            <div class="account-form-title"><label>登录</label></div>
            <div class="account-input-area">
                <input name="username" type="text" placeholder="账号" >
            </div>
            <div class="account-input-area">
                <input name="password" type="password" placeholder="密码">
            </div>
            <div class="check-box">
                <input id="remember" name="remember" type="checkbox">
                <label for="remember"></label>
                <span>记住我</span>
            </div>
            <button class="account-button login-btn" type="submit"><span>登录</span></button>
            
            <div class="account-bottom-tip reset-password">
                <label>忘记密码？<a href="#" class="reset-password-href">重新设置密码！</a></label>
            </div>

        </form>
    </div>
    </div>
</div>

          
</div>


</body>
</html>
