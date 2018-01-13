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
<title>密码修改</title>
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="../skin/public.css" />
<link type="text/css" rel="stylesheet" href="../skin/top.css" />
<link type="text/css" rel="stylesheet" href="../skin/main.css" />
<link type="text/css" rel="stylesheet" href="../skin/side.css" />
<link type="text/css" rel="stylesheet" href="../skin/project.css" />
<link type="text/css" rel="stylesheet" href="../skin/jquery-ui-1.9.2.custom.min.css" />
<style>
tr:nth-child(odd){background:#f6f6f6;}
.chart{margin-top:10px;display:none;width:92%;}
.chartselecter label{margin-right:15px;}
</style>
<style>  
	td {
	text-align:center; /*设置水平居中*/
	vertical-align:middle;/*设置垂直居中*/
	} 
</style>  
	<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="../skin/main.js"></script>
	<script type="text/javascript" src="../js/jquery-ui-1.9.2.custom.min.js"></script>
	<script type="text/javascript" src="../js/highcharts.js"></script>
	<script type="text/javascript" src="../js/jquery.autocomplete.pack.js"></script>
	
</head>
<body>
	<jsp:include page="common/admin_head.jsp"></jsp:include>
	<div id="body_content" class="cfix">
		<!-- Start:left -->
		<jsp:include page="common/navigation.jsp"></jsp:include>
		<!-- End:left -->
		<div id="main">
			<br />
			<div class="breadcrumb"><font>修改密码</font><font color="red">${msg }</font>
		     </div>
		     <div id="addDiv">
		     	<form id="form" action="<%=request.getContextPath() %>/admin/updatePwd.do" method="post">
			         <div class="project-add">
			              <div class="ProLeft">
				              <ul>
				              <li>
				              <b style="font-size: 15px;"></b>原  密   码
				              <input type="text" id="oldPwd" name="oldPwd" value="">
				              </li>
				              <li>
				              <b style="font-size: 15px;"></b>新  密   码
				              <input type="text"  id="newPwd" name="newPwd" value="">
				              </li>
				                 <li>
				              确认密码
				              <input type="text"  id="newPwd1" name="newPwd1" value="">
				              </li>
				              </ul>
			                  <!-- 保存按钮显示 -->
			               	  <div class="ProReward">
			               	    <div style="margin-left: 20px;">
			                		<button type="submit" value="提交保存" id="btnSubmit" class="big green">提 交</button>
			               	    </div>
							   </div>
			          	</div>
			           </div>
		           </form>
		         </div>
				</div>
	</div>
</body>
</html>