$().ready(function(){
    $("a[name=cancel-top]").click(function(){
        var bid=getBid(this);
        ajaxNotice();
        $.post('bulletin_top.action',{action:"canceltop",bid:bid},function(data){ajaxResultWithoutAlert(data);});
    });
    $("a[name=top]").click(function(){
        var bid=getBid(this);
        ajaxNotice();
        $.post('bulletin_top.action',{action:"top",bid:bid},function(data){ajaxResultWithoutAlert(data);});
    });
    $("a[name=move-top-top]").click(function(){
        var bid=getBid(this);
        ajaxNotice();
        $.post('bulletin_top.action',{action:"top",bid:bid},function(data){ajaxResultWithoutAlert(data);});
    });
	$(".newslist li").mouseover(function(){
		$(this).find(".bulletin-edit").show();
	});
	$(".newslist li").mouseout(function(){
		$(this).find(".bulletin-edit").hide();
	});
	
	
    $("input[name=search-day]").datepicker({dateFormat: "yy-mm-dd"});
});
function getBid(element){
    return $(element).parents("li").attr("id").split("_")[1];;   
}
var PassportSC = {cookieHandle:function(){return "videomonitor@sohu-inc.com";}};
var defaultTitle = {
	'1':'操作规范',
	'2':'审核尺度'
};
/**
 * 生成大纲
 * @param element
 * @return
 */
function outline(element, container){
    if(!element) element = $("<div></div>").append(window.editor.html());
    var menus = [];
    var tag = 0;
    $(element).find("h1,h2,h3,h4").each(function(){
        var context  = $(this);
        var item = {};
        item.type = context.get(0).tagName.substring(1);
        item.text = $.trim(context.text());
        item.name = "menu_"+(tag++);
        context.before("<a name='"+item.name+"'></a>");
        menus.push(item);
    });
    if(menus.length == 0) return;
    var html = "<ul class='mktree' id='tree1'>";
    var urlObj = parseURL(location.href);
    var baseUrl = urlObj.path + urlObj.query;
    for(var i=0;i<menus.length;i++){
         html += "<li><a href='"+baseUrl+"#"+menus[i].name+"'>"+menus[i].text;
         var tmp = '';
         if(i == (menus.length-1)){
             html += "</li></ul>";
         }else if(menus[i].type < menus[i+1].type){
             html += "<ul>";
         }else if(menus[i].type > menus[i+1].type){
             html += "</ul></li>"
         }else if (menus[i].type == menus[i+1].type){
             html += "</li>"
         }
    }
    if(!container){
    	$("#outline").empty().append(html);
    }else{
    	$(container).empty().append(html);
    }
    convertTrees("tree1");
}