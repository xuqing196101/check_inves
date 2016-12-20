<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
 <script type="text/javascript">
  	/** 全选全不选 */
	$(function(){
		
			$("#docType").val('${noticeDocument.docType}');
		
		
	});
  </script>
  </head>
<body>
 
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   		<li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">支撑系统</a></li><li><a href="javascript:void(0)">后台管理</a></li><li class="active"><a href="javascript:void(0)">修改须知文档</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container container_box">
   		<form action="${pageContext.request.contextPath}/noticeDocument/update.do" method="post">
   		<div>
   		<div class="headline-v2">
             <h2>修改须知文档</h2>
           </div> 
   		<ul class="ul_list">
   		<input class="span2" name="id" type="hidden" value="${noticeDocument.id}">
     		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档名称</span>
               <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="input_group" name="name" type="text" value="${noticeDocument.name}">
		        	 <span class="add-on">i</span>
		        	<div id="contractCodeErr" class="cue">${ERR_name}</div>
		       </div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档类型</span>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
                    <select id="docType" name =docType >
                        <option value="-请选择-">-请选择-</option>
                        <option value="供应商须知文档">供应商须知文档</option>
                        <option value="专家须知文档">专家须知文档</option>
                    </select>
                    <div id="contractCodeErr" class="cue">${ERR_docType}</div>
                  </div>
             </li>
		       <li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档内容</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
	  				 <script id="editor" name="content" type="text/plain" class=""></script>
	  				 <div id="contractCodeErr" class="clear red">${ERR_content}</div>
        			<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
       			</div>
			 </li> 
	 		
			 
   			</ul>
  		<div  class="col-md-12 col-sm-12 col-xs-12 tc">
    		<button class="btn btn-windows edit" type="submit">更新</button>
    		<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  		</div>
  		</div>
  		</form>
 	</div>
 
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content='${noticeDocument.content}';
	ue.ready(function(){
  		ue.setContent(content);    
	});
    
</script>
</body>
</html>
