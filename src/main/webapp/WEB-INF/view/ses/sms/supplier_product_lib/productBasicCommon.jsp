<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
     <div class="padding-top-10 clear">
       <h2 class="count_flow"><i>1</i>产品基本信息</h2>
       <ul class="ul_list">
         <li class="col-md-3 col-sm-6 col-xs-12 pl15">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>选择类别：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input id="citySel4" name="" placeholder="点击选择" value="${ smsProductBasic.category.name }" type="text" onclick="showMenu(); return false;" readonly> 
			 <input id="categorieId4" name="productBasic.categoryId" value="${cId}" type="hidden" class="w230 mb0 border0"> 
              <span class="add-on">i</span>
              <span class="input-tip">商品类目</span>
              <div class="cue" id="err_category"></div>
           </div>
         </li>
         
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input name="productBasic.name" value='${smsProductBasic.name}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">名称</span>
             <div class="cue" id="err_name"></div>
           </div>
         </li>
         
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>价格：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input name="productBasic.price" value='${smsProductBasic.price}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">价格</span>
             <div class="cue" id="err_price"></div>
           </div>
         </li>
         
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>品牌：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input class="span5" name="productBasic.brand" value='${smsProductBasic.brand}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">输入品牌</span>
             <div class="cue" id="err_brand"></div>
           </div>
         </li>
         
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>型号：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input name="productBasic.typeNum" value='${smsProductBasic.typeNum}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">型号</span>
             <div class="cue" id="err_typeNum"></div>
           </div>
         </li>
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>库存：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input name="productBasic.store" value='${smsProductBasic.store}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">库存</span>
             <div class="cue" id="err_store"></div>
           </div>
         </li>
         <li class="col-md-3 col-sm-6 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>SKU：</span>
           <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
             <input name="productBasic.sku" value='${smsProductBasic.sku}' type="text" readonly="readonly">
             <span class="add-on">i</span>
             <span class="input-tip">SKU</span>
             <div class="cue" id="err_sku"></div>
           </div>
         </li>
         
          <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="fl">产品主图 </span>
            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
            	<u:show showId="major_picture" businessId="${ smsProductBasic.pictureMajor }" sysKey="${sysKey}" typeId="${typeId }" delete="false" />
            </div>
          </li>
         
          <li class="col-md-3 col-sm-6 col-xs-12">
           	<span class="fl">产品子图 </span>
           	<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
            	<u:show showId="sub__picture" businessId="${ smsProductInfo.pictureSub }" sysKey="${ sysKey }" typeId="${typeId }" delete="false" />
            </div>
          </li>
         
         <li class="col-md-12 col-sm-12 col-xs-12">
           <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red"></div>包装清单：</span>
           <div class="col-md-12 col-sm-12 col-xs-12 p0">
             <textarea class="h80 col-md-12 col-sm-12 col-xs-12 " name="smsProductInfo.detailList" title="" placeholder="" readonly="readonly">${ smsProductInfo.detailList }</textarea>
           </div>
           <div class="clear red">${err_detailList}</div>
         </li>
         </ul>
         <!-- 产品参数加载 -->
         <c:if test="${not empty smsProductInfo.smsProductArguments }">
       		<%@ include file="/WEB-INF/view/ses/sms/supplier_product_lib/productParamterCommon.jsp" %>
         </c:if>
         
         <c:if test="${not empty smsProductInfo.smsProductArguments }">
         	<h2 class="count_flow" id="changeNum"><i>3</i>产品介绍信息</h2>
	    	<ul class="ul_list" id="paramter">
	         <li class="col-md-12 col-sm-12 col-xs-12">
	          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>商品介绍</span>
	          <div class="col-md-12 col-sm-12 col-xs-12 p0">
		 		<script id="editor" name="smsProductInfo.introduce" type="text/plain"></script>
	 			<div id="contractCodeErr" class="clear red">${ err_introduce}</div>
	 			</div>
			 </li>
			</ul>
		 </c:if>
         <c:if test="${empty smsProductInfo.smsProductArguments }">
         	<h2 class="count_flow" id="changeNum"><i>2</i>产品介绍信息</h2>
	    	<ul class="ul_list" id="paramter">
	         <li class="col-md-12 col-sm-12 col-xs-12">
	          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>商品介绍</span>
	          <div class="col-md-12 col-sm-12 col-xs-12 p0">
		 		<script id="editor" name="smsProductInfo.introduce" type="text/plain"></script>
	 			<div id="contractCodeErr" class="clear red">${ err_introduce}</div>
	 			</div>
			 </li>
			</ul>
		 </c:if>
		 
     </div>

	<!-- 加载富文本编辑器 -->
	<script type="text/javascript">
	 	var ue=new UE.ui.Editor({readonly:true,})
	    ue.render('editor');
	 	
	    var ue = UE.getEditor('editor');
	    var content='${smsProductInfo.introduce}';
		ue.ready(function(){
	  		ue.setContent(content);
		});
	</script>