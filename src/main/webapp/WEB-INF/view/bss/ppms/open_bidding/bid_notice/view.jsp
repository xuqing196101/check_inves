<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/upload" prefix="p" %>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">
       
        //导出
        function exportWord(){
            $("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/export.html');   
            $("#form").submit();
        }
        //预览
        function pre_view(){
             var ue = UE.getEditor('editor'); 
    		 var content = ue.getContent();
             $("#preview").removeClass("dnone");
             $("#pre_content").empty(); 
             $("#pre_content").append(content);
             $("#form").addClass("dnone");
        }
        
        //预览返回
        function pre_back(){
        	$("#preview").addClass("dnone");
        	$("#form").removeClass("dnone");
        }
        
        $(function(){
			var range="${article.range}";
			if(range !=null && range != ""){
				if(range==2){
					$("input[name='ranges']").attr("checked",true); 
				}else{
					$("input[name='ranges'][value="+range+"]").attr("checked",true); 
				}
			}
		});
    </script>
</head>

<body>
	 <form  method="post" id="form" > 
        <!-- 按钮 -->
        <div class="fr pr15 mt10">
	         <input type="button" class="btn btn-windows output" onclick="exportWord()" value="导出"></input>
	         <input type="button" class="btn btn-windows git" onclick="pre_view()" value="预览"></input>  
	    </div>
	    <input type="hidden" name="article" id="articleId" value="${article.id }">
	    <input type="hidden" name="projectId" value="${article.projectId }">
		<div class="col-md-12 clear">
			 <span class="red">*</span>公告标题：<br>
			 <input class="col-md-12 w100p" id="name" name="name" readonly="readonly" value="${article.name}" type="text"><br>
        	 <span class="red">*</span>发布范围：<br>
			 <div>
	            <label class="fl margin-bottom-0"><input type="checkbox" disabled="disabled" name="ranges" value="0">内网</label>
	            <label class="ml30 fl"><input type="checkbox" disabled="disabled" name="ranges" value="1" >外网</label>
	         </div><br>
        	 <div class="mt10"><span class="red">*</span><span>公告内容：</span></div>
             <script id="editor" name="content" type="text/plain" class="ml125 w900"></script>
             <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="" >文章附件：</span>
             		 <p:show  showId="b" groups="b,d,c" delete="false" businessId="${article.id }" sysKey="${sysKey }" typeId="${typeId }"/>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="" >审批附件: </span>
                  <p:show  showId="d"  groups="b,d,c" businessId="${article.id }" sysKey="${sysKey }" typeId="${typeId_examine }"/>
              </li>
        </div>
      </form>
	  <div class="dnone" id="preview">
	   	<!-- <div class="col-md-12 p30_40 border1 margin-top-20"> -->
	   		<div class="col-md-10 tc">
            <input type="button" class="btn " value="打印" onclick="window.print();" id="print"/>
            <input class="btn btn-windows back" onclick="pre_back();" value="返回" type="button">
        	</div>
		     <h3 class="tc f22">
			   <div class="title bbgrey" id="pre_name">${article.name}</div>
			 </h3>
			 <div class="source" >
			 </div>
			 <div class="clear margin-top-20 new_content" id="pre_content">
			 </div>
			 <div class="extra_file">
			 	<div class="">
					<p:show  showId="c" groups="b,d,c" delete="false" businessId="${article.id }" sysKey="${sysKey }" typeId="${typeId }"/>
			 	</div>
			 </div>
		</div>		     
    <script type="text/javascript">
    var ue = UE.getEditor('editor'); 
    var content='${article.content}';
    ue.ready(function(){
        //需要ready后执行，否则可能报错
       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
        ue.setContent(content); 
        ue.setDisabled(true);
        ue.setHeight(500);
    })
    </script>
</body>
</html>



