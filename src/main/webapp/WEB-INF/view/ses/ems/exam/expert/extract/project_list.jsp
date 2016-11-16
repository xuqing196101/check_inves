<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>项目管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript">
  
  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
            skip: true, //是否开启跳页
            groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//                  var page = location.search.match(/page=(\d+)/);
//                  return page ? page[1] : 1;
                return "${info.pageNum}";
            }(), 
            jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                //  $("#page").val(e.curr);
                    // $("#form1").submit();
                    
                 location.href = '${pageContext.request.contextPath}/project/list.do?page='+e.curr;
                }  
            }
        });
  });
  
  
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
    
    function opens(){
        var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
             window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId="+id+"&&typeclassId=1";
        }
    }
    
    function record(){
           location.href = '${pageContext.request.contextPath}/ExpExtract/resuleRecordlist.do';
    }
    function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    }
  </script>
</head>

<body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a></li>
                <li><a href="#">支撑环境系统</a></li>
                <li><a href="#">专家抽取</a></li>
                <li class="active"><a href="#">抽取项目</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
        <div class="headline-v2">
            <h2>立项列表</h2>
        </div>
        
        <!-- 项目戳开始 -->
       <h2 class="search_detail">
     <form  action="${pageContext.request.contextPath}/ExpExtract/projectlist.html" id="form1" method="post" class="mb0">
     <ul class="demand_list">
    
     <li class="fl">
       <label class="fl">项目名称：<span><input type="hidden" name="page" id="page"><input type="text" name="name" id="proName" value="${projects.name }"/></span></label>
       </li>
       <li class="fl">
      <label class="fl">项目编号：<input type="text" name="projectNumber" id="projectNumber" value="${projects.projectNumber }"/> </label> 
       </li>
         <button class="btn" type="submit">查询</button>
         <button type="reset" class="btn" onclick="resetQuery();">重置</button> 
     </ul>
     <div class="clear"></div>
    </form>
    </h2>
         <div class="col-md-12 pl20 mt10">
            <button class="btn"
                onclick="opens();">人工抽取</button>
            <button class="btn"
                onclick="record();">抽取记录</button>
        </div>
        <div class="container table_box">
      <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">项目名称</th>
          <th class="info">项目编号</th>
          <th class="info">采购方式</th>
          <th class="info">项目状态</th>
        </tr>
        </thead>
        <tbody id="tbody_id">
        
        <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="hidden" value="${obj.status }"/><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.name}</a></td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.projectNumber }</a></td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.purchaseType }</a></td>
              <td class="tc">
              <c:if test="${'1'==obj.status}">实施中</c:if>
              <c:if test="${'2'==obj.status}">已成交</c:if>
              <c:if test="${'3'==obj.status}">已立项</c:if>
              </td>
            </tr>
         </c:forEach> 
        </tbody>
         
         

      </table>
      </div>
       <div id="pagediv" align="right"></div>
    </div>


</body>
</html>
