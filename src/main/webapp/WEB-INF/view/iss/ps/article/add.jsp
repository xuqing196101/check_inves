<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>

<!DOCTYPE HTML>
<html>

<head>
<link
	href="${pageContext.request.contextPath }/public/select2/css/select2.css"
	rel="stylesheet" />
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
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
		var threeType = $("#threeType").select2("data").text;
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
		var threeType = $("#threeType").select2("data").text;
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
			$("#three").hide();
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

	function goBack() {
		window.location.href = "${pageContext.request.contextPath }/article/getAll.html";
	}

	function clk() {
		var second = $("#secondType").select2("val");
		var three = $("#threeType").select2("val");
		var four = $("#fourType").select2("val");
		var articleTypes = $("#articleTypes").select2("data").text;
		if (articleTypes == "工作动态") {
			return true;
		} else if ($("#second").is(":visible")) {
			if (second == null || second == "") {
				$("#ERR_secondType").html("栏目属性不能为空");
				return false;
			} else if ($("#three").is(":visible")) {
				if (three == null || three == "") {
					$("#ERR_threeType").html("采购类型不能为空");
					return false;
				} else if ($("#four").is(":visible")) {
					if (four == null || four == "") {
						$("#ERR_fourType").html("采购方式不能为空");
						return false;
					}else if (articleTypes == "单一来源公示" || articleTypes == "采购公告" || articleTypes == "中标公示"){
	          		   var categoryIds = $("#cId").val();
			           if (categoryIds == "" || categoryIds == null || categoryIds== "undefined") {
							$("#ERR_category").html("产品类别不能为空");
							return false;
					   }
	              	}
                }else if (articleTypes == "单一来源公示" || articleTypes == "采购公告" || articleTypes == "中标公示"){
          		    var categoryIds = $("#cId").val();
		            if (categoryIds == "" || categoryIds == null || categoryIds== "undefined") {
						$("#ERR_category").html("产品类别不能为空");
						return false;
				    }
	            }
			}
		} else {
			return true;
		}
	}
</script>
</head>

<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a>信息服务</a></li>
				<li><a>门户管理</a></li>
				<li>
					<a href="javascript:jumppage('${pageContext.request.contextPath}/article/getAll.html')">信息管理</a>
				</li>
				<li class="active"><a href="${ pageContext.request.contextPath }/article/add.html">新增</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container container_box">
		<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
			    <div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs('${articleId}')">
			    </div>
			    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
			</div>
	   	</div>
		<form id="myform"
			action="${pageContext.request.contextPath }/article/save.html"
			onsubmit="return clk()" method="post">
			<input type="hidden" name="status" id="articleStatus" value="0" /> <input
				type="hidden" id="publishType" value="${curUser.publishType}">
			<div class="">
				<h2 class="list_title">新增信息</h2>

				<ul class="ul_list mb20">
					<li class="col-md-12 col-sm-6 col-xs-12 pl15"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								class="star_red">*</div>信息标题：</span>
						<div
							class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
							<input name="id" type="hidden" value="${articleId }"> <input
								id="name" name="name" value="${article.name }" maxlength="200"
								type="text"> <span class="add-on">i</span>
							<div class="cue">${ERR_name}</div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								class="star_red">*</div>信息栏目：</span>
						<div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select id="articleTypes" name="articleType.id"
								class="select col-md-12 col-sm-12 col-xs-12 p0"
								onchange="typeInfo()">
							</select>
							<div class="cue">${ERR_typeId}</div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide" id="second"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								id="lmsx" class="star_red">*</div>栏目属性：</span>
						<div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select id="secondType" name="secondArticleTypeId"
								class="select col-md-12 col-sm-12 col-xs-12 p0"
								onchange="secondTypeInfo()">
							</select>
							<div class="cue" id="ERR_secondType"></div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide" id="three"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								class="star_red">*</div>栏目类型：</span>
						<div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select id="threeType" name="threeArticleTypeId"
								class="select col-md-12 col-sm-12 col-xs-12 p0"
								onchange="threeTypeInfo()">
							</select>
							<div class="cue" id="ERR_threeType"></div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 hide" id="four"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								class="star_red">*</div>采购方式：</span>
						<div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select id="fourType" name="fourArticleTypeId"
								class="select col-md-12 col-sm-12 col-xs-12 p0"
								onchange="fourTypeInfo()">
							</select>
							<div class="cue" id="ERR_fourType"></div>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
						<div class="star_red">*</div>发布范围：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
							<label class="fl margin-bottom-0">
								<input type="radio" name="ranges" value="0" class="mt0">内网
							</label>
							<label class="ml10 fl">
								<input type="radio" name="ranges" value="2" class="mt0">内外网
							</label>
							<div class="cue">${ERR_range}</div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 dnone" id="choseCategory">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
						<div class="star_red">*</div>选择产品类别：</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
							<input id="cId" name="categoryId"  type="hidden" value="${categoryIds}">
					        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryNames}"  onclick="showCategory('${articleId}');" />
							<div class="drop_up" onclick="showCategory('${articleId}');">
							    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
					        </div>
							<div class="cue" id="ERR_category">${ERR_category}</div>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12" id="picshow"
						style="display: none"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5">图片展示：</span>
						<div
							class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
							<input id="isPicShow" name="isPicShow"
								value="${article.isPicShow }" type="text"> <span
								class="add-on">i</span>
							<div class="cue">${ERR_isPicShow}</div>
						</div></li>
					<li class="col-md-12 col-sm-12 col-xs-12"><span
						class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div
								class="star_red">*</div>信息正文：</span>
						<div class=" col-md-12 col-sm-12 col-xs-12 p0">
							<script id="editor" name="content" type="text/plain"
								class="col-md-12 col-sm-12 col-xs-12 edit-posit p0"></script>
						</div>
						<div class="red f14 clear col-ms-12 col-xs-12 col-sm-12 p0">${ERR_content}</div>
					</li>
					<li class="col-md-6 col-sm-6 col-xs-12 mt10"><span class="fl">附件上传：</span>
						<div>
							<u:upload id="artice_file_up" buttonName="上传文档"
								businessId="${articleId }" sysKey="${sysKey}"
								typeId="${artiAttachTypeId }" multiple="true" auto="true" />
							<u:show showId="artice_file_show" businessId="${articleId }"
								sysKey="${sysKey}" typeId="${artiAttachTypeId }" />
						</div></li>
					<li class="col-md-6 col-sm-6 col-xs-12 mt10"><span class="fl">单位及保密委员会审核表：</span>
						<div>
							<u:upload id="artice_secret_up" multiple="true"
								businessId="${articleId}" sysKey="${sysKey}"
								typeId="${secretTypeId }" singleFileSize="10000"
								exts="png,jpeg,jpg,bmp" auto="true" />
							<u:show showId="artice_secret_show" businessId="${articleId }"
								sysKey="${sysKey}" typeId="${secretTypeId }" />
						</div>
						<div class="cue">${ERR_auditDoc}</div></li>
					<li class="col-md-6 col-sm-6 col-xs-12 mt10 dis_hide" id="picNone">
						<span class="fl"><div class="star_red">*</div>图片上传：</span>
						<div class="mb20 h30">
							<u:upload id="artice_up" businessId="${articleId }"
								sysKey="${sysKey}" typeId="${attachTypeId }" exts="png,jpeg,jpg,bmp" auto="true" />
							<u:show showId="artice_show" businessId="${articleId }"
								sysKey="${sysKey}" typeId="${attachTypeId }" />
							<div class="cue">${ERR_auditPic}</div>
						</div>
					</li>
				</ul>

				<div class="col-md-12 tc">
					<button class="btn btn-windows save" type="submit" onclick="return formValidate()">暂存</button>
					<button class="btn btn-windows save" type="button"
						onclick="tijiao()">提交</button>
					<input class="btn btn-windows back" value="返回" type="button"
						onclick="goBack()">
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var option = {
			toolbars : [ [ 'undo', 'redo', '|', 'bold', 'italic', 'underline',
					'formatmatch', 'autotypeset', '|', 'forecolor',
					'backcolor', 'fontfamily', 'fontsize', '|', 'indent', '|',
					'justifyleft', 'justifycenter', 'justifyright',
					'justifyjustify', ] ]
		}
		UE.Editor.prototype.placeholder = function(justPlainText) {
			var _editor = this;
			_editor.addListener("focus", function() {
				UE.dom.domUtils.setStyles(self.ue.body, {
					'color' : 'black'
				});
				var localHtml = _editor.getPlainTxt();
				if ($.trim(localHtml) === $.trim(justPlainText)) {
					_editor.setContent("");
				}
			});
			_editor.addListener("blur", function() {
				var localHtml = _editor.getContent();
				if (localHtml == "" || localHtml == null) {
					UE.dom.domUtils.setStyles(self.ue.body, {
						'color' : '#ff9999'
					});
				}
				if (!localHtml) {
					_editor.setContent(justPlainText);
				}
			});
			_editor.ready(function() {
				_editor.fireEvent("blur");
			});
		};

		var ue = UE.getEditor('editor', option);
		var content = '${article.content}';
		var messageTip = "${properties['messageTip']}";

		ue.ready(function() {
			if(content == "" || content == null) {
				ue.placeholder(messageTip);
				UE.dom.domUtils.setStyles(self.ue.body, {
					'color' : '#ff9999'
				});
			} else {
				ue.placeholder(content);
				UE.dom.domUtils.setStyles(self.ue.body, {
					'color' : 'black'
				});
			}
			
		});

		function tijiao() {
			$("#articleStatus").val("1");
			if (!(ue.getContentTxt() == messageTip || ue.getContentTxt() == "" || ue
					.getContentTxt() == null)) {
				$("#myform").submit();
			} else if(ue.getContentTxt() == messageTip || ue.getContentTxt() == "" || ue
					.getContentTxt() == null) {
				layer.alert("请输入信息正文");
			}
		}
		
		function formValidate() {
			if (!(ue.getContentTxt() == messageTip || ue.getContentTxt() == "" || ue
					.getContentTxt() == null)) {
				return true;
			} else if(ue.getContentTxt() == messageTip || ue.getContentTxt() == "" || ue
					.getContentTxt() == null) {
				layer.alert("请输入信息正文");
				return false;
			}
		}
	</script>

</body>

</html>