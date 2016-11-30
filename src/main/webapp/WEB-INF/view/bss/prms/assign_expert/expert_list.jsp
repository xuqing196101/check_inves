<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
 	 /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
		 /**重置密码*/
		 function resetPwd(){
		 	   var id=[]; 
		        $('input[name="chkItemExp"]:checked').each(function(){ 
		            id.push($(this).val());
		        }); 
		        if(id.length==1){
		     	   $.ajax({
		                type: "GET",
		                url: "${pageContext.request.contextPath}//ExpExtract/resetPwd.do?eid"+id,
		                dataType: "json",
		                success: function(data){
		             	   if("sccuess"==data){
		                        layer.alert("重置成功！默认密码：123456",{offset: ['222px', '390px'], shade:0.01});
		                           }else{
		                         	   layer.alert("重置失败！请尝试重新重置",{offset: ['222px', '390px'], shade:0.01});
		                           }
		                         }
		            });
		        }else if(id.length>1){
		            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		        }else{
		            layer.alert("请选择需要重置密码的专家",{offset: ['222px', '390px'], shade:0.01});
		        }
		 }
		 function addexp(){
		 	  layer.open({
		           type: 2, //page层
		         area: ['80%', '50%'],
		           title: '添加临时专家',
		           closeBtn: 1,
		           shade:0.01, //遮罩透明度
		           shadeClose: true,
		           offset: '30px',
		           move:false,
		           content: '${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?projectId=${project.id}'
		         });
		 }
  </script>
  <body>
  	<div class="container">
	    <div class="headline-v2">
	     	<h2>专家抽取名单</h2>
	    </div>
	    <div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows add" onclick="addexp();" type="button">添加临时专家</button>
		    <button class="btn btn-windows add" onclick="" type="button">分配组长</button>
	   	</div>
	   	<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
				  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				  <th class="w50 info">序号</th>
				  <th class="info">包名</th>
				  <th class="info">专家人数</th>
				</tr>
				</thead>
				<c:forEach items="${packageList }" var="pack" varStatus="vs">
					<tr>
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${pack.id}" /></td>
						<td class="tc w30">${vs.count }</td>
						<td class="tc">${pack.name }</td>
						<td class="tc"><a href="#" onclick="view('${pack.id}');">1</td>
					</tr>
				</c:forEach>
			</table>
	    </div>
    </div>
  </body>
</html>
