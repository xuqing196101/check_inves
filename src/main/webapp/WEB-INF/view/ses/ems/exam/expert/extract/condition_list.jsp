<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>抽取列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

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
				//对于采购机构人员进行判断
                var isCurment = '${isCurment}';
                if(isCurment == '1'){
                    $('.isCurment_div').removeClass('hide');
                    $('.isCurment_div').addClass('block');
                }else if(isCurment == '0'){
                    $('.isCurment_div').removeClass('block');
                    $('.isCurment_div').addClass('hide');
                }
		    })
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
                    $(".red").each(function(){
                        for(var i = 2; i < 4; i++){
                            $("#red"+i).addClass("dnone");
                        }
                    });
                } else {
                    for(var i = 0; i < 4; i++){
                        $("#red"+i).addClass("dnone");
                    }
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
					$("#tenderTimeId").attr("disabled", true);
				} else {
					$("#projectName").attr("disabled", false);
					$("#projectNumber").attr("disabled", false);
					$("#packageName").attr("readonly", false);
					$("#tenderTimeId").attr("disabled", false);
				}
				var index = 0 ;
				 var divObj = $(".p0" + index);
			        $(divObj).removeClass("hide");
			        $("#package").removeClass("shrink");        
			        $("#package").addClass("spread");
			        
				
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

			function add(type) {
				 var packageId=$("#packageId").val();
					 // $("#packageId").find("option:selected").val();
				     var typeclassId = "${typeclassId}";
				$.ajax({
					cache: true,
					type: "POST",
					dataType: "json",
					url: '${pageContext.request.contextPath}/ExpExtract/validateAddExtraction.do?type='+type,
					data: $('#form').serialize(), // 你的formid
					async: false,
					success: function(data) {
						$("#projectNameError").text("");
						$("#projectNumberError").text("");
						$("#packageNameError").text("");
						$("#dSupervise").text("");
						$("#tenderTimeError").text("");
						$("#responseTimeError").text("");
						$("#extractionSitesError").text("");
						var map = data;
						$("#projectNameError").text(map.projectNameError);
						$("#projectNumberError").text(map.projectNumberError);
						$("#packageNameError").text(map.packageNameError);
						$("#dSupervise").text(map.supervise);
						$("#tenderTimeError").text(map.tenderTimeError);
						$("#responseTimeError").text(map.responseTimeError);
						$("#extractionSitesError").text(map.extractionSitesError);
					    var projectId = map.projectId;
					    if(map.error){
                            if(map.packageError != null && map.packageError != ''){
                                layer.alert("请选择包", {
                                    shade: 0.01
                                });
                            }
					    }
					    
						if(map.status != null && map.status != 0) {
							layer.confirm('上次抽取未完成，是否继续上次抽取？', {
								  btn: ['确定','取消'], shade:0.01 //按钮
								}, function(){
									window.location.href = '${pageContext.request.contextPath}/ExpExtract/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+map.packageId;
								}, function(){
									layer.closeAll();
								});
						}
						if(map.error == null && map.error != 'error'){
						if(map.sccuess == "SCCUESS") {
								  window.location.href = '${pageContext.request.contextPath}/ExpExtract/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
						}else if(map.packageError != null && map.packageError != ''){
						            layer.alert("请选择包", {
			                    shade: 0.01
						                  });
						}else if(typeclassId != null && typeclassId != ''){
					             $("#projectId").val(projectId);
					             $("#pid").val(projectId);
// 					             alert($("#pid").val());
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
					title: "填写监督人员",
					shadeClose: true,
					shade: 0.01,
					offset: '20px',
					move: false,
					area: ['90%', '50%'],
					content: '${pageContext.request.contextPath}/ExpExtract/showSupervise.do?projectId=${projectId}',
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
				<div class="container container_box">
					<ul class="breadcrumb margin-left-0">
						<li>
                            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
						</li>
						<li>
							<a href="javascript:void(0);">支撑环境系统</a>
						</li>
						<li>
							<a href="javascript:void(0);">专家管理</a>
						</li>
						<li>
							<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/ExpExtract/projectList.html?typeclassId=typeclassId')">专家抽取</a>
						</li>
						<li class="active">
							<a href="javascript:void(0);">专家抽取列表</a>
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
		</c:if>

		<!-- 项目戳开始 -->
		<div class="col-md-12 col-sm-12 col-xs-12 container_box">
			<form id="form">
				<!-- 抽取地区 -->
				<input type="hidden" name="extAddress" id="extAddress" value="${extractionSites}">
				<!-- 打开类型 -->
				<input type="hidden" value="${typeclassId}" name="typeclassId" />
				<!-- 项目id  -->
				<input type="hidden" id="pid" value="${projectId}" name="id">
				  <!-- 监督人员id  -->
                <input type="hidden" id="superviseId" value="${superviseId}" name="superviseId">
					<h2 class="count_flow"><i>1</i>项目信息</h2>
					<ul class="ul_list">
                        <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red" id="red0">*</span> 项目名称:</span>
                            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                                <input class="span5" id="projectName" name="name"  value="${projectName}" type="text">
                                <span class="add-on">i</span>
                                <div class="cue" id="projectNameError"></div>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12">
                          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red" id="red1">*</span> 项目编号:</span>
                          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input class="span5" id="projectNumber" name="projectNumber" value="${projectNumber}" type="text">
                            <span class="add-on">i</span>
                            <div class="cue" id="projectNumberError"></div>
                          </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12 ">
                          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red" id="red2">*</span> 采购方式:</span>
                          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <select class="col-md-12 col-sm-12 col-xs-12 p0" name="purchaseType">
                              <c:forEach items="${findByMap}" var="map">
                                <option value="${map.id}">${map.name}</option>
                              </c:forEach>
                            </select>
                          </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12 ">
                          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red" id="red3">*</span> 开标日期:</span>
                          <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="tenderTimeId" name="bidDate" value="<fmt:formatDate value='${bidDate}'
                                            pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                              <div class="cue" id="tenderTimeError"></div>
                          </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12 ">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 监督人员:</span>
                            <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0 left_table">
                                <input class="span5" readonly id="supervises" title="${userName}" value="${userName}" onclick="supervise();" type="text">
                                <span class="add-on">i</span>
                                <div class="cue" id="dSupervise"></div>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12 dnone">
                            <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span> 响应时间:</span>
                            <div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
                                <input class="col-md-5 col-sm-5 col-xs-5" name="hour" value="${hour}" maxlength="3" type="text">
                                <span class="f14   fl">时</span>
                                <input class="col-md-5 col-sm-5 col-xs-5" value="${minute}" id="minute" name="minute" maxlength="3" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                <span class="f14   fl">分</span>
                                <div class="cue" id="responseTimeError"></div>
                            </div>
                        </li>
                        <li class="col-md-3 col-sm-4 col-xs-12 ">
                          <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="red">*</span> 抽取地区:</span>
                           <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                            <input class="span5" id="extractionSites" name="extractionSites" value="${extractionSites}" type="text">
                            <span class="add-on">i</span>
                            <div class="cue" id="extractionSitesError"></div>
                          </div>
                        </li>
                    </ul>
          <div>
          <h2 class="count_flow "><i>2</i>
            <div class="ww50 fl">抽取信息</div>
          </h2>
           <div align="right" class=" pl20 mb10 hide isCurment_div"  >
               <c:if test="${typeclassId!=null && typeclassId !='' }">
                       <button class="btn mb10" 
                onclick="add(1);" type="button">添加包</button>
                </c:if>
             <input class=" " readonly id="packageName" value="" placeholder="请选择包" onclick="showPackageType();"   type="text">
              <input  readonly id="packageId"  name="packageId"     type="hidden">
<!--            <select class="w200 dnone" id="packageId"  > -->
<%--             <c:forEach items="${listResultExpert}" var="list"> --%>
<%--                 <option value="${list.id }" >${list.name }</option> --%>
<%--             </c:forEach> --%>
<!--           </select> -->
            <button class="btn mb10" 
                onclick="add(2);" type="button">抽取</button>
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
                <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">包名:<span class="f14 blue">${listResultExpert[index].name }</span></h2>
              </div>
              <div class="p0${index} hide">
							  <table id="table" class="table table-bordered table-condensed">
							          <thead>
							            <tr>
							              <th class="info w50">序号</th>
							              <th class="info">专家姓名</th>
							              <th class="info">联系电话</th>
							              <th class="info">专家类型</th>
							              <th class="info">工作单位名称</th>
							              <th class="info">专家技术职称</th>
							            </tr>
							          </thead>
							          <tbody id="tbody">
							          <c:choose>
							           <c:when test="${typeId == 1}">
                                           <c:set value="${0}" var="_index"></c:set>
                                            <c:forEach items="${list.listProjectExtract}" var="listyes" varStatus="vs">
                                                <c:if test="${listyes.expert.isProvisional == null || listyes.expert.isProvisional == 0}">
                                                    <c:set value="${_index+1}" var="_index"></c:set>
                                                <tr class='cursor '>
                                                  <td class='tc'>${_index}</td>
                                                  <td class='tc'>${listyes.expert.relName}</td>
                                                  <td class='tc'>${listyes.expert.mobile}</td>
                                                  <td class='tc'>
                                                  <c:forEach var="expertType" items="${ddList}">
                                                    <c:if test="${listyes.reviewType eq expertType.id}">
                                                      ${expertType.name}
                                                    </c:if>
                                                  </c:forEach>
                                                  </td>
                                                  <td class='tc'>${listyes.expert.workUnit}</td>
                                                  <td class='tc'>${listyes.expert.professTechTitles}</td>
                                                </tr>
                                                </c:if>
                                            </c:forEach>
							           </c:when>
							           <c:otherwise>
                                           <c:set value="${0}" var="_index"></c:set>
							             <c:forEach items="${list.listProjectExtract}" var="listyes"    varStatus="vs">
                                             <c:if test="${listyes.expert.isProvisional == 0 }">
                                                 <c:set value="${_index+1}" var="_index"></c:set>
                                               <tr class='cursor '>
                                                  <td class='tc'>${_index}</td>
                                                  <td class='tc'>******</td>
                                                  <td class='tc'>******</td>
                                                  <td class='tc'>******</td>
                                                  <td class='tc'>******</td>
                                                  <td class='tc'>******</td>
                                                </tr>
                                             </c:if>
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