<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
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
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
	    <!-- <div class="headline-v2">
	      <h2>任务汇总</h2>
	    </div> -->
	      <c:if test="${listRequired != null}">
	      <div>
          <h2 class="count_flow"><i>1</i>采购需求</h2>
          <ul class="ul_list">
            <c:forEach items="${listRequired}" var="obj">
	            <table class="table table-bordered mt10">
		            <tbody>
		              <tr>
		                <td width="25%" class="info">计划名称：</td>
		                <td width="25%">${obj.planName}</td>
		                <td width="25%" class="info">填制单位：</td>
		                <td width="25%">${obj.department}</td>
		              </tr>
		              <tr>
		                <td width="25%" class="info">预算金额：</td>
		                <td width="25%">${obj.budget}</td>
		                <td width="25%" class="info">计划状态：</td>
		                <td width="25%">
		                  <c:if test="${obj.status eq '1'}">未提交</c:if>
                      <c:if test="${obj.status eq '4'}">受理退回</c:if> 
                      <c:if test="${obj.status eq '2' || obj.status eq '3' || obj.status eq '5'}">已提交</c:if>
		                </td>
		              </tr>
		              <tr>
		                <td width="25%" class="info">编制时间：</td>
		                <td width="25%"><fmt:formatDate value='${obj.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
		                <td width="25%" class="info">创建人：</td>
		                <td width="25%">${obj.userId}</td>
		              </tr>
		              <%-- <tr>
		                <td width="25%" class="info">审批时间：</td>
		                <td width="25%"><fmt:formatDate value='${obj.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
		                <td width="25%" class="info">审核人：</td>
		                <td width="25%"></td>
		              </tr> --%>
		            </tbody>
		          </table>
            </c:forEach>
          </ul>
        </div>
        </c:if>
        <c:if test="${collectPlan != null}">
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>2</i>采购计划</h2>
          <ul class="ul_list">
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <td width="25%" class="info">计划名称：</td>
                    <td width="25%">${collectPlan.fileName}</td>
                    <td width="25%" class="info">计划编号：</td>
                    <td width="25%">${collectPlan.planNo}</td>
                  </tr>
                  <tr>
                    <td width="25%" class="info">预算金额：</td>
                    <td width="25%">${collectPlan.budget}</td>
                    <td width="25%" class="info">状态：</td>
                    <td width="25%">
                      <c:if test="${collectPlan.status eq '1'}">审核轮次设置</c:if>
                      <c:if test="${collectPlan.status eq '3'}">第一轮审核</c:if>
			                <c:if test="${collectPlan.status eq '4'}">第二轮审核人员设置</c:if>
			                <c:if test="${collectPlan.status eq '5'}">第二轮审核</c:if>
			                <c:if test="${collectPlan.status eq '6'}">第三轮审核人员设置</c:if>
			                <c:if test="${collectPlan.status eq '7'}">第三轮审核</c:if>
			                <c:if test="${collectPlan.status eq '8'}">审核结束</c:if>
			                <c:if test="${collectPlan.status eq '12'}">未下达</c:if>
			                <c:if test="${collectPlan.status eq '2'}">已下达</c:if>
                    </td>
                  </tr>
                  <tr>
                    <td width="25%" class="info">编制时间：</td>
                    <td width="25%"><fmt:formatDate value='${collectPlan.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    <td width="25%" class="info">创建人：</td>
                    <td width="25%">${collectPlan.userId}</td>
                  </tr>
                  <%-- <tr>
                    <td width="25%" class="info">审批时间：</td>
                    <td width="25%"><fmt:formatDate value='${obj.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    <td width="25%" class="info">审核人：</td>
                    <td width="25%"></td>
                  </tr> --%>
                </tbody>
              </table>
          </ul>
        </div>
        </c:if>
        <c:if test="${task != null}">
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>3</i>采购任务</h2>
          <ul class="ul_list">
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <td width="25%" class="info">任务名称：</td>
                    <td width="25%">${task.name}</td>
                    <td width="25%" class="info">计划文号：</td>
                    <td width="25%">${task.documentNumber}</td>
                  </tr>
                  <tr>
                    <td width="25%" class="info">预算金额：</td>
                    <td width="25%">${task.passWord}</td>
                    <td width="25%" class="info">状态：</td>
                    <td width="25%">
                      <c:if test="${task.status eq '0'}">未受领</c:if>
                      <c:if test="${task.status eq '1'}">已受领</c:if>
                    </td>
                  </tr>
                  <tr>
                    <td width="25%" class="info">受领时间：</td>
                    <td width="25%"><fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    <td width="25%" class="info">创建人：</td>
                    <td width="25%">${task.createrId}</td>
                  </tr>
                </tbody>
              </table>
          </ul>
        </div>
        </c:if>
        <c:if test="${listProject != null}">
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>4</i>采购项目</h2>
          <ul class="ul_list">
            <c:forEach items="${listProject}" var="obj">
	            <table class="table table-bordered mt10">
	              <tbody>
	                <tr>
	                  <td width="25%" class="info">项目名称：</td>
	                  <td width="25%">${obj.name}</td>
	                  <td width="25%" class="info">创建时间：</td>
	                  <td width="25%"><fmt:formatDate value='${obj.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
	                </tr>
	                <tr>
	                  <td width="25%" class="info">招标单位：</td>
	                  <td width="25%">${obj.purchaseDepId}</td>
	                  <td width="25%" class="info">负责人：</td>
	                  <td width="25%">${obj.appointMan}</td>
	                </tr>
	                <tr>
	                  <td width="25%" class="info">联系地址：</td>
	                  <td width="25%">${obj.address}</td>
	                  <td width="25%" class="info">联系电话：</td>
	                  <td width="25%">${obj.ipone}</td>
	                </tr>
	                <tr>
	                  <td width="25%" class="info">状态：</td>
	                  <td width="25%">${obj.status}</td>
	                  <td width="25%" class="info"></td>
	                  <td width="25%"></td>
	                </tr>
	               </tbody>
	             </table>
             </c:forEach>
          </ul>
       </div>
       </c:if>
       <c:if test="${listContract != null}">
       <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>5</i>合同信息</h2>
          <ul class="ul_list">
            <c:forEach items="${listContract}" var="obj">
	            <table class="table table-bordered mt10">
	              <tbody>
	                <tr>
	                  <td width="25%" class="info">合同名称：</td>
	                  <td width="25%">${obj.name}</td>
	                  <td width="25%" class="info">合同状态：</td>
	                  <td width="25%">
	                    <c:if test="${obj.status==1}">草案</c:if>
	                    <c:if test="${obj.status==2}">正式</c:if>
	                  </td>
	                </tr>
	                <tr>
	                  <td width="25%" class="info">签订时间：</td>
	                  <td width="25%"></td>
	                  <td width="25%" class="info">合同编号：</td>
	                  <td width="25%">${obj.code}</td>
	                </tr>
	               </tbody>
	             </table>
             </c:forEach>
          </ul>
       </div>
       </c:if>
    </div>
    <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
      <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
    </div>
  </body>
</html>
