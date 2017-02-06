<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登陆</title>
</head>
<body>

<form action="/passport/login.leo" method="post">
	<input type="hidden" name="url" value="${url}"/>
	用户名:<input type="text" name="username"/><br/>
	密码:<input type="password" name="password"/><br/>
	<input type="submit" value="登陆"/>
</form>

</body>
</html>