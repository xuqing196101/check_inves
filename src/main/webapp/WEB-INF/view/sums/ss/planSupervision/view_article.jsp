<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en">
<head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
  </head>
  
  <body>
    <input type="hidden" id="articleContent" value='${article.content}'>
    <div class="col-md-12 col-sm-12 col-xs-12 p0">
      <script id="editor" name="content" type="text/plain" class="ml125 w900 edit-posit"></script>
    </div>
    <div class="">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
        <span class="" >公告附件：</span>
          <u:show  showId="g" groups="b,d,f,g"  businessId="${article.id}" delete="false" sysKey="${sysKey}" typeId="${typeId}"/>
        </li>
      </div>
      
      <script type="text/javascript">
    var option ={
      toolbars: [
          [
            'fullscreen', 'source', '|', 'undo', 'redo', '|','bold', 
            'italic', 'underline', 'fontborder', 'strikethrough',
            'superscript', 'subscript', 'removeformat', 'formatmatch', 
            'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
            'backcolor', 'insertorderedlist', 'insertunorderedlist', 
            'selectall', 'cleardoc', '|','rowspacingtop', 'rowspacingbottom',
            'lineheight', '|','customstyle', 'paragraph', 'fontfamily', 
            'fontsize', '|', 'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
            'anchor','pagebreak', '|', 'horizontal', 'date', 'time', 'spechars', '|',
            'preview', 'searchreplace', 'help',
          ]
        ] 
    }
    var ue = UE.getEditor('editor', option);
    var content = $("#articleContent").val(); 
    ue.ready(function(){
        //需要ready后执行，否则可能报错
       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
        ue.setContent(content);
        ue.setDisabled(true); 
        ue.setHeight(700);
    });
    </script>
  </body>
</html>
