<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<div class="search_detail ml0">
	<form id="form1" action="" method="post">
		<input type="hidden" name="page" id="page"> 
		<input type="hidden" name="categoryIds" id="categoryIds" />
		<input type="hidden" name="supplierTypeId" id="supplierTypeId" />
		<input type="hidden" name="itemTypeName" id="itemTypeName" />
		<input type="hidden" name="clickCategoryId" id="clickCategoryId">
		<input type="hidden" name="nodeLevel" id="nodeLevel">
		<div class="m_row_5">
    <div class="row">
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
          <div class="col-xs-8 f0 lh0">
						<input id="supplierName" name="supplierName" type="text" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">联系人：</div>
          <div class="col-xs-8 f0 lh0">
						<input id="armyBusinessName" name="armyBusinessName" type="text" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
			
			<div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10 hide" id="selectSupplierType"s>
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商等级：</div>
          <div class="col-xs-8 f0 lh0">
						<select name="supplierLevelName" id="supplierLevel" class="w100p h32 f14">
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
          </div>
        </div>
      </div>
			
			<div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10 hide" id="projectLevel"s>
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商等级：</div>
          <div class="col-xs-8 f0 lh0">
						<select name="supplierLevel" id="projectAllLevels" class="w100p h32 f14">
							<option selected="selected" value=''>全部</option>
						</select>
          </div>
        </div>
      </div>
    </div>
    </div>
		
		<div class="tc" id="addButton">
		</div>
		</form>
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
