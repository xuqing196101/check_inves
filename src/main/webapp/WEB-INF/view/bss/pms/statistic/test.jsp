<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/common.jsp"/> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家个人信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src="${pageContext.request.contextPath}/public/echarts/china.js"></script>
 <script type="text/javascript">

 
 
 $(function(){
	 option = {
			    title : {
			        text: 'iphone销量',
			        subtext: '纯属虚构',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item'
			    },
			    legend: {
			        orient: 'vertical',
			        x:'left',
			        data:['iphone3','iphone4','iphone5']
			    },
			    dataRange: {
			        min: 0,
			        max: 2500,
			        x: 'left',
			        y: 'bottom',
			        text:['高','低'],           // 文本，默认为数值文本
			        calculable : true
			    },
			    toolbox: {
			        show: true,
			        orient : 'vertical',
			        x: 'right',
			        y: 'center',
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    roamController: {
			        show: true,
			        x: 'right',
			        mapTypeControl: {
			            'china': true
			        }
			    },
			    series : [
			        {
			            name: 'iphone3',
			            type: 'map',
			            mapType: 'china',
			            roam: false,
			            itemStyle:{
			                normal:{label:{show:true}},
			                emphasis:{label:{show:true}}
			            },
			            data:[
			                {name: '北京',value: Math.round(Math.random()*1000)},
			                {name: '天津',value: Math.round(Math.random()*1000)},
			                {name: '上海',value: Math.round(Math.random()*1000)},
			                {name: '重庆',value: Math.round(Math.random()*1000)},
			                {name: '河北',value: Math.round(Math.random()*1000)},
			                {name: '河南',value: Math.round(Math.random()*1000)},
			                {name: '云南',value: Math.round(Math.random()*1000)},
			                {name: '辽宁',value: Math.round(Math.random()*1000)},
			                {name: '黑龙江',value: Math.round(Math.random()*1000)},
			                {name: '湖南',value: Math.round(Math.random()*1000)},
			                {name: '安徽',value: Math.round(Math.random()*1000)},
			                {name: '山东',value: Math.round(Math.random()*1000)},
			                {name: '新疆',value: Math.round(Math.random()*1000)},
			                {name: '江苏',value: Math.round(Math.random()*1000)},
			                {name: '浙江',value: Math.round(Math.random()*1000)},
			                {name: '江西',value: Math.round(Math.random()*1000)},
			                {name: '湖北',value: Math.round(Math.random()*1000)},
			                {name: '广西',value: Math.round(Math.random()*1000)},
			                {name: '甘肃',value: Math.round(Math.random()*1000)},
			                {name: '山西',value: Math.round(Math.random()*1000)},
			                {name: '内蒙古',value: Math.round(Math.random()*1000)},
			                {name: '陕西',value: Math.round(Math.random()*1000)},
			                {name: '吉林',value: Math.round(Math.random()*1000)},
			                {name: '福建',value: Math.round(Math.random()*1000)},
			                {name: '贵州',value: Math.round(Math.random()*1000)},
			                {name: '广东',value: Math.round(Math.random()*1000)},
			                {name: '青海',value: Math.round(Math.random()*1000)},
			                {name: '西藏',value: Math.round(Math.random()*1000)},
			                {name: '四川',value: Math.round(Math.random()*1000)},
			                {name: '宁夏',value: Math.round(Math.random()*1000)},
			                {name: '海南',value: Math.round(Math.random()*1000)},
			                {name: '台湾',value: Math.round(Math.random()*1000)},
			                {name: '香港',value: Math.round(Math.random()*1000)},
			                {name: '澳门',value: Math.round(Math.random()*1000)}
			            ]
			        },
			        {
			            name: 'iphone4',
			            type: 'map',
			            mapType: 'china',
			            itemStyle:{
			                normal:{label:{show:true}},
			                emphasis:{label:{show:true}}
			            },
			            data:[
			                {name: '北京',value: Math.round(Math.random()*1000)},
			                {name: '天津',value: Math.round(Math.random()*1000)},
			                {name: '上海',value: Math.round(Math.random()*1000)},
			                {name: '重庆',value: Math.round(Math.random()*1000)},
			                {name: '河北',value: Math.round(Math.random()*1000)},
			                {name: '安徽',value: Math.round(Math.random()*1000)},
			                {name: '新疆',value: Math.round(Math.random()*1000)},
			                {name: '浙江',value: Math.round(Math.random()*1000)},
			                {name: '江西',value: Math.round(Math.random()*1000)},
			                {name: '山西',value: Math.round(Math.random()*1000)},
			                {name: '内蒙古',value: Math.round(Math.random()*1000)},
			                {name: '吉林',value: Math.round(Math.random()*1000)},
			                {name: '福建',value: Math.round(Math.random()*1000)},
			                {name: '广东',value: Math.round(Math.random()*1000)},
			                {name: '西藏',value: Math.round(Math.random()*1000)},
			                {name: '四川',value: Math.round(Math.random()*1000)},
			                {name: '宁夏',value: Math.round(Math.random()*1000)},
			                {name: '香港',value: Math.round(Math.random()*1000)},
			                {name: '澳门',value: Math.round(Math.random()*1000)}
			            ]
			        },
			        {
			            name: 'iphone5',
			            type: 'map',
			            mapType: 'china',
			            itemStyle:{
			                normal:{label:{show:true}},
			                emphasis:{label:{show:true}}
			            },
			            data:[
			                {name: '北京',value: Math.round(Math.random()*1000)},
			                {name: '天津',value: Math.round(Math.random()*1000)},
			                {name: '上海',value: Math.round(Math.random()*1000)},
			                {name: '广东',value: Math.round(Math.random()*1000)},
			                {name: '台湾',value: Math.round(Math.random()*1000)},
			                {name: '香港',value: Math.round(Math.random()*1000)},
			                {name: '澳门',value: Math.round(Math.random()*1000)}
			            ]
			        }
			    ]
			};
		
 	var myChart = echarts.init(document.getElementById("main"));
		myChart.setOption(option);
		myChart.hideLoading(); 
		
		/*  var chart = echarts.init(document.getElementById('main'));
		 chart.setOption({
		     series: [{
		         type: 'map',
		         map: 'china'
		     }]
	  $.get('public/echarts/china.json', function (chinaJson) {
			    echarts.registerMap('china', chinaJson);
			    var chart = echarts.init(document.getElementById('main'));
			    chart.setOption({
			        series: [{
			            type: 'map',
			            map: 'china'
			        }]
			    });
			}); */
 }) ;
 
	
$(function(){
	
	option = {
		    title : {
		        text: '某站点用户访问来源',
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	var myChart = echarts.init(document.getElementById("pipe"));
	myChart.setOption(option);
	myChart.hideLoading(); 
	});
 
 $(function(){
	 var dataAxis = ['点', '击', '柱', '子', '或', '者', '两', '指', '在', '触', '屏', '上', '滑', '动', '能', '够', '自', '动', '缩', '放'];
	 var data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220];
	 var yMax = 500;
	 var dataShadow = [];

	 for (var i = 0; i < data.length; i++) {
	     dataShadow.push(yMax);
	 }

	 option = {
	     title: {
	         text: '特性示例：渐变色 阴影 点击缩放',
	         subtext: 'sss'
	     },
	     xAxis: {
	         data: dataAxis,
	         axisLabel: {
	             inside: true,
	             textStyle: {
	                 color: '#fff'
	             }
	         },
	         axisTick: {
	             show: false
	         },
	         axisLine: {
	             show: false
	         },
	         z: 10
	     },
	     yAxis: {
	         axisLine: {
	             show: false
	         },
	         axisTick: {
	             show: false
	         },
	         axisLabel: {
	             textStyle: {
	                 color: '#999'
	             }
	         }
	     },
	 /*     dataZoom: [
	         {
	             type: 'inside'
	         }
	     ], */
	     series: [
	         { // For shadow
	             type: 'bar',
	             itemStyle: {
	                 normal: {color: 'rgba(0,0,0,0.05)'}
	             },
	             barGap:'-100%',
	             barCategoryGap:'40%',
	             data: dataShadow
	         },
	         {
	             type: 'bar',
	             itemStyle: {
	                 normal: {
	                     color: new echarts.graphic.LinearGradient(
	                         0, 0, 0, 1,
	                         [
	                             {offset: 0, color: '#83bff6'},
	                             {offset: 0.5, color: '#188df0'},
	                             {offset: 1, color: '#188df0'}
	                         ]
	                     )
	                 },
	                 emphasis: {
	                     color: new echarts.graphic.LinearGradient(
	                         0, 0, 0, 1,
	                         [
	                             {offset: 0, color: '#2378f7'},
	                             {offset: 0.7, color: '#2378f7'},
	                             {offset: 1, color: '#83bff6'}
	                         ]
	                     )
	                 }
	             },
	             data: data
	         }
	     ]
	 };
	 
	 var myChart = echarts.init(document.getElementById("bar"));
		myChart.setOption(option);
		myChart.hideLoading(); 
		
		
	 });

	$(function(){
		option = {
			    title: {
			        text: '折线图堆叠'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['采购金额']
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: ['周一','周二','周三','周四','周五','周六','周日']
			    },
			    yAxis: {
			        type: 'value'
			    },
			    series:${json}
			};
		 var myChart = echarts.init(document.getElementById("line"));
			myChart.setOption(option);
			myChart.hideLoading(); 
		
		});	 
 </script>
 
<style>   
.error {   
    color: #ff0000;   
    font-weight: bold;   
}   
</style>
</head>
<body>

<sf:form action="${pageContext.request.contextPath}/test/add.html"  method="post" modelAttribute="test" >
 用户名:<input type="text" name="name" value="${test.name }"/><sf:errors path="name"/><br/>
 密码:<input type="text" name="password"/><sf:errors path="password"/><br/>
 
邮箱:<input type="text" name="email"/><sf:errors path="email"/><br/>
身份证号:<input type="text" name="idNumer"/><sf:errors path="idNumer"/><br/>
手机号:<input type="text" name="mobile"/><sf:errors path="mobile"/><br/>
年龄:<input type="text" name="ss"/><sf:errors path="age"/><br/>   
 <input type="submit" value="添加"/>
</sf:form>

 	<u:upload id="cs"   businessId="1231" sysKey="2" typeId="12314141"/>
				<u:show showId="cs"   sysKey="2" typeId="12124124"/>
				
			
			<div id="main" style="width:800px; height: 100%;"></div>
			<div id="pipe" style="width:800px; height: 100%;"></div>
			<div id="bar" style="width:800px; height: 100%;"></div>
			<div id="line" style="width:800px; height: 100%;"></div>
</body>
</html>