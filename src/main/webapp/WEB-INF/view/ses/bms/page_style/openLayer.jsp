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
	<script type="text/javascript">
		function openDiv(){
				layer.open({
					  type: 1,
					  title: '新增明细',
					  skin: 'layui-layer-rim',
					  shadeClose: true,
					  area: ['580px','510px'],
					  content: $("#openDiv")
					});
		}
		function cancel(){
			layer.closeAll();
		}
	</script>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">弹出框页面</a></li> 
		</ul>
	  </div>
   </div>
   <div class="mt10">
	   <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
  <h4 id="tipsId"><a  href="javascript:openDiv();">弹出框,点我</a></h4>
  
 	  <div id="openDiv" class="dnone layui-layer-wrap" >
		  <div class="drop_window">
			  <ul class="list-unstyled">
			    <li class="mt10 col-md-12 col-sm-12 col-xs-12 pl15">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">物资名称</label>
				  <span class="col-md-12 col-sm-12 col-xs-12 input-append input_group">
				   <input id="citySel4" readonly="" name="categoryName" value="" class="title" type="text">
				  </span>
	            </li>
			    <li class="col-md-6 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">编号</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="planNo" name="planNo" type="text">
				  </span>
	            </li>
			    
				<li class="col-md-6 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 padding-left-5">交付时间</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="givetime" name="givetime" value="" type="text">
                  </span>
	            </li>
			    <li class="col-md-6 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 padding-left-5">品牌商标</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="bra" name="bra" value="" type="text">
                  </span>
	            </li>
			    <li class="col-md-6 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">规格型号</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="model" name="model" value="" type="text">
                  </span>
	            </li> 
			    <li class="col-md-3 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">计量单位</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="unit" name="unit" value="" type="text">
                  </span>
	            </li>
				<li class="col-md-3 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="purNum" name="purNum"  type="text">
                  </span>
	            </li>
			    <li class="col-md-3 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="univalent" name="univalent" value="" type="text">
                  </span>
	            </li>
			    <li class="col-md-3 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合计</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                   <input maxlength="11" id="purBudgetSum" name="purBudgetSum" value="" readonly="readonly" type="text">
                  </span>
	            </li> 
			    <li class="col-md-12 col-sm-6 col-xs-12">
	    	      <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</label>
	    	      <span class="col-md-12 col-sm-12 col-xs-12 p0">
                  	<textarea id="remarks" name="remarks" class="w100p h80 p0" rows="3" cols="1"></textarea>
                  </span>
	            </li>
			    
	            <div class="clear"></div>
			  </ul>
              <div class="tc mt10 col-md-12 col-sm-12 col-xs-12">
                <input class="btn" id="inputb" name="addr" onclick="alert('自己实现')" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		   </div>
</body>
</html>
