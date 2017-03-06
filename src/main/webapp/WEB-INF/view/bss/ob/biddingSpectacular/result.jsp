<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价结果查询页面</title>
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
     	<h2>竞价标题：测试-263842312346</h2>
	</div> 
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn" type="submit">打印结果</button>
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
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX</a></td>
		  <td class="tc">4000</td>
		  <td class="tc">4000</td>
		  <td class="tc">200</td>
		  <td class="tc">800，000.00</td>
		  <td class="tc">已确认</td>
		</tr>
		<tr>
		  <td class="tc w50">2</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX</a></td>
		  <td class="tc">4000</td>
		  <td class="tc">4100</td>
		  <td class="tc">150</td>
		  <td class="tc">600，000.00</td>
		  <td class="tc">已确认</td>
		</tr>
		<tr>
		  <td class="tc w50">3</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX</a></td>
		  <td class="tc">4200</td>
		  <td class="tc">4000</td>
		  <td class="tc">100</td>
		  <td class="tc">400，000.00</td>
		  <td class="tc">已确认</td>
		</tr>
		</table>
    </div>
      <!-- <div id="pagediv" align="right"></div> -->
   </div>
</body>
</html>