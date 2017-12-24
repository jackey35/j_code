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
<title>会员列表</title>
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
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/highcharts.js"></script>
	<script type="text/javascript" src="../js/summary.js"></script>
	<script type="text/javascript" src="../js/jquery.autocomplete.pack.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			$("#queryUser input[name=startDt]").datepicker({dateFormat: "yy-mm-dd"});
			$("#queryUser input[name=endDt]").datepicker({dateFormat: "yy-mm-dd"});
		});
	</script>
	<script type="text/javascript">
		function show(obj,id) { 
			var objDiv = $("#"+id+""); 
			$(objDiv).css("display","block"); 
			$(objDiv).css("left", event.clientX+50); 
			$(objDiv).css("top", event.clientY-40); 
		} 
		function hide(obj,id) { 
			var objDiv = $("#"+id+""); 
			$(objDiv).css("display", "none"); 
		} 
	</script>
</head>
<body>
	<jsp:include page="../common/admin_head.jsp"></jsp:include>
	<div id="body_content" class="cfix">
		<!-- Start:left -->
		<jsp:include page="../common/navigation.jsp"></jsp:include>
		<!-- End:left -->
		<div id="main">
			<div class=" mB15 mT10 ovh">
				<ul class="fl wtypes ovh">
				</ul>
			</div>
			<br />
			<form action="<%=request.getContextPath() %>/admin/user/list.do"  method="get">
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<label>会员昵称
						<input style='width:100px;' class="txtc" id="nickName" name="nickName" type="text" value='${user.nickName}'>
						</label>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<label>渠道
						<select style='width: 100px;' class="txtc" name="regChannel">
							<option value="0">请求选择</option>
                            <option value="1" <c:if test="${user.regChannel==1}">selected</c:if>>应用宝</option>
                            <option value="2" <c:if test="${user.regChannel==2}">selected</c:if>>91助手</option>
						</select>
						</label>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">检测日期</p>
						<input type="text" class="txte gray fl" name="startDt" value="${startDt }"/>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">检测日期</p>
						<input type="text" class="txte gray fl" name="endDt"  value="${endDt }"/>
					</div>
				</div>
				
				<span class="bnta mL10"><input type="submit" 
					id="query-aiDetect-list" value="查 询" /></span>
			</div>
			</form>
			<br />
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 210px;">序号</th>
						<th style="width: 310px;">会员昵称</th>
						<th style="width: 310px;">注册日期</th>
						<th style="width: 220px;">渠道名称</th>
					</tr>
					<c:forEach items="${list}" var="user" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${user.nickName}</td>
							<td>${user.regTime}</td>
							<td>${user.regChannel}</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<div id="pagination" class="pagination">
					<a class="prev" <%if(pageNow>1){ %> href="<%=request.getContextPath() %>/admin/user/list.do?start=${ pageNow-1}&nickName=${user.nickName }&regChannel=${user.regChannel}&startDt=${startDt}&endDt=${endDt}">
					<%}else{%> href="javascript:volid(0);"> <%} %>上一页</a>
					
					
					<%for(int i=1;i<=pageCount;i++){if(i>=1&&i<=pageCount){ %>
					<a <%if(i==pageNow){%> class="current" <%} %> href="<%=request.getContextPath() %>/admin/user/list.do?start=<%=i%>&nickName=${user.nickName }&regChannel=${user.regChannel}&startDt=${startDt}&endDt=${endDt}"><%=i%></a>
					<%}}%>
				
				    <a class="next" <%if(pageNow<pageCount){ %> href="<%=request.getContextPath() %>/admin/user/list.do?start=<%=pageNow+1%>&nickName=${user.nickName }&regChannel=${user.regChannel}&startDt=${startDt}&endDt=${endDt}"
				    <%}else{%> href="javascript:volid(0);" <%} %>>下一页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>