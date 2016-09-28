<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>登记质检报告</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
  </head>
    <script type="text/javascript">
    function selectByCode(){
    		var code= $(".contract_code").val();
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=basePath%>purchaseContract/selectByCode.do?code="+code,
				success:function(json){
  					 $(".contract_id").val(json.id);
  					 $(".contract_name").val(json.name);
  					 $(".supplier_id").val(json.supplier.id);	
  					 $(".supplier_name").val(json.supplier.supplierName);			
	       		}
	       	});
	}
    
    function addAttach(){
		html="<input id='pic' type='file' class='toinline span2' name='report'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		$("#uploadAttach").append(html);
	}
    function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">登记质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<form action="<%=basePath %>pqinfo/save.html" method="post" enctype="multipart/form-data">
   		<div class="headline-v2">
   			<h2>登记质检报告</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="contract_id" name="contract.id">
		     <li class="col-md-6  p0 ">
			   <span class="">合同编号：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" name="contract_code" id="contract_code" type="text" onblur="selectByCode()">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同名称：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name"type="text"  readonly="readonly" >
       			</div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">供应商组织机构代码：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" type="text"  readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text"  readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">项目类别：</span>
			   <div class="btn-group ">
		        	<select id="temType" name ="projectType" class="w220" >
						<option value="0">请选择</option>
	  				</select> 
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检单位：</span>
		        <div class="input-append ">
		        	<input class="span2" name="unit" type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="fl">质检类型：</span>
			   <div class="btn-group ">
		        	<select id="temType" name =type class="w220" >
						<option>请选择</option>
						<option value="首件检验">首件检验</option>
						<option value="生产验收">生产验收</option>
						<option value="出厂验收">出厂验收</option>
						<option value="到货验收">到货验收</option>
	  				</select> 
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检地点：</span>
		        <div class="input-append ">
		        	<input class="span2" name="place" type="text">
       			</div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="">质检日期：</span>
			   <div class="input-append">
		        <input class="span2 Wdate" name="dateString" type="text"  onfocus="WdatePicker({isShowWeek:true})">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检人员：</span>
		        <div class="input-append ">
		        	<input class="span2" name="inspectors" type="text">
       			</div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">质检情况：</span>
			   <div class="input-append">
		        <input class="span2" name="condition" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="fl">质检结论：</span>
		        <div class="btn-group ">
		        	<select id="temType" name ="conclusion" class="w220" >
						<option value="" >请选择</option>
						<option value="合格">合格</option>
						<option value="不合格">不合格</option>
	  				</select> 
       			</div>
			 </li>
			 <li class="col-md-12  p0 ">
			   <span class="fl">详细情况：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <input class="span2" name="detail" type="text">
		       </div>
			 </li>
   		</ul>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">质检报告：</span>
			   <div class="fl " id="uploadAttach" >
			  	 <input id="pic" type="file" class="toinline" name="attaattach"/>
		       </div>
			 </li>
		</ul>
  		<div  class="col-md-12 ml185">
   			<div class="fl padding-10">
    			<button class="btn btn-windows save" type="submit">新增</button>
    			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
			</div>
  		</div>
  		
  	</form>
 </div>
 
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>
</body>
</html>
