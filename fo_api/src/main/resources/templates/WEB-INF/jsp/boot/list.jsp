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
<title>启动项配置</title>
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
	<script type="text/javascript">
    function uploadImage(){
        $.ajaxFileUpload({
            url:'/admin/boot/upload.do',
            secureuri:false,
            fileElementId:'uploadimage',
            dataType: 'json',
            success: function (data)
            {
                if(data.error != 0){
                    alert('上传失败，请重试！');
                    return;
                }
                $('#imgview').html('<img src="' + data.url + '"/>');
                alert("上传成功");
                window.location.reload();
            },
            error: function ()
            {
                alert('上传失败，请重试！');
            }
        });
    }
    
    function changeStatus(id,status){
    		$.ajax({
　　　　		url: '/admin/boot/update.do',
　　　　　	type: 'get',
　　　	　 　data: { id: id,status:status },
　　　　　    　//请求成功后触发
　　　　      success: function (data) { 
			if(data.error != 0){
 				alert('更新失败，请重试');
 				window.location.reload();
 				return;
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
			<div class="breadcrumb"><font>启动项配置</font>
		     </div>
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="fl ftD mB8">
					<div class="ftDw cfix">
						<input class="input" id="uploadimage" type="file" name="image" />
						<button class="small gray" onclick="uploadImage()">上传</button>
					</div>
				</div>
			</div>
			<br />
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 210px;">序号</th>
						<th style="width: 310px;">图片预览</th>
						<th style="width: 310px;">状态</th>
						<th style="width: 220px;">操作</th>
					</tr>
					<c:forEach items="${list}" var="boot" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td><img src="${boot.bootUrl}"></img></td>
							<td><c:if test="${boot.status==0}">禁用</c:if>
								<c:if test="${boot.status==1}">启用</c:if></td>
							<td><c:if test="${boot.status==0}"><button onclick="changeStatus(${boot.id},1)">启用</button></c:if>  <c:if test="${boot.status==1}"><button onclick="changeStatus(${boot.id},0)">禁用</button></c:if>       <button onclick="changeStatus(${boot.id},2)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>