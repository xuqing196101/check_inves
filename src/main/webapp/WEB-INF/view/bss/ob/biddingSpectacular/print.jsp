<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>打印结果展示页面</title>
</head>
<body>
<div class="container">
<table  class="table table-bordered">
		<tbody>
			<tr>
				<td colspan="6" align="center"><br/>测试-674982364218<br/>竞价结果信息表<br/><br/></td>
			</tr> 
			<tr>
				<td class="info">项目名称</td>
				<td colspan="5">测试-674982364218</td>
			</tr> 
			<tr>
			  <td class="info">产品名称</td>
			  <td>摩托车</td>
			  <td class="info">数量</td>
			  <td>500</td>
			  <td class="info">预算</td>
			  <td>5000</td>
			</tr> 
			<tr>
				<td class="info">产品规格</td>
				<td colspan="5">XXXX</td>
			</tr>
			<tr>
				<td class="info">需求部门</td>
				<td colspan="5">XXXXXXXXXXXX</td>
			</tr>
			<tr>
				<td class="info">技术参数</td>
				<td colspan="5">XXXXXXXXXXXXXXXX</td>
			</tr>
			<tr>
				<td class="info">运杂费</td>
				<td colspan="5">XXXXXXXXXXXXXXXX</td>
			</tr> 
			<tr>
				<td class="info">交货地点</td>
				<td colspan="2">北京</td>
				<td class="info">交货时间</td>
				<td colspan="2">20160928</td>
			</tr> 
			<tr>
				<td colspan="6" style="border: none;" >
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>名次</td>
								<td class="tc">供应商名称</td>
								<td class="tc">自报单价（元）</td>
		  						<td class="tc">成交单价（元）</td>
		  						<td class="tc">成交数量</td>
		  						<td class="tc">成交总价（元）</td>
								<td class="tc">操作状态</td>
							</tr>
							<tr>
								<td class="tc w50">1</td>
								<td>XXXXXXXXXXXXXXX</td>
								<td class="tc">4000</td>
								<td class="tc">4000</td>
								<td class="tc">200</td>
								<td class="tc">800，000.00</td>
								<td class="tc">已确认</td>
							</tr>
							<tr>
								<td class="tc w50">2</td>
								<td>XXXXXXXXXXXXXXX</td>
								<td class="tc">4000</td>
								<td class="tc">4100</td>
								<td class="tc">150</td>
								<td class="tc">600，000.00</td>
								<td class="tc">已确认</td>
							</tr>
							<tr>
								<td class="tc w50">3</td>
								<td>XXXXXXXXXXXXXXX</td>
								<td class="tc">4200</td>
								<td class="tc">4000</td>
								<td class="tc">100</td>
								<td class="tc">400，000.00</td>
								<td class="tc">已确认</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr> 
			<tr>
				<td align="center" colspan="6">供应商确认中标数量总量为450，预定采购数量为500，剩余采购数量为50.</td>
			</tr>
			</tbody>
		   </table>
		   </div>
		   <div class="col-md-12 clear tc mt10">
	    	<button class="btn btn-windows print" type="submit">打印</button>
	    	<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	    </div>
</body>
</html>