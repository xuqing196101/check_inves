<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>投诉记录列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
	function publish(){
		window.location.href = "${pageContext.request.contextPath }/onlineComplaints/publish.do";
	}
</script>
</head>
<body>
     
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">投诉记录查询</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div> 
	<div class="container">
	<div class="headline-v2">
		<h2>投诉记录列表</h2>
	 </div>
	<div class="search_detail">
	<form action="" method="post" class="mb0">
	   <ul class="demand_list">
				<li>
			    	<label class="fl">投诉人名称：</label><span><input type="text" id="name" name="name" class=""/></span>
			    </li>
		  		<li>
			    	<label class="fl">状态：</label>
			    	<span>
			    		<select id="status" name="status">
				  			<option value="">请选择</option>
				  			<option value="0">单位</option>
				  			<option value="1">个人</option>
				  		</select>
			    	</span>
			    </li>
		  		<li>
			    	<label class="fl">投诉对象：</label><span><input type="text" id="contractCode" name="contractCode" class=""/></span>
			    </li>
			  	<button class="btn" type="submit">查询</button>
	  		</ul>
	  		<div class="clear"></div>
	  </form>
	  </div>
	  	<div class="col-md-12 pl20 mt10">
				<button class="btn" type="button" onclick="publish()">公布</button>
		   </div>
       <div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th>投诉人名称</th>
							<th>投诉人类型</th>
							<th>投诉对象</th>
							<th width="25%">投诉事项</th>
							<th>处理情况</th>
						</tr>
					</thead>
					<tbody>
						<tr class="tc">
							<td class="tc">1</td>
							<td class="tc">XXXXXXXXXXXX</td>
							<td class="tc">xhxhxhxhxhx</td>
							<td class="tc">dddsdadad</td>
							<td class="tl">dasdadasda</td>
							<td class="tc">adasdasdasd</td>
						</tr>
					</tbody>
				</table>
	
	</div></div>

</body>
</html>