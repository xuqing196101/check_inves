<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>任务管理</title>  
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lodop/LodopFuncs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

 
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
    
    function check(ele){
        var flag = $(ele).prop("checked");
        var purchaseType = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).text();
        purchaseType = $.trim(purchaseType);
        var goodUse = $("input[name='chkItem']:checked").parents("tr").find("td").eq(13).text();
        goodUse = $.trim(goodUse);
        if (!goodUse) {
            goodUse = null;
        }
        var id = $(ele).val();
        $.ajax({
                    url:"${pageContext.request.contextPath}/project/checkDeail.html",
                    data:"id="+id,
                    type:"post",
                    dataType:"json",
                    success:function(result){
                        for (var i = 0; i < result.length; i++) {
                           $("input[name='chkItem']").each(function() {
                                var v1 = result[i].id;
                                var v3 = result[i].purchaseType;
	                          if(v3 == purchaseType){
	                                var v2 = $(this).val();
	                                if (v1 == v2) {
	                                    $(this).prop("checked", flag);
	                                }
	                          }else{
	                               layer.alert("采购方式不相同",{offset: ['222px', '390px'], shade:0.01});
	                          }
                           });
                        } 
                      $("input[name='chkItem']:checked").each(function() {
                            var currGoodUse = $(this).parents("tr").find("td").eq(13).text();
                            currGoodUse = $.trim(currGoodUse);
                            if (!currGoodUse) {
                                currGoodUse = null;
                            }
                            if (currGoodUse != goodUse) {
                                $(this).prop("checked", false);
                                layer.alert("进口方式不相同",{offset: ['222px', '390px'], shade:0.01});
                            }
                       });
                    },
                    error: function(){
                        layer.msg("失败",{offset: ['222px', '390px']});
                    }
         });
    }
    
     function save(){
        var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        });
         if(id.length>0){
              $("#detail_id").val(id);
              $("#save_form_id").submit();
         }
    } 
    
    function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
    }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
          <div class="container">
               <ul class="breadcrumb margin-left-0">
               <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">项目管理</a></li><li class="active"><a href="#">选择明细</a></li>
               </ul>
            <div class="clear"></div>
          </div>
       </div>
  
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="clear"></div>

     
     <div class="container">
          <div class="col-md-8">
        <button class="btn btn-windows save" type="button" onclick="save();">确定</button>
        <button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">返回</button>
          </div>
      </div>
   <div class="container margin-top-5">
          <div class="content padding-left-25 padding-right-25 padding-top-5">
               <table class="table table-striped table-bordered table-hover">
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
          <th class="info">采购方式建议</th>
          <th class="info">供应商名称</th>
          <th class="info">是否申请办理免税</th>
          <th class="info">物资用途（进口）</th>
          <th class="info">使用单位（进口）</th>
          <th class="info">备注</th>
          <th class="info w30">操作</th>
        </tr>
        </thead>
            <tbody id="tbody_id">
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.seq}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">${obj.purchaseCount}</td>
              <td class="tc">${obj.price}</td>
              <td class="tc">${obj.budget}</td>
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc">${obj.purchaseType}</td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
              <td class="tc">${obj.memo}</td>
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this)"  alt=""></td>
            </tr>
     
         </c:forEach>  
         </tbody>

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
   </div>
 </div>


 <form id="save_form_id" action="${pageContext.request.contextPath}/project/add.html" method="post" target="_parent">
    <input id="detail_id" name="id" type="hidden" />
    <input  name="checkedIds" value="${checkedIds}" type="hidden" />
 </form> 
     </body>
</html>
