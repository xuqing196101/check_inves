<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<title>审核汇总</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
	<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/audit_reasons.js"></script>
    <script type="text/javascript">
        //只读
        $(function() {
            /*$(":input").each(function() {
             $(this).attr("readonly", "readonly");
             }); */
            //审核按钮
            var num = ${num};
            if(num == 0){
                if('${supplierStatus}' != -2 && '${supplierStatus}' != -3){
                    //$("#tuihui").attr("disabled", true);
                    $("#butongguo").attr("disabled", true);
                }
                $("#buhege").attr("disabled", true);
            }
            if(num != 0){
                //$("#tongguo").attr("disabled", true);
                $("#hege").attr("disabled", true);
            };
        });
        function trim(str) { //删除左右两端的空格
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }
	   /* function tijiao(status){
	     $("#supplierStatus").val(status);
		 form1.submit();
	   } */
			
		//上一步
        function lastStep(){
            var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
            $("#form_id").attr("action",action);
            $("#form_id").submit();
        }

        //审核
        function shenhe(status){
            var supplierId = $("input[name='supplierId']").val();
            /*if(status == 3){
                //询问框
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认','取消']
                }, function(){
                    var index = layer.prompt({
                        title: '请填写理由：',
                        formType: 2,
                        offset: '100px',
                    }, function(text) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/supplierAudit/recordNotPassed.html",
                            data: {"reason" : text , "supplierId" : supplierId},
                            success: function() {
                                //提交审核
                                $("#status").val(status);
                                $("#status").val(status);
                                $("#form_shen").submit();
                            },
                        });
                    });
                });
            }else{*/
                //询问框
            if(status == -2){
                /*// 获取审核意见
                var opinion  = $("#opinion").val();
                if(opinion == ''){
                    layer.msg("审核意见不能为空！");
                    return;
                }
                if(opinion.length > 1000){
                    layer.msg("审核意见不能超过1000字！");
                    return;
                }*/
                // 校验
                var flags = vartifyAuditCount();
                if(flags){
                    return;
                }
                // 校验通过
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认','取消']
                }, function(index){
                    //最终意见
                    $("#status").val(status);
                    //$("#auditOpinion").val($("#auditOpinionFile").val());
                    //$("input[name='opinion']").val(opinion);
                    // ajax提交改变供应商状态
                    $.ajax({
                        url: "${pageContext.request.contextPath}/supplierAudit/updateStatusOfPublictity.do",
                        data: $("#form_shen").serialize(),
                        success: function (data) {
                            if(data.status == 200){
                                $("#tongguoSpan").hide();
                                $("#tuihui").hide();
                                $("#checkWord").show();
                                $("#publicity").show();
                                $("#tempSave").css("display","inline-block");
                                $("#nextStep").css("display","inline-block");
                                // 显示上传批准审核表页面标签
                                $("#reverse_of_seven_i").show();
                                $("#reverse_of_eight").show();
                            }
                        }
                    });
                    layer.close(index);
                    return;
                });
            }

            if(status == 2){
                var flags = false;
                $.ajax({
                    url:globalPath + "/supplierAudit/vertifyAuditNoPassItem.do",
                    type: "POST",
                    async:false,
                    data:{
                        "supplierId":supplierId
                    },
                    dataType:"json",
                    success:function (data) {
                        if (data.status == 500) {
                            layer.msg(data.msg);
                            flags = true;
                            return;
                        }
                    }
                });
                if(flags){
                    return;
                }
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认','取消']
                }, function(index){
                    //最终意见
                    $("#status").val(status);
                    //提交审核
                    $("#form_shen").submit();
                });
            }

            if(status != -2 && status != 2){
                var opinion = document.getElementById('opinion').value;
                opinion = trim(opinion);
                if (opinion != null && opinion != "") {
                    if (opinion.length <= 200) {
                        layer.confirm('您确认吗？', {
                            closeBtn: 0,
                            offset: '100px',
                            shift: 4,
                            btn: ['确认','取消']
                        }, function(index){
                            //最终意见
                            $("#status").val(status);
                            $("input[name='opinion']").val(opinion);
                            if(status == -2){
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/supplierAudit/updateStatusOfPublictity.do",
                                    data: $("#form_shen").serialize(),
                                    success: function (data) {
                                        if(data.status == 200){
                                            layer.alert('完成操作，请公示！', function(index){
                                                $("#opinion").attr("disabled", true);
                                                $("#tongguoSpan").hide();
                                                $("#checkWord").show();
                                                $("#publicity").show();
                                                init_web_upload();
                                                layer.close(index);
                                            });
                                        }
                                    }
                                });
                                layer.close(index);
                                return;
                            }
                            //提交审核
                            $("#form_shen").submit();
                        });
                    } else {
                        layer.msg("不能超过200字", {offset: '100px'});
                    }
                } else {
                    layer.msg("请填写最终意见", {offset: '100px'});
                    return;
                }
            }
        };

		/** 全选全不选 */
        function selectAll(){
            var checklist = document.getElementsByName ("chkItem");
            var checkAll = document.getElementById("checkAll");
            if(checkAll.checked){
                for(var i=0;i<checklist.length;i++){
                    checklist[i].checked = true;
                } ;
            }else{
                for(var j=0;j<checklist.length;j++){
                    checklist[j].checked = false;
                };
            };
        }

        //移除
        function dele(){
            var supplierId = $("input[name='supplierId']").val();
            var ids =[];
            $('input[name="chkItem"]:checked').each(function(){
                ids.push($(this).val());
            });
            if(ids.length>0){
                layer.confirm('您确定要移除吗?', {title:'提示！',offset: ['200px']}, function(index){
                    layer.close(index);
                    $.ajax({
                        url:"${pageContext.request.contextPath}/supplierAudit/deleteById.html",
                        data:"ids="+ids,
                        dataType:"json",
                        success:function(result){
                            result = eval("(" + result + ")");
                            if(result.msg == "yes"){
                                layer.msg("删除成功!",{offset : '100px'});
                                window.setTimeout(function(){
                                    var action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
                                    $("#form_id").attr("action",action);
                                    $("#form_id").submit();
                                }, 1000);
                            }
                        },
                        error: function(message){
                            layer.msg("删除失败",{offset : '100px'});
                        }
                    });
                });
            }else{
                layer.alert("请选择需要移除的信息！",{offset:'100px'});
            }
        }
    </script>
  </head>

  <body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			  </li>
			  <li>
				  <a>支撑环境</a>
			  </li>
			  <li>
				  <a>供应商管理</a>
			  </li>
			  <c:if test="${sign == 1}">
				  <li>
					  <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
				  </li>
			  </c:if>
			  <c:if test="${sign == 2}">
				  <li>
					  <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
				  </li>
			  </c:if>
			  <c:if test="${sign == 3}">
				  <li>
					  <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
				  </li>
			  </c:if>
		  </ul>
	  </div>
  </div>
  <div class="container container_box">
      <div class="content">
        <div class="col-md-12 tab-v2 job-content">
            <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%>
            <form id="form_id" action="" method="post">
                <input name="supplierId" value="${supplierId}" type="hidden">
                <input name="supplierStatus" id="supplierStatus" value="${supplierStatus}" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form>

            <!--审核意见上传表单-->
            <form id="opinionForm" method="post">
                <input name="id" value="${supplierAuditOpinion.id}" type="hidden">
                <input name="supplierId" value="${supplierId}" type="hidden">
                <input name="opinion" value="" id="opinionId" type="hidden">
                <input name="flagTime" value="" id="flagTime" type="hidden">
                <input name="flagAduit" value="" id="flagAduit" type="hidden">
                <input name="vertifyFlag" value="" id="vertifyFlag" type="hidden">
                <input name="isDownLoadAttch" id="downloadAttachFile" value="${supplierAuditOpinion.isDownLoadAttch}" type="hidden">
            </form>`
          
          <!-- download check table -->
            <form id="shenhe_form_id" action="" method="post">
                <input name="supplierId" type="hidden" value="${supplierId}"/>
                <input type="hidden" name="sign" value="${sign}">
                <input type="hidden" name="opinion">
                <input type="hidden" name="tableType">
            </form>
          
          <%-- <c:if test="${supplierStatus == 3 }">
             <h2 class="count_flow"><i>1</i>问题汇总</h2>
          </c:if> --%>
           <h2 class="count_flow"><i>1</i>审核汇总信息</h2>
          <div class="ul_list count_flow">
            <c:if test="${supplierStatus == 0 or supplierStatus==-2 or supplierStatus ==4 or (sign ==3 and supplierStatus ==5)}">
              <button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">移除</button>
            </c:if>
            <table class="table table-bordered table-condensed table-hover">
             <thead>
               <tr>
               	 <th class="info"><input type="checkbox" onclick="selectAll();"  id="checkAll"></th>
                 <th class="info">序号</th>
                 <th class="info" >审批类型</th>
                 <th class="info" >审批字段名字</th>
                 <th class="info" >审批内容</th>
                 <th class="info">不通过理由</th>
               </tr>
             </thead>
               <c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
                <input id="auditId" value="${list.id}" type="hidden">
                 <tr>
                   <td class="tc"><input type="checkbox" value="${reasons.id }" name="chkItem"  id="${reasons.id}"></td>
                   <td class="tc">${vs.index + 1}</td>
                   <td class="tc">
                     <c:if test="${reasons.auditType eq 'basic_page'}">基本信息</c:if>
                     <c:if test="${reasons.auditType eq 'finance_page'}">财务信息</c:if>
                     <c:if test="${reasons.auditType eq 'shareholder_page'}">股东信息</c:if>
                     <%-- <c:if test="${reasons.auditType == 'mat_pro_page'}">物资-生产信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_sell_page'}">物资-销售信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_eng_page'}">工程信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_serve_page'}">服务信息</c:if> --%>
                     <c:if test="${reasons.auditType eq 'mat_pro_page' || reasons.auditType eq 'mat_sell_page' || reasons.auditType eq 'mat_eng_page' || reasons.auditType eq 'mat_serve_page' || reasons.auditType eq 'supplierType_page'}">供应商类型</c:if>
                     <%-- <c:if test="${reasons.auditType == 'mat_serve_page' || reasons.auditType == 'item_sell_page' || reasons.auditType == 'item_eng_page' || reasons.auditType == 'item_serve_page'}">品目信息</c:if> --%>
                     <c:if test="${reasons.auditType eq 'items_page'}">品目信息</c:if>
                     <c:if test="${reasons.auditType eq 'items_product_page' or reasons.auditType eq 'items_sales_page' or reasons.auditType eq 'contract_product_page' or reasons.auditType eq 'contract_sales_page' or reasons.auditType eq 'aptitude_product_page' or reasons.auditType eq 'aptitude_sales_page'}">产品类别及资质合同</c:if>
                     <c:if test="${reasons.auditType eq 'aptitude_page'}">资质文件</c:if>
                     <c:if test="${reasons.auditType eq 'contract_page'}">品目合同</c:if>
                     <c:if test="${reasons.auditType eq 'download_page'}">申请表</c:if>
                   </td>
                   <td class="tl hand" title="${reasons.auditFieldName }">
                     <c:if test="${fn:length (reasons.auditFieldName) > 12}">${fn:substring(reasons.auditFieldName,0,12)}...</c:if>
              		   <c:if test="${fn:length(reasons.auditFieldName) <= 12}">${reasons.auditFieldName}</c:if>
                   </td>
                   <td class="tl hand" title="${reasons.auditContent}">
                   	 <c:if test="${fn:length (reasons.auditContent) > 20}">${fn:substring(reasons.auditContent,0,20)}...</c:if>
              		   <c:if test="${fn:length(reasons.auditContent) <= 20}">${reasons.auditContent}</c:if>
                   </td>
                   <td class="tl pl20 hand" title="${reasons.suggest}">
                   	 <c:if test="${fn:length (reasons.suggest) > 20}">${fn:substring(reasons.suggest,0,20)}...</c:if>
              		   <c:if test="${fn:length(reasons.suggest) <= 20}">${reasons.suggest}</c:if>
                   </td>
                 </tr>
               </c:forEach>
            </table>
          </div>
	        <%-- <c:if test="${supplierStatus == 5}">
		        <!-- <h2 class="count_flow"><i></i>上传考察表报告</h2> -->
			      <ul class="ul_list">
		          <li class="col-md-6 p0 mb25">
		            <input name="supplierId" value="${supplierId}" type="hidden">
		            <span class="col-md-5 padding-left-5" ><a class="star_red">*</a>考察报告:</span>
		            <div style="margin-bottom: 25px">
		              <u:upload id="inspect" businessId="${suppliers.id}" buttonName="上传考察报告" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" auto="true" /> 
		              <u:show showId="inspect_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" />
		            </div>
		          </li>
	          </ul>
	        </c:if> --%>
			<c:if test="${sign != 1}">
				<div>
					<h2 class="count_flow"><i>2</i>最终意见</h2>
					<ul class="ul_list">
						<li class="col-md-12 col-sm-12 col-xs-12">
							<div class="col-md-12 col-sm-12 col-xs-12 p0">
								<textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${ supplierAuditOpinion.opinion }</textarea>
							</div>
						</li>
					</ul>
				</div>
			</c:if>
			<c:if test="${ sign == 1}">
				<div>
					<div id="opinionDiv">
					  <div class="clear"></div>
						<h2 class="count_flow"><i>2</i><span class="red">*</span>审核意见</h2>
					  <ul class="ul_list">
						  <li>
							  <div class="select_check" id="selectOptionId">
							    <c:choose>
							      <c:when test="${supplierStatus == 0 or supplierStatus==-2 or supplierStatus ==4 or (sign ==3 and supplierStatus ==5)}">
                                      <input type="radio" name="selectOption" value="1">预审核通过
                                      <input type="radio" name="selectOption" value="0">预审核不通过
							      </c:when>
							      <c:otherwise>
							         <input type="radio" disabled="disabled" name="selectOption" value="1">预审核通过
                                     <input type="radio" disabled="disabled" name="selectOption" value="0">预审核不通过
							      </c:otherwise>
							    </c:choose>
							  </div>
						  </li>
                          <li><span type="text" name="cate_result" id="cate_result"></span></li>
                          <li class="mt10">
                                  <c:choose>
                                      <c:when test="${supplierStatus == 0 or supplierStatus==-2 or supplierStatus ==4 or (sign ==3 and supplierStatus ==5)}">
                                          <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${ supplierAuditOpinion.opinion }</textarea>
                                      </c:when>
                                      <c:otherwise>
                                          <textarea id="opinion" disabled="disabled" class="col-md-12 col-xs-12 col-sm-12 h80">${ supplierAuditOpinion.opinion }</textarea>
                                      </c:otherwise>
                                  </c:choose>
                                  <div class="clear"></div>
                          </li>
					  </ul>
                    <input type="hidden" value="${supplierAuditOpinion.flagAduit}" id="hiddenSelectOptionId">
                    </div>
			        <!-- 审核公示扫描件上传 -->
                    <div id="checkWord" class="display-none">
                        <h2 class="count_flow"><i>3</i><span class="red">*</span>供应商审批表</h2>
                        <ul class="ul_list">
                            <li class="col-md-6 col-sm-6 col-xs-6">
                                <span class="fl">下载审核表：</span>
                                <a href="javascript:;" onclick="downloadTable(0)"><img src="${ pageContext.request.contextPath }/public/webupload/css/download.png"/></a>
                            </li>
                        </ul>
                    </div>
				</div>
			</c:if>

			<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc mt20">
				<input name="opinionBack" id="opinionBack" value="" type="hidden">
                <form id="form_shen" action="${pageContext.request.contextPath}/supplierAudit/updateStatus.html"  enctype="multipart/form-data">
                    <input name="supplierId" id="supplierId" value="${supplierId}" type="hidden">
                    <input name="status" id="status" type="hidden" value="${supplierStatus}">
                    <input name="opinion" type="hidden">
                    <input name="id" type="hidden">
                    <input name="auditOpinionAttach" id="auditOpinion" type="hidden" />
                    <div class="margin-bottom-0  categories">
                        <div class="col-md-12 add_regist tc">
                            <div class="col-md-12 add_regist tc">
                                <a class="btn"  type="button" onclick="lastStep();">上一步</a>
                                <!-- <a class="btn"  type="button" onclick="lastStep();">上一步</a> -->
                                <c:if test="${supplierStatus == 0}">
                                    <input  class="btn btn-windows back"  type="button" onclick="shenhe(2)" value="退回修改" id="tuihui">
                                    <span id="tongguoSpan"><input class="btn btn-windows git"  type="button" onclick="shenhe(-2)" value="预审核结束" id="tongguo"></span>
                                    <%--<span class="display-none" id="publicity"><input class="btn btn-windows apply" type="button" onclick="shenhe(-3);" value="公示 "></span>--%>
                                    <a id="tempSave" class="btn padding-left-20 padding-right-20 btn_back margin-5 display-none" onclick="tempSave();">暂存</a>
                                    <a id="nextStep" class="btn display-none" type="button" onclick="nextStep();">下一步</a>
                                    <%--<input class="btn btn-windows cancel"  type="button" onclick="shenhe(3)" value="审核不通过" id="butongguo">--%>
                                </c:if>
                                <c:if test="${supplierStatus == -2 || supplierStatus == -3 || supplierStatus == 3 || (supplierStatus == 1 && sign == 1)}">
                                    <%--<c:if test="${supplierStatus == -2}">
                                <span id="publicity"><input class="btn btn-windows apply" type="button" onclick="shenhe(-3);" value="公示 "></span>
                              </c:if>--%>
                                    <%--<input class="btn btn-windows back"  type="button" onclick="shenhe(2)" value="退回修改" id="tuihui">--%>
                                    <%-- <input class="btn btn-windows cancel"  type="button" onclick="shenhe(3)" value="审核不通过" id="butongguo">--%>
                                    <c:if test="${supplierStatus == -2}">
                                      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempSave();">暂存</a>
                                    </c:if>
                                    <a class="btn" type="button" onclick="nextStep();">下一步</a>
                                </c:if>

                                <c:if test="${supplierStatus == 4}">
                                    <input class="btn btn-windows git"  type="button" onclick="shenhe(5)" value="复核通过 " id="tongguo">
                                    <input class="btn btn-windows cancel"  type="button" onclick="shenhe(6)" value="复核不通过" id="butongguo">
                                </c:if>
                                <c:if test="${supplierStatus == 5 && sign ==3}">
                                    <input class="btn btn-windows git"  type="button" onclick="shenhe(7)" value="合格 " id="hege">
                                    <input class="btn btn-windows cancel"  type="button" onclick="shenhe(8)" value="不合格" id="buhege">
                                </c:if>
                            </div>
                        </div>
                    </div>
                </form>
          </div>
        </div>     
      </div>
    </div>
  </body>
</html>
