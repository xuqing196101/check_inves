<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>

  <head>
    <%@ include file="../../../common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>查看质检信息</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />

    <script type="text/javascript">
      function selectByCode() {
        var code = $("#contractCode").val();
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "${pageContext.request.contextPath}/purchaseContract/selectByCode.do?code=" + code,
          success: function(json) {
            if(json.code == "ErrCode") {
              $("#contractId").val(json.id);
              $("#contractName").val(json.name);
              $("#projectType").val(json.purchaseType);
              $("#procurementId").val(json.supplier.creditCode);
              $("#supplierName").val(json.supplier.supplierName);
              $("#contractCodeErr").text("合同编号不存在");
            } else {
              $("#contractId").val(json.id);
              $("#contractName").val(json.name);
              $("#projectType").val(json.purchaseType);
              $("#procurementId").val(json.supplier.creditCode);
              $("#supplierName").val(json.supplier.supplierName);
              $("#contractCodeErr").text("");
            }
          }
        });
      }
      $(function() {
        $("#type").val("${pqinfo.type}");
        $("#conclusion").val("${pqinfo.conclusion}");
      });

      function goback() {
        window.location.href = "${pageContext.request.contextPath}/pqinfo/getAll.html";

      }
      
      
      function view(){
        var code = $("#contractCode").val();
        var type = "1";
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "${pageContext.request.contextPath}/purchaseContract/viewAfter.html?code=" + code,
          success: function(result) {
            if(result == 0) {
              layer.msg("此合同没录入售后服务信息！");
            }else if(result == 2) {
              layer.msg("请输入合同编号！");
            }else{
              window.location.href = "${pageContext.request.contextPath}/after_sale_ser/list.html?code=" + code + "&type=" + type;
            }
          }
        });
      }
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品质量管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">修改质检报告</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 修改订列表开始-->
    <div class="container container_box">
      <form action="${pageContext.request.contextPath}/pqinfo/update.html" method="post" enctype="multipart/form-data">
        <div>
          <h2 class="count_flow"><i>1</i>合同基本信息</h2>
          <ul class="ul_list">
            <input type="hidden" class="id" name="id" value='${pqinfo.id}'>
            <input type="hidden" id="contractId" name="contract.id" value='${pqinfo.contract.id}'>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>合同编号：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="span5 contractCode" id="contractCode" name="contract.code" onblur="selectByCode()" type="text" value="${pqinfo.contract.code }">
                <span class="add-on">i</span>
                <span class="input-tip">填写合同编号合同信息自动生成</span>
                <div class="cue" id="contractCodeErr">${ERR_contract_code }</div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合同名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="span5 projectType" type="text" id="contractName" name="contract.name" readonly="readonly" value="${pqinfo.contract.name }">
                <span class="add-on">i</span>
                <span class="input-tip">填写合同编号合同信息自动生成</span>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">项目类别：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="span5 projectType" id="projectType" type="text" name="projectType" readonly="readonly" value='${pqinfo.projectType}'>
                <span class="add-on">i</span>
                <span class="input-tip">填写合同编号合同信息自动生成</span>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商组织机构代码：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="span5 procurementId" id="procurementId" name="procurementId" value='${pqinfo.contract.supplier.creditCode}' type="text" readonly="readonly">
                <span class="add-on">i</span>
                <span class="input-tip">填写合同编号合同信息自动生成</span>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="span5 supplier_name" id="supplierName" name="supplier_name" value='${pqinfo.contract.supplier.supplierName}' type="text" readonly="readonly">
                <span class="add-on">i</span>
                <span class="input-tip">填写合同编号合同信息自动生成</span>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">售后服务信息详情</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <button class="btn" onclick="view();" type="button">查看</button>
              </div>
            </li>
          </ul>
        </div>

        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>2</i>质检登记</h2>
          <ul class="ul_list">

            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检单位：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input class="span5" name="unit" value='${pqinfo.unit}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">填写质检单位</span>
                <div class="cue">${ERR_unit}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检类型：</span>
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="type" name=type>
                  <option value="-请选择-">请选择</option>
                  <option value="首件检验">首件检验</option>
                  <option value="生产验收">生产验收</option>
                  <option value="出厂验收">出厂验收</option>
                  <option value="到货验收">到货验收</option>
                </select>
                <div class="cue">${ERR_type}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检地点：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="place" value='${pqinfo.place}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">填写质检地点</span>
                <div class="cue">${ERR_place}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检日期：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="Wdate" name="date" value="<fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd'/>" type="text">
                <div class="cue">${ERR_pqdate}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检人员：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="inspectors" value='${pqinfo.inspectors}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">填写质检人员</span>
                <div class="cue">${ERR_inspectors}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检情况：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input name="condition" value='${pqinfo.condition}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">填写质检情况</span>
                <div class="cue">${ERR_condition}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>质检结论：</span>
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="conclusion" name="conclusion">
                  <option value="-请选择-">请选择</option>
                  <option value="合格">合格</option>
                  <option value="不合格">不合格</option>
                </select>
                <div class="cue">${ERR_conclusion}</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>详细情况：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="h130 col-md-12 col-sm-12 col-xs-12 " name="detail" title="不超过800个字" placeholder="不超过800个字">${pqinfo.detail}</textarea>
              </div>
              <div class="clear red">${ERR_detail}</div>
            </li>

            <li class="col-md-12 col-sm-12 col-xs-12 mt10" id="picNone">
              <span class="fl">图片上传：</span>
              <div class="">
                <u:upload id="artice_up" businessId="${pqinfoID }" sysKey="${pqinfoKey}" typeId="${attachtypeId }" buttonName="上传质检报告图片" auto="true" exts="png,jpeg,jpg,bmp,git" />
                <u:show showId="artice_show" businessId="${pqinfoID }" sysKey="${pqinfoKey}" typeId="${attachtypeId }" />
              </div>
            </li>
          </ul>

          <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
            <button class="btn btn-windows save" type="submit">更新</button>
            <button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
          </div>
        </div>
      </form>
    </div>

  </body>

</html>