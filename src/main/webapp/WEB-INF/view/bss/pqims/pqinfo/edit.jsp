<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看质检信息</title>
    
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

  </head>
<script src="<%=basePath%>public/layer/layer.js"></script>
   <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  function showPic(url,name){
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '516px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $("#photo")
			});
	};
	
	$(function(){
		$("#projectType").val('${pqinfo.projectType}');
		$("#type").val('${pqinfo.type}');
		$("#conclusion").val('${pqinfo.conclusion}');
	});
  	
	function selectByCode(){
		var code= $(".contract_code").val();
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"<%=basePath%>purchaseContract/selectByCode.do?code="+code,
			success:function(json){
					 $(".contract_id").val(json.id);
					 $(".contract_name").val(json.name);
					 $(".supplier_name").val(json.supplier.supplierName);
					 $(".procurementId").val(json.supplier.id);
					 
       		}
       	});
};

	
  </script>
<body>
 
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   		<li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">产品质量管理</a></li><li class="active"><a href="#">修改质检报告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   		<form action="<%=basePath%>pqinfo/update.html" method="post"  enctype="multipart/form-data">
   		<div class="headline-v2">
   			<h2>修改质检报告</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="id" name="id" value = '${pqinfo.id}'>
   			<input type="hidden" class="contract_id" name="contract.id" value = '${pqinfo.contract.id}'>
		     <li class="col-md-6  p0 ">
			   <span class="">合同编号：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" name="contract_code" id="contract_code" type="text" value = '${pqinfo.contract.code}' onblur="selectByCode()">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同名称：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="contract_name" value = '${pqinfo.contract.name}'  type="text"  readonly="readonly">
       			</div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">供应商组织机构代码：</span>
		        <div class="input-append ">
		        	<input class="span2 procurementId" name="procurementId"  value = '${pqinfo.contract.supplierPurId}' type="text"  readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="supplier_name" value = '${pqinfo.contract.supplierDepName}' type="text"  readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">项目类别：</span>
			   <div class="btn-group ">
		        	<select id="projectType" name ="projectType" class="w220" >
						<option value="0">请选择</option>
	  				</select> 
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检单位：</span>
		        <div class="input-append ">
		        	<input class="span2" name="unit" value = '${pqinfo.unit}'  type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="fl">质检类型：</span>
			   <div class="btn-group ">
		        	<select id="type" name =type class="w220" >
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
		        	<input class="span2" name="place" value = '${pqinfo.place}'  type="text">
       			</div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="">质检日期：</span>
			   <div class="input-append">
		        <input class="span2" name="date" value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>"  type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检人员：</span>
		        <div class="input-append ">
		        	<input class="span2" name="inspectors" value = '${pqinfo.inspectors}'  type="text">
       			</div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">质检情况：</span>
			   <div class="input-append">
		        <input class="span2" name="condition" value = '${pqinfo.condition}'  type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="fl">质检结论：</span>
		        <div class="btn-group ">
		        	<select id="conclusion" name ="conclusion" class="w220" >
						<option value="" >请选择</option>
						<option value="合格">合格</option>
						<option value="不合格">不合格</option>
	  				</select> 
       			</div>
			 </li>
			 <li class="col-md-12  p0 ">
			   <span class="fl">详细情况：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <input class="span2" name="detail" value = '${pqinfo.detail}'  type="text">
		       </div>
			 </li>
   		</ul>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">质检报告：</span>
			   <div class="fl mt5">
		        <button id="button" type="button" onclick="showPic('${pqinfo.report}','质检报告')">质检报告</button>
		        <img class="hide" id="photo" src="${pqinfo.report}"/>
		        <div class="mt5"><input type="file" name="attaattach" value="重新上传"/></div>
		         
		       </div>
			 </li>
		</ul>
  		<div  class="col-md-12 ml185">
   			<div class="fl padding-10 ">
    			<button class="btn btn-windows reset" type="submit">更新</button>
    			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
			</div>
  		</div>
  		</form>
 	</div>

</body>
</html>
