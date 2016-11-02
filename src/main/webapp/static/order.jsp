<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">协议采购</a></li><li class="active"><a href="#">我的订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
   <div class="container">
   <div class="headline-v2">
     <h2>我的协议供货订单</h2>
   </div>   
     <h2 class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">合同名称：</label><span><input type="text" id="topic" class=""/></span>
	      </li>
    	  <li>
	    	<label class="fl">合同编号：</label><span><input type="text" id="topic" class=""/></span>
	      </li>
    	  <li>
	    	<label class="fl">供应商名称：</label><span><input type="text" id="topic" class=""/></span>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>

<!-- 表格开始-->
   <div class="col-md-12 pl20 mt10">
    <button class="btn btn-windows add" type="submit">新增</button>
    <button class="btn btn-windows withdraw" type="submit">撤回</button>
	<button class="btn btn-windows edit" type="submit">修改</button>
	<button class="btn btn-windows git" type="submit">提交</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<button class="btn btn-windows back" type="submit">返回</button>
	<button class="btn btn-windows check" type="submit">审核</button>
	<button class="btn btn-windows apply" type="submit">发布</button>
	<button class="btn" type="submit">分配</button>
	<button class="btn btn-windows input" type="submit">导入</button>
	<button class="btn btn-windows output" type="submit">导出</button>
	<button class="btn btn-windows ht_add" type="submit">新增合同</button>
	<button class="btn btn-windows cancel" type="submit">取消</button>
   </div>
   <div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="w30 info"><input type="checkbox" alt=""></th>
		  <th class="w50 info">序号</th>
		  <th class="info">凭证编号</th>
		  <th class="info">名称</th>
		  <th class="info">总金额（元）</th>
		  <th class="info">
			<div class="">
              <select class="form-control input-lg">
                 <option value="">全部状态</option>
              </select>
            </div>
		  </th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">1</td>
		  <td>BG-XY-IT20131120106054</td>
		  <td><a href="#">2013-11-20  台式机采购项目</a></td>
		  <td class="tc">¥40,000.00</td>
		  <td>
		  <div class="col-md-12 padding-0">
		  <span class="fl padding-5">暂存</span>
		  <div class="progress w55p fl margin-left-0">
             <div class="progress-bar progress-bar-danger" role="progressbar" 
                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
                 style="width:10%;"> 
             </div> 
          </div>
		  <span class="fl padding-5">10%</span>
		  </div>
		  </td>
		</tr>
		<tr>
		  <td class="tc w30"><input type="checkbox" alt=""></td>
		  <td class="tc w50">1</td>
		  <td>BG-XY-IT20131120106054</td>
		  <td><a href="#">2013-11-20  台式机采购项目</a></td>
		  <td class="tc">¥40,000.00</td>
		  <td>
		  <div class="col-md-12 padding-0">
		  <span class="fl padding-5">暂存</span>
		  <div class="progress w55p fl margin-left-0">
             <div class="progress-bar progress-bar-danger" role="progressbar" 
                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
                 style="width:10%;"> 
             </div> 
          </div>
		  <span class="fl padding-5">10%</span>
		  </div>
		 
		  </td>
		</tr>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc">1</td>
		  <td>BG-XY-IT20131120106054</td>
		  <td><a href="#">2013-11-20  台式机采购项目</a></td>
		  <td class="tc">¥40,000.00</td>
		  <td>
		  <div class="col-md-12 padding-0">
		  <span class="fl padding-5">暂存</span>
		  <div class="progress w55p fl margin-left-0">
             <div class="progress-bar progress-bar-danger" role="progressbar" 
                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
                 style="width:10%;"> 
             </div> 
          </div>
		  <span class="fl padding-5">10%</span>
		  </div>
		 
		  </td>
		</tr>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc">1</td>
		  <td>BG-XY-IT20131120106054</td>
		  <td><a href="#">2013-11-20  台式机采购项目</a></td>
		  <td class="tc">¥40,000.00</td>
		  <td>
		  <div class="col-md-12 padding-0">
		  <span class="fl padding-5">暂存</span>
		  <div class="progress w55p fl margin-left-0">
             <div class="progress-bar progress-bar-danger" role="progressbar" 
                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
                 style="width:10%;"> 
             </div> 
          </div>
		  <span class="fl padding-5">10%</span>
		  </div>
		  </td>
		</tr>
	</table>

   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
