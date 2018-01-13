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
<title>符列表</title>
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
	<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
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
	
	<script type="text/javascript">
    
    function changeStatus(id,status){
    		$.ajax({
　　　　		url: '/admin/p/update.do',
　　　　　	type: 'get',
　　　	　 　data: { id: id,status:status },
　　　　　    　//请求成功后触发
　　　　      success: function (data) { 
			if(data.error != 0){
 				alert('更新失败，请重试');
 				window.location.reload();
			}
				alert("更新成功"); 
				window.location.reload();
	      	},
		  
	      	error: function ()
	        {
	             alert('更新失败，请重试！');
	             window.location.reload();
	        }
    		});
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
			<div class="breadcrumb"><font>符列表----<a href="/admin/p/predit.do">添加新符</a></font>
		     </div>
		     <form action="<%=request.getContextPath() %>/admin/p/list.do"  method="get">
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<p class="fl">符名称</p>
						<input type="text" id="pName" name="pName" value="${product.pName }">
						</label>
					</div>
				</div>
				<span class="bnta mL10"><input type="submit" 
					id="query-share-list" value="提 交"/></span>
			</div>
			</form>
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 80px;">序号</th>
						<th style="width: 200px;">符名称</th>
						<th style="width: 200px;">符小图</th>
						<th style="width: 200px;">符大图</th>
						<th style="width: 200px;">符价格</th>
						<th style="width: 200px;">符描述</th>
						<th style="width: 100px;">符种类</th>
						<th style="width: 100px;">状态</th>
						<th style="width: 200px;">操作</th>
					</tr>
					<c:forEach items="${list}" var="p" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td><a href="admin/p/predit.do?id=${p.id }" title="点击修改">${p.pName}</a></td>
							<td><img src="${p.icon}"  width="150px" height="150px"/></td>
							<td><img src="${p.pPic}" width="150px" height="150px"/></td>
							<td>${p.price}</td>
							<td>${p.gfDesc}</td>
							<td><c:if test="${p.cateCode==1}">金钱财运</c:if>
								<c:if test="${p.cateCode==2}">桃花姻缘</c:if>
								<c:if test="${p.cateCode==3}">避祸镇煞</c:if>
								<c:if test="${p.cateCode==4}">平安护身</c:if>
								<c:if test="${p.cateCode==5}">新年必请</c:if>
								<c:if test="${p.cateCode==6}">安家镇宅</c:if>
								<c:if test="${p.cateCode==7}">文昌官运</c:if>
								<c:if test="${p.cateCode==8}">送子添丁</c:if>
								<c:if test="${p.cateCode==9}">护身符</c:if> 

							</td>
							<td><c:if test="${p.status==0}">停用</c:if>
								<c:if test="${p.status==1}">启用</c:if>
							</td>
							<td><c:if test="${p.status==0}"><button onclick="changeStatus(${p.id},1)">启用</button></c:if>  <c:if test="${p.status==1}"><button onclick="changeStatus(${p.id},0)">禁用</button></c:if>       <button onclick="changeStatus(${p.id},2)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				
			</div>
		</div>
	</div>
</body>
</html>