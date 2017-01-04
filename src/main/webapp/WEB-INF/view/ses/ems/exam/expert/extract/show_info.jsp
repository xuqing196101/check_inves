<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	function cheClick() {
		var roleIds = "";
		var roleNames = "";
		$('input[name="chkItem"]:checked').each(function() {
			var idName = $(this).val();
			var arr = idName.split(";");
			roleIds += arr[0] + ",";
			roleNames += arr[1] + ",";
		});
		$("#roleId").val(roleIds.substr(0, roleIds.length - 1));
		$("#roleName").val(roleNames.substr(0, roleNames.length - 1));
	}
</script>
</head>
<body>
    <!--面包屑导航开始-->
    <c:if test="${typeclassId!=null && typeclassId !=''  }">
      <div class="margin-top-10 breadcrumbs ">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="#"> 首页</a>
            </li>
            <li>
              <a href="#">支撑环境系统</a>
            </li>
            <li>
              <a href="#">专家管理</a>
            </li>
            <li>
              <a href="#">专家抽取</a>
            </li>
            <li class="active">
              <a href="#">专家抽取列表</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>
    </c:if>
	<!-- 修改订列表开始-->
	<div class="container">
		<div>
			<div class="headline-v2">
				<h2>专家抽取表</h2>
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed">
					<tr>
						<td  class="bggrey" width="100px">项目名称:</td>
						<td colspan="8" width="150px" id="tName">${ExpExtractRecord.projectName}</td>
					</tr>
					<tr>
						<td  class="bggrey">抽取时间:</td>
						<td colspan="4"><fmt:formatDate
								value="${ExpExtractRecord.extractionTime}"
								pattern="yyyy年MM月dd日   " /></td>
						<td class="bggrey" >抽取地点:</td>
						<td colspan="3" >${fn:replace(ExpExtractRecord.extractionSites,',','')}</td>
					</tr>
					<tr>
						<td align="center" class="bggrey" height="300px;">抽取条件<br>抽取数量
						</td>
						<td colspan="8">
              <div class="col-md-12 col-xs-12 col-sm-12">
                <c:forEach items="${conditionList}" var="con" varStatus="vs">
                    <p class="f16"><span class="b">包名：</span><span class="light_blue b">${con.name}</span></p>
                      <c:forEach items="${ con.listExpExtCondition}" var="conlist" varStatus="vs">
                        <p>第<span class="b orange">${(vs.index+1)}</span>次抽取，抽取条件如下：</p>
                        <p> 专家所在地区【全国】</p>
                                        <ol>
                                            <c:forEach items="${conlist.conTypes }" var="contypes">
																	                      <li>{专家类型 
																	                    
																	                      <c:forEach items="${ddList}" var="type">
																	                       <c:if test="${type.id == contypes.expertsTypeId}">
																	                           ${type.name}
																	                       </c:if>
																	                      </c:forEach>
																	                 <c:if test="${contypes.categoryName != null && contypes.categoryName != '' }">
																	                  ，       采购类别【 ${fn:replace(contypes.categoryName,'^',',')}】
																	                 </c:if>
																	                         
																	                        】，专家数量【${contypes.expertsCount}】 }
                                      </li>
                                </c:forEach>
                                      </ol>
                      </c:forEach>
                </c:forEach>

              </div>
            </td>
					</tr>
					<tr>
						<td colspan="9" align="center" class="bggrey">抽取记录</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">专家名称</td>
						  <td align="center">包名称</td>
						<td align="center">联系人</td>
						<td align="center">手机号</td>
						<td align="center">传真</td>
						<td align="center">能否参加</td>
						<td align="center">不参加理由</td>
					</tr>
					<c:forEach items="${conditionList}" var="con" varStatus="vs">
					<c:forEach items="${con.listExpExtCondition}" var="pe" varStatus="vse">
						<c:forEach items="${pe.extRelatesList}" var="ext" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${ext.expert.relName}</td>
							    <td align="center">${con.name}</td>
								<td align="center">${ext.expert.relName}</td>
								<td align="center">${ext.expert.relName}</td>
								  <td align="center">${ext.expert.relName}</td>
								<td align="center"><c:if test="${ext.operatingType==1 }">
                                                                                         参加
                            </c:if>
                            <c:if test="${ext.operatingType==2 }">
                                                                                         待定
                            </c:if>
                             <c:if test="${ext.operatingType==3 }">
                                                                                     不参加                                                           
                            </c:if></td>
								<td align="center">${ext.reason}</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</c:forEach>
					<tr>
						<td colspan="9" align="center" class="bggrey">抽取人员</td>
					</tr>
					<tr>
					<td colspan="9" >
					 <table class="table table-bordered table-condensed">
					<tr>
						<td align="center">序号</td>
						<td align="center">姓名</td>
						  <td align="center">手机号</td>
						<td align="center">单位</td>
						<td align="center">职务</td>
						<td align="center">军衔</td>
						<td colspan="2" align="center">签字</td>
					</tr>
					<tr>
						<td align="center">1</td>
						<td align="center">${ExpExtractRecord.perpleUser.relName}</td>
						<td align="center">${ExpExtractRecord.perpleUser.mobile}</td>
						 <td align="center">${ExpExtractRecord.perpleUser.org.name}</td>
               <td align="center">${ExpExtractRecord.perpleUser.duties}</td>
						<td align="center">军23衔</td>
						<td colspan="2" align="center"></td>
					</tr>
					 </table>
					</td>
				</tr>
					<tr>
						<td colspan="9" align="center" class="bggrey">监督人员</td>
					</tr>
					<tr>
					<td colspan="9">
					 <table class="table table-bordered table-condensed">
						<tr>
							<td align="center">序号</td>
							<td align="center">姓名</td>
							<td align="center">单位</td>
							<td align="center">手机号</td>
							<td align="center">职务</td>
							<td colspan="2" align="center">签字</td>
						</tr>
						<c:forEach items="${listUser}" var="tuser" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${tuser.relName}</td>
	              <td align="center">${tuser.company}</td>
	              <td align="center">${tuser.phone}</td>
	              <td align="center">${tuser.duties}</td> 
	              <td colspan="2" align="center"></td>
							</tr>
						</c:forEach>
						  </table>
					</td>
					</tr>
			</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="fl padding-10">
				<button class="btn btn-windows git" onclick="history.go(-1)"
					type="button">返回</button>
			</div>
		</div>
	</div>
</body>
</html>
