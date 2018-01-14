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
<title>挂符编辑</title>
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
	<script src="../js/tinymce/tinymce.min.js"></script>
	<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
	<script type="text/javascript">
$(function(){
    var tx = tinymce.init({
         selector: 'textarea',
         height: 500,
         language: 'zh_CN',
         plugins: [
             'advlist autolink lists link image charmap print preview anchor',
             'searchreplace visualblocks code fullscreen',
             'insertdatetime media table contextmenu paste code uploadimg sohutv'
         ],
         toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimg sohutv',

         extended_valid_elements :'script[type|src]'
        ,setup: function(editor) {
        	window._editor=editor;
//            editor.on('keydown change', function() {
//                  textContent = editor.getContent()
//            });
        }

     });
      
});

function uploadSmallImage(){
    $.ajaxFileUpload({
        url:'/admin/p/upload.do',
        secureuri:false,
        fileElementId:'uploadSmallimage',
        dataType: 'json',
        success: function (data)
        {	
            if(data.error != 0){
                alert('上传失败，请重试！');
                return;
            }
            $('#icon').val(data.url);
            alert("上传成功");
            //window.location.reload();
        },
        error: function ()
        {
            alert('上传失败，请重试！');
        }
    });
}
function uploadImage(){
    $.ajaxFileUpload({
        url:'/admin/p/upload.do',
        secureuri:false,
        fileElementId:'uploadimage',
        dataType: 'json',
        success: function (data)
        {	
            if(data.error != 0){
                alert('上传失败，请重试！');
                return;
            }
            $('#pPic').val(data.url);
            alert("上传成功");
            //window.location.reload();
        },
        error: function ()
        {
            alert('上传失败，请重试！');
        }
    });
}

function saveProduct(){
	var id = $("#pid").val();
	if(id == null || id == ''){
		id = 0;
	}
	var pName = $("#pName").val();
	var price = $("#price").val();
	var pPic = $("#pPic").val();
	var icon = $("#icon").val();
	var cateCode = $("#cateCode").val();
	var activeEditor = tinymce.activeEditor; 
	var editBody = activeEditor.getBody(); 
	activeEditor.selection.select(editBody); 
	var gfDesc = activeEditor.selection.getContent( { 'format' : 'text' } );
	
	//var gfDesc =tinymce.activeEditor.getContent() ;
	if(pName=='' || price==''||pPic=='' || gfDesc=='' || icon==''){
		alert('请填写符的名称或价格或图片、描述');
		return;
	}
	$.ajax({
		url: '/admin/p/save.do',
　   	type: 'get',
	　 　data: {id:id, pName: pName,price:price, pPic:pPic,icon:icon,cateCode:cateCode,gfDesc:gfDesc},
　    　//请求成功后触发
      	success: function (data) { 
		if(data.error != 0){
				alert('保存失败，请重试');
		}
			alert("保存成功"); 
			window.location.replace("/admin/p/list.do");
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
			<div class="breadcrumb"><font>添加新符</font><font color="red">${msg }</font>
		     </div>
		     <div id="addDiv">
			         <div class="project-add">
			              <div class="ProLeft">
				              <ul>
				              <li>
				              <b style="font-size: 15px;"></b>符的名称
				              <input type="hidden" id="pid" name="pid" value="${p.id }">
				              <input type="text" id="pName" name="pName" value="${p.pName }">
				              </li>
				              <li>
				              <b style="font-size: 15px;"></b>挂符价格
				              <input type="text"  id="price" name="price" value="${p.price }">
				              </li>
				              <li>
				               <input class="input" id="icon" type="hidden" name="icon" value="${p.icon}"/>
				               <input class="input" id="uploadSmallimage" type="file" name="image"/>
							   <button class="small gray" onclick="uploadSmallImage()">上传符小图</button>
				              </li>
				              <li>
				               <input class="input" id="pPic" type="hidden" name="pPic" value="${p.pPic}"/>
				               <input class="input" id="status" type="hidden" name="status" value="${p.status}"/>
				               <input class="input" id="uploadimage" type="file" name="image" />
							   <button class="small gray" onclick="uploadImage()">上传符大图</button>
				              </li>
				              <li>
				              <label>符的类别
								<select style='width: 100px;' class="txtc" name="cateCode" id="cateCode">
									<option value="0">请选择</option>
		                            <option value="1" <c:if test="${p.cateCode==1}">selected</c:if>>金钱财运</option>
		                            <option value="2" <c:if test="${p.cateCode==2}">selected</c:if>>桃花姻缘</option>
		                            <option value="3" <c:if test="${p.cateCode==3}">selected</c:if>>避祸镇煞</option>
		                            <option value="4" <c:if test="${p.cateCode==4}">selected</c:if>>平安护身</option>
		                            <option value="5" <c:if test="${p.cateCode==5}">selected</c:if>>新年必请</option>
		                            <option value="6" <c:if test="${p.cateCode==6}">selected</c:if>>安家镇宅</option>
		                            <option value="7" <c:if test="${p.cateCode==7}">selected</c:if>>文昌官运</option>
		                            <option value="8" <c:if test="${p.cateCode==8}">selected</c:if>>送子添丁</option>
		                            <option value="9" <c:if test="${p.cateCode==9}">selected</c:if>>护身符</option>
		                         </select>
								</label>
							  </li>
							  <li>符的描述
							  <textarea id="notification-content" name="gfDesc" style="height:150px;width: 780px; resize:none">${p.gfDesc }</textarea> 
				               </li>
				              </ul>
			                  <!-- 保存按钮显示 -->
			               	  <div class="ProReward">
			               	    <div style="margin-left: 20px;">
			                		<button type="button" value="提交保存" id="btnSubmit" onclick="saveProduct()" class="big green">提 交</button>
			               	    </div>
							   </div>
			          	</div>
			           </div>
		         </div>
				</div>
	</div>
</body>
</html>