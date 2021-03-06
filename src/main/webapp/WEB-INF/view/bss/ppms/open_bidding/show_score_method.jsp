<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
    <!--<![endif]-->

    <head>
    <%@ include file="../../../common.jsp"%>
    <script type="text/javascript">
        function save(obj){
            var y;  
            oRect = obj.getBoundingClientRect();  
            y=oRect.top-150;  
            x=oRect.left;
            var reg = /^\d+\.?\d*$/;
            var typename= $("#typeName").val();
            var business = $("input[name='business']").val();
            var valid = $("input[name='valid']").val();
            var fr = $("input[name='floatingRatio']").val();
            if (typename == 0) {
                if (valid == "" || fr =="") {
                    layer.msg("表单需要填写完整",{offset: ['30%', '40%']});
                    return;
                }
                if(!reg.exec(valid)) {
                    layer.msg("请填写数字",{offset: ['30%', '40%']});
                    return;
                }
                if(!reg.exec(fr)) {
                    layer.msg("请填写数字",{offset: ['30%', '40%']});
                    return;
                }
                if(reg.exec(fr)  < 3  || reg.exec(fr) > 5) {
                    layer.msg("浮动比例只能在：3%-5%",{offset: ['30%', '40%']});
                    return;
                }
                if(reg.exec(valid) >40) {
                    layer.msg("平均值不能高于40%",{offset: ['30%', '40%']});
                    return;
                }
            }
            if (typename == 2) {
                if (valid == "" || business =="") {
                    layer.msg("表单需要填写完整",{offset: ['30%', '40%']});
                    return;
                }
                if(!reg.exec(valid)) {
                    layer.msg("请填写数字",{offset: ['30%', '40%']});
                    return;
                }
                if(!reg.exec(business)) {
                    layer.msg("请填写数字",{offset: ['30%', '40%']});
                    return;
                }
                if(reg.exec(valid) >40) {
                    layer.msg("平均值不能高于40%",{offset: ['30%', '40%']});
                    return;
                }
                if(reg.exec(business) >30) {
                    layer.msg("不能高于30%",{offset: ['30%', '40%']});
                    return;
                }
               /*  if (!$("#medicalType_li").hasClass("dnone")){ */
					/* if($("#medicalType").val()=="1"){ */
						/* if($("#deviation").val().length==0){
							layer.msg("表单需要填写完整",{offset: ['30%', '40%']});
		        			return;
						}else{ */
							if(!reg.exec($("#deviation").val())){
								layer.msg("请填写数字",{offset: ['30%', '40%']});
								return;
							}
						/* } */
					/* } */
				/* } */
            }
            if(typename!=2){
        		/* $("#medicalType").val(3) */
        		$("#deviation").val("");
        	}
            form1.submit();
        }
        
        
        function show(id){
            //基准价法
            if (id ==0) {
               if (!$("#business").hasClass("dnone")) {
                    $("#business").addClass("dnone");
                }
                if ($("#floatingRatio").hasClass("dnone")){
                    $("#floatingRatio").removeClass("dnone");
                }
                if ($("#valid").hasClass("dnone")){
                    $("#valid").removeClass("dnone");
                }
                /* if (!$("#medicalType_li").hasClass("dnone")){
			    	$("#medicalType_li").addClass("dnone");
			    } */
			    if (!$("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").addClass("dnone");
			    }
            }
            //性价比
            if (id ==1) {
                if (!$("#business").hasClass("dnone")) {
                    $("#business").addClass("dnone");
                }
                if (!$("#floatingRatio").hasClass("dnone")){
                    $("#floatingRatio").addClass("dnone");
                }
                if (!$("#valid").hasClass("dnone")){
                    $("#valid").addClass("dnone");
                }
                /* if (!$("#medicalType_li").hasClass("dnone")){
			    	$("#medicalType_li").addClass("dnone");
			    } */
			    if (!$("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").addClass("dnone");
			    }
            }
            //综合评分法
            if (id ==2) {
                if ($("#business").hasClass("dnone")) {
                    $("#business").removeClass("dnone");
                }
                if (!$("#floatingRatio").hasClass("dnone")){
                    $("#floatingRatio").addClass("dnone");
                }
                if ($("#valid").hasClass("dnone")){
                    $("#valid").removeClass("dnone");
                }
                if ($("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").removeClass("dnone");
			    }
            }
            //最低价
            if (id ==3) {
                if (!$("#business").hasClass("dnone")) {
                    $("#business").addClass("dnone");
                }
                if (!$("#floatingRatio").hasClass("dnone")){
                    $("#floatingRatio").addClass("dnone");
                }
                if (!$("#valid").hasClass("dnone")){
                    $("#valid").addClass("dnone");
                }
               /*  if (!$("#medicalType_li").hasClass("dnone")){
			    	$("#medicalType_li").addClass("dnone");
			    } */
			    if (!$("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").addClass("dnone");
			    }
            }
        }
        
        $(function() {
            var option = $("#typeName").find("option");
            for (var i = 0; i<option.length; i ++) {
                if (option[i].value == '${bidMethod.typeName}') {
                    $(option[i]).attr("selected","selected");
                    show(option[i].value);
                    //console.dir(option[i].value);
                }
            }
              
        });
        /* function medicalTypes(objs){
        	if($(objs).val()=="0"){
        		if (!$("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").addClass("dnone");
			    }
        		$("#deviation").val("");
        	}else{
        		if ($("#deviation_li").hasClass("dnone")){
			    	$("#deviation_li").removeClass("dnone");
			    }
        		$("#deviation").val("");
        	}
        	
        } */
    </script>
    </head>
    <body>
        <!-- 修改订列表开始-->
        <div>
            <form id="form1" action="${pageContext.request.contextPath}/intelligentScore/updateScoreMethod.html" method="post">
                <div class="bggrey p20">
                    <h2 class="list_title mt0">修改评标方法</h2>
                    <ul class="list-unstyled m_boxS1">
                            <input type="hidden" name="projectId" value="${projectId}" />
                            <input type="hidden" name="packageId" value="${packageId}" />
                            <input type="hidden" name="flowDefineId" value="${flowDefineId}" />
                            <input type="hidden" name="id" value="${bidMethod.id}" />
                            <li class="col-md-5 col-sm-6 col-xs-12 pl15">
                                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">评分方法:</span> 
                                <div class="select_common col-md-12 col-sm-12 col-xs-12 p0 mb0">
                                <select class="w180" name="typeName" id="typeName"  onchange="show(this.value);">
                                        <c:forEach items="${ddList}" var="list" varStatus="vs">
                                            <c:if test="${vs.index != 1}">
                                            <option  value="${vs.index}">${list.name}</option>
                                            </c:if>
                                        </c:forEach>
                                </select>
                                </div>
                            </li>
                            <li id="floatingRatio" class="col-md-5 col-sm-6 col-xs-12 clear mt25">
                              <div class="col-md-12 col-xs- 12 col-sm-12 p0">
                                <span class="block w100p">浮动比例(%):</span> 
                                <div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">
                                    <input name="floatingRatio"  type="text" value="${bidMethod.floatingRatio }">
                                    <span class="add-on hand">i</span>
                                    <span class="input-tip">浮动比例只能在：3%-5%</span>
                                </div>
                                <div class="cue">${fr }</div>
                              </div>
                            </li>
                            <li id="valid" class="col-md-5 col-sm-6 col-xs-12 clear mt25" >
                               <div class="col-md-12 col-xs- 12 col-sm-12 p0">
                                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商报价不得高于有效供应商报价平均值的百分比(%)：</span> 
                                 <div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">
                                    <input name="valid"   type="text" value="${bidMethod.valid }">
                                    <span class="add-on hand">i</span>
                                    <span class="input-tip">平均值不能高于40%</span>
                                 </div>
                                 <div class="cue">${valid }</div>
                               </div>
                                    <!-- <span>供应商报价不得超过有效供应商报价平均值百分比</span> -->
                            </li>
                            <li id="business" class="col-md-5 col-sm-6 col-xs-12 dnone clear mt25" id="business">
                                <!-- <span class="">商务技术评分高于上午技术评分百分比:</span> --> 
                                <div class="col-md-12 col-xs- 12 col-sm-12 p0">
                                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">抛开价格因素经济技术评分不得低于有效经济技术评分的百分比(%) ：</span> 
                                <div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">                         
                                    <input name="business"  type="text" value="${bidMethod.business }">
                                     <span class="add-on">i</span>
                                     <span class="input-tip">不能高于30%</span>
                                </div>
                                <div class="cue">${busi }</div>
                                </div>
                            </li>
                            <%-- <li id="medicalType_li" class="col-md-5 col-sm-6 col-xs-12 pl15 dnone clear mt25">
				              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">是否为医疗:</span> 
				              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0 mb0">
				              <select class="w180" name="medicalType" id="medicalType" onchange="medicalTypes(this)"  >
				               <option value="0"  <c:if test="${bidMethod.medicalType==0}">selected="selected"</c:if>>否</option>
				               <option value="1" <c:if test="${bidMethod.medicalType==1}">selected="selected"</c:if>>是</option>
				              </select>
				              </div>
				            </li> --%>
				            <li id="deviation_li"  class="col-md-5 col-sm-6 col-xs-12 <c:if test="${bidMethod.medicalType!=1}">dnone</c:if> clear mt25" >
				              <div class="col-md-12 col-xs- 12 col-sm-12 p0">
				              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">最少满足技术指标数量（<span class="red"]>超过该数量时技术得分0分</span>）：</span> 
				              <div class="input_append input_group col-md-12 col-sm-12 col-xs-12 p0">                         
				                <input name="deviation" id="deviation"  type="text" value="${bidMethod.deviation}">
				               <span class="add-on">i</span>
				              </div>
				              <div class="cue"></div>
				              </div>
				            </li>
                            <li class="clear"></li>
                      </ul>
                </div>
                <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
                 <c:if test="${status != 1}">
                    <input class="btn btn-windows save w80" readonly onclick="save(this)" value="保存" />
                    </c:if>
                    <button class="btn btn-windows back w80" onclick="history.go(-1)" type="button">返回</button>
                </div>
            </form>
        </div>
    </body>

</html>