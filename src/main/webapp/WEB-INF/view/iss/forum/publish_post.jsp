<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="../../front.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title></title>  
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
        <link href="${ pageContext.request.contextPath }/public/ZHQ/css/style.css" media="screen" rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/public/ZHQ/css/forum.css" media="screen" rel="stylesheet">
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
           <h2 class="f30 tc">发布帖子</h2>
            <div class="col-md-12 p20 border1 margin-top-20 mb40">     
        <form  id="form" action="${pageContext.request.contextPath}/post/indexsave.html" method="post" >
       <ul class="list-unstyled list-flow p0_20 f18">   
        
              <li class="col-md-12  p0  mb10">
               <span class="fl"><div class="red star_red">*</div>帖子名称：</span>
               <div class="select_common col-md-9 p0">
                <input class="col-md-12"  name="name" type="text"/>
                <div class="cue">${ERR_name}</div>
               </div>
                <%--<span class="add-on">i</span>--%>
               
             </li>
             
             <li class="col-md-6 p0">
               <span class="fl"><div class="red star_red">*</div>所属版块：</span>
               <div class="select_common">
                <select name ="parkId" onchange="change(this.options[this.selectedIndex].value)">
                    <option></option>
                    <c:forEach items="${parks}" var="park">
                        <option  value="${park.id}">${park.name}</option>
                    </c:forEach> 
                </select>
                <div class="cue">${ERR_park}</div>
                </div>
             </li>
             
             <li class="col-md-6  p0">
               <span class="fl"><div class="red star_red">*</div>所属主题：</span>    
               <div class="select_common">                
                <select id="topics" name="topicId">
                <option></option>
                </select>
                <div class="cue">${ERR_topic}</div>
                </div>
             </li>
                        
            <li class="col-md-12 p0">
                <span class="fl"><div class="red star_red">*</div>帖子内容：</span>
                <div class="fl mt5 col-md-9 p0">
                     <script id="editor" name="content" type="text/plain" class= ""></script>
                      <div class="red clear f12">${ERR_content}</div>
                </div>
             </li>   
              <input type="hidden" name="id" value='${id}'></input> 
              <li class="col-md-12 p0">
               <span>上传附件：</span>
               <div class="fl f14">
                  <up:upload id="post_attach_up" multiple="true" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
                  <up:show showId="post_attach_show"  businessId="${id}" sysKey="${sysKey}" typeId="${typeId}"/>
               </div>
              </li>            
         </ul>
        <!-- 底部按钮 -->                     
      <div  class="mt20 tc">   
        <button class="btn" type="submit">发布</button>
        <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
      </div>
      
        </form>
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
                 'insertimage', 

            ]]

    }
    var ue = UE.getEditor('editor',option);
        
    </script>
    </div>
    <!-- footer -->
      <jsp:include page="/index_bottom.jsp"></jsp:include>
  </body>   
</html>