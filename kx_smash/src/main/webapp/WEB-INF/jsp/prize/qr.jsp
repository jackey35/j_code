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
<title>上传注册二维码</title>
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
	<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
	<script type="text/javascript">
function uploadZip(){
    $.ajaxFileUpload({
        url:'/admin/prize/saveqr.do',
        secureuri:false,
        fileElementId:'uploadzip',
        dataType: 'json',
        success: function (data)
        {	
            if(data.error != 0){
                alert('上传失败，请重试！');
                return;
            }
            alert("上传成功");
        },
        error: function ()
        {
            alert('上传失败，请重试！');
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
			<div class="breadcrumb"><font>二维码</font><font color="red">${msg }</font>
		     </div>
		     <div id="addDiv">
			         <div class="project-add">
			              <div class="ProLeft">
				              <ul>
				              <li>
				               <input class="input" id="uploadzip" type="file" name="uploadzip" />
							   <button class="small gray" onclick="uploadZip()">上传二维码图</button>
				              </li>
				              </ul>
			          	</div>
			           </div>
		         </div>
				</div>
	</div>
</body>
</html>