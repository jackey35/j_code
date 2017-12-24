/**
 * 自动运行的一些程序
 */
$(function(){
	if(typeof $.fn.dataTable != 'undefined') {
		// dataTables插件汉化
		$.extend($.fn.dataTable.defaults, {
			language: {
				"sProcessing": "处理中...",
				"sLengthMenu": "显示 _MENU_ 项结果",
				"sZeroRecords": "没有匹配结果",
				"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix": "",
				"sSearch": "搜索:",
				"sUrl": "",
				"sEmptyTable": "表中数据为空",
				"sLoadingRecords": "载入中...",
				"sInfoThousands": ",",
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "上页",
					"sNext": "下页",
					"sLast": "末页"
				},
				"oAria": {
					"sSortAscending": ": 以升序排列此列",
					"sSortDescending": ": 以降序排列此列"
				}
			}
		});
	}
	//所有select节点，若有 data-value节点，则自动将初始值设置为改属性的值
    $('select').each(function(){
        var value = $(this).data('value');
        if(value != null && value !== ''){
            $(this).val(value+'');
        }
    });
	//所有复选框单选框节点，若有 data-value属性，且值与当前节点值一致，或按照逗号分隔后包含当前节点值，则初始选中
    $('input[type=radio],input[type=checkbox]').each(function(){
    	var value = $(this).data('value');
    	if(value != null && value !== ''){
    		if($(this).val()==value || (","+value+",").indexOf($(this).val()) != -1){
    			$(this).attr('checked', 'checked');
    		}
    	}
    });
    //日期选择控件
    try{
    	$('.datepicker').datepicker({dateFormat: "yy-mm-dd"});
    }catch(e){}
    //可排序表格
    try{
        $('.sortable-column').each(function(){
            $(this).css('cursor', 'pointer');
        	var urlInfo = parseURL(location.href);
            var columnName = $(this).data('order-column');
            var text = $(this).text();
            if(columnName == urlInfo.params['orderColumn'] || ($(this).data('order-default')!=null && !urlInfo.params['orderColumn'])){
                if(urlInfo.params['order'] == 'asc'){
                    $(this).append(' <i style="font-size:14px;" class="icon-sort-up"></i>');
                }else{
                    $(this).append(' <i style="font-size:14px;" class="icon-sort-down"></i>');
                }
            }else{
                $(this).append(' <i style="font-size:14px;" class="icon-sort"></i>');
            }
            if(columnName == urlInfo.params['orderColumn'] && 'desc'==urlInfo.params['order'] || ($(this).data('order-default')!=null && !urlInfo.params['orderColumn'])){
                urlInfo.params['order'] = 'asc';
            }else{
                urlInfo.params['order'] = 'desc';
            }
        	urlInfo.params['orderColumn'] = columnName;
            var url = urlInfo.path+'?'+$.param(urlInfo.params);
            $(this).click(function(){
                location.href = url;
            });
        });
    }catch(e){}
});
/**
 * 左侧菜单使用的函数
 * @param obj
 * @return
 */
function menu(obj){
	$(obj).find(".bti,.l-close>a,.l-open>a").click(function(){
		$(this).parent().toggleClass("l-open").toggleClass("l-close");
	});
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
                seg = a.search.replace(/^\?/,'').split('&');
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
 * 分页程序
 * @param element 分页容器
 * @param allCount 记录总条数
 * @param pageSize 每页条数
 * @param curruntPage 当前页数
 * @param urlPrefix url前缀，不包含页数参数的完整url地址，或者页数参数名。若是后一种情况，函数会自动获取当前页的url地址，并按照提供的参数名修改页数来附加url地址
 */
function pageSplit(element, allCount, pageSize, curruntPage, urlPrefix){
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
	if(target.length == 0) return;//没有传递分页容器
	target.css("clear","both");
	var allPage = Math.ceil(allCount/pageSize);
	var prePage = curruntPage-1<1?1:curruntPage-1;
	var nextPage = curruntPage+1>allPage?allPage:curruntPage+1;
	var html = '';
	html += '<p style="float:left;margin:0;">共找到'+allCount+'条记录,共'+allPage+'页，当前第'+curruntPage+'页</p>';
	html += '<div class="pagetabs" style="float:right;"><a href="'+urlPrefix+'1"><i class="icon-angle-double-left"></i></a>';
	html += '<a href="'+urlPrefix+prePage+'"><i class="icon-angle-left"></i></a>';
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
	html += '<a href="'+urlPrefix+nextPage+'"><i class="icon-angle-right"></i></a><a href="'+urlPrefix+allPage+'"><i class="icon-angle-double-right"></i></a>';
	//html += '<input type="text" value=""/>';
	//html += '<a href="javascript:void(0);" onclick="location.href=\''+urlPrefix+'\'+$(this).prev(\'input\').val();">跳</a>';
	target.empty();
	target.append(html);
}
//ajax进行提示
function ajaxNotice(){
	$.blockUI({
        message: '<div style="margin:30px;font-size:14px;">正在提交操作，请稍等...</div>',
        css:{cursor:"normal"},
        fadeIn: 0, fadeOut: 0
    });
}
//通用ajax结果提示
function ajaxResult(data, isClose){
	if(!!data['status']){
		if(data.status == 200){
			alert("操作成功！");
			if(typeof isClose == 'boolean' && !!isClose){
				window.close();
			}else{
				location.href = location.href;
			}
		}else{
			alert("提交失败:"+data.message);
			$.unblockUI();
		}
		return;
	}
	if(data.result == 0){
		alert("操作成功！");
		if(typeof isClose == 'boolean' && !!isClose){
			window.close();
		}else{
			location.href = location.href;
		}
	}else{
		alert("提交失败:"+data.message);
		$.unblockUI();
	}
}
/**
 * 通用ajax请求
 * @param url,要请求的后台地址
 * @param params 附加的参数对
 */
function ajax(url, params){
	$.blockUI({
        message: '<div style="margin:30px;font-size:14px;">正在提交操作，请稍等...</div>',
        css:{cursor:"normal"},
        fadeIn: 0, fadeOut: 0
    });
	if(!params){
		$.get(url, ajaxResult);
	}else{
		$.get(url, params, function(data){
			if(data.result == 0){
				alert("操作成功！");
				location.href = location.href;
			}else{
				alert("提交失败:"+data.message);
				$.unblockUI();
			}
		});
	}
}
/**
 * 渲染一个表格
 * @param option 属性配置,可以为空
 * @param data 表格数据
 * @param fields 表头设置
 * [
 * 	{
 * 		text:'表头显示字段',
 * 		field:'显示的字段名字',
 * 		format:function,格式化函数
 * 		isTotle:是否合计
 * 	}
 * ]
 * @return html,表格内容
 */
function getTableHtml(data, fields, option){
	if(!data) return "<h1>没有数据可以显示！</h1>";
	var defaultOption = {
				cellpadding : "0",
				cellspacing : "0",
				border : "0",
				'class' : "table"
	};
	jQuery.extend(true, defaultOption, option);
	option = defaultOption;
	var html = [];
	html.push("<table");
	for(var x in option){
		html.push(" "+x+"='"+option[x]+"' ");
	}
	html.push(" >");
	
	html.push("<tr>");
	for(var i=0;i<fields.length;i++){
		html.push("<th ");
		if(!!fields[i].width){
			html.push("width='");
			html.push(fields[i].width);
			html.push("'");
		}
		html.push(">");
		html.push(fields[i].text);
		html.push("</th>");
	}
	html.push("</tr>");
	
	var total = {};
	for(var i=0;i<data.length;i++){
		var trHtml = "<tr>";
		for(var j=0;j<fields.length;j++){
			var tmp = "<td>";
			var initData;
			if(typeof data[i][fields[j].field] == "undefined"){
				initData = data[i][j];
			}else{
				initData = data[i][fields[j].field];
			}
			if(typeof fields[j].format == "function"){
				tmp += fields[j]['format'].call(data[i],initData);
			}else{
				tmp += typeof initData == 'undefined'?"":initData;
			}
			if(fields[j].isTotal){
				if(!total[fields[j].field]) total[fields[j].field] = 0;
				total[fields[j].field] += initData;
			}
			tmp += "</td>";
			trHtml += tmp;
		}
		trHtml += "</tr>";
		html.push(trHtml=="<tr></tr>"?"":trHtml);
	}
	if(!$.isEmptyObject(total)){
		html.push("<tr><th>合计</th>");
		for(var i=1;i<fields.length;i++){
			if(!fields[i].isTotal){
				html.push("<th>--</th>");
			}else{
				html.push("<th>");
				html.push(total[fields[i].field]);
				html.push("</th>");
			}
		}
		html.push("</tr>");
	}
	html.push("</table>");
	return html.join("");
}