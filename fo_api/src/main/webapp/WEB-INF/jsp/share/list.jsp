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
<title>分享配置列表</title>
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
    function uploadImage(){
        $.ajaxFileUpload({
            url:'/admin/share/upload.do',
            secureuri:false,
            fileElementId:'uploadimage',
            dataType: 'json',
            success: function (data)
            {	
                if(data.error != 0){
                    alert('上传失败，请重试！');
                    return;
                }
                $('#fileUrl').val(data.url);
                alert("上传成功");
                //window.location.reload();
            },
            error: function ()
            {
                alert('上传失败，请重试！');
            }
        });
    }
    
    function changeStatus(id,status){
    		$.ajax({
　　　　		url: '/admin/share/update.do',
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
    
    function saveShare(){
    		var fileUrl = $("#fileUrl").val();
    		var title = $("#title").val();
    		var subTitle = $("#subTitle").val();
    		var downloadUrl = $("#downloadUrl").val();
    		var type = $("#type").val();
    		if(fileUrl=='' || title==''){
    			alert('请先上传图片或填写主标题');
    			return;
    		}
    		$.ajax({
    　　　　		url: '/admin/share/save.do',
    　　　　　	type: 'get',
    　　　	　 　data: { fileUrl: fileUrl,title:title, subTitle:subTitle,downloadUrl:downloadUrl,type:type},
    　　　　　    　//请求成功后触发
    　　　　      success: function (data) { 
    			if(data.error != 0){
     				alert('保存失败，请重试');
    			}
    				alert("保存成功"); 
    				window.location.reload();
    	      	},
    		  
    	      	error: function ()
    	        {
    	             alert('保存失败，请重试！');
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
			<div class="breadcrumb"><font>分享配置</font>
		     </div>
			<div class="dateSelect mB15 ovh" id="queryUser">
					<div class="fl ftD mB8">
						<div class="ftDw ">
							<input class="input" id="uploadimage" type="file" name="image" />
							<button class="small gray" onclick="uploadImage()">上传</button>
						</div>
					</div>

				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">主标题</p>
						<input type="text" id="title" name="title" value="${title }">
						<input type="hidden" id="fileUrl" name="fileUrl" value="">
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">副标题</p>
						<input type="text" id="subTitle" name="subTitle" value="${subTitle }">
					</div>
				</div>
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">下载地址</p>
						<input type="text" id="downloadUrl" name="downloadUrl" value="${downloadUrl }">
					</div>
				</div>
			</div>

			<div class="dateSelect mB15 ovh" id="queryshare1">
				<div class="ft gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<label>平台
						<select style='width: 100px;' class="txtc" name="type" id="type">
							<option value="0">请求选择</option>
                            <option value="1" <c:if test="${share.type==1}">selected</c:if>>android</option>
                            <option value="2" <c:if test="${user.type==2}">selected</c:if>>ios</option>
                         </select>
						</label>
					</div>
				</div>
				<span class="bnta mL10"><input type="submit" 
					id="query-share-list" value="提 交" onclick="saveShare()"/></span>
			</div>
			<br />
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 80px;">序号</th>
						<th style="width: 200px;">主标题</th>
						<th style="width: 200px;">副标题</th>
						<th style="width: 200px;">下载链接</th>
						<th style="width: 200px;">图标</th>
						<th style="width: 100px;">平台类型</th>
						<th style="width: 100px;">状态</th>
						<th style="width: 200px;">操作</th>
					</tr>
					<c:forEach items="${list}" var="share" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${share.title}</td>
							<td>${share.subTitle}</td>
							<td>${share.downloadUrl}</td>
							<td><img src="${share.icon}"/></td>
							<td><c:if test="${share.type==1}">android</c:if>
								<c:if test="${share.type==2}">ios</c:if>
							</td>
							<td><c:if test="${share.status==0}">停用</c:if>
								<c:if test="${share.status==1}">启用</c:if>
							</td>
							<td><c:if test="${share.status==0}"><button onclick="changeStatus(${share.id},1)">启用</button></c:if>  <c:if test="${share.status==1}"><button onclick="changeStatus(${share.id},0)">禁用</button></c:if>       <button onclick="changeStatus(${share.id},2)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<div id="pagination" class="pagination">
					<a href="#">总记录数：${count},总页数：${pageCount} </a>
					<a class="prev" <%if(pageNow>1){ %> href="<%=request.getContextPath() %>/admin/share/list.do?start=${ pageNow-1}">
					<%}else{%> href="javascript:volid(0);"> <%} %>上一页</a>
					
					
					<%for(int i=1;i<=pageCount;i++){if(i>=1&&i<=pageCount){ %>
					<a <%if(i==pageNow){%> class="current" <%} %> href="<%=request.getContextPath() %>/admin/share/list.do?start=<%=i%>"><%=i%></a>
					<%}}%>
				
				    <a class="next" <%if(pageNow<pageCount){ %> href="<%=request.getContextPath() %>/admin/share/list.do?start=<%=pageNow+1%>"
				    <%}else{%> href="javascript:volid(0);" <%} %>>下一页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>