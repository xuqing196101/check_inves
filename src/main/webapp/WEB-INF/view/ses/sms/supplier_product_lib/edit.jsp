<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/productLibManage/commons.js"></script>
	<title>产品修改</title>
	<script type="text/javascript">
			//加载产品类别下拉框
			$(function(){
				intiTree();
				// 回车键触发搜索
				$("#search").keyup(function(event){
				  if(event.keyCode == 13){
				    searchs();
				  }
				});
				$("#citySel4").click(function(){
					$("#pcategory").html("");
					var cityObj = $("#citySel4");
					var cityOffset = $("#citySel4").offset();
					$("#menuContent").css({}).slideDown("fast");
					$("body").bind("mousedown", onBodyDown);
				});
				// 价格输入格式验证
				$("#price").keyup(function(){
					var price = $("#price").val();
					if(! /^-?\d+$/.test(price) && ! /^-?\d+\.?\d{0,2}$/.test(price)){
						layer.msg("格式有误");
						$("#price").val("");
					}
				});
				
				// 校验SKU是否唯一
				$("#sku").keyup(function(){
					// 获取用户输入的sku
					var sku=$("#sku").val();
					var pid = $("#pid").val();
					 $.ajax({
						    url: "${pageContext.request.contextPath }/product_lib/vartifyUniqueSKU.do",
						    type: "POST",
						    dataType: "json",
						 	data: {
						 		"sku": sku,
						 		"pid":pid
							},
					    success: function(data) {
					    	if(data.data==500){
					    		layer.msg("SKU已存在,请重新输入");
					    		$("#sku").val("")
					    	}
					    }
			          });
				});
			});
			
			/** 判断是否为根节点 */
		    function isRoot(node){
		    	if (node.pId == 0){
		    		return true;
		    	} 
		    	return false;
		    }
 
			/*点击事件*/
		    function zTreeOnClick(event,treeId,treeNode){
		  	  if (isRoot(treeNode)){
		  		  layer.msg("不可选择根节点");
		  		  return;
		  	  }
			  if(!treeNode.isParent) {
				  $("#citySel4").val(treeNode.name);
		          $("#categorieId4").val(treeNode.id);
		          $("#categoryLevel").val(treeNode.level+1);
		          hideMenu();
		          $.ajax({
					    url: "${pageContext.request.contextPath }/product_lib/getParametersByItemId.do",
					    async:false,
					    type: "POST",
					    dataType: "json",
					 	data: {
					 	cateId: treeNode.id
						},
				    success: function(data) {
				    	if(data != ''){
					          $("#paramter").load("${pageContext.request.contextPath}/product_lib/productParamterUI.html?categoryId="+treeNode.id)
					          $("#showArgu").html("");
					          $("#paramter").css("display","block");
					          $("#changeNum").html("<i>3</i>产品介绍信息");
				    	}else{
				    		$("#paramter").html("");
				            $("#showArgu").html("");
				    		$("#paramter").css("display","none");
				    		$("#changeNum").html("<i>2</i>产品介绍信息");
				    	}
				    }
		          });
		          
			  }
		    }
		 
		  function intiTree(){
			  /* 加载目录信息 */
				var datas;
				var setting={
						   async:{
									autoParam:["id"],
									enable:true,
									url:"${pageContext.request.contextPath}/category/createtree.do",
									otherParam:{"otherParam":"zTreeAsyncTest"},  
									dataType:"json",
									type:"get",
								},
								callback:{
							    	onClick:zTreeOnClick,//点击节点触发的事件
				       			    
							    }, 
								data:{
									keep:{
										parent:true
									},
									key:{
										title:"title"
									},
									simpleData:{
										enable:true,
										idKey:"id",
										pIdKey:"pId",
										rootPId:"0",
									}
							    },
							   view:{
							        selectedMulti: false,
							        showTitle: false,
							   },
				         };
			    $.fn.zTree.init($("#treeDemo"),setting,datas);
		  }
		 
			    function showMenu() {
			    	$("#pcategory").html("");
					var cityObj = $("#citySel4");
					var cityOffset = $("#citySel4").offset();
					$("#menuContent").css({}).slideDown("fast");
					$("body").bind("mousedown", onBodyDown);
				}
			    function hideMenu() {
					$("#menuContent").fadeOut("fast");
					$("body").unbind("mousedown", onBodyDown);
				}
				function onBodyDown(event) {
					if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
						hideMenu();
					}
				} 

		// 产品目录搜索
		function searchs(){
			var name=$("#search").val();
			if(name!=""){
			 var zNodes;
				var zTreeObj;
				var setting = {
						async:{
							autoParam:["id"],
							enable:true,
							url: "${pageContext.request.contextPath}/category/createtree.do"
						},
					data : {
						simpleData : {
							enable : true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					callback: {
						onClick: zTreeOnClick
					},view: {
						showLine: true
					}
				};
				// 加载中的菊花图标
				 var loading = layer.load(1);
					$.ajax({
						url: "${pageContext.request.contextPath}/category/createtree.do",
						data: { "param" : encodeURI(name)},
						async: false,
						dataType: "json",
						success: function(data){
							if (data.length == 1) {
								layer.msg("没有符合查询条件的产品类别信息！");
							} else {
								zNodes = data;
								zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
								zTreeObj.expandAll(true);//全部展开
							}
							// 关闭加载中的菊花图标
							layer.close(loading);
						}
					});
			}else{
				intiTree();
			}
		}
	
	
		// 提交表单
		function submitForm(flag){
			// 判断用户点击(保存/提交)
			$("#err_category").html("");
			$("#err_name").html("");
			$("#err_price").html("");
			
			$("#err_brand").html("");
			$("#err_typeNum").html("");
			$("#err_store").html("");
			$("#err_sku").html("");
			$("#err_introduce").html("");
			
			if(flag == 1){
				// 提交时做校验
				if(!vertifySubmitForm()){
					return;
				}
			}
			
			layer.confirm("您确认修改吗？", {
			    btn: ['确定','取消'], //按钮
			}, function(index){
				layer.close(index);
				$("#flag").val(flag);
				// 表单提交
				$.post("${pageContext.request.contextPath}/product_lib/updateSignalProductInfo.do", $("#smsProductLibForm").serialize(), function(data) {
					if (data.status == 200) {
						layer.confirm("修改成功",{
							btn:['确定']
						},function(){
								// 确认后加载商品信息 
								window.location.href="${pageContext.request.contextPath}/product_lib/findAllProductLibBasicInfo.html";
							}
						) 
					}
					if(data.status == 500){
						layer.alert(data.msg);
					}
				});
			});
		}
	
</script>
</head>
<body>
	
	<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商后台管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品库管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">产品修改</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

     <!-- 新增模板开始-->
    <div class="container container_box">
    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
      <form action="" id="smsProductLibForm" method="post" enctype="multipart/form-data">
      	<input id="flag" name="flag" type="hidden" value="">
      	<input id="pid" name="productBasic.id" type="hidden" value="${ smsProductBasic.id }">
      	<input name="smsProductInfo.id" type="hidden" value="${ smsProductInfo.id }">
      	<input name="smsProductInfo.argumentsId" type="hidden" value="${ smsProductInfo.argumentsId }">
        <div class="padding-top-10 clear">
	      <h2 class="count_flow"><i>1</i>产品基本信息</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>选择类别：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="citySel4" name="" placeholder="点击选择" value="${ smsProductBasic.category.name }" type="text" readonly> 
					<input id="categorieId4" name="productBasic.categoryId" value="${smsProductBasic.categoryId}" type="hidden" class="w230 mb0 border0"> 
					<input id="categoryLevel" name="categoryLevel" value="${obProduct.productCategoryLevel}" type="hidden" class="w230 mb0 border0">
					<!-- 目录框 -->
					<div id="menuContent" class="menuContent col-md-12 col-xs-12 col-sm-12 p0 tree_drop" style="z-index:10000;position:absolute;top:30px;left:0px" hidden="hidden">
						<div class="col-md-12 col-xs-12 col-sm-12 p0">
						    <input type="text" id="search" class="fl m0">
						    <img alt="" src="${pageContext.request.contextPath }/public/backend/images/view.png" style="position: absolute; right: 10px;top: 5px;" onclick="searchs()">
						</div>
						<ul id="treeDemo" class="ztree slect_option clear" style="max-height: 400px;"></ul>
					</div>
                <span class="add-on">i</span>
                <span class="input-tip">商品类目</span>
                <div class="cue" id="err_category"></div>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="name" name="productBasic.name" maxlength="660" value='${smsProductBasic.name}' type="text" />
                <span class="add-on">i</span>
                <span class="input-tip">名称</span>
                <div class="cue" id="err_name"></div>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>价格：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="price" name="productBasic.price" value='${smsProductBasic.price}' type="text" />
                <span class="add-on">i</span>
                <span class="input-tip">价格</span>
                <div class="cue" id="err_price"></div>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>品牌：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="brand" class="span5" maxlength="660" name="productBasic.brand" value='${smsProductBasic.brand}' type="text" />
                <span class="add-on">i</span>
                <span class="input-tip">输入品牌</span>
                <div class="cue" id="err_brand"></div>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>型号：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="typeNum" maxlength="660" name="productBasic.typeNum" value='${smsProductBasic.typeNum}' type="text" />
                <span class="add-on">i</span>
                <span class="input-tip">型号</span>
                <div class="cue" id="err_typeNum"></div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>库存：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input id="store" name="productBasic.store" maxlength="19" value='${smsProductBasic.store}' type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <span class="add-on">i</span>
                <span class="input-tip">库存</span>
                <div class="cue" id="err_store"></div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>SKU：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
              	<c:if test="${not empty smsProductBasic.sku}">
	                <input value='${smsProductBasic.sku}' type="text" disabled="disabled"/>
	                <input name="productBasic.sku" value='${smsProductBasic.sku}' type="hidden"/>
              	</c:if>
              	<c:if test="${empty smsProductBasic.sku}">
	                <input id="sku" name="productBasic.sku" value='${smsProductBasic.sku}' type="text" />
              	</c:if>
                <span class="add-on">i</span>
                <span class="input-tip">SKU</span>
                <div class="cue" id="err_sku"></div>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="fl">产品主图 </span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
	              <input name="productBasic.pictureMajor"  type="hidden" value="${ smsProductBasic.pictureMajor }" />
	              <u:upload id="major_picture" businessId="${ smsProductBasic.pictureMajor }" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="附件上传" auto="true"/>
	              <u:show showId="major_picture" businessId="${ smsProductBasic.pictureMajor }" sysKey="${sysKey}" typeId="${typeId }" />
	            </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12">
            	<span class="fl">产品子图 </span>
	            <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0" >
	              <input name="smsProductInfo.pictureSub" type="hidden" value="${ smsProductInfo.pictureSub }" />
	              <u:upload id="sub_picture" businessId="${ smsProductInfo.pictureSub }" sysKey="${ sysKey }" typeId="${ typeId }" multiple="true" buttonName="附件上传" auto="true"/>
	              <u:show showId="sub_picture" businessId="${ smsProductInfo.pictureSub }" sysKey="${ sysKey }" typeId="${typeId }" />
	            </div>
            </li>
            
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red"></div>包装清单：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="h80 col-md-12 col-sm-12 col-xs-12 " maxlength="1000" name="smsProductInfo.detailList" title="" placeholder="" >${ smsProductInfo.detailList }</textarea>
              </div>
              <div class="clear red">${err_detailList}</div>
            </li>
           </ul>
            <!-- 产品参数加载 -->
            
            <c:if test="${not empty smsProductInfo.smsProductArguments }">
	            <span id="showArgu">
	            	<%@ include file="/WEB-INF/view/ses/sms/supplier_product_lib/editproductParamterCommon.jsp" %>
	            </span>
            </c:if>
           	<!-- 产品参数加载 -->
            <span id="paramter" style="display: none">
	            
            </span>
            
            <c:if test="${not empty smsProductInfo.smsProductArguments }">
	            <h2 class="count_flow" id="changeNum"><i>3</i>产品介绍信息</h2>
	    		<ul class="ul_list">
		            <li class="col-md-12 col-sm-12 col-xs-12">
			            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>产品介绍</span>
			            <div class="col-md-12 col-sm-12 col-xs-12 p0">
						 	<script id="introduce" name="smsProductInfo.introduce" type="text/plain"></script>
			   			</div>
		   				<div id="err_introduce" class="clear red"></div>
					</li>
				</ul>
			</c:if>
            <c:if test="${empty smsProductInfo.smsProductArguments }">
	            <h2 class="count_flow" id="changeNum"><i>2</i>产品介绍信息</h2>
	    		<ul class="ul_list" id="paramter">
		            <li class="col-md-12 col-sm-12 col-xs-12">
			            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>产品介绍</span>
			            <div class="col-md-12 col-sm-12 col-xs-12 p0">
						 	<script id="introduce"  name="smsProductInfo.introduce" type="text/plain"></script>
			   			</div>
		   				<div id="err_introduce" class="clear red"></div>
					</li>
				</ul>
			</c:if>
			
          <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
            <button class="btn btn-windows save" type="button" onclick="submitForm(0)">修改</button>
            <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
          </div>
        </div>
      </form>
    </div>
	<!-- 加载富文本编辑器 -->
	<script type="text/javascript">
		var ue = UE.getEditor('introduce');
	    var content='${smsProductInfo.introduce}';
		ue.ready(function(){
	  		ue.setContent(content);
		});
	</script>
</body>
</html>