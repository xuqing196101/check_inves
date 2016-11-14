<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家诚信列表</title>
<script type="text/javascript">
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("check");
		 var checkAll = document.getElementById("allId");
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
 
   
   	function clearSearch(){
   		$("#relName").attr("value","");
   	}
   
   	function submit1(){
   		
   	 var count = 0;
	  var ids = document.getElementsByName("check");
	 var id2="";
	 var num =0;
     for(i=0;i<ids.length;i++) {
   		 if(document.getElementsByName("check")[i].checked){
	    		  id2 += document.getElementsByName("check")[i].value+",";
	    		  num++;
   		  }
   		 //id.push(document.getElementsByName("check")[i].value);
      		 count++;
    }
  	var id = id2.substring(0,id2.length-1);
  	var expertId = "${expertId}";
  		if(num>0){
  			layer.confirm('您确定要登记吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
  	 			layer.close(index);
  	 			$.ajax({
  	 				url:"${pageContext.request.contextPath}/credible/git.html",
  	 				data:{"ids":id,"expertId":expertId},
  	 				type:"post",
  	 	       		success:function(){
  	 	       		parent.location.reload();
  	 	       			layer.msg('已登记',{offset: ['222px', '390px']});
  	 		       		window.setTimeout(function(){
  	 		       			window.location.reload();
  	 		       				for(var i = 0;i<info.length;i++){
  	 						info[i].checked = false;
  	 						}
  	 		       		}, 1000);
  	 	       		},
  	 	       		error: function(){
  	 					layer.msg("登记失败",{offset: ['222px', '390px']});
  	 				}
  	 	       	});
  	 		});
  		}else{
  			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
     	}
   	}
   	function cancel(){
		 var index=parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);
	}
</script>
</head>
<body>
  <div class="wrapper">
<!-- 我的订单页面开始-->
   <div class="container">
   <div class="headline-v2">
   <h2>专家诚信登记</h2>
   </div>
   </div>
  
   <form action="${pageContext.request.contextPath}/credible/findAll.html"  method="post"   class="registerform"> 
  <input type="hidden" name="page" id="page">
  <input type="hidden" name="flag" value="0">
   <div align="center">
                    <table>
                    <tr>
                    <td>
                    <span>关键字：</span><input type="text" id="relName" name="badBehavior" value="">
                    </td>
					<td>
                         &nbsp;&nbsp; <input class="btn btn-windows "  value="搜索" type="submit">
                          <input class="btn btn-windows reset" id="button" onclick="clearSearch();" value="重置" type="reset">
                     </td>
                        </tr>
                        </table>
                  </form>
                  </div>
                  </div>  
<form action="${pageContext.request.contextPath}/credible/list.html"  method="post"   class="registerform"> 
   <div class="container">
   <div class="col-md-8">
	<button class="btn btn-windows git" type="button" onclick="submit1();">提交</button>
	<button class="btn btn-windows cancel" type="button" onclick="cancel();">关闭</button>
	</div>
    </div>
<!-- 表格开始-->
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     
        <input type="hidden" name="expertId" value="${expertId }">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" onclick="selectAll();"  id="allId" alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">诚信内容</th>
		  <th class="info">状态</th>
		  <th class="info">创建时间</th>
		  <th class="info">修改时间</th>
		  <th class="info">分值</th>
		</tr>
		</thead>
		<c:forEach items="${expertCredible }" var="e" varStatus="vs">
		<tr style="cursor: pointer; ">
		  <td class="tc w30"><input type="checkbox" name="check" id="checked" alt="" value="${e.id }"></td>
		  <td  class="tc w50">${vs.index+1}</td>
		  <td class="tc" title="${e.badBehavior}">
		     <c:if test="${fn:length(e.badBehavior)>5}">${fn:substring(e.badBehavior, 0, 5)}...</c:if> 
		     <c:if test="${fn:length(e.badBehavior)<6}">${e.badBehavior}</c:if>
		  </td>
		  <c:if test="${e.isStatus == 1 }">
		 	<td  class="tc"><span class="label rounded-2x label-u">启用</span></td>
		 </c:if>
		 <c:if test="${e.isStatus == 2 }">
		 	<td  class="tc"><span class="label rounded-2x label-dark">停用</span></td>
		 </c:if>
		 <td  class="tc"><fmt:formatDate type='date' value='${e.createAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <td  class="tc"><fmt:formatDate type='date' value='${e.updateAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <td  class="tc">${e.score }</td>
		</tr>
		</c:forEach>
        </table>
     </div>
   </div>
   </form>
 </div>
</body>
</html>
