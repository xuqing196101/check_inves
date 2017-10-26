<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
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
  		var projectId = $("#projectId").val();
  		$.ajax({
			url: "${pageContext.request.contextPath}/packageExpert/getMethodType.do",
			data: {"projectId" : projectId, "packageId" : packageId},
			success: function(data){
				if (data == '2') {
					$("#tab-6").load('${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId='+packageId+'&projectId='+projectId);
				} else if (data == '1') {
					$("#tab-6").load('${pageContext.request.contextPath}/packageExpert/checkAuditView.html?packageId='+packageId+'&projectId='+projectId);
				}else {
					layer.msg(data);
				}
			}
		});
  		//window.location.href='${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId='+packageId+'&projectId=${projectId}';
  	}
  </script>
</head>
  <body>
	    <h2 class="list_title">经济技术评审</h2>
	    <!-- <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
		    <button class="btn" onclick="scoreTotal(this)" type="button">评分汇总</button>
	   	</div> -->
   		<input type="hidden" id="projectId" value="${projectId}">
    	<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			<tr>
			  <!-- <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
			  <th class="w50 info">序号</th>
			  <th class="info">包名</th>
			  <th class="info">状态</th>
			  <th class="info">评审 进度</th>
			  <th class="info">操作</th>
			</tr>
			</thead>
			<c:set value="false" var="flg"></c:set>
			<c:forEach items="${reviewProgressList}" var="rp" varStatus="vs">
		       <tr>
		       	<%-- <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${rp.packageId}" /></td> --%>
		        <td class="tc w30">${vs.count} </td>
		        <td class="tc">${rp.packageName}
		          <c:set value="false" var="flg"></c:set>
		         <c:forEach items="${packageList}" var="pa">
		            <c:if test="${pa.id==rp.packageId }">
		              <c:if test="${pa.projectStatus eq 'YZZ'}">
		                <span class="star_red">[已终止]</span>
		                <c:set value="true" var="flg"></c:set>
		              </c:if>
		              <c:if test="${pa.projectStatus eq 'ZJZXTP'}">
		                <span class="star_red">[已转竞谈]</span>
		                <c:set value="true" var="flg"></c:set>
		              </c:if>
		              <c:if test="${pa.projectStatus eq 'ZJTSHZ'}">
		                <span class="star_red">[转竞谈审核中]</span>
		                <c:set value="true" var="flg"></c:set>
		              </c:if>
		            </c:if>
		         </c:forEach>
		        </td>
		        <td class="tc">
		          <c:if test="${rp.auditStatus == 0}">符合性和资格性检查未开始</c:if>
		          <c:if test="${rp.auditStatus == 1}">符合性和资格性检查中</c:if>
		          <c:if test="${rp.auditStatus == 2}">符合性和资格性检查完成</c:if>
		          <c:if test="${rp.auditStatus == 3}">经济技术评审中</c:if>
		          <c:if test="${rp.auditStatus == 4}">经济技术评审完成</c:if>
		        </td>
			    <td class="tc">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${rp.scoreProgress*100}%;"> 
			             </div> 
			          </div>
					  <%-- <span class="fl padding-5">${rp.scoreProgress*100}%</span> --%>
					  <span class="fl padding-5">${rp.finishNum}人完成，${rp.noFinishNum}人未完成</span>
				  </div>
			    </td>
			    <td class="tc w100">
			    	<c:if test="${rp.auditStatus == 0 || rp.auditStatus == 1}">
			          	<input disabled="disabled" class="btn" <c:if test="${flg=='true' }">disabled="disabled" </c:if>  type="button" value="查看" onclick="scoreView('${rp.packageId}')">
			    	</c:if>
			    	<c:if test="${rp.auditStatus == 2 || rp.auditStatus == 3 || rp.auditStatus == 4}">
			          	<input class="btn" type="button" <c:if test="${flg eq 'true' }">disabled="disabled" </c:if> value="查看" onclick="scoreView('${rp.packageId}')">
			    	</c:if>
		        </td>
		      </tr>
			</c:forEach>
		</table>
  </body>
</html>
