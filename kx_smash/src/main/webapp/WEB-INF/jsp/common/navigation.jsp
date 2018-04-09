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
			<li id="_mid_" ><a href="/admin/prize/list.do" >奖品管理</a></li>
			<li id="_mid_" ><a href="/admin/winning/list.do" >中奖纪录</a></li>
			<li id="_mid_" ><a href="/admin/prize/qr.do" >上传二维码</a></li>
			<li id="_mid_" ><a href="/admin/config/edit.do" >活动设置</a></li>
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