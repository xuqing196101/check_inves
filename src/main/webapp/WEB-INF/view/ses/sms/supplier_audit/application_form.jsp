<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>申请表</title>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script> 
		<script type="text/javascript">
            $(function() {
                $("#reverse_of_six").attr("class","active");
                $("#reverse_of_six").removeAttr("onclick");
                $("li").each(function() {
                    $(this).find("p").hide();
                });

                $("li").find("span").each(function() {
                    var onMouseMove = "this.style.background='#E8E8E8'";
                    var onmouseout = "this.style.background='#FFFFFF'";
                    $(this).attr("onMouseMove",onMouseMove);
                    $(this).attr("onmouseout",onmouseout);
                });
            });

            function reason1(ele,auditField){
                var supplierStatus= $("input[name='supplierStatus']").val();
                var sign = $("input[name='sign']").val();
                //只有审核的状态能审核
                if(supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 4 || (sign == 3 && supplierStatus == 5)){
                    var supplierId=$("#supplierId").val();
                    var auditFieldName = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
                    var index = layer.prompt({
                            title: '请填写不通过的理由：',
                            formType: 2,
                            offset: '100px',
                            maxlength: '100'
                        },
                        function(text){
                            var text = trim(text);
                            if(text != null && text !=""){
                                $.ajax({
                                    url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.do",
                                    type: "post",
                                    data: {"auditType":"download_page","auditFieldName":auditFieldName,"auditContent":"附件","suggest":text,"supplierId":supplierId,"auditField":auditField},
                                    dataType: "json",
                                    success:function(result){
                                      if(result.status == "503"){
                                         layer.msg('该条信息已审核过！', {             
                                           shift: 6, //动画类型
                                           offset:'100px'
                                        });
                                      }
                                    }
                                });
								/* $(ele).parent("li").find("div").eq(1).show(); //显示叉
								 layer.close(index); */

                                $(ele).parents("li").find("p").show(); //显示叉
                                layer.close(index);
                            }else{
                                layer.msg('不能为空！', {offset:'100px'});
                            }
                        });
                }
            }

			//下一步
            function nextStep(){
                var action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
                $("#form_id").attr("action",action);
                $("#form_id").submit();
            }
            //上一步
			/* function lastStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}  */
            function lastStep(){
              var action = "${pageContext.request.contextPath}/supplierAudit/toPageAptitude.html";
              $("#form_id").attr("action",action);
              $("#form_id").submit();
            } 

		  //文件下載
		  function downloadFile(fileName) {
		    $("input[name='fileName']").val(fileName);
		    $("#download_form_id").submit();
		  }

		  //删除左右两端的空格
			function trim(str){
				return str.replace(/(^\s*)|(\s*$)/g, "");
			}
			
			//暂存
        function zhancun(){
         var supplierId = $("#supplierId").val();
          $.ajax({
            url: "${pageContext.request.contextPath}/supplierAudit/temporaryAudit.do",
            dataType: "json",
            data:{supplierId : supplierId},
            success : function (result) {
                layer.msg(result, {offset : [ '100px' ]});
            },error : function(){
              layer.msg("暂存失败", {offset : [ '100px' ]});
            }
          });
        }
		</script>
		<script type="text/javascript">
			  /* function jump(str){
			  var action;
			  if(str=="essential"){
			     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
			  }
			  if(str=="financial"){
			    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
			  }
			  if(str=="shareholder"){
			    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
			  }
			  /*if(str=="materialProduction"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
			  }
			  if(str=="materialSales"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
			  }
			  if(str=="engineering"){
			    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
			  }
			  if(str=="serviceInformation"){
			    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
			  }* /
			  if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
			  if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
			  if(str=="contract"){
			    action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
			  }
			  if(str=="applicationForm"){
			    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  }
			  if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
			   }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			  } */
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
        <div class="content ">
          <div class="col-md-12 tab-v2 job-content">
			  <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%>
            <form id="form_id" action="" method="post" >
                <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                <input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form>

            <ul class="count_flow ul_list hand">
              <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierLevel');" >军队供应商分级方法：</span>
                <up:show showId="lvel_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLevel}"/>
                <p class="b f18 ml10 red">×</p>
              </li> --%>
              	<li class="col-md-6 mt10 mb25" >
	              <span <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierPledge)}">style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onclick="reason1(this,'supplierPledge');" >供应商承诺书：</span>
	                <u:show showId="pledge_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}"/>
	                <p class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>

	                <c:if test="${fn:contains(passedField,'supplierPledge')}">
	                 <a class='abolish'>
	                   <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
	                 </a>
					</c:if>
              	</li>
              
	            <li class="col-md-6 p0 mt10 mb25">
                <span <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierRegList)}">style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onclick="reason1(this,'supplierRegList');" >供应商申请表：</span>
                <u:show showId="regList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}"/>
                <p class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                <c:if test="${fn:contains(passedField,'supplierRegList')}">
                  <a class='abolish'>
					 <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
					</a>
				</c:if>
	            </li>
	            
	            <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierInspectList');" >军队供应商实地考察记录表：</span>
                <up:show showId="inspectList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierReviewList');" >军队供应商考察廉政意见函：</span>
                <up:show showId="reviewList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReviewList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierChangeList');" >军队供应商注册变更申请表：</span>
                <up:show showId="changeList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierChangeList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierExitList');" >军队供应商退库申请表：</span>
                <up:show showId="exitList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierExitList}"/>
                <p class="b f18 ml10 red">×</p>
	             </li> --%>
             </ul>
           </div>
	         <div class="col-md-12 add_regist tc">
	           <a class="btn"  type="button" onclick="lastStep();">上一步</a>
	           <c:if test="${supplierStatus == -2 or supplierStatus == 0 or supplierStatus ==4 or (sign ==3 and supplierStatus ==5)}">
	             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
	           </c:if>
	           <a class="btn"  type="button" onclick="nextStep();">下一步</a>
	         </div>
         </div>
       </div>
     <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
       <input type="hidden" name="fileName" />
     </form>
  </body>
</html>
