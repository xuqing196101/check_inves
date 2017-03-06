<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>确认结果页面</title>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">确认结果</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div> 
    <!-- 表格开始 -->
    <div class="container container_box">
    <h2 class="count_flow">排名：第一名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态：中标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中标比例:<input id="" name="" value="50" type="text" class="w5 mb0 ">%</h2>
    <h2 class="count_flow">确认结束时间：2016-1-1 12：12：12</h2>
     <div>
     <div class="clear total f22"><span class="fl block">基本数量确认：</span><h2 class="count_flow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认成交&nbsp;&nbsp;<input id="" name="" value="50" type="text" class="w5 mb0 ">%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认倒计时：0小时5分钟12秒</h2></div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">20</td>
		  <td class="tc">100</td>
		  <td class="tc">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">20</td>
		  <td>200</td>
		  <td class="tc">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc">10</td>
		  <td>300</td>
		  <td class="tc">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
	</table>
  </div>
  </div>
  <div>
     <div class="clear total f22"><span class="fl block">第二轮确认：</span><h2 class="count_flow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认成交&nbsp;&nbsp;<input id="" name="" value="20" type="text" class="w5 mb0 ">%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二轮确认倒计时：0小时5分钟12秒</h2></div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">20</td>
		  <td class="tc">100</td>
		  <td class="tc">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">20</td>
		  <td>200</td>
		  <td class="tc">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc">10</td>
		  <td>300</td>
		  <td class="tc">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
	</table>
  </div>
  </div>
  <div class="star_red">规则1、第一轮确认如果都按比例成交，则没有第二轮确认，如果不是按比例成交，则有第二轮确认，第一轮正在确认的时候不显示<br/>第二轮数据，只有所有供应商第一轮确认完毕后，才有第二轮确认。<br/>
						规则2、自动生成成交比例（可修改），修改后成交数量和总价发生变化，未中标的的显示未中标，不显示成交比例，未中标的只能<br/>看到已中标明细，未中标的只有返回按钮。
  </div>
  <div class="col-md-12 clear tc mt10">
  <button class="btn" type="submit">接受</button>
  <button class="btn" type="submit">放弃</button>
  </div>
  </div>
</body>
</html>