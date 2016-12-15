<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>My JSP 'expert_list.jsp' starting page</title>
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
	//汇总判断每一个table中的每一行每一列的值是否相等
	function scoreTotal(obj) {
		//得到点击坐标。
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left;
		y = oRect.top;

		var projectId = $("#projectId").val();
		var packageIds = "";
		$("input[name='chkItem']").each(function(i,result){
			if (result.checked == true) {
				packageIds = packageIds + result.value + ",";
			}
		});
		if (packageIds == "") {
			layer.alert("请至少选择一项!", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			$.ajax({
				url:"${pageContext.request.contextPath}/packageExpert/isGather.do",
				data:{"packageIds":packageIds, "projectId":projectId},
				async:false,
				success:function (response) {
					if (response == "ok") {
						$.ajax({
							 url:'${pageContext.request.contextPath}/packageExpert/scoreTotal.do',
							 data:{"packageId":packageId,"projectId":projectId},
							 async:false,
							 success:function(){
								 layer.alert("已汇总",{offset: [y, x], shade:0.01});
							 }
						 });
					} else {
						layer.alert(response + "不满足汇总条件!", {
							offset : [ y, x ],
							shade : 0.01
						});
					}
				}
			});
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
  	function scoreView(packageId){
  		window.location.href='${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId='+packageId+'&projectId=${projectId}';
  	}
  </script>
</head>
  <body>
	    <h2 class="list_title">详细评审</h2>
	    <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
		    <button class="btn" onclick="scoreTotal(this)" type="button">评分汇总</button>
	   	</div>
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
		       	<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${rp.packageId}" /></td>
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
  </body>
</html>
