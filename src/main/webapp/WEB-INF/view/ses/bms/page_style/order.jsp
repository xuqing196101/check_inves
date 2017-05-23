<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
 <jsp:include page="backend_common.jsp"></jsp:include>	

</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">列表页面</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
   <div class="container">
   <div class="ml20 mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
   <div class="headline-v2">
     <h2>我的协议供货订单</h2>
   </div>   
     <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">合同名称：</label>
			<input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">合同编号：</label>
			<input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">供应商名称：</label>
			<input type="text" id="topic" class=""/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>

     <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">合同名称：</label><input type="text" id="topic" class=""/>
	      </li>

    	  <li>
	    	<label class="fl">合同编号：</label><input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">供应商名称：</label><input type="text" id="topic" class=""/>
	      </li> 
	      <li>
	    	<label class="fl">下拉选择框二：</label>
	    	  <select>
	    	    <option>选项一</option>
	    	    <option>选项二</option>
	    	    <option>选项三</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">供应商名称：</label>
	    	<input type="text" id="topic" class=""/>
	      </li>
    	  <li>
	    	<label class="fl">供应商名称：</label>
			<input type="text" id="topic" class=""/>
	      </li>
	      <li>
	    	<label class="fl">下拉选择框一：</label>
	    	  <select class="w178">
	    	    <option>选项一</option>
	    	    <option>选项二</option>
	    	    <option>选项三</option>
	    	  </select>
	      </li>
	
    	</ul>

           <div class="col-md-12 clear tc mt10">
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button> 
	    	</div>
    	  <div class="clear"></div>
       </form>
     </div>
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
	<button class="btn btn-windows print" type="submit">打印</button>
	<button class="btn" type="submit">分配</button>
	<button class="btn btn-windows input" type="submit">导入</button>
	<button class="btn btn-windows output" type="submit">导出</button>
	<button class="btn btn-windows add" type="submit">新增合同</button>
	<button class="btn btn-windows cancel" type="submit">取消</button>
	<button class="btn btn-windows check_pass" type="submit">审核通过</button>
	<button class="btn btn-windows check_back" type="submit">退回重报</button>
   </div>
   <div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
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
		  <td><a href="javascript:void(0);">2013-11-20  台式机采购项目</a></td>
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
		  <td><a href="javascript:void(0);">2013-11-20  台式机采购项目</a></td>
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
		  <td><a href="javascript:void(0);">2013-11-20  台式机采购项目</a></td>
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
		  <td><a href="javascript:void(0);">2013-11-20  台式机采购项目</a></td>
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
