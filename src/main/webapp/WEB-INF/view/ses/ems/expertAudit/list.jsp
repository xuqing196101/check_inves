<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
			var state = $("#" + id +"").parent("tr").find("td").eq(8).text().trim();
			var isExtract = $("#" + id + "_isExtract").text();
			if(state == "初审通过" || state == "初审未通过" || state == "退回修改" || state == "初审退回" || state == "复审通过" || state == "复审踢除") {
				layer.msg("请选择待审核项 !", {
					offset: '100px',
				});
				return;
			}
			/* //抽取之后的才能复核
			if(isExtract != 1 && state == "待复核") {
				layer.msg("该专家未抽取 !", {
					offset: '100px',
				});
				return;
			} */
			$("input[name='expertId']").val(id);
			$("#shenhe_form_id").submit();
		}
		
		 //重置搜索栏
		
		  function resetForm(){
		      $("input[name='relName']").val("");
		        //还原select下拉列表只需要这一句
		      $("#status option:selected").removeAttr("selected");
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
			<h2>专家审核列表</h2>
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
						<label class="fl">专家姓名：</label>
						<input type="text" name="relName" value="${relName }">
					</li>
					<li>
						<label class="fl">状态：</label>
						<select name="status" class="w178" id="status">
			        <option value="">全部</option>
		          <c:if test="${sign == 1}">
		            <option <c:if test="${state eq '0'}">selected</c:if> value="0">待审核</option>
		            <option <c:if test="${state eq '1'}">selected</c:if> value="1">初审通过</option>
		            <option <c:if test="${state eq '3'}">selected</c:if> value="3">退回修改</option>
		            <option <c:if test="${state eq '2'}">selected</c:if> value="2">初审未通过</option>
		          </c:if>
		          <c:if test="${sign == 2}">
		            <option <c:if test="${state eq '4'}">selected</c:if> value="4">待复审</option>
		            <option <c:if test="${state eq '3'}">selected</c:if> value="5">复审通过</option>
		            <option <c:if test="${state eq '6'}">selected</c:if> value="6">复审踢除</option>
		          </c:if>
			      </select> 
					</li>
				</ul>
				<input class="btn" value="查询" type="submit">
				<button onclick="resetForm();" class="btn" type="button">重置</button>
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
							<c:if test="${(expert.status eq '0')}">
								<td  class="tc"><span class="label rounded-2x label-u">待初审</span></td>
							</c:if>
							<c:if test="${expert.status eq '1' }">
								<td  class="tc"><span class="label rounded-2x label-dark">初审通过</span></td>
							</c:if>
							<c:if test="${expert.status eq '2' }">
								<td  class="tc"><span class="label rounded-2x label-dark">初审未通过</span></td>
							</c:if>
							<c:if test="${expert.status eq '3' }">
								<td  class="tc"><span class="label rounded-2x label-dark">退回修改</span></td>
							</c:if>
							<c:if test="${expert.status eq '4' }">
								<td  class="tc"><span class="label rounded-2x label-u">待复审</span></td>
							</c:if>
							<c:if test="${expert.status eq '5' }">
								<td  class="tc"><span class="label rounded-2x label-dark">复审通过</span></td>
							</c:if>
							<c:if test="${expert.status eq '6' }">
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