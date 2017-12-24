var VIDEO_TYPE = 1;//视频清晰度
var PERCENT_WIDTH = 1;
var PERCENT_HEIGHT = 1;
var X_DEC = 0;
var Y_DEC = 0;
//加载播放器
var width=480,height=391,nobheight="360";
//---end----
var start_rencode = function(){};
$().ready(function(){
    $('#sohuplayer').html($(showVrsPlayer({
        bid:vid,
        showCtrlBar: 1,
        width: "480",
        height: "391",
        autoplay: true,
        api_key:"jk1235lsdfldiffsaa",
        getHTML: 1,
        variables:[['shareBtn',0],['likeBtn',0],['api',1],['topBarNor',0],['isListPlay',0]]
    })));
    document.getElementById('shuiyinplayer').innerHTML=showVrsPlayer({
        vid : '1'
        ,vrs_player : 'http://tv.sohu.com/upload/swf/shuiyin.swf'
        ,getHTML: 1
        ,width:width
        ,height: nobheight
        ,playerID:"rencodeFlash"
    }); 

    var obj={},wild_width,wild_height;

    window.save_rencode = function(x,y,w,h,st,et){
    	var order = $("#recode-list li").length+1;
        $("#recode-list").append("<li title='X:"+Math.floor((x-X_DEC)*PERCENT_WIDTH)+"&nbsp;Y:"+Math.floor((y-Y_DEC)*PERCENT_HEIGHT)
        		+"&nbsp;宽:"+Math.floor(w*PERCENT_WIDTH)+"&nbsp;高:"+Math.floor(h*PERCENT_HEIGHT)+"&nbsp;起始时间:"
        		+st+"&nbsp;结束时间:"+et+"'>遮标分段"+order+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
        		+"遮标效果：<label><input type='radio' name='model"+order+"' value='2' checked />毛玻璃</label><label><input type='radio' name='model"+order+"' value='0'>全黑</label>"
        		+"</li>");
        var fx = Math.floor((x-X_DEC)*PERCENT_WIDTH);
        var fy = Math.floor((y-Y_DEC)*PERCENT_HEIGHT);
        var fw = Math.floor(w*PERCENT_WIDTH);
        var fh = Math.floor(h*PERCENT_HEIGHT);
        var item = {
        		"fx" : fx,
        		"fy" : fy,
        		"fw" : fw,
        		"fh" : fh,
        		"st" :st,
        		"et" :et
        };
        $("#recode-list li:last").data("cache",item);
    }
    window.cancel_rencode = function(){
        $(syplayer).width(0).height(0);
    }
    var syplayer = $('#shuiyinplayer>embed')[0];
    if (document.all){
        syplayer = $('#shuiyinplayer>object')[0];
    }
    var mainplayer = $('#sohuplayer>embed')[0];
    if (document.all){
        mainplayer = $('#sohuplayer>object')[0];
    }
    $(syplayer).width(0).height(0);
    $('#shuiyinbtn').bind('click',function(){
        wild_height = mainplayer.videoHeight();
        wild_width = mainplayer.videoWidth();
        wild_videolenght = mainplayer.videoTotTime();
        switch(wild_width){
            case 480:
                VIDEO_TYPE = 2;
                break;
            case 640:
                VIDEO_TYPE = 1;
                break;
            case 1024:
                VIDEO_TYPE = 21;
                break;
            case 1280:
                VIDEO_TYPE = 31;
                break;
            default :
                VIDEO_TYPE = 1;
        }
        
        // 这里需要你计算下，把原始的尺寸转换成相应的播放的视频的尺寸
        if(wild_width*0.75 >= wild_height){//宽铺满
            obj.vh = width * wild_height/wild_width;
            obj.vw= width;
            PERCENT_WIDTH = wild_width/obj.vw;
            PERCENT_HEIGHT = wild_height/obj.vh;
            Y_DEC = (nobheight-obj.vh)/2;
        } else{
            obj.vw = height * wild_width/wild_height;
            obj.vh= height;
            PERCENT_WIDTH = wild_width/obj.vw;
            PERCENT_HEIGHT = wild_height/obj.vh;
            X_DEC = (width-obj.vw)/2;
        }
        obj.st=0;
        obj.et=wild_videolenght;
        $(syplayer).width(width).height(nobheight);
        syplayer.start_rencode(obj);
    });
});