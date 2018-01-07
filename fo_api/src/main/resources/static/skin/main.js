$(document).ready(function(){
	menu("#side");
	fold("#fold");
	mover(".mover");
	selBox("#selBox");
	serBox("#serBox");
	bgover(".bgover");
});



function menu(obj){
	var bti = $(obj).find(".bti");
	var objo = $(obj).find("ul .l-open");
	bti.toggle(function(){
		$(this).parent().attr("class","").attr("class","l-open");
	},function(){
		$(this).parent().attr("class","").attr("class","l-close");
	});
	objo.toggle(function(){
		$(this).attr("class","").attr("class","l-close");
	},function(){
		$(this).attr("class","").attr("class","l-open");
	});
}



function fold(obj){
	var ico = $(obj).find("td .ico-plus");
	var icoth = $(obj).find("th .ico-plus");
	ico.toggle(function(){
		$(this).attr("class","").attr("class","ico-minus");
		$(this).parent().parent().next(".tr1").show();
	},function(){
		$(this).attr("class","").attr("class","ico-plus");
		$(this).parent().parent().next(".tr1").hide();
	});
	icoth.toggle(function(){
		$(this).attr("class","").attr("class","ico-minus");
		$(this).parent().parent().nextAll(".tr1").show();
		$(ico).attr("class","").attr("class","ico-minus");
	},function(){
		$(this).attr("class","").attr("class","ico-plus");
		$(this).parent().parent().nextAll(".tr1").hide();
		$(ico).attr("class","").attr("class","ico-plus");
	});
}



function mover(obj){
	var objs = $(obj).find(".rel");
	$(objs).mouseover(function(){
		$(this).addClass("detail");
	});
	$(objs).mouseout(function(){
		$(this).removeClass("detail");
	});
};


function bgover(obj){
	var objs = $(obj).find("tr");
	$(objs).mouseover(function(){
		$(this).addClass("tbg");
	});
	$(objs).mouseout(function(){
		$(this).removeClass("tbg");
	});
};



function selBox(obj){
	var objs = $(obj).find(".em");
	$(objs).toggle(function(){
		$(obj).find(".detail").show();
		$(objs).find(".s2").show();
		$(objs).find(".s1").hide();
		$(this).find(".ico-plus").attr("class","").attr("class","ico-minus");
	},function(){
		$(obj).find(".detail").hide();
		$(objs).find(".s1").show();
		$(objs).find(".s2").hide();
		$(this).find(".ico-minus").attr("class","").attr("class","ico-plus");
	});
};

function serBox(obj){
	var obja = $("#serBox ul li a");
	var objb = $("#serBox .d .txt");
	objb.click(function(){
		$("#serBox ul").toggle();	
		$(this).addClass("active");
	});
//	obja.click(function(){
//		 var n = obja.index($(this));
//		 $("#serBox .d .txt a").replaceWith(obja.eq(n).clone());
//		 $("#serBox ul").hide();
//		 $(objb).removeClass("active");
//	});
	obja.click(function(){
		 var strText1 = $("#serBox .u li a").text();
		 var strText2 = $("#serBox .d .txt a").text();
		 $("#serBox .d .txt a").text(strText1);
		 $("#serBox .u li a").text(strText2);
		 $("#serBox ul").hide();
		 $(objb).removeClass("active");
	});
}
