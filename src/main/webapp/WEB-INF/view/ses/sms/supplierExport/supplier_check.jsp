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
						<table id="supTable"
								class="table table-bordered table-condensed table-hover table-striped">
								<thead >
									<tr>
										<th class="w50 info" rowspan="2">序号</th>
										<th class="info" rowspan="2" width="10%">采购机构</th>
										<th class="info" colspan="10">审核（入库）阶段</th>
										<th class="info" colspan="4">复核阶段</th>
										<th class="info" colspan="4">实地考察阶段</th>
									</tr>
									<tr>
									  <th class="info">提交审核</th>
										<th class="info">待审核</th>
										<th class="info">退回再审核</th>
										<th class="info">审核中</th>
										<th class="info">退回修改</th>
										<th class="info">预审核结束</th>
										<th class="info">审核不通过</th>
										<th class="info">公示中</th>
										<th class="info">异议处理</th>
										<th class="info">入库（待复核）</th>
										<th class="info">复核中</th>
										<th class="info">预复核结束</th>
										<th class="info">复核不合格</th>
										<th class="info">复核合格（待考察）</th>
										<th class="info">考察中</th>
										<th class="info">预考察结束</th>
										<th class="info">考察不合格</th>
										<th class="info">考察合格</th>
									</tr>
									</thead>
									<c:set value="0" var="TJSH"></c:set>
									<c:set value="0" var="DSH"></c:set>
									<c:set value="0" var="THZSH"></c:set>
									<c:set value="0" var="SHZ"></c:set>
									<c:set value="0" var="THXG"></c:set>
									<c:set value="0" var="YSHJS"></c:set>
									<c:set value="0" var="SHBTG"></c:set>
									<c:set value="0" var="GSZ"></c:set>
									<c:set value="0" var="RKDFH"></c:set>
									<c:set value="0" var="FHZ"></c:set>
									<c:set value="0" var="YFHJS"></c:set>
									<c:set value="0" var="FHBHG"></c:set>
									<c:set value="0" var="FHHGDKC"></c:set>
									<c:set value="0" var="KCZ"></c:set>
									<c:set value="0" var="YKCJS"></c:set>
									<c:set value="0" var="KCBHG"></c:set>
									<c:set value="0" var="KCHG"></c:set>
									<c:forEach items="${list}" var="su" varStatus="vs">
									<c:set value="${TJSH+su['TJSH']}" var="TJSH"></c:set>
									<c:set value="${DSH+su['DSH']}" var="DSH"></c:set>
									<c:set value="${THZSH+su['THZSH']}" var="THZSH"></c:set>
									<c:set value="${SHZ+su['SHZ']}" var="SHZ"></c:set>
									<c:set value="${THXG+su['THXG']}" var="THXG"></c:set>
									<c:set value="${YSHJS+su['YSHJS']}" var="YSHJS"></c:set>
									<c:set value="${SHBTG+su['SHBTG']}" var="SHBTG"></c:set>
									<c:set value="${GSZ+su['GSZ']}" var="GSZ"></c:set>
									<c:set value="${RKDFH+su['RKDFH']}" var="RKDFH"></c:set>
									<c:set value="${FHZ+su['FHZ']}" var="FHZ"></c:set>
									<c:set value="${YFHJS+su['YFHJS']}" var="YFHJS"></c:set>
									<c:set value="${FHBHG+su['FHBHG']}" var="FHBHG"></c:set>
									<c:set value="${FHHGDKC+su['FHHGDKC']}" var="FHHGDKC"></c:set>
									<c:set value="${KCZ+su['KCZ']}" var="KCZ"></c:set>
									<c:set value="${YKCJS+su['YKCJS']}" var="YKCJS"></c:set>
									<c:set value="${KCBHG+su['KCBHG']}" var="KCBHG"></c:set>
									<c:set value="${KCHG+su['KCHG']}" var="KCHG"></c:set>
										 <tr>
										  <td>${vs.index+1} </td>
										  <td>${su['SHORTNAME']}</td>
										  <td>${su['TJSH']}</td>
										  <td>${su['DSH']}</td>
										  <td>${su['THZSH']}</td>
										  <td>${su['SHZ']}</td>
										  <td>${su['THXG']}</td>
										  <td>${su['YSHJS']}</td>
										  <td>${su['SHBTG']}</td>
										  <td>${su['GSZ']}</td>
										  <td></td>
										  <td>${su['RKDFH']}</td>
										  <td>${su['FHZ']}</td>
										  <td>${su['YFHJS']}</td>
										  <td>${su['FHBHG']}</td>
										  <td>${su['FHHGDKC']}</td>
										  <td>${su['KCZ']}</td>
										  <td>${su['YKCJS']}</td>
										  <td>${su['KCBHG']}</td>
										  <td>${su['KCHG']}</td>
										 </tr>
								</c:forEach>
								<tr>
									  <td colspan="2">合计</td>
									  <td>${TJSH}</td>
									  <td>${DSH}</td>
									  <td>${THZSH}</td>
									  <td>${SHZ}</td>
									  <td>${THXG}</td>
									  <td>${YSHJS}</td>
									  <td>${SHBTG}</td>
									  <td>${GSZ}</td>
									  <td></td>
									  <td>${RKDFH}</td>
									  <td>${FHZ}</td>
									  <td>${YFHJS}</td>
									  <td>${FHBHG}</td>
									  <td>${FHHGDKC}</td>
									  <td>${KCZ}</td>
									  <td>${YKCJS}</td>
									  <td>${KCBHG}</td>
									  <td>${KCHG}</td>
									</tr>
								</table>
						</div>
					
</body>

</html>