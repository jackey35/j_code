var SampleCategorys = {
	'1' : {
		'text' : '视听库',
		'parent' :null
	},
	'11' : {
		'text' : '违规视频',
		'parent' :'1'
	},
	'12' : {
		'text' : '低俗视频',
		'parent' :'1'
	},
	'13' : {
		'text' : '需编辑视频',
		'parent' :'1'
	},
	'2' : {
		'text' : '清理/删除/舆情',
		'parent' :null
	},
	'21' : {
		'text' : '指示清理',
		'parent' :'2'
	},
	'22' : {
		'text' : '指示删除',
		'parent' :'2'
	},
	'23' : {
		'text' : 'PR保护',
		'parent' :'2'
	},
	'24' : {
		'text' : '热点舆情',
		'parent' :'2'
	},
	'3' : {
		'text' : '常规敏感事件',
		'parent' :null
	},
	'31' : {
		'text' : '法轮功',
		'parent' :'3'
	},
	'32' : {
		'text' : '六四',
		'parent' :'3'
	},
	'33' : {
		'text' : '其它',
		'parent' :'3'
	},
	'4' : {
		'text' : '侵权片单',
		'parent' :null
	},
	'41' : {
		'text' : '优酷土豆',
		'parent' :'4'
	},
	'42' : {
		'text' : '乐视',
		'parent' :'4'
	},
	'43' : {
		'text' : 'PPS',
		'parent' :'4'
	},
	'44' : {
		'text' : 'PPTV',
		'parent' :'4'
	},
	'45' : {
		'text' : '央视',
		'parent' :'4'
	},
	'46' : {
		'text' : '其他',
		'parent' :'4'
	}
};
var CategoryShowField = {
	'1' : [
	       {field:'id',width:'6%',text:'ID',type:'input'},
	       {field:'title',width:'16%',text:'标题',type:'input'},
	       {field:'type',width:'8%',text:'视频类型',type:'input'},
	       {field:'secondCateId',width:'8%',text:'级别',type:'select',format:function(cateid){return SampleCategorys[cateid].text;}},
	       {field:'lastModifyTime',width:'8%',text:'修改时间',type:'time',format:function(time){return new Date(time).Format("yyyy-MM-dd HH:mm:ss");}},
	       {field:'timeLength',width:'8%',text:'时长',type:'input'},
	       {field:'description',width:'46%',text:'详情',type:'input'}
	      ],
	'2' : [
	        {field:'id',width:'6%',text:'ID',type:'input'},
		    {field:'title',width:'16%',text:'标题',type:'input'},
		    {field:'source',width:'8%',text:'视频类型',type:'input'},
			{field:'secondCateId',width:'8%',text:'级别',type:'select',format:function(cateid){return SampleCategorys[cateid].text;}},
			{field:'urgency',width:'8%',text:'紧急度',type:'input'},
			{field:'createTime',width:'8%',text:'创建时间',type:'time',format:function(time){return new Date(time).Format("yyyy-MM-dd HH:mm:ss");}},
            {field:'description',width:'46%',text:'详情',type:'input'}
	       ],
	'3' : [
	        {field:'id',width:'6%',text:'ID',type:'input'},
			{field:'title',width:'16%',text:'标题',type:'input'},
			{field:'secondCateId',width:'8%',text:'级别',type:'select',format:function(cateid){return SampleCategorys[cateid].text;}},
			{field:'type',width:'8%',text:'视频类型',type:'input'},
		    {field:'lastModifyTime',width:'8%',text:'修改时间',type:'time',format:function(time){return new Date(time).Format("yyyy-MM-dd HH:mm:ss");}},
			{field:'createTime',width:'8%',text:'创建时间',type:'time',format:function(time){return new Date(time).Format("yyyy-MM-dd HH:mm:ss");}},
			{field:'description',width:'46%',text:'详情',type:'input'}
	       ],
	'4' : [
	        {field:'id',width:'6%',text:'ID',type:'input'},
			{field:'title',width:'16%',text:'标题',type:'input'},
			{field:'aliases',width:'8%',text:'译名/别名',type:'input'},
			{field:'type',width:'8%',text:'类型',type:'input'},
			{field:'year',width:'4%',text:'年代',type:'input'},
			{field:'source',width:'4%',text:'产地',type:'input'},
			{field:'secondCateId',width:'8%',text:'版权归属',type:'select',format:function(cateid){return SampleCategorys[cateid].text;}},
			{field:'createTime',width:'8%',text:'创建时间',type:'time',format:function(time){return new Date(time).Format("yyyy-MM-dd HH:mm:ss");}},
			{field:'description',width:'38%',text:'详情',type:'input'}
	       ]
};
function toFirstCateHtml(firstCate){
	firstCate = !firstCate?'1':firstCate;
	var html = "";
	html += "<select id='first_sample_cate'>";
	for(var x in SampleCategorys){
		if(SampleCategorys[x].parent == null){
			html += "<option value='"+x+"'";
			if(x == firstCate) html += " selected ";
			html += ">"+SampleCategorys[x].text+"</option>";
		}
	}
	html += "</select>";
	return html;
}
function toSecCateHtml(secCate){
	secCate = !secCate || secCate=='0'?('11'):secCate;
	var firstCate = secCate.substring(0,1);
	var html = "";
	html += "<select id='sec_sample_cate'>";
	for(var x in SampleCategorys){
		if(SampleCategorys[x].parent == firstCate){
			html += "<option value='"+x+"'";
			if(x == secCate) html += " selected ";
			html += ">"+SampleCategorys[x].text+"</option>";
		}
	}
	html += "</select>";
	return html;
}
function getCateText(cateId){
	return SampleCategorys[cateId].text;
}
function getSampleDetail(content){
    if(content == "") return "无详细内容";
    if(typeof content == 'string') content = eval("("+content+")");
    var initHtml = '<div class="sample-detail-div" style="text-align: left;"><div class="pic fl player"></div><div class="video-detail"><div class="cfix"><div class="fl vd-moda"><ul class="sample-vids"></ul>'
        +'</div><div><p class="mB15 sample-title"></p><h4>解读或辨别要点</h4><div class="cfix"><span class="fl mR6"><img class="sample-image" width="160" height="160" /></span>'
        +'<p class="sample-desc"></p></div></div></div><div><ul class="v-bgs cfix sample-screenshot"></ul></div></div></div>';
    var element = $(initHtml).data('cache',content);
    
    var prody = function(content,vid){
    	var html = "";
        for(var x in content){
            if(x == vid){
                 html += "<li class='vid-selected'>"+x+"</li>";
            }else{
                 html += "<li>"+x+"</li>";
            }
        }
        $(this).find(".sample-vids").empty().append(html);
        if(!vid){
            vid = $(this).find(".sample-vids li:first").addClass("vid-selected").text();
        }
        var item = content[vid];
        $(this).find(".sample-title").empty().html("<span style='color:black;font-weight:bold;'>标题：</span>"+item.title);
        $(this).find(".sample-desc").empty().text(item.desc);
        if(!item.imageUrl){
        	$(this).find(".sample-image").parent().hide();
        }else{
        	$(this).find(".sample-image").attr('src',item.imageUrl).parent().show();
        }
        html = "";
        for(var i=0;i<content[vid].screenshot.length;i++){
            html += "<li><img src='"+content[vid].screenshot[i]+"' /></li>"
        }
        $(this).find(".sample-screenshot").empty().append(html);
        showPlayer($(this).find(".player"), vid, 480, 390);
    };

    element.click(function(event){if(!$(event.target).is(".sample-vids li")) return;prody.call(element,content,$(event.target).text());});
    prody.call(element,content);
    return element;
}
$().ready(function(){
	$("#first_sample_cate").live('change',function(){
		var cate = $(this).val();
		$("#sec_sample_cate").empty();
		for(var x in SampleCategorys){
			if(SampleCategorys[x].parent == cate) $("#sec_sample_cate").append("<option value='"+x+"'>"+SampleCategorys[x].text+"</option>");
		}
	});
});

function postSample(ele,sid){
    var tr = $(ele).parents("tr").prev().clone();
    var title = tr.children("td").eq(1).text();
    tr.children("td").eq(1).html('<a href="sample_info.action?id='+sid+'" target="_blank">'+title+'</a>');
    var contentHtml = jQuery("<div></div>").append(tr).html();
    var titleHtml = jQuery("<div></div>").append($(ele).parents("table").find("tr:first").clone()).html();
    var html = '<table cellpadding="0" cellspacing="0" border="0" class="tablef mB15">'+titleHtml+contentHtml+'</table>';
    var param = {
      id : '',
      title : title,
      describe : html,
      imageurl : '',
      vid : '',
      cateid : 0
    };
    ajaxNotice();
    $.post('bulletin_save.action',param,function(data){
        ajaxResult(data);
    });
}
function deleteSample(sid){
    if(confirm("确实要删除视频样本合集【"+sid+"】吗？")){
        ajaxNotice();
        $.post('sample_delete.action',{id:sid},function(data){ajaxResult(data);});
    }
}
function editSample(sid){
        location.href = 'sample_one.action?id='+sid;
}