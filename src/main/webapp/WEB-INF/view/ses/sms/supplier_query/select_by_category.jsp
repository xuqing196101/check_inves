<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_query/ajax_supplier.js"></script>
		<script type="text/javascript">
			var key;
			$(function() {
				var zTreeObj;
				var zNodes;
				loadZtree();

				function loadZtree() {
					var setting = {
						async: {
							autoParam: ["id"],
							enable: true,
							url: "${pageContext.request.contextPath}/category/supplierCreatetree.do",
							otherParam: {
								categoryIds: "${categoryIds}",
							},
							dataType: "json",
							type: "post",
						},
					 /* check: {
							enable: true,
							chkboxType: {
								"Y": "s",
								"N": "s"
							}
						}, */
						callback: {
							onClick: zTreeOnClick
						},
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "parentId"
							}
						},
						view: {
							fontCss: getFontCss
						}
					};
					zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
					key = $("#key");
					key.bind("focus", focusKey)
						.bind("blur", blurKey)
						.bind("propertychange", searchNode)
						.bind("input", searchNode);
				}
			});

            function init(){
            $("#page").val(1);
            $("#supplierTypeId").val();
            $("#itemTypeName").val();
            empty();
            }
            function getroot(){
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes =treeObj.getSelectedNodes();  
            var selectNode;
            var tempnode;
            if(nodes.length>0){
                selectNode = nodes[0];
                var l = selectNode.level;
                if(selectNode.level!=0){
                    for(var i=0;i< l;i++){
                        if(i==0){
                            tempnode=selectNode.getParentNode();
                        }else{
                            tempnode = tempnode.getParentNode();
                        }
                    }
                }
            }
           return tempnode;
        }
			function zTreeOnClick(event, treeId, treeNode){
					var name = treeNode.name;
					var categoryIds = treeNode.id;
					var tempnode= getroot();
					if(tempnode){
						if (treeNode.level !=3 && tempnode.name != "工程") {
							$("#selectSupplierType").hide();
						} else if (treeNode.level ==3 && tempnode.name != "工程") {
							$("#selectSupplierType").show();
						} else if (tempnode.name == "工程") {
							//如果是工程类别，获取该品目的所有等级
							$.ajax({
								type : "POST",
								url : globalPath + "/supplierQuery/ajaxCategoryLevels.do",
								data : {"categoryId" : categoryIds},
								success : function(obj) {
									if (obj.status == 500 || obj.status == 501) {
										var html = "<option selected='selected' value=''>全部</option>";
										$(obj.data).each(
												function(index, item) {
													html += "<option  value='" + item.id + "'>" + item.name + "</option>";
												}
										);
										$("#projectAllLevels").empty();
										$("#projectAllLevels").append(html);
									} else {
										layer.msg(obj.msg);
									}
								},
								error : function(data) {
									layer.msg("等级请求异常!");
									layer.close(index);
								},
							});
							$("#projectLevel").show();
						}
						$("#categoryIds").val(categoryIds);
					 	$("#page").val(1);
					 	$("#supplierTypeId").val(tempnode.id);
                     	$("#itemTypeName").val(tempnode.name); 
						findSupplier(treeNode.level);
					}else{
						$("#selectSupplierType").hide();
						$("#projectLevel").hide();
						//根节点
						$("#page").val(1);
						$("#categoryIds").val("");
					 	$("#supplierTypeId").val(treeNode.id);
                     	$("#itemTypeName").val(treeNode.name); 
						findSupplier();
					}
			}


			function focusKey(e) {
				if(key.hasClass("empty")) {
					key.removeClass("empty");
				}
			}

			function blurKey(e) {
				if(key.get(0).value === "") {
					key.addClass("empty");
				}
			}
			var lastValue = "",
				nodeList = [],
				fontCss = {};

			function clickRadio(e) {
				lastValue = "";
				searchNode(e);
			}

			function searchNode(e) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var value = $.trim(key.get(0).value);
				var keyType = "name";
				if(key.hasClass("empty")) {
					value = "";
				}
				if(lastValue === value) return;
				lastValue = value;
				if(value === "") return;
				updateNodes(false);
				nodeList = zTree.getNodesByParamFuzzy(keyType, value);
				updateNodes(true);
			}

			function updateNodes(highlight) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				for(var i = 0, l = nodeList.length; i < l; i++) {
					nodeList[i].highlight = highlight;
					zTree.updateNode(nodeList[i]);
				}
			}

			function getFontCss(treeId, treeNode) {
				return(!!treeNode.highlight) ? {
					color: "#A60000",
					"font-weight": "bold"
				} : {
					color: "#333",
					"font-weight": "normal"
				};
			}

			function filter(node) {
				return !node.isParent && node.isFirstNode;
			}
			function resetQuery() {
				var Obj = $.fn.zTree.getZTreeObj("treeDemo");
				Obj.checkAllNodes(false);
				$("#supplierName").val("");
				$("#contactName").val("");
				$("#form1").submit();
			}
			
			function iFrameHeight() {   
	      var ifm= document.getElementById("open_main");   
	      var subWeb = document.frames ? document.frames["open_main"].document : ifm.contentDocument;   
	      if(ifm != null && subWeb != null) {
	         ifm.height = subWeb.body.scrollHeight;
	         /*ifm.width = subWeb.body.scrollWidth;*/
	      	}   
	      } 
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/selectByCategory.html')">按照品目查询供应商</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container content height-350">
			<div class="row">
				<!-- Begin Content -->
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-xs-12 mb20">
						<input type="button" class="btn fl mt1" onclick="resetAllAgain()" value="计算全部">
					</div>
					<div class="col-md-3 col-sm-4 col-xs-12" id="show_tree_div">
						<div class="tag-box tag-box-v3">
							<ul id="treeDemo" class="ztree s_ztree" ></ul>
						</div>
					</div>
					<!-- 右侧内容开始-->
					<div class="tag-box tag-box-v4 col-md-9 col-sm-8 col-xs-12" id="show_content_div">
                    <%@ include file="/WEB-INF/view/ses/sms/supplier_query/ajax_supplier.jsp" %>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>