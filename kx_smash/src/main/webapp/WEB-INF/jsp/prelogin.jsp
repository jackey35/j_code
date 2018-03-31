<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开运大师管理中心</title>
<link  href="../skin/public.css" type="text/css" rel="stylesheet" />
<link  href="../skin/log.css" type="text/css" rel="stylesheet" />

</head>
<body class="logpage">
	<div class="ma">
		<form action="/admin/login.do" method="POST">
		   <table cellpadding="0" cellspacing="0" class="table-log">
		      <tr>
			     <td colspan="2" class="tit2" id="tips">亲，给自己乐一个？<font color="red">${msg}</font></td>
			  </tr>
			  <tr>
			     <td colspan="2"><div class="logname"><input type="text" id="userName" name="userName" autofocus placeholder="输入您账号"/></div></td>
			  </tr>
			  <tr>
			     <td colspan="2"><div class="logpsw"><input type="password" name="password" placeholder="输入您邮箱密码"/></div></td>
			  </tr>
			  <tr>
				 <td align="right"><input type="submit" value="登录" class="logbnt"/></td>
			  </tr>
		   </table>
		</form>
	</div>
</body>
</html>