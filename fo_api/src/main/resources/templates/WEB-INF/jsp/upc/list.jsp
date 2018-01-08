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
<title>渠道包列表</title>
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
　　　　		url: '/admin/upc/update.do',
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
    
    function saveUpgrade(){
    		var versionNo = $("#versionNo").val();
    		var memo = $("#memo").val();
    		var channel = $("#channel").val();
    		var downloadUrl = $("#downloadUrl").val();
    		var type = $("#type").val();
    		alert(channel);
    		if(versionNo=='' ){
    			alert('请填写版本号');
    			return;
    		}
    		$.ajax({
    　　　　		url: '/admin/upc/save.do',
    　　　　　	type: 'get',
    　　　	　 　data: { versionNo: versionNo,memo:memo, channel:channel,downloadUrl:downloadUrl,type:type},
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
			<div class="breadcrumb"><font>渠道包配置</font>
		     </div>
			<div class="dateSelect mB15 ovh" id="queryUser">
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">版本号</p>
						<input type="text" id="versionNo" name="versionNo" value="${versionNo }">
						<input type="hidden" id="fileUrl" name="fileUrl" value="">
					</div>
				</div>
				
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<p class="fl">升级文案</p>
						<input type="text" id="memo" name="memo" value="${memo }">
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
				<div class="ft fl gray"></div>
				<div class="fl ftD">
					<div class="ftDw cfix">
						<label>渠道
						<select style='width: 100px;' class="txtc" id="channel" name="channel">
							<option value="0">请求选择</option>
                            <option value="1" <c:if test="${upc.channel==1}">selected</c:if>>百度手助</option>
                            <option value="2" <c:if test="${upc.channel==2}">selected</c:if>>360</option>
                            <option value="3" <c:if test="${upc.channel==3}">selected</c:if>>华为</option>
                            <option value="4" <c:if test="${upc.channel==4}">selected</c:if>>小米</option>
                            <option value="5" <c:if test="${upc.channel==5}">selected</c:if>>OPPO</option>
                            <option value="6" <c:if test="${upc.channel==6}">selected</c:if>>VIVO</option>
                            <option value="7" <c:if test="${upc.channel==7}">selected</c:if>>魅族</option>
                            <option value="8" <c:if test="${upc.channel==8}">selected</c:if>>酷派</option>
                            <option value="9" <c:if test="${upc.channel==9}">selected</c:if>>联想</option>
                            <option value="10" <c:if test="${upc.channel==10}">selected</c:if>>应用宝</option>
                            <option value="11" <c:if test="${upc.channel==11}">selected</c:if>>安智</option>
                            <option value="12" <c:if test="${upc.channel==12}">selected</c:if>>PP</option>
                            <option value="13" <c:if test="${upc.channel==13}">selected</c:if>>搜狗</option>
                            <option value="14" <c:if test="${upc.channel==14}">selected</c:if>>三星</option>
                            <option value="15" <c:if test="${upc.channel==15}">selected</c:if>>锤子</option>
                            <option value="16" <c:if test="${upc.channel==16}">selected</c:if>>金立</option>
                            <option value="17" <c:if test="${upc.channel==17}">selected</c:if>>应用汇</option>
                            <option value="18" <c:if test="${upc.channel==18}">selected</c:if>>木蚂蚁</option>
                            <option value="19" <c:if test="${upc.channel==19}">selected</c:if>>优亿</option>
						</select>
						</label>
					</div>
				</div>
				
				<div class="ft fl gray"></div>
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
					id="query-share-list" value="提 交" onclick="saveUpgrade()"/></span>
			</div>
			<br />
			<div id="table-style">
				<table id="mytable" cellspacing=0>
					<tr>
						<th style="width: 80px;">序号</th>
						<th style="width: 200px;">版本号</th>
						<th style="width: 200px;">升级文案</th>
						<th style="width: 200px;">下载链接</th>
						<th style="width: 200px;">渠道</th>
						<th style="width: 100px;">平台类型</th>
						<th style="width: 100px;">状态</th>
						<th style="width: 200px;">操作</th>
					</tr>
					<c:forEach items="${list}" var="upc" varStatus="varStatus">
						<tr>
							<td>${varStatus.index+1}</td>
							<td>${upc.versionNo}</td>
							<td>${upc.memo}</td>
							<td>${upc.downloadUrl}</td>
							<td>
							<c:if test="${upc.channel==1}">百度手助</c:if>
								<c:if test="${upc.channel==2}">360</c:if>
								<c:if test="${upc.channel==3}">华为</c:if>
								<c:if test="${upc.channel==4}">小米</c:if>
								<c:if test="${upc.channel==5}">OPPO</c:if>
								<c:if test="${upc.channel==6}">VIVO</c:if>
								<c:if test="${upc.channel==7}">魅族</c:if>
								<c:if test="${upc.channel==8}">酷派</c:if>
								<c:if test="${upc.channel==9}">联想</c:if>
								<c:if test="${upc.channel==10}">应用宝</c:if>
								<c:if test="${upc.channel==11}">安智</c:if>
								<c:if test="${upc.channel==12}">PP</c:if>
								<c:if test="${upc.channel==13}">搜狗</c:if>
								<c:if test="${upc.channel==14}">三星</c:if>
								<c:if test="${upc.channel==15}">锤子</c:if>
								<c:if test="${upc.channel==16}">金立</c:if>
								<c:if test="${upc.channel==17}">应用汇</c:if>
								<c:if test="${upc.channel==18}">木蚂蚁</c:if>
								<c:if test="${upc.channel==19}">优亿</c:if>
							</td>
							<td><c:if test="${upc.type==1}">android</c:if>
								<c:if test="${upc.type==2}">ios</c:if>
							</td>
							<td><c:if test="${upc.status==0}">停用</c:if>
								<c:if test="${upc.status==1}">启用</c:if>
							</td>
							<td><c:if test="${upc.status==0}"><button onclick="changeStatus(${upc.id},1)">启用</button></c:if>  <c:if test="${upc.status==1}"><button onclick="changeStatus(${upc.id},0)">禁用</button></c:if>       <button onclick="changeStatus(${upc.id},2)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br />
				<div id="pagination" class="pagination">
					<a class="prev" <%if(pageNow>1){ %> href="<%=request.getContextPath() %>/admin/upc/list.do?start=${ pageNow-1}">
					<%}else{%> href="javascript:volid(0);"> <%} %>上一页</a>
					
					
					<%for(int i=1;i<=pageCount;i++){if(i>=1&&i<=pageCount){ %>
					<a <%if(i==pageNow){%> class="current" <%} %> href="<%=request.getContextPath() %>/admin/upc/list.do?start=<%=i%>"><%=i%></a>
					<%}}%>
				
				    <a class="next" <%if(pageNow<pageCount){ %> href="<%=request.getContextPath() %>/admin/upc/list.do?start=<%=pageNow+1%>"
				    <%}else{%> href="javascript:volid(0);" <%} %>>下一页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>