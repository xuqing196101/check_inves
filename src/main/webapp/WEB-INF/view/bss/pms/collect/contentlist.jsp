<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<link href="${pageContext.request.contextPath }/public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

 
  <script type="text/javascript">


    

	
	function closed(){
		var id  = $('input[name="chkItem"]:checked').val(); 
		var planNo=parent.ids;
		var index = parent.layer.getFrameIndex(window.name); 
		var  ctype  = $('input[name="chkItem"]:checked').next().val(); 
		var ptype="${type}";
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
				//parent.layer.close(index);
			
		
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
      <h2>采购需求列表
	  </h2>
   </div> 
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30">选择</th>
		  <th class="info w50">序号</th>
		  <th class="info">下达状态</th>
		  <th class="info" width="45%">采购计划名称</th>
		  <th class="info" width="25%">采购类别</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30"><input  type="radio" value="${obj.id }" name="chkItem"> <input type="hidden" value="${obj.goodsType}"> </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			    <td class="tc">
			    <c:if test="${obj.status !=2}">
			    	未下达
			    </c:if>
			      <c:if test="${obj.status ==2}">
			    	已下达
			    </c:if>
			    </td>
			    <td class="tl"  >${obj.fileName }</td>
			      <td class="tl"  >
					    <c:forEach items="${mType }" var="mt">
								<c:if test="${mt.id==obj.goodsType }"> ${mt.name} </c:if> 
					    </c:forEach>	
			  </td>
			</tr>
		 </c:forEach>
      </table>
      
   </div>
   <div class="col-md-12 tc">
 <button class="btn btn-windows git"  onclick="closed()" >确定</button>
      		<button class="btn btn-windows cancel"  onclick="cancel()" >取消</button>
 		
 		</div>
 <form id="collected_form"  method="post" >
	 <input type="hidden" value="" name="id" id="id">
	 <input type="hidden" value=""  name="uniqueId" id="planNo">
 </form>
	 </div>
	 </body>
	 
</html>
