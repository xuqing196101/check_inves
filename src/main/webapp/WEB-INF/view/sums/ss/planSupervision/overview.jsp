<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

    <script type="text/javascript">
      $(function() {
        $("#onmouse").addClass("btmfixs");
        /* var cc = $("#main-1").offset().top;
        var bb = $("#main-8").offset().top; */
        $(".flow_tips").each(function(i) {
          if(i == 5) {
            $(this).addClass("round_tips round_l last_r");
            $(this).children(":last").prev().removeClass("tip_line col-md-5 col-sm-3 col-xs-4");
            $(this).children(":last").addClass("col-sm-offset-1 col-md-offset-1  col-md-offset-0");
          }
          if(i == 6) {
            $(this).children(":last").removeClass("tip_line col-md-5 col-sm-3 col-xs-4");
            $(this).children(":last").prev().removeClass("tip_line col-md-5 col-sm-3 col-xs-4");
          }
          if(i == 11) {
            $(this).addClass("round_tips round_l last_r");
            $(this).children(":last").addClass("col-sm-offset-1 col-md-offset-1  col-md-offset-0");
          }
          
          if(i > 5 && i < 12) {
            $(this).addClass("last_r");
          }
        });
        var flow = $(".flow_tips").length;
        if(flow > 12 || flow < 6){
          $(".flow_tips").children(":last").parent().removeClass("last_r");
          $(".flow_tips").children(":last").prev().removeClass("tip_line col-md-5 col-sm-3 col-xs-4");
        }
        $(".tip_time").each(function() {
          var time = $(this).text();
          time = $.trim(time);
          if(time){
            $(this).parent().parent().parent().addClass("pre_btn");
          }
        });
        $('.pre_btn').last().addClass("current_btn");
        $('.pre_btn').last().removeClass("pre_btn");
        $(".flow_tips").children(":last").hide();
      });
      
      function viewDemand() {
        layer.open({
          type: 1, //page层
          area: ['1300px', '300px'],
          title: '受理结果',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: $("#file")
        });
      }

      function viewUpload(id) {
        var projectId = "${project.id}";
        var uploadFile = "${uploadFile}";
        if(uploadFile == 0){
          var a = "2";
          openViewDIv(projectId, id, a, null, null);
        }else{
          layer.msg("未上传附件");
        }
      }

      function audit(id, type) {
        layer.open({
          type: 2, //page层
          area: ['800px', '500px'],
          title: '查看审核意见',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=' + id + '&type=' + type
        });
      }

      /** 文件发售 **/
      function sell(id, type) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看投标记录',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/viewSell.html?packageId=' + id + '&type=' + type,
        });
      }

      /** 开标 **/
      function bid(id) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看开标',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/bidAnnouncement.html?packageId=' + id,
        });
      }
      
      function expertExtract(packageId, projectId) {
        location.href="${pageContext.request.contextPath }/planSupervision/showRecord.html?packageId="+packageId + "&projectId=" + projectId;
      }
      
      function supplierExtract(packageId, projectId) {
        location.href="${pageContext.request.contextPath }/planSupervision/showRecords.html?packageId="+packageId + "&projectId=" + projectId;
      }
      
      function viewArticle(id){
        window.open("${pageContext.request.contextPath}/planSupervision/viewArticle.html?id=" + id, "采购公告");
      }

      function openPrint(projectId, packageId) {
        window.open("${pageContext.request.contextPath}/packageExpert/openPrint.html?packageId=" + packageId + "&projectId=" + projectId, "打印检查汇总表");
      }

      function openPrints(projectId, packageId) {
        window.open("${pageContext.request.contextPath}/packageExpert/expertConsult.html?packageId=" + packageId + "&projectId=" + projectId + "&flag=1", "评审汇总表");
      }

      function report(id) {
        window.open("${pageContext.request.contextPath}/planSupervision/report.html?packageId=" + id, "专家评审报告");
      }

      function info(id) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看质检',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/pqinfo/view.html?id=' + id,
        });
      }

      function viewAuditPerson(id, type) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看审核意见',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/viewAuditPerson.html?id=' + id + '&type=' + type,
        });
      }

      function graded(id, packageId) {
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '查看供应商评分排序',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: '${pageContext.request.contextPath}/planSupervision/graded.html?id=' + id + '&packageId=' + packageId,
        });
      }

      function openFile() {
        window.open("${pageContext.request.contextPath}/planSupervision/bidFileView.html?id=${project.id}", "采购文件");
      }

      function openContract(id){
		    location.href="${pageContext.request.contextPath }/contractSupervision/filePage.html?id="+id;
		  }
      function bigImg(x) {
        $(x).removeClass("btmfixs");
        $(x).addClass("btmfix");
      }

      function normalImg(x) {
        $(x).removeClass("btmfix");
        $(x).addClass("btmfixs");
      }
    </script>


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
              <a href="javascript:void(0)">采购计划监督</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>

      <div class="container container_box">
        <div>
          <h2 class="count_flow"><i>1</i>项目基本信息</h2>
          <ul class="ul_list">
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <td width="10%" class="info">项目名称：</td>
                  <td width="25%">${project.name}</td>
                  <td width="10%" class="info">项目编号：</td>
                  <td width="25%">${project.projectNumber}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">采购计划名称：</td>
                  <td width="25%">${collectPlan.fileName}</td>
                  <td width="10%" class="info">计划文号：</td>
                  <td width="25%">${collectPlan.planNo}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">需求部门：</td>
                  <td width="25%">${required.department}</td>
                  <td width="10%" class="info">采购管理部门：</td>
                  <td width="25%">${collectPlan.purchaseId}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">采购机构：</td>
                  <td width="25%">${project.purchaseDepName}</td>
                  <td width="10%" class="info">项目状态：</td>
                  <td width="25%">${project.status}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">需求提报人：</td>
                  <td width="25%">${purchaseRequired.userId}</td>
                  <td width="10%" class="info">提报人电话：</td>
                  <td width="25%">${purchaseRequired.code}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">计划下达人：</td>
                  <td width="25%">${task.createrId}</td>
                  <td width="10%" class="info">下达人电话：</td>
                  <td width="25%">${task.materialsType}</td>
                </tr>
                <tr>
                  <td width="10%" class="info">项目负责人：</td>
                  <td width="25%">${project.principal}</td>
                  <td width="10%" class="info">负责人电话：</td>
                  <td width="25%">${project.ipone}</td>
                </tr>
              </tbody>
            </table>
          </ul>
        </div>
        <div class="padding-top-10 clear" id="clear">
          <h2 class="count_flow"><i>2</i>流程进度</h2>
          <ul class="ul_list">
          <div class="container">
            <div class="col-md-12 col-xs-12 col-sm-12 flow_more" id="main-1">
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-1">
                    <p class="tip_main">采购需求编报</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_small" id="main-2">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-2">
                    <p class="tip_main">采购需求受理</p>
                    <p class="tip_time">
                     <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <c:if test="${advancedProject != null}">
                <div class="flow_tips col-md-2 col-sm-2 col-xs-12 small_r" id="main-3">
                  <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                    <a href="#tab-3">
                      <p class="tip_main">预研任务下达</p>
                      <p class="tip_time">
                        <fmt:formatDate value='${tasks.giveTime}' pattern='yyyy-MM-dd' />
                      </p>
                    </a>
                  </div>
                  <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                  <div class="tip_down col-xs-offset-6"></div>
                </div>
              </c:if>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 small_r" id="main-4">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-4">
                    <p class="tip_main">采购计划审核</p>
                    <c:if test="${listAuditPerson ne null}">
                      <p class="tip_time">
                        <fmt:formatDate value='${listAuditPerson[0].createDate}' pattern='yyyy-MM-dd' />
                      </p>
                    </c:if>
                    <c:if test="${listAuditPerson eq null}">
                      <p class="tip_time"><fmt:formatDate value='${collectPlan.createdAt}' pattern='yyyy-MM-dd' /></p>
                    </c:if>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

                <c:if test="${collectPlan ne null}">
                <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-5">
                  <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                    <a href="#tab-5">
                      <p class="tip_main">采购计划下达</p>
                      <p class="tip_time">
                        <fmt:formatDate value='${collectPlan.updatedAt}' pattern='yyyy-MM-dd' />
                      </p>
                    </a>
                  </div>
                  <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                  <div class="tip_down col-xs-offset-6"></div>
                </div>
                </c:if>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12  round_tips" id="main-6">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-6">
                    <p class="tip_main">采购任务受领</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${task.acceptTime}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r" id="main-7">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-7">
                    <p class="tip_main">采购项目立项</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${project.createAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-8">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-8">
                    <p class="tip_main">采购文件编报</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${project.approvalTime}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-9">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-9">
                    <p class="tip_main">采购公告发布</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${articles.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <c:if test="${'GKZB' ne code.code && 'DYLY' ne code.code}">
                <c:if test="${extUserNames ne null}">
                <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r" id="main-10">
                  <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                    <a href="#tab-10">
                      <p class="tip_main">供应商抽取</p>
                      <p class="tip_time"><fmt:formatDate value='${extUserDate}' pattern='yyyy-MM-dd' /></p>
                    </a>
                  </div>
                  <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                  <div class="tip_down col-xs-offset-6"></div>
                </div>
                </c:if>
              </c:if>

              <c:if test="${'DYLY' ne code.code}">
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-11">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-11">
                    <p class="tip_main">采购文件发售</p>
                    <p class="tip_time">${begin}</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
              </c:if>
              
              <c:if test="${userNames ne null}">
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-12">
                    <p class="tip_main">评审专家抽取</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${userDate}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
              </c:if>
              
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-13">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-13">
                    <p class="tip_main">开标</p>
                    <p class="tip_time">
                      <c:if test="${operName ne null}">
                      <fmt:formatDate value='${project.bidDate}' pattern='yyyy-MM-dd' />
                      </c:if>
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-15">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-14">
                    <p class="tip_main">采购项目评审</p>
                    <p class="tip_time">
                      <c:choose>
                        <c:when test="${packages.qualificationTime ne null}">
                          <fmt:formatDate value='${packages.qualificationTime}' pattern='yyyy-MM-dd' />
                        </c:when>
                        <c:otherwise>
                          <fmt:formatDate value='${packages.techniqueTime}' pattern='yyyy-MM-dd' />
                        </c:otherwise>
                      </c:choose>
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-16">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-15">
                    <p class="tip_main">中标公示发布</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${articleList.createdAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 " id="main-17">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-16">
                    <p class="tip_main">预中标供应商确定</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${listCheckPass[0].confirmTime}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12" id="main-18">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-17">
                    <p class="tip_main">采购合同签订</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${purchaseContract.formalAt}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <!-- <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-18">
                    <p class="tip_main">采购合同履约</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
              </div> -->

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r" id="main-19">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="#tab-18">
                    <p class="tip_main">采购质检验收</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${PqInfo.date}' pattern='yyyy-MM-dd' />
                    </p>
                  </a>
                </div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
            </div>
          </div>
          </ul>
        </div>
        <c:set var="flag" value="1" />
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>3</i>进度详情</h2>
          <ul class="ul_list">
            <h2 class="list_son" id="tab-1"><i>${flag}</i>采购需求编报</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" width="25%">采购需求名称</th>
                  <th class="info" width="35%">需求部门</th>
                  <th class="info" width="20%">编报人</th>
                  <th class="info" width="20%">提报时间</th>
                </tr>
                <tr>
                  <td>${purchaseRequired.planName}</td>
                  <td>${purchaseRequired.department}</td>
                  <td class="tc">${purchaseRequired.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>

            <h2 class="list_son" id="tab-2"><i>${flag}</i>采购需求受理</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" width="25%">受理结果</th>
                  <th class="info">采购管理部门</th>
                  <th class="info" width="20%">受理人</th>
                  <th class="info" width="20%">受理时间</th>
                </tr>
                <tr>
                  <td class="tc"><button class="btn" onclick="viewDemand();" type="button">查看</button></td>
                  <td>${management}</td>
                  <td class="tc">${auditPerson.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>

            <c:if test="${advancedProject != null}">
              <h2 class="list_son" id="tab-3"><i>${flag}</i>预研任务下达</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info" width="25%">预研通知书名称</th>
                    <th class="info">采购管理部门</th>
                    <th class="info" width="20%">下达人</th>
                    <th class="info" width="20%">下达时间</th>
                  </tr>
                  <tr>
                    <td class="tc">
                      <u:show showId="upload_id" businessId="${advancedProject.id}" sysKey="2" delete="false" typeId="${adviceId}" />
                    </td>
                    <td>${tasks.orgName}</td>
                    <td class="tc">${tasks.createrId}</td>
                    <td class="tc">
                      <fmt:formatDate value='${tasks.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
            </c:if>

            <h2 class="list_son" id="tab-4"><i>${flag}</i>采购计划审核</h2>
            <c:set var="flag" value="${flag+1}" />
            <c:choose>
              <c:when test="${listAuditPerson != null}">
                <table class="table table-bordered mt10">
                  <thead>
                    <tr>
                      <th class="info" width="25%">审核轮次</th>
                      <th class="info" width="35%">审核人员</th>
                      <th class="info" width="20%">审核意见</th>
                      <th class="info" width="20%">审核时间</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${listAuditPerson}" var="obj" varStatus="vs">
                      <tr>
                        <td class="tc">第${(vs.index+1)}轮</td>
                        <td class="tc">${obj.name}</td>
                        <td class="tc"><button class="btn" onclick="viewAuditPerson('${detailId}','${(vs.index+1)}');" type="button">查看</button> </td>
                        <td class="tc">
                          <fmt:formatDate value='${obj.createDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </c:when>
              <c:otherwise>
                <p>本计划未审核，直接下达</p>
              </c:otherwise>
            </c:choose>

            <c:if test="${collectPlan.fileName != null}">
              <h2 class="list_son" id="tab-5"><i>${flag}</i>采购计划下达</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info" width="25%">采购计划名称</th>
                    <th class="info">计划文号</th>
                    <th class="info" width="20%">采购管理部门</th>
                    <th class="info" width="20%">下达人</th>
                    <th class="info" width="20%">下达时间</th>
                  </tr>
                  <tr>
                    <td>${collectPlan.fileName}</td>
                    <td>${collectPlan.planNo}</td>
                    <td>${collectPlan.purchaseId}</td>
                    <td class="tc">${collectPlan.userId}</td>
                    <td class="tc">
                      <fmt:formatDate value='${collectPlan.updatedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
            </c:if>

            <c:if test="${task ne null}">
            <h2 class="list_son" id="tab-6"><i>${flag}</i>采购任务受领</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" width="25%">采购任务名称</th>
                  <th class="info" width="20%">采购机构</th>
                  <th class="info" width="15%">任务性质</th>
                  <th class="info" width="20%">受领人</th>
                  <th class="info" width="20%">受领时间</th>
                </tr>
                <tr>
                  <td>${task.name}</td>
                  <td>${task.purchaseId}</td>
                  <td class="tc">
                    <c:if test="${task.taskNature eq '0'}">正常</c:if>
                    <c:if test="${task.taskNature eq '1'}">预研</c:if>
                  </td>
                  <td class="tc">${task.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
                <c:if test="${tasks ne null}">
                <tr>
                  <td>${tasks.name}</td>
                  <td>${tasks.purchaseId}</td>
                  <td class="tc">预研<c:if test="${YYYBYY eq '0'}">(已终止)</c:if><c:if test="${YYYBYY eq '1'}">(已引用)</c:if>
                  </td>
                  <td class="tc">${tasks.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${tasks.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
                </c:if>
              </tbody>
            </table>
            </c:if>

            <c:if test="${project ne null}">
            <h2 class="list_son" id="tab-7"><i>${flag}</i>采购项目立项</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" width="25%">采购项目名称</th>
                  <th class="info" width="10%">立项审批文件</th>
                  <th class="info" width="15%">立项部门</th>
                  <th class="info" width="10%">项目性质</th>
                  <th class="info" width="20%">立项人</th>
                  <th class="info" width="20%">立项时间</th>
                </tr>
                <tr>
                  <td>${project.name}</td>
                  <td class="tc"><button class="btn" onclick="viewUpload('${uploadId}');" type="button">查看</button></td>
                  <td>${project.purchaseDepName}</td>
                  <td class="tc">
                    <c:if test="${status eq '0'}">正常</c:if>
                    <c:if test="${status eq '1'}">预研</c:if>
                  </td>
                  <td class="tc">${project.appointMan}</td>
                  <td class="tc">
                    <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
                <c:if test="${advancedProject ne null}">
                <tr>
                  <td>${advancedProject.name}</td>
                  <td class="tc"><button class="btn" onclick="viewUpload('${uploadId}');" type="button">查看</button></td>
                  <td>${advancedProject.purchaseDepName}</td>
                  <td class="tc">预研<c:if test="${YYYBYY eq '0'}">(已终止)</c:if><c:if test="${YYYBYY eq '1'}">(已引用)</c:if></td>
                  <td class="tc">${advancedProject.appointMan}</td>
                  <td class="tc">
                    <fmt:formatDate value='${advancedProject.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
                </c:if>
              </tbody>
            </table>
            </c:if>

            <c:if test="${'DYLY' ne code.code}">
              <c:if test="${fileName ne null}">
              <h2 class="list_son" id="tab-8"><i>${flag}</i>采购文件编报</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info" width="25%">采购文件名称</th>
                    <th class="info" width="10%">编制人</th>
                    <th class="info" width="25%">提报时间</th>
                    <th class="info" width="20%">审核意见</th>
                    <th class="info" width="20%">意见批复时间</th>
                  </tr>
                  <tr>
                    <td>
                      <a href="javascript:void(0)" onclick="openFile()">${fileName}</a>
                    </td>
                    <td class="tc">${operatorName}</td>
                    <td class="tc">
                      <fmt:formatDate value='${project.approvalTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                    <td>
                      <a href="javascript:void(0)" onclick="audit('${project.id}','1')">采购管理部门、事业部门审核、财务部门</a>
                    </td>
                    <td class="tc">
                      <fmt:formatDate value='${project.replyTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
              </c:if>
            </c:if>

            <c:if test="${articles ne null}">
            <h2 class="list_son" id="tab-9"><i>${flag}</i>采购公告发布</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" >公告名称</th>
                  <th class="info" width="20%">编制人</th>
                  <th class="info" width="20%">编制时间</th>
                </tr>
                <tr>
                  <td><a href="#" onclick="viewArticle('${articles.id}')">${articles.name}</a></td>
                  <td class="tc">${articles.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${articles.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>

            <c:if test="${'GKZB' ne code.code && 'DYLY' ne code.code}">
              <c:if test="${extUserNames ne null}">
              <h2 class="list_son" id="tab-10"><i>${flag}</i>供应商抽取</h2>
              <c:set var="flag" value="${flag+1}" />
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info" width="25%">抽取记录</th>
                    <th class="info">抽取人</th>
                    <th class="info" width="20%">监督人</th>
                    <th class="info" width="20%">抽取时间</th>
                  </tr>
                  <tr>
                    <td class="tc"><button class="btn" onclick="supplierExtract('${packageId}','${project.id}')" type="button">查看</button></td>
                    <td class="tc">${extUserNames}</td>
                    <td class="tc">${extUserName}</td>
                    <td class="tc">
                      <fmt:formatDate value='${extUserDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
              </c:if>
            </c:if>

            <c:if test="${'DYLY' ne code.code}">
            <c:if test="${begin ne null}">
            <h2 class="list_son" id="tab-11"><i>${flag}</i>采购文件发售</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">文件发售记录</th>
                  <th class="info" width="20%">操作人</th>
                  <th class="info" width="20%">开始发售时间</th>
                </tr>
                <tr>
                  <td class="tc"><button class="btn" onclick="sell('${packageId}','1')" type="button">查看</button></td>
                  <td class="tc">${operatorNames}</td>
                  <td class="tc">
                    ${begin}
                    <c:if test="${end!=null}">
                      —${end}
                    </c:if>
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>
            </c:if>

            <c:if test="${userNames ne null}">
            <h2 class="list_son" id="tab-12"><i>${flag}</i>评审专家抽取</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info" width="25%">抽取记录</th>
                  <th class="info">抽取人</th>
                  <th class="info" width="20%">监督人</th>
                  <th class="info" width="20%">抽取时间</th>
                </tr>
                <tr>
                  <td class="tc"><button class="btn" onclick="expertExtract('${packageId}','${project.id}')" type="button">查看</button></td>
                  <td>${userNames}</td>
                  <td width="20%">${userName}</td>
                  <td class="tc">
                    <fmt:formatDate value='${userDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>

            <c:if test="${operName ne null}">
            <h2 class="list_son" id="tab-13"><i>${flag}</i>开标</h2>
            <c:set var="flag" value="${flag+1}" />
            <c:choose>
              <c:when test="${fes eq '1'}">
                <p>评审未结束，不可查看</p>
              </c:when>
              <c:otherwise>
                <table class="table table-bordered mt10">
		              <tbody>
		                <tr>
		                  <th class="info" width="25%">投标记录</th>
		                  <th class="info" width="35%">开标一览表</th>
		                  <th class="info" width="20%">开标人</th>
		                  <th class="info" width="20%">开标时间</th>
		                </tr>
		                <tr>
		                  <td class="tc"><button class="btn" onclick="sell('${packageId}','2')" type="button">查看</button></td>
		                  <td class="tc"><button class="btn" onclick="bid('${packageId}')" type="button">查看</button></td>
		                  <td class="tc">${operName}</td>
		                  <td class="tc">
		                    <fmt:formatDate value='${project.bidDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
		                  </td>
		                </tr>
		              </tbody>
		            </table>
              </c:otherwise>
            </c:choose>
            </c:if>

            <c:if test="${experts[0].relName ne null}">
            <h2 class="list_son" id="tab-14"><i>${flag}</i>采购项目评审</h2>
            <c:set var="flag" value="${flag+1}" />
            <c:choose>
              <c:when test="${fes eq '1'}">
                <p>评审未结束，不可查看</p>
              </c:when>
              <c:otherwise>
                <table class="table table-bordered mt10">
		              <tbody>
		                <tr>
		                  <th class="info" width="25%">文件名称</th>
		                  <th class="info" width="35%">查看评审专家打分表</th>
		                  <th class="info" width="20%">查看汇总表</th>
		                  <th class="info" width="20%">评审时间</th>
		                </tr>
		                <tr>
		                  <td>资格性符合性检查</td>
		                  <td>
		                    <c:forEach items="${experts}" var="obj" varStatus="vs">
		                      <c:set value="${vs.index}" var="index"></c:set>
		                      <a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}" target="view_window">${experts[index].relName}</a>
		                    </c:forEach>
		                  </td>
		                  <td class="tc"><button class="btn" onclick="openPrint('${project.id}','${packageId}')" type="button">查看</button></td>
		                  <td class="tc">
		                    <fmt:formatDate value='${packages.qualificationTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
		                  </td>
		                </tr>
		                <tr>
		                  <td>技术商务评分（审查）</td>
		                  <td>
		                    <c:forEach items="${experts}" var="obj" varStatus="vs">
		                      <c:set value="${vs.index}" var="index"></c:set>
		                      <a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${project.id}&packageId=${packageId}&expertId=${experts[index].id}&auditType=1" target="view_window">${experts[index].relName}</a>
		                    </c:forEach>
		                  </td>
		                  <td class="tc"><button class="btn" onclick="openPrints('${project.id}','${packageId}')" type="button">查看</button></td>
		                  <td class="tc">
		                    <fmt:formatDate value='${packages.techniqueTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
		                  </td>
		                </tr>
		                <c:if test="${DYLY != null}">
		                  <tr>
		                    <td>专家评审报告</td>
		                    <td>
		                      <c:forEach items="${expertIdList}" var="obj" varStatus="vs">
		                        <c:if test="${obj.isGroupLeader == 1}">组长:${obj.expertId}</c:if>
		                      </c:forEach>
		                    </td>
		                    <td class="tc"><button class="btn" onclick="report('${packageId}')" type="button">查看</button></td>
		                    <td class="tc">
		                      <fmt:formatDate value='${reviewTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
		                    </td>
		                  </tr>
		                </c:if>
		              </tbody>
		            </table>
              </c:otherwise>
              
            </c:choose>
            
            </c:if>

            <c:if test="${articleList ne null}">
            <h2 class="list_son" id="tab-15"><i>${flag}</i>中标公示发布</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">中标公示名称</th>
                  <th class="info" width="20%">编制人</th>
                  <th class="info" width="20%">编制时间</th>
                </tr>
                <tr>
                  <td><a href="#" onclick="viewArticle('${articleList.id}')">${articleList.name}</a></td>
                  <td class="tc">${articleList.userId}</td>
                  <td class="tc">
                    <fmt:formatDate value='${articleList.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>

            <c:if test="${listCheckPass ne null}">
            <h2 class="list_son" id="tab-16"><i>${flag}</i>预中标供应商确定</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">中标供应商名称</th>
                  <th class="info" width="25%">评分排序</th>
                  <th class="info" width="20%">操作人</th>
                  <th class="info" width="20%">确定时间</th>
                </tr>
                <c:forEach items="${listCheckPass}" var="obj" varStatus="vs">
                  <tr>
                    <td>${obj.supplierId}</td>
                    <td class="tc"><button class="btn" onclick="graded('${obj.supplier.id}','${packageId}')" type="button">查看</button></td>
                    <td class="tc">${operatorName1}</td>
                    <td class="tc">
                      <fmt:formatDate value='${obj.confirmTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            </c:if>

            <c:if test="${purchaseContract ne null}">
            <h2 class="list_son" id="tab-17"><i>${flag}</i>采购合同签订</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">合同名称</th>
                  <th class="info">甲方</th>
                  <th class="info">乙方</th>
                  <th class="info" width="20%">签订时间</th>
                </tr>
                <tr>
                  <td><a href="#" onclick="openContract('${purchaseContract.id}');">${purchaseContract.name}</a></td>
                  <td>${purchaseContract.purchaseDepName}</td>
                  <td>${purchaseContract.supplierDepName}</td>
                  <td class="tc">
                    <fmt:formatDate value='${purchaseContract.formalAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>

            <c:if test="${PqInfo ne null}">
            <h2 class="list_son" id="tab-18"><i>${flag}</i>采购质检验收</h2>
            <c:set var="flag" value="${flag+1}" />
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">验收记录</th>
                  <th class="info">质检专家</th>
                  <th class="info">质检单位</th>
                  <th class="info" width="20%">验收时间</th>
                </tr>
                <tr>
                  <td class="tc"><button class="btn" onclick="info('${PqInfo.id}'}" type="button">查看</button></td>
                  <td>${PqInfo.inspectors}</td>
                  <td>${PqInfo.unit}</td>
                  <td class="tc">
                    <fmt:formatDate value='${PqInfo.date}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
            </c:if>
          </ul>
        </div>
      </div>

      <div id="file" class="dnone">
        <div id="content" class="col-md-12 col-sm-12 col-xs-12 over_scroll mt20" id="content">
          <table id="table" class="table table-bordered table-condensed lockout">
            <thead>
              <tr class="space_nowrap">
                <th class="info w50">序号</th>
                <th class="info w80">需求部门</th>
                <th class="info w80">物资类别<br>及名称</th>
                <th class="info w80">规格型号</th>
                <th class="info w80">质量技术标准</br>（技术参数）</th>
                <th class="info w80">计量<br>单位</th>
                <th class="info w80">采购<br>数量</th>
                <th class="info w80">单价<br>（元）</th>
                <th class="info w80">预算金额</br>（万元）</th>
                <th class="info w80">交货期限</th>
                <th class="info w100">采购方式</br>建议</th>
                <th class="info w80">采购机构</th>
                <c:if test="${required.supplier ne null}">
                <th class="info w100">供应商名称</th>
                </c:if>
                <th class="info w80">是否申请</br>办理免税</th>
                <th class="info w160">备注</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="tc w50">
                  <div class="w50">${required.seq }</div>
                </td>
                <td class="tl">
                  <div class="w80">${required.department }</div>
                </td>
                <td class="tl">
                  <div class="w80">${required.goodsName }</div>
                </td>
                <td class="tl">
                  <div class="w80">${required.stand }</div>
                </td>
                <td class="tl">
                  <div class="w80"> ${required.qualitStand }</div>
                </td>
                <td class="tc">
                  <div class="w80">${required.item }</div>
                </td>
                <td class="tc">
                  <div class="w80">${required.purchaseCount }</div>
                </td>
                <td class="tr">
                  <div class="w80">${required.price }</div>
                </td>
                <td class="tr">
                  <div class="w80">${required.budget }</div>
                </td>
                <td>
                  <div class="w80">${required.deliverDate }</div>
                </td>
                <td class="p0">${required.purchaseType }</td>
                <td class="tc p0">${required.organization }</td>
                <c:if test="${required.supplier ne null}">
                <td class="tl">
                  <div class="w80">${required.supplier }</div>
                </td>
                </c:if>
                <td class="tc">
                  <div class="w80">${required.isFreeTax }</div>
                </td>
                <td class="tl">
                  <div class="w160">${required.memo }</div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div id="onmouse" onmouseover="bigImg(this)" onmouseout="normalImg(this)">
        <div class="mt5 mb5 tc">
          <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
        </div>
      </div>
    </body>

</html>