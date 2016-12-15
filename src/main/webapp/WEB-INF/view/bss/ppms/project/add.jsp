<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ include file="../../../common.jsp"%>
<%
    String tokenValue = new Date().getTime()
					+ UUID.randomUUID().toString() + "";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
  /*分页  */
  $(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	  pages : "${info.pages}", //总页数
	  skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	  skip : true, //是否开启跳页
	  groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
	  curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				return "${info.pageNum}";
	  },
	  jump : function(e, first) { //触发分页后的回调
			   if (!first) { //一定要加此判断，否则初始时会无限刷新
			     location.href = '${pageContext.request.contextPath}/project/add.html?page=' + e.curr;
			   }
	  }
	});
    // 前台验证
	$("#form1").validate({
	  rules : {
		name : {
		  remote : {
			type : "post",
			url : "${pageContext.request.contextPath}/project/SameNameCheck.html",
			dataType : "json",
			data : {
			  name : function() {
			    return $("#pic").val();
			  }
			}
		  }
		},
        projectNumber : {
          remote : {
            type : "post",
            url : "${pageContext.request.contextPath}/project/SameNameCheck.html",
            dataType : "json",
            data : {
              projectNumber : function() {
                return $("#pc").val();
              }
            }
          }
        },
      },
	  messages : {
	    name : {
		  remote : "<div class='cue'>该项目名称已存在</div>"
		},
		projectNumber : {
          remote : "<div class='cue'>该项目编号已存在</div>"
        },
	  }
	});
    // 显示添加明细
	var id = $("#idss").val();
	if (!id) {
	  $("#hide_detail").hide();
	} else {
	  $("#hide_detail").show();
	}
    // 选中任务
	var v = "${checkedIds}";
	if (v) {
	  var vs = v.split(",");
	  for ( var i = 0; i < vs.length; i++) {
	    $("#task_id").find(":checkbox").each(function() {
		  if (vs[i] == $(this).val()) {
		    $(this).prop("checked", true);
		  }
		});
	  }
	}
  });

  /** 勾选节点 */
  function check(ele) {
    var id = $(ele).val();
    var name = $("input[name='name']").val();
    var projectNumber = $("input[name='projectNumber']").val();
	  var checkedIds = "";
	  $("#task_id").find(":checkbox:checked").each(function() {
	     if (checkedIds) {
		   checkedIds += ",";
	     }
	    checkedIds += $(this).val();
	  });
	if ($(ele).prop("checked")) {
	  window.location.href = "${pageContext.request.contextPath}/project/addDeatil.html?id=" + id + "&checkedIds=" + checkedIds + "&name=" + name + "&projectNumber=" + projectNumber;
	}
  }

  // 添加
  function add() {
	var id =[]; 
	var name = $("input[name='name']").val();
	var projectNumber = $("input[name='projectNumber']").val();
	$('input[name="chkItem"]:checked').each(function(){ 
	  id.push($(this).val()); 
	});  
	if(id==""){
	  layer.tips("请勾选明细","#chkItem");
	} else if (name == "") {
	  layer.tips("项目名称不允许为空", "#pic");
	} else if (projectNumber == "") {
	  layer.tips("项目编号不允许为空", "#pc");
	} else {
	  $("#form1").submit();
	}
  }
  
  // 返回
  function bask() {
	window.location.href = "${pageContext.request.contextPath}/project/list.html";
  }
</script>
</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)">首页</a></li>
        <li><a href="javascript:void(0)">保障作业系统</a></li>
        <li><a href="javascript:void(0)">采购项目管理</a></li>
        <li class="active"><a href="javascript:void(0)">新建采购项目</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <div class="container container_box">
    <sf:form id="form1" action="${pageContext.request.contextPath}/project/create.html" method="post" modelAttribute="project">
	  <div>
		<h2 class="count_flow"><i>1</i>添加信息</h2>
		<% session.setAttribute("tokenSession", tokenValue); %>
		<input type="hidden" name="token2" value="<%=tokenValue%>">
		<ul class="ul_list">
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目名称</span>
			  <div class="input-append input_group col-sm-12 col-xs-12 p0">
		        <input id="pic" type="text" class="input_group" name="name" value="${name}"/> 
		        <span class="add-on">i</span>
		        <div class="cue">${ERR_name}</div>
			  </div>
		  </li>
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目编号</span>
			<div class="input-append input_group col-sm-12 col-xs-12 p0">
			  <input id="pc" type="text" class="input_group" name="projectNumber" value="${projectNumber}"/>
			  <span class="add-on">i</span>
			  <div class="cue">${ERR_projectNumber}</div>
		    </div>
		  </li>
		</ul>
	  </div>
	  <div>
		<h2 class="count_flow"><i>2</i>选择采购明细</h2>
		<ul class="ul_list">
		  <div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
			  <thead>
				<tr>
				  <th class="info w50">序号</th>
				  <th class="info">采购任务名称</th>
				  <th class="info">需求部门</th>
				  <th class="info">下达文件编号</th>
				  <th class="info">状态</th>
				  <th class="info">下达时间</th>
				  <th class="info"><i class="star_red">*</i>操作</th>
				</tr>
			  </thead>
			  <tbody id="task_id">
			    <c:forEach items="${info.list}" var="obj" varStatus="vs">
				  <tr style="cursor: pointer;">
					<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					<td class="tc">${obj.name}</td>
					<td class="tc">${obj.purchaseId.name }</td>
					<td class="tc">${obj.documentNumber}</td>
					<td class="tc">
					  <c:if test="${'0'==obj.status}">
						<span class="label rounded-2x label-u">受领</span>
					  </c:if>
					</td>
					<td class="tc"><fmt:formatDate value="${obj.giveTime }" /></td>
					<td class="tc w30">
					  <input type="checkbox" id="chkItem" value="${obj.id }" name="chkItem" onchange="check(this)" alt="" /> 
					  <input type="hidden" id="idss" value="${ids}" />
					</td>
				  </tr>
				</c:forEach>
			  </tbody>
			</table>
			<div id="pagediv" align="right"></div>
		  </div>
		  <div id="hide_detail">
			<div class="content table_box">
			  <table class="table table-bordered table-condensed table-hover">
				<thead>
				  <tr>
					<th class="info w50">序号</th>
					<th class="info">需求部门</th>
					<th class="info">物资名称</th>
					<th class="info">规格型号</th>
					<th class="info">质量技术标准</th>
					<th class="info">计量单位</th>
					<th class="info">采购数量</th>
					<th class="info">单价（元）</th>
					<th class="info">预算金额（万元）</th>
					<th class="info">交货期限</th>
					<th class="info">采购方式</th>
					<th class="info">供应商名称</th>
					<th class="info">是否申请办理免税</th>
					<th class="info">物资用途（进口）</th>
					<th class="info">使用单位（进口）</th>
					<th class="info">备注</th>
				  </tr>
				</thead>
				<c:forEach items="${lists}" var="obj" varStatus="vs">
				  <tr style="cursor: pointer;">
					<td class="tc w50"> ${obj.seq}
					  <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
					  <input type="hidden" name="list[${vs.index }].id" value="${obj.id }">
					</td>
					<td class="tc"> ${obj.department}
					  <input type="hidden" name="list[${vs.index }].department" value="${obj.department }">
					</td>
					<td class="tc">${obj.goodsName}
					  <input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }">
					</td>
					<td class="tc">${obj.stand}
					  <input type="hidden"name="list[${vs.index }].stand" value="${obj.stand }">
					</td>
					<td class="tc">${obj.qualitStand}
					  <input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }">
					</td>
					<td class="tc">${obj.item}
					  <input type="hidden" name="list[${vs.index }].item" value="${obj.item }">
					</td>
					<td class="tc">${obj.purchaseCount}
					  <input type="hidden" name="list[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
					</td>
					<td class="tc">${obj.price}
					  <input type="hidden" name="list[${vs.index }].price" value="${obj.price }">
					</td>
					<td class="tc">${obj.budget}
					  <input type="hidden" name="list[${vs.index }].budget" value="${obj.budget }">
					</td>
					<td class="tc">${obj.deliverDate}
					  <input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }">
					</td>
					<td class="tc">
				      <c:forEach items="${kind}" var="kind" >
                        <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                      </c:forEach>
					  <input type="hidden" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }">
					</td>
					<td class="tc">${obj.supplier}
					  <input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }">
					</td>
					<td class="tc">${obj.isFreeTax}
					  <input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
					</td>
					<td class="tc">${obj.goodsUse}
					  <input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
					</td>
					<td class="tc">${obj.useUnit}
					  <input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }">
					</td>
					<td class="tc">${obj.memo}
					  <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
					  <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
					  <input type="hidden" name="list[${vs.index }].detailStatus" value="${obj.detailStatus}"> 
					  <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType}">
					</td>
				  </tr>
				</c:forEach>
			  </table>
			</div>
		  </div>
		</ul>
	  </div>
	  <div class="col-md-12 tc">
        <button class="btn" onclick="add()" type="button">下一步</button>
        <button class="btn btn-windows back" onclick="bask()" type="button">返回</button>
      </div>
	</sf:form>
  </div>
</body>
</html>
