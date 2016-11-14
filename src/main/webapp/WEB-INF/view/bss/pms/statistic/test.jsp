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

 <script type="text/javascript">

 
 $(function(){
	 function randomData() {
		    return Math.round(Math.random()*1000);
		}

		option = {
		    title: {
		        text: 'iphone销量',
		        subtext: '纯属虚构',
		        left: 'center'
		    },
		    tooltip: {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data:['iphone3','iphone4','iphone5']
		    },
		    visualMap: {
		        min: 0,
		        max: 2500,
		        left: 'left',
		        top: 'bottom',
		        text: ['高','低'],           // 文本，默认为数值文本
		        calculable: true
		    },
		    toolbox: {
		        show: true,
		        orient: 'vertical',
		        left: 'right',
		        top: 'center',
		        feature: {
		            dataView: {readOnly: false},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    series: [
		        {
		            name: 'iphone3',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data:[
		                {name: '北京',value: randomData() },
		                {name: '天津',value: randomData() },
		                {name: '上海',value: randomData() },
		                {name: '重庆',value: randomData() },
		                {name: '河北',value: randomData() },
		                {name: '河南',value: randomData() },
		                {name: '云南',value: randomData() },
		                {name: '辽宁',value: randomData() },
		                {name: '黑龙江',value: randomData() },
		                {name: '湖南',value: randomData() },
		                {name: '安徽',value: randomData() },
		                {name: '山东',value: randomData() },
		                {name: '新疆',value: randomData() },
		                {name: '江苏',value: randomData() },
		                {name: '浙江',value: randomData() },
		                {name: '江西',value: randomData() },
		                {name: '湖北',value: randomData() },
		                {name: '广西',value: randomData() },
		                {name: '甘肃',value: randomData() },
		                {name: '山西',value: randomData() },
		                {name: '内蒙古',value: randomData() },
		                {name: '陕西',value: randomData() },
		                {name: '吉林',value: randomData() },
		                {name: '福建',value: randomData() },
		                {name: '贵州',value: randomData() },
		                {name: '广东',value: randomData() },
		                {name: '青海',value: randomData() },
		                {name: '西藏',value: randomData() },
		                {name: '四川',value: randomData() },
		                {name: '宁夏',value: randomData() },
		                {name: '海南',value: randomData() },
		                {name: '台湾',value: randomData() },
		                {name: '香港',value: randomData() },
		                {name: '澳门',value: randomData() }
		            ]
		        },
		        {
		            name: 'iphone4',
		            type: 'map',
		            mapType: 'china',
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data:[
		                {name: '北京',value: randomData() },
		                {name: '天津',value: randomData() },
		                {name: '上海',value: randomData() },
		                {name: '重庆',value: randomData() },
		                {name: '河北',value: randomData() },
		                {name: '安徽',value: randomData() },
		                {name: '新疆',value: randomData() },
		                {name: '浙江',value: randomData() },
		                {name: '江西',value: randomData() },
		                {name: '山西',value: randomData() },
		                {name: '内蒙古',value: randomData() },
		                {name: '吉林',value: randomData() },
		                {name: '福建',value: randomData() },
		                {name: '广东',value: randomData() },
		                {name: '西藏',value: randomData() },
		                {name: '四川',value: randomData() },
		                {name: '宁夏',value: randomData() },
		                {name: '香港',value: randomData() },
		                {name: '澳门',value: randomData() }
		            ]
		        },
		        {
		            name: 'iphone5',
		            type: 'map',
		            mapType: 'china',
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data:[
		                {name: '北京',value: randomData() },
		                {name: '天津',value: randomData() },
		                {name: '上海',value: randomData() },
		                {name: '广东',value: randomData() },
		                {name: '台湾',value: randomData() },
		                {name: '香港',value: randomData() },
		                {name: '澳门',value: randomData() }
		            ]
		        }
		    ]
		};
		
		var myChart = echarts.init(document.getElementById("main"));
		myChart.setOption(option);
		myChart.hideLoading();
 }) ;
 
	
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
    
 <input type="submit" value="添加"/>
</sf:form>

 	<u:upload id="cs"   businessId="1231" sysKey="2" typeId="12314141"/>
				<u:show showId="cs"   sysKey="2" typeId="12124124"/>
				
			
			<div id="main"></div>
			
				
</body>
</html>