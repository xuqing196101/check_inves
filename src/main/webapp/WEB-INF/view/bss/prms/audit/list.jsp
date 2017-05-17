<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <title>评标管理</title>  
  <script type="text/javascript">
  var expert = "${expert}";
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
   //项目评审
 /* function toAudit(){
    var count = 0;
    var ids = document.getElementsByName("chkItem");
 
     for(i=0;i<ids.length;i++) {
       if(document.getElementsByName("chkItem")[i].checked){
       var id = document.getElementsByName("chkItem")[i].value;
       var value = id.split(",");
       count++;
    }
  }   
      if(count>1){
        layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      }else if(count<1){
        layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      }else if(count==1){
        window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+value[0]+"&packageId="+value[1];
      }
  }*/
  //符合性审查
  function toAudit(projectId, packageId){
      if (expert.status == '4' || expert.status == 4) {
      layer.msg("抱歉,您暂未通过复审,无法进行此项操作!");
      } else if (expert.status == '6' || expert.status == 6) {
        layer.confirm("抱歉,您已被剔除!", {
        btn : [ '确定' ]
      //按钮
      }, function() {
        window.location.href = "${pageContext.request.contextPath}/login/loginOut.html";
      });
      } else {
      window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
      }
  }
  //经济技术审查
  function toFirstAudit(projectId, packageId){
    // 下面代码是判断必须符合性审查通过才可以进入经济技术审查
    /*$.ajax({
      url: "${pageContext.request.contextPath}/expert/validateIsGrade.do",
      data: {"projectId": projectId, "packageId": packageId},
      success: function(response){
        // 1 代表isAudit==1 and isGrade != 1
        if (response == "1") {
          window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+projectId+"&packageId="+packageId;
        } else {
          layer.alert("符合性审查全部通过之后才可以进行此项操作!", {
            offset : [ '222px', '700px' ],
            shade : 0.01
          });1
        }
      }
    });*/
    // 直接进入经济技术审查
    window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toGrade.html?projectId="+projectId+"&packageId="+packageId;
  }
  function showView (packageId) {
    $.ajax({
      url: "${pageContext.request.contextPath}/reviewFirstAudit/isShowView.do",
      data: {"packageId": packageId},
      success: function(response){
        if (response == '1') {
          window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/showPackView.html?packageId="+packageId;
        } else {
          layer.msg("只有评审结束的项目(包)才可以进行查看!");
        }
      }     
    });
  }
  $(function() {
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages: "${projectExtList.pages}", //总页数
      skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip: true, //是否开启跳页
      total: "${projectExtList.total}",
      startRow: "${projectExtList.startRow + 1}",
      endRow: "${projectExtList.endRow + 1}",
      groups: "${projectExtList.pages}" >= 3 ? 3 : "${projectExtList.pages}", //连续显示分页数
      curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${projectExtList.pageNum}";
      }(),
      jump: function(e, first) { //触发分页后的回调
        if(!first) { //一定要加此判断，否则初始时会无限刷新
          $("#pageNum").val(e.curr);
          $("#formSearch").submit();
        }
      }
    });
  });
  function resetForm() {
    $("input[type='text']").val("");
    $("#status option:selected").removeAttr("selected");
  }
  function review(){
    var ids = new Array();
    var checkCount = 0;
    $("input[name='chkItem']").each(function(index, element){
      if (element.checked) {
        checkCount++;
      }
    });
    if (checkCount == 0) {
      layer.msg("请先选择一项!");
    } else if (checkCount == 1) {
      var expertId = "${expertId}";
      ids = $(":checked").next("input[type='hidden']").val().split(",");
      var projectId = ids[0];
      var packageId = ids[1];
      $.ajax({
        url: "${pageContext.request.contextPath}/expert/getReviewType.do",
        data: {"expertId" : expertId, "packageId" : packageId},
        success: function(data){
          if (data == '1') {
            toAudit(projectId, packageId);
          } else if (data == '2') {
            toFirstAudit(projectId, packageId);
          } else if (data == '3') {
            //进入基准价法和最低价法的经济技术评审
            window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toCheck.html?projectId="+projectId+"&packageId="+packageId;
          } else {
            layer.msg(data);
          }
        }
      });
    } else {
      layer.msg("只能选择一项!");
    }
  }
   function view(packageId,expertId){
        window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/showView.html?packageId="+packageId+"&expertId="+expertId;
      }
</script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">项目评审</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="container" id="tab-1">
   <div class="headline-v2 fl">
      <h2>项目评审</h2>
   </div> 
   </div>
   <div class="container">
      <div class="search_detail">
        <form action="${pageContext.request.contextPath}/expert/projectList.html"  method="post" id="formSearch">
          <input type="hidden" name="pageNum" id="pageNum">
          <ul class="demand_list">
          <li>
            <label class="fl">项目名称：</label>
            <input type="text" name="projectName" value="${projectName}">
          </li>
          <li>
            <label class="fl">项目编号：</label>
            <input type="text" name="projectCode" value="${projectId}">
          </li>
          <li>
            <label class="fl">状态：</label>
            <select name="status" id="status">
              <option value="0">全部</option>
              <option value="1" <c:if test="${status eq '1' or status == null}">selected</c:if> >资格性和符合性检查</option>
              <option value="2" <c:if test="${status eq '2'}">selected</c:if> >经济技术评审</option>
              <option value="3" <c:if test="${status eq '3'}">selected</c:if> >评审结束</option>
            </select>
          </li>
        </ul>
        <input class="btn fl" value="查询" type="submit">
        <input class="btn fl" value="重置" type="button" onclick="resetForm();">
        <div class="clear"></div>
        </form>
      </div>
      <div class="col-md-12 col-sm-12 col-xs-12 pl20 mt10">
        <input class="btn fl" value="评审" type="button" onclick="review()">
      </div>
      <div class="container margin-top-5">
       <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">项目名称</th>
          <th class="info w200">项目编号</th>
          <th class="info">包名</th>
      <th class="info w100">开标时间</th>
      <th class="info w200">状态</th>
      </tr>
        </thead>
        <tbody id="tbody_id">
        
        <c:forEach items="${projectExtList.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="checkbox" name="chkItem"><input type="hidden" value="${obj.id},${obj.packageId}"></td>
              <td class="tc w50" onclick="view('${obj.packageId}')">${vs.count}</td>
              <td onclick="view('${obj.packageId}')">${obj.name}</td>
              <td onclick="view('${obj.packageId}')">${obj.projectNumber}</td>
              <td class="tc" onclick="view('${obj.packageId}')">${obj.packageName}</td>
              <td class="tc" onclick="view('${obj.packageId}')"><fmt:formatDate pattern="yyyy-MM-dd" value="${obj.bidDate}"/></td>
              <td class="tc">
                <c:forEach items="${obj.packageExperts}" var="pe">
                  <c:if test="${pe.isAudit == 2 or pe.isAudit == 0}">
                    资格性和符合性检查
                  </c:if>
                  <c:if test="${pe.isAudit == 1 && pe.isGather == 0}">
                    资格性和符合性检查已提交
                  </c:if>
                  <c:if test="${(pe.isGrade == 0 and pe.isGather == 1 and pe.isAudit == 1) or (pe.isGrade == 2 and pe.isGather == 1 and pe.isAudit == 1)}">
                    经济技术评审
                  </c:if>
                  <c:if test="${pe.isGrade == 1 and pe.isGather == 1 and pe.isGatherGather == 0}">
                    经济技术评审已提交
                  </c:if>
                  <!-- 符合性审查结束，经济技术审查结束 -->
                  <c:if test="${pe.isGatherGather == 1 and pe.isGather == 1}">
                    评审结束
                  </c:if>
                </c:forEach>
              </td>
            </tr>
         </c:forEach> 
        </tbody>
      </table>
      <div id="pagediv" align="right"></div>
      </div>
  </div> 
  </body>
</html>
