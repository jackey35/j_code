<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>砸金蛋活动</title>
<base href="<%=basePath%>">
	<script type="text/javascript">
	window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2568868aed8854e6&redirect_uri=http%3A%2F%2Fwww.jinxinsenhui.com%2Fsmash%2Fwxauth.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	</script>
</head>
<body>
	砸金蛋
</body>
</html>