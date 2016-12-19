<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
	<%@ include file="../../../../../common.jsp"%>
		<title>抽取列表</title>
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
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新projectNumber
							location.href = '${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&page=' + e.curr;
						}
					}

				});
				var typeclassId = "${typeclassId}";
				if(typeclassId != null && typeclassId != "") {
					$("#tenderTimeId").removeAttr("readonly");
				} else {
					$("#tenderTimeId").attr("rereadonly", "readonly");
				}

				$('#minute').bind('input propertychange', function() {
					var count = $(this).val();
					if(count > 60) {
						$("#minute").val("59");
					}
					if(count == 0) {
						$("#minute").val("");
					}

				});


				//获取包id
				var projectId = "${projectId}";
				if(projectId != null && projectId != '') {
					$("#projectName").attr("readonly", true);
					$("#projectNumber").attr("readonly", true);
					$("#packageName").attr("readonly", true);
				} else {
					$("#projectName").attr("readonly", false);
					$("#projectNumber").attr("readonly", false);
					$("#packageName").attr("readonly", false);
				}
			});

			   function ycDiv(obj, index){
			        if ($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
			          $(obj).removeClass("jbxx");
			          $(obj).addClass("zhxx");
			        } else {
			          if ($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
			            $(obj).removeClass("zhxx");
			            $(obj).addClass("jbxx");
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

			function add() {
				 var packageId=$("#packageId").val();
					 // $("#packageId").find("option:selected").val();
				$.ajax({
					cache: true,
					type: "POST",
					dataType: "json",
					url: '${pageContext.request.contextPath}/ExpExtract/validateAddExtraction.do',
					data: $('#form').serialize(), // 你的formid
					async: false,
					success: function(data) {
						$("#projectNameError").text("");
						$("#projectNumberError").text("");
						$("#packageNameError").text("");
						$("#dSupervise").text("");
						$("#tenderTimeError").text("");
						$("#responseTimeError").text("");

						var map = data;
						$("#projectNameError").text(map.projectNameError);
						$("#projectNumberError").text(map.projectNumberError);
						$("#packageNameError").text(map.packageNameError);
						$("#dSupervise").text(map.supervise);
						$("#tenderTimeError").text(map.tenderTimeError);
						$("#responseTimeError").text(map.responseTimeError);
						if(map.status != null && map.status != 0) {
							layer.alert("请全部抽取完之后在添加条件", {
								shade: 0.01
							});
						}
						if(map.sccuess == "SCCUESS") {
							  var projectId = map.projectId;
				              window.location.href = '${pageContext.request.contextPath}/ExpExtract/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
						}
					}
				});

			}

			function extract(id, btn) {
				layer.open({
					type: 2, //page层
					area: ['90%', '50%'],
					title: '抽取专家 项目名称： ${projectName}',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					shadeClose: true,
					offset: '30px',
					move: false,
					content: '${pageContext.request.contextPath}/ExpExtract/extractCondition.html?cId=' + id
				});
				$(btn).next().remove();
				$(btn).parent().parent().find("td:eq(2)").html("抽取中");

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
					content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
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

			function update(id) {
				location.href = '${pageContext.request.contextPath}/ExtCondition/showExtCondition.html?Id=' + id;
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
        
        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId=${projectId}",
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
        $("#packageId").val(rid);
      }
    </script>
	</head>

	<body>
	  <body>
   <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
  </div>
		<!--面包屑导航开始-->
		<c:if test="${typeclassId!=null && typeclassId !=''  }">
			<div class="margin-top-10 breadcrumbs ">
				<div class="container">
					<ul class="breadcrumb margin-left-0">
						<li>
							<a href="#"> 首页</a>
						</li>
						<li>
							<a href="#">支撑环境系统</a>
						</li>
						<li>
							<a href="#">专家管理</a>
						</li>
						<li>
							<a href="#">专家抽取</a>
						</li>
						<li class="active">
							<a href="#">专家抽取列表</a>
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
		</c:if>

		<!-- 项目戳开始 -->
		<div class="container container_box">
			<form id="form">
				<!-- 抽取地区 -->
				<input type="hidden" name="extAddress" id="extAddress" value="${extractionSites}">
				<!-- 监督人员 -->
				<input type="hidden" name="sids" id="sids" value="${userId}" />
				<!-- 打开类型 -->
				<input type="hidden" value="${typeclassId}" name="typeclassId" />
				<!-- 项目id  -->
				<input type="hidden" id="projectId" value="${projectId}" name="id">
				<div>
					<h2 class="count_flow"><i>1</i>必填项</h2>
					<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i>项目名称:</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="span5" id="projectName" name="name" value="${projectName}" type="text">
                <span class="add-on">i</span>
                <div class="cue" id="projectNameError"></div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>项目编号:</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="span5" id="projectNumber" name="projectNumber" value="${projectNumber}" type="text">
                <span class="add-on">i</span>
                <div class="cue" id="projectNumberError"></div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>采购方式:</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <select class="col-md-12 col-sm-12 col-xs-6 p0" name="purchaseType">
                  <c:forEach items="${findByMap}" var="map">
                    <option value="${map.id}">${map.name}</option>
                  </c:forEach>
                </select>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>开标时间:</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="col-md-12 col-sm-12 col-xs-6 p0"  id="tenderTimeId" name="tenderTime" value="<fmt:formatDate value='${bidDate}'
                                pattern='yyyy-MM-dd' />" maxlength="30" type="text">
                <div class="cue" id="tenderTimeError"></div>
              </div>
            </li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>监督人员:</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="span5" readonly id="supervises" title="${userName}" value="${userName}" onclick="supervise();" type="text">
								<span class="add-on">i</span>
								<div class="cue" id="dSupervise"></div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12 ">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>响应时间:</span>
							<div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
								<input class="col-md-5 col-sm-5 col-xs-5" name="hour" value="${hour}" maxlength="3" type="text">
								<span class="f14   fl">时</span>
								<input class="col-md-5 col-sm-5 col-xs-5" value="${minute}" id="minute" name="minute" maxlength="3" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
								<span class="f14   fl">分</span>
								<div class="cue" id="responseTimeError"></div>
							</div>
						</li>
						  <li class="col-md-12 col-sm-6 col-xs-12 ">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i>抽取地区:</span>
               <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="span5" id="extractionSites" name="extractionSites" value="${extractionSites}" type="text">
                <span class="add-on">i</span>
                <div class="cue" id="projectNumberError"></div>
              </div>
            </li>
					</ul>
				</div>
				
				 <div>
          <h2 class="count_flow "><i>2</i>
                    <div class="ww50 fl">抽取信息</div>
          </h2>
           <div align="right" class=" pl20 mb10 "  >
             <input class=" " readonly id="packageName" value="" onclick="showPackageType();"   type="text">
              <input  readonly id="packageId"      type="hidden">
<!--            <select class="w200 dnone" id="packageId"  > -->
<%--             <c:forEach items="${listResultExpert}" var="list"> --%>
<%--                 <option value="${list.id }" >${list.name }</option> --%>
<%--             </c:forEach> --%>
<!--           </select> -->
            <button class="btn mb10" 
                onclick="add();" type="button">抽取</button>
            <button class="btn dnone"
                onclick="record();" type="button">引用其他包</button>
        </div>
          <div class="ul_list">
        <div class="clear">
            <input id="priceStr" name="priceStr" type="hidden" />
            <input id="projectId" name="projectId" value="${projectId }" type="hidden" />
            <c:forEach items="${listResultExpert }" var="list" varStatus="vs">
              <c:set value="${vs.index}" var="index"></c:set>
              <div>
                <h2 onclick="ycDiv(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${listResultExpert[index].name }</span></h2>
              </div>
              <div class="p0${index}">
							  <table id="table" class="table table-bordered table-condensed">
							          <thead>
							            <tr>
							              <th class="info w50">序号</th>
							              <th class="info">专家姓名</th>
							              <th class="info">联系电话</th>
							              <th class="info">工作单位名称</th>
							              <th class="info">专家技术职称</th>
							            </tr>
							          </thead>
							          <tbody id="tbody">
							          <c:choose>
							           <c:when test="${typeId == 1}">
							            <c:forEach items="${list.listExperts}" var="listyes"
                            varStatus="vs">
                            <tr class='cursor '>
                              <td class='tc'>${vs.index+1}</td>
                              <td class='tc'>${listyes.relName}</td>
                              <td class='tc'>${listyes.mobile}</td>
                              <td class='tc'>${listyes.workUnit}</td>
                              <td class='tc'>${listyes.professTechTitles}</td>
                            </tr>
                          </c:forEach>
							           </c:when>
							           <c:otherwise>
							             <c:forEach items="${list.listExperts}" var="listyes"    varStatus="vs">
								           <tr class='cursor '>
	                              <td class='tc'>${vs.index+1}</td>
	                              <td class='tc'>******</td>
	                              <td class='tc'>******</td>
	                              <td class='tc'>******</td>
	                              <td class='tc'>******</td>
	                            </tr>
                          </c:forEach>
							           </c:otherwise>
							          </c:choose>
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