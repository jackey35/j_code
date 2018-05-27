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
<%
	Object pageObject = request.getAttribute("pageNow");
	int pageNow = 1;
	if(pageObject != null){
		pageNow = Integer.parseInt(pageObject.toString());
	}
	
	Object cntObject = request.getAttribute("pageCount");
	int pageCount= 0 ;
	if(cntObject != null){
		pageCount = Integer.parseInt(cntObject.toString());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中奖列表</title>
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="../skin/public.css" />
<link type="text/css" rel="stylesheet" href="../skin/top.css" />
<link type="text/css" rel="stylesheet" href="../skin/main.css" />
<link type="text/css" rel="stylesheet" href="../skin/side.css" />
<link type="text/css" rel="stylesheet" href="../skin/jquery.autocomplete.css" />
<link type="text/css" rel="stylesheet" href="../skin/jquery-ui-1.9.2.custom.min.css" />
<link type="text/css" rel="stylesheet" href="../skin/page.css"  />
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
	<script type="text/javascript">
		$().ready(function(){
			$("#queryUser input[name=sdt]").datepicker({dateFormat: "yy-mm-dd"});
			$("#queryUser input[name=edt]").datepicker({dateFormat: "yy-mm-dd"});
		});
		
		$(function(){
		    $("#exportExcel").click(function(){
		    	var name = $("#name").val();
	    		var phone = $("#phone").val();
	    		var sdt = $("#sdt").val();
	    		var edt = $("#edt").val();
	    		var newUrl = 'http://www.jinxinsenhui.com/admin/winning/export.do?name='+name+"&phone="+phone+",sdt="+sdt+",edt="+edt;    //设置新提交地址
	        $("#queryForm").attr('action',newUrl);    //通过jquery为action属性赋值
	        $("#queryForm").submit();    //提交ID为myform的表单
		    });
		    
		    $("#query-list").click(function(){
		    	var name = $("#name").val();
	    		var phone = $("#phone").val();
	    		var sdt = $("#sdt").val();
	    		var edt = $("#edt").val();
	    		var newUrl = 'http://www.jinxinsenhui.com/admin/winning/list.do?name='+name+"&phone="+phone+",sdt="+sdt+",edt="+edt;    //设置新提交地址
	        $("#queryForm").attr('action',newUrl);    //通过jquery为action属性赋值
	        $("#queryForm").submit();    //提交ID为myform的表单
		    })
		})
	</script>
</head>
<body>
	<jsp:include page="../common/admin_head.jsp"></jsp:include>
	<div id="body_content" class="cfix">
		<!-- Start:left -->
		<jsp:include page="../common/navigation.jsp"></jsp:include>
		<!-- End:left -->
		<div id="main">
			<br />
			<div class="breadcrumb"><font>中奖纪录</font>
		     </div>
			<form action="<%=request.getContextPath() %>/admin/winning/list.do"  method="get" id="queryForm">
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<label>姓名
						<input style='width:100px;' class="txtc" id="name" name="name" type="text" value='${kxWinning.name}'>
						</label>
					</div>
				</div>
				<div class="ft fl gray"></div>
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<label>手机号
						<input style='width:100px;' class="txtc" id="phone" name="phone" type="text" value='${kxWinning.phone}'>
						</label>
					</div>
				</div>
			    <div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">开始时间</p>
						<input type="text" class="txte gray fl" name="sdt"  id="sdt" value="${kxWinning.sdt }"/>
					</div>
				</div>
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">截止时间</p>
						<input type="text" class="txte gray fl" name="edt"  id="edt" value="${kxWinning.edt }"/>
					</div>
				</div>
				<div class="ft fl gray"></div>
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<span class="bnta mL10"><input type="button"  
							id="query-list" value="查 询" /></span>
					</div>
				</div>
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<span class="bnta mL10"><input type="button" id="exportExcel" name="exportExcel" value="导出excel" /></span>
					</div>
				</div>
			</div>
			</form>
			<br />
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 80px;">序号</th>
						<th style="width: 120px;">姓名</th>
						<th style="width: 150px;">手机号</th>
						<th style="width: 300px;">联系地址</th>
						<th style="width: 120px;">中奖级别</th>
						<th style="width: 200px;">中奖日期</th>
					</tr>
					<c:forEach items="${list}" var="kxWinning" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${kxWinning.name}</td>
							<td>${kxWinning.phone}</td>
							<td>${kxWinning.address}</td>
							<td><c:if test="${kxWinning.winLevel==1}">特等奖</c:if>
							<c:if test="${kxWinning.winLevel==2}">一等奖</c:if>
							<c:if test="${kxWinning.winLevel==3}">二等奖</c:if>
							<c:if test="${kxWinning.winLevel==4}">三等奖</c:if>
							<c:if test="${kxWinning.winLevel==5}">小确幸30元现金兑换码</c:if>
							<c:if test="${kxWinning.winLevel==6}">阳光普照奖50老客户</c:if>
							</td>
							<td>${kxWinning.createDt}</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<div id="pagination" class="pagination">
					<a href="javascript:void(0)" >总记录数：${count},总页数：${pageCount} </a>
					<a class="prev" <%if(pageNow>1){ %> href="<%=request.getContextPath() %>/admin/winning/list.do?start=${ pageNow-1}&name=${kxWinning.name}&phone=${kxWinning.phone }">
					<%}else{%> href="javascript:volid(0);"> <%} %>上一页</a>
					<%if((pageNow-2)>=2) {%>
					<a href="javascript:void(0)">...</a>
					<%} %>
					
					<%for(int i=1;i<=pageCount;i++){if(i>=(pageNow-2) && i<=(pageNow+2)){ %>
					
					<a <%if(i==pageNow){%> class="current" <%} %> href="<%=request.getContextPath() %>/admin/winning/list.do?start=<%=i%>&name=${kxWinning.name}&phone=${kxWinning.phone }"><%=i%></a>
					<%}}%>
					<%if(pageNow+2<pageCount) {%>
					<a href="javascript:void(0)">...</a>
					<%} %>
				    <a class="next" <%if(pageNow<pageCount){ %> href="<%=request.getContextPath() %>//admin/winning/list.do?start=<%=pageNow+1%>&name=${kxWinning.name}&phone=${kxWinning.phone }"
				    <%}else{%> href="javascript:volid(0);" <%} %>>下一页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>