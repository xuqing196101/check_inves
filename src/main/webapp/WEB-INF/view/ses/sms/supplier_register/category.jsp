<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
 <%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加工程资质资格信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	
 
	function cloase(){
		 var index=parent.layer.getFrameIndex(window.name);

		    parent.layer.close(index);

	}
	
	function add(){
		 var index=parent.layer.getFrameIndex(window.name);
		   var id =[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val()); 
			}); 
		$("#cid").val(id);	
		
		 $.ajax({
			 type: "POST",  
             url: "${pageContext.request.contextPath}/supplier_item/save_or_update.html",  
             data: $("#category_id").serialize(),  
             success:function(result){
     		     parent.layer.close(index);
              },
              error: function(result){
                  layer.msg("添加失败",{offset: ['150px', '180px']});
              }
              
              
		 });
		 
		 
		 
	}
</script>

</head>

<body>
<div class="wrapper">

 <div class="col-md-12 service_kind">
  <h2 class="m0 col-md-12">已选产品/服务分类：</h2>
  <div class="col-md-12 service_desc bgwhite">
  <c:forEach items="${list }" var="obj">
  
	  <div class="col-md-12 service_list p0">
	    <span class="col-md-3 m0"><input type="checkbox" value="${obj.id }"/>${obj.name }</span>
	 
	  </div>
	  </c:forEach>
  </div>
</div>
<div class="col-md-12 tc">
<input type="button" onclick="add()" class="btn" value="确定">
<input type="button" class="btn" onclick="cloase()" value="关闭">
</div>

<form action="$" id="category_id">
		<input type="hidden" name="cid" id="cid">
		<input type="hidden" name="sid" id="sid" value="${sid }">
</form>
</body>
</html>
