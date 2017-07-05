<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../../../../common.jsp" %>

    <title>装备（产品）技术资料概述</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>
<script type="text/javascript">
    function cancel() {
        var id = $("#conId").val();
        window.location.href = "${pageContext.request.contextPath}/offer/userSelectProductCheck.html?contractId=" + id;
    }

</script>
<body>

<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
            </li>
            <li>
                <a href="javascript:void(0);"> 保障作业</a>
            </li>
            <li>
                <a href="javascript:void(0);"> 单一来源审价</a>
            </li>
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
            </li>
            <li><a href="javascript:void(0)">产品复审</a></li>
            <li><a href="javascript:void(0)">装备（产品）技术资料概述</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<form action="${pageContext.request.contextPath}/offerProduct/userSaveCheck.html" method="post"
      enctype="multipart/form-data">

    <div class="container container_box">
        <h2 class="list_title">装备（产品）技术资料概述</h2>
        <input type="hidden" id="conId" value="${contractProduct.appraisalContract.id }" readonly>
        <input type="hidden" id="proId" name="proId" class="w230 mb0" value="${contractProduct.id }" readonly>
        <input type="hidden" id="id" name="id" class="w230 mb0" value="${productInfo.id }" readonly>
        <ul class="list-unstyled ul_list">
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品名称：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input type="text" id="name" name="name" class="col-md-8 " value="${contractProduct.name }"
                           readonly>
                    <div class="cue"></div>
                </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>设计单位：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input id="designDepartment" name="designDepartment" type="text"
                           class="col-md-8 m0 col-sm-12 col-xs-12" value="${productInfo.designDepartment }" readonly>
                    <div class="cue">${ERR_designDepartment}</div>
                </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>产品概述：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productOverview" name="productOverview"
                              title="不超过4000个字" placeholder="不超过4000个字"
                              readonly>${productInfo.productOverview }</textarea>
                    <div class="cue mt70">${ERR_productOverview}</div>
                </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div
                        class="red star_red">*</div>产品生产过程概述：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productProcess" name="productProcess"
                              title="不超过4000个字" placeholder="不超过4000个字"
                              readonly>${productInfo.productProcess }</textarea>
                    <div class="cue mt70">${ERR_productProcess}</div>
                </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div
                        class="red star_red">*</div>产品技术状况概述：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="productSkill" name="productSkill"
                              title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.productSkill }</textarea>
                    <div class="cue mt70">${ERR_productSkill}</div>
                </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>结论：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h100" id="conclusion" name="conclusion"
                              title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.conclusion }</textarea>
                    <div class="cue mt70">${ERR_conclusion}</div>
                </div>
            </li>
        </ul>

        <%-- <table class="table table-bordered">
             <tobody>
                  <tr>
                     <td width="10%" class="bggrey tr">产品名称：</td>
                     <td width="25%">
                         <input type="text" id="name" name="name" class="col-md-8 border0 m0" value="${contractProduct.name }" readonly>
                     </td>
                 </tr>
                 <tr>
                     <td width="10%" class="bggrey tr"><div class="red star_red">*</div>设计单位：</td>
                     <td width="25%">
                         <input id="designDepartment" name="designDepartment" type="text" class="col-md-8 m0 col-sm-12 col-xs-12" value="${productInfo.designDepartment }" >
                     </td>
                 </tr>
                 <tr>
                     <td width="10%" class="bggrey tr"><div class="red star_red">*</div>产品概述：</td>
                     <td width="25%">
                     <textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productOverview" name="productOverview" title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productOverview } </textarea>
                     </td>
                 </tr>
                 <tr>
                     <td width="10%" class="bggrey tr"> <div class="red star_red">*</div>产品生产过程概述：</td>
                     <td width="25%">
                         <textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productProcess" name="productProcess"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productProcess }</textarea>
                     </td>
                 </tr>
                 <tr>
                     <td width="10%" class="bggrey tr"><div class="red star_red">*</div>产品技术状况概述：</td>
                     <td width="25%">
                         <textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="productSkill" name="productSkill"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.productSkill }</textarea>
                     </td>
                 </tr>
                 <tr>
                     <td width="10%" class="bggrey tr"><div class="red star_red">*</div>结论：</td>
                     <td width="25%">
                         <textarea class="col-md-8 col-sm-12 col-xs-12 h100" id="conclusion" name="conclusion"  title="不超过4000个字" placeholder="不超过4000个字">${productInfo.conclusion }</textarea>
                     </td>
                 </tr>
             </tobody>
        </table> --%>
        <div class="mt20 tc w100p clear">
            <button class="btn" type="submit">下一步</button>
            <button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
        </div>
    </div>
</form>


</body>
</html>
