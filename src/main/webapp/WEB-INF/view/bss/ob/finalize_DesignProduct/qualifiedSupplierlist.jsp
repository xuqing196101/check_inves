<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商列表页面</title>

</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li><li class="active"><a href="javascript:void(0)">供应商列表</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 供应商列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
		<li>
			<label class="fl">供应商证书状态：</label>
			<select class="w178">
				<option>-请选择-</option>
	    	    <option>未过期</option>
	    	    <option>已过期</option>
			</select>
		</li>
		<button type="button" onclick="query()" class="btn">查询</button>
		<button type="reset" class="btn">重置</button>  	
		</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
	</div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input alt="" type="checkbox"></th>
		  <th class="w50 info">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">证书有效期至</th>
		  <th class="info">是否过期</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">1</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX公司</a></td>
		  <td class="tc">2018-1-1</td>
		  <td class="tc">未过期</td>
		</tr>
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">2</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX公司</a></td>
		  <td class="tc">2018-1-1</td>
		  <td class="tc">未过期</td>
		</tr> 
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">3</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX公司</a></td>
		  <td class="tc">2018-1-1</td>
		  <td class="tc">未过期</td>
		</tr> 
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">4</td>
		  <td><a href="javascript:void(0)">XXXXXXXXXXXXXXXXXXXXXX公司</a></td>
		  <td class="tc">2018-1-1</td>
		  <td class="tc">已过期</td>
		</tr> 
		</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>