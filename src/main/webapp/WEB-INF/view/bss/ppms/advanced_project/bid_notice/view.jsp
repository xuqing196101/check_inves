<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript">
       	$(function() {
	  		//获取查看或操作权限
	       	var isOperate = $('#isOperate', window.parent.document).val();
	       	if(isOperate == 0) {
	       		//只具有查看权限，隐藏操作按钮
				$(":button").each(function(){ 
					$(this).hide();
	            }); 
			}
    	})
        //导出
        function exportWord(){
            $("#form").attr("action",'${pageContext.request.contextPath}/Adopen_bidding/export.html');   
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
	         <!-- <input type="button" class="btn btn-windows output" onclick="exportWord()" value="导出"></input> -->
	         <!-- <input type="button" class="btn btn-windows git" onclick="pre_view()" value="预览"></input>   -->
	    </div>
	    <input type="hidden" name="article" id="articleId" value="${article.id }">
	    <input type="hidden" name="projectId" value="${article.projectId }">
		<ul class="clear col-md-12 col-sm-12 col-xs-12 p0 mb10">
			<li class="col-md-12 col-sm-12 col-xs-12">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
				<div class="star_red">*</div>公告标题：</span>
				<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				 	<input type="text" id="name" name="name" readonly="readonly" value="${article.name}">
			 	</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 clear pl0">
			 	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
					<div class="star_red">*</div>发布范围：
				</span>
				<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
		             <label class="fl margin-bottom-0"><input type="radio" disabled="disabled" name="ranges" value="0">内网</label>
	            	 <label class="ml30 fl"><input type="radio" disabled="disabled" name="ranges" value="1" >内外网</label>
				</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 pl0">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
					<div class="star_red">*</div>产品类别：
				</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
			        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryNames}"  />
				</div>
			 </li>
			 <li class="col-md-12 col-sm-12 col-xs-12 pl0">
	        	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	        		<div class="star_red">*</div>信息正文：
	        	</span>
				 <div class="col-md-12 col-sm-12 col-xs-12 p0">
             	 	<input type="hidden" id="articleContent" value='${article.content}'>
             		<script id="editor" name="content" type="text/plain" class="ml125 w900"></script>
			 	 </div>
			 </li>
                <li class="col-md-3 col-sm-6 col-xs-12 mt10">
                  <span class="fl">公告附件：</span>
                  <u:show  showId="b" groups="b,d,f,g" delete="false" businessId="${article.id}" sysKey="${sysKey}" typeId="${typeId}"/>
                </li>
                
                <li class="col-md-3 col-sm-6 col-xs-12 mt10">
                  <span class="fl">单位及保密委员会审核表：</span>
                  <u:show  showId="fl"  groups="b,d,f,g" delete="false" businessId="${article.id}" sysKey="${sysKey}" typeId="${security}"/>
                </li>
             </ul>
        </div>
      </form>
	  <div class="dnone" id="preview">
	   	<!-- <div class="col-md-12 p30_40 border1 margin-top-20"> -->
	   		<div class="col-md-10 tc">
            <!-- <input type="button" class="btn " value="打印" onclick="window.print();" id="print"/> -->
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
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="" >公告附件：</span>
             		 <u:show  showId="g" groups="b,d,f,g"   businessId="${article.id}" delete="false" sysKey="${sysKey}" typeId="${typeId}"/>
	              </li>
			 	</div>
			 </div>
		</div>		     
    <script type="text/javascript">
    var ue = UE.getEditor('editor'); 
    var content = $("#articleContent").val(); 
    ue.ready(function(){
        //需要ready后执行，否则可能报错
       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
        ue.setContent(content); 
        ue.setDisabled(true);
        ue.setHeight(200);
    })
    </script>
</body>
</html>



