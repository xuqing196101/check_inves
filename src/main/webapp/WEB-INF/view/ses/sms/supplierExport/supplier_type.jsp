<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
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

<script type="text/javascript">
		</script>
</head>

<body>
				<div class="content table_box">
						<table id="supTableFormal"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" width="10%" rowspan="2">采购机构</th>
										<th class="info" colspan="5">已入库</th>
										<th class="info" colspan="5">复核通过</th>
										<th class="info" colspan="5">考察合格</th>
									</tr>
									<tr>
									  <th>小计</th>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									  <th>小计</th>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									  <th>小计</th>
									  <th>物资生产</th>
									  <th>物资销售</th>
									  <th>工程</th>
									  <th>服务</th>
									</tr>
								</thead>
								<c:set value="0" var="RKHJ"></c:set>
								<c:set value="0" var="RKWZSC"></c:set>
								<c:set value="0" var="RKWZXS"></c:set>
								<c:set value="0" var="RKGC"></c:set>
								<c:set value="0" var="RKFW"></c:set>
								<c:set value="0" var="FHHJ"></c:set>
								<c:set value="0" var="FHWZSC"></c:set>
								<c:set value="0" var="FHWZXS"></c:set>
								<c:set value="0" var="FHGC"></c:set>
								<c:set value="0" var="FHFW"></c:set>
								<c:set value="0" var="KCHJ"></c:set>
								<c:set value="0" var="KCWZSC"></c:set>
								<c:set value="0" var="KCWZXS"></c:set>
								<c:set value="0" var="KCGC"></c:set>
								<c:set value="0" var="KCFW"></c:set>
								<c:forEach items="${list}" var="su" varStatus="vs">
								<c:set value="${RKHJ+su['RKHJ']}" var="RKHJ"></c:set>
								<c:set value="${RKWZSC+su['RKWZSC']}" var="RKWZSC"></c:set>
								<c:set value="${RKWZXS+su['RKWZXS']}" var="RKWZXS"></c:set>
								<c:set value="${RKGC+su['RKGC']}" var="RKGC"></c:set>
								<c:set value="${RKFW+su['RKFW']}" var="RKFW"></c:set>
								<c:set value="${FHHJ+su['FHHJ']}" var="FHHJ"></c:set>
								<c:set value="${FHWZSC+su['FHWZSC']}" var="FHWZSC"></c:set>
								<c:set value="${FHWZXS+su['FHWZXS']}" var="FHWZXS"></c:set>
								<c:set value="${FHGC+su['FHGC']}" var="FHGC"></c:set>
								<c:set value="${FHFW+su['FHFW']}" var="FHFW"></c:set>
								<c:set value="${KCHJ+su['KCHJ']}" var="KCHJ"></c:set>
								<c:set value="${KCWZSC+su['KCWZSC']}" var="KCWZSC"></c:set>
								<c:set value="${KCWZXS+su['KCWZXS']}" var="KCWZXS"></c:set>
								<c:set value="${KCGC+su['KCGC']}" var="KCGC"></c:set>
								<c:set value="${KCFW+su['KCFW']}" var="KCFW"></c:set>
								 <tr>
								  <td>${vs.index+1} </td>
								  <td>${su['SHORTNAME']}</td>
								  <td>${su['RKHJ']}</td>
								  <td>${su['RKWZSC'] }</td>
								  <td>${su['RKWZXS']}</td>
								  <td>${su['RKGC']}</td>
								  <td>${su['RKFW'] }</td>
								  <td>${su['FHHJ']}</td>
								  <td>${su['FHWZSC']}</td>
								  <td>${su['FHWZXS']}</td>
								  <td>${su['FHGC']}</td>
								  <td>${su['FHFW']}</td>
								  <td>${su['KCHJ']}</td>
								  <td>${su['KCWZSC']}</td>
								  <td>${su['KCWZXS']}</td>
								  <td>${su['KCGC']}</td>
								  <td>${su['KCFW']}</td>
								 </tr>
								</c:forEach>
								<tr>
								  <td colspan="2">合计</td>
								  <td>${RKHJ}</td>
								  <td>${RKWZSC}</td>
								  <td>${RKWZXS}</td>
								  <td>${RKGC}</td>
								  <td>${RKFW}</td>
								  <td>${FHHJ}</td>
								  <td>${FHWZSC}</td>
								  <td>${FHWZXS}</td>
								  <td>${FHGC}</td>
								  <td>${FHFW}</td>
								  <td>${KCHJ}</td>
								  <td>${KCWZSC}</td>
								  <td>${KCWZXS}</td>
								  <td>${KCGC}</td>
								  <td>${KCFW}</td>
								</tr>
							</table>
						</div>
					
</body>

</html>