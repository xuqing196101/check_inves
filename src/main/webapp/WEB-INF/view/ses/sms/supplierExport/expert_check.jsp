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
										<th class="info" colspan="8">初审阶段</th>
										<th class="info" colspan="8">复审（入库）阶段</th>
										<th class="info" colspan="5">复查阶段</th>
									</tr>
									<tr>
									  <th class="info">提交初审</th>
										<th class="info">待初审</th>
										<th class="info">退回再初审</th>
										<th class="info">初审中</th>
										<th class="info">初审退回修改</th>
										<th class="info">预初审结束</th>
										<th class="info">初审不合格</th>
										<th class="info">初审合格（待复核）</th>
										<th class="info">复审已分配</th>
										<th class="info">复审中</th>
										<th class="info">预复审结束</th>
										<th class="info">复审退回修改</th>
										<th class="info">复审不合格</th>
										<th class="info">公示中</th>
										<th class="info">异议处理</th>
										<th class="info">入库（待复查）</th>
										<th class="info">复查中</th>
										<th class="info">预复查结束</th>
										<th class="info">资料不全</th>
										<th class="info">复查不合格</th>
										<th class="info">复查合格</th>
									</tr>
									</thead>
									<c:set value="0" var="TJCS"></c:set>
									<c:set value="0" var="DCS"></c:set>
									<c:set value="0" var="THZCS"></c:set>
									<c:set value="0" var="CSZ"></c:set>
									<c:set value="0" var="CSTHXG"></c:set>
									<c:set value="0" var="YCSJS"></c:set>
									<c:set value="0" var="CSBHG"></c:set>
									<c:set value="0" var="CSHG"></c:set>
									<c:set value="0" var="FSYFP"></c:set>
									<c:set value="0" var="FSZ"></c:set>
									<c:set value="0" var="YFSJS"></c:set>
									<c:set value="0" var="FSTHXG"></c:set>
									<c:set value="0" var="FSBHG"></c:set>
									<c:set value="0" var="GSZ"></c:set>
									<c:set value="0" var="RKDFC"></c:set>
									<c:set value="0" var="FCZ"></c:set>
									<c:set value="0" var="ZLBQ"></c:set>
									<c:set value="0" var="FCBHG"></c:set>
									<c:set value="0" var="FCHG"></c:set>
									<c:forEach items="${list}" var="su" varStatus="vs">
									<c:set value="${TJCS+su['TJCS']}" var="TJCS"></c:set>
									<c:set value="${DCS+su['DCS']}" var="DCS"></c:set>
									<c:set value="${THZCS+su['THZCS']}" var="THZCS"></c:set>
									<c:set value="${CSZ+su['CSZ']}" var="CSZ"></c:set>
									<c:set value="${CSTHXG+su['CSTHXG']}" var="CSTHXG"></c:set>
									<c:set value="${YCSJS+su['YCSJS']}" var="YCSJS"></c:set>
									<c:set value="${CSBHG+su['CSBHG']}" var="CSBHG"></c:set>
									<c:set value="${CSHG+su['CSHG']}" var="CSHG"></c:set>
									<c:set value="${FSYFP+su['FSYFP']}" var="FSYFP"></c:set>
									<c:set value="${FSZ+su['FSZ']}" var="FSZ"></c:set>
									<c:set value="${YFSJS+su['YFSJS']}" var="YFSJS"></c:set>
									<c:set value="${FSTHXG+su['FSTHXG']}" var="FSTHXG"></c:set>
									<c:set value="${FSBHG+su['FSBHG']}" var="FSBHG"></c:set>
									<c:set value="${GSZ+su['GSZ']}" var="GSZ"></c:set>
									<c:set value="${RKDFC+su['RKDFC']}" var="RKDFC"></c:set>
									<c:set value="${FCZ+su['FCZ']}" var="FCZ"></c:set>
									<c:set value="${ZLBQ+su['ZLBQ']}" var="ZLBQ"></c:set>
									<c:set value="${FCBHG+su['FCBHG']}" var="FCBHG"></c:set>
									<c:set value="${FCHG+su['FCHG']}" var="FCHG"></c:set>
										 <tr>
										  <td>${vs.index+1} </td>
										  <td>${su['SHORTNAME']}</td>
										  <td>${su['TJCS']}</td>
										  <td>${su['DCS']}</td>
										  <td>${su['THZCS']}</td>
										  <td>${su['CSZ']}</td>
										  <td>${su['CSTHXG']}</td>
										  <td>${su['YCSJS']}</td>
										  <td>${su['CSBHG']}</td>
										  <td>${su['CSHG']}</td>
										  <td>${su['FSYFP']}</td>
										  <td>${su['FSZ']}</td>
										  <td>${su['YFSJS']}</td>
										  <td>${su['FSTHXG']}</td>
										  <td>${su['FSBHG']}</td>
										  <td>${su['GSZ']}</td>
										  <td></td>
										  <td>${su['RKDFC']}</td>
										  <td>${su['FCZ']}</td>
										  <td></td>
										  <td>${su['ZLBQ']}</td>
										  <td>${su['FCBHG']}</td>
										  <td>${su['FCHG']}</td>
										 </tr>
								</c:forEach>
								<tr>
									  <td class="tc" colspan="2">合计</td>
									  <td>${TJCS}</td>
									  <td>${DCS}</td>
									  <td>${THZCS}</td>
									  <td>${CSZ}</td>
									  <td>${CSTHXG}</td>
									  <td>${YCSJS}</td>
									  <td>${CSBHG}</td>
									  <td>${CSHG}</td>
									  <td>${FSYFP}</td>
									  <td>${FSZ}</td>
									  <td>${YFSJS}</td>
									  <td>${FSTHXG}</td>
									  <td>${FSBHG}</td>
									  <td>${GSZ}</td>
									  <td></td>
									  <td>${RKDFC}</td>
									  <td>${FCZ}</td>
									  <td></td>
									  <td>${ZLBQ}</td>
									  <td>${FCBHG}</td>
									  <td>${FCHG}</td>
									</tr>
								</table>
						</div>
					
</body>

</html>