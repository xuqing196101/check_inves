<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>专家黑名单历史记录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
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
   function resetForm(){
      $("#expertId").attr("value","");
        //还原select下拉列表只需要这一句
      $("#operationType option:selected").removeAttr("selected");
    }
</script>
</head>
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">专家黑名单</a></li><li class="active"><a href="#">操作记录</a></li>
      </ul>
    </div>
  </div>
    <div class="container">
    <div class="p10_25">
      <form action="<%=basePath %>expertBlacklist/expertBlackListLog.html"  method="post" id="form1" enctype="multipart/form-data" class="padding-10 border1 mb0"> 
        <input type="hidden" name="page" id="page">
            <ul class="demand_list">
              <li class="fl">
                <label class="fl mt5">专家姓名：</label>
                <input type="text" id="expertId" name="expertId" class="mb0 mt5" value="${expertName }">
              </li>
              <li class="fl">
                 <label class="fl mt5">操作类型：</label>
                   <select name="operationType"  id="operationType" class="mb0 mt5">
                    <option value="">-请选择-</option>
                      <option <c:if test="${operationType =='0' }">selected</c:if> value="0">新增</option>
                      <option <c:if test="${operationType =='1' }">selected</c:if> value="1">修改</option>
                      <option <c:if test="${operationType =='2' }">selected</c:if> value="2">移除</option>
                  </select>
              </li>
             <li>
               <input type="submit" class="btn btn_back fl ml10 mt6" value="查询" />
               <button onclick="resetForm();" class="btn btn_back fl ml10 mt6" type="button">重置</button>
             </li>
        </ul>
      <div class="clear"></div>
  </form>
  </div>
  </div>
  
  <div class="container margin-top-5">
    <div class="content padding-left-25 padding-right-25 padding-top-5">
      <table class="table table-bordered table-condensed">
        <thead>
          <tr>
            <th class="info w50">序号</th>
            <th class="info">操作人</th>
            <th class="info">操作类型</th>
            <th class="info">专家</th>
            <th class="info">操作时间</th>
            <th class="info">处罚日期</th>
            <th class="info">处罚时限</th>
            <th class="info">处罚方式</th>
            <th class="info">处罚理由</th>
          </tr>
        </thead>
         <c:forEach items="${log }" var="log" varStatus="vs">
          <tr>
              <td class="tc w50">${vs.index+1}</td>
              <td class="tc">${log.operator }</td>
              <td class="tc">
                <c:if test="${log.operationType == 0}">新增</c:if>
                <c:if test="${log.operationType == 1}">修改</c:if>
                <c:if test="${log.operationType == 2}">移除</c:if>
             </td>
             <td class="tc">${log.expertName }</td>
             <td class="tc"><fmt:formatDate type='date' value='${log.operationDate }' dateStyle="default" pattern="yyyy-MM-dd HH:mm"/></td>
             <td class="tc"><fmt:formatDate type='date' value='${log.dateOfPunishment }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
             <td class="tc">${log.punishDate }</td>
             <td class="tc">
                <c:if test="${log.punishType == 1}">警告</c:if>
                <c:if test="${log.punishType == 2}">严重警告</c:if>
                <c:if test="${log.punishType == 3}">取消资格</c:if>
             </td>
            <td class="tc">${log.reason }</td>
          </tr>
        </c:forEach>
      </table>
      <div id="pagediv" align="right"></div>
      <div class="margin-bottom-0  categories">
      <div class="col-md-12 add_regist tc">
        <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
      </div>
    </div>
   </div>
  </div>
  
</body>
</html>
