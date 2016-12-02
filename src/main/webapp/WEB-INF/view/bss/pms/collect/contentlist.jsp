<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>


<jsp:include page="/WEB-INF/view/common.jsp"/> 
<link href="${pageContext.request.contextPath }/public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

 
  <script type="text/javascript">


    

	
	function closed(){
		var id  = $('input[name="chkItem"]:checked').val(); 
		var planNo=parent.ids;
		var index = parent.layer.getFrameIndex(window.name); 
		var  ctype  = $('input[name="chkItem"]:checked').next().val(); 
		var ptype=$("#ctype").val();
	      if(ctype!=ptype){
	    	  layer.alert("物资类别不一样",{offset: ['100px', '100px'], shade:0.01});
	      }
		else if(id==""||id==null){
			layer.alert("请选择要汇总的计划",{offset: ['100px', '100px'], shade:0.01});
		}else{
			$("#id").val(id);
			$("#planNo").val(planNo);

			$.ajax({
			url: "${pageContext.request.contextPath}/collect/update.html",
			type: "post",
			data:$("#collected_form").serialize(),
			success: function(result) {
				parent.location.reload(); // 父页面刷新
				parent.layer.close(index);
			
		
			},
			error: function(message){
				layer.msg("汇总失败",{offset: ['222px', '390px']});
				parent.layer.close(index);
			}
			
			
		});
		
		}
			
 	
		 
		 
			
		 
	}
	
 	function cancel(){
 		 var index = parent.layer.getFrameIndex(window.name); 
 		 
		 parent.layer.close(index);  
 	}
	
 	function ss(){
 		
 	}
  </script>
  </head>
  
  <body>
  <div class="container">
   <div class="headline-v2 fl">
      <h2>需求计划列表
	  </h2>
   </div> 
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w50">选择</th>
		  <th class="info w50">序号</th>
		  <th class="info">下达状态</th>
		  <th class="info">采购计划名称</th>
		  <th class="info">物资类别</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input  type="radio" value="${obj.id }" name="chkItem"> <input type="hidden" value="${obj.goodsType}"> </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			    <td class="tc"  >
			    <c:if test="${obj.status ==1}">
			    	未下达
			    </c:if>
			      <c:if test="${obj.status ==2}">
			    	已下达
			    </c:if>
			    
			    </td>
			    <td class="tc"  >${obj.fileName }</td>
			      <td class="tc"  >
					    <c:forEach items="${dicType }" var="mt">
								  <option value="${mt.id }"<c:if test="${mt.id==obj.goodsType }"> selected="selected"</c:if> >${mt.name}</option>
					    </c:forEach>
								
								
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
   </div>
   <div class="col-md-12 tc">
 <button class="btn btn-windows git"  onclick="closed()" >确定</button>
      		<button class="btn btn-windows cancel"  onclick="cancel()" >取消</button>
 		<input type="hidden" id="ctype" vlaue="${type }">
 		</div>
 <form id="collected_form" action="${pageContext.request.contextPath }collect/add.html" method="post" >
	 <input type="hidden" value="" name="id" id="id">
	 <input type="hidden" value=""  name="planNo" id="planNo">
 </form>
	 </div>
	 </body>
	 
</html>
