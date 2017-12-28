<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
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
        
        var isRecord = "${isRecord}";
        //如果有意见就显示"重新复核"按钮，复核表
        if(isRecord == "yes"){
        	$("#review").removeClass("hidden");
        	$("#checkList").removeClass("hidden");
        }
      }); 
    </script>
    
    <script type="text/javascript">
      function reviewEnd(){
    	  //显示复核表
    	  $("#checkList").removeClass("hidden");
    	  //显示重新复审、返回按钮
    	  $("#review").removeClass("hidden");
    	  //隐藏复核结束、暂存按钮
    	  $("#reviewEnd").addClass("hidden");
    	  
    	  var url = "${pageContext.request.contextPath}/supplierReview/reviewEnd.do";
    	  saveOpinion(url, 0);
      }
      
      //保存
      function saveOpinion(url, msg){
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
	    	  url: url,
          type: "post",
          data: {"supplierId" : supplierId, "opinion" : opinion, "flagAduit" : selectOption},
          success: function(result){
        	  if(msg == 1){
        		  layer.msg(result, {offset: '100px'});
        	  }
          },
          error: function(){
              layer.msg("保存失败！", {offset: '100px'});
            }
	      });
      }
      
      //重新复核
      function restartReview(){
    	  var supplierId = $("#supplierId").val();
    	  var supplierId = $("input[name='supplierId']").val(supplierId);
    		$("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/restartReview.html");
    		$("#submitform").submit();
      }
      
      //返回列表
      function renturnList(){
    	  $("#submitform").attr("action", "${pageContext.request.contextPath}/supplierReview/list.html");
        $("#submitform").submit();
      }
      
      //暂存
      function temporary(){
    	  var url = "${pageContext.request.contextPath}/supplierReview/temporary.do";
        saveOpinion(url, 1);
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
                  <th class="info">项目</th>
                  <th class="info">扫描件</th>
                  <th class="info">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
            </table>
          </ul>
          
          <h2 class="count_flow"><i>2</i>复核意见</h2>
          <ul class="ul_list hand">
            <li>
              <div class="select_check">
					      <input type="radio" value="1" name="selectOption" id="qualified">复核合格
					      <input type="radio" value="0" name="selectOption" id="unqualified">复核不合格
				      </div>
            </li>
            <li>
              <div id="cate_result"></div>
            </li>
						<li class="mt10">
	             <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80">${auditOpinion}</textarea>
	          </li>
          </ul>
          
          <h2 class="count_flow"><i>3</i>下载供应商复核表</h2>
          <ul class="ul_list hand">
          
          </ul>
          
          <div id="checkList" class="hidden">
            <h2 class="count_flow"><i>4</i>上传供应商复核表</h2>
	          <ul class="ul_list hand">
	          
	          </ul>
          </div>
        </div>
      </div>
    </div>
    
    <c:if test="${isRecord eq 'no'}">
      <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc" id="reviewEnd">
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="toStep('six');">上一步</a>
	      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="temporary();">暂存</a>
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