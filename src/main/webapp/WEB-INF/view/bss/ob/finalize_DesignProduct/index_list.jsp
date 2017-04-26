<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<jsp:include page="/index_head.jsp"></jsp:include>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script type="text/javascript">
	/* 分页 */
	$(function() {
	    laypage({
	      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	      pages : "${info.pages}", //总页数
	      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	      skip : true, //是否开启跳页
	      total : "${info.total}",
	      startRow : "${info.startRow}",
	      endRow : "${info.endRow}",
	      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
	      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
	        return "${info.pageNum}";
	      }(),
	      jump : function(e, first) { //触发分页后的回调
        	if(!first){ //一定要加此判断，否则初始时会无限刷新
	      		location.href = "${pageContext.request.contextPath }/product/index_list.do?page=" + e.curr;
	        }
	      }
	    });
	    intiTree();
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
          hideMenu();
	  }
    }
	
    function intiTree(){
  	  /* 加载目录信息 */
  		var datas;
  		var setting={
  				   async:{
  							autoParam:["id"],
  							enable:true,
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
  					         showLine: true
  					   },
  		         };
  		$.ajax({
			url: "${pageContext.request.contextPath}/obSupplier/createtreeByproduct.do",
			async: false,
			dataType: "json",
			success: function(data){
				if (data.length == 1) {
					layer.msg("没有符合查询条件的产品类别信息！");
				} else {
					zNodes = data;
					zTreeObj = $.fn.zTree.init($("#treeDemo"),setting,zNodes);
				}
			}
		});
  	    
    }
   
      function showMenu() {
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
	
	function searchs(){
		var name=$("#search").val();
		if(name!=""){
		 var zNodes;
			var zTreeObj;
			var datas;
	  		var setting={
	  				   async:{
	  							autoParam:["id"],
	  							enable:true,
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
	  					         showLine: true
	  					   },
	  		         };
			// 加载中的菊花图标
			 var loading = layer.load(1);
			
				$.ajax({
					url: "${pageContext.request.contextPath}/obSupplier/createtreeByproduct.do",
					data: { "name" : encodeURI(name)},
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
	
	//查询
	function query(){
		var name = $("#name").val();
		var code = $("#code").val();
		var smallPointsId = $("#categorieId4").val();
		window.location.href="${pageContext.request.contextPath }/product/index_list.html?name="+name+"&&code="+code+"&&smallPointsId="+smallPointsId;
	}
	
	//重置
	function res(){
		window.location.href="${pageContext.request.contextPath }/product/index_list.html";
	}
	</script>
</head>

<body>
	  	<!--面包屑导航开始-->
	   	<div class="margin-top-10 breadcrumbs">
	      <div class="container">
			   	<ul class="breadcrumb margin-left-0">
			   		<li><a href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li><li><a href="#">定型产品列表</a></li>
			   	</ul>
					<div class="clear"></div>
		  	</div>
	   	</div>
		  <div class="container job-content ">
		  		<div class="search_box col-md-12 col-sm-12 col-xs-12">
		         	产品名称：<input name="name" class="m0" type="text" id="name" value="${product.name }"/>
		         	产品代码：<input name="code" class="m0" type="text" id="code" value="${product.code }"/>
		         	产品目录：
		         	<div class="pr inline-block"><input class="m0 w100p" name="smallname" type="text" id="citySel4" value="${catName }"  onclick=" showMenu(); return false;" readonly="readonly" />
		        	<input id="categorieId4" name="smallPointsId" value="${smallPointsId }" type="hidden">
		        	<!-- 目录框 -->
					<div id="menuContent" class="menuContent col-md-12 col-xs-12 col-sm-12 p0 tree_drop" style="z-index:10000;position:absolute;top:30px;left:0px" hidden="hidden">
						<div class="col-md-12 col-xs-8 col-sm-8 p0">
			    			<input type="text" id="search" class="input_group">
			    			<img alt="" src="${pageContext.request.contextPath }/public/backend/images/view.png" style="position: absolute; right: 10px;top: 5px;" onclick="searchs()">
						</div>
						<ul id="treeDemo" class="ztree slect_option clear" style="max-height: 340px;"></ul>
					</div>
		        	</div>
		        	<div class="inline-block">
		        		<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
		        		<button type="button" onclick="res()" class="btn btn-u-light-grey">重置</button>
		      		</div>
		      </div>
          <div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20 lh35">
            <div class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
          		<div class="col-md-2 col-xs-4 col-sm-4 tc f16">产品名称</div>
          		<div class="col-md-2 col-xs-3 col-sm-4 tc f16">产品代码</div>
          		<div class="col-md-2 col-xs-3 col-sm-4 tc f16">规格型号</div>
          		<div class="col-md-2 col-xs-3 col-sm-4 tc f16">质量技术标准</div>
                <div class="col-md-2 col-xs-4 col-sm-4 tc f16">所属目录</div>
                <div class="col-md-2 col-xs-4 col-sm-4 tc f16">合格供应商数量</div>
             </div>
             <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0">
                <c:choose>
                	<c:when test="${info.list != null }">
                		<c:forEach items="${info.list}" var="product">
		                  <li>
		                  	<div class="col-md-2 col-xs-4 col-sm-4" title="${product.name}">
					  		 	<span class="f16 mr5 fl">·</span>
					  		 	<c:if test="${fn:length(product.name) > 8 }">${fn:substring(product.name, 0, 8)}...</c:if>
					  		 	<c:if test="${fn:length(product.name) <= 8 }">${product.name }</c:if>
					    	</div>
					    	<div class="col-md-2 col-xs-3 col-sm-4 tc">
		                   		<span class="f16 mr5">${product.code}</span>
				  			</div>
					    	<div class="col-md-2 col-xs-3 col-sm-4" title="${product.standardModel }">
		                   		<span class="f16 mr5">
		                   			<c:if test="${fn:length(product.standardModel) > 9 }">${fn:substring(product.standardModel, 0, 9)}...</c:if>
					  		 		<c:if test="${fn:length(product.standardModel) <= 9 }">${product.standardModel }</c:if>
		                   		</span>
				  			</div>
					    	<div class="col-md-2 col-xs-3 col-sm-4" title="${product.qualityTechnicalStandard}">
		                   		<span class="f16 mr5">
		                   			<c:if test="${fn:length(product.qualityTechnicalStandard) > 9 }">${fn:substring(product.qualityTechnicalStandard, 0, 9)}...</c:if>
					  		 		<c:if test="${fn:length(product.qualityTechnicalStandard) <= 9 }">${product.qualityTechnicalStandard }</c:if>
		                   		</span>
				  			</div>
		                   	<div class="col-md-2 col-xs-4 col-sm-4 tc" title="${product.pointsName }">${product.smallPoints.name }</div>
		                   	<div class="col-md-2 col-xs-4 col-sm-4 tc">
		                   		<c:forEach items="${numlist }" var="num">
		  							<c:if test="${num.smallPointsId == product.smallPointsId }">${num.nCount }</c:if>
		  						</c:forEach>
		                   	</div>
		                  </li>
		                </c:forEach> 
                	</c:when>
                	<c:otherwise>
                		<li class="tc">暂无数据</li>
                	</c:otherwise>
                </c:choose>
             </ul>
        	<div id="pagediv" align="right"></div>
        </div>
	  </div>
		<!--底部代码开始-->
		<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
