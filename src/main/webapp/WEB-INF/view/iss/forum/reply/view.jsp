<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>        
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >评论管理</a></li><li class="active"><a >评论详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">回复详情</a></li>
            </ul>
            <div class="tab-content padding-top-20 over_hideen">
	        <div class="tab-pane fade active in" id="tab-1">
	        <h2 class="count_flow jbxx">基本信息</h2>
	            <table class="table table-bordered">
	            <tbody>
	            <%-- <tr>
	                <td class="bggrey ">所属版块：</td>
	                <td>${reply.park.name }</td>
	                <td class="bggrey ">所属主题：</td>
	                <td>${reply.post.name }</td>
	            </tr> --%>
	            <tr>
	                <td class="bggrey " >所属帖子：</td>
	                <td >${reply.post.name }</td>
	                <td>发布人：</td>
	                <td>${reply.user.relName}</td>
	            </tr>
	            <tr>
	                <td class="bggrey "> 发布时间：</td>
	                <td>${reply.publishedAt }</td>
	                <td class="bggrey ">更新时间：</td>
	                <td>${reply.updatedAt }</td>
	            </tr>
	            </tbody>
	            </table>
	            <h2 class="count_flow jbxx ">回复内容</h2>
            <div class="col-md-12 col-sm-12 col-cs-12 p0">
            
             <script id="editor" name="content" type="text/plain" class="mt20" readonly="readonly">
             </script>
           <%--   <textarea  class="h130 col-md-12 col-xs-12 col-sm-12"  title="不超过800个字" readonly="readonly">${reply.content}</textarea> --%>
	        </div>  	 	 
	<!-- 底部按钮 -->			          
    <div class="col-md-12 tc">    
    	<button class="btn btn-windows back mt10" onclick="history.go(-1)" type="button">返回</button>
	</div>
  </div>
     </div>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例

    var option ={
            toolbars: [[
                        'undo', 'redo', '|',
                        'bold', 'italic', 'underline',  'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',                
                         'fontfamily', 'fontsize', '|',
                         'indent', '|',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|','emotion',
                ]]

        }
    var ue = UE.getEditor('editor',option);
    var content='${reply.content}';
    ue.ready(function(){
        ue.setContent(content);
        ue.setDisabled([]);    
    });
</script>

  </body>
</html>

