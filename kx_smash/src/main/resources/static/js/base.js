(function (doc, win) {
    var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                docEl.style.fontSize = 24 * (clientWidth / 320) + 'px';
            };

    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

var doudan = setInterval('dandan()',2000);
function dandan(){
    console.log(1);
    $('.dan .za').removeClass('doudan');
    var num = Math.floor(Math.random()*5);
    $('.dan .za').eq(num).addClass('doudan');
}

var openid = $('#openid').val();
var vtype = $('#vtype').val();
//winLevel 结果
var winLevel = {
    1:['特等奖','苹果IphoneX256G手机一部'],
    2:['一等奖：','100元手机话费'],
    3:['二等奖：','50元手机话费'],
    4:['三等奖：','精品菜谱和铁锅'],
    5:['普照奖A：','小确幸30元现金兑换码'],
    6:['普照奖B：','50元开心宝平台红包代金券'],
}

//httpStatus 结果
var ss = [];
ss[0] = '系统异常';
ss[100] = '今日抽奖机会已用完';
ss[101] = '分享朋友圈赢取抽奖机会';
ss[102] = '奖品已抽完';
ss[103] = '活动尚未开始';
ss[104] = '活动已结束';

//测试数据
var data = {
    obj:{
        winLevel:1,
    },
    httpStatus:1
}
var winId;
var number = 1;// 默认砸蛋数量  需后台传入变量
$('.za').click(function(event) { // 砸蛋事件
    var item = this;
    $(item).css('z-index','10').find('.chuizi').addClass('zadan').show();
    setTimeout(function(){
        $(item).find('.jindan').attr('src','../skin/images/suidan.png');
    },150);
    clearInterval(doudan);
    setTimeout(function(){
        $.post('http://www.jinxinsenhui.com/smash/zha.do?openId='+openid+"&vtype="+vtype,function(data){
            $('.result').removeClass('hide');
            if(data.httpStatus == 1){
            	   $('#winLevel').val(data.obj.winLevel);
                $('#qrUrl').val(data.obj.qrUrl);
                 
                if(data.obj.winLevel<7){
                    number--;
                    $('.winning').removeClass('hide');
                    $('.winning .head').html('恭喜您！</br>砸中'+winLevel[data.obj.winLevel][0]);
                    $('.winning .prize').html(winLevel[data.obj.winLevel][1]);
                    $('.success').addClass('hide');
                    
                    $('#result-img').attr('src','../skin/images/zhongjiang.png');
                    $('#receive').html('点击领取');
                    $('#receive').addClass('submit');
                    winId = data.obj.winId
                }else{
                	    $('#result-img').attr('src','../skin/images/meizhongjiang.png');
                    $('.lost').removeClass('hide').find('p').html('</br>谢谢参与！');
                }
            }else{
                console.log(ss , data.httpStatus);
                $('.lost').removeClass('hide').find('p').html(ss[data.httpStatus]);
                
                if(data.httpStatus == 101){
                		$('#result-img').attr('src','../skin/images/share.png');
                }else{
                		$('#result-img').attr('src','../skin/images/meizhongjiang.png');
                }
            }
        }); 
    },500);
});
//点击领取 显示表单信息
$('#receive').click(function(){
    $('.winning').addClass('hide');
    $('.submit-from').removeClass('hide');
});


var userStatus = [];
userStatus[0] = '请输入姓名和手机号';
userStatus[102] = '非法用户';
userStatus[103] = '非法中奖信息';
userStatus[103] = '中奖信息重复提交';
//提交表单 用户信息
$('#submit').click(function(){
    var name = $('#name').val(),phone=$('#phone').val();
    $.post('http://www.jinxinsenhui.com/smash/winning.do?phone='+phone+'&name='+name+
       '&openId='+openid+'&winId='+winId,function(data){
        if(data.httpStatus==1){
            var wLevel = $("#winLevel").val();
            var qrUrl = $("#qrUrl").val();
            if(wLevel == 5){
            	    $('.submit-from').addClass('hide');
            	    $('.winning').removeClass('hide');
                $('.winning .head').html('此二维码是兑奖的唯一凭证，请截图妥善保管');
            	    $('#result-img').attr('src',qrUrl);
            	    $('.winning .prize').html('');
            	    $('#receive').html('');
                $('#receive').removeClass('submit');
            }else{
            		$('.submit-from').addClass('hide');
                $('.success').removeClass('hide')
            }
        }else{
            $('.submit-from p.errorMsg').html(userStatus[data.httpStatus]);
        }
    });
})
//表单提交成功之后  点击确认打开分享页面
$('.success .submit').click(function(){
    $('.lost').removeClass('hide');
    $('.success').addClass('hide');
    $('.result-egg img').attr('src','../skin/images/share.png');
});
//关闭结果窗口
$('a.close').click(function(){
    $('.result').addClass('hide');
    $('.jindan').attr('src','../skin/images/jindan.png');
    $('.chuizi').removeClass('zadan').hide();
    $('.result-content > div').addClass('hide');
    $('.result-egg img').attr('src','../skin/images/zhongjiang.png');
    doudan = setInterval('dandan()',2000);
});
