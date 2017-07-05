<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
var index;
function cancel(){
   layer.close(index);
}
function openWindow(){
	index = layer.open({
          type: 1, //page层
          area: ['700px', '300px'],
          title: '新增模板',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '250px'],
          shadeClose: true,
          content:$('#package') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
	 });
}
function submit1(){
	
	/* var name = $("#name").val();
	if(!name){
		layer.tips("请填写名称", "#name");
		return ;
	}
	var id=[]; 
	$('input[name="kind"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==0){
		layer.tips("请选择类型", "#kind");
		return ;
	} */
	
	/* var creater = $("#creater").val();
	if(!creater){
		layer.tips("请填写名称", "#creater");
		return ;
	} */
	/* var id2=[]; 
	$('input[name="isOpen"]:checked').each(function(){ 
		id2.push($(this).val());
	}); 
	if(id2.length==0){
		layer.tips("请选择一个", "#isOpen");
		return ;
	}
	var id3=[]; 
	$('input[name="isUse"]:checked').each(function(){ 
		id3.push($(this).val());
	}); 
	if(id3.length==0){
		layer.tips("请选择一个", "#isUse");
		return ;
	} */
	$("#form1").submit();
}
	function goBack(){
	    window.location.href = '${pageContext.request.contextPath}/auditTemplat/list.html';
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
</head>
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
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/auditTemplat/toEdit.html')">评审模版管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
<div class="container container_box">
	<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
			    <div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs('${templat.id}')">
			    </div>
			    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
			</div>
	   	</div>
	<sf:form action="${pageContext.request.contextPath}/auditTemplat/edit.html" method="post" id="form1" modelAttribute="firstAuditTemplat">
                 <h2 class="list_title">修改模板</h2>
                 <input type="hidden" name="isUse" value="${templat.isUse}">
                 <input type="hidden" name="id" value="${templat.id}">
       <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>模板名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input type="text" class="input_group" id="name" maxlength="30" name="name" value="${templat.name}">
                        <span class="add-on">i</span>
                        <div class="cue"><sf:errors path="name"/></div>
                    </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                 <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>模板类型</span>
                 <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
                 <select  name="kind">
                     <option value="">请选择</option>
                     <c:forEach items="${kinds}" var="k" varStatus="vs">
                         <option value="${k.id }" <c:if test="${k.id eq templat.kind}">selected</c:if>>
                             ${k.name}
                         </option>
                     </c:forEach>
                 </select>
                 <div class="cue"><sf:errors path="kind"/></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-6 col-xs-12" id="choseCategory">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
				<div class="star_red">*</div>所属产品目录：</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
					<input id="cId" name="categoryId"  type="hidden" value="${templat.categoryId}">
			        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryName}"  onclick="showCategory('${templat.id}');" />
					<div class="drop_up" onclick="showCategory('${templat.id}');">
					    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
			        </div>
					<div class="cue"><sf:errors path="categoryId"/></div>
				</div>
			</li>
            <%-- <li class="col-md-3 margin-0 padding-0 "><span class="">是否公开</span>
                    <div class="select_check">
                        <input name="isOpen" maxlength="10" type="radio" value="0" <c:if test="${templat.isOpen eq '0' }">checked="true"</c:if> >公开
                        <input name="isOpen" id="isOpen" type="radio" value="1" <c:if test="${templat.isOpen eq '1' }">checked="true"</c:if> >私有
                    </div>
            </li>
            <li class="col-md-3 margin-0 padding-0 "><span class="">是否可用</span>
                    <div class="select_check">
                        <input name="isUse"  maxlength="10" type="radio" <c:if test="${templat.isUse eq '0' }">checked="true"</c:if>  value="0" >可用
                        <input name="isUse" id="isUse"  type="radio" value="1" <c:if test="${templat.isUse eq '1' }">checked="true"</c:if>>不可用
                        <input type="hidden" name="userId" value="${templat.userId }">
                        <input type="hidden" name="id" value="${templat.id }">
                    </div>
                    <br/>
            </li> --%>
          <%--   <li class="col-md-3 margin-0 padding-0 ">
            <span class="col-md-12 padding-left-5">创建人</span>
                    <div class="input-append">
                        <input readonly="readonly" name="creater" id="creater" maxlength="10" type="text" value="${sessionScope.loginUser.relName}" >
                        <span class="add-on">i</span>
                    </div>
            </li> --%>
       </ul>
       <div class="col-md-12 tc">
		    <input type="button"  value="更新" onclick="submit1();" class="btn btn-windows save"/>
		    <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
	   </div>
  </sf:form>
</div>
</body>
</html>