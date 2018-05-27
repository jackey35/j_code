<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中奖列表</title>
<link type="text/css" rel="stylesheet" href="../skin/public.css" />
<link type="text/css" rel="stylesheet" href="../skin/top.css" />
<link type="text/css" rel="stylesheet" href="../skin/main.css" />
<link type="text/css" rel="stylesheet" href="../skin/side.css" />
<link type="text/css" rel="stylesheet" href="../skin/jquery.autocomplete.css" />
<link type="text/css" rel="stylesheet" href="../skin/jquery-ui-1.9.2.custom.min.css" />
<link type="text/css" rel="stylesheet" href="../skin/page.css"  />
<style>
tr:nth-child(odd){background:#f6f6f6;}
.chart{margin-top:10px;display:none;width:100%;}
.chartselecter label{margin-right:15px;}
</style>
<style>  
	td {
	text-align:center; /*设置水平居中*/
	vertical-align:middle;/*设置垂直居中*/
	} 
</style>  
</head>
<body>
	<div id="body_content" class="cfix">
		<div id="main">
			<br />
			<div class="breadcrumb"><font>中奖纪录</font>
		     </div>
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 80px;">序号</th>
						<th style="width: 120px;">姓名</th>
						<th style="width: 150px;">手机号</th>
						<th style="width: 120px;">中奖级别</th>
						<th style="width: 200px;">中奖日期</th>
					</tr>
					<c:forEach items="${list}" var="kxWinning" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${kxWinning.name}</td>
							<td>${kxWinning.phone}</td>
							<td><c:if test="${kxWinning.winLevel==1}">特等奖</c:if>
							<c:if test="${kxWinning.winLevel==2}">一等奖</c:if>
							<c:if test="${kxWinning.winLevel==3}">二等奖</c:if>
							<c:if test="${kxWinning.winLevel==4}">三等奖</c:if>
							<c:if test="${kxWinning.winLevel==5}">小确幸30元现金兑换码</c:if>
							<c:if test="${kxWinning.winLevel==6}">阳光普照奖50老客户</c:if>
							</td>
							<td>${fn:substring(kxWinning.createDt, 0, 19)}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>