<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
 <jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>	

</head>
<body>

<!-- 我的订单页面开始-->
   <div class="container">
   <div class="headline-v2">
     <h2>测试专用</h2>
   </div>   
<!-- 表格开始-->
   <div class="content">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
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
		
	</table>

   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>
