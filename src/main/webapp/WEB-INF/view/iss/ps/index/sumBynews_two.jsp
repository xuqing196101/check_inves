<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<link
	href="${pageContext.request.contextPath }/public/select2/css/select2.css"
	rel="stylesheet" />
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>

<script type="text/javascript">
var id = "${id}";
var twoid = "${twoid}";
var title = "${title}";
var tab='${tab}';
$(function(){
	laypage({
	    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	    pages: "${list.pages}", //总页数
	    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	    skip: true, //是否开启跳页
	    total: "${list.total}",
	    startRow: "${list.startRow}",
	    endRow: "${list.endRow}",
	    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	        	var productType=$("#cId").val();
	            var productTypeName='${productTypeName}';
	            var lastArticleTypeName=$("#lastArticleTypeName").val();
	            var publishStartDate=$("#publishStartDate").val();
	       	    var publishEndDate=$("#publishEndDate").val();
	      		var url="${pageContext.request.contextPath}/index/selectsumBynews.html?page="+e.curr+"&id="+id+"&twoid="+twoid+"&title="+title+"&productType="+productType+"&productTypeName="+productTypeName+"&tab="+tab+"&lastArticleTypeName="+lastArticleTypeName+"&publishStartDate="+publishStartDate+"&publishEndDate="+publishEndDate;
	      		window.location.href = encodeURI(encodeURI(url));
	        }
	    }
	});
});

function query(){
	var title = $("#title").val();
	var productType=$("#cId").val();
	var lastArticleTypeName=$("#lastArticleTypeName").val();
	//title = decodeURI(title);
	//alert(title);
	 var productTypeName=$("#categorySel").val();
	 var publishStartDate=$("#publishStartDate").val();
	 var publishEndDate=$("#publishEndDate").val();
	 var url="${pageContext.request.contextPath}/index/selectsumBynews.html?id="+id+"&twoid="+twoid+"&title="+title+"&productType="+productType+"&productTypeName="+productTypeName+"&tab="+tab+"&lastArticleTypeName="+lastArticleTypeName+"&publishStartDate="+publishStartDate+"&publishEndDate="+publishEndDate;
	 window.location.href = encodeURI(encodeURI(url));
}
function myReSet(){
	var url="${pageContext.request.contextPath}/index/selectsumBynews.html?id="+id+"&twoid="+twoid+"&tab="+tab;
	window.location.href = encodeURI(encodeURI(url));
}

</script>
<script type="text/javascript">
	<!-- ztree 产品类别 --> 
	var treeid = null;
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeCategory");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function zTreeBeforeCheck(treeId, treeNode) {
      if (treeNode.isParent == true) {
          layer.msg("请选择末节点");
          return false;
        } else {
        return true;        
        }
    }
	
	function onCheck(e, treeId, treeNode) {
		var clickFlag;
        if(treeNode.checked) {
          	clickFlag = "1";
        } else {
          	clickFlag = "0";
        }
        var articleId = "${articleId}";
        var categoryIds = $("#cId").val();
        var categoryNames = $("#categorySel").val();
        if(clickFlag == "1") {
          $.ajax({
            url: "${pageContext.request.contextPath}/article/saveArtCategory.do",
            contentType:'application/json;charset=UTF-8',
            async: false,
            data: {
              "categoryIds":categoryIds,
              "categoryNames":encodeURI(categoryNames),
              "articleId": articleId,
              "categoryId": treeNode.id,
              "type": clickFlag
            },
            dataType: "json",
            success: function(data){
            	$("#cId").val(data.categoryIds);
        		$("#categorySel").val(data.categoryNames);
            }
          });
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/article/saveArtCategory.do",
            contentType:'application/json;charset=UTF-8',
            async: false,
            data: {
              "categoryIds":categoryIds,
              "categoryNames":encodeURI(categoryNames),
              "articleId": articleId,
              "categoryId": treeNode.id,
              "type": clickFlag
            },
            dataType: "json",
            success: function(data){
            	$("#cId").val(data.categoryIds);
        		$("#categorySel").val(data.categoryNames);
            }
	      });
       	}
	}
	
	/*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
    
  	  if (treeNode.isParent == true) {
          layer.msg("请选择末节点");
          return false;
      }
	  if (!treeNode.isParent) {
	  	$("#cId").val(treeNode.id);
        $("#categorySel").val(treeNode.name);
	    hideCategory();
	  }
    }
	
	function showCategory(articleId) {
	
		//回显勾选
		//var backCategoryIds = $("#cId").val();
		//栏目类型
		//var threeType = $("#threeType").select2("data").text;
		var threeType='${tab}';
		if(""==threeType){
			threeType = $("#threeType").select2("data").text;
		}
		
		var rootCode = null;
		if (threeType == "进口" || threeType == "物资") {
			rootCode = "GOODS";
		}
		if (threeType == "工程") {
			rootCode = "PROJECT";
		}
		if (threeType == "服务") {
			rootCode = "SERVICE";
		}
		//rootCode = "SERVICE";
		articleId="";
		var zTreeObj;
		var zNodes;
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/article/categoryTree.do",
				otherParam: {
					"articleId": articleId,
					//"backCategoryIds":backCategoryIds,
					"rootCode":rootCode,
				},
				dataFilter: ajaxDataFilter,
				dataType: "json",
				type: "post"
			},
			/* check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: {
					"Y": "ps",
					"N": "ps"
				}, //勾选checkbox对于父子节点的关联关系  
			}, */
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				/* beforeClick: beforeClick,
				onCheck: onCheck,
				beforeCheck: zTreeBeforeCheck, */
				onClick:zTreeOnClick,
			}
		};
		zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
		zTreeObj.expandAll(true); //全部展开
		var cityObj = $("#categorySel");
		var cityOffset = $("#categorySel").offset();
		$("#categoryContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDownOrg);
		
	}
	
	function ajaxDataFilter(treeId, parentNode, childNodes) {
		// 判断是否为空
		if(childNodes) {
			// 判断如果父节点是第二级,则将查询出来的子节点全部改为isParent = false
			if(parentNode != null && parentNode != "undefined" && parentNode.level == 1) {
				for(var i = 0; i < childNodes.length; i++) {
					childNodes[i].isParent += false;
				}
			}
		}
		return childNodes;
	}
	function hideCategory() {
		$("#categoryContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownOrg);
	}
	function onBodyDownOrg(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "categorySel" || event.target.id == "categoryContent" || $(event.target).parents("#categoryContent").length>0)) {
			hideCategory();
		}
	}

	function searchs(articleId){
		//var threeType = $("#threeType").select2("data").text;
		var threeType='${tab}';
		if(""==threeType){
			threeType = $("#threeType").select2("data").text;
		}
			
		var rootCode = null;
		if (threeType == "进口" || threeType == "物资") {
			rootCode = "GOODS";
		}
		if (threeType == "工程") {
			rootCode = "PROJECT";
		}
		if (threeType == "服务") {
			rootCode = "SERVICE";
		}
		var name=$("#search").val();
		if(name!=""){
		 	var zNodes;
			var zTreeObj;
			var setting = {
				async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/article/categoryTree.do",
						otherParam: {
							"articleId": articleId,
							"rootCode":rootCode,
						},
						dataFilter: ajaxDataFilter,
						dataType: "json",
						type: "post"
					},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick:zTreeOnClick,
				}
			};
			// 加载中的菊花图标
			var loading = layer.load(1);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/article/searchCategory.do",
				data: { "name" : encodeURI(name), "rootCode" : rootCode},
				async: false,
				dataType: "json",
				success: function(data){
					if (data.length == 1) {
						layer.msg("没有符合查询条件的产品类别信息！");
					} else {
						zNodes = data;
						zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
						zTreeObj.expandAll(true);//全部展开
					}
					// 关闭加载中的菊花图标
					
					layer.close(loading);
					
				}
			});
		}else{
			showCategory();
		}
	}

	function cheClick(id, name) {
		$("#articleTypeId").val(id);
		$("#articleTypeName").val(name);
	}

	function addAttach() {
		html = "<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
		$("#uploadAttach").append(html);
	}

	function deleteattach(obj) {
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}

	function typeInfo() {
		var typeId = $("#articleTypes").select2("data").text;
		var parentId = $("#articleTypes").select2("val");
		$("#secondType").empty();
		$("#threeType").empty();
		$("#threeType").select2("val", "");
		$("#fourType").empty();
		$("#fourType").select2("val", "");
		if (typeId == "工作动态") {
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			$("#second").show();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").addClass("tphide");
			getSencond(parentId);
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		} else if (typeId == "采购公告") {
			$("#second").show();
			$("#three").show();
			$("#four").show();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
		} else if (typeId == "中标公示") {
			$("#second").show();
			$("#three").show();
			$("#four").show();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
		} else if (typeId == "单一来源公示") {
			$("#second").show();
			$("#three").show();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
		} else if (typeId == "商城竞价公告") {
			$("#second").show();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		} else if (typeId == "网上竞价公告") {
			$("#second").show();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		} else if (typeId == "采购法规") {
			$("#second").show();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		} else if (typeId == "处罚公告") {
			$("#second").show();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			getSencond(parentId);
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		} else {
			$("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
			$("#second").hide();
			$("#three").hide();
			$("#four").hide();
			$("#lmsx").removeClass("tphide");
			$("#secondType").empty();
			$("#threeType").empty();
			$("#fourType").empty();
			$("#choseCategory").hide();
			hideCategory();
			$("#cId").val("");
        	$("#categorySel").val("");
		}
	}

	function secondTypeInfo() {
		$("#threeType").empty();
		$("#fourType").empty();
		$("#fourType").select2("val", "");
		var parentId = $("#secondType").select2("val");
		var TtypeId = $("#secondType").select2("data").text;
		if (TtypeId == "图片新闻") {
			$("#picNone").removeClass().addClass(
					"col-md-6 col-sm-6 col-xs-12 mt10");
		}
		if (TtypeId == "供应商处罚公告") {
			$("#three").show();
		}
		if (TtypeId == "专家处罚公告") {
			$("#three").hide();
		}
		/* if (TtypeId == "部队采购") {
			$("input[name='ranges']").each(function() {
				if ($(this).val() == 0) {
					$(this).attr('checked', 'true');
				}
				if ($(this).val() == 2) {
					$(this).attr('disabled', true);
				}
			});
		} else {
			$("input[name='ranges']").each(function() {
				$(this).attr('disabled', false);
			});
		} */

		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ parentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#threeType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#threeType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#threeType").select2();
					}
				});
	}

	function threeTypeInfo() {
		hideCategory();
		$("#cId").val("");
        $("#categorySel").val("");
		$("#fourType").empty();
		var parentId = $("#threeType").select2("val");
		var threeTypeName = $("#threeType").select2("data").text;
		if (threeTypeName == '进口' || threeTypeName == '物资' || threeTypeName == '工程' || threeTypeName == '服务') {
			$("#choseCategory").show();
		}
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ parentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#fourType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#fourType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#fourType").select2();
					}
				});
	}

	function getSencond(parentId) {
		$("#secondType").empty();
		$("#threeType").empty();
		$("#fourType").empty();
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ parentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							/* if (articleTypes[0].name == '集中采购' || articleTypes[0].name == '部队采购') {
							  $("#secondType").append("<option></option>");
							  $("#secondType").append("<option value=" + articleTypes[0].id + ">" + articleTypes[0].name + "</option>");
								  $("#secondType").select2();
								  $("#secondType").select2("val", articleTypes[0].id);
								  $("#secondType").attr("disabled",true);
								  loadThrees(articleTypes[0].id,articleTypes[0].name);
							} else { */
							$("#secondType").attr("disabled", false);
							$("#secondType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#secondType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
							/*  } */
						}
						$("#secondType").select2();
					}
				});
	}

	function loadThrees(parentId, TtypeId) {
		$("#threeType").empty();
		$("#fourType").empty();
		$("#fourType").select2("val", "");
		if (TtypeId == "图片新闻") {
			$("#picNone").removeClass().addClass(
					"col-md-6 col-sm-6 col-xs-12 mt10");
		}
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ parentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#threeType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#threeType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#threeType").select2();
					}
				});
	}

	$(function() {
		var range = "${article.range}";
		$("input[name='ranges']").each(function() {
			/* if (range == '2') {
			    $(this).attr('checked','true');
			  } else {
			  	  if($(this).val()==range){
			      $(this).attr('checked','true');
				  }
			  } */
			if ($(this).val() == range) {
				$(this).attr('checked', 'true');
			}
		});
		var typeId;
		$("#secondType").empty();
		$("#secondType").select2("val", "");
		$("#threeType").empty();
		$("#threeType").select2("val", "");
		$("#fourType").empty();
		$("#fourType").select2("val", "");
		$("#picshow").hide();
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=0",
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#articleTypes").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#articleTypes").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#articleTypes").select2();
						$("#articleTypes").select2("val",
								"${article.articleType.id }");
						var typeId = $("#articleTypes").select2("data").text;
						if (typeId == "工作动态") {
							$("#second").show();
							$("#lmsx").addClass("tphide");
							$("#choseCategory").hide();
							hideCategory();
							$("#cId").val("");
				        	$("#categorySel").val("");
						} else if (typeId == "采购公告") {
							$("#second").show();
							$("#three").show();
							$("#four").show();
						} else if (typeId == "中标公示") {
							$("#second").show();
							$("#three").show();
							$("#four").show();
						} else if (typeId == "单一来源公示") {
							$("#second").show();
							$("#three").show();
							$("#four").hide();
						} else if (typeId == "商城竞价公告") {
							$("#second").show();
							$("#three").hide();
							$("#four").hide();
							$("#choseCategory").hide();
							hideCategory();
							$("#cId").val("");
				        	$("#categorySel").val("");
						} else if (typeId == "网上竞价公告") {
							$("#second").show();
							$("#three").hide();
							$("#four").hide();
							$("#choseCategory").hide();
							hideCategory();
							$("#cId").val("");
				        	$("#categorySel").val("");
						} else if (typeId == "采购法规") {
							$("#second").show();
							$("#three").hide();
							$("#four").hide();
							$("#choseCategory").hide();
							hideCategory();
							$("#cId").val("");
				        	$("#categorySel").val("");
						} else if (typeId == "处罚公告") {
							$("#second").show();
							$("#choseCategory").hide();
							hideCategory();
							$("#cId").val("");
				        	$("#categorySel").val("");
							var secId = "${article.secondArticleTypeId}";
							if (secId == '114') {
								$("#three").show();
							}
							if (secId == '115') {
								$("#three").hide();
							}
							$("#four").hide();
						}
					}
				});

		var parentId = "${article.articleType.id }";
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ parentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#secondType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#secondType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#secondType").select2();
						$("#secondType").select2("val",
								"${article.secondArticleTypeId }");
						var TtypeId = $("#secondType").select2("data").text;
						if (TtypeId == "图片新闻") {
							$("#picNone").removeClass().addClass(
									"col-md-6 col-sm-6 col-xs-12 mt10");
						}
						/* if (TtypeId == "部队采购") {
							$("input[name='ranges']").each(function() {
								if ($(this).val() == 0) {
									$(this).attr('checked', 'true');
								}
								if ($(this).val() == 2) {
									$(this).attr('disabled', true);
								}
							});
						} else {
							$("input[name='ranges']").each(function() {
								$(this).attr('disabled', false);
							});
						} */
					}
				});

		var sparentId = "${article.secondArticleTypeId }";
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ sparentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#threeType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#threeType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#threeType").select2();
						$("#threeType").select2("val", "${article.threeArticleTypeId }");
						var threeTypeName = $("#threeType").select2("data").text;
						if (threeTypeName == '进口' || threeTypeName == '物资' || threeTypeName == '工程' || threeTypeName == '服务') {
							$("#choseCategory").show();
						}
					}
				});
		var fparentId = "${article.threeArticleTypeId }";
		$.ajax({
					contentType : "application/json;charset=UTF-8",
					url : "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="
							+ fparentId,
					type : "POST",
					dataType : "json",
					success : function(articleTypes) {
						if (articleTypes) {
							$("#fourType").append("<option></option>");
							$.each(articleTypes, function(i, articleType) {
								if (articleType.name != null
										&& articleType.name != '') {
									$("#fourType").append(
											"<option value=" + articleType.id + ">"
													+ articleType.name
													+ "</option>");
								}
							});
						}
						$("#fourType").select2();
						$("#fourType").select2("val",
								"${article.fourArticleTypeId }");
					}
				});

	})

	
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="javascript:void(0);">信息公告</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <form action="">
  <div class="container job-content ">
       <div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
			    <div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs('${articleId}')">
			    </div>
			    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
			</div>
	   	</div>
		<div class="search_box col-md-12 col-sm-12 col-xs-12">
			<span class="pl10" >标题：<input
				name="title" type="text" id="title" value="${title }" class="mb0"/></span>
				<span class="fl padding-left-5"> 采购方式：
				<select name="lastArticleTypeName" id="lastArticleTypeName">
				<option value="">全部</option>
				<option value="公开招标" <c:if test="${'公开招标' eq lastArticleTypeName }"> selected=selected </c:if> >公开招标</option>
				<option value="邀请招标" <c:if test="${'邀请招标' eq lastArticleTypeName }"> selected=selected </c:if> >邀请招标</option>
				<option value="询价" <c:if test="${'询价' eq lastArticleTypeName }"> selected=selected </c:if> >询价</option>
				<option value="竞争性谈判" <c:if test="${'竞争性谈判' eq lastArticleTypeName }"> selected=selected </c:if> >竞争性谈判</option>
				</select>
			    <!-- <input name="lastArticleTypeName" type="text" id="lastArticleTypeName" value="${lastArticleTypeName }" />-->
				</span>
				<span class="pl10 fl">
					选择产品类别：
				</span>
				<div class="col-md-3 col-sm-6 col-xs-12 w200 p0" id="choseCategory" >
				<div class="input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0 mt1" >
					<input id="cId" name="categoryId" type="hidden" value="${categoryIds}"> 
					<input id="categorySel" type="text" name="categoryName"  readonly value="${categoryNames}"
						onclick="showCategory('${categoryIds}');" />
					<div class="drop_up" onclick="showCategory('${categoryIds}');">
						<img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
					</div>
					<div class="cue" id="ERR_category">${ERR_category}</div>
				</div>
			  </div>
			
			<span class="fl pl10" > 发布时间：
			<input class="w80 mb0" name="publishStartDate" type="text" id="publishStartDate" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${publishStartDate }" />
			-<input class="w80 mb0" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
				name="publishEndDate" type="text" id="publishEndDate" value="${publishEndDate }" />
				</span> 
			<span class="fl">
				<button type="button" onclick="query()" class="btn btn-u-light-grey ml5">查询</button>
				<button type="button" onclick="myReSet()" class="btn btn-u-light-grey">重置</button>
			</span>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 border1 p20_20">
            <h2 class="col-md-12 col-sm-12 col-xs-12 bg7 h35">
          		<div class="col-md-7 col-xs-4 col-sm-6 tc f16 p0">标题</div>
          		<div class="col-md-3 col-sm-3 col-xs-4 tc f16 p0">发布时间</div>
          		<div class="col-md-2 col-sm-3 col-xs-4 tc f16 p0" >产品类别</div>
             </h2>
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
                <c:forEach items="${indexList}" var="i">
	                  <%--<li>
	                   <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self"><span class="f18 mr5">·</span>${i.name }</a>
	                   <span class="hex pull-right"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                  </li> 
	                  --%><c:set value="${i.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>36}">
						<li>
						<a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self" 
							class="col-md-7 col-sm-6 col-xs-12">
							<span class="f18 mr5 fl" >·</span>【${i.lastArticleType.name}】${fn:substring(name,0,36)}...
						</a>
	                     <span class="col-md-3 col-sm-3 col-xs-6 tc p0"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                   	 <span  class="col-md-2 col-sm-3 col-xs-6">${i.categoryName }</span>
	                    </li>
					</c:if>
					<c:if test="${length<=36}">
					   <li>
					   <a href="${pageContext.request.contextPath}/index/selectArticleNewsById.do?id=${i.id}" title="${i.name }" target="_self" class="col-md-7 col-sm-6 col-xs-12">
					 	  <span class="f18 mr5 fl">·</span>【${i.lastArticleType.name}】${i.name }
					   </a>
	                   <span class="col-md-3 col-sm-3 col-xs-6 tc p0"><fmt:formatDate value='${i.publishedAt}' pattern="yyyy年MM月dd日 " /></span>
	                   <span class="col-md-2 col-sm-3 col-xs-6">${i.categoryName }</span>
	                   </li>
					</c:if>
	                </c:forEach>         
                </ul>
        <div id="pagediv" align="right"></div>
        </div>
	  </div>
	  </form>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
