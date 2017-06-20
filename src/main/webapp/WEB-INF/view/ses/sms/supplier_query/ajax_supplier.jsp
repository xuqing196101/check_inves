<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<div class="search_detail ml0 ">
	<form id="form1" action="" method="post">
		<input type="hidden" name="page" id="page"> 
		<input type="hidden" name="categoryIds" id="categoryIds" />
		<input type="hidden" name="supplierTypeId" id="supplierTypeId" />
		<ul class="demand_list">
			<li><label class="fl">供应商名称：</label><span class="w220"> <input
					class="w220" id="supplierName" name="supplierName"
					 type="text">
			</span></li>
			<li><label class="fl">联系人：</label> <input id="armyBusinessName"
				class="w220" name="armyBusinessName"
				 type="text"></li>
			<li><input class="btn fl mt1" onclick="findSupplier()"
				type="button" value="查询"> <input type="button"
				class="btn fl mt1" onclick="resetQuery()" value="重置"></li>
		</ul>
	</form>
	<div class="clear"></div>
</div>

<div class="content table_box pl0">
	<table id="tb1"
		class="table table-bordered table-condensed table-hover table-striped">
		<thead>
			<tr>
				<th class="info w50">序号</th>
				<th class="info">供应商名称</th>
				<th class="info">供应商等级</th>
				<th class="info">联系人</th>
				<th class="info">联系人电话</th>
				<th class="info">采购机构</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<div id="pagediv" align="right"></div>
