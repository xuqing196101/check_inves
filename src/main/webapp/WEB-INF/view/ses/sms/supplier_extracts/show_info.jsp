<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
</head>
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
<body>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !='' }">
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑系统</a></li>
				<li><a href="#">供应商管理</a></li>
				<li class="active"><a href="#">供应商抽取</a></li>
				<li class="active"><a href="#">供应商抽取详情</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	</c:if>
	<!-- 修改订列表开始-->
	<div class="container">
		<div>
			<div class="headline-v2">
				<h2>供应商抽取表</h2>
			</div>
     		  <div class="content table_box">
				<table class="table table-bordered table-condensed">
					<tr>
						<td class="bggrey" width="100px">项目名称:</td>
						<td colspan="8" width="150px" id="tName">${ExpExtractRecord.projectName}</td>
					</tr>
					<tr>
						<td class="bggrey">抽取时间:</td>
						<td colspan="3" ><fmt:formatDate
								value="${ExpExtractRecord.extractionTime}"
								pattern="yyyy年MM月dd日   " /></td>
						<td class="bggrey">抽取地点:</td>
						<td colspan="4">${fn:replace(ExpExtractRecord.extractionSites,',','')}</td>
					</tr>
					<tr>
						<td  class="bggrey">抽取条件:<br>抽取数量:
						
						</td>
						<td colspan="8">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<c:forEach items="${conditionList}" var="con" varStatus="vs">
								  <c:if test="${con.listSupplierCondition != null && fn:length(con.listSupplierCondition) != 0}">
								    <p class="f16"><span class="b">包名：</span><span class="light_blue b">${con.name}</span></p>
									    <c:forEach items="${ con.listSupplierCondition}" var="conlist" varStatus="vs">
										    <p>第<span class="b orange">${(vs.index+1)}</span>次抽取，抽取条件如下：</p>
										    <p> 供应商所在地区【全国】</p>
		                                    <ol>
                                           <c:forEach items="${conlist.conTypes }" var="contypes">  
                                           <li>
                                           ${contypes.supplierType.name} 
                                                                                                                                ，专家数量【${contypes.supplierCount}】 }     
                                           </li>
                                           </c:forEach>
	                                    </ol>
									    </c:forEach>
								    </c:if>
								</c:forEach>

							</div>
						</td>
					</tr>
					<tr>
						<td colspan="9" class="bggrey" align="center">抽取记录</td>
					</tr>
					<tr>
						<td align="center">序号</td>
						<td align="center">供应商名称</td>
						<td align="center">包名称</td>
						<td align="center" >联系人</td>
						<td align="center">手机号</td>
						<td align="center" >传真</td>
						<td align="center">能否参加</td>
						<td align="center">不参加理由</td>
					</tr>
					<c:forEach items="${conditionList}" var="con" varStatus="vs">
						<c:forEach items="${con.listSupplierCondition}" var="conlist" varStatus="vse">
						  <c:forEach items="${conlist.extRelatesList}" var="ext" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${ext.supplier.supplierName}</td>
							      <td align="center">${con.name}</td>
								<td align="center">${ext.supplier.contactName}</td>
								<td align="center">${ext.supplier.mobile}</td>
								<td align="center">${ext.supplier.contactFax}</td>
								<td align="center"><c:if test="${ext.operatingType==1 }">
                                                                                         参加
                            </c:if> <c:if test="${ext.operatingType==2 }">
                                                                                         待定
                            </c:if> <c:if test="${ext.operatingType==3 }">
                                                                                     不参加                                                           
                            </c:if></td>
								<td align="center">${ext.reason}</td>
							</tr>
							</c:forEach>
						</c:forEach>
					</c:forEach>
					<tr>
						<td colspan="9"  class="bggrey" align="center">抽取人员</td>
					</tr>
					<tr>
						<td align="center" >序号</td>
						<td align="center" >姓名</td>
						<td align="center">手机号</td>
						<td align="center" >单位</td>
						<td align="center" >职务</td>
						<td colspan="2" align="center">签证</td>
					</tr>
					<tr>
						<td align="center">1</td>
						<td align="center">${ExpExtractRecord.perpleUser.id}</td>
						<td align="center">${ExpExtractRecord.perpleUser.mobile}</td>
						<td align="center">${ExpExtractRecord.perpleUser.org.name}</td>
						<td align="center">${ExpExtractRecord.perpleUser.duties}</td>
						<td colspan="2" align="center"></td>
					</tr>
					<tr>
						<td colspan="9" class="bggrey" align="center">监督人员</td>
					</tr>
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
              <td align="center">${tuser.relName}</td>
              <td align="center">${tuser.company}</td>
              <td align="center">${tuser.phone}</td>
              <td align="center">${tuser.duties}</td> 
              <td colspan="2" align="center"></td>
            </tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-12 col-xs-12 col-sm-12">
				<button class="btn btn-windows back" onclick="history.go(-1)"
					type="button">返回</button>
		</div>
	</div>
</body>
</html>
