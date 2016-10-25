<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>审价结果统计</title>
    
    <script src="<%=basePath%>public/echarts/echarts.js"></script>
    <script src="<%=basePath%>public/echarts/theme/vintage.js"></script>
    <script src="<%=basePath%>public/echarts/theme/macarons.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
   
<script type="text/javascript">
$(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${list.total}",
		    startRow:"${list.startRow}",
		    endRow:"${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '<%=basePath%>statistical/view.html?page='+e.curr;
		        }
		    }
		});
});

function showCharts(){
		var myChart = echarts.init(document.getElementById("chart"));
		$.getJSON("<%=basePath %>statistical/echarts.do", function(json) {
			console.log(json);  
			myChart.setOption(json);
			window.onresize = myChart.resize;
			myChart.hideLoading();
		});
       
}

function on(){
	showCharts();
}
</script>    

  </head>
  
  <body>
  
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">单一来源审价</a></li><li><a href="#">审价结果统计</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
    <div class="container mt10">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
   
   <div class="container">
     <div class="p10_25">
     <h2 class="padding-10 border1">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">采购机构：</label><span><input type="text" id="topic" class=""/></span>
	      </li>
    	  <li class="fl">
	    	<label class="fl">合同名称：</label><span><input type="text" id="topic" class=""/></span>
	      </li>
    	  <li class="fl">
	    	<label class="fl">合同编号：</label><span><input type="text" id="topic" class=""/></span>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
	  </h2>
	 </div>
	</div>
  
<div class="container content height-350">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgwhite">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">统计列表</a></li>
            <li class="" onclick="on()"><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">统计分析图</a></li>
          </ul>
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in height-450" id="tab-1">
              <div class=" margin-bottom-0 mt10">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th class="info">序号</th>
							<th class="info">采购机构</th>
							<th class="info">合同名称</th>
							<th class="info">合同编号</th>
							<th class="info">合同金额(万元)</th>
							<th class="info">审价金额</th>
							<th class="info">审减百分比</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach  items="${list.list}" var="statis" varStatus="vs">
							<tr>
								<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<td>${statis.purchaseDepName }</td>
								<td>${statis.name }</td>
								<td>${statis.code }</td>
								<td class="tc">${statis.money }</td>
								<td class="tc">${statis.auditMoney }</td>
								<td class="tc">${statis.subtract }</td>
							</tr>
						</c:forEach>
					</tbody>
			   </table>
              </div>
              <div id="pagediv" align="right"></div>
            </div>
            
            
            <div class="tab-pane fade height-450" id="tab-2">
              <div id="dcDataUseStatisticContainer" class="margin-bottom-0 categories">
					<div id="chart" class="icharts" style="width:800px; height:500px;">
					</div>
              </div>
            </div>
			
		</div> 
     </div>
  </div>
</div>
</div>
   
   
  		
  		
  </body>
</html>
