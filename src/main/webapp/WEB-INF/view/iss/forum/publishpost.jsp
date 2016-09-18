<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
	<script src="<%=basePath%>public/ZHQ/js/hm.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
	<!--导航js-->
	<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
	<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript"src="<%=basePath%>public/js/jquery.min.js"></script>
	
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>public/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/validate/messages_zh.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/validate/additional-methods.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>

  <script type="text/javascript">
	$(function(){
		$("#form").validate({
			errorElement: "span",
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules:{
				name:"required",			
				parkId:"required",
				topicId:"required",
				content:"required"
			},
			messages:{
				name:"帖子名称不能为空",
				parkId:"所属版块不能为空",
				topicId:"所属主题不能为空",
				content:"内容不能为空"
			}
		});
	});
	/** 当是发布时将状态修改后再发送请求 */
	function save(){
		var contentVal=UE.getEditor('editor').getContent();
		if(contentVal==null||contentVal==''){
			layer.msg('请填写内容',{offset: ['222px', '390px']});
			return;
		}else{
			$("#form").submit();
		}
	};
	  //2级联动
	  function change(id){
			$.ajax({
			    url:"<%=basePath %>topic/getListForSelect.do?parkId="+id,   
			    contentType: "application/json;charset=UTF-8", 
			    dataType:"json",   //返回格式为json
			    type:"POST",   //请求方式		    
		        success : function(data) {     
		            if (data) {          	
		              $("#topics").html("");                
		              $.each(data.topics, function(i, topic) {  
		            	  $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");	            	  
		              });  	                          
		            }
		        }
			});
	  }
 </script>
  </head>
  
  <body>
   <div class="container margin-top-10">
     <div class="content padding-left-25 padding-right-25 padding-top-20">	
        <form  id="form" action="<%=basePath %>post/indexsave.html" method="post" >
        	<div>
        		帖子名称：<input id="name" name="name" class="w200" type="text">	 <br/>         	
	          	所属版块：
	         	 <select name ="parkId" id ="parks" class="w100 mt10" onchange="change(this.options[this.selectedIndex].value)">
					<option></option>
		  	  		<c:forEach items="${parks}" var="park">
		  	  			<option  value="${park.id}">${park.name}</option>
		  	  		</c:forEach> 
	  			</select><br/> 
	  			
	          	所属主题：
	        	<select id="topics" name="topicId" class="w100 mt10">
	        	<option></option>
	  			</select><br/> 
	  			
	         </div>

			     <div class=''>
					帖子内容：
					<script id="editor" name="content" type="text/plain" class="w900"></script>
				</div>
	        	<input value="发布" type="submit" onclick="save()">
     	</form>
   </div>
   </div>
   <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
	</script>
  </body>
</html>



