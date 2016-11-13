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
					  area: ['496px'],
					  content: $("#openDiv")
					});
		}
		function cancel(){
			layer.closeAll();
		}
	</script>
</head>
  
<body>
  <h4 id="tipsId"><a  href="javascript:openDiv();">弹出框,点我</a></h4>
  
 	  <div id="openDiv" class="dnone layui-layer-wrap" >
		  <div class="drop_window">
			  <ul class="demand_list list-unstyled">
			    <li class="mt10 col-md-12 p0">
	    	      <label class="col-md-12 padding-left-15">物资名称：</label>
				  <span class="col-md-12">
				   <input id="citySel4" readonly="" name="categoryName" value="" class="title col-md-12" type="text">
				  </span>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5">编号：</label>
	    	      <span>
                   <input maxlength="11" id="planNo" name="planNo" type="text" class="col-md-12 p0">
				  </span>
	            </li>
			    
				<li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5">交付时间：</label>
	    	      <span class="col-md-12 p0">
                   <input maxlength="11" id="givetime" name="givetime" value="" type="text" class="col-md-12 p0">
                  </span>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5">品牌商标：</label>
	    	      <span class="col-md-12 p0">
                   <input maxlength="11" id="bra" name="bra" value="" type="text" class="col-md-12 p0">
                  </span>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5">规格型号：</label>
                   <input maxlength="11" id="model" name="model" value="" type="text" class="col-md-12 p0">
	            </li> 
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5">计量单位：</label>
                   <input maxlength="11" id="unit" name="unit" value="" type="text" class="col-md-12 p0">
	            </li>
				<li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5">数量：</label>
                   <input maxlength="11" id="purNum" name="purNum" onblur="sum2()" type="text"class="col-md-12 p0">
	            </li>
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5">单价：</label>
                   <input maxlength="11" id="univalent" onblur="sum1()" name="univalent" value="" type="text" class="col-md-12 p0">
	            </li>
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5">合计：</label>
                   <input maxlength="11" id="purBudgetSum" name="purBudgetSum" value="" readonly="readonly" type="text" class="col-md-12 p0">
	            </li> 
			    <li class="col-md-12">
	    	      <label class="col-md-12 padding-left-5">备注：</label>
                  <textarea id="remarks" name="remarks" class="textAreaSize col-md-12 h80 p0" rows="3" cols="1"></textarea>
	            </li>
			    
	            <div class="clear"></div>
			  </ul>
              <div class="tc mt20 col-md-12">
                <input class="btn" id="inputb" name="addr" onclick="alert('自己实现')" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		   </div>
</body>
</html>
