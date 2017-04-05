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
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          },
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              location.href = '${pageContext.request.contextPath}/advancedProject/add.html?page=' + e.curr;
            }
          }
        });
        // 前台验证
        $("#form1").validate({
          rules: {
            name: {
              remote: {
                type: "post",
                url: "${pageContext.request.contextPath}/advancedProject/SameNameCheck.html",
                dataType: "json",
                data: {
                  name: function() {
                    return $("#pic").val();
                  }
                }
              }
            },
            projectNumber: {
              remote: {
                type: "post",
                url: "${pageContext.request.contextPath}/advancedProject/SameNameCheck.html",
                dataType: "json",
                data: {
                  projectNumber: function() {
                    return $("#pc").val();
                  }
                }
              }
            },
          },
          messages: {
            name: {
              remote: "<div class='cue'>该项目名称已存在</div>"
            },
            projectNumber: {
              remote: "<div class='cue'>该项目编号已存在</div>"
            },
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

      /** 勾选节点 */
      function check(id) {
        var name = $("input[name='name']").val();
        var projectNumber = $("input[name='projectNumber']").val();
        var projectId = $("#projectId").val();
        window.location.href = "${pageContext.request.contextPath}/advancedProject/addDetail.html?id=" + id + "&name=" + name + "&projectNumber=" + projectNumber+"&projectId="+projectId;
      }

      // 添加
      function add() {
        var name = $("input[name='name']").val();
        var projectNumber = $("input[name='projectNumber']").val();
        if(name == "") {
          layer.tips("项目名称不允许为空", "#pic");
        } else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#pc");
        } else {
          $("#form1").submit();
        }
      }
      
      //删除明细
      function deleted(){
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
			    id.push($(this).val());
			  });
        window.location.href = "${pageContext.request.contextPath}/advancedProject/deleted.html?id="+id;
      }
      
      //返回
      function goBack(){
        var id = [];
        $('input[name="chkItem"]').each(function() {
          id.push($(this).val());
        });
        var projectIds = $("#projectIds").val();
        window.location.href = "${pageContext.request.contextPath}/advancedProject/deleted.html?idss="+id+"&projectIds="+projectIds;
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
          <h2 class="count_flow"><i>1</i>添加信息</h2>
          <% session.setAttribute("tokenSession", tokenValue); %>
          <input type="hidden" name="token2" value="<%=tokenValue%>">
          <input type="hidden" id="projectId" name="projectId" value="${projectId}">
          <input type="hidden" id="projectIds" name="projectIds" value="${projectIds}">
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="pic" type="text" class="input_group" name="name" value="${name}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="pc" type="text" class="input_group" name="projectNumber" value="${projectNumber}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>选择需求明细</h2>
          <ul class="ul_list">
            <div class="content table_box">
              <table class="table table-bordered table-condensed table-hover">
                <thead>
                  <tr>
                    <th class="info w50">序号</th>
                    <th class="info">需求部门</th>
                    <th class="info">采购需求名称</th>
                    <th class="info">物资类别</th>
                    <th class="info">提交日期</th>
                    <th class="info">预算总金额</th>
                    <th class="info">状态</th>
                    <th class="info"><i class="star_red">*</i>操作</th>
                  </tr>
                </thead>
                <tbody id="task_id">
                  <c:forEach items="${info.list}" var="obj" varStatus="vs">
                    <tr style="cursor: pointer;">
                      <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                      <td class="tc">${obj.department }<input type="hidden" name="department" value="${obj.department }"></td>

                      <td class="tc">${obj.planName }</td>

                      <td class="tc">
                        <c:forEach items="${dic }" var="dic">
                          <c:if test="${obj.planType==dic.id}">
                            ${dic.name }
                          </c:if>
                        </c:forEach>
                         <input type="hidden" name="list[${vs.index }].id" value="${obj.id }">
                         <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
                         <input type="hidden" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }">
                         <input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
                      </td>
                      <td class="tc">
                        <fmt:formatDate value="${obj.createdAt }" />
                      </td>
                      <td class="tc">
                        <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
                      </td>
                      <td class="tc">
                        <c:if test="${obj.status=='4' }">已受理</c:if>
                      </td>
                      <td class="tc">
                        <a href="javascript:void(0)" id="choice" onclick="check('${obj.planNo}')">请选择</a>
                      </td>
                    </tr>

                  </c:forEach>
                </tbody>
              </table>
              <div id="pagediv" align="right"></div>
            </div>
            <c:if test="${lists != null}">
              <div class="col-md-12 pl20 mt10">
						    <button class="btn btn-windows delete" onclick="deleted();" type="button">删除</button>
						  </div>
              <div class="content table_box over_scroll">
                <table id="table" class="table table-bordered table-condensed">
                  <thead>
                    <tr class="space_nowrap">
                      <th class="info choose"><input type="checkbox" id="checkAll" onclick="selectAll()" alt="" /></th>
                      <th class="info seq">序号</th>
                      <th class="info department">需求部门</th>
                      <th class="info goodsname">物资名称</th>
                      <th class="info stand">规格型号</th>
                      <th class="info qualitstand">质量技术<br>标准</th>
                      <th class="info item">计量<br>单位</th>
                      <th class="info purchasecount">采购<br>数量</th>
                      <th class="info price">单价<br>（元）</th>
                      <th class="info budget">预算金额<br>（万元）</th>
                      <th class="info deliverdate">交货期限</th>
                      <th class="info purchasetype">采购方式<br>建议</th>
                      <th class="info purchasename">供应商名称</th>
                      <th class="info freetax">是否申请<br>办理免税</th>
                      <th class="info goodsuse">物资用途<br>（进口）</th>
                      <th class="info useunit">使用单位<br>（进口）</th>
                      <th class="info memo">备注</th>
                    </tr>
                  </thead>
                  <tbody id="tbody_id">
                    <c:forEach items="${lists}" var="obj" varStatus="vs">
                      <tr style="cursor: pointer;">
                        <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem"  alt=""><input type="hidden" id="detailId" name="detailId" value="${obj.requiredId }"/></td>
                        <td class="tc w50"><div class="w50">${obj.serialNumber}</div></td>
                        <td class=""><div class="w80">${obj.department}</div></td>
                        <td class=""><div class="w80">${obj.goodsName}</div></td>
                        <td class=""><div class="w80">${obj.stand}</div></td>
                        <td class=""><div class="w80">${obj.qualitStand}</div></td>
                        <td class="tc"><div class="w80">${obj.item}</div></td>
                        <td class="tc"><div class="w80">${obj.purchaseCount}</div></td>
                        <td class="tr"><div class="w80">${obj.price}</div></td>
                        <td class="tr"><div class="w80">${obj.budget}</div></td>
                        <td class=""><div class="w80">${obj.deliverDate}</div></td>
                        <td class="">
                         <div class="w100">
                          <c:forEach items="${kind}" var="kind">
                            <c:if test="${kind.id == obj.purchaseType}">
                              ${kind.name}
                            </c:if>
                          </c:forEach>
                         </div>
                        </td>
                        <td class=""><div class="w80">${obj.supplier}</div></td>
                        <td class="tc"><div class="w80">${obj.isFreeTax}</div></td>
                        <td class=""><div class="w80">${obj.goodsUse}</div></td>
                        <td class=""><div class="w80">${obj.useUnit}</div></td>
                        <td class=""><div class="w160">${obj.memo}</div></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:if>
          </ul>
        </div>
        <div class="col-md-12 tc">
          <button class="btn" onclick="add()" type="button">下一步</button>
          <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
        </div>
      </sf:form>
    </div>
  </body>

</html>