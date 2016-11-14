<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
  </head>
  
  <body>
  
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">论坛管理</a></li><li class="active"><a href="#">版块管理</a></li><li class="active"><a href="#">版块详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
  <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">须知文档详情</a></li>
            </ul>
            <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
                <table class="table table-bordered">
                 <tbody>
                     <tr>
	                  <td class="bggrey ">须知文档名称：</td>
	                  <td>${noticeDocument.name}</td>
	                  <td class="bggrey ">须知文档类型：</td>
	                  <td>${noticeDocument.docType}</td>
	                 </tr>
	                 <tr>
	                  <td class="bggrey ">创建时间：</td>
	                  <td><fmt:formatDate value='${noticeDocument.createdAt}' pattern='yyyy-MM-dd'/></td>
	                  <td class="bggrey ">修改时间：</td>
	                  <td><fmt:formatDate value='${noticeDocument.updatedAt}' pattern='yyyy-MM-dd'/></td>
	                 </tr> 
                 </tbody>
                 </table>
                 <h2 class="count_flow jbxx">须知文档内容</h2>
                 <div class="col-md-12  fn mt5 pwr9">
                     <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
                    <!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
                </div>
                 </div>
                 </div>
	<!-- 底部按钮 -->			          
  <div class="col-md-12 tc">
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
    var content='${noticeDocument.content}';
	ue.ready(function(){
  		ue.setContent(content);    
  		ue.setDisabled([]);
	});
    
</script>
  </body>
</html>
