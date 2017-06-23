<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${list.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${list.total}",
			startRow : "${list.startRow}",
			endRow : "${list.endRow}",
			groups : "${list.pages}" >= 5 ? 5 : "${list.pages}",
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
			//	 			        var page = location.search.match(/page=(\d+)/);
			//	 			        return page ? page[1] : 1;
				return "${list.pageNum}";
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					$("#page").val(e.curr);
					$("#form1").submit();
				}
			}
		});
	});
	/** 全选全不选 */
	function selectAll() {
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		if (checkAll.checked) {
			for ( var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
			}
		} else {
			for ( var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
			}
		}
	}

	/** 单选 */
	function check() {
		var count = 0;
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		for ( var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == false) {
				checkAll.checked = false;
				break;
			}
			for ( var j = 0; j < checklist.length; j++) {
				if (checklist[j].checked == true) {
					checkAll.checked = true;
					count++;
				}
			}
		}
	}
	
	function view(id) {
		window.location.href = "${pageContext.request.contextPath}/auditTemplat/toAddFirstAudit.html?id="+id;
	}
	function edit() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {
			window.location.href = "${pageContext.request.contextPath}/auditTemplat/toEdit.html?id="+id;
		} else if (id.length > 1) {
			layer.alert("只能选择一个", {offset : ['222px'],shade : 0.01});
		} else {
			layer.alert("请选择需要修改的模板", {offset : ['222px'],shade : 0.01});
		}
	}
	function addFirstAudit() {
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {
			window.location.href = "${pageContext.request.contextPath}/auditTemplat/toAddFirstAudit.html?id="+ id;
		} else if (id.length > 1) {
			layer.alert("只能选择一个", {offset : [ '222px'],shade : 0.01});
		} else {
			layer.alert("请选择一个模板", {offset : [ '222px'],shade : 0.01});
		}
	}
	function del() {
		var ids = [];
		$('input[name="chkItem"]:checked').each(function() {
			ids.push($(this).val());
		});
		if (ids.length > 0) {
			layer.confirm('您确定要删除吗?',{title : '提示',offset : [ '222px'],shade : 0.01},function(index) {
				layer.close(index);
				window.location.href = "${pageContext.request.contextPath}/auditTemplat/delete.html?ids="+ids;
			});
		} else {
			layer.alert("请选择要删除模板", {offset : [ '222px'],shade : 0.01});
		}
	}
	function add() {
		window.location.href = "${pageContext.request.contextPath}/auditTemplat/toAdd.html";
	}
	
	function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    	$("#cId").val("");
    }
	
	//编辑模板内容
	function editTemplat(templetKind,templetId){
		window.location.href = "${pageContext.request.contextPath}/auditTemplat/editTemplat.html?templetKind="+templetKind+"&templetId="+templetId;
	}
	
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
	
	function showCategory(tempId) {
		var rootCode = null;
		var zTreeObj;
		var zNodes;
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
				otherParam: {
					"tempId": tempId,
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
	
	function searchs(tempId){
		var name=$("#search").val();
		if(name!=""){
		 	var zNodes;
			var zTreeObj;
			var setting = {
				async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
						otherParam: {
							"tempId": tempId,
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
				url: "${pageContext.request.contextPath}/auditTemplat/searchCategory.do",
				data: { "name" : encodeURI(name)},
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
</script>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">支撑系统</a>
				</li>
				<li><a href="javascript:void(0)">后台管理</a>
				</li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/auditTemplat/list.html')">评审模板管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container">
		<div class="headline-v2">
			<h2>模版管理</h2>
		</div>
		<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
			    <div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs()">
			    </div>
			    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
			</div>
	   	</div>
		<!-- 查询 -->
		<div class="search_detail">
			<form action="${pageContext.request.contextPath}/auditTemplat/list.html" id="form1" method="post" class="mb0">
				<input type="hidden" name="page" id="page">
				<ul class="demand_list">
					<li>
					   <label class="fl">模板名称：</label><input type="text" id="name" name="name" value="${name}" />
					</li>
					<li>
		               <label class="fl">模板类型：</label>
		               <select class="w178" name="kind">
			               <option value="">请选择</option>
	                       <c:forEach items="${kinds}" var="k" varStatus="vs">
                                  <option value="${k.id}" <c:if test="${k.id eq kind}">selected</c:if> >${k.name}</option>
                           </c:forEach>
		               </select>
		            </li>
		            <li>
						<label class="fl">所属产品目录：</label>
						<div class="input_group w200">
							<input id="cId" name="categoryId"  type="hidden" value="${categoryId}">
					        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryName}"  onclick="showCategory();" />
							<%-- <div class="drop_up" onclick="showCategory();">
							    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
					        </div> --%>
						</div>
					</li>
				</ul>
				<button class="btn mt1 fl" type="submit">查询</button>
                <button type="button" onclick="resetQuery()" class="btn fl mt1">重置</button>
				<div class="clear"></div>
			</form>
		</div>

		<!-- 表格开始-->
		<div class="col-md-12 pl20 mt10">
			<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			<!-- <button class="btn btn-windows add" type="button" onclick="addFirstAudit()">初审项定义</button> -->
		</div>

		<div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped opinter">
						<thead>
							<tr>
								<th class="info w30"><input id="checkAll" type="checkbox"
									onclick="selectAll()" />
								</th>
								<th class="info w50">序号</th>
								<th class="info">名称</th>
								<th class="info">所属产品目录</th>
								<th class="info">类型</th>
								<th class="info">操作</th>
								<!-- <th class="info">是否公开</th>
								<th class="info">创建人</th>
								<th class="info">创建日期</th>
								<th class="info">修改日期</th> -->
							</tr>
						</thead>
						<c:forEach items="${list.list}" var="templet" varStatus="vs">
							<tr>

								<td class="tc "><input onclick="check()"
									type="checkbox" name="chkItem" value="${templet.id}" />
								</td>

								<td class="tc " >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>

								<td class="tl pl20" >${templet.name}</td>

								<td class="tl pl20" >${templet.categoryName}</td>

								<td class="tl pl20" >
								    <c:forEach items="${kinds}" var="k" varStatus="vs">
		                               <c:if test="${k.id eq templet.kind}">${k.name}</c:if>
		                            </c:forEach>
								</td>
								<td class="tc ">
								    <button class="btn btn-windows edit" type="button" onclick="editTemplat('${templet.kind}','${templet.id}')">编辑</button>
								</td>
								<%-- 
								<c:if test="${templet.isOpen eq '0'}">
									<td class="tc opinter" onclick="view('${templet.id}')">公开</td>
								</c:if>
								<c:if test="${templet.isOpen eq '1'}">
									<td class="tc opinter" onclick="view('${templet.id}')">私有</td>
								</c:if>

								<td class="tc opinter" onclick="view('${templet.id}')">${templet.creater}</td>

								<td class="tc opinter" onclick="view('${templet.id}')"><fmt:formatDate
										value='${templet.createdAt}' pattern="yyyy-MM-dd" />
								</td>
								<td class="tc opinter" onclick="view('${templet.id}')"><fmt:formatDate
										value='${templet.updatedAt}' pattern="yyyy-MM-dd " />
								</td> --%>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div id="pagediv" align="right"></div>
		</div>
</body>
</html>
