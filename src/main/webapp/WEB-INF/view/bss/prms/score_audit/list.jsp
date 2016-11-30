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
  	function scoreView(packAgeId){
  		
  	}
  
  </script>
  <body>
  	<div class="container">
	    <div class="headline-v2">
	     	<h2>详细评审</h2>
	    </div>
	    <div class="col-md-12 pl20 mt10">
		    <button class="btn" onclick="" type="button">评分汇总</button>
	   	</div>
	   	<div class="content table_box">
	   		<input type="hidden" id="projectId" value="${projectId}">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
				  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				  <th class="w50 info">序号</th>
				  <th class="info">包名</th>
				  <th class="info">详审进度</th>
				  <th class="info">操作</th>
				</tr>
				</thead>
				<c:forEach items="${reviewProgressList}" var="rp" varStatus="vs">
			       <tr>
			       	<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${pack.id}" /></td>
			        <td class="tc w30">${vs.count} </td>
			        <td class="tc">${rp.packageName}</td>
				    <td class="tc">
					  <div class="col-md-12 padding-0">
						  <div class="progress w55p fl margin-left-0">
				             <div class="progress-bar progress-bar-danger" role="progressbar" 
				                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
				                 style="width:${rp.scoreProgress*100}%;"> 
				             </div> 
				          </div>
						  <span class="fl padding-5">${rp.scoreProgress*100}%</span>
					  </div>
				    </td>
				    <td class="tc w100">
			          <input class="btn" type="button" value="查看" onclick="scoreView('${rp.packageId}')">
			        </td>
			      </tr>
				</c:forEach>
			</table>
	    </div>
    </div>
  </body>
</html>
