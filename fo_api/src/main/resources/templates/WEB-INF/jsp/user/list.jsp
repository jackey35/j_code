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
			<br />
			<div class="breadcrumb"><font>会员列表</font>
		     </div>
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
                            <option value="1" <c:if test="${user.regChannel==1}">selected</c:if>>百度手助</option>
                            <option value="2" <c:if test="${user.regChannel==2}">selected</c:if>>360</option>
                            <option value="3" <c:if test="${user.regChannel==3}">selected</c:if>>华为</option>
                            <option value="4" <c:if test="${user.regChannel==4}">selected</c:if>>小米</option>
                            <option value="5" <c:if test="${user.regChannel==5}">selected</c:if>>OPPO</option>
                            <option value="6" <c:if test="${user.regChannel==6}">selected</c:if>>VIVO</option>
                            <option value="7" <c:if test="${user.regChannel==7}">selected</c:if>>魅族</option>
                            <option value="8" <c:if test="${user.regChannel==8}">selected</c:if>>酷派</option>
                            <option value="9" <c:if test="${user.regChannel==9}">selected</c:if>>联想</option>
                            <option value="10" <c:if test="${user.regChannel==10}">selected</c:if>>应用宝</option>
                            <option value="11" <c:if test="${user.regChannel==11}">selected</c:if>>安智</option>
                            <option value="12" <c:if test="${user.regChannel==12}">selected</c:if>>PP</option>
                            <option value="13" <c:if test="${user.regChannel==13}">selected</c:if>>搜狗</option>
                            <option value="14" <c:if test="${user.regChannel==14}">selected</c:if>>三星</option>
                            <option value="15" <c:if test="${user.regChannel==15}">selected</c:if>>锤子</option>
                            <option value="16" <c:if test="${user.regChannel==16}">selected</c:if>>金立</option>
                            <option value="17" <c:if test="${user.regChannel==17}">selected</c:if>>应用汇</option>
                            <option value="18" <c:if test="${user.regChannel==18}">selected</c:if>>木蚂蚁</option>
                            <option value="19" <c:if test="${user.regChannel==19}">selected</c:if>>优亿</option>
						</select>
						</label>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">注册日期</p>
						<input type="text" class="txte gray fl" name="startDt" value="${startDt }"/>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">注册日期</p>
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
							<td><c:if test="${user.regChannel==1}">百度手助</c:if>
								<c:if test="${user.regChannel==2}">360</c:if>
								<c:if test="${user.regChannel==3}">华为</c:if>
								<c:if test="${user.regChannel==4}">小米</c:if>
								<c:if test="${user.regChannel==5}">OPPO</c:if>
								<c:if test="${user.regChannel==6}">VIVO</c:if>
								<c:if test="${user.regChannel==7}">魅族</c:if>
								<c:if test="${user.regChannel==8}">酷派</c:if>
								<c:if test="${user.regChannel==9}">联想</c:if>
								<c:if test="${user.regChannel==10}">应用宝</c:if>
								<c:if test="${user.regChannel==11}">安智</c:if>
								<c:if test="${user.regChannel==12}">PP</c:if>
								<c:if test="${user.regChannel==13}">搜狗</c:if>
								<c:if test="${user.regChannel==14}">三星</c:if>
								<c:if test="${user.regChannel==15}">锤子</c:if>
								<c:if test="${user.regChannel==16}">金立</c:if>
								<c:if test="${user.regChannel==17}">应用汇</c:if>
								<c:if test="${user.regChannel==18}">木蚂蚁</c:if>
								<c:if test="${user.regChannel==19}">优亿</c:if>
							</td>
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