<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
  	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <!-- 文件上传 -->
		<link href="${pageContext.request.contextPath}/public/webupload/css/webuploader.css" media="screen" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" media="screen" rel="stylesheet" type="text/css">
		<!-- 文件显示 -->
		<link href="${pageContext.request.contextPath}/public/webupload/css/viewer.css" media="screen" rel="stylesheet" type="text/css">
		
		<!-- 文件上传 -->
		<script src="${pageContext.request.contextPath}/public/webupload/js/webuploader.js"></script>
		<script src="${pageContext.request.contextPath }/public/webuploadFT/upload.js"></script>
		<!-- 文件显示 -->
		
		<script src="${pageContext.request.contextPath}/public/webupload/js/viewer.js"></script>
		<script src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
    
    <script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>	
    <script type="text/javascript">
      $(function() {
	  		//获取查看或操作权限
	       	var isOperate = $('#isOperate', window.parent.document).val();
	       	if(isOperate == 0) {
	       		//只具有查看权限，隐藏操作按钮
				$(":button").each(function(){ 
					$(this).hide();
	            }); 
			}
	    })
      //表单验证
      var controldate;
      var deadline;
      function checkDate() {
    	  layer.confirm('您确定要保存吗?', {
				title: '提示',
				offset: ['30%', '40%'],
				shade: 0.01
				}, function(index) {
				layer.close(index);
        var flag = true;
        var id = $("#id").val();
        var flowDefineId = $("#flowDefineId").val();
        deadline = $("#deadline").val();
        controldate = $("#bidDate").val();
        var bidAddress = $("#bidAddress").val();
        var supplierNumber = $("#supplierNumber").val();
        supplierNumber = $.trim(supplierNumber);
        var purchaseType = $("#purchaseType").val();
        if(purchaseType == "JZXTP" || purchaseType == "YQZB" || purchaseType == "XJCG" || purchaseType == "GKZB") {
          if(supplierNumber < 3) {
            layer.tips("供应商人数不能小于3人", "#supplierNumber");
            flag = false;
          }

          if(!(/^[0-9]+$/.test(supplierNumber))) {
            layer.tips("请输入数字", "#supplierNumber");
            flag = false;
          }

        }

        if(purchaseType == "DYLY") {
          if(supplierNumber != 1) {
            layer.tips("供应商人数只能为1人", "#supplierNumber");
            flag = false;
          }
          if(!(/^[0-9]+$/.test(supplierNumber))) {
            layer.tips("请输入数字", "#supplierNumber");
            flag = false;
          }
        }
        if(bidAddress == '' || bidAddress == null) {
          layer.tips("请填写开标地点", "#bidAddress");
          flag = false;
        }

        if(deadline == "") {
          layer.tips("时间不能为空", "#deadline");
          flag = false;
        }else {
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
          //alert(CurrentDate); //当前日期
          var startDate = new Date(CurrentDate.replace("-", ",")).getTime();
          var endDate = new Date(deadline.replace("-", ",")).getTime();
          if(startDate > endDate) {
            layer.tips("选择日期不能小于当前日期!", "#deadline");
            flag = false;
          }

        }

        if(controldate == "") {
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
          //alert(CurrentDate); //当前日期
          var startDate = new Date(CurrentDate.replace("-", ",")).getTime();
          var endDate = new Date(controldate.replace("-", ",")).getTime();
          if(startDate > endDate) {
            layer.tips("选择日期不能小于当前日期!", "#bidDate");
            flag = false;
          }

        }
        if(flag == true) {
          layer.msg("修改成功", {
            shade: 0.01
          });
          $("#save_form_id").submit();
        }
				});
      }
     
     
      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }

      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
          }
        }
      }
				
				
      function ycDiv(obj, index) {
    	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
              $(obj).removeClass("shrink");
              $(obj).addClass("spread");
            } else {
              if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                $(obj).removeClass("spread");
                $(obj).addClass("shrink");
              }
            }
            
            var divObj = new Array();
            divObj = $(".p0" + index);
            for (var i =0; i < divObj.length; i++) {
                if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                  $(divObj[i]).removeClass("hide");
                } else {
                  if ($(divObj[i]).hasClass("p0"+index)) {
                    $(divObj[i]).addClass("hide");
                  };
                };
            };
      }
        function bidRegister(id) {
        var type = "1";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }
            
      function bidRecord(id) {
        var type = "2";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function validInspect(id) {
        var type = "3";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function host(id) {
        var type = "4";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function cashDeposit(id) {
        var type = "5";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function singleConstruction(id) {
        var type = "6";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function confidentiality(id) {
        var type = "7";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function cover(id) {
        var type = "8";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function biddingDocument(id) {
        var type = "9";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }
      
      function expertsSignIn(id) {
        var type = "10";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function bidReport(id) {
        var type = "11";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function bidNotice(id) {
        var type = "12";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function bidReports(id) {
        var type = "13";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }

      function bidReportss(id) {
        var type = "14";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }


       function issueRegistration(id) {
        var type = "15";
        window.location.href = "${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=" + id + "&type=" + type;
      }
      
      function getValue(){
    	  var date = $("#deadline").val();
    	  $("#bidDate").val(date);
      }
      
      $(function(){
    	  var id = "${project.id}";
    	  $.ajax({
					url: "${ pageContext.request.contextPath }/project/getUserForSelect.do?id="+id,
					contentType: "application/json;charset=UTF-8",
					dataType: "json", //返回格式为json
					type: "POST", //请求方式           
					success: function(users) {
						if(users) {
							$("#principal").html("<option></option>");
							$.each(users, function(i, user) {
								if(user.relName != null && user.relName != '') {
									$("#principal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
								}
							});
						}
						$("#principal").select2();
						$("#principal").select2("val", "${project.principal}");
					}
				});
      });
      
      function change(id) {
				$("#userId").val(id);
			}
			
			function view(id){
			 var projectId = $("#id").val();
			 layer.open({
         type : 2, //page层
         area : [ '800px', '500px' ],
         title : '',
         shade : 0.01, //遮罩透明度
         moveType : 1, //拖拽风格，0是默认，1是传统拖动
         shift : 1, //0-6的动画形式，-1不开启
         shadeClose : true,
         content : '${pageContext.request.contextPath}/project/viewIdss.html?id=' + id+'&projectId='+projectId,
       });
			}
			
			
			function downloads(){
			   var id = [];
			   $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
         });
         if(id.length > 0){
           var a = "2";
           download(id,a,null,null);
         }else{
            layer.msg("请选择！");
         }
			   
			}
			
			
			function deleted(){
			   var ids = [];
         $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
         });
         var a = "2";
         //removeFile(ids,a,null);
         if(ids.length > 0){
            $.ajax({
		          type:"post",
		          url: "${pageContext.request.contextPath}/file/deleteFile.html?id="+ids+"&key="+a,
		          async:true,
		          success:function(msg){
		            if (msg == "ok"){
		              window.location.reload();
		            }
		          }
		        });
         }else{
            layer.msg("请选择！");
         }
         
			}
			
			
			function views(id){
			   var projectId = "${project.id}";
			   var a = "2";
			   openViewDIv(projectId,id,a,null,null);
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
              <h2 onclick="ycDiv(this,'${1}')" class="count_flow spread hand">基本信息</h2>
              <div class="p0${1}">
                <table class="table table-bordered left_table">
                  <tbody>
                    <tr>
                      <td class="bggrey">项目编号:</td>
                      <td class="p0"><input name="projectNumber" class="m0" id="projectNumber" value="${project.projectNumber}" type="text" class="m0"/><input type="hidden" name="id" id="id" value="${project.id}" /></td>
                      <td class="bggrey">项目名称:</td>
                      <td class="p0"><input name="name" class="m0" id="name" value="${project.name}" type="text"/><input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">项目负责人:</td>
                      <td class="p0"><input type="hidden" name="userId" id="userId" value="${project.principal}"/><select id="principal" name="principal" class="col-md-12 col-sm-12 col-xs-12 p0" onchange="change(this.options[this.selectedIndex].value)"></select></td>
                      <td class="bggrey">负责人手机:</td>
                      <td class="p0"><input name="ipone" id="ipone" class="m0" value="${project.ipone}" type="text"/></td>
                    </tr>
                    <tr>
                      <td class="bggrey">采购机构名称:</td>
                      <td class="p0">
                        <c:if test="${project.purchaseDepId eq orgnization.id}">
                          <input name="sectorOfDemand" class="m0" id="sectorOfDemand" value="${orgnization.name}" type="text"/>
                        </c:if>
                      </td>
                      <td class="bggrey"><span class="red star_red">*</span>最少供应商数量:</td>
                      <td class="p0"><input name="supplierNumber" class="m0" id="supplierNumber" value="${project.supplierNumber}" type="text"/></td>
                    </tr>
                    <tr>

                      <td class="bggrey">采购方式:</td>
                      <td colspan="3">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == project.purchaseType}">
                            <input type="hidden" id="purchaseType" value="${kind.code}" type="text"/> ${kind.name}
                          </c:if>
                        </c:forEach>
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey"><span class="red star_red">*</span>投标截止时间:</td>
                      <td class="p0"><input value="<fmt:formatDate type='date' value='${project.deadline }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="deadline" id="deadline" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="getValue()" class="Wdate" /></td>
                      <td class="bggrey"><span class="red star_red">*</span>开标时间:</td>
                      <td class="p0"><input value="<fmt:formatDate type='date' value='${project.bidDate }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="bidDate" id="bidDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"></td>
                    </tr>
                    <tr>
                      <td class="bggrey"><span class="red star_red">*</span>开标地点:</td>
                      <td colspan="3" class="p0"><input name="bidAddress" id="bidAddress" value="${project.bidAddress}" type="text" class="m0"/></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <h2 onclick="ycDiv(this,'${2}')" class="count_flow spread hand">时间信息</h2>
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
                      <td><fmt:formatDate value='${auditDate}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                      <td class="bggrey">采购任务下达时间:</td>
                      <td>
                        <fmt:formatDate value='${task.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">采购任务受理时间:</td>
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
                      <td class="bggrey">招标公告审批时间:</td>
                      <td><fmt:formatDate value='${project.appTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    </tr>
                    <tr>
                       <td class="bggrey">项目结束时间:</td>
                      <td><fmt:formatDate value='${project.endTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                      <td class="bggrey">供应商报名时间:</td>
                      <td><fmt:formatDate value='${project.signUpTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">报名截止时间:</td>
                      <td><fmt:formatDate value='${project.applyDeanline}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                      <td class="bggrey">售后维护时间:</td>
                      <td><fmt:formatDate value='${project.maintenanceTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">合同签订时间:</td>
                      <td><fmt:formatDate value='${project.signingTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                      <td class="bggrey">验收时间:</td>
                      <td><fmt:formatDate value='${project.acceptanceTime}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
                    </tr>
                  </tbody>
                </table>
                <div class="col-md-12 tc mt20">
                  <button class="btn btn-windows git" type="button" onclick="checkDate();">保存</button>
                </div>
              </div>
            </form>
          </div>
          <div class="tab-pane fade " id="tab-2">
            <table class="table table-bordered table-condensed mt5">
              <c:forEach items="${packageList }" var="pack" varStatus="p">
                <div class="col-md-6 col-sm-6 col-xs-12 p0">
                  <span class="f16 b">包名：</span>
                  <span class="f14 blue">${pack.name }</span>
                </div>
                <input type="hidden" value="${pack.id }" />
                <table id="table" class="table table-bordered table-condensed table-hover table-striped left_table">
                  <thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info" width="15%">物资名称</th>
                      <th class="info" width="10%">规格型号</th>
                      <th class="info" width="10%">质量技术标准</th>
                      <th class="info" width="">计量<br>单位</th>
                      <th class="info" width="">采购<br>数量</th>
                      <th class="info" width="8%">交货期限</th>
                      <th class="info" width="15%">供应商名称</th>
                      <c:if test="${pack.isImport==1 }">
                        <th class="info">是否申请<br>办理免税</th>
                        <th class="info" width="8%" >物资用途<br>（进口）</th>
                        <th class="info" width="8%" >使用单位<br>（进口）</th>
                      </c:if>
                      <th class="info" width="10%">备注</th>
                    </tr>
                  </thead>
                  <c:forEach items="${pack.projectDetails}" var="obj" varStatus="vs">
                    <tr>
                      <td class="tc w50">${vs.index+1}</td>
                      <td class="tl"  width="15%"><a href="javascript:void(0)" onclick="view('${obj.requiredId}');">${obj.goodsName}</a></td>
                      <td class="tl"  width="10%">${obj.stand}</td>
                      <td class="tl"  width="10%">${obj.qualitStand}</td>
                      <td class="tc">${obj.item}</td>
                      <td class="tc">${obj.purchaseCount}</td>
                      <td class="tl" width="8%">${obj.deliverDate}</td>
                      <td class="tl" width="15%">${obj.supplier}</td>
                      <c:if test="${pack.isImport==1 }">
                        <td class="tc">${obj.isFreeTax}</td>
                        <td class="tl"  width="8%">${obj.goodsUse}</td>
                        <td class="tl"  width="8%">${obj.useUnit}</td>
                      </c:if>
                      <td class="tl"  width="10%">${obj.memo}</td>
                    </tr>
                  </c:forEach>
                </table>
              </c:forEach>
            </table>
          </div>
          <div class="tab-pane fade " id="tab-3">
            <button class="btn btn-windows input" type="button" onclick="bidRegister('${project.id}')">投标登记表</button>
            <button class="btn btn-windows input" type="button" onclick="bidRecord('${project.id}')">开标记录</button>
            <button class="btn btn-windows input" type="button" onclick="validInspect('${project.id}')">有效监标词</button>
            <button class="btn btn-windows input" type="button" onclick="host('${project.id}')">大会主持词</button>
            <button class="btn btn-windows input" type="button" onclick="cashDeposit('${project.id}')">保证金登记表</button>
            <button class="btn btn-windows input" type="button" onclick="singleConstruction('${project.id}')">送审单</button>
            <button class="btn btn-windows input" type="button" onclick="confidentiality('${project.id}')">保密审查单</button>
            <button class="btn btn-windows input mt10" type="button" onclick="cover('${project.id}')">公告封面</button>
            <button class="btn btn-windows input mt10" type="button" onclick="biddingDocument('${project.id}')">招标文件</button>
            <button class="btn btn-windows input mt10" type="button" onclick="expertsSignIn('${project.id}')">专家签到表</button>
            <button class="btn btn-windows input mt10" type="button" onclick="bidReport('${project.id}')">评标报告</button>
            <button class="btn btn-windows input mt10" type="button" onclick="bidNotice('${project.id}')">中标通知书</button>
            <button class="btn btn-windows input mt10" type="button" onclick="bidReports('${project.id}')">评标报告（综合）</button>
            <button class="btn btn-windows input mt10" type="button" onclick="bidReportss('${project.id}')">评标报告（最低）</button>
            <button class="btn btn-windows input mt10" type="button" onclick="issueRegistration('${project.id}')">劳务发放登记表</button>
          </div>
          <div class="tab-pane fade " id="tab-4">
            <div class="margin-bottom-0  categories">
              <form id="add_form" action="${pageContext.request.contextPath}/project/adddetail.html" method="post">
                <div>报批文件：</div>
                <u:show showId="upload_id" groups="upload123,upload_id" delete="false" businessId="${project.id}" sysKey="2" typeId="${dataIds}"/>
              </form>
            </div>
          </div>
          <div class="tab-pane fade active over_hideen" id="tab-5">
            <u:upload id="upload123" groups="upload123,upload_id" multiple="true" auto="true" businessId="${project.id}" typeId="${dataId}" sysKey="2" buttonName="上传附件"/>
            <%-- <u:show showId="upload123" groups="upload123,upload_id" businessId="${project.id}" sysKey="2" typeId="${dataId}" /> --%>
            &nbsp;
            <button class="btn btn-windows input" onclick="downloads();">下载</button>
            <button class="btn btn-windows delete" onclick="deleted();">删除</button>
            <table class="table table-bordered table-condensed mt5">
              <thead>
                    <tr>
                      <th class="w30">
				                <input type="checkbox" id="checkAll" onclick="selectAll()" />
				              </th>
                      <th class="info w50">序号</th>
                      <th class="info">附件名称</th>
                      <th class="info">操作人</th>
                      <th class="info">上传时间</th>
                    </tr>
                </thead>
            <c:forEach items="${listd}" var="data" varStatus="vs">
                <tr>
                      <td class="tc w30">
	                      <input type="checkbox" value="${data.id }" name="chkItem" onclick="check()">
	                    </td>
                      <td class="tc w50">${vs.index+1}</td>
                      <td class="tl" ><a href="javascript:void(0)" onclick="views('${data.typeId}');">${data.name}</a></td>
                      <td class="tl" onclick="views('${data.typeId}')">${relName.relName}</td>
                      <td class="tl">
                      <fmt:formatDate type='date' value='${data.createDate}' pattern=" yyyy-MM-dd HH:mm:ss " />
                      </td>
                    </tr>
            </c:forEach>
            </table>
            
          </div>
        </div>
      </div>
    </div>
  </body>

</html>