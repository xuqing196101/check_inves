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
	}
  </script>
  <body>
  
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li class="active"><a href="#">产品质量管理</a></li><li class="active"><a href="#">质检信息详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
  <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    	<div>
	    	<div class="headline-v2">
	   			<h2>质检信息详情</h2>
	   		</div>
	   		
	   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">合同编号：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" name="contract_code" id="contract_code" type="text" value = '${pqinfo.contract.code}' readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同名称：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="contract_name" type="text" value = '${pqinfo.contract.name}' readonly="readonly">
       			</div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">供应商组织机构代码：</span>
		        <div class="input-append ">
		        	<input class="span2" name="procurementId" type="text"  value = '${pqinfo.contract.supplierPurId}' readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="supplier_name" type="text" value = '${pqinfo.contract.supplierDepName}' readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">项目类别：</span>
			   <div class="btn-group ">
		        <input class="span2" name="projectType" type="text" value = '${pqinfo.projectType}' readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检单位：</span>
		        <div class="input-append ">
		        	<input class="span2" name="unit" type="text" value = '${pqinfo.unit}' readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="fl">质检类型：</span>
			   <div class="btn-group ">
		        	<input class="span2" name="type" type="text" value = '${pqinfo.type}' readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检地点：</span>
		        <div class="input-append ">
		        	<input class="span2" name="place" type="text" value = '${pqinfo.place}' readonly="readonly">
       			</div>
			 </li>
			<li class="col-md-6  p0 ">
			   <span class="">质检日期：</span>
			   <div class="input-append">
		        <input class="span2" name="date" type="text"  value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>"readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">质检人员：</span>
		        <div class="input-append ">
		        	<input class="span2" name="inspectors" type="text" value = '${pqinfo.inspectors}' readonly="readonly">
       			</div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">质检情况：</span>
			   <div class="input-append">
		        <input class="span2" name="condition" type="text" value = '${pqinfo.condition}' readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="fl">质检结论：</span>
		        <div class="btn-group ">
		         <input class="span2" name="conclusion" type="text" value = '${pqinfo.conclusion}' readonly="readonly">
       			</div>
			 </li>
			 <li class="col-md-12  p0 ">
			   <span class="fl">详细情况：</span>
			   <div class="col-md-12 pl200 fn mt5 pwr9">
		        <input class="span2" name="detail" type="text" value = '${pqinfo.detail}' readonly="readonly">
		       </div>
			 </li>
   		</ul>
   		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-6  p0 ">
			   <span class="">质检报告：</span>
			   <div class="fl">
		        <button type="button" onclick="showPic('${pqinfo.report}','质检报告')">质检报告</button><img class="hide" id="photo" src="${pqinfo.report}"/>
		       </div>
			 </li>
		</ul>
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 ml185">
   <div class="fl padding-10">
    <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
	  	 
	</div>  	
     
     </div>
     </div>
     <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content="${templet.content}";
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>
