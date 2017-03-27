<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    
    <script type="text/javascript">
      function viewDemand() {
        layer.open({
          type: 1, //page层
          area: ['1300px', '300px'],
          title: '请上传更改附件',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: $("#file")
        });
      }

      function viewUpload(id) {
        var projectId = "${project.id}";
        var a = "2";
        openViewDIv(projectId, id, a, null, null);
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
                  <td width="25%" class="info">项目名称：</td>
                  <td width="25%">${project.name}</td>
                  <td width="25%" class="info">项目编号：</td>
                  <td width="25%">${project.projectNumber}</td>
                </tr>
                <tr>
                  <td width="25%" class="info">计划名称：</td>
                  <td width="25%">${collectPlan.fileName}</td>
                  <td width="25%" class="info">计划编号：</td>
                  <td width="25%">${collectPlan.planNo}</td>
                </tr>
                <tr>
                  <td width="25%" class="info">需求部门：</td>
                  <td width="25%">${collectPlan.department}</td>
                  <td width="25%" class="info">采购管理部门：</td>
                  <td width="25%">${collectPlan.purchaseId}</td>
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
          <h2 class="count_flow"><i>2</i>流程进度</h2>
          <div class="container">
            <div class="col-md-12 col-xs-12 col-sm-12 flow_more">
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购需求编报</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn last_small">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购需求受理</p>
                    <p class="tip_time">
                      <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy-MM-dd  HH:mm:ss' />
                    </p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">预研任务下达</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购计划审核</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn  ">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购计划下达</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购任务受领</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>

              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购项目立项</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购文件编报</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购公告发布</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">供应商抽取</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购文件发售</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">评审专家抽取</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">开标</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购项目评审</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>
              
              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">中标公示发布</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">中标供应商确定</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购合同签订</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">采购合同履约</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
                <div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
              </div>

              <div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
                <div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
                  <a href="javascript:void(0);">
                    <p class="tip_main">质量校验验收</p>
                    <p class="tip_time">2016-08-08</p>
                  </a>
                </div>
                <div class="tip_down col-xs-offset-6"></div>
              </div>

            </div>

          </div>

        </div>
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>3</i>进度详情</h2>
          <ul class="ul_list">
            <c:set var="flag" value="1"/>
            <h2 class="count_flow"><i>${flag}</i>采购需求编报</h2>
            <c:set var="flag" value="${flag+1}"/>
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">采购需求名称</th>
                  <th class="info">需求部门</th>
                  <th class="info">编报人</th>
                  <th class="info">提报时间</th>
                </tr>
                <tr>
                  <td>${purchaseRequired.planName}</td>
                  <td>${purchaseRequired.department}</td>
                  <td>${purchaseRequired.userId}</td>
                  <td>
                    <fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
  
            <h2 class="count_flow"><i>${flag}</i>采购需求受理</h2>
            <c:set var="flag" value="${flag+1}"/>
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">受理结果</th>
                  <th class="info">采购管理部门</th>
                  <th class="info">受理人</th>
                  <th class="info">受理时间</th>
                </tr>
                <tr>
                  <td class="tc"><button class="btn" onclick="viewDemand();" type="button">查看</button></td>
                  <td>${management}</td>
                  <td>${auditPerson.userId}</td>
                  <td>
                    <fmt:formatDate value='${auditPerson.createDate}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>

            <h2 class="count_flow"><i>${flag}</i>采购需求受理</h2>
            <c:if test="${advancedProject != null}">
              <table class="table table-bordered mt10">
                <tbody>
                  <tr>
                    <th class="info">预研通知书名称</th>
                    <th class="info">采购管理部门</th>
                    <th class="info">下达人</th>
                    <th class="info">下达时间</th>
                  </tr>
                  <tr>
                    <td class="tc">
                      <u:show showId="upload_id" businessId="${advancedProjectId}" sysKey="2" delete="false" typeId="${adviceId}" />
                    </td>
                    <td>${task.orgId}</td>
                    <td>${task.createrId}</td>
                    <td>
                      <fmt:formatDate value='${task.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                    </td>
                  </tr>
                </tbody>
              </table>
            </c:if>

            <c:choose>
              <c:when test="${listAuditPerson != null}">
                <table class="table table-bordered mt10">
                  <thead>
                    <tr>
                      <th class="info">审核轮次</th>
                      <th class="info">审核人员</th>
                      <th class="info">审核意见</th>
                      <th class="info">审核时间</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${listAuditPerson}" var="obj" varStatus="vs">
                      <tr>
                        <td class="tc">第${(vs.index+1)}轮</td>
                        <td>${obj.name}</td>
                        <td><button class="btn" onclick="viewDemand();" type="button">查看</button> </td>
                        <td>
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

            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">采购计划名称</th>
                  <th class="info">计划文号</th>
                  <th class="info">采购管理部门</th>
                  <th class="info">下达人</th>
                  <th class="info">下达时间</th>
                </tr>
                <tr>
                  <td>${collectPlan.fileName}</td>
                  <td>${collectPlan.planNo}</td>
                  <td>${collectPlan.purchaseId}</td>
                  <td>${collectPlan.userId}</td>
                  <td>
                    <fmt:formatDate value='${collectPlan.updatedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>

            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">采购任务名称</th>
                  <th class="info">采购机构</th>
                  <th class="info">任务性质</th>
                  <th class="info">受领人</th>
                  <th class="info">受领时间</th>
                </tr>
                <tr>
                  <td>${task.name}</td>
                  <td>${task.purchaseId}</td>
                  <td>
                    <c:if test="${task.taskNature eq '0'}">正常</c:if>
                    <c:if test="${task.taskNature eq '1'}">预研</c:if>
                  </td>
                  <td>${task.userId}</td>
                  <td>
                    <fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>

            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <th class="info">采购项目名称</th>
                  <th class="info">立项审批文件</th>
                  <th class="info">立项部门</th>
                  <th class="info">项目性质</th>
                  <th class="info">立项人</th>
                  <th class="info">立项时间</th>
                </tr>
                <tr>
                  <td>${project.name}</td>
                  <td class="tc"><button class="btn" onclick="viewUpload('${uploadId}');" type="button">查看</button></td>
                  <td>${project.purchaseDepName}</td>
                  <td>
                    <c:if test="${status eq '0'}">正常</c:if>
                    <c:if test="${status eq '1'}">预研</c:if>
                  </td>
                  <td>${project.appointMan}</td>
                  <td>
                    <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
          </ul>
        </div>

      </div>

      <div id="file" class="dnone">
        <div class="col-md-12 col-sm-12 col-xs-12 p0" id="content">
          <table id="table" class="table table-bordered table-condensed table-hover">
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
                <th class="info w100">供应商名称</th>
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
                <td class="tl">
                  <div class="w80">${required.supplier }</div>
                </td>
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
    </body>

</html>