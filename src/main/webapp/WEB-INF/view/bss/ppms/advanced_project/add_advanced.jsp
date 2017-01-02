<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%
    String tokenValue = new Date().getTime()
          + UUID.randomUUID().toString() + "";
%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      
      function check(ele){
        var flag = $(ele).prop("checked");
		    var id = $(ele).val();
		    $.ajax({
		      url:"${pageContext.request.contextPath}/advancedProject/checkDetail.html",
		      data:"id="+id,
		      type:"post",
		      dataType:"json",
		      success:function(result){
		        for (var i = 0; i < result.length; i++) {
		          $("input[name='chkItem']").each(function() {
		            var v1 = result[i].id;
		            var v2 = $(this).val();
		            if (v1 == v2) {
		              $(this).prop("checked", flag);
		            }
		          });
		        } 
		      },
		      error: function(){
		        layer.msg("失败",{offset: ['222px', '390px']});
		      }
		    });
      }
      
      function download(){
        var proName = $("#proName").val();
        var userId = $("#userId").val();
        var orgName = $("#orgName").val();
        var orgId = $("#orgIds").val();
        var kindName = $("#kindName").val();
        var seq = $("#planNo").val();
        window.location.href = "${pageContext.request.contextPath}/advancedProject/download.html?proName="+proName+"&userId="+userId+
                                "&orgName="+orgName+"&orgId="+orgId+"&kindName="+kindName+"&seq="+seq;
      }
      
      
      function upload(){
        var proName = $("#proName").val();
        var projectNumber = $("#projectNumber").val();
        var department = $("#department").val();
        var purchaseType = $("#purchaseType").val();
        var planType = $("#planType").val();
        var organization = $("#orgIds").val();
         var ids = [];
        $('input[name="id"]').each(function() {
          ids.push($(this).val());
        });
        if(proName == ""){
          layer.tips("项目名称不允许为空", "#proName");
        }else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#projectNumber");
        } else {
          layer.open({
            type : 2, //page层
            area : [ '800px', '500px' ],
            title : '下达',
            shade : 0.01, //遮罩透明度
            moveType : 1, //拖拽风格，0是默认，1是传统拖动
            shift : 1, //0-6的动画形式，-1不开启
            shadeClose : true,
            content : '${pageContext.request.contextPath}/advancedProject/attachment.html?proName='+proName+
            '&projectNumber='+projectNumber+'&department='+department+'&purchaseType='+purchaseType+'&ids='+ids+'&planType='+planType+'&organization='+organization,
          });
        }
        
      }
      
      
      function goBack(){
         window.location.href = "${pageContext.request.contextPath}/collect/list.html";
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">预研项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">新建预研项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <sf:form id="form1" action="${pageContext.request.contextPath}/advancedProject/save.html" method="post" modelAttribute="project">
        <div>
          <h2 class="count_flow"><i>1</i>添加预研基本信息</h2>
          <% session.setAttribute("tokenSession", tokenValue); %>
          <input type="hidden" name="token2" value="<%=tokenValue%>">
          <input type="hidden" id="userId" name="user" value="${user}">
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>预研项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="proName" type="text" class="input_group" name="name"  />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>预研项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="projectNumber" type="text" class="input_group" name="projectNumber"/>
                <span class="add-on">i</span>
                <div class="cue">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>需求明细</h2>
          <div class="ul_list">
            <div class="content table_box over_scroll">
              <table class="table table-bordered table-condensed table-hover space_nowrap">
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
                    <th class="info">交货期限</th>
                    <th class="info">采购方式</th>
                    <th class="info">采购机构</th>
                    <th class="info">供应商名称</th>
                    <th class="info">是否申请办理免税</th>
                    <th class="info">物资用途（进口）</th>
                    <th class="info">使用单位（进口）</th>
                    <th class="info">备注</th>
                  </tr>
                </thead>
                <tbody id="task_id">
                  <c:forEach items="${lists}" var="obj" varStatus="vs">
                    <tr class="tc">
                      <td class="tc w50">${obj.seq} <input type="hidden" id="planNo" name="planNo" value="${obj.planNo}"/></td>
                      <td class="tc">
                          ${obj.department}
                           <input type="hidden" id="orgName" name="department" value="${obj.department}"/>
                        
                        <input type="hidden" id="id" name="id" value="${obj.id}"/>
                      </td>
                      <td class="tc">${obj.goodsName}</td>
                      <td class="tc">${obj.stand}</td>
                      <td class="tc">${obj.qualitStand}</td>
                      <td class="tc">${obj.item}</td>
                      <td class="tc">${obj.purchaseCount}</td>
                      <td class="tc">${obj.price}</td>
                      <td class="tc">${obj.deliverDate}</td>
                      <td class="tc">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">
                            ${kind.name}
                            <input type="hidden" id="kindName" name="kindName" value="${kind.name}"/>
                            <input type="hidden" id="purchaseType" name="purchaseType" value="${obj.purchaseType}"/>
                            <input type="hidden" id="planName" name="planName" value="${obj.planName}"/>
                          </c:if>
                        </c:forEach>
                      </td>
                      <td class="tc">
                      <c:if test="${list2 != null}">
                        <c:forEach items="${list2}" var="list" varStatus="vs">
                          <c:if test="${obj.organization eq list.id}">${list.name}</c:if>
                          <input type="hidden" id="orgIds" name="organization" value="${list.id}"/>
                        </c:forEach>
                        </c:if>
                      </td>
                      <td class="tc">${obj.supplier}</td>
                      <td class="tc">${obj.isFreeTax}</td>
                      <td class="tc">${obj.goodsUse}</td>
                      <td class="tc">${obj.useUnit}</td>
                      <td class="tc">${obj.memo} <input type="hidden" id="planType" name="planType" value="${obj.planType}"/></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
          (请先下载预研通知书)
          <button class="btn btn-windows input" type="button" onclick="download()">下载预研通知书</button>
          <button class="btn" onclick="upload()" type="button">下达</button>
          <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
        </div>
      </sf:form>
    </div>
  </body>

</html>