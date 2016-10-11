<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title></title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
	<script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
  <script type="text/javascript">
	  //2级联动
	  function change(id){
			$.ajax({
			    url:"${pageContext.request.contextPath}/topic/getListForSelect.do?parkId="+id,   
			    contentType: "application/json;charset=UTF-8", 
			    dataType:"json",   //返回格式为json
			    type:"POST",   //请求方式		    
		        success : function(topics) {     
		            if (topics) {          	
		            	$("#topics").html("");               
		              $.each(topics, function(i, topic) {  
		            	  $("#topics").append("<option  value="+topic.id+">"+topic.name+"</option>");	            	  
		              });  	                          
		            }
		        }
			});
	  }
 </script>
  </head>
  
  <body>
  <div class="wrapper">
   <jsp:include page="/index_head.jsp"></jsp:include>
        <div class="container content height-350 job-content ">
            <div class="col-md-12 p20 border1 margin-top-20 mb40">
                <div class="tab-v1">
                    <h2 class="ml50 bbgrey f30">发布帖子</h2>
                </div>
        <form  id="form" action="${pageContext.request.contextPath}/post/indexsave.html" method="post" >
       <div>
       <ul class="list-unstyled list-flow p0_20 f18">   
        
              <li class="col-md-12  p0  mb10">
               <span class="fl">帖子名称：</span>
               
                <textarea class="col-md-9" ></textarea>
                <%--<span class="add-on">i</span>--%>
               
             </li>
             
             <li class="col-md-6 p0">
               <span class="fl">所属版块：</span>
                <select name ="parkId" class="w250 mb10" onchange="change(this.options[this.selectedIndex].value)">
                    <option></option>
                    <c:forEach items="${parks}" var="park">
                        <option  value="${park.id}">${park.name}</option>
                    </c:forEach> 
                </select>
             </li>
             
             <li class="col-md-6  p0">
               <span class="fl">所属主题：</span>                    
                <select id="topics" name="topicId" class="w250 mb10">
                <option></option>
                </select>
             </li>
                        
            <li class="col-md-12 p0">
                <span class="fl">帖子内容：</span>
                <div class="fl mt5 col-md-9 p0">
                     <script id="editor" name="content" type="text/plain" class= ""></script>
                </div>
             </li>                
         </ul>
         <div class="clear"></div>
    </div>      
	    <!-- 底部按钮 -->                     
	  <div  class="mt20 tc">   
	    <button class="btn btn-windows save" type="submit">发布</button>
	    <button class="btn btn-windows reset" onclick="history.go(-1)" type="button">返回</button>
	  </div>
	  
     	</form>
   </div>
   </div>
   <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var option ={
    	toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload', 'insertimage', /*'emotion', 'scrawl',*/ /*'insertvideo', 'music',*/ 'attachment', /* 'map', 'gmap',*/ 'insertframe', /*'insertcode', 'webapp',*/ 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts'
            ]]

    }
    var ue = UE.getEditor('editor',option);
        
	</script>
	</div>
	<!-- footer -->
	  <jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>   
</html>



