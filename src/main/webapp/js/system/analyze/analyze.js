var option1;
$(function() {
	// 初始化echarts实例
	var uploadPic = echarts.init(document.getElementById('uploadPic'));
	var uploadPic1 = echarts.init(document.getElementById('uploadPic1'));
	var uploadPic2 = echarts.init(document.getElementById('uploadPic2'));

	option1 = {
		title : {
			top : 0,
			left : '43%'
		},
		tooltip : {
	        trigger: 'axis'
	    },
		legend : {
			bottom : 0
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		/*yAxis : [ {
			type : 'value',
			name : '数量/人'
		} ],*/
	};
	// 使用刚指定的配置项和数据显示图表
	// 用户登录统计
	
	/*var data = [{group:'供应商',name:'2001',value:45},{group:'供应商',name:'2002',value:12},{group:'供应商',name:'2003',value:103},
	            {group:'专家',name:'2001',value:34},{group:'专家',name:'2002',value:76},{group:'专家',name:'2003',value:87},
	            {group:'后台',name:'2001',value:56},{group:'后台',name:'2002',value:102},{group:'后台',name:'2003',value:150}]*/
	$.ajax({
		url: globalPath+"/analyze/analyzeLoginCount.do?",
		data:{
			analyzeType : "DAY",
			analyzeTypeByCate: "C_LOGIN"
		},
		type: "POST", //请求方式 
		dataType: "json", //返回格式为json
		success: function(data) {
			$("#uploadPic").echartsTemplate({
				
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				title:'用户登录统计图',
				XTitle:'日期',
				YTitle:'数量/人',
				customEchartsOptions:function(res,dataList,option){
					return option1;
				}
			});
		}
	});
	
	$.ajax({
		url: globalPath+"/analyze/analyzeLoginCount.do?",
		data:{
			analyzeType : "DAY",
			analyzeTypeByCate: "C_REGISTER"
		},
		type: "POST", //请求方式 
		dataType: "json", //返回格式为json
		success: function(data) {
			$("#uploadPic1").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				title:'用户注册统计图',
				XTitle:'日期',
				YTitle:'数量/人',
				customEchartsOptions:function(res,dataList,option){
					return option1;
				}
			});
		}
	});
	
	
	$.ajax({
		url: globalPath+"/analyze/analyzeLoginCount.do?",
		data:{
			analyzeType : "DAY",
			analyzeTypeByCate: "C_ATT_UPLOAD"
		},
		type: "POST", //请求方式 
		dataType: "json", //返回格式为json
		success: function(data) {
			$("#uploadPic2").echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				title:'图片上传统计图',
				XTitle:'日期',
				YTitle:'数量/个',
				customEchartsOptions:function(res,dataList,option){
					return option1;
				}
			});
		}
	});
});

//按钮点击统计事件
function analyzeAll(type,typeCate,id,title){
	
	var xtitle = '';
	var ytitle = '';
	if(type == "DAY"){
		xtitle = '日期';
	}
	if(type == 'WEEK'){
		xtitle = '周';
	}
	if(type == 'MONTH'){
		xtitle = '月份';
	}
	if(typeCate == 'C_LOGIN' || typeCate == 'C_REGISTER'){
		ytitle = '数量/人';
	}
	if(typeCate == 'C_ATT_UPLOAD'){
		ytitle = '数量/个';
	}
	
	$.ajax({
		url: globalPath+"/analyze/analyzeLoginCount.do?",
		data:{
			analyzeType : type,
			analyzeTypeByCate: typeCate
		},
		type: "POST", //请求方式 
		dataType: "json", //返回格式为json
		success: function(data) {
			$("#"+id).echartsTemplate({
				dataList:data,
				XField:'name',
				YField:'value',
				groupField:'group',
				chartType:'bar',
				title:title,
				XTitle:xtitle,
				YTitle:ytitle,
				customEchartsOptions:function(res,dataList,option){
					return option1;
				}
			});
		}
	});
}