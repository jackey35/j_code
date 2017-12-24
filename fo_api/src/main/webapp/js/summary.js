
/**
 * 时报或日报处理函数
 */
function hourOrDayChart(Data,befData){
	var xMinute = new Set();
	var cinvokeCnt = [];
	var befcinvokeCnt = [];
	var csumTimes = [];
	var befcsumTimes = [];
	
	var sinvokeCnt = [];
	var befsinvokeCnt = [];
	var ssumTimes = [];
	var befssumTimes = [];
	var xminuteLen = 0;
	if(Data !=null && Data !="" ){
		xminuteLen = Data.length
		for(var i=0;i<xminuteLen;i++){
			var item = Data[i];
			xMinute.add(item.dt);
		}
	} else if(befData !=null && befData !=""){
		xminuteLen = befData.length
		for(var i=0;i<xminuteLen;i++){
			var item = befData[i];
			xMinute.add(item.dt);
		}
	}
	
	
	
	for(var i=0;i<xMinute.size();i++){
		var dt = xMinute.toArray()[i]
		if(Data !=null && Data !="" ){
		    var ccnt = 0;
		    var scnt = 0;
			for(var j=0;j<Data.length;j++){
				var item = Data[j];
				if(item != null && item != ""){
					if(dt == item.dt){
						cinvokeCnt[i]=item.invokeCnt;
						csumTimes[i]=item.sumTimes;
						ccnt=ccnt+1;
					}
				}else {
					cinvokeCnt[i]=0;
				}
			}
			
			if(ccnt==0){
				cinvokeCnt[i]=0;
				csumTimes[i]=0;
			}
		}
		
		if(befData != null && befData != ""){
			var bccnt=0;
			var bscnt=0;
			for(var k=0;k<befData.length;k++){
				var befItem = befData[k];
				if(befItem != null && befItem != ""){
					if(dt == befItem.dt){
						befcinvokeCnt[i]=befItem.invokeCnt;
						befcsumTimes[i]=befItem.sumTimes;
						bccnt=bccnt+1;
					}
				} else {
					befcinvokeCnt[i]=0;
				}
			}
			
			if(bccnt==0){
				befcinvokeCnt[i]=0;
				befcsumTimes[i]=0;
			}
		}
	}
	
	  $('#invoke-count-chart').highcharts({
	          chart: {
	              type: 'line'
	          },
	          title: {
	              text: '接口调用统计'
	          },
	          subtitle: {
	              text: '调用次数'
	          },
	          xAxis: {
	              categories:xMinute.toArray()
	          },
	          yAxis: {
	              min: 0,
	              title: {
	                  text: 'count'
	              }
	          },
	          tooltip: {
	              headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	              pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                  '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	              footerFormat: '</table>',
	              shared: true,
	              useHTML: true
	          },
	          plotOptions: {
	              column: {
	                  pointPadding: 0.2,
	                  borderWidth: 0
	              }
	          },
	          series: [{
	              name: '当日客户端调用次数',
	              data: cinvokeCnt
	  
	          }, {
	              name: '前日客户端调用次数',
	              data: befcinvokeCnt
	  
	          }]
	      });
	  
	  $('#invoke-time-chart').highcharts({
	          chart: {
	              type: 'line'
	          },
	          title: {
	              text: '接口调用统计'
	          },
	          subtitle: {
	              text: '调用耗时'
	          },
	          xAxis: {
	              categories:xMinute.toArray()
	          },
	          yAxis: {
	              min: 0,
	              title: {
	                  text: 'times(毫秒)'
	              }
	          },
	          tooltip: {
	              headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	              pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                  '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
	              footerFormat: '</table>',
	              shared: true,
	              useHTML: true
	          },
	          plotOptions: {
	              column: {
	                  pointPadding: 0.2,
	                  borderWidth: 0
	              }
	          },
	          series: [{
	              name: '当日客户端调用耗时',
	              data: csumTimes
	  
	          }, {
	              name: '前日客户端调用耗时',
	              data: befcsumTimes
	  
	          }]
	      });
	          
	  $("input[name=chart-select]").click(function(){$(".chart").hide();$("#"+$("input[name=chart-select]:checked").val()).show();});
}

function initSel(name,methodName){
	$.post("/minute/mdname.do", { Action: "post", serviceName: name },
			function (data, textStatus){
				if(data.status==1){
					var mdname = eval(data.msg)
					
					if(mdname!=null && mdname.length > 0){
						$("#methodName option").each(function(){
   							if($(this).val() != null && $(this).val() != ""){
   								 $(this).remove();
   							}
  						 });
  						 
						for(var i=0; i<mdname.length; i++)
  						{
  							if(methodName != null && methodName == mdname[i]){
    							$("#methodName").append("<option selected value='"+mdname[i]+"'>"+mdname[i]+"</option>"); 
    						} else {
    							$("#methodName").append("<option value='"+mdname[i]+"'>"+mdname[i]+"</option>"); 
    						}
  						}
  					}else{
  						 $("#methodName option").each(function(){
   							if($(this).val() != null && $(this).val() != ""){
   								 $(this).remove();
   							}
  						 });
  					}
				}
			}, "json");
}