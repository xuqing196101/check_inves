<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>

<%@ include file="/reg_head.jsp"%>
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
		   var name=[];
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val()); 
				name.push($(this).next().val());
			});
			var flag=$("#flag").val();
			if(flag=='SERVICE'){
				parent.$('#server_seach').val(name);
			}
			if(flag=='PROJECT'){
				parent.$('#project_seach').val(name);
			}
			if(flag=='SALES'){
				parent.$('#sale_seach').val(name);
			}
			if(flag=='PRODUCT'){
				parent.$('#production_seach').val(name);
			}
			
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
	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${info.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    total: "${info.total}",
			    startRow: "${info.startRow}",
			    endRow: "${info.endRow}",
			    skip: true, //是否开启跳页
			    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//				        var page = location.search.match(/page=(\d+)/);
//				        return page ? page[1] : 1;
					return "${info.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			            if(!first){ //一定要加此判断，否则初始时会无限刷新
			            	$("#page").val(e.curr);
				        	$("#add_form").submit();
			        	
			     
			        }  
			    }
			});
	  });
	  
	  
		$(function() {
			  window.onload=function(){
				  var checkeds = $("#choseId").val();
		 		  var arrays =checkeds.split(",");
		 		   var checkBoxAll = $("input[name='chkItem']"); 
		 		   if(arrays.length>0){
				  for(var i=0;i<arrays.length;i++){
					  $.each(checkBoxAll,function(j,checkbox){
						    //获取复选框的value属性
						      var checkValue=$(checkbox).val();
						            if(arrays[i]==checkValue){
					                      $(checkbox).attr("checked",true);
					               }
					      });
				  }
		 		}
			  }
		});
		
		
	  
</script>
</head>

<body>
	 <div class="service_kind container p0 p0">
	  <h2 class="m0 m0 col-md-12 col-sm-12 col-xs-12">已选品目：</h2>
	  <div class="col-md-12 col-sm-12 col-xs-12 service_desc bgwhite">
		  <div class="col-md-12 col-sm-12 col-xs-12 service_list p0" id="chose">
		   
		   <c:forEach items="${chose }" var="ch" varStatus="vs">
		        <span class="col-md-3 col-sm-4 col-xs-12 m0"><input type="checkbox" name='checks'  checked="checked" value="${ch.id }"/>${ch.name }</span>
		 	  </c:forEach>
		 	  
		  </div>
	  </div>
	</div>
	
	<div class="service_kind container p0">
	  <h2 class="m0 col-md-12 col-sm-12 col-xs-12">品目信息 </h2>
	  <div class="col-md-12 col-sm-12 col-xs-12 service_desc bgwhite">
		  <div class="col-md-12 col-sm-12 col-xs-12 service_list p0">
		     <c:forEach items="${info.list }" var="obj" varStatus="vs">
		        <span class="col-md-3 col-sm-4 col-xs-12 m0"><input type="checkbox" onclick="chsoe(this)" name="chkItem" value="${obj.id }"/> <input type="hidden" value="${obj.name }"> ${obj.name }</span>
		 	  </c:forEach>
		  </div>
	  </div>
	    <div id="pagediv" align="right"></div>
	</div>
	
	
	  
	  
	<div class="col-md-12 col-sm-12 col-xs-12 tc">
	  <input type="button" class="btn" onclick="add()" value="确定">
	  <input type="button" class="btn" onclick="cloase()" value="关闭">
	</div>
	 
	 <input type="hidden" value="${id}" id="flag" >
	  <input type="hidden" value="${choseId}" id="choseId" >
	<form class="dnone" action="" id="category_id">
			<input type="hidden" name="categoryId" id="cid">
			<input type="hidden" name="supplierId" id="sid" value="${sid }">
			<input type="hidden" name="supplierTypeRelateId"  value="${code}">
	</form>
	
	<form class="dnone" action="" id="page">
			<input type="hidden" name="page" id="page" >
			<input type="hidden" name="sid"  value="${sid }">
			<input type="hidden" name="id"  value="${id}">
	</form>
	
	
</body>
</html>

