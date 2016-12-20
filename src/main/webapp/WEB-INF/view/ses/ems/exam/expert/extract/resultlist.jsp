<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>抽取</title>
<link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
	
<script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listStationMessage.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${listStationMessage.pages}">=3?3:"${listStationMessage.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
		        return "${listStationMessage.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pages").val(e.curr);
		        	$("form:first").submit();
		        }
		    }
		});
  });
  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName("chkItem");
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
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${pageContext.request.contextPath}/StationMessage/showStationMessage.do?id="+id+"&&type='edit'";
		}else if(id.length>1){  
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="${pageContext.request.contextPath}/StationMessage/showInsertSM.do";
    }

    function continues(id){
    	   window.location.href="${pageContext.request.contextPath}/SupplierExtracts/conditions.do?id="+id;
    }
   
  </script>
  </head>
<body>

	<!--=== Content Part ===-->
	<div class="container content height-350">
		<div class="row">
			<!-- Begin Content -->
			<div class="col-md-12" id="count" style="min-height: 400px;">
				<div id="extcontype">
					<c:forEach var="con" items="${extConType}">
					
					
					   类型:<c:if test="${con.expertsTypeId==1 }">
						技术,
					</c:if>
						<c:if test="${con.expertsTypeId==2}">
					    法律,
					</c:if>
						<c:if test="${con.expertsTypeId==3 }">
						商务,
					</c:if>
						<c:if test="${con.categoryName != null && con.categoryName != ''}">
				                  抽取品目 :${fn:replace(con.categoryName, "^", ",")}
					</c:if>
						<c:if
							test="${con.isMulticondition!=null && con.isMulticondition!= '' }">
							<c:if test="${con.isMulticondition==1}">
                                                                      满足一个条件,
                    </c:if>
							<c:if test="${con.isMulticondition==2}">
                                                                     满足多个条件,
                    </c:if>
						</c:if>
					   抽取数量${con.alreadyCount}/${con.expertsCount }
					          
						<br />

					</c:forEach>
				</div>
				<div class="clear"></div>
				<table id="table" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">专家姓名</th>
							<th class="info">联系电话</th>
							<th class="info">工作单位名称</th>
							<th class="info">专家技术职称</th>
							<th class="info">操作</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${extRelateListYes}" var="listyes"
							varStatus="vs">
							<tr class='cursor '>
								<td class='tc'>${vs.index+1}</td>
								<td class='tc'>${listyes.expert.relName}</td>
								<td class='tc'>${listyes.expert.mobile}</td>
								<td class='tc'>${listyes.expert.workUnit}</td>
								<td class='tc'>${listyes.expert.professTechTitles}</td>
								<td class='tc'><select id='select'
									onchange='operation(this);'>
										<c:choose>
											<c:when test="${listyes.operatingType==1}">
												<option selected="selected" disabled="disabled"
													value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
											</c:when>
											<c:when test="${listyes.operatingType==2}">
												<option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
												<option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
												<option selected="selected" disabled="disabled"
													value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
											</c:when>
											<c:when test="${listyes.operatingType==3}">
												<option selected="selected" disabled="disabled"
													value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
											</c:when>
											<c:otherwise>
												<option>请选择</option>
												<option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
												<option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
												<option value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
											</c:otherwise>
										</c:choose>
								</select></td>
							</tr>
						</c:forEach>
						<c:forEach items="${extRelateListNo }" var="listno" varStatus="vs">
							<tr class='cursor'>
								<td class='tc'>${(vs.index+1)+1}</td>
								<td class='tc'>*****</td>
								<td class='tc'>*****</td>
								<td class='tc'>*****</td>
								<td class='tc'>*****</td>
								<td class='tc'>请选择</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- End Content -->
	</div>
	<!--/container-->
	<!--=== End Content Part ===-->

</body>
</html>
