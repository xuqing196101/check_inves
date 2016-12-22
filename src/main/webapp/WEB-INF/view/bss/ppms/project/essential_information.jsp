<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      //表单验证
      var controldate;

      function checkDate() {
        debugger;
        var flag = true;
        var id = $("#id").val();
        var flowDefineId = $("#flowDefineId").val();
        controldate = $("#bidDate").val();
        var bidAddress = $("#bidAddress").val();
        var supplierNumber = $("#supplierNumber").val();
        supplierNumber = $.trim(supplierNumber);
        var purchaseType = $("#purchaseType").val();
        if(purchaseType == "JZXTP" || purchaseType == "YQZB" || purchaseType == "XJCG" || purchaseType == "GKZB") {
          if(supplierNumber < 3) {
            layer.tips("供应商人数不能小于3人", "#supplierNumber");
            flag = false;
          } else if(!(/^[0-9]+$/.test(supplierNumber))) {
            layer.tips("请输入数字", "#supplierNumber");
            flag = false;
          }
        } else if(purchaseType == "DYLY") {
          if(supplierNumber != 1) {
            layer.tips("供应商人数只能为1人", "#supplierNumber");
            flag = false;
          } else if(!(/^[0-9]+$/.test(supplierNumber))) {
            layer.tips("请输入数字", "#supplierNumber");
            flag = false;
          }
        } else if(bidAddress == "") {
          layer.tips("请填写开标地点", "#bidAddress");
          flag = false;
        } else if(controldate == "") {
          layer.tips("时间不能为空", "#bidDate");
          flag = false;
        } else {
          //验证时间不能小于当前时间
          var day = new Date();
          var Year = 0;
          var Month = 0;
          var Day = 0;
          var CurrentDate = "";
          //初始化时间
          Year = day.getFullYear();
          Month = day.getMonth() + 1;
          Day = day.getDate();
          CurrentDate += Year + "-";
          if(Month >= 10) {
            CurrentDate += Month + "-";
          } else {
            CurrentDate += "0" + Month + "-";
          }
          if(Day >= 10) {
            CurrentDate += Day;
          } else {
            CurrentDate += "0" + Day;
          }
          //alert(CurrentDate);//当前日期
          var startDate = new Date(CurrentDate.replace("-", ",")).getTime();
          var endDate = new Date(controldate.replace("-", ",")).getTime();
          if(startDate > endDate) {
            layer.tips("选择日期不能小于当前日期!", "#bidDate");
            flag = false;
          }
        }
        if(flag == true) {
          $("#save_form_id").submit();
        }
      }

      function ycDiv(obj, index) {
        if($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
          $(obj).removeClass("jbxx");
          $(obj).addClass("zhxx");
        } else {
          if($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
            $(obj).removeClass("zhxx");
            $(obj).addClass("jbxx");
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
            }
          }
        };
      }

      function purchaseEmbodiment(id) {
        var type = "1";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;

      }
      
      function biddingAnnouncement(id) {
        var type = "2";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function organizationalDocument(id) {
        var type = "3";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }

      function biddingNotice(id){
         var type = "4";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function reviewFile(id){
         var type = "5";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function ExOfEvalExperts(id){
         var type = "6";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function EvalExpertsInLetter(id){
         var type = "7";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidOpening(id){
         var type = "8";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function NotarizedInvitation(id){
         var type = "9";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidDocuments(id){
         var type = "10";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidBond(id){
         var type = "11";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidOpeningAttendance(id){
         var type = "12";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function preparatory(id){
         var type = "13";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidAssessmentRecord(id){
         var type = "14";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function bidReport(id){
         var type = "15";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function winningSupplier(id){
         var type = "16";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
      
      function procurement(id){
         var type = "17";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id="+id+"&type="+type;
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="tab-content">
      <div class="tab-v2">
        <ul class="nav nav-tabs bgwhite">
          <li class="active">
            <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">详细信息</a>
          </li>
          <li class="">
            <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">项目明细</a>
          </li>
          <li class="">
            <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">项目表单</a>
          </li>
          <li class="">
            <a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">下载报批文件</a>
          </li>
          <li class="">
            <a aria-expanded="false" href="#tab-5" data-toggle="tab" class="f18">附件上传</a>
          </li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade active in" id="tab-1">
            <form id="save_form_id" action="${pageContext.request.contextPath}/project/addProject.html" method="post">
              <h2 onclick="ycDiv(this,'${1}')" class="count_flow jbxx hand">基本信息</h2>
              <div class="p0${1}">
                <table class="table table-bordered">
                  <tbody>
                    <tr>
                      <td class="bggrey">项目编号:</td>
                      <td><input name="projectNumber" id="projectNumber" value="${project.projectNumber}" /><input type="hidden" name="id" id="id" value="${project.id}" /></td>
                      <td class="bggrey">项目名称:</td>
                      <td><input name="name" id="name" value="${project.name}" /><input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">项目经办人:</td>
                      <td><input name="principal" id="principal" value="${project.principal}" /></td>
                      <td class="bggrey">经办人手机:</td>
                      <td><input name="ipone" id="ipone" value="${project.ipone}" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">采购机构名称:</td>
                      <td>
                        <c:if test="${project.purchaseDepId eq orgnization.id}">
                          <input name="sectorOfDemand" id="sectorOfDemand" value="${orgnization.name}" />
                        </c:if>
                      </td>
                      <td class="bggrey">最少供应商人数:</td>
                      <td><input name="supplierNumber" id="supplierNumber" value="${project.supplierNumber}" /></td>
                    </tr>
                    <tr>

                      <td class="bggrey">采购方式:</td>
                      <td colspan="3">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == project.purchaseType}">
                            <input type="hidden" id="purchaseType" value="${kind.code}" /> ${kind.name}
                          </c:if>
                        </c:forEach>
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">投标截止时间:</td>
                      <td><input readonly="readonly" value="<fmt:formatDate type='date' value='${project.deadline }' dateStyle=" default " pattern="yyyy-MM-dd HH:mm:ss "/>" name="deadline" id="deadline" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" /></td>
                      <td class="bggrey">开标时间:</td>
                      <td><input readonly="readonly" value="<fmt:formatDate type='date' value='${project.bidDate }' dateStyle=" default " pattern="yyyy-MM-dd HH:mm:ss "/>" name="bidDate" id="bidDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"></td>
                    </tr>
                    <tr>
                      <td class="bggrey">开标地点:</td>
                      <td colspan="3"><input name="bidAddress" id="bidAddress" value="${project.bidAddress}" /></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <h2 onclick="ycDiv(this,'${2}')" class="count_flow jbxx hand">时间信息</h2>
              <div class="p0${2}">
                <table class="table table-bordered">
                  <tbody>
                    <tr>
                      <td class="bggrey">招标文件报批时间:</td>
                      <td>
                        <fmt:formatDate value='${project.approvalTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                      <td class="bggrey">招标文件批复时间:</td>
                      <td>
                        <fmt:formatDate value='${project.replyTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">需求计划提报时间:</td>
                      <td>${project.demandFromTime}</td>
                      <td class="bggrey">${task.name}采购任务下达时间:</td>
                      <td>
                        <fmt:formatDate value='${task.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">${task.name}采购任务受理时间:</td>
                      <td>
                        <fmt:formatDate value='${task.acceptTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                      <td class="bggrey">采购项目立项时间:</td>
                      <td>
                        <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">采购项目实施时间:</td>
                      <td>
                        <fmt:formatDate value='${project.startTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                      <td class="bggrey">招标公告发布时间:</td>
                      <td>${project.noticeNewsTime}</td>
                    </tr>
                    <tr>
                      <td class="bggrey">招标公告审批时间:</td>
                      <td>${project.appTime}</td>
                      <td class="bggrey">供应商报名时间:</td>
                      <td>${project.signUpTime}</td>
                    </tr>
                    <tr>
                      <td class="bggrey">报名截止时间:</td>
                      <td>${project.applyDeanline}</td>
                      <td class="bggrey">售后维护时间:</td>
                      <td>${project.maintenanceTime}</td>
                    </tr>
                    <tr>
                      <td class="bggrey">发送中标通知书时间:</td>
                      <td>${project.noticeTime}</td>
                      <td class="bggrey">项目结束时间:</td>
                      <td>${project.endTime}</td>
                    </tr>
                    <tr>
                      <td class="bggrey">合同签订时间:</td>
                      <td>${project.signingTime}</td>
                      <td class="bggrey">验收时间:</td>
                      <td>${project.acceptanceTime}</td>
                    </tr>
                  </tbody>
                </table>
                <div class="col-md-12 tc mt20">
                  <button class="btn btn-windows git" type="button" onclick="checkDate();">更新</button>
                </div>
              </div>
            </form>
          </div>
          <div class="tab-pane fade " id="tab-2">
            <table class="table table-bordered table-condensed mt5">
              <c:forEach items="${packageList }" var="pack" varStatus="p">
                <div class="col-md-6 col-sm-6 col-xs-12 p0">
                  <span class="f16 b">包名:</span>
                  <span class="f14 blue">${pack.name }</span>
                </div>
                <input type="hidden" value="${pack.id }" />
                <table class="table table-bordered table-condensed table-hover table-striped">
                  <thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">物资名称</th>
                      <th class="info">规格型号</th>
                      <th class="info">质量技术标准</th>
                      <th class="info">计量单位</th>
                      <th class="info">采购数量</th>
                      <th class="info">单价（元）</th>
                      <th class="info">交货期限</th>
                      <th class="info">供应商名称</th>
                      <c:if test="${pack.isImport==1 }">
                        <th class="info">是否申请办理免税</th>
                        <th class="info">物资用途（进口）</th>
                        <th class="info">使用单位（进口）</th>
                      </c:if>
                      <th class="info">备注</th>
                    </tr>
                  </thead>
                  <c:forEach items="${pack.projectDetails}" var="obj">
                    <tr>
                      <td class="tc w50">${obj.serialNumber}</td>
                      <td class="tc">${obj.goodsName}</td>
                      <td class="tc">${obj.stand}</td>
                      <td class="tc">${obj.qualitStand}</td>
                      <td class="tc">${obj.item}</td>
                      <td class="tc">${obj.purchaseCount}</td>
                      <td class="tc">${obj.price}</td>
                      <td class="tc">${obj.deliverDate}</td>
                      <td class="tc">${obj.supplier}</td>
                      <c:if test="${pack.isImport==1 }">
                        <td class="tc">${obj.isFreeTax}</td>
                        <td class="tc">${obj.goodsUse}</td>
                        <td class="tc">${obj.useUnit}</td>
                      </c:if>
                      <td class="tc">${obj.memo}</td>
                    </tr>
                  </c:forEach>
                </table>
              </c:forEach>
            </table>
          </div>
          <div class="tab-pane fade " id="tab-3">
             <h2 class="count_flow"><i>1</i>项目信息 </h2>
            <button class="btn btn-windows output" type="button" onclick="purchaseEmbodiment('${project.id}')">采购实施方案</button>
             <h2 class="count_flow"><i>2</i>拟制招标文件 </h2>
            <button class="btn btn-windows output" type="button" onclick="biddingAnnouncement('${project.id}')">招标文件</button>
             <h2 class="count_flow"><i>3</i>确认招标文件 </h2>
            <button class="btn btn-windows output" type="button" onclick="organizationalDocument('${project.id}')">组织文件</button>
             <h2 class="count_flow"><i>4</i>拟制招标公告 </h2>
            <button class="btn btn-windows output" type="button" onclick="biddingNotice('${project.id}')">招标公告</button>
             <h2 class="count_flow"><i>5</i>抽取评审专家 </h2>
            <button class="btn btn-windows output" type="button" onclick="reviewFile('${project.id}')">评审文件</button>
            <button class="btn btn-windows output" type="button" onclick="ExOfEvalExperts('${project.id}')">评审专家抽取记录</button>
            <button class="btn btn-windows output" type="button" onclick="EvalExpertsInLetter('${project.id}')">评审专家邀请函</button>
             <h2 class="count_flow"><i>6</i>开标唱标 </h2>
            <button class="btn btn-windows output" type="button" onclick="bidOpening('${project.id}')">开标记录</button>
            <button class="btn btn-windows output" type="button" onclick="NotarizedInvitation('${project.id}')">公正邀请函</button>
            <button class="btn btn-windows output" type="button" onclick="bidDocuments('${project.id}')">投标文件受领登记表</button>
            <button class="btn btn-windows output" type="button" onclick="bidBond('${project.id}')">投标保证金收取登记表</button>
            <button class="btn btn-windows output" type="button" onclick="bidOpeningAttendance('${project.id}')">投标人开标签到表</button>
             <h2 class="count_flow"><i>7</i>组织专家评审 </h2>
            <button class="btn btn-windows output" type="button" onclick="preparatory('${project.id}')">预备会议记录</button>
            <button class="btn btn-windows output" type="button" onclick="bidAssessmentRecord('${project.id}')">评标记录</button>
            <button class="btn btn-windows output" type="button" onclick="bidReport('${project.id}')">评标报告</button>
             <h2 class="count_flow"><i>8</i>拟制中标公告 </h2>
            <button class="btn btn-windows output" type="button" onclick="winningSupplier('${project.id}')">中标供应商审批书</button>
             <h2 class="count_flow"><i>9</i>确认中标供应商 </h2>
            <button class="btn btn-windows output" type="button" onclick="procurement('${project.id}')">采购合同审批表</button>
          </div>
          <div class="tab-pane fade " id="tab-4">
            <div class="margin-bottom-0  categories">
              <form id="add_form" action="${pageContext.request.contextPath}/project/adddetail.html" method="post">
                <u:show showId="upload_id" groups="upload123,upload_id" delete="false" businessId="${project.id}" sysKey="2" typeId="${dataIds}" />
              </form>
            </div>
          </div>
          <div class="tab-pane fade active" id="tab-5">
            <div>上传附件：</div>
            <u:upload id="upload123" groups="upload123,upload_id" auto="true" businessId="${project.id}" typeId="${dataId}" sysKey="2" />
            <u:show showId="upload123" groups="upload123,upload_id" businessId="${project.id}" sysKey="2" typeId="${dataId}" />
          </div>
        </div>
      </div>
    </div>
  </body>

</html>