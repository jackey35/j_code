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
<title> 砸金蛋 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">


<link rel="stylesheet" type="text/css" href="../skin/style.css">
<script type="text/javascript" src='../js/zepto.js'></script>
<style type="text/css" media="screen">
	body{
		background:url(../skin/images/bg.jpg) no-repeat center top #000;
		background-size: 100%; 
		height: 23.6rem;
	}
</style>
</head>
<body>
	<c:if test="${subscribe==1 }">
	<div class="dan">
		<div>
			<div class="za ms-2 t_c">
				<img class="jindan" src="../skin/images/jindan.png" alt="">
				<img class="chuizi" src="../skin/images/chuizi.png" alt="">
			</div>
			<div class="za ms-2 t_c">
				<img class="jindan" src="../skin/images/jindan.png" alt="">
				<img class="chuizi" src="../skin/images/chuizi.png" alt="">
			</div>
		</div>
		<div>
			<div class="za ms-3 t_c">
				<img class="jindan" src="../skin/images/jindan.png" alt="">
				<img class="chuizi" src="../skin/images/chuizi.png" alt="">
			</div>
			<div class="za ms-3 t_c">
				<img class="jindan" src="../skin/images/jindan.png" alt="">
				<img class="chuizi" src="../skin/images/chuizi.png" alt="">
			</div>
			<div class="za ms-3 t_c">
				<img class="jindan" src="../skin/images/jindan.png" alt="">
				<img class="chuizi" src="../skin/images/chuizi.png" alt="">
			</div>
		</div>
	</div>
	</c:if>
	
	<c:if test="${subscribe == 0 }">
		扫描二维码关注，获抽奖机会
		<img src="http://www.jinxinsenhui.com/qrcode_ekxb.jpg" alt="扫描二维码关注，获抽奖机会" />
	</c:if>
	<div class="wenben">
		<img src="../skin/images/wenben.png" alt="">
		<p>所有关注开心宝公众号的粉丝均可参与</p>
	</div>	
	<div class="jiangli">
		<img src="../skin/images/bg02.png" alt="">
		<div class="neirong">
			<p><span>头等奖：</span><span>苹果X256G手机两部</span></p>
			<p><span>一等奖：</span><span>一百元手机话费</span></p>
			<p><span>一等奖：</span><span>50元手机话费</span></p>
			<p><span>一等奖：</span><span>锅/书</span></p>
			<p><span>普照奖：</span><span>开心宝平台红包代金券100元，50元首投和50元老用户投资红包</span></p>
		</div>
	</div>	

<script type="text/javascript" src='../js/base.js'></script>
<script>
	$('.za').click(function(event) {
		var item = this;
		$(item).css('z-index','10').find('.chuizi').addClass('zadan').show();
		setTimeout(function(){
			$(item).find('.jindan').attr('src','../skin/images/suidan.png');
			$.post('http://www.jinxinsenhui.com/smash/zha.do?openId=${openId}'); 
		},150);
	});

</script>
</body>
</html>
