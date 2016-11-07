<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>供应商列表</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
     $(function(){
      laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}">=3?3:"${result.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
          return "${result.pageNum}";
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
                $("#form1").submit();
              }
          }
      });
    });
</script>
<script type="text/javascript">
    /** 全选全不选 */
  function selectAll(){
     var checklist = document.getElementsByName ("chkItem");
     var checkAll = document.getElementById("checkAll");
       if(checkAll.checked){
         for(var i=0;i<checklist.length;i++)
         {
            checklist[i].checked = true;
         } 
       }else{
        for(var j=0;j<checklist.length;j++)
        {
           checklist[j].checked = false;
        }
      }
    }
  
  /** 单选 */
  function check(){
     var count=0;
     var checklist = document.getElementsByName ("chkItem");
     var checkAll = document.getElementById("checkAll");
     for(var i=0;i<checklist.length;i++){
         if(checklist[i].checked == false){
           checkAll.checked = false;
           break;
         }
         for(var j=0;j<checklist.length;j++){
           if(checklist[j].checked == true){
               checkAll.checked = true;
               count++;
             }
         }
       }
  }
    //审核
    function shenhe(){
    var size = $(":radio:checked").size();
    
    if (!size) {
      layer.msg("请选择供应商 !");
      return;
    }
    var id = $(":radio:checked").val();
    var state = $("#"+id).parents("tr").find("td").eq(5).text().trim();
    if (state == "已审核" || state == "初审核未通过" || state == "复审未通过") {
        layer.msg("请选择待审核项 !");
        return;
      } 
    
    $("input[name='supplierId']").val(id);
    $("#shenhe_form_id").submit();
    
    } 
    
    
    //重置搜索栏

  function resetForm(){
      $("input[name='supplierName']").val("");
        //还原select下拉列表只需要这一句
      $("#status option:selected").removeAttr("selected");
    }
</script>
</head>
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="#"> 首页</a></li><li><a href="#">供应商注册管理</a></li><li><a href="#">供应商审核</a></li><li class="active"><a href="#">供应商列表</a></li>
      </ul>
    </div>
  </div>
  <div class="container">
    <div class="headline-v2">
      <h2>供应商列表</h2>
    </div>
  </div>  
<!-- 搜索 -->
  <div class="container">
      <div class="p10_25">
        <form action="${pageContext.request.contextPath}/supplierAudit/supplierAll.html"  method="post" id="form1" enctype="multipart/form-data" class="padding-10 border1 mb0"> 
        <input type="hidden" name="page" id="page">
          <ul class="demand_list">
            <li class="fl">
              <label class="fl mt5">供应商名称：</label> 
                <input class="mb0 mt5" name="supplierName" type="text" value="${supplierName }">
            </li>
            <li class="fl">
              <label class="fl mt5">审核状态：</label> 
                <select name="status" class="mb0 mt5" id="status">
                  <option value="">全部</option>
                  <option <c:if test="${state == 0 }">selected</c:if> value="0">待初审</option>
                  <option <c:if test="${state == 1 }">selected</c:if> value="1">待复审</option>
                  <option <c:if test="${state == 5 }">selected</c:if> value="5">初审中</option>
                  <option <c:if test="${state == 6 }">selected</c:if> value="6">复审中</option>
                  <option <c:if test="${state == 3 }">selected</c:if> value="3">已审核</option>
                  <option <c:if test="${state == 2 }">selected</c:if> value="2">初审核未通过</option>
                  <option <c:if test="${state == 4 }">selected</c:if> value="4">复审不通过</option>
               </select> 
	           </li>
	           <%-- <li class="fl">
	              <label class="fl mt5">企业类型：</label> 
	                <select name="supplierType" class="mb0 mt5">
	                  <option value="">全部</option>
	                  <c:forEach var="type" varStatus="vs" items="${supplierType}">
	                    <option value="${type.name}">${type.name}</option>
	                  </c:forEach>
	               </select> 
	            </li> --%>
           <li>
            <input type="submit" class="btn btn_back fl ml10 mt6" value="查询" />
            <button onclick="resetForm();" class="btn btn_back fl ml10 mt6" type="button">重置</button>
           </li>
      </ul>
      <div class="clear"></div>
    </form>
  </div>
  </div>
  <div class="container">
    <div class="col-md-8">
        <button class="btn btn-windows git" type="button" onclick="shenhe();" style="margin-left: 10px;">审核</button>
    </div>
  </div>
  <div class="container margin-top-5">
    <div class="content padding-left-25 padding-right-25 padding-top-5">
      <table class="table table-bordered table-condensed">
        <thead>
          <tr>
            <th class="info w50">选择</th>
            <th class="info w50">序号</th>
            <th class="info">供应商名称</th>
            <th class="info">企业类型</th>
            <th class="info">企业性质</th>
            <!-- <th class="info">企业状态</th> -->
            <th class="info">审核状态</th>
          </tr>
        </thead>
        <c:forEach items="${supplierAll }" var="list" varStatus="page">
          <tr>
            <td class="tc w30"><input name="id" type="radio" value="${list.id}"></td>
            <td class="tc w50">${page.count}</td>
            <td class="tc">${list.supplierName }</td>
            <td class="tc">
              <c:forEach items="${list.listSupplierTypeRelates}" var="str">
                ${str.supplierTypeName}
              </c:forEach>
            </td>
            <td class="tc">${list.businessType }</td>
            <!-- <td class="tc"></td> -->
            <td class="tc" id="${list.id}">
               <c:if test="${list.status==0 }">待初审</c:if>
               <c:if test="${list.status==1 }">待复审</c:if>
               <c:if test="${list.status==5 }">初审中</c:if> 
               <c:if test="${list.status==6 }">复审中</c:if>
               <c:if test="${list.status==7 }">初审退回</c:if>
               <c:if test="${list.status==8 }">复审退回</c:if>
               <c:if test="${list.status==3 }">已审核</c:if>
               <c:if test="${list.status==2 }">初审核未通过</c:if> 
               <c:if test="${list.status==4 }">复审未通过</c:if>
            </td>
          </tr>
        </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
     </div>
   </div>
  <form id="shenhe_form_id" action="${pageContext.request.contextPath}/supplierAudit/essential.html" method="post">
    <input name="supplierId" type="hidden" />
  </form>
</body>
</html>
