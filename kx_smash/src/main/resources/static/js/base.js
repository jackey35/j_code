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

var openid = $('#openid').val();
//winLevel 结果
var winLevel = {
    1:['特等奖','苹果IphoneX256G手机一部'],
    2:['一等奖：','一百元手机话费'],
    3:['二等奖：','50元手机话费'],
    4:['三等奖：','锅/书'],
    5:['普照奖：','50元首投现金红包'],
    6:['普照奖：','50元老用户投资红包'],
}

//httpStatus 结果
var ss = [];
ss[0] = '系统异常';
ss[100] = '今日抽奖机会已用完';
ss[101] = '转发朋友圈赢取抽奖机会';
ss[102] = '奖品已抽完';

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
    setTimeout(function(){
        $.post('http://www.jinxinsenhui.com/smash/zha.do?openId='+openid,function(data){
            $('.result').removeClass('hide');
            if(data.httpStatus == 1){
                if(data.obj.winLevel!=0){
                    number--;
                    $('.winning').removeClass('hide');
                    $('.winning .head').html('恭喜您！</br>砸中'+winLevel[data.obj.winLevel][0]+'!');
                    $('.winning .prize').html(winLevel[data.obj.winLevel][1]);
                    $('.success').addClass('hide');
                    $('#result-img').attr('src','../skin/images/zhongjiang.png');
                    winId = data.obj.winId
                }else{
                    $('.lost').removeClass('hide').find('p').html('好可惜！</br>这个是空的！');
                }
            }else{
                console.log(ss , data.httpStatus);
                $('.lost').removeClass('hide').find('p').html(ss[data.httpStatus]);
                $('#result-img').attr('src','../skin/images/meizhongjiang.png');
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
    var name = $('#name').val(),phone=$('#phone').val(),address = $('#address').val();
    $.post('http://www.jinxinsenhui.com/smash/winning.do?phone='+phone+'&name='+name+'&address='+address+
       '&openId='+openid+'&winId='+winId,function(data){
        if(data.httpStatus==1){
            $('.submit-from').addClass('hide');
            $('.success').removeClass('hide')
        }else{
            $('.submit-from p.errorMsg').html(userStatus[data.httpStatus]);
        }
    });
})
//关闭结果窗口
$('a.close').click(function(){
    $('.result').addClass('hide');
    $('.jindan').attr('src','../skin/images/jindan.png');
    $('.chuizi').removeClass('zadan').hide();
    $('.result-content > div').addClass('hide');
});
