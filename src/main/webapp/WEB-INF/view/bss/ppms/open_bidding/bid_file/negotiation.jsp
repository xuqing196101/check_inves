<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">

    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>

    <script type="text/javascript">
      $(function() {
        var index = 0 ;
         var divObj = $(".p0" + index);
              $(divObj).removeClass("hide");
              $("#package").removeClass("shrink");        
              $("#package").addClass("spread");
        
      });
      /** 保存  **/
	    function save(id){
	      layer.confirm('您确定要保存吗?', {
        title: '提示',
        offset: ['30%', '40%'],
        shade: 0.01
        }, function(index) {
	        layer.close(index); 
		      var projectId = $("#projectId").val();
		      var createdAt = $("#createdAt").val();
		      var nuter = $("#nuter").val();
		      var net = $("#negotiationRecord").val();
		      var negId = $("#negId").val();
		      var uuId = $("#uuId").val();
		      $.ajax({
	          url : "${pageContext.request.contextPath}/open_bidding/saveNet.html?projectId="+projectId+"&createdAt="+createdAt+"&nuter="+nuter+"&net="+net+"&negId="+negId+"&uuId="+uuId+"&packageId="+id,
	          type : "post",
	          dataType : "json",
	          success : function(result) {
	           if(result == "1"){
	             layer.msg("保存成功", {
               });
	           }
	           if(result == "2"){
               layer.msg("修改成功", {
               });
             }
	          }
	        });
	      });
	    }
	    
	    /** 导出  **/
	    function educe(){
	       var projectId = $("#projectId").val();
         var createdAt = $("#createdAt").val();
         var nuter = $("#nuter").val();
         var net = $("#negotiationRecord").val();
	       window.location.href = "${pageContext.request.contextPath}/open_bidding/educe.html?projectId="+projectId+"&createdAt="+createdAt+"&nuter="+nuter+"&net="+net;
	    }
	    
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
    </script>
  </head>

  <body>
    <div class="container">
      <div class="tab-content mt10">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li class="active">
              <a href="#dep_tab-0" data-toggle="tab" class="f18">谈判记录</a>
            </li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
              <c:forEach items="${listResultExpert }" var="list" varStatus="vs">
                <c:set value="${vs.index}" var="index"></c:set>
                <div>
                  <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">包名:<span class="f14 blue">${listResultExpert[index].name }</span></h2>
                </div>
                <div class="p0${index} hide">
                <table class="table table-bordered left_table">
                  <tbody>
                    
                    <tr>
                      <td class="bggrey" colspan="2">项目编号:<input type="hidden" id="projectId" value="${project.id}"/></td>
                      <td class="p0"><input name="projectNumber" class="m0" id="projectNumber" value="${project.projectNumber}" type="text" class="m0" /><input type="hidden" name="id" id="id" value="${project.id}" /></td>
                      <td class="bggrey" >项目名称:</td>
                      <td class="p0"><input name="name" class="m0" id="name" value="${project.name}" type="text" /><input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="2">时间:</td>
                      <td class="p0"><input readonly="readonly"  value="<fmt:formatDate type='date' value='${list.negotiation.createdAt }'  pattern=" yyyy-MM-dd HH:mm:ss "/>" name="createdAt" id="createdAt" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" /></td>
                      <td class="bggrey">地点:</td>
                      <td class="p0"><input name="bidAddress" id="bidAddress" value="${project.bidAddress}" type="text" class="m0" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="2">记录人:</td>
                      <td class="p0" colspan="3"><input name="nuter" id="nuter" value="${list.negotiation.nuter}" type="text" class="m0" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey" colspan="5">
                        <p align="center" class="f22">谈判小组成员</p>
                      </td>
                    </tr>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">专家姓名</th>
                      <th class="info">工作单位</th>
                      <th class="info">职务</th>
                      <th class="info">备注</th>
                    </tr>
                    <c:forEach items="${list.listProjectExtract}" var="listyes" varStatus="vs">
                      <tr>
                        <td class='tc'>${vs.index+1}</td>
                        <td class='tc'>${listyes.expert.relName}</td>
                        <td class='tc'>${listyes.expert.workUnit}</td>
                        <td class='tc'>${listyes.expert.professTechTitles}</td>
                        <td class='tc'>${listyes.expert.remarks}</td>
                      </tr>
                    </c:forEach> 
                    <tr>
                      <td class="bggrey" colspan="5">
                        <p align="center" class="f22">记录内容</p>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="6"><textarea class="col-md-12 col-sm-12 col-xs-12" name="negotiationRecord" id="negotiationRecord" style="height:330px" title="不超过800个字">${list.negotiation.negotiationRecord}</textarea></td>
                    </tr>
                  </tbody>
                </table>
                <div class="col-md-12 tc mt20">
                  <button class="btn btn-windows git" type="button" onclick="save('${list.id}');">保存</button>
                  <button class="btn btn-windows input" type="button" onclick="educe()">导出</button>
                  <%-- <c:choose>
									   <c:when test="${negotiation.id != null}">  
									     <u:upload id="upload1"  auto="true"  businessId="${negotiation.id}" typeId="${dataId}" sysKey="2" />
                       <u:show showId="upload12"  businessId="${negotiation.id}" sysKey="2" typeId="${dataId}" />    
									   </c:when>
									   <c:otherwise> 
									     <u:upload id="upload3"  auto="true"  businessId="${uuId}" typeId="${dataId}" sysKey="2" />
                       <u:show showId="upload33"  businessId="${uuId}" sysKey="2" typeId="${dataId}" />
									   </c:otherwise>
									</c:choose> --%>
                </div>
                </div>
                </c:forEach>
                
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>