<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		$(function(){
		 laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${result.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${result.total}",
		    startRow: "${result.startRow}",
		    endRow: "${result.endRow}",
		    groups: "${result.pages}">=5?5:"${result.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				return "${result.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pageNum").val(e.curr);
		        	$("#formSearch").submit();
		        }
		  		}
			});
		});
	</script>
	
	<script type="text/javascript">
		//审核
		function shenhe() {
			var size = $(":radio:checked").size();
			if(!size) {
				layer.msg("请选择专家 !", {
					offset: '100px',
				});
				return;
			}
			var id = $(":radio:checked").val();
			var state = $("#" + id).parent("tr").find("td").eq(8).text().trim();
			var isExtract = $("#" + id + "_isExtract").text();
			if(state == "已审核" || state == "初审未通过" || state == "复核踢出" || state == "初审退回") {
				layer.msg("请选择待审核项 !", {
					offset: '100px',
				});
				return;
			}
			//抽取之后的才能复核
			if(isExtract != 1 && state == "待复核") {
				layer.msg("该供应商未抽取 !", {
					offset: '100px',
				});
				return;
			}
			$("input[name='expertId']").val(id);
			$("#shenhe_form_id").submit();
		}
</script>
	
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li>
					<a href="javascript:void(0)">首页</a>
				</li>
				<li>
					<a href="javascript:void(0)">支撑系统</a>
				</li>
				<li>
					<a href="javascript:void(0)">专家管理</a>
				</li>
				<li>
					<a href="javascript:void(0)">专家审核</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 我的订单页面开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>评标专家列表</h2>
		</div>
		<div class="search_detail">
			<form id="shenhe_form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
			  <input name="expertId" type="hidden" />
	  	</form>
			<form action="${pageContext.request.contextPath}/expertAudit/list.html" method="post" id="formSearch" class="mb0">
				<input type="hidden" name="pageNum" id="pageNum">
				<input type="hidden" name="sign" value="${sign }">
				<ul class="demand_list">
				
					<li>
						<label class="fl">姓名：</label><span><input type="text" id="relName" name="relName" value=""></span>
					</li>
					<%-- <li>
						<label class="fl">来源：</label>
							<span class="fl">
								<select name="expertsFrom" id="expertsFrom">
									<option selected="selected" value="">-请选择-</option>
									<c:forEach items="${lyTypeList }" var="ly" varStatus="vs">
										<option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
									</c:forEach>
								</select>
							</span>
					</li> --%>
					<%-- <li>
						<label class="fl">状态：</label>
						<select name="status" id="status">
							<option selected="selected" value=''>-请选择-</option>
							<option <c:if test="${expert.status =='0' }">selected</c:if> value="0">未审核</option>
							<option <c:if test="${expert.status =='1' }">selected</c:if> value="1">审核通过</option>
							<option <c:if test="${expert.status =='2' }">selected</c:if> value="2">审核未通过</option>
							<option <c:if test="${expert.status =='3' }">selected</c:if> value="3">退回修改</option>
						</select>
					</li> --%>
					<%-- <li>
						<label class="fl">专家类型：</label>
						<!--<span class="fl">-->
						<select name="expertsTypeId" id="expertsTypeId">
							<option selected="selected" value=''>-请选择-</option>
							<option <c:if test="${expert.expertsTypeId =='1' }">selected</c:if> value="1">技术</option>
							<option <c:if test="${expert.expertsTypeId =='3' }">selected</c:if> value="3">经济</option>
						</select>
						<!--</span>-->
					</li> --%>
				</ul>
				<input class="btn" value="查询" type="submit">
				<input class="btn btn-windows reset" id="button" onclick="clearSearch();" value="重置" type="reset">
			</form>
			</div>
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w40">选择</th>
							<th class="info w50">序号</th>
							<th class="info">专家姓名</th>
							<th class="info">性别</th>
							<!-- <th class="info">类型</th> -->
							<th class="info">毕业院校及专业</th>
							<th class="info">工作单位</th>
							<th class="info">创建时间</th>
							<th class="info">诚信积分</th>
							<th class="info">审核状态</th>
						</tr>
					</thead>
					<c:forEach items="${expertList}" var="expert" varStatus="vs">
						<tr>
							<td class="tc w40"><input name="id" type="radio" value="${expert.id}"></td>
							<td class="tc w50">${vs.count}</td>
							<td  class="tc">${expert.relName}</td>
							<td  class="tc">${expert.sex}</td>
							<%-- <c:if test="${expert.expertsTypeId ==null}">
								<td  class="tc"></td>
							</c:if>
							<c:if test="${expert.expertsTypeId =='1' || expert.expertsTypeId ==1}">
								<td  class="tc">技术</td>
							</c:if>
							<c:if test="${expert.expertsTypeId =='2' || expert.expertsTypeId ==2}">
								<td  class="tc">法律</td>
							</c:if>
							<c:if test="${expert.expertsTypeId =='3' || expert.expertsTypeId ==3}">
								<td  class="tc">经济</td>
							</c:if> --%>
							<td  class="tc">${expert.graduateSchool }</td>
							<td  class="tc">${expert.workUnit }</td>
							<td  class="tc">
								<fmt:formatDate type='date' value='${expert.createdAt }' dateStyle="default" pattern="yyyy-MM-dd" />
							</td>
							<td  class="tc" id="${expert.id}">${expert.honestyScore }</td>
							<c:if test="${expert.status eq '0' }">
								<td  class="tc"><span class="label rounded-2x label-dark">待初审</span></td>
							</c:if>
							<c:if test="${expert.status eq '1' }">
								<td  class="tc"><span class="label rounded-2x label-u">待复审</span></td>
							</c:if>
							<c:if test="${expert.status eq '2' }">
								<td  class="tc"><span class="label rounded-2x label-u">初审未通过</span></td>
							</c:if>
							<c:if test="${expert.status eq '3' }">
								<td  class="tc"><span class="label rounded-2x label-u">初审退回</span></td>
							</c:if>
							<c:if test="${expert.status eq '4' }">
								<td  class="tc"><span class="label rounded-2x label-dark">复审通过</span></td>
							</c:if>
							<c:if test="${expert.status eq '5' }">
								<td  class="tc"><span class="label rounded-2x label-dark">复审踢除</span></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>