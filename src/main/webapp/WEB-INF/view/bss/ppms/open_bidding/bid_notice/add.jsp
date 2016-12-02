<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/upload" prefix="p" %>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
	<%@ include file="/WEB-INF/view/common.jsp"%>
	
    <script type="text/javascript">
       
  	//导入模板
    function inputTemplete(){
        var iframeWin;
        layer.open({
          type: 2, //page层
          area: ['700px', '500px'],
          title: '导入模板',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          shift: 1, //0-6的动画形式，-1不开启
          offset: '70px',
          shadeClose: false,
          content: '${pageContext.request.contextPath}/resultAnnouncement/getAll.html',
          success: function(layero, index){
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          }
        });
    }
        //导出
        function exportWord(){
        	var content = ue.getContent();
        	if(content == null || content == ""){
        		layer.alert("请填写公告内容",{offset: '222px', shade:0.01});
        	}else{
	            $("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/export.html');   
	            $("#form").submit();
        	}
        }
        //预览
        function pre_view(){
             //$("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/printView.html');   
             //$("#form").submit();
             var ue = UE.getEditor('editor'); 
    		 var content = ue.getContent();
             $("#preview").removeClass("dnone");
             $("#pre_name").append($("#name").val());
             $("#pre_content").append(content);
             $("#form").addClass("dnone");
        }
        
        //预览返回
        function pre_back(){
        	$("#preview").addClass("dnone");
        	$("#form").removeClass("dnone");
        	$("#pre_content").empty();
        	$("#pre_name").empty();
        }
        
        //发布
        function publish(){
        	var articleId = $("#articleId").val();
        	var saveStatus = $("#is_saveNotice").val();
        	var noticeType = $("#noticeType").val();
        	var flowDefineId = $("#flowDefineId").val();
        	if(saveStatus != 'isok'){
        		layer.alert("请先保存公告",{offset: '222px', shade:0.01});
        	}else{
	        	var iframeWin;
	            layer.open({
	              type: 2, //page层
	              area: ['400px', '200px'],
	              title: '发布招标公告',
	             // skin: 'layui-layer-rim',
	              closeBtn: 1,
	              shade:0.01, //遮罩透明度
	              shift: 1, //0-6的动画形式，-1不开启
	              offset: '100px',
	              shadeClose: false,
	              content: '${pageContext.request.contextPath}/open_bidding/publishEdit.html?id='+articleId+'&noticeType='+noticeType+'&flowDefineId='+flowDefineId,
	              success: function(layero, index){
	                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	              }
	            });
        	}
        }
       
       function save(){
       		$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/open_bidding/saveBidNotice.do",
			    dataType:'json',
			    data : $('#form').serialize(),
			    success: function(data) {
			    	if(!data.success){
                        layer.msg(data.message,{offset: ['220px']});
                    }else{
				    	$("#is_saveNotice").val("isok");
				        layer.msg(data.message,{offset: '222px'});
                    }
			    }
			});
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
		     <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
	         <input type="button" class="btn btn-windows output" onclick="exportWord()" value="导出"></input>
	         <input type="button" class="btn btn-windows git" onclick="pre_view()" value="预览"></input>  
	         <input type="button" class="btn btn-windows save" onclick="save()" value="保存"></input>
	         <input type="button" class="btn btn-windows apply" onclick="publish()" value="发布"></input>  
	    </div>
	    <input type="hidden" id="is_saveNotice" value="${saveStatus}">
	    <input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId }">
	    <input type="hidden" id="noticeType" value="${noticeType }">
	    <input type="hidden" name="articleTypeId" id="articleTypeId" value="${articleType.id }">
	    <input type="hidden" name="id" id="articleId" value="${articleId }">
	    <input type="hidden" name="projectId" value="${projectId }">
		<div class="col-md-12 clear">
			 <span class="red">*</span>公告标题：<br>
			 <input class="col-md-12 w100p" id="name" name="name" value="${article.name }" type="text"><br>
			 <span class="red">*</span>发布范围：<br>
			 <div >
	            <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0">内网</label>
	            <label class="ml30 fl"><input type="checkbox" name="ranges" value="1" >外网</label>
	         </div><br>
        	 <div class="mt10"><span class="red">*</span><span>公告内容：</span></div>
             <script id="editor" name="content" type="text/plain" class="ml125 w900"></script>
                          上传附件： 
             <p:upload id="a" businessId="${articleId }" multiple="true" sysKey="${sysKey }" typeId="${typeId }" auto="true" />
             <p:show  showId="b" groups="b,c"  businessId="${articleId }" sysKey="${sysKey }" typeId="${typeId }"/>
        </div>
      </form>
	<div class="dnone" id="preview">
	   	<!-- <div class="col-md-12 p30_40 border1 margin-top-20"> -->
	   		<div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
            <input type="button" class="btn " value="打印" onclick="window.print();" id="print"/>
            <input class="btn btn-windows back" onclick="pre_back();" value="返回" type="button">
        	</div>
		     <h3 class="tc f22">
			   <div class="title bbgrey" id="pre_name"></div>
			 </h3>
			 <div class="source" >
			 </div>
			 <div class="clear margin-top-20 new_content" id="pre_content">
			 </div>
			 <div class="extra_file">
			 	<div class="">
					<p:show  showId="c" groups="b,c" delete="false" businessId="${articleId }" sysKey="${sysKey }" typeId="${typeId }"/>
			 	</div>
			 </div>
	</div>
   <script type="text/javascript">
    var option ={
            toolbars: [[
                    'fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'directionalityltr', 'directionalityrtl', 'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|','simpleupload', 'insertimage',
                     'emotion', /*'scrawl',*/ /*'insertvideo', 'music',*/  /* 'map', 'gmap',*/ 'insertframe', /*'insertcode', 'webapp',*/ 'pagebreak', 'template', 'background', '|',
                    'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                     'preview', 'searchreplace', 'help', 'drafts'
                ]]

        }
    var ue = UE.getEditor('editor', option); 
    var content = '${article.content}';
	    ue.ready(function(){
	        //需要ready后执行，否则可能报错
	       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
	        ue.setContent(content);
	        ue.setHeight(500);
	    })
    </script>
</body>
</html>



