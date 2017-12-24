/**
 * 日期格式化方法，格式化字符串与java兼容
 */
Date.prototype.Format = function(formatStr)
{
    var str = formatStr;
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));
    str=str.replace(/MM/,this.getMonth()>8?""+(this.getMonth()+1):'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth());
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());
    return str;   
}
/**
 * 判断一个变量是否是未定义
 */
function isUndefined(v){
	return typeof v == 'undefined';
}
/**
 * 解析一个url
 * @param url
 * @return 示例：http://10.7.40.101:8080/spaces-videos-monitor-web/recommend_list.action?_mid=56#sasd
 * file: 文件名，"recommend_list.action"
 * hash: 锚点，"sasd"
 * host: 主机，"10.7.40.101"
 * params: 参数对
 * path: 路径，"/spaces-videos-monitor-web/recommend_list.action"
 * port: 端口，"8080"
 * protocol: 协议"http"
 * query: 查询字符串，"?_mid=56"
 * relative: 相对地址，"/spaces-videos-monitor-web/recommend_list.action?_mid=56#sasd"
 * segments: 相对路径分段，Array[2]
 * source: 原网址，"http://10.7.40.101:8080/spaces-videos-monitor-web/recommend_list.action?_mid=56#sasd"
 */
function parseURL(url) {
    var a =  document.createElement('a');
    a.href = url;
    return {
        source: url,
        protocol: a.protocol.replace(':',''),
        host: a.hostname,
        port: a.port,
        query: a.search,
        params: (function(){
            var ret = {},
                seg = a.search.replace(/^\?/,'').split('&'),
                len = seg.length, i = 0, s;
            for (var i=0;i<seg.length;i++){
                if (!seg[i]) { continue; }
                s = seg[i].split('=');
                if(typeof ret[s[0]] == 'undefined'){
                	ret[s[0]] = s[1];
                }else if(typeof ret[s[0]] == 'object'){
                	ret[s[0]].push(s[1]);
                }else{
                	var tmp = ret[s[0]];
                	ret[s[0]] = [];
                	ret[s[0]].push(tmp);
                	ret[s[0]].push(s[1]);
                }
            }
            return ret;
        })(),
        file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
        hash: a.hash.replace('#',''),
        path: a.pathname.replace(/^([^\/])/,'/$1'),
        relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
        segments: a.pathname.replace(/^\//,'').split('/')
    };
}
/**
 * 模拟数据结构Set
 * @param arr，初始化array
 * @return
 */
function Set(arr){
    this._data = !arr?[]:arr;
}
Set.prototype = {
        add : function(obj){
            for(var x=0;x<this._data.length;x++){
                if(this._data[x] == obj) break;
            }
            if(x >= this._data.length) this._data.push(obj);
        },
        size : function(){
            return this._data.length;
        },
        toArray : function(){
            return this._data;
        },
        contains : function(obj){
            for(var x=0;x<this._data.length;x++){
                if(this._data[x] == obj) return true;
            }
            return false;
        }
}
/**
 * 渲染一个表格
 * @param option 属性配置
 * @param data 表格数据
 * @return html,表格内容
 */
function getTableHtml(option, data){
	if(!data) return "<h1>没有数据可以显示！</h1>";
	var defaultOption = {
			attrs:{
				cellpadding : "0",
				cellspacing : "0",
				border : "0",
				'class' : "tablef mB15"
			}
	};
	jQuery.extend(true, defaultOption, option);
	option = defaultOption;
	var html = [];
	html.push("<table");
	for(var x in option.attrs){
		html.push(" "+x+"='"+option.attrs[x]+"' ");
	}
	html.push(" >");
	
	option = option.fields;
	html.push("<tr>");
	for(var i=0;i<option.length;i++){
		html.push("<th ");
		if(!!option[i].width){
			html.push("width='");
			html.push(option[i].width);
			html.push("'");
		}
		html.push(">");
		html.push(option[i].text);
		html.push("</th>");
	}
	html.push("</tr>");
	
	for(var i=0;i<data.length;i++){
		var trHtml = "<tr>";
		for(var j=0;j<option.length;j++){
			var tmp = "<td>";
			var initData;
			if(typeof data[i][option[j].field] == "undefined"){
				initData = data[i][j];
			}else{
				initData = data[i][option[j].field];
			}
			if(typeof option[j].format == "function"){
				tmp += option[j]['format'].call(data[i],initData);
			}else{
				tmp += !initData?"":initData;
			}
			tmp += "</td>";
			trHtml += tmp;
		}
		trHtml += "</tr>";
		html.push(trHtml=="<tr></tr>"?"":trHtml);
	}
	html.push("</table>");
	return html.join("");
}
/**
 * 渲染一个表格
 * @param option 属性配置
 * @param data 表格数据
 * @return JQuery对象,内含一个表格，每一行的cache储存数据
 */
function getTable(option, data){
	if(!data) return ;
	var defaultOption = {
		attrs:{
			cellpadding : "0",
			cellspacing : "0",
			border : "0",
			'class' : "tablef mB15"
		}
	};
	jQuery.extend(true, defaultOption, option);
	option = defaultOption;
	var table = $("<table></table>");
	table.attr(option.attrs);
	
	option = option.fields;
	var html = [];
	html.push("<tr>");
	for(var i=0;i<option.length;i++){
		html.push("<th ");
		if(!!option[i].width){
			html.push("width='");
			html.push(option[i].width);
			html.push("'");
		}
		html.push(">");
		html.push(option[i].text);
		html.push("</th>");
	}
	html.push("</tr>");
	table.append(html.join(""));
	
	for(var i=0;i<data.length;i++){
		var trHtml = "<tr id='tr"+j+"'>";
		for(var j=0;j<option.length;j++){
			var tmp = "<td>";
			var initData;
			if(typeof data[i][option[j].field] == "undefined"){
				initData = data[i][j];
			}else{
				initData = data[i][option[j].field];
			}
			if(typeof option[j].format == "function"){
				tmp += option[j]['format'].call(data[i],initData);
			}else{
				tmp += !initData?"":initData;
			}
			tmp += "</td>";
			trHtml += tmp;
		}
		trHtml += "</tr>";
		if(trHtml!=="<tr></tr>"){
			table.append(trHtml);
			table.find("tr:last").data("cache",data[i]);
		}
	}
	return table;
}
//关键字染色
$().ready(function(){
	var BANWORD_TYPE_GLOBLE = {"0":"政治类","1":"政治类","3":"色情类","5":"违禁品","6":"高考","7":"版权","8":"广告","11":"视听"};
	$(".banword-style").each(function(){
		var ele = $(this);
		var title = ele.attr("title");
		ele.removeAttr("title");
		title = eval("("+title+")");
		var str = new Array();
		for(var i=0;i<title.length;i++){
			str.push(title[i].name+" "+BANWORD_TYPE_GLOBLE[title[i].type]+" "+title[i].description);
		}
		ele.data("cache", str.join("<br />"));
		var color = title[0].type == 7?"green":"red";
		color = title.length>1?"#800080":color;
		ele.css({"font-size":"1.5em","font-weight":"bold","color":color});
	});
	$("body").append("<div style='border:solid gray 1px;;background-color:white;position:absolute;display:none;padding:5px;' id='banword-info-show'></div>");
	$(".banword-style").mouseover(function(event){
		$("#banword-info-show").html($(event.target).data("cache")).css("top",$(event.target).offset().top+25).css("left",$(event.target).offset().left+20).show();
	});
	$(".banword-style").mouseout(function(){
		$("#banword-info-show").hide();
	});
	
	//用户详情弹层
	$("a[name=usernick]").live('mouseover', function(event){
		var ele = $(event.target);
		var userId = ele.attr("id");
		var pos = ele.position();
		var showDiv = $("#userinfo");
		if(showDiv.length == 0){
			$("body").append("<div id='userinfo'><dl><dt style='float:left;width:110px;'></dt><dd style='float:left;width:210px;padding-top:10px;'><ul></ul></dd></dl></div>");
			showDiv = $("#userinfo");
			showDiv.css({
				'position'	: 'absolute',
				'display'	: 'none',
				'z-index'	: '6',
				'width'		: '320px',
				'border'	: 'solid gray 1px',
				'background-color'	: 'white',
				'padding'	: '3px'
			});
		}
		showDiv.css("top",pos.top+17).css("left",pos.left+40).show();
		if(ele.data("infocache") == null){
			$.post('account_ajaxInfo.action',{uid:userId},function(data){
				userinfoShow(data);
				ele.data("infocache",data);//js层面缓存
			});
		}else{
			var data = ele.data("infocache");
			userinfoShow(data);
		}
	});
	$("a[name=usernick]").live('mouseout', function(event){
		var oo = setTimeout(function(){
				$("#userinfo").hide();
				$("#userinfo dt").empty();
				$("#userinfo dd ul").empty();
			},500);
		$("#userinfo").one("mouseenter", function(){
			clearTimeout(oo);
		});
		$("a[name=usernick]").one('mouseover',function(){
			clearTimeout(oo);
		});
	});
	$("#userinfo").live('mouseleave', function(event){
			$("#userinfo").hide();
			$("#userinfo dt").empty();
			$("#userinfo dd ul").empty();
	});
	//查看是否有紧急通知
	checkUrgentBulletin();
	window._checkUrgenBulletin = setInterval(checkUrgentBulletin,8000);
});
//查看是否有紧急通知
function checkUrgentBulletin(){
	$.get('immediately.action',function(data){
		if($.isEmptyObject(data)) return;
		var content = data.result;
		var urgentBulletin = $('<div id="immediately-notice" style="position: fixed;right:0px;bottom:-110px;width:400px;height:80px;border:1px solid red;'
				+'background-color:#E1EAF3;font-size:13px;padding:10px;color:#3A3A3A;z-index:999;">'
				+'<a style="font-weight:bold;font-size:22px;margin:-10px -10px 0 0;" title="关闭" href="javascript:void(0)" onclick="$(\'#immediately-notice\').animate({bottom:-500},500);" class="fr">'
				+'×</a><h1>紧急通知</h1>'+content+'</div>');
		$('body').append(urgentBulletin);
		urgentBulletin.animate({bottom:0},500);
		clearInterval(window._checkUrgenBulletin);
	})
}
//ajax进行提示
function ajaxNotice(){
	$.blockUI({
        message: '<div style="margin:30px;font-size:14px;">正在提交操作，请稍等...</div>',
        css:{cursor:"normal"}
    });
}
//通用ajax结果提示
function ajaxResult(data, isClose){
	if(data.result == "yes"){
		alert("操作成功！");
		if(!!isClose){
			window.close();
		}else{
			location.href = location.href;
		}
	}else{
		alert("提交失败:"+data.result);
		$.unblockUI();
	}
}
function ajaxResultWithoutAlert(data){
    if(data.result == "yes"){
        location.href = location.href;
    }else{
        alert("提交失败:"+data.result);
        $.unblockUI();
    }
}
//分页程序
function pageSpliter(element, allCount, pageSize, curruntPage, urlPrefix){
	allCount = parseInt(allCount);
	pageSize = parseInt(pageSize);
	curruntPage = parseInt(curruntPage);
	if(urlPrefix.indexOf("http://")==-1 && urlPrefix.length < 10){
		var url = location.href.split("?")[0]+"?";
		var urlObj = parseURL(location.href);
		for(var x in urlObj.params){
			if(urlPrefix != x){
				url += x+"="+urlObj.params[x]+"&";
			}
		}
		urlPrefix = url + urlPrefix + "=";
	}
	var target = $(element);
	target.addClass("cfix");
	target.css("margin-bottom","5px");
	var allPage = Math.ceil(allCount/pageSize);
	var prePage = curruntPage-1<1?1:curruntPage-1;
	var nextPage = curruntPage+1>allPage?allPage:curruntPage+1;
	var html = '';
	html += '<p class="note fl">共找到'+allCount+'条记录,共'+allPage+'页，当前第'+curruntPage+'页</p>';
	html += '<div class="pagetabs fr ovh"><a href="'+urlPrefix+'1" class="pres"></a>';
	html += '<a href="'+urlPrefix+prePage+'" class="pre"></a>';
	if(curruntPage>3) html += "&nbsp;&nbsp;...&nbsp;&nbsp;";
	for(var i=curruntPage-2>0?curruntPage-2:1;i<curruntPage;i++){
		html += '<a href="'+urlPrefix+i+'">'+i+'</a>';
	}
	html += '<span class="on">'+curruntPage+'</span>';
	var endpage = allPage>(curruntPage+2)?curruntPage+2:allPage;
	for(var i=curruntPage+1;i<=endpage;i++){
		html += '<a href="'+urlPrefix+i+'">'+i+'</a>';
	}
	if(allPage-2>curruntPage) html += '&nbsp;&nbsp;...&nbsp;&nbsp;';
	html += '<a href="'+urlPrefix+nextPage+'" class="next"></a><a href="'+urlPrefix+allPage+'" class="nexts"></a>';
	html += '<input type="text" value="" class="txtg"/>';
	html += '<a href="javascript:void(0);" onclick="location.href=\''+urlPrefix+'\'+$(this).prev(\'input\').val();">跳</a>';
	target.empty();
	target.append(html);
}
//用户信息弹层显示
function userinfoShow(data){
	$("#userinfo dt").empty();
	$("#userinfo dd ul").empty();
	$("#userinfo dt").append("<img style='width:100px;height:100px;' src='"+(data.bigPhoto==null?data.smallPhoto:data.bigPhoto)+"' />");
	$("#userinfo dd ul").append("<li>用 户 ID："+data.id+"</li>");
	$("#userinfo dd ul").append("<li>昵称："+data.nickName+"</li>");
	$("#userinfo dd ul").append("<li>登陆邮箱："+data.passport+"</li>");
	$("#userinfo dd ul").append("<li>注册时间："+data.createTime.substring(0,19)+"</li>");
	$("#userinfo dd ul").append("<li>最后登陆："+data.lastLogin.substring(0,19)+"</li>");
}
//预览图放大
$(".video-preview").live('mouseover',function(event){
	var maxHeight = 300;//预览图最高
	var maxWidth = 320;//预览图最宽
	var smallHeight = 90;//小图高度
	var smallWidth = 120;//小图宽度
	var pos = $(event.target).offset();
	var url = $(event.target).attr("src");
	var ele = $("#bigpreview");
	if(ele.length == 0){
		$("body").append("<div id='bigpreview'></div>");
		ele = $("#bigpreview");
		ele.css({
			'position'	: 'absolute',
			'display'	: 'none',
			'z-index'	: '6',
			'background-color'	: 'white'
		});
	}
	ele.empty();
	var src = url.substring(0,url.length-4)+"b.jpg";
	if(src.indexOf(".17173.")==-1 && src.indexOf(".img.pp.sohu.")==-1){
		src = url;
		maxHeight = 1000;
		maxWidth = 1000;
	}
	var timer = Date.parse(new Date());
	ele.data('cache',timer);
	var img = new Image();
	$(img).load(function() {
		if($(this).attr("timer") != ele.data('cache')){//如果不是最新的图片放大请求，则什么都不做
			return;
		}
		ele.empty();
        ele.append(this);
        var imgHeight = img.height/img.width > maxHeight/maxWidth?maxHeight:(maxWidth*img.height/img.width);
        var imgWidth = img.height/img.width > maxHeight/maxWidth?(maxHeight*img.width/img.height):maxWidth;
        if($(document).height() - pos.top < imgHeight+smallHeight){
    		ele.css("top",pos.top-imgHeight);
    	}else{
    		ele.css("top",pos.top+smallHeight);
    	}
    	if($(document).width() - pos.left < imgWidth+smallWidth){
    		ele.css("left",pos.left-imgWidth);
    	}else{
    		ele.css("left",pos.left+smallWidth);
    	}
    	ele.show();
    })
    .error(function() {
        ele.html("<h2>图片加载失败！</h2>");
        ele.css("left",pos.left+smallWidth).css("top",pos.top+smallHeight);
        ele.show();
    })
    .attr("src", src).attr("timer",timer)
    .css({'max-width':maxWidth+'px','max-height':maxHeight+'px'});
}).live('mouseout', function(){
	var ele = $("#bigpreview");
	ele.removeData('cache')
	ele.hide();
});

//显示播放器
function showPlayer(element,vid,width,height,isAutoPlay){
	//var w = AUDIT_LEVEL == 1?320:640;
	//var h = AUDIT_LEVEL == 1?266:515;
	var w = !width?320:width;
	var h = !height?266:height;
	isAutoPlay = !isAutoPlay;
	
	if ($(element).length == 0) return;
	var html = showVrsPlayer({bid: vid,width: w,height: h,autoplay: isAutoPlay+'',getHTML:1, api_key:'jk1235lsdfldiffsaa', variables : [['isListPlay',0]]});
	$(element).html(html);
}
var passageMap = new Array();
passageMap[null] = "无通道";
passageMap["1"] = "短视频";
passageMap["2"] = "中视频";
passageMap["3"] = "长视频";
passageMap["4"] = "移动视频";
passageMap["6"] = "抓取视频";
passageMap["8"] = "高级通道";
passageMap["9"] = "重审";
passageMap["10"] = "高危";
passageMap["11"] = "广告";
passageMap["12"] = "保十杰";
passageMap["13"] = "MD5白名单";
passageMap["102"] = "17173";
passageMap["103"] = "MV抓取";
passageMap["104"] = "人工关键字";
var deleteReasonMap = new Array();
deleteReasonMap["11"] = "含有垃圾广告内容";
deleteReasonMap["12"] = "含有版权风险内容";
deleteReasonMap["13"] = "含有淫秽色情内容";
deleteReasonMap["14"] = "含有网络低俗内容";
deleteReasonMap["15"] = "含有非法管制内容";
deleteReasonMap["16"] = "标题不规范内容";
deleteReasonMap["17"] = "根据视听管理通知规定";
deleteReasonMap["18"] = "虚假剧集、站外指引内容";
deleteReasonMap["19"] = "转码失败、黑屏或者花屏";
deleteReasonMap["100"] = "其他";