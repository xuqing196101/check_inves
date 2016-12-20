<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>

<html class=" js cssanimations csstransitions" lang="en">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
	<%@ include file="/WEB-INF/view/common.jsp"%>
   
    
	<script type="text/javascript">
   
		function cancel(){
			layer.closeAll();
		}
		
		$(function(){
		 $("#publish").click(function(){
       		$.ajax({
			    type: 'post',
			    url: "${pageContext.request.contextPath}/open_bidding/publish.html",
			    data : $('#form').serializeArray(),
			    dataType:'json',
			    success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['20px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/open_bidding/bidNotice.html?projectId="+result.projectId;
                        }, 500);
                        layer.msg(result.msg,{offset: ['20px']});
                    }
                },
                error: function(result){
                    layer.msg("提交失败",{offset: ['20px']});
                }
			});
       	});
       });
	</script>    
  </head>
  <body>
  	<form  id ="form" action="${pageContext.request.contextPath}/open_bidding/publish.html" method="post" >
	  	<input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId }">
	  	<input type="hidden" name="id" id="articleId" value="${articleId }">
	  	<div class="layui-layer-wrap" >
		  <div class="drop_window">
			  <ul class="list-unstyled">
			    <li class="col-md-6 p0 mt10 mb25">
              <label class="col-md-5 padding-left-5"><span class="red">*</span>上传审批附件:</label>
                <p:upload id="a" businessId="${articleId }"  sysKey="${sysKey }" typeId="${typeId }" auto="true" />
                <p:show  showId="b"  businessId="${articleId }" sysKey="${sysKey }" typeId="${typeId }"/>
              </li>
	            <div class="clear"></div>
			  </ul>
          	  <div class="tc mt10 col-md-12">
                <input class="btn" value="确定" type="button" id="publish"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		  </div>
	</div>
	</form>
  </body>
</html>

