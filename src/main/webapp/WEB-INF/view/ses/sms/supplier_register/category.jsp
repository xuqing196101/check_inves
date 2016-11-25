<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
 <%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>产品品目</title>

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
		if(id.length>0){
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
			
		}else{
			layer.alert("至少选择一项",{offset: ['222px', '390px'], shade:0.01});
		}
	}
	
	function chsoe(obj){
		var bool=$(obj).is(':checked');
		var name=$(obj).next().val();
		var val=$(obj).val();
		if(bool==true){
			$("#chose").append("<span class='col-md-3 col-sm-4 col-xs-12 m0'><input type='checkbox' checked=checked'' name='checks' value='"+val+"'/>"+name+"</span>")
		}else{
			$('input[name="checks"]:checked').each(function(){ 
				 var chec=$(this).val();
			 
				 if(val==chec){
						$(this).parent().remove();
					}
			}); 
		}
		
  
	
		
	}
</script>
</head>

<body>
	 <div class="service_kind container p0 p0">
	  <h2 class="m0 m0 col-md-12 col-sm-12 col-xs-12">已选品目：</h2>
	  <div class="col-md-12 col-sm-12 col-xs-12 service_desc bgwhite">
		  <div class="col-md-12 col-sm-12 col-xs-12 service_list p0" id="chose">
		   
		  </div>
	  </div>
	</div>
	
	<div class="service_kind container p0">
	  <h2 class="m0 col-md-12 col-sm-12 col-xs-12">品目信息 </h2>
	  <div class="col-md-12 col-sm-12 col-xs-12 service_desc bgwhite">
		  <div class="col-md-12 col-sm-12 col-xs-12 service_list p0">
		     <c:forEach items="${list }" var="obj" varStatus="vs">
		        <span class="col-md-3 col-sm-4 col-xs-12 m0"><input type="checkbox" onclick="chsoe(this)" name="chkItem" value="${obj.id }"/> <input type="hidden" value="${obj.name }"> ${obj.name }</span>
		 	  </c:forEach>
		  </div>
	  </div>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12 tc">
	  <input type="button" class="btn" value="确定">
	  <input type="button" class="btn" value="关闭">
	</div>
	 
	<form class="dnone" action="" id="category_id">
			<input type="hidden" name="categoryId" id="cid">
			<input type="hidden" name="supplierId" id="sid" value="${sid }">
	</form>
</body>
</html>

