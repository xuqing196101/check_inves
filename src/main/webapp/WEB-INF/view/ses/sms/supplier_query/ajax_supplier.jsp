<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<div class="search_detail ml0 ">
	<form id="form1" action="" method="post">
		<input type="hidden" name="page" id="page"> 
		<input type="hidden" name="categoryIds" id="categoryIds" />
		<input type="hidden" name="supplierTypeId" id="supplierTypeId" />
		<input type="hidden" name="itemTypeName" id="itemTypeName" />
		<input type="hidden" name="clickCategoryId" id="clickCategoryId">
		<input type="hidden" name="nodeLevel" id="nodeLevel">
		<ul class="demand_list">
			<li><label class="fl">供应商名称：</label>
			 <input	id="supplierName" name="supplierName" type="text">
			</li>
			<li><label class="fl">联系人：</label> 
			 <input id="armyBusinessName"  name="armyBusinessName" type="text">
			</li>
			<li id="selectSupplierType" class="hide">
				<label class="fl">供应商等级：</label> 
			  <select  name="supplierLevelName" id="supplierLevel" class="w182">
		    	<option selected="selected" value=''>全部</option>
		   		<option value="一级">一级</option>
		   		<option value="二级">二级</option>
		   		<option value="三级">三级</option>
		   		<option value="四级">四级</option>
		   		<option value="五级">五级</option>
		   		<option value="六级">六级</option>
		   		<option value="七级">七级</option>
		   		<option value="八级">八级</option>
		      </select>
			</li>
			<li id="projectLevel" class="hide">
				<label class="fl">供应商等级：</label> 
			  <select  name="supplierLevel" id="projectAllLevels" class="w182">
		    	<option selected="selected" value=''>全部</option>
		      </select>
			</li>
			<li id="addButton">
			</li>
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
