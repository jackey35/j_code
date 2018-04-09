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
		background-image:url(../skin/images/bg.jpg),url(../skin/images/bg02.jpg),url(../skin/images/bg01.jpg);
		background-position:center top,center bottom, center center;
		background-repeat:no-repeat,no-repeat,repeat;
		padding-bottom:1.3rem;
	}
</style>
</head>
<body>
	<input type="hidden" id='openid' value='${openId}'>
	<input type="hidden" id='winLevel' value='${winLevel}'>
	<input type="hidden" id='qrUrl' value='${qrUrl}'>
	<div class="index">
		
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
		<div class="wenben">
			<img src="../skin/images/wenben.png" alt="">
			<p>关注“开心宝财富”公众号即可参与</p>
		</div>	
		<div class="jiangli">
			<img src="../skin/images/bg02.png" alt="">
			<div class="neirong">
				<p><span>特等奖：</span><span>苹果IphoneX256G手机一部</span></p>
				<p><span>一等奖：</span><span>100元手机话费</span></p>
				<p><span>二等奖：</span><span>50元手机话费</span></p>
				<p><span>三等奖：</span><span>精品菜谱和铁锅</span></p>
				<p><span>普照奖A：</span><span>50元现金红包</span></p>
				<p class="ts">(平台注册后，账户可见，30日后可直接提现到个人银行卡)</p>
				<p><span>普照奖B：</span><span>50元开心宝平台红包代金券</span></p>
				<p class="ts">(不与平台其他活动共享，投资任意标的1万元以上可用)</p>
			</div>
			<!--
			<img src="../skin/images/bg02.png" alt="">
			<div>
				<p>最新获奖用户</p>
				<p><span>AAA获得一等奖：</span><span>100元手机话费</span></p>
				<p><span>BB获得二等奖：</span><span>50元手机话费</span></p>
				<p><span>CCC获得三等奖：</span><span>精品菜谱和铁锅</span></p>
				<p><span>EEE获得普照奖A：</span><span>50元现金红包</span></p>
				<p><span>FFF获得普照奖B：</span><span>50元开心宝平台红包代金券</span></p>
			</div>-->
		</div>	
	</div>
	<div class="result hide">
		<div class="result-egg">
			<img src="../skin/images/zhongjiang.png" alt="" id="result-img">
		</div>
		<div class="result-content">
			<!-- 未中奖 -->
			<div class="lost hide">  
				<p class="head" >
					
				</p>
				<a class="submit close">确定</a>
			</div>
			<!-- 中奖 -->
			<div class="winning hide">
				<p class="head">
					恭喜您！</br>
					砸中头等奖
				</p>
				<p class="prize">
					获得X256G一部！
				</p>
				<a id='receive' class="submit">点击领取</a>
			</div>
			<!-- 填写表单 -->
			<div class="submit-from hide">
				<p>
					<span>姓 &nbsp;&nbsp;名</span>
					<input id='name' type="" name="">
				</p>
				<p>
					<span>手机号</span>
					<input id='phone' type="" name="">
				</p>
				<p class="errorMsg">
					
				</p>
				<a id='submit' class="submit">提交</a>
				<p class="tishi">
					1、老朋友请填写开心宝平台注册手机号</br>
					2、新朋友请填写常用手机号</br>
					3、本活动兑奖信息将以用户填写为准，如有因填写出错而导致的任何问题，将由本人承担。
				</p>	
			</div>
			<!-- 提交表单成功 -->
			<div class="success hide">
				<p class="head">
					您的资料已提交</br>
					客服人员将于活动结束后五个工作日内与您联系奖品发放事宜</br>
					请保持电话畅通！
				</p>
				<a class="submit close">确定</a>
			</div>
		</div>
	</div>	
	<c:if test="${subscribe == 0 }">
	<div class="erweima ">
		<p>扫描二维码关注开心宝财富公众号，获抽奖机会</p>
		<img src="../skin/images/qrcode_ekxb.jpg" alt="">
	</div>	
	</c:if>
<script type="text/javascript" src='../js/base.js'></script>
<script type="text/javascript" src='../js/jweixin-1.2.0.js'></script>
<script>
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: 'wx2568868aed8854e6', // 必填，公众号的唯一标识
	    timestamp: '${timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${noncestr}', // 必填，生成签名的随机串
	    signature: '${sign}',// 必填，签名
	    jsApiList: [//需要调用的JS接口列表
            'checkJsApi',//判断当前客户端版本是否支持指定JS接口
            'onMenuShareTimeline',//分享给好友
            'onMenuShareAppMessage'//分享到朋友圈
        ]
	});
	wx.ready(function () {
		 var link = window.location.href;
	        var protocol = window.location.protocol;
	        var host = window.location.host;
	        //分享朋友圈
	        wx.onMenuShareTimeline({
	            title: '开心宝，迎春砸金蛋中大奖！',
	            link: link,
	            imgUrl: protocol+'//'+host+'/skin/images/favicon.ico',// 自定义图标
	            success: function (res) {//分享成功
	                $.post('http://www.jinxinsenhui.com/smash/share.do?openId='+openid,function(data){
				        if(data.httpStatus==1){
				            number++;
				            $('.result').addClass('hide');
				            $('.jindan').attr('src','../skin/images/jindan.png');
				            $('.chuizi').removeClass('zadan').hide();
				            $('.result-content > div').addClass('hide');
				        }
				    });
	                //some thing you should do
	            },
	            cancel: function (res) { //取消分享
	                //alert('shared cancle');
	            }
	        });
	        //分享给好友
	        wx.onMenuShareAppMessage({
	            title: '开心宝，迎春砸金蛋中大奖！', // 分享标题
	            desc: '开心宝，迎春砸金蛋中大奖！', // 分享描述
	            link: link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	            imgUrl: protocol+'//'+host+'/skin/images/favicon.ico',// 自定义图标
	            type: 'link', // 分享类型,music、video或link，不填默认为link
	            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	            success: function () {//分享成功
	                $.post('http://www.jinxinsenhui.com/smash/share.do?openId='+openid,function(data){
				        if(data.httpStatus==1){
				            number++;
				            $('.result').addClass('hide');
				            $('.jindan').attr('src','../skin/images/jindan.png');
				            $('.chuizi').removeClass('zadan').hide();
				            $('.result-content > div').addClass('hide');
				        }
				    });
	            },
	            cancel: function () {//取消分享
	                
	            }
	        });
	        wx.error(function (res) {
	        });
    });
</script>	
</body>
</html>






