<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/upload" prefix="p" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
	
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
   <link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>

    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
     <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}//public/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}//public/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}//public/ueditor/lang/zh-cn/zh-cn.js"></script>
     <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
    <script type="text/javascript">
       var globalPath = "${contextPath}";
       
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
        function preview(){
             $("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/printView.html');   
             $("#form").submit();
        }
        //发布
        function publish(){
        	var articleId = $("#articleId").val();
        	if(articleId == null || articleId == ""){
        		layer.alert("请先保存公告",{offset: '222px', shade:0.01});
        	}else{
	        	var iframeWin;
	            layer.open({
	              type: 2, //page层
	              area: ['400px', '200px'],
	              title: '发布招标公告',
	              closeBtn: 1,
	              shade:0.01, //遮罩透明度
	              shift: 1, //0-6的动画形式，-1不开启
	              offset: '100px',
	              shadeClose: false,
	              content: '${pageContext.request.contextPath}/open_bidding/publishEdit.html?id='+articleId,
	              success: function(layero, index){
	                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	              }
	            });
        	}
        }
        //保存
        //function save(){         
        //    layer.prompt({title: '请输入招标公告名称', formType: 3}, function(text){
        //    	$("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/saveBidNotice.do?name='+text);   
        //        $("#form").submit();               
        //     });
       // }
       
       function save(){
       		$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/open_bidding/saveBidNotice.do",
			    dataType:'json',
			    data : $('#form').serialize(),
			    success: function(data) {
			    	$("#articleId").val(data.obj.id);
			        layer.msg(data.message,{offset: '222px'});
			    }
			});
       }
       
       $(function(){
			var range="${range}";
			if(range==2){
				$("input[name='ranges']").attr("checked",true); 
			}else{
				$("input[name='ranges'][value="+range+"]").attr("checked",true); 
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
	         <input type="button" class="btn btn-windows git" onclick="preview()" value="预览"></input>  
	         <input type="button" class="btn btn-windows save" onclick="save()" value="保存"></input>
	         <input type="button" class="btn btn-windows apply" onclick="publish()" value="发布"></input>  
	    </div>
	    <input type="hidden" name="id" id="articleId" value="${articleId }">
	    <input type="hidden" name="projectId" value="${projectId }">
		<div class="col-md-12 clear">
			 <i class="red">*</i>公告标题：<br>
			 <input class="col-md-12 w100p" id="name" name="name" value="${name }" type="text"><br>
			 <i class="red">*</i>发布范围：<br>
			 <div class="input-append">
	            <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0">内网</label>
	            <label class="ml30 fl"><input type="checkbox" name="ranges" value="1" >外网</label>
	         </div><br>
        	 <i class="red">*</i>公告内容：
             <script id="editor" name="content" type="text/plain" class="ml125 w900"></script>
             <p:upload businessId="111" selectButton="sssss" typeId="1" sysKey="1"/>
             <p:show businessId="111" sysKey="1"/>
        </div>
      </form>
				     
    <script type="text/javascript">
    var option ={
            toolbars: [[
                    'fullscreen', 'source', '|', 'undo', 'redo', '|',
                    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                    'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                    'directionalityltr', 'directionalityrtl', 'indent', '|',
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                    'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                     'emotion', /*'scrawl',*/ /*'insertvideo', 'music',*/  /* 'map', 'gmap',*/ 'insertframe', /*'insertcode', 'webapp',*/ 'pagebreak', 'template', 'background', '|',
                    'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                     'preview', 'searchreplace', 'help', 'drafts'
                ]]

        }
    var ue = UE.getEditor('editor', option); 
    var content = '${content}';
	    ue.ready(function(){
	        //需要ready后执行，否则可能报错
	       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
	        ue.setContent(content);
	        ue.setHeight(500);
	    })
    </script>
</body>
</html>



