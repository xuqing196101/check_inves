<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
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
	
	//进入包初审页面
  	function fitsrView(packageId, projectId, flowDefineId){
  		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/firstAuditView.html?packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+flowDefineId);
  		//window.location.href="${pageContext.request.contextPath}/packageExpert/firstAuditView.html?packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+flowDefineId;
  	}
  
    //初审汇总
  	function gather(){
  	    var projectId = $("#projectId").val();
	    var ids =[]; 
	    var count = 0;
	    var tips = "";
		$('input[name="chkItem"]:checked').each(function(){ 
			var tdArr = $(this).parent().parent().children();
			var paName = tdArr.eq(2).text();
	    	var is_gather = tdArr.eq(3).find("input").val();//汇总状态
	    	var audit_progress = tdArr.eq(4).find("input").val();//评审进度
	    	//已汇总状态和评审进度小于100的不能汇总
	    	var message = "";
	    	if (is_gather == 1) {
	    		message += "【";
	    		message += paName;
	    		message += "已汇总";
	    		message += "】";
	    		count += 1;
			}
			if (audit_progress < 100) {
				message += "【";
				message += paName;
	    		message += "初审未完成";
	    		message += "】";
	    		count += 1;
			}
			tips += message;
			ids.push($(this).val()); 
		}); 
		if (count > 0) {
			layer.alert(tips,{offset: "100px", shade:0.01});
		} else {
			if(ids.length>0){
				layer.confirm('确定要汇总吗?', {title:'提示',offset: '80px',shade:0.01},function(index){
					$.ajax({   
			            type: "POST",  
			            url: "${pageContext.request.contextPath}/packageExpert/gather.html?projectId="+projectId+"&packageIds="+ids,       
					    success:function(result){
					    	layer.alert(result,{offset: "100px", shade:0.01});
					    	$.each(ids,function(n,value) {  
				           		 $("#ty").find("tr").each(function(){
				           		 	var tdArr = $(this).children();
				           		 	var paId = tdArr.eq(0).find("input").val();//包id
				           		 	if (value == paId) {
										tdArr.eq(3).text("已汇总");
									}
				           		 });
				            });  
		                },
		                error: function(result){
		                    layer.msg("汇总失败",{offset: "100px"});
		                }
			     	});
				});
			}else{
				layer.alert("请选择包",{offset: "80px", shade:0.01});
			}
		}
	}
  </script>
  <body>
	    <h2 class="list_title">符合性审查</h2>
	    <!-- <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
		    <button class="btn" onclick="gather();" type="button">初审汇总</button>
	   	</div> -->
   		<input type="hidden" id="projectId" value="${projectId}">
   		<input type="hidden" id="flowDefineId" value="${flowDefineId}">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <!-- <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
			  <th class="w50 info">序号</th>
			  <th class="info">包名</th>
			  <th class="info">状态</th>
			  <th class="info">初审进度</th>
			  <th class="info">操作</th>
			</tr>
			</thead>
			<tbody id="ty">
			<c:forEach items="${reviewProgressList}" var="rp" varStatus="vs">
		       <tr>
		       	<%-- <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${rp.packageId}" /></td> --%>
		        <td class="tc w30">${vs.count}</td>
		        <td class="tc">${rp.packageName}</td>
		        <td class="tc">
		        	<input type="hidden" value="${rp.isGather}">
		        	<c:if test="${rp.isGather == 0}">符合性审查中</c:if>
		        	<c:if test="${rp.isGather == 1}">符合性审查结束</c:if>
		        </td>
			    <td class="tc">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${rp.firstAuditProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${rp.firstAuditProgress*100}%</span>
				  </div>
				  <input type="hidden" value="${rp.firstAuditProgress*100}">
			    </td>
			    <td class="tc w100">
		          <input class="btn" type="button" value="查看" onclick="fitsrView('${rp.packageId}','${projectId}','${flowDefineId}')">
		        </td>
		      </tr>
			</c:forEach>
			</tbody>
		</table>
  </body>
</html>
