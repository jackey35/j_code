<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <style type="text/css">
        .hover
        {
            background-color: Red;
        }
    </style>

<!-- Start:left -->
	<div id="side" class="l">
		<div class="l-open">
		<div class="bti"><a rel="child" href="javascript:void(0);">业务管理</a></div>
			<ul>
			<li id="_mid_" ><a href="/admin/user/list.do" >会员管理</a></li>
			<li id="_mid_"><a href="/admin/order/list.do">支付流水</a></li>
			<li id="_mid_"><a href="/admin/p/list.do">符列表</a></li>
			</ul>
		</div>
		<div class="l-open">
		<div class="bti"><a rel="child" href="javascript:void(0);">app配置管理</a></div>
		  <ul>
		      <li id="_mid_"><a href="/admin/boot/list.do">启动页管理</a></li>
              <li id="_mid_"><a href="/admin/mb/list.do">蒙版管理</a></li>
              <li id="_mid_"><a href="/admin/share/list.do">分享文案管理</a></li>
              <li id="_mid_"><a href="/admin/upc/list.do">渠道包管理</a></li>
		  </ul>
		</div>
				<div class="l-open">
		<div class="bti"><a rel="child" href="javascript:void(0);">安全中心</a></div>
		  <ul>
              <li id="_mid_"><a href="/admin/predit.do">密码修改</a></li>
		  </ul>
		</div>
	</div>
<script>
var CURRENT_URL = window.location.href.split('?')[0];  
CURRENT_URL_ARR=CURRENT_URL.split("/");   
TEM_URL="/";
for (i=0;i<CURRENT_URL_ARR.length ;i++ ){ 
	if(i==0 || i==1 || i ==2){
		continue;
	}
    TEM_URL+= CURRENT_URL_ARR[i];  
    if(i!=CURRENT_URL_ARR.length-1){
   	 	TEM_URL =TEM_URL	+"/"
    }
    TEM_URL = TEM_URL.replace(/,/g,"/");  
   
   
}  
$("#_mid_ a").each(function(index, element) {
   	 hrefValue = $(this).attr('href') ;
     content = $(this).html() ;	
           
     if(TEM_URL == hrefValue){
    		$(this).html("<font color='red'>"+content+"</font>");
     }

});
</script>