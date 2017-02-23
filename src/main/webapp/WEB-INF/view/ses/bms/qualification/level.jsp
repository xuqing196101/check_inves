<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/qualification/list.js"></script>
  <script type="text/javascript">
	  $(function(){
		  list(1);
		  loadCss();
		  
			var checkeds = $("#level").val();
			var arrays = checkeds.split(",");
			
			var checkBoxAll = $("input[name='chkItem']");
			
			for ( var i = 0; i < arrays.length; i++) {
				$.each(checkBoxAll,function(j, checkbox) {
							//获取复选框的value属性
							var checkValue = $(checkbox).val();
							if (arrays[i] == checkValue) {
								$(checkbox).attr("checked", true);
							}
				});
				
	      }
	  });
	  
	 
	  
	  //保存资质文件等级
	  function saveAp(){
		  var did= [];
		  var count = 0;	
			 
			
			$("input[type='checkbox']:checked").each(function(){
				did.push($(this).val());
				 count++;
		  });
		   if (count <1){
				layer.msg("至少选中一个等级！");
				return ;
			}else{
				$("#adid").val(did);
				$.ajax({
					//$("#qua_form").submit();
					type:"post",
					url:"${pageContext.request.contextPath}/qualification/update.do",
					data:$("#qua_form").serialize(),
					success:function(data){
				    	//window.location.href="${pageContext.request.contextPath}/qualification/init.html";
				    	parent.location.reload(); // 父页面刷新
					}
					
					
				});
			}
			
	  }
	  
	  function cancels(){
		  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  parent.layer.close(index);
		  
	  }
   </script>
  </head>
  <body>
    <!--面包屑导航开始-->
 
 
	
 
	
	<div id="dicDiv" class="layui-layer-wrap" >
	   <div class="drop_window">
			 <div class="col-md-12 col-sm-12 col-xs-12 level_desc">
			  <!--  <input  id="quaName"  name="name"  class="title col-md-12 pl20"   type="text"> -->
			  	<c:forEach items="${kind }" var="obj">
			  		<span> <input name="chkItem"   type="checkbox" value="${obj.id }"> ${obj.name } </span> 
			     </c:forEach>
			 </div>
	    <div class="tc mt10 col-md-12 col-sm-12 col-xs-12">
	       <br/>
          <button class="btn btn-windows save"  onclick="saveAp();" type="button">确定 </button>
		  <button class="btn btn-windows cancel"   onclick="cancels();"  type="button">取消</button>
        </div>
	  </div>
	</div>
	
	<form  id="qua_form" action="${pageContext.request.contextPath}/qualification/update.html" method="post">
		<input type="hidden" name="did" id="adid" value="">
		<input type="hidden" name="qid"   value="${id }">
		<input type="hidden"  id="level"  value="${list}">
	</form>
	
	
  </body>
</html>
