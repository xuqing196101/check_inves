<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
					$(":button").each(function(){ 
						$(this).attr("disabled",true);
	        }); 
				}
    })
	function removeSupplier(supplierId, packageId){
		var projectId = "${projectId}";
		var removedReason = layer.prompt({
		    title : '请填写移除的理由：', 
		    formType : 2, 
		    offset : '100px',
		    maxlength: 300,
		},function(text){
			$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/removeSaleTender.do",
				data: {"supplierId": supplierId, "packageId": packageId, "projectId": projectId, "removedReason": text, "removeType" : "0"},
				success: function (response) {
					$("#"+supplierId+"_"+packageId).text('已移除');
					$("#"+supplierId+"_"+packageId).next().children().attr("disabled","disabled");
					layer.msg("移除成功!",{offset: '100px'});
					var path = "${pageContext.request.contextPath}/packageExpert/removeSupplier.html?projectId=" + projectId + "&flowDefineId=${flowDefineId}";
				      $("#tab-9").load(path);
				},
				error: function () {
					layer.msg("抱歉,移除失败!",{offset: ['100px', '350px']});
					layer.close(removedReason);
				}
			});
		});
	}
	
	function removed(id,name,removedReason){
	  $("#removedReason").val(removedReason);
	  layer.open({
          type: 1, //page层
          area: ['300px', '300px'],
          title: id+name+'移除理由',
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          shadeClose: true,
          content: $("#file")
        });
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
      
      $(function() {
        var index=0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#packageIds").removeClass("shrink");        
        $("#packageIds").addClass("spread");
      });
  </script>
  <body>
	    <h2 class="list_title">合格供应商</h2>
   		<input type="hidden" id="projectId" value="${projectId}">
			<c:forEach items="${list}" var="pack" varStatus="vs">
				<div class="over_hideen">
					<h2 onclick="ycDiv(this,'${vs.index}')" 
						<c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ' || pack.projectStatus eq 'ZJTSHBTG'}">
							class="count_flow hand fl spread"</c:if>class="count_flow shrink hand fl clear" id="packageIds">包名:<span class="f15 blue">${pack.name}</span>
						<c:if test="${pack.projectStatus eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJTSHBTG'}"><span class="star_red">[该包转竞谈审核不通过]</span></c:if>
          </h2>
          <c:if test="${pack.projectStatus ne 'YZZ' || pack.projectStatus ne 'ZJZXTP' || pack.projectStatus ne 'ZJTSHZ' || pack.projectStatus ne 'ZJTSHBTG'}">
          	<div class="p0${vs.index} hide">
          		<table class="table table-bordered table-condensed table-hover table-striped">
									<thead>
										<tr>
			  							<th class="w50 info">序号</th>
			  							<th class="info w500">供应商名称</th>
			  							<th class="info">状态</th>
			  							<th class="info">操作</th>
			  							<th class="info">备注</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pack.saleTenderList}" var="supp" varStatus="v">
											<c:if test="${(supp.isRemoved ne '1' and supp.removeType eq null) || (supp.isRemoved eq '1' and supp.removeType == 0)}">
										<tr>
					    				<td class="tc w50">${v.index+1}</td>
					    				<td class="tc">${supp.suppliers.supplierName}</td>
					    				<td class="tc" id="${supp.suppliers.id}_${supp.packages}">
					    					<c:if test="${pack.status eq null || pack.status == 0}">符合性和资格性检查未开始</c:if>
							          <c:if test="${pack.status == 1}">符合性和资格性检查中</c:if>
							          <c:if test="${pack.status == 2}">符合性和资格性检查完成</c:if>
							          <c:if test="${pack.status == 3}">经济技术评审中</c:if>
							          <c:if test="${pack.status == 4}">经济技术评审完成</c:if>
					    					<%-- <c:if test="${supp.isRemoved eq '1'}"><a href="javascript:void(0)" onclick="removed('${pack.name}','${supp.suppliers.supplierName}','${supp.removedReason}')">已移除</a></c:if>
					    					<c:if test="${supp.isRemoved eq '2'}">已放弃报价</c:if> --%>
					    				</td>
					    				<td class="tc"><input <c:if test="${supp.isFinish != 1 || (supp.isRemoved eq '1' and supp.removeType == 0)}">disabled="disabled"</c:if> type="button" value="移除" onclick="removeSupplier('${supp.suppliers.id}','${supp.packages}')" class="btn"></td>
					    				<td class="tc w100" title="${supp.removedReason }">
						    				<c:if test="${fn:length (supp.removedReason) > 8}">${fn:substring(supp.removedReason,0,7)}...</c:if>
	                      <c:if test="${fn:length(supp.removedReason) <= 8}">${supp.removedReason}</c:if>
					    				</td>
					  				</tr>
					  				</c:if>
					  				</c:forEach>
									</tbody>
							</table>
          	</div>
          </c:if>
        </div>
			</c:forEach>
		<div id="file" class="drop_window dnone">
      <ul class="list-unstyled">
        <li class="col-md-12 col-sm-6 col-xs-12">
          <span class="col-md-12 col-sm-12 col-xs-12 p0">
            <textarea style="height: 200px;width: 250px" rows="3" cols="1" id="removedReason" disabled="disabled"></textarea>
          </span>
        </li>
        <div class="clear"></div>
      </ul>
    </div>
  </body>
</html>
