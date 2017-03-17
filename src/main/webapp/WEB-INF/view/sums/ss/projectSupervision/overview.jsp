<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
        var index = 0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");
        $("#package").addClass("spread");

      });

      function ycDiv(obj, index) {
        if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
          $(obj).removeClass("shrink");
          $(obj).addClass("spread");
        } else {
          if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
            $(obj).removeClass("spread");
            $(obj).addClass("shrink");
          }
        }

        var divObj = new Array();
        divObj = $(".p0" + index);
        for(var i = 0; i < divObj.length; i++) {
          if($(divObj[i]).hasClass("p0" + index) && $(divObj[i]).hasClass("hide")) {
            $(divObj[i]).removeClass("hide");
          } else {
            if($(divObj[i]).hasClass("p0" + index)) {
              $(divObj[i]).addClass("hide");
            };
          };
        };
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
      <div>
        <h2 class="count_flow"><i>1</i>项目基本信息</h2>
        <ul class="ul_list">
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="25%" class="info">项目名称：</td>
                <td width="25%">${project.name}</td>
                <td width="25%" class="info">项目编号：</td>
                <td width="25%">${project.projectNumber}</td>
              </tr>
              <tr>
                <td width="25%" class="info">计划名称：</td>
                <td width="25%">${name}</td>
                <td width="25%" class="info">计划编号：</td>
                <td width="25%">${number}</td>
              </tr>
              <tr>
                <td width="25%" class="info">需求部门：</td>
                <td width="25%"></td>
                <td width="25%" class="info">采购管理部门：</td>
                <td width="25%">${org}</td>
              </tr>
              <tr>
                <td width="25%" class="info">项目状态：</td>
                <td width="25%">${project.status}</td>
                <td width="25%" class="info">创建人：</td>
                <td width="25%">${project.appointMan}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%">
                  <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                </td>
                <td width="25%" class="info"></td>
                <td width="25%"></td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>2</i>执行进度</h2>
        <c:forEach items="${packages}" var="list" varStatus="vs">
          <c:set value="${vs.index}" var="index"></c:set>
          <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">
            <span class="f14 blue">${packages[index].name }:执行进度${aa},项目${bb}启动</span>
          </h2>
          <div class="p0${index} hide">
            <div class="col-md-12 col-sm-12 col-xs-12 p0">
              <ul class="flow_step">
                <li class="active">
                  <a aria-expanded="true" href="#tab-1" data-toggle="tab">计划下达时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-2" data-toggle="tab">项目启动时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-3" data-toggle="tab">确认供应商时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-4" data-toggle="tab">方案报批时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-5" data-toggle="tab">抽取评委时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-6" data-toggle="tab">标书发售起始时间</a>
                  <i></i>
                </li>
                <li>
                  <a aria-expanded="false" href="#tab-7" data-toggle="tab">开标唱标时间</a>
                </li>
              </ul>
            </div>
            <ul class="ul_list">
              <table class="table table-bordered table-condensed table-hover table-striped">
                <thead>
                  <tr class="info">
                    <th class="w50">序号</th>
                    <th>计划名称</th>
                    <th>计划编号</th>
                    <th>预算总金额</th>
                    <th>汇总时间</th>
                  </tr>
                </thead>
                <tbody id="tbody_id">
                  <c:forEach items="${list.collectPlan}" var="obj" varStatus="v">
                    <tr class="pointer">
                      <td class="tc w50">${(v.index+1)}</td>
                      <td class="tl pl20">${obj.fileName}</td>
                      <td class="tl pl20">${obj.planNo}</td>
                      <td class="tl pl20">${obj.budget}</td>
                      <td class="tl pl20">
                        <fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </ul>
          </div>
        </c:forEach>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>3</i>项目实施明细</h2>
        <ul class="ul_list">
          <p align="center" class="f22 fw">需求编报阶段</p>
          <p align="center" class="f22 fw">任务下达阶段</p>
          <p align="center" class="f22 fw">项目筹备阶段</p>
          <p align="center" class="f22 fw">项目实施阶段</p>
          <p align="center" class="f22 fw">合同签订阶段</p>
          <p align="center" class="f22 fw">质检验收阶段</p>
          <p align="center" class="f22 fw">单一来源审价阶段</p>
        </ul>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>