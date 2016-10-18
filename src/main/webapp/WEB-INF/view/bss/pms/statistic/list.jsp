<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>采购需求管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	


<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/js/FusionCharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
<link href="<%=basePath%>public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${info.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		        //	$("#page").val(e.curr);
		        	// $("#form1").submit();
		        	
		         location.href = '<%=basePath%>purchaser/list.do?page='+e.curr;
		        }  
		    }
		});
  });
  
   
  function bar(sign) {
		var swf="Column3D.swf";
	  
	  $("#div_table").hide();
	  $.ajax({
			 	url : '${pageContext.request.contextPath}/statistic/bar.html',
				type : "post",
				data :$("#add_form").serialize(),
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType : "json",
				success : function(data) {
					 var chart = new FusionCharts('${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/swf/' + swf, "funsionCharts_id", "100%", "100%", "0", "1");
					chart.setJSONData(data);  
					$("#funsionCharts_div_id").show();
					$("#divTable").hide();
		 			chart.render("funsionCharts_div_id");   
				},
				 
			}); 
		  
}
 
  function pipe(sign){
		var  swf = "Pie3D.swf";
		  $("#div_table").hide();
		  $.ajax({
				 	url : '${pageContext.request.contextPath}/statistic/pipe.html',
					type : "post",
					data :$("#add_form").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType : "json",
					success : function(data) {
						 var chart = new FusionCharts('${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/swf/' + swf, "funsionCharts_id", "100%", "100%", "0", "1");
						chart.setJSONData(data);  
						$("#funsionCharts_div_id").show();
						$("#divTable").hide();
			 			chart.render("funsionCharts_div_id");   
					},
					 
				});
		  
  }
  
  function line(sign){
		var  swf = "Line.swf";
		  $("#div_table").hide();
		  $.ajax({
				 	url : '${pageContext.request.contextPath}/statistic/line.html',
					type : "post",
					data :$("#add_form").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType : "json",
					success : function(data) {
						 var chart = new FusionCharts('${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/swf/' + swf, "funsionCharts_id", "100%", "100%", "0", "1");
						chart.setJSONData(data);  
						$("#funsionCharts_div_id").show();
						$("#divTable").hide();
			 			chart.render("funsionCharts_div_id");   
					},
					 
				});
		  
}
 
  
	$(function () {
		var address;
	    Highcharts.setOptions({
	        lang:{
	            drillUpText:"返回 > {series.name}"
	        }
	    });

	    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#container').width() < 400;
	    // 给城市设置随机数据
	    $.each(data, function (i) {
	        this.drilldown = this.properties['drill-key'];
			 
	        this.value = i;
	    });
			function getPoint(e){
				console.log(e.point.name);
			}
		function getShow(e){
			alert(1);
		}
	    //初始化地图
	    $('#container').highcharts('Map', {

	        chart : {
						spacingBottom:30,
					 
	            events: {
	               
	            }
	        },
				tooltip: { 
					formatter:function(){
						var htm="";
						if(this.point.drilldown){
						    htm+=this.point.properties["cn-name"];
						}else{
							 htm+=this.point.name;
						}
						address=htm;
						 var data='${data}';
					    if(data==''){
					     	htm+=":"+0; 
					    }else{
						   var index=data.indexOf(htm);
						   var indexStart=index+htm.length;
						   var indexEnd=indexStart+2;
						   var supplierNum=data.substring(indexStart,indexEnd );
						   if("0123456789".indexOf(supplierNum.substring(supplierNum.length-1, supplierNum.length))==-1){
						   		supplierNum=supplierNum.substring(0,1);
						   }
						   htm+=":"+supplierNum; 
						 }
						return htm;
					}
						},
	        credits:{
						href:"javascript:goHome()",
	            text:""
	        },
	        title : {
	            text : '供应商数量统计'
	        },

	        subtitle: {
	            text: '中国',
	            floating: true,
	            align: 'right',
	            y: 50,
	            style: {
	                fontSize: '16px'
	            }
	        },

	        legend: small ? {} : {
						 // enabled: false,
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle'
	        },
	        //tooltip:{
	        //pointFormat:"{point.properties.cn-name}:{point.value}"
	        //},
	        colorAxis: {
	            min: 0,
	            minColor: '#E6E7E8',
	            maxColor: '#005645',
						labels:{
							style:{
									"color":"red","fontWeight":"bold"
							}
						}
	        },

	        mapNavigation: {
	            enabled: true,
	            buttonOptions: {
	                verticalAlign: 'bottom'
	            }
	        },

	        plotOptions: {
	            map: {
	                states: {
	                    hover: {
	                        color: '#EEDD66'
	                    }
	                }
	            }
	        },

	        series : [{
	            data : data,
	            name: '中国',
	            dataLabels: {
	                enabled: true,
	                format: '{point.properties.cn-name}'
	            },
	            point: {
	               events: {
	                   click: function () { 
	                   address=encodeURI(address);
	                   address=encodeURI(address);
	                       window.location.href="<%=basePath%>supplierQuery/findSupplierByPriovince.html?address="+address+"&status=3";
	                    }
	                  }
	           }
	        }],

	        drilldown: {
						
	            activeDataLabelStyle: {
	                color: '#FFFFFF',
	                textDecoration: 'none',
	                textShadow: '0 0 3px #000000'
	            },
	            drillUpButton: {
	                relativeTo: 'spacingBox',
	                position: {
	                    x: 0,
	                    y: 60
	                }
	            }
	        }
	    });
	});
	
	
	
	function maps(){
		$("#funsionCharts_div_id").hide();
		$("#container").show();
		
	}
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">障碍作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!--    <div class="headline-v2">
      <h2>查询条件</h2>
   </div> -->
<!-- 项目戳开始 -->
  <div class="border1 col-md-12 ml30">
    <form id="add_form" action="<%=basePath%>statistic/list.html" method="post" >
   

	  物资类别：<select name="">
	  <option></option>
	  </select>
	    年度： <input class="mt10" type="text" name="year" value="${year}" /> 
	   需求部门： <input class="mt10" type="text" name="department" value="${inf.department }" /> 
	
	  采购方式： <input class="mt10" type="text" name="purchaseType" value="${inf.purchaseType }" /> 
	   采购机构：  <input class="mt10"  value='${inf.organization }' name="organization" type="text" > 
	   预算：  <input class="mt10"  value='${inf.budget }' name="budget" type="text" > 
	   	 <input class="btn-u"   type="submit" name="" value="查询" /> 
	   	 <input class="btn-u"   type="button" name="" value="按需求部门统计" onclick="bar(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购方式统计" onclick="pipe(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按月统计" onclick="line(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购省市统计" onclick="maps()" /> 
   </form>
  </div>
   <div class="headline-v2 fl">
      <h2>需求计划列表
	  </h2>
   </div> 
   <div id="div_table" class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
		<thead>
		<tr>
<!-- 		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
 -->		  <th class="info w50">序号</th>
		  <th class="info">计划名称</th>
		  <th class="info">编制单位名称</th>
		  <th class="info">金额</th>
		  <th class="info">编制时间</th>
		  <th class="info">完成时间</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
<%-- 			  <td class="tc w30"><input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""></td>
 --%>			  <td class="tc w50" onclick="view('${obj.planNo }')" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.planName }</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.department }</td>
			  <td class="tc" onclick="view('${obj.planNo }')">${obj.budget }</td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.auditDate }"/> </td>
			  <td class="tc" onclick="view('${obj.planNo }')">
			  <c:if test="${obj.status=='1' }">
			 	 已编制为采购计划
			  </c:if>
			   <c:if test="${obj.status=='2' }">
			 	已提交
			  </c:if>
			  <c:if test="${obj.status=='3' }">
			 	提交受理
			  </c:if>
			    <c:if test="${obj.status=='4' }">
			 	已受理
			  </c:if>
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>

 	<div id="funsionCharts_div_id" style="width:91%;height:90%;overflow:atuo; display: none;"></div>
 
   <div id="container" style="display: none;height: 700px;min-width: 310px;margin: 0 auto;min-width: 310px;"></div>
   
   
	 </body>
</html>
