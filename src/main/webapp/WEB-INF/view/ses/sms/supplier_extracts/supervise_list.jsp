<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script src="<%=basePath%>public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
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
	
  	function view(id){
  		window.location.href="<%=basePath%>user/show.html?id="+id;
  	}
  	
    function add(){
    	var ids =[]; 
    	var nams="";
    	var id="";
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		});
		if(ids.length>0){
				for(var i=0;i<ids.length;i++){
					var name=ids[i].split("^");
					id+=name[0]+",";
					nams+=name[1]+",";
				}
				  
				  if(nams!=null && "" != nams){
					  parent.$('#sids').val(id.substring(0,id.length-1));
	                  parent.$('#supervises').val(nams.substring(0,nams.length-1));
	                  parent.$('#supervises').attr("title",nams.substring(0,nams.length-1));
				  }
			         var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				  parent.layer.close(index);
		
		}else{
			layer.alert("请选择要添加的监督人员",{offset: ['222px', '390px'], shade:0.01});
		}
    }
 
    
  </script>
  <body>
<!-- 表格开始-->

   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info">用户名</th>
		  <th class="info">姓名</th>
		  <th class="info">角色</th>
		  <th class="info">单位</th>
		  <th class="info">联系电话</th>
		</tr>
		</thead>
		<c:forEach items="${list.list}" var="user" varStatus="vs">
			<tr>
				  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${user.id}^${user.loginName}"/></td>
				  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				  <td class="tc" onclick="view(${user.id});">${user.loginName}</td>
				  <td class="tc">${user.relName}</td>
				  <td class="tc">
					<c:forEach items="${user.roles}" var="role" varStatus="vs">
			     		<c:if test="${user.roles.size()>vs.index+1}">
			     			${role.name},
			     		</c:if>
			     		<c:if test="${user.roles.size()<=vs.index+1}">
			     			${role.name}
			     		</c:if>
			     	</c:forEach>
				  </td>
				  <td class="tc">${user.org.name}</td>
				  <td class="tc">${user.telephone}</td>
			</tr>
		</c:forEach>
        </table>
     </div>
   </div>
  </body>
</html>
