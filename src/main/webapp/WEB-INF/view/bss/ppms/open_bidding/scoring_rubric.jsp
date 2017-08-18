<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="${pageContext.request.contextPath}/">
    <%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
    $(function(){
    	if ($("#tipMsg").val() == "noSecond") {
    		$("#tipMsg").val("");
			layer.alert("请先完成经济和技术评审细则的编写",{offset: '50px'});
		}
		if ($("#tipMsg").val() == "noThired") {
            $("#tipMsg").val("");
            layer.alert("综合评分法必须有且只有一个价格评审数据.",{offset: '50px'});
        }
    	var packageId=	$("input[name='packageId']").val();
    	var flag="${flag}";
    	if(flag=="success"){
    		layer.msg("关联成功",{offset: ['222px', '390px']});
    	}
    })
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
        function addMarkTerm(){
	    	var id=[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
			}); 
			if(id.length==1){
				window.location.href="${pageContext.request.contextPath}/intelligentScore/list.do?id="+id[0]+"&projectId=${projectId}";
			}else if(id.length>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("至少选择一个",{offset: ['222px', '390px'], shade:0.01});
			}
    	}
	 	function addBidMethod(projectId){
	 		var id=[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
			}); 
			if(id.length==1){
				layer.open({
					  type: 2,
					  title: '制定评标办法',
					  shadeClose: true,
					  shade: 0.4,
					  area: ['980px', '30%'],
					  content: "${pageContext.request.contextPath}/intelligentScore/bidMethod?projectId="+projectId+"&packageId="+id[0] //iframe的url
				}); 
			}else if(id.length>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("至少选择一个",{offset: ['222px', '390px'], shade:0.01});
			}
		}
		function show(packageId){
			window.location.href = "${pageContext.request.contextPath}/intelligentScore/scoreModelList.html?packageId="+packageId+"&proid=${projectId}";
		}
		
	   function jump(url){
       	 $("#open_bidding_main").load(url);
	   }
	   
	   function confirmOk(obj, id, flowDefineId){
	      	   layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
		 			layer.close(index);
		 			$.ajax({
		 				url:"${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId="+id+"&flowDefineId="+flowDefineId,
		 				dataType: 'json',
		 	       		success:function(result){
		                   $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
		                    $("#queren").remove();
		                },
		                error: function(result){
		                    layer.msg("确认失败",{offset: '222px'});
		                }
		 	       	});
		 		});
	      }
	      
	  //编辑模板内容
    function editPackageFirstAudit(packageId,projectId){
        window.location.href = "${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+'${flowDefineId}';
    }
    
    function editScoreMehtod(packageId,projectId) {
    	window.location.href = "${pageContext.request.contextPath}/intelligentScore/editScoreMethod.html?packageId="+packageId+"&projectId="+projectId;
    }
    function addScoreMethod(packageId,projectId) {
    	window.location.href = "${pageContext.request.contextPath}/intelligentScore/addScoreMethod.html?packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+'${flowDefineId}';
    }
    function show(packageId, projectId) {
    	var ok = "${project.confirmFile}"
        
            window.location.href = "${pageContext.request.contextPath}/intelligentScore/showScoreMethod.html?packageId="+packageId+"&status="+ok+"&projectId="+projectId+"&flowDefineId="+'${flowDefineId}';
         
    }
    function view(packageId,projectId,flag){
    	if (flag == 0) {
			layer.msg("未选择评分办法",{offset: '222px'});
		} else {
	    	window.open("${pageContext.request.contextPath}/intelligentScore/viewModel.html?packageId="+packageId+"&projectId="+projectId);   
		}
    }
</script>
  </head>
  
  <body>
	<div class="col-md-12 p0">
		<ul class="flow_step">
			<c:if test="${ope != 'view' }">
			<li><a
				href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
				<i></i>
			</li>

			<%-- <li><a
				href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、符合性关联</a>
				<i></i>
			</li> --%>
			<li class="active"><a
				href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
				<i></i>
			</li>
			<li><a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
			     03、采购文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
			</a>
			  <i></i>
			 </li>
			 <li>
			   <a  href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、审核意见</a>
			 </li>
			</c:if>
			<c:if test="${ope == 'view' }">
	   	 	<li >
			   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${projectId}&flowDefineId=${flowDefineId }" >01、资格性和符合性审查</a>
			   <i></i>
			 </li>
			<%--  <li>
			   <a href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${projectId}&flowDefineId=${flowDefineId }">02、符合性关联</a>
			   <i></i>							  
			 </li> --%>
		     <li class="active">
			   <a  href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${projectId}&flowDefineId=${flowDefineId }">02、经济和技术评审细则</a>
			   <i></i>
			 </li>
			 <li>
			   <a  href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${projectId}&flowDefineId=${flowDefineId }" >
			     03、采购文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
			   </a>
			   <i></i>
			 </li>
			 <li>
			    <c:if test="${project.confirmFile == 0 || project.confirmFile==null}"><a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a></c:if>
			    <c:if test="${project.confirmFile == 1 }"><a>05、已确认</a></c:if>
			 </li>
	   	 	</c:if>
		</ul>
	</div>
		<!--第一个  -->
		<!--第二个 -->
			<!-- <h1 class="f16 count_flow">
				<i>01</i>制定评分细则                    
			</h1> -->
			<%-- <c:if test="${ope != 'view' }">
			<div class="fr pr15 mt10">
				<button class="btn btn-windows edit" onclick="addBidMethod('${projectId}');">制定评标办法</button> 
				
				<c:if test="${project.confirmFile==0 || project.confirmFile==null}">
					<button class="btn btn-windows edit" onclick="addMarkTerm();">制定评分细则</button>
				</c:if> 
		    </div>
		    </c:if> --%>
		    <input type="hidden" id="tipMsg" value="${msg}">
			<div id="package">
				<div>
					<h2 class="f16 count_flow"><span id="projectName">项目名称:${project.name }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="projectCode">项目编号:${project.projectNumber }</span></h2>
				</div>
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
						<!-- 	<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th> -->
							<th>序号</th>
							<th>包名</th>
							<th>状态</th>
							<th>评分办法</th>
							<th>操作</th>
							<!-- <th>评分办法名称</th>
							<th>评标方法</th> -->
						</tr>
					</thead>
					<c:forEach items="${packagesList }" var="p" varStatus="vs">
						<thead>
							<tr >
								<%-- <td class="tc w30"><input type="checkbox" value="${p.id }" name="chkItem">
								</td> --%>
								<td class="tc w50">${vs.index+1 }</td>
								<td class="tc">${p.name}
								<c:if test="${p.projectStatus=='YZZ'}">
                       <span class="star_red">[该包已终止]</span>
                    </c:if>
								</td>
								<td class="tc">
								<c:if test="${p.isEditSecond == 0}">
										请选择评分办法
								</c:if>
								<c:if test="${p.isEditSecond == 1}">
										未维护评审数据
								</c:if>
								<c:if test="${p.isEditSecond == 2}">
										已维护评审数据
								</c:if>
								</td>
								<td class="tc">
									<c:forEach items="${ddList}" var="list" varStatus="vs">
										<c:if test="${list.position == p.bidMethodTypeName }"><a onclick="show('${p.id}','${p.projectId }')" class="pointer">${list.name }</a></c:if>
										
									</c:forEach>
								</td>
								 <td class="tc">
								   <c:if test="${p.isHaveScoreMethod == 1 and project.confirmFile != 1}">
								       <!-- <button class="btn" type="button" onclick="editScoreMethod()">修改评分方法</button> -->
				                       <button class="btn" type="button" <c:if test="${p.projectStatus=='YZZ'}">disabled="disabled"</c:if> onclick="editPackageFirstAudit('${p.id}','${projectId}')">编辑</button>
								   </c:if>
								   <c:if test="${p.isHaveScoreMethod == 2 and project.confirmFile != 1}">
				                       <button class="btn" type="button" <c:if test="${p.projectStatus=='YZZ'}">disabled="disabled"</c:if> onclick="addScoreMethod('${p.id}','${projectId}')">选择评分办法</button>
								   </c:if>
								   <!-- 采购文件提交后不可修改 -->
								   <c:if test="${project.confirmFile == 1}">
				                       <button class="btn" type="button" <c:if test="${p.projectStatus=='YZZ'}">disabled="disabled"</c:if> onclick="view('${p.id}','${projectId}','${p.isEditSecond}')">查看</button>
								   </c:if>
				                </td>
								<%-- <td align="center">
									<a href="javascript:void(0)" onclick="show('${p.id}');">${p.bidMethodName }</a>
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${p.bidMethodTypeName==0  }">综合评标法</c:when>
										<c:when test="${p.bidMethodTypeName==1  }">最低评标法</c:when>
										<c:when test="${p.bidMethodTypeName==2  }">基准价评标法</c:when>
										<c:when test="${p.bidMethodTypeName==3  }">性价比评标法</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td> --%>
							</tr>
						</thead>
					</c:forEach>
				</table>
			</div>
			<div class="container clear margin-top-30" id="package"></div>
</body>
</html>
