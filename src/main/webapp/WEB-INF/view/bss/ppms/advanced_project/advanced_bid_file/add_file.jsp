<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
        var pStatus = "${pStatus}";
        if(pStatus == 'ZBWJYTJ') {
          //采购部门意见
          $("#pcReason").removeAttr("readonly");
          //事业部门意见
          $("#causereason").removeAttr("readonly");
          //财务部门意见
          $("#financereason").removeAttr("readonly");
          //最终意见
          $("#finalreason").removeAttr("readonly");
        }
      });

      function OpenFile(fileId) {
        setTimeout(open_file(fileId), 5000);
      }

      function open_file(fileId) {
        var obj = document.getElementById("TANGER_OCX");
        obj.Menubar = true;
        obj.Caption = "( 双击可放大 ! )";
        if(fileId != '0') {
          obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/loadFile.html?fileId=" + fileId, true, false, 'word.document'); // 异步加载, 服务器文件路径
        } else {
          var filePath = "${filePath}";
          if(filePath != null && filePath != undefined && filePath != "") {
            obj.BeginOpenFromURL("${pageContext.request.contextPath}/Adopen_bidding/downloadFile.html?filePath=" + filePath, true, false, 'word.document'); // 异步加载, 服务器文件路径
          }
        }
      }

      function exportWord() {
        var obj = document.getElementById("TANGER_OCX");
        // 参数说明
        // 1.url  2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
        //obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
      }

      function queryVersion() {

        var obj = document.getElementById("TANGER_OCX");
        var v = obj.GetProductVerString();
        obj.ShowTipMessage("当前ntko版本", v);
      }

      function inputTemplete() {
        var obj = document.getElementById("TANGER_OCX");
      }

      function saveFile(flag) {
        var flowDefineId = $("#flowDefineId").val();
        var projectId = $("#projectId").val();
        var projectName = $("#projectName").val();
        var obj = document.getElementById("TANGER_OCX");

        //提交
        if(flag == "1") {
          //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
          obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
          alert("采购文件已提交");
          $("#handle").attr("class", "dnone");
          $("#audit_file_add").attr("class", "dnone");
          $("#audit_file_view").removeAttr("class", "dnone");
          $("#cgdiv").addClass("dnone");
        }

        //暂存
        if(flag == "0") {
          //参数说明
          //1.url 2.后台接收的文件的变量  3.可选参数(为空)    4.文件名   5.form表单的ID
          obj.SaveToURL("${pageContext.request.contextPath}/Adopen_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
          //obj.ShowTipMessage("提示","招标文件已上传至服务器");
          alert("采购文件已暂存");
        }

      }

      function updateAudit(status) {
        var formData = $("#MyFile").serialize();
        formData = decodeURIComponent(formData, true);
        if(status == 2) {
          if($("#pcReason").val() != null && $("#causereason").val() != null && $("#financereason").val() != null && $("#finalreason").val() != null) {
            ajax(formData, status);
          } else {
            alert("理由不能为空");
          }
        } else if(status == 3 || status == 4) {
          ajax(null, status);
        }
      }

      function closeFile() {
        var obj = document.getElementById("TANGER_OCX");
        obj.close();
      }

      function ajax(reason, status) {
        var projectId = $("#projectId").val();
        var flowDefineId = $("#flowDefineId").val();
        var process = "${process}";
        $.ajax({
          url: "${pageContext.request.contextPath}/AdAuditbidding/updateAuditStatus.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&status=" + status + "&" + encodeURI(encodeURI(reason)),
          dataType: 'json',
          success: function(result) {
            if(result == 'SUCCESS') {
              if(process != null && process == 1) {
                window.location.href = "${pageContext.request.contextPath}/AdAuditbidding/list.html";
              }
              $("#cgspan").addClass("dnone");
              $("#cgdiv").addClass("dnone");
            }
          },
          error: function(result) {
            layer.msg("失败", {
              offset: '222px'
            });
          }
        });
      }

      function jump(url) {
        $("#open_bidding_main").load(url);
      }

      function confirmOk(obj, id, flowDefineId) {
        layer.confirm('您已经确认了吗?', {
          title: '提示',
          offset: ['100px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          $.ajax({
            url: "${pageContext.request.contextPath}/Adopen_bidding/confirmOk.html?projectId=" + id + "&flowDefineId=" + flowDefineId,
            dataType: 'json',
            success: function(result) {
              layer.msg(result.msg, {
                offset: '222px'
              });
              $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
              $("#queren").remove();
            },
            error: function(result) {
              layer.msg("确认失败", {
                offset: '222px'
              });
            }
          });
        });
      }
    </script>

  </head>

  <body onload="OpenFile('${fileId}')">
    <c:if test="${process == 1 }">
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
              <a href="javascript:void(0)">采购项目管理</a>
            </li>
            <li class="active">
              <a href="javascript:void(0)">采购文件审核</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>
    </c:if>
    <div class="container">
      <c:if test="${process != 1 }">
        <div class="col-md-12 p0">
          <ul class="flow_step">
            <c:if test="${ope == 'add' }">
              <li>
                <a href="${pageContext.request.contextPath}/adFirstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
                <i></i>
              </li>

              <li>
                <a href="${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
                <i></i>
              </li>
              <li class="active">
                <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}">
                  03、采购文件
                </a>
                <i></i>
              </li>
              <li>
			         <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
			       </li>
            </c:if>
            <c:if test="${ope == 'view' }">
              <li>
                <a href="${pageContext.request.contextPath}/Adopen_bidding/firstAduitView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
                <i></i>
              </li>
              <li>
                <a href="${pageContext.request.contextPath}/adIntelligentScore/packageListView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
                <i></i>
              </li>
              <li class="active">
                <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFileView.html?id=${project.id}&flowDefineId=${flowDefineId}">
                  03、采购文件
                </a>
                <i></i>
              </li>
              <li>
               <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
             </li>
              <li>
                <c:if test="${project.confirmFile == 0 || project.confirmFile==null}">
                  <a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a>
                </c:if>
                <c:if test="${project.confirmFile == 1 }">
                  <a>05、已确认</a>
                </c:if>
              </li>
            </c:if>
          </ul>
        </div>
      </c:if>
      <!-- 按钮 -->
      <c:if test="${project.confirmFile != 1 && project.confirmFile != 3 && project.confirmFile != 4 && ope =='add' && isAdmin == 1 }">
        <div class="mt5 mb5 fr" id="handle">
          <input type="button" class="btn btn-windows save" onclick="saveFile('0')" value="暂存">
          <input type="button" class="btn btn-windows git" onclick="saveFile('1')" value="提交至采购管理部门"></input>
        </div>
      </c:if>
      <c:if test="${(project.confirmFile == 3 || project.confirmFile == 4) && (pStatus == 'ZBWJYTG' || pStatus=='ZBWJXGBB') &&  isAdmin == 1 }">
        <div class="mt5 mb5 fr" id="handle">
          <input type="button" class="btn btn-windows save" onclick="oncreate();" value="生成正式采购文件">
        </div>
      </c:if>
      <form id="MyFile" method="post" class="h800">
        <c:if test="${ (project.confirmFile != 1)    }">
          <div class="" id="audit_file_add">
            <span class="fl">上传审批文件</span>
            <div>
              <u:upload id="a" buttonName="上传彩色扫描件" exts="jpg,jpeg,gif,png,bmp" multiple="true" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
              <u:show showId="b" groups="b,c,d" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
            </div>
          </div>
          <div class="dnone" id="audit_file_view">
            <span class="fl">审批文件</span>
            <u:show showId="d" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
          </div>
        </c:if>

        <c:if test="${(project.confirmFile == 1 )   }">
          <div class="clear">
            <span class="fl">审批文件</span>
            <u:show showId="c" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
          </div>
        </c:if>
        <input type="hidden" id="ope" value="${ope}">
        <input type="hidden" id="confirmFileId" value="${project.confirmFile}">
        <input type="hidden" id="flowDefineId" value="${flowDefineId }">
        <input type="hidden" id="projectId" value="${project.id}">
        <input type="hidden" id="projectName" value="${project.name}">
        <script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
        <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0" id="cgdiv">
          <c:if test="${(project.confirmFile != 0 && project.confirmFile != 1 )  || ( exist == true )  }">
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">采购管理部门意见</span>
            <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" readonly="readonly" id="pcReason" maxlength="100" name="pcReason" title="不超过100个字">${reasons.pcReason}</textarea>
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">事业部门意见</span>
            <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" readonly="readonly" id="causereason" maxlength="100" name="causeReason" title="不超过100个字">${reasons.causeReason}</textarea>
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">财务部门意见</span>
            <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" readonly="readonly" id="financereason" maxlength="100" name="financeReason" title="不超过100个字">${reasons.financeReason}</textarea>
            <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5" id="cgspan">最终意见</span>
            <textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12 h80" readonly="readonly" id="finalreason" maxlength="100" name="finalReason" title="不超过100个字">${reasons.finalReason}</textarea>
          </c:if>
          <c:if test="${pStatus == 'ZBWJYTJ' && exist == true}">
            <div class="tc mt50">
              <input type="button" class="btn btn-windows check_pass " onclick="updateAudit('3')" value="审核通过"></input>
              <input type="button" class="btn btn-windows check_back " onclick="updateAudit('2')" value="退回重报 "></input>
              <input type="button" class="btn btn-windows edit " onclick="updateAudit('4')" value="修改报备 "></input>
              <input type="button" class="btn btn-windows back " onclick="javascript:history.go(-1);" value="返回 "></input>
            </div>
          </c:if>

        </div>

      </form>
    </div>
  </body>

</html>