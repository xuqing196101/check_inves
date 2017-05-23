<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp"%>
    <title>任务管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

    <script type="text/javascript">
        /*分页  */
        $(function() {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${list.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${list.total}",
                startRow: "${list.startRow}",
                endRow: "${list.endRow}",
                groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
                curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

                    return "${list.pageNum}";
                }(),
                jump: function(e, first) { //触发分页后的回调
                    if(!first) { //一定要加此判断，否则初始时会无限刷新
                        //                      location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=${projectId}&page='+e.curr;
                    }
                }
            });

            var typeclassId = "${typeclassId}";

            if(typeclassId != null && typeclassId != "") {
                $(".star_red").each(function(){
                    for(var i = 2; i < 4; i++){
                        $("#red"+i).addClass("dnone");
                    }
                });
            } else {
                for(var i = 0; i < 4; i++){
                    $("#red"+i).addClass("dnone");
                }
            }


            //获取包id
            var projectId = "${projectId}";
            if(projectId != null && projectId != '') {
                $("#projectName").attr("readonly", true);
                $("#projectNumber").attr("readonly", true);
                $("#packageName").attr("readonly", true);
                $("#tenderTimeId").attr("disabled", true);
            } else {
                $("#projectName").attr("readonly", false);
                $("#projectNumber").attr("readonly", false);
                $("#packageName").attr("readonly", false);
                $("#tenderTimeId").attr("disabled", false);
            }
            var index = 0 ;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
            //对于采购机构人员进行判断
            var isCurment = '${isCurment}';
            if(isCurment == '1'){
                $('.isCurment_div').removeClass('hide');
                $('.isCurment_div').addClass('block');
            }else if(isCurment == '0'){
                $('.isCurment_div').removeClass('block');
                $('.isCurment_div').addClass('hide');
            }

        });


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
        $(function() {
            $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
            var index=0;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
        });

        function add(type) {

            var packageId=$("#packageId").val();
            var typeclassId = "${typeclassId}";
            $.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url: '${pageContext.request.contextPath}/SupplierExtracts/validateAddExtraction.do?type='+type,
                data: $('#form').serialize(), // 你的formid
                async: false,
                success: function(data) {
                    $("#projectNameError").text("");
                    $("#projectNumberError").text("");
                    $("#packageNameError").text("");
                    $("#dSupervise").text("");
                    $("#extractionSitesError").text("");
                    var map = data;
                    $("#projectNameError").text(map.projectNameError);
                    $("#projectNumberError").text(map.projectNumberError);
                    $("#packageNameError").text(map.packageNameError);
                    $("#dSupervise").text(map.supervise);
                    $("#extractionSitesError").text(map.extractionSitesError);
                    var projectId = map.projectId;
                    if(map.isSuccess=="false"){
                        layer.alert(map.msg, {shade: 0.01});
                        return false;
                    }
                    if(map.status != null && map.status != 0) {
                        layer.confirm('上次抽取未完成，是否继续上次抽取？', {
                            btn: ['确定','取消'], shade:0.01 //按钮
                        }, function(){
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }, function(){
                            layer.closeAll();
                        });
                    }
                    if(map.error == null && map.error != 'error'){
                        if(map.sccuess == "SCCUESS") {
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }else if(map.packageError != null && map.packageError != ''){
                            layer.alert("请选择包", {
                                shade: 0.01
                            });
                        }else if(typeclassId != null && typeclassId != ''){
                            $("#projectId").val(projectId);
                            $("#pId").val(projectId);
                            if(map.type != null && map.type == '1'){
                                var iframeWin;
                                layer.open({
                                    type: 2,
                                    title: "选择包",
                                    shadeClose: true,
                                    shade: 0.01,
                                    offset: '20px',
                                    move: false,
                                    area: ['50%', '50%'],
                                    content: '${pageContext.request.contextPath}/SupplierExtracts/showPackage.do?projectId='+projectId,
                                    success: function(layero, index) {
                                        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                                    },
                                    btn: ['保存', '关闭'],
                                    yes: function() {
                                        iframeWin.add();

                                    },
                                    btn2: function() {
                                        layer.closeAll();
                                    }
                                });

                            }
                        }
                    }
                }
            });

        }
        /**抽取页面*/
        function opens(){

            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + pachageId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
        }

        //选择监督人员
        function supervise() {
            //  iframe层
            var iframeWin;
            layer.open({
                type: 2,
                title: "选择监督人员",
                shadeClose: true,
                shade: 0.01,
                offset: '20px',
                move: false,
                area: ['90%', '50%'],
                content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do?projectId=${projectId}',
                success: function(layero, index) {
                    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
                btn: ['保存', '关闭'],
                yes: function() {
                    iframeWin.add();

                },
                btn2: function() {
                    layer.closeAll();
                }
            });
        }
    </script>

    <script type="text/javascript">
        function showPackageType() {
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {
                        "Y": "",
                        "N": ""
                    }
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    }
                },
                callback: {
                    beforeClick: beforeClick,
                    onCheck: onCheck
                }
            };
            var projectId =$("#projectId").val();
            $.ajax({
                type: "GET",
                async: false,
                url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId="+projectId,
                dataType: "json",
                success: function(zNodes) {
                    tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
                    tree.expandAll(true); //全部展开
                }
            });
            var cityObj = $("#packageName");
            var cityOffset = $("#packageName").offset();
            $("#packageContent").css({
                left: cityOffset.left + "px",
                top: cityOffset.top + cityObj.outerHeight() + "px"
            }).slideDown("fast");
            $("body").bind("mousedown", onBodyDownPackageType);
        }

        function onBodyDownPackageType(event) {
            if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
                hidePackageType();
            }
        }

        function hidePackageType() {
            $("#packageContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDownPackageType);

        }

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
                nodes = zTree.getCheckedNodes(true),
                v = "";
            var rid = "";
            for(var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].name + ",";
                rid += nodes[i].id + ",";
            }
            if(v.length > 0) v = v.substring(0, v.length - 1);
            if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
            var cityObj = $("#packageName");
            cityObj.attr("value", v);
            cityObj.attr("title", v);
            $("#packageId").attr("value",rid);

        }
    </script>

</head>

<body>
<div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
</div>
<!--面包屑导航开始-->
<c:if test="${typeclassId!=null && typeclassId !='' }">
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li>
                    <a href="javascript:void(0);"> 首页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">支撑环境系统</a>
                </li>
                <li>
                    <a href="javascript:void(0);">供应商管理</a>
                </li>
                <li>
                    <a href="javascript:void(0);">供应商抽取</a>
                </li>
                <li class="active">
                    <a href="javascript:void(0);">供应商抽取列表</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
</c:if>

<!-- 项目戳开始 -->
<div class="container_box col-md-12 col-sm-12 col-xs-12">
    <form id="form">
        <!-- 监督人员 -->
        <input type="hidden" name="sids" id="sids" value="${userId}" />
        <!-- 打开类型 -->
        <input type="hidden" value="${typeclassId}" name="typeclassId"/>
        <!-- 项目id  -->
        <input type="hidden" id="pId" value="${projectId}" name="id">
        <!-- 监督人员id  -->
        <input type="hidden" id="superviseId" value="${superviseId}" name="superviseId">
        <!-- 包id  -->
        <%--           <input type="hidden" id="packageId" value="${packageId}" name="packageId"> --%>
            <h2 class="count_flow"><i>1</i>项目信息</h2>
            <ul class="ul_list border0">
                <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red0">*</span> 项目名称:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="projectName" name="name"  value="${projectName}" type="text">
                        <span class="add-on">i</span>
                        <div class="cue" id="projectNameError"></div>
                    </div>
                </li>
                <li class="col-md-3 col-sm-4 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red1">*</span> 项目编号:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="projectNumber" name="projectNumber" value="${projectNumber}" type="text" >
                        <span class="add-on">i</span>
                        <div class="cue" id="projectNumberError"></div>
                    </div>
                </li>
                <li class="col-md-3 col-sm-4 col-xs-12">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red2">*</span>采购方式:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0">
                            <c:forEach items="${findByMap}" var="map">
                                <option value="${map.id}">${map.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li class="col-md-3 col-sm-4 col-xs-12 ">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red3">*</span> 开标日期:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="tenderTimeId" readonly="readonly"  name="bidDate" value="<fmt:formatDate value='${bidDate}'
                                pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                        <div class="cue" id="tenderTimeError"></div>
                    </div>
                </li>
                <li class="col-md-3 col-sm-4 col-xs-12 ">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 监督人员:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input readonly id="supervises" title="${userName}" value="${userName}" onclick="supervise();" type="text">
                        <span class="add-on">i</span>
                        <div class="cue" id="dSupervise"></div>
                    </div>
                </li>
                <li class="col-md-3 col-sm-4 col-xs-12 ">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="star_red">*</span> 抽取地区:</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                        <input id="extractionSites" name="extractionSites" value="${extractionSites}" type="text">
                        <span class="add-on">i</span>
                        <div class="cue" id="extractionSitesError"></div>
                    </div>
                </li>
            </ul>
        <div>
        <div class="count_flow ">
            <i>2</i>
            <div class="ww50 fl">抽取信息
            </div>
        </div>
        <div align="right" class=" pl20 mb10 hide isCurment_div" >
            <c:if test="${typeclassId!=null && typeclassId !='' }">
                <button class="btn mb10" onclick="add(1);" type="button">添加包</button>
            </c:if>
            <input class="input_group " readonly id="packageName" placeholder="请选择包" value="" onclick="showPackageType();" type="text">
            <input  readonly id="packageId" name="packageId"     type="hidden">
                <!--           <select class="w200" id="packageId" > -->
                <%--             <c:forEach items="${listResultSupplier}" var="list"> --%>
                <%--                 <option value="${list.id }" >${list.name }</option> --%>
                <%--             </c:forEach> --%>
                <!--           </select> -->
                <button class="btn mb10"
                        onclick="add(2);" type="button">抽取</button>
                <!--             <button class="btn" -->
                <!--                 onclick="record();" type="button">引用其他包</button> -->
            </div>
            <div class="ul_list">
                <div class="clear">
                    <input id="priceStr" name="priceStr" type="hidden" />
                    <input id="projectId" name="projectId" value="${projectId }" type="hidden" />
                    <c:forEach items="${listResultSupplier }" var="list" varStatus="vs">
                        <c:set value="${vs.index}" var="index"></c:set>
                        <div>
                            <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">包名:<span class="f14 blue">${listResultSupplier[index].name }</span></h2>
                        </div>
                        <div class="p0${index} hide">
                            <table  class="table table-bordered table-condensed mt5">
                                <thead>
                                <tr>
                                    <th class="info w50">序号</th>
                                    <th class="info">供应商名称</th>
                                    <th class="info">类型</th>
                                    <th class="info">联系人名称</th>
                                    <th class="info">联系人电话</th>
                                    <th class="info">联系人手机</th>
                                </tr>
                                </thead>
                                <tbody id="tbody">
                                <c:forEach items="${list.listExtRelate}" var="listyes" varStatus="vs">
                                    <tr class='cursor '>
                                        <td class='tc' >${vs.index+1}</td>
                                        <td class='tc' >${listyes.supplier.supplierName}</td>
                                        <td class='tc'>
                                            <c:forEach var="type" items="${supplierType}">
                                                <c:if test="${type.code eq listyes.reviewType }">
                                                    ${type.name}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td class='tc' >${listyes.supplier.contactName}</td>
                                        <c:choose>
                                            <c:when test="${listyes.supplier.contactTelephone==null || listyes.supplier.contactTelephone==''}">
                                                <td class='tc' >-</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class='tc' >${listyes.supplier.contactTelephone}</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td class='tc' >${listyes.supplier.contactMobile}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>