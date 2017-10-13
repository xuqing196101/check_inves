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
								<tr>
									  <td class="tc" colspan="2">合计</td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									  <td></td>
									</tr>
								</table>
						</div>
					
</body>

</html>