<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_attach/attach_audit.js"></script>
    <script type="text/javascript">
      $(function(){
    	  //绑定事件
        $("input[name='selectOption']").bind("click", function(){
          $("#cate_result").html("");
          var selectedVal = $(this).val();
          if(selectedVal == 1){
            $("#cate_result").html("复核合格。");
            return;
          }
          if(selectedVal == '0'){
            $("#cate_result").html("复核不合格 。");
          }
        });
        
        //自动选中状态
        var flagAduit = "${flagAduit}";
        if(flagAduit !="" && flagAduit == 1){
        	$("#qualified").attr("checked", "checked");
        	$("#cate_result").html("复核合格。");
        }
        if(flagAduit !="" && flagAduit == 0){
        	$("#unqualified").attr("checked", "checked");
        	$("#cate_result").html("复核不合格 。");
        }
        
        var status = ${status};
        if(status == 5 || status == 6){
        	//如果有意见就显示"重新复核"按钮，复核表
        	$("#review").removeClass("hidden");
        	$("#checkList").removeClass("hidden");
        	
        	//只读
        	$("input[type='text'],textArea").attr("readonly", "readonly");
        	$("input[name='selectOption']").attr("disabled", true);
        }
      }); 
    </script>
    
    <script type="text/javascript">
      //复核结束
      function reviewEnd(){
    	  var supplierId = $("#supplierId").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/supplierReview/reviewEnd.do",
          type: "post",
          data: {"supplierId" : supplierId},
          success: function(result){
            if(result.status == 200){
          	    //显示复核表
                $("#checkList").removeClass("hidden");
                //显示重新复审、返回按钮
                $("#review").removeClass("hidden");
                //隐藏复核结束、暂存按钮
                $("#reviewEnd").addClass("hidden");
             }else{
               layer.msg(result.msg, {offset: '100px'});
             }
          },
          error: function(){
              layer.msg("操作失败！", {offset: '100px'});
            }
        });
      }
      
      //重新复核
      function restartReview(){
    	  var supplierId = $("#supplierId").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/supplierReview/restartReview.do",
          type: "post",
          data: {"supplierId" : supplierId},
          success: function(result){
            if(result.status == 200){
              layer.msg(result.msg, {offset: '100px'});
              window.setTimeout(function() {
                $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/list.html");
                $("#submitform").submit();
              }, 1000);
            }else{
              layer.msg("操作失败！", {offset: '100px'});
            }
          },
          error: function(){
            layer.msg("操作失败！", {offset: '100px'});
          }
        });
      }
      
      //返回列表
      function renturnList(){
    	  $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/list.html");
        $("#submitform").submit();
      }
      
      //暂存/实时保存  type: 1 提示信息， 2：不提示信息
      function temporary(type){
    	  var supplierId = $("#supplierId").val();
        //选择的意见
        var selectOption = $("input[name='selectOption']:checked").val();
        //手输入的意见
        var opinion = $("#opinion").val();
        if(selectOption == 1){
          opinion = "复核合格。" + opinion;
        }
        if(selectOption == 0){
          opinion = "复核不合格。" + opinion;
        }
        
        $.ajax({
          url: "${pageContext.request.contextPath}/supplierReview/temporary.do",
          type: "post",
          data: {"supplierId" : supplierId, "opinion" : opinion, "flagAduit" : selectOption},
          success: function(result){
            if(result.status == 200){
            	if(type == 1){
            		layer.msg(result.msg, {offset: '100px'});
            	}
             }else{
               layer.msg("操作失败！", {offset: '100px'});
             }
          },
          error: function(){
              layer.msg("操作失败！", {offset: '100px'});
            }
        });
      }
      
      //下载复核表
      function downloadTable (){
    	  var supplierId = $("#supplierId").val();
    	  $("input[name='supplierId']").val(supplierId);
    	  $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/downloadTable.html");
        $("#submitform").submit();
    	  
    	  //window.location.href = "${pageContext.request.contextPath}/supplierReview/downloadTable.do?supplierId=" + supplierId;
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
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商管理</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商复核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
            <jsp:param value="nine" name="currentStep"/>
            <jsp:param value="${supplierId}" name="supplierId"/>
            <jsp:param value="${supplierStatus}" name="supplierStatus"/>
            <jsp:param value="${sign }" name="sign"/>
           </jsp:include>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info w250">项目</th>
                  <th class="info w60">扫描件</th>
                  <th class="info w150">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
              <tbody id="tbody_items">
                <c:forEach items="${itemList}" var="item" varStatus="vs">
                  <tr class="h40">
                    <td class="tc">${vs.index+1}</td>
                    <td class="tc">${item.attachName}</td>
                    <td class="tc">
                      <c:if test="${empty item.businessId || empty item.typeId}">
                        <a href="javascript:;" onclick="viewAttach('${item.viewUrl}','${item.attachName}')">查看</a>
                      </c:if>
                      <c:if test="${!empty item.businessId && !empty item.typeId}">
                        <u:show showId="inves_${vs.index+1}" businessId="${item.businessId}" sysKey="${sysKey}" typeId="${item.typeId}" delete="false"/>
                      </c:if>
                    </td>
                    <td class="tc">
                      <input type="hidden" value="" id="isAccord_${item.id}" />
                      <c:if test="${item.isAccord==1}">
                        <button class="btn" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgdd black_link" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2, 1)"</c:if>>不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==2}">
                        <button class="btn bgdd black_link" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgred" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2)"</c:if>>不一致</button>
                      </c:if>
                      <c:if test="${item.isAccord==0}">
                        <button class="btn bgdd black_link" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 1, 1)"</c:if>>一致</button>
                        <button class="btn bgdd black_link" type="button" <c:if test="${status == 1}">onclick="opr(this, '${item.id}', 2, 1)"</c:if>>不一致</button>
                      </c:if>
                    </td>
                    <td><input type="text" class="w100p mb0" id="${item.id}_suggest_${vs.index+1}" value="${item.suggest}" maxlength="300" onblur="saveAuditSuggest('${item.id}', 1 , ${vs.index+1})"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </ul>
          <div class="clear"></div>
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div class="select_check">
					      <input type="radio" value="1" name="selectOption" id="qualified" onclick="temporary(2)">复核合格
					      <input type="radio" value="0" name="selectOption" id="unqualified" onclick="temporary(2)">复核不合格
				      </div>
            </li>
            <li>
              <div id="cate_result"></div>
            </li>
						<li class="mt10">
	             <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" onblur="temporary(2)">${auditOpinion}</textarea>
	          </li>
          </ul>
          
          <div class="clear"></div>
          <h2 class="count_flow"><i>3</i>下载供应商复核表</h2>
          <ul class="ul_list hand">
            <a class="btn btn-windows input" onclick='downloadTable()' href="javascript:void(0)">下载复核表</a>
          </ul>
          
          <div class="clear"></div>
          <div id="checkList" class="hidden">
            <h2 class="count_flow"><i>4</i>上传供应商复核表</h2>
	          <ul class="ul_list hand">
	            <li class="col-md-6 col-sm-6 col-xs-6">
                <div>
	                <span class="fl"><span class="red">*</span>上传复核表：</span>
	                <u:upload id="pic_review" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReview}" buttonName="上传彩色扫描件" auto="true" multiple="true"/>
	                <u:show showId="pic_review" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReview}"/>
                </div>
              </li>
	          </ul>
          </div>
        </div>
      </div>
    </div>
    
    <c:if test="${status == 1}">
      <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc" id="reviewEnd">
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="toStep('six');">上一步</a>
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporary(1);">暂存</a>
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="reviewEnd();">复核结束</a>
      </div>
    </c:if>
    <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc hidden" id="review">
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="restartReview();">重新复核</a>
      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="renturnList();">返回</a>
    </div>
    
    <input id="supplierId" value="${supplierId}" type="hidden">
    
    <form action="" id="submitform">
      <input value="" name="supplierId" type="hidden"/>
    </form>
  </body>
</html>