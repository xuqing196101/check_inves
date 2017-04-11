<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>登记质检报告</title>
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
            <a href="javascript:void(0)">供应商后台管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品库管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">产品录入</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 新增模板开始-->
    <div class="container container_box">
      <form action="" method="post" enctype="multipart/form-data">

        <div class="padding-top-10 clear">
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>选择类别：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input class="span5" name="unit" value='${pqinfo.unit}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">选择类别</span>
                <div class="cue">${ERR_category}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>品牌：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input class="span5" name="unit" value='${pqinfo.unit}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">输入品牌</span>
                <div class="cue">${ERR_category}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="place" value='${pqinfo.place}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">名称</span>
                <div class="cue">${ERR_place}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>价格：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="place" value='${pqinfo.place}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">价格</span>
                <div class="cue">${ERR_place}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>型号：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="inspectors" value='${pqinfo.inspectors}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">型号</span>
                <div class="cue">${ERR_inspectors}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>库存：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="inspectors" value='${pqinfo.inspectors}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">库存</span>
                <div class="cue">${ERR_inspectors}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>SKU：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
                <input name="inspectors" value='${pqinfo.inspectors}' type="text">
                <span class="add-on">i</span>
                <span class="input-tip">SKU</span>
                <div class="cue">${ERR_inspectors}</div>
              </div>
            </li>
           <%--  <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">上传产品主图 <span class="red">*注意：图片尺寸300*300(px);大小在100k以内</span></span>
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
		        <u:upload id="pro_picture" buttonName="上传产品主图"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
		                <u:show showId="major_picture" groups="b,c,d"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
		       <div class="cue" id="majoFileUploadErr">${fileUploadErr}</div>
		       </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">上传产品子图<span class="red">注意：图片尺寸300*300(px);大小在100k以内</span></span>
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
		        <u:upload id="sub__picture" buttonName="上传产品子图"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
		                <u:show showId="sub__picture" groups="b,c,d"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
		       <div class="cue" id="subFileUploadErr">${fileUploadErr}</div>
		       </div>
            </li> --%>
            <li class="col-md-12 col-sm-12 col-xs-12 mt10" id="picNone">
              <span class="fl">上传产品主图 <span class="red">*注意：图片尺寸300*300(px);大小在100k以内</span></span>
              <u:upload id="pro_picture" businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }" buttonName="上传产品主图" auto="true" exts="png,jpeg,jpg,bmp,git" />
              <u:show showId="major_picture" businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12 mt10" id="picNone">
              <span class="fl">上传产品子图<span class="red">注意：图片尺寸300*300(px);大小在100k以内</span></span>
              <u:upload id="sub__picture" businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }" buttonName="上传产品子图" auto="true" exts="png,jpeg,jpg,bmp,git" />
              <u:show showId="sub__picture" businessId="${pqinfoId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
            </li>
            <ul class="ul_list">
            	
            </ul>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>包装清单：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="h80 col-md-12 col-sm-12 col-xs-12 " name="detail" title="" placeholder="不超过800个字">${pqinfo.detail}</textarea>
              </div>
              <div class="clear red">${ERR_detail}</div>
            </li>
          </ul>

          <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
            <button class="btn btn-windows save" type="submit">保存</button>
            <button class="btn btn-windows save" type="submit">提交</button>
            <button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
          </div>
        </div>
      </form>
    </div>

  </body>

</html>