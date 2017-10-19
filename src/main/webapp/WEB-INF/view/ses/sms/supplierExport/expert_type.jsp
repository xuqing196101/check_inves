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
						<table id="expTableFormal"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" width="10%" rowspan="2">采购机构</th>
										<th class="info" colspan="6">初审合格</th>
										<th class="info" colspan="6">已入库</th>
										<th class="info" colspan="6">复查合格</th>
									</tr>
									<tr>
									  <th>小计</th>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物资服务经济</th>
									  <th>工程经济</th>
									  <th>小计</th>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物资服务经济</th>
									  <th>工程经济</th>
									  <th>小计</th>
									  <th>物资技术</th>
									  <th>工程技术</th>
									  <th>服务技术</th>
									  <th>物资服务经济</th>
									  <th>工程经济</th>
									</tr>
								</thead>
								<c:set value="0" var="CSHJ"></c:set>
								<c:set value="0" var="CSWZJS"></c:set>
								<c:set value="0" var="CSGCJS"></c:set>
								<c:set value="0" var="CSFWJS"></c:set>
								<c:set value="0" var="CSWZFWJJ"></c:set>
								<c:set value="0" var="CSGCJJ"></c:set>
								<c:set value="0" var="RKHJ"></c:set>
								<c:set value="0" var="RKWZJS"></c:set>
								<c:set value="0" var="RKGCJS"></c:set>
								<c:set value="0" var="RKFWJS"></c:set>
								<c:set value="0" var="RKWZFWJJ"></c:set>
								<c:set value="0" var="RKGCJJ"></c:set>
								<c:set value="0" var="FCHJ"></c:set>
								<c:set value="0" var="FCWZJS"></c:set>
								<c:set value="0" var="FCGCJS"></c:set>
								<c:set value="0" var="FCFWJS"></c:set>
								<c:set value="0" var="FCWZFWJJ"></c:set>
								<c:set value="0" var="FCGCJJ"></c:set>
								<c:forEach items="${list}" var="su" varStatus="vs">
								<c:set value="${CSHJ+su['CSHJ'] }" var="CSHJ"></c:set>
								<c:set value="${CSWZJS+su['CSWZJS'] }" var="CSWZJS"></c:set>
								<c:set value="${CSGCJS+su['CSGCJS'] }" var="CSGCJS"></c:set>
								<c:set value="${CSFWJS+su['CSFWJS'] }" var="CSFWJS"></c:set>
								<c:set value="${CSWZFWJJ+su['CSWZFWJJ'] }" var="CSWZFWJJ"></c:set>
								<c:set value="${CSGCJJ+su['CSGCJJ'] }" var="CSGCJJ"></c:set>
								<c:set value="${RKHJ+su['RKHJ'] }" var="RKHJ"></c:set>
								<c:set value="${RKWZJS+su['RKWZJS'] }" var="RKWZJS"></c:set>
								<c:set value="${RKGCJS+su['RKGCJS'] }" var="RKGCJS"></c:set>
								<c:set value="${RKFWJS+su['RKFWJS'] }" var="RKFWJS"></c:set>
								<c:set value="${RKWZFWJJ+su['RKWZFWJJ'] }" var="RKWZFWJJ"></c:set>
								<c:set value="${RKGCJJ+su['RKGCJJ'] }" var="RKGCJJ"></c:set>
								<c:set value="${FCHJ+su['FCHJ'] }" var="FCHJ"></c:set>
								<c:set value="${FCWZJS+su['FCWZJS'] }" var="FCWZJS"></c:set>
								<c:set value="${FCGCJS+su['FCGCJS'] }" var="FCGCJS"></c:set>
								<c:set value="${FCFWJS+su['FCFWJS'] }" var="FCFWJS"></c:set>
								<c:set value="${FCWZFWJJ+su['FCWZFWJJ'] }" var="FCWZFWJJ"></c:set>
								<c:set value="${FCGCJJ+su['FCGCJJ'] }" var="FCGCJJ"></c:set>
								 <tr>
								  <td>${vs.index+1} </td>
								  <td>${su['SHORTNAME']}</td>
								  <td>${su['CSHJ']}</td>
								  <td>${su['CSWZJS'] }</td>
								  <td>${su['CSGCJS']}</td>
								  <td>${su['CSFWJS']}</td>
								  <td>${su['CSWZFWJJ'] }</td>
								  <td>${su['CSGCJJ']}</td>
								  <td>${su['RKHJ']}</td>
								  <td>${su['RKWZJS'] }</td>
								  <td>${su['RKGCJS']}</td>
								  <td>${su['RKFWJS']}</td>
								  <td>${su['RKWZFWJJ'] }</td>
								  <td>${su['RKGCJJ']}</td>
								  <td>${su['FCHJ']}</td>
								  <td>${su['FCWZJS'] }</td>
								  <td>${su['FCGCJS']}</td>
								  <td>${su['FCFWJS']}</td>
								  <td>${su['FCWZFWJJ'] }</td>
								  <td>${su['FCGCJJ']}</td>
								 </tr>
								</c:forEach>
								<tr>
								  <td colspan="2">合计</td>
								  <td>${CSHJ }</td>
								  <td>${CSWZJS }</td>
								  <td>${CSGCJS }</td>
								  <td>${CSFWJS }</td>
								  <td>${CSWZFWJJ }</td>
								  <td>${CSGCJJ }</td>
								  <td>${RKHJ }</td>
								  <td>${RKWZJS }</td>
								  <td>${RKGCJS }</td>
								  <td>${RKFWJS }</td>
								  <td>${RKWZFWJJ }</td>
								  <td>${RKGCJJ }</td>
								  <td>${FCHJ }</td>
								  <td>${FCWZJS }</td>
								  <td>${FCGCJS }</td>
								  <td>${FCFWJS }</td>
								  <td>${FCWZFWJJ }</td>
								  <td>${FCGCJJ }</td>
								</tr>
							</table>
						</div>
					
</body>

</html>