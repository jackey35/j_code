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
<title>支付流水列表</title>
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
			<div class="breadcrumb"><font>支付流水</font>
		     </div>
			<form action="<%=request.getContextPath() %>/admin/order/list.do"  method="get">
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<label>商品名称
						<input style='width:100px;' class="txtc" id="pName" name="pName" type="text" value='${order.pName}'>
						</label>
					</div>
				</div>

				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">下单日期</p>
						<input type="text" class="txte gray fl" name="startDt" value="${startDt }"/>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">下单日期</p>
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
						<th style="width: 80px;">序号</th>
						<th style="width: 120px;">订单号</th>
						<th style="width: 150px;">商品名称</th>
						<th style="width: 200px;">下单日期</th>
						<th style="width: 200px;">支付日期</th>
						<th style="width: 90px;">支付金额</th>
						<th style="width: 120px;">渠道名称</th>
						<th style="width: 120px;">会员名称</th>
						<th style="width: 120px;">订单状态</th>
					</tr>
					<c:forEach items="${list}" var="order" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${order.orderNo}</td>
							<td>${order.pName}</td>
							<td>${order.createDt}</td>
							<td>${order.payDt}</td>
							<td>${order.orderPrice}</td>
							<td><c:if test="${order.orderChannel==1}">百度手助</c:if>
								<c:if test="${order.orderChannel==2}">360</c:if>
								<c:if test="${order.orderChannel==3}">华为</c:if>
								<c:if test="${order.orderChannel==4}">小米</c:if>
								<c:if test="${order.orderChannel==5}">OPPO</c:if>
								<c:if test="${order.orderChannel==6}">VIVO</c:if>
								<c:if test="${order.orderChannel==7}">魅族</c:if>
								<c:if test="${order.orderChannel==8}">酷派</c:if>
								<c:if test="${order.orderChannel==9}">联想</c:if>
								<c:if test="${order.orderChannel==10}">应用宝</c:if>
								<c:if test="${order.orderChannel==11}">安智</c:if>
								<c:if test="${order.orderChannel==12}">PP</c:if>
								<c:if test="${order.orderChannel==13}">搜狗</c:if>
								<c:if test="${order.orderChannel==14}">三星</c:if>
								<c:if test="${order.orderChannel==15}">锤子</c:if>
								<c:if test="${order.orderChannel==16}">金立</c:if>
								<c:if test="${order.orderChannel==17}">应用汇</c:if>
								<c:if test="${order.orderChannel==18}">木蚂蚁</c:if>
								<c:if test="${order.orderChannel==19}">优亿</c:if>
							</td>
							<td>${order.nickName}</td>
							<td><c:if test="${order.status==0}">待支付</c:if>
								<c:if test="${order.status==1}">支付成功</c:if>
								<c:if test="${order.status==2}">支付失败</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<div id="pagination" class="pagination">
					<a class="prev" <%if(pageNow>1){ %> href="<%=request.getContextPath() %>/admin/order/list.do?start=${ pageNow-1}&pName=${order.pName }&startDt=${startDt}&endDt=${endDt}">
					<%}else{%> href="javascript:volid(0);"> <%} %>上一页</a>
					
					
					<%for(int i=1;i<=pageCount;i++){if(i>=1&&i<=pageCount){ %>
					<a <%if(i==pageNow){%> class="current" <%} %> href="<%=request.getContextPath() %>/admin/order/list.do?start=<%=i%>&pName=${order.pName }&startDt=${startDt}&endDt=${endDt}"><%=i%></a>
					<%}}%>
				
				    <a class="next" <%if(pageNow<pageCount){ %> href="<%=request.getContextPath() %>/admin/order/list.do?start=<%=pageNow+1%>&pName=${order.pName }&startDt=${startDt}&endDt=${endDt}"
				    <%}else{%> href="javascript:volid(0);" <%} %>>下一页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>