<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    
    <title>采购需求管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

 
<script type="text/javascript" src="${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/js/FusionCharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
<script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
<script src="${pageContext.request.contextPath}/public/echarts/china.js"></script>

<link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  
	  if (!window.console || !console.firebug){
		    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

		    window.console = {};
		    for (var i = 0; i < names.length; ++i)
		        window.console[names[i]] = function() {}
		}
	  
	  
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
		        	$("#page").val(e.curr);
		        	  $("#add_form").submit();
		        	
		     
		        }  
		    }
		});
  });
  
   
  function bar(sign) {
	  
	  $("#div_table").hide();
	  $.ajax({
			 	url : '${pageContext.request.contextPath}/statistic/bar.html',
				type : "post",
				data :$("#add_form").serialize(),
				dataType:"json",
			
				success : function(data) {
			 
					  var dataAxis = data.name;
					 var data = data.data;
					 var yMax = data.max;
					 
					 var dataShadow = [];

					 for (var i = 0; i < data.length; i++) {
					     dataShadow.push(yMax);
					 }

					 option = {
					     title: {
					         text: '需求部门统计',
					         subtext: 'sss',
					         left: '200px',
					     },
					     tooltip : {
						        trigger: 'item',
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
					            	 /**  注释的是柱状图显示渐变颜色的方式，但是在IE8下面不支持(显示)这样获取的颜色
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
					                 **/
					                 normal: {
					                     color: function(params) {
					                            // build a color map as your need.
					                            var colorList = [//写这么多颜色是为了各个柱子
													'#83bff6','#83bff6','#83bff6','#83bff6','#83bff6','#83bff6','#83bff6',
													'#83bff6','#83bff6','#83bff6','#83bff6'
					                            ];
					                            return colorList[params.dataIndex]
					                        }
					                 }//,
					                 /** 
					                 emphasis: {//这个先去掉
					                     color: function(params) {
					                            // build a color map as your need.
					                            var colorList = [
													'red'
					                            ];
					                            return colorList[params.dataIndex]
					                        }
					                 }**/
					             },
					             data: data
					         }
					     ]
					 };
					 $("#funsionCharts_div_id").html("");
					 var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
						myChart.setOption(option);
						myChart.hideLoading(); 
					  $("#funsionCharts_div_id").show();
					  $("#container").hide();  
		 			 
				}
				 
			}); 
		  
}
 
  function pipe(sign){
		  $("#div_table").hide();
		  $.ajax({
				 	url : '${pageContext.request.contextPath}/statistic/pipe.html',
					type : "post",
					data :$("#add_form").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType:"json",
					success : function(data) {
						option = {
							    title : {
							        text: '采购方式统计',
							        x:'center'
							    },
							    tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {c} ({d}%)"
							    },
							    legend: {
							        orient: 'vertical',
							        left: '200px',
							        data: data.type
							    },
							    series : [
							        {
							            name: '采购方式',
							            type: 'pie',
							            radius : '55%',
							            center: ['50%', '60%'],
							            data:data.maps,
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
						$("#funsionCharts_div_id").html("");
						var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
						myChart.setOption(option);
						myChart.hideLoading();  
						$("#funsionCharts_div_id").show();
						$("#container").hide();
					 
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
						option = {
							    title: {
							        text: '按月统计'
							    },
							    tooltip: {
							        trigger: 'axis'
							    },
							    legend: {
							        data:['采购金额统计']
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
							        data: data.month
							    },
							    yAxis: {
							        type: 'value'
							    },
							    series:data.line
							};
						$("#funsionCharts_div_id").html("");
					 	 var myChart = echarts.init(document.getElementById("funsionCharts_div_id"));
							myChart.setOption(option);
							myChart.hideLoading();
							$("#funsionCharts_div_id").show();
							$("#container").hide();
					},
					 
				});
		  
}
 
  $(function(){
		 option = {
				    title : {
				        text: '省市采购',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item'
				    },
				    legend: {
				        orient: 'vertical',
				        x:'left',
				        data:['iphone3']
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
				            name: '中国',
				            type: 'map',
				            mapType: 'china',
				            roam: false,
				            itemStyle:{
				                normal:{label:{show:true}},
				                emphasis:{label:{show:true}}
				            },
				            data:${data}
				        }
				         
				     
				    ]
				};
			
	 	var myChart = echarts.init(document.getElementById("container"));
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
			 
	
	
	function maps(){
		$("#funsionCharts_div_id").hide();
		$("#container").show();
		 $("#div_table").hide();
		
	}
	
	function resetQuery(){
		$("#add_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>采购需求列表
	  </h2>
   </div> 
   
   
  <div class="search_detail">
    <form id="add_form" action="${pageContext.request.contextPath}/statistic/list.html" class="mb0" method="post" >
   <input type="hidden" name="page" id="page">

	 <%--  物资类别：<select name="planType" >
	  	<option value="1" <c:if test="${inf.planType=='1'}"> selected</c:if> >货物</option>
		<option value="2" <c:if test="${inf.planType=='2'}"> selected</c:if> >工程</option>
		<option value="3" <c:if test="${inf.planType=='3'}"> selected</c:if> >服务</option>
	  </select>
	    年度： <input class="mt10" type="text" name="year" value="${year}" /> 
	   需求部门： <input class="mt10" type="text" name="department" value="${inf.department }" /> 
	
	  采购方式：
	  	<select  name="purchaseType" style="width:100px" id="select">
              				    <option value="" >请选择</option>
	                            <option value="公开招标" <c:if test="${'公开招标'==inf.purchaseType}">selected="selected"</c:if>>公开招标</option>
	                            <option value="邀请招标" <c:if test="${'邀请招标'==inf.purchaseType}">selected="selected"</c:if>>邀请招标</option>
	                            <option value="竞争性谈判" <c:if test="${'竞争性谈判'==inf.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
	                            <option value="询价采购" <c:if test="${'询价采购'==inf.purchaseType}">selected="selected"</c:if>>询价采购</option>
	                            <option value="单一来源" <c:if test="${'单一来源'==inf.purchaseType}">selected="selected"</c:if>>单一来源</option>
			                </select>
			                
	   采购机构：  <input class="mt10"  value='${inf.organization }' name="organization" type="text" > 
	   预算：  <input class="mt10"  value='${inf.budget }' name="budget" type="text" > 
	   	 <input class="btn-u"   type="submit" name="" value="查询" /> 
	   	 <input class="btn-u"   type="button" name="" value="按需求部门统计" onclick="bar(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购方式统计" onclick="pipe(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按月统计" onclick="line(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购省市统计" onclick="maps()" />  --%>
	   	 
	   	    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl"> 物资类别：</label><span>
				  	   <select name="planType" style="width: 152px;" >
				  	     <option value="" >请选择</option>
						  	<option value="1" <c:if test="${inf.planType=='1'}"> selected</c:if> >货物</option>
							<option value="2" <c:if test="${inf.planType=='2'}"> selected</c:if> >工程</option>
							<option value="3" <c:if test="${inf.planType=='3'}"> selected</c:if> >服务</option>
					   </select>
				    	</span>
				      </li>
				   <li>
				    	<label class="fl">年度：</label><span>
				  	    <input   type="text" name="year" value="${year}" /> 
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl">   需求部门：</label><span>
				  	   		<input  type="text" name="department" value="${inf.department }" /> 
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl">  采购方式：</label><span>
							<select  name="purchaseType" style="width:100px" id="select">
	              				    <option value="" >请选择</option>
		                            <option value="公开招标" <c:if test="${'公开招标'==inf.purchaseType}">selected="selected"</c:if>>公开招标</option>
		                            <option value="邀请招标" <c:if test="${'邀请招标'==inf.purchaseType}">selected="selected"</c:if>>邀请招标</option>
		                            <option value="竞争性谈判" <c:if test="${'竞争性谈判'==inf.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
		                            <option value="询价采购" <c:if test="${'询价采购'==inf.purchaseType}">selected="selected"</c:if>>询价采购</option>
		                            <option value="单一来源" <c:if test="${'单一来源'==inf.purchaseType}">selected="selected"</c:if>>单一来源</option>
				              </select>
				    	
				    	</span>
				      </li>
				    	  
				    	  
				      <li>
				    	<label class="fl">采购机构：</label><span>
				  	       <select name="organization" style="width: 152px;" >
				  	        <option value="" >请选择</option>
				  	       <c:forEach items="${org }" var="obj">
				  	      
						  	<option value="${obj.name }" <c:if test="${obj.name==inf.organization }"> selected</c:if> >${obj.name }</option>
						  </c:forEach>
					  	 </select>
				    	
				    	</span>
				      </li>
				      <li>
				    	<label class="fl"> 预算：</label><span>
				  	   		<input  type="text" name="budget" value="${inf.budget }" /> 
				    	
				    	</span>
				      </li>
				      
				      
			    	</ul>
			    	<div class="col-md-12 col-sm-12 col-xs-2 clear tc mt10">
			    	<input class="btn"   type="submit" name="" value="查询" /> 
				      <input type="button" onclick="resetQuery()" class="btn" value="重置"/>	 	
			    	</div>
		    	  	<div class="clear"></div>
   </form>
  </div>
   <div class="col-md-12 pl20 mt10">
	     	 <input class="btn-u"   type="button" name="" value="按需求部门统计" onclick="bar(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购方式统计" onclick="pipe(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按月统计" onclick="line(1)" /> 
	   	 <input class="btn-u"   type="button" name="" value="按采购省市统计" onclick="maps()" />
	  </div>
	  
	  
   <div id="div_table" class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
<!-- 		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
 -->		  <th class="info w50">序号</th>
		  <th class="info">物资类别</th>
		  <th class="info">年度</th>
		  <th class="info">需求部门</th>
		  <th class="info">采购方式</th>
		  <th class="info">采购机构</th>
		  <th class="info">预算（万元）</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
<%-- 			  <td class="tc w30"><input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""></td>
 --%>			  <td class="tc w50" onclick="view('${obj.planNo }')" >${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td class="tc" onclick="view('${obj.planNo }')">
	<%-- 		  <c:if test="${obj.planType=='1'}">
					  货物
			  </c:if>
			  <c:if test="${obj.planType=='2'}">
					工程
			  </c:if>
			  <c:if test="${obj.planType=='3'}">
					服务
			  </c:if> --%>
			  <c:forEach items="${goods }"  var="gd" >
					  <c:if test="${gd.id==obj.planType }">
						  ${gd.name }
					  </c:if>
				  </c:forEach>
				  
			  </td>
			  <td class="tc" onclick="view('${obj.planNo }')"><fmt:formatDate value="${obj.createdAt }" pattern="yyyy" /></td>
			  <td class="tl pl20" onclick="view('${obj.planNo }')">${obj.department }</td>
			  <td class="tl pl20" onclick="view('${obj.planNo }')">
				  <c:forEach items="${kind }"  var="kind" >
					  <c:if test="${kind.id==obj.purchaseType }">
						  ${kind.name }
					  </c:if>
				  </c:forEach>
			  </td>
			  <td class="tl pl20" onclick="view('${obj.planNo }')"> 
			  <c:forEach items="${org }" var="ss">
								  <c:if test="${ss.id==obj.organization }">${ss.name} </c:if> 
							</c:forEach>
			  
			  </td>
			  <td class="tr pr20" onclick="view('${obj.planNo }')">
		 	<fmt:formatNumber>${obj.budget }</fmt:formatNumber>
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>

 	<div id="funsionCharts_div_id" style="width:800px;height:400px;display: none;margin: 0 auto;"></div>
 
    <div id="container" style="display: none;height: 700px;min-width: 310px;margin: 0 auto;width: 800px;"></div>
   
   
	 </body>
</html>
