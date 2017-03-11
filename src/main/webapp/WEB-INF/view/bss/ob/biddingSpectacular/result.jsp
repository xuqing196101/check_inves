<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价结果查询页面</title>
	
	<script type="text/javascript">
		function printResult(){
			window.location.href="${pageContext.request.contextPath}/ob_project/printResult.html?id="+'${id}';
		}
		
		/* 分页 */
		$(function() {
		    laypage({
		      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		      pages : "${info.pages}", //总页数
		      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		      skip : true, //是否开启跳页
		      total : "${info.total}",
		      startRow : "${info.startRow}",
		      endRow : "${info.endRow}",
		      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
		      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
		        return "${info.pageNum}";
		      }(),
		      jump : function(e, first) { //触发分页后的回调
	        	if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		location.href = "${pageContext.request.contextPath }/ob_project/findBiddingResult.html?id="+${info.list[0].projectId}+"&&page=" + e.curr;
		        }
		      }
		    });
		  });
		
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
       <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价看板</a></li><li class="active"><a href="javascript:void(0)">竞价结果查询</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 添加供应商列表页面开始 -->
<div class="container">
	<div class="headline-v2">
     	<h2>竞价标题：${projectName }</h2>
	</div> 
	<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<span><font size="3">供应商确认中标数量总量为450，预定采购数量为500，剩余采购数量为50.</font></span>
		<button class="btn" onclick="printResult()">打印结果</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w50 info">名次</th>
		  <th class="info">供应商名称</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交数量</th>
		  <th class="info">成交总价（元）</th>
		  <th class="info">操作状态</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w50">1</td>
		  <td class="tc">4000</td>
		  <td class="tc">4000</td>
		  <td class="tc">4000</td>
		  <td class="tc">200</td>
		  <td class="tc">800，000.00</td>
		  <td class="tc">已确认</td>
		</tr>
		</table>
    </div>
      <!-- <div id="pagediv" align="right"></div> -->
    <div align="left">
  		<button class="btn btn-windows back" type="button"  onclick="history.go(-1)">返回</button>
 	</div>
   </div>
</body>
</html>