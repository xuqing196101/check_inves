<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pagediv"),
          pages: "${list.pages}",
          skin: '#2c9fA6',
          skip: true,
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}",
          curr: function() {
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) { 
            if(!first) {
            	 var articleTypeId = "${articlesArticleTypeId}";
            	 var secondArticleTypeId = "${secondArticleTypeId}";
	             var range = $("#range").val();
	             var status = $("#status").val();
	             var name = "${articleName}";
	             var startDate = $("#startDate").val();
	             var endDate = $("#endDate").val();
	             var endDate = $("#endDate").val();
	             var publishYear = $("#publishYear").val();
	             var threeArticleTypeId = $("#threeArticleTypeId").val();
	             var categoryId = $("#categoryId").val();
	             var fourArticleTypeId = $("#fourArticleTypeId").val();
                 window.location.href = "${ pageContext.request.contextPath }/article/readOnlyList.html?page=" + e.curr + "&articleTypeId=" + articleTypeId + "&range=" + range + "&status=" + status + "&name=" + name +"&publishStartDate="+startDate+"&publishEndDate="+endDate+"&secondArticleTypeId="+secondArticleTypeId+"&publishYear="+publishYear+"&threeArticleTypeId="+threeArticleTypeId+"&categoryId="+categoryId+"&fourArticleTypeId="+fourArticleTypeId;
            }
          }
        });
      });

      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }

      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
          }
        }
      }

      function view(id) {
      	var status = $("#status").val();
      	var curpage = "${list.pageNum}";
      	var articleTypeId = $("#articleTypes").val();
      	var range = $("#range").val();
      	var title = $("#name").val();  
      	var endDate = $("#endDate").val();
      	var startDate = $("#startDate").val();
      	var startDate = $("#startDate").val();
      	var secondArticleTypeId = $("#secondType").val();
      	var publishYear = $("#publishYear").val();
      	var threeArticleTypeId = $("#threeArticleTypeId").val();
      	var categoryId = $("#categoryId").val();
      	var fourArticleTypeId = $("#fourArticleTypeId").val();
        window.location.href = "${pageContext.request.contextPath }/article/showaudit.html?reqType=analyze&id="+id+"&status="+status+"&curpage="+curpage+"&articleTypeId="+articleTypeId+"&range="+range+"&title="+title+"&startDate="+startDate+"&endDate="+endDate+"&secondArticleTypeId="+secondArticleTypeId+"&publishYear="+publishYear+"&threeArticleTypeId="+threeArticleTypeId+"&categoryId="+categoryId+"&fourArticleTypeId="+fourArticleTypeId;
      }

      $(function() {
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=0",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              /* $("#articleTypes").append("<option></option>"); */
              $("#articleTypes").append("<option value=''>全部</option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#articleTypes").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#articleTypes").select2();
            $("#articleTypes").select2("val", "${article.articleType.id }");
            var typeId = $("#articleTypes").select2("data").text;
            if(typeId == "工作动态") {
            	$("#second").show();
            	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "采购公告") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "中标公示") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "单一来源公示") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "商城竞价公告") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "网上竞价公告") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "采购法规") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else if(typeId == "处罚公告") {
              	$("#second").show();
              	$("#publish_status").attr("class","clear mt5");
	  			$("#audit_date").attr("class","mt5");
            } else {
				$("#publish_status").attr("class","");
	  			$("#audit_date").attr("class","clear mt5");
			}
          }
        });
        
        var parentId = "${articlesArticleTypeId}";
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=" + parentId + "&type=1",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#secondType").append("<option value=''>全部</option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#secondType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#secondType").select2();
            $("#secondType").select2("val", "${secondArticleTypeId}");
          }
        });
      })

	  function typeInfo() {
	  	$("#publish_status").attr("class","clear mt5");
	  	$("#audit_date").attr("class","mt5");
        var typeId = $("#articleTypes").select2("data").text;
        var parentId = $("#articleTypes").select2("val");
        $("#secondType").empty();
        if(typeId == "工作动态") {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
          $("#second").show();
          getSencond(parentId);
        }else if(typeId == "采购公告"){
            $("#second").show();
            getSencond(parentId);
         }else if(typeId == "中标公示"){
             $("#second").show();
             getSencond(parentId);
         }else if(typeId == "单一来源公示"){
             $("#second").show();
             getSencond(parentId);
         }else if(typeId == "商城竞价公告"){
        	  $("#second").show();
        	  getSencond(parentId);
         }else if(typeId == "网上竞价公告"){
              $("#second").show();
              getSencond(parentId);
         }else if(typeId == "采购法规"){
              $("#second").show();
              getSencond(parentId);
         }else if(typeId == "处罚公告"){
              $("#second").show();
              getSencond(parentId);
         }else {
         	  $("#publish_status").attr("class","");
	  		  $("#audit_date").attr("class","clear mt5");
	          $("#second").hide();
	          $("#secondType").empty();
        }
      }

	  function getSencond(parentId){
    	  $("#secondType").empty();
    	  $.ajax({
              contentType: "application/json;charset=UTF-8",
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+parentId+"&type=1",
              type: "POST",
              dataType: "json",
              success: function(articleTypes) {
                if(articleTypes) {
                  $("#secondType").append("<option value=''>全部</option>");
                  $.each(articleTypes, function(i, articleType) {
                    if(articleType.name != null && articleType.name != '') {
                      $("#secondType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                    }
                  });
                }
                $("#secondType").select2();
              }
            });
      }

      function search() {
        var kname = $("#kname").val();
        var parkId = $("#parkId  option:selected").val();
        location.href = "${ pageContext.request.contextPath }/article/serch.html?kname=" + kname;

      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
        $("#articleTypes").select2("val", "");
        $("#secondType").select2("val", "");
        $("#status").val("2");
      }

      $(function() {
        $("#articleTypes").select2("val", "${articlesArticleTypeId}");
      })

      function back(){
    	  window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzePurchaseNotice.html"
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
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li>
            <a  href="javascript:void(0)">门户管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>审核信息列表</h2>
      </div>

      <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath }/article/readOnlyList.html" method="post" class="mb0">
        	<input type="hidden" id="publishYear" name="publishYear" value="${ articleAnalyzeVo.publishYear }" />
        	<input type="hidden" id="status" name="status" value="${ articlesStatus }" />
        	<input type="hidden" id="threeArticleTypeId" name="threeArticleTypeId" value="${ articleAnalyzeVo.threeArticleTypeId }" />
        	<input type="hidden" id="categoryId" name="categoryId" value="${ articleAnalyzeVo.categoryId }" />
        	<input type="hidden" id="fourArticleTypeId" name="fourArticleTypeId" value="${ articleAnalyzeVo.fourArticleTypeId }" />
          <ul class="demand_list">
            <li>
              	<label class="fl">信息标题：</label>
              	<span>
		          <input type="text" id="name" name="name" maxlength="200" value="${articleName}"/>
		        </span>
            </li>
            <li>
              	<label class="fl">信息栏目：</label>
              	<span class="fl">
        	  	<div class="w200">
          			<select id="articleTypes" name="articleTypeId" class="w200" onchange="typeInfo()">
            		</select>
          	  	</div>
              	</span>
            </li>
            <li class="hide" id="second">
              	<label class="fl">栏目属性：</label>
              	<span class="fl">
        	  	<div class="w200">
          			<select id="secondType" name="secondArticleTypeId" class="w200" >
            		</select>
          	  	</div>
              	</span>
            </li>
            <li class="">
              <label class="fl">发布范围：</label>
              <span>
              <select id ="range" name="range" class=""  >
                <option value=""  <c:if test="${articlesRange == ''}">selected</c:if>>全部</option>
                <option value="0" <c:if test="${articlesRange == '0'}">selected</c:if>>内网</option>
                <%-- <option value="1" <c:if test="${articlesRange == '1'}">selected</c:if>>外网</option> --%>
                <option value="2" <c:if test="${articlesRange == '2'}">selected</c:if>>内外网</option>
               </select>
           	   </span>
            </li>
            <li class="clear mt5" id="audit_date">
            	<label class="fl">审核时间：</label>
				<input id="startDate" name="publishStartDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${publishStartDate}" pattern="YYYY-MM-dd"/>'
                onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" />
                <span class="f13">至</span>
                <input id="endDate" name="publishEndDate" value='<fmt:formatDate value="${publishEndDate}" pattern="YYYY-MM-dd"/>' class="Wdate w110" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
          	</li>
          </ul>
            <div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
            <button type="submit" class="btn">查询</button>
            <button type="button" class="btn" onclick="resetQuery()">重置</button>
          </div>
          <div class="clear"></div>
        </form>
      </div>

      <input type="hidden" id="depid" name="depid">

			<div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="info w50">序号</th>
              <th class="info" width="25%">信息标题</th>
              <th class="info" width="8%">发布范围</th>
              <th class="info" width="12%">发布人</th>
              <th class="info" width="12%">发布单位</th>
              <!-- <th class="info">提交时间</th> -->
              <th class="info" width="12%">审核时间</th>
              <th class="info" width="10%">信息栏目</th>
              <th class="info">发布状态</th>
              <th class="info" width="7%">发布依据</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="article" varStatus="vs">
            <tr class="pointer">
              <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
              <td class="tnone">${article.status }</td>
              <td class="tc w50" onclick="view('${article.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <c:if test="${fn:length(article.name)>26}">
                <td class="tl" onclick="view('${article.id }')" <%-- onmouseover="titleMouseOver('${article.name}',this)" onmouseout="titleMouseOut()" --%> title="${article.name}">${fn:substring(article.name,0,26)}...</td>
              </c:if>
              <c:if test="${fn:length(article.name)<=26}">
                <td class="tl" onclick="view('${article.id }')" title="${article.name}">${article.name }</td>
              </c:if>
              <td class="tl" onclick="view('${article.id }')">
                <c:if test="${article.range=='0' }">
                  内网
                </c:if>
                <c:if test="${article.range=='2' }">
                  内外网
                </c:if>
              </td>
              <td class="tl">
                 ${article.user.relName }
              </td>
              <td class="tl" >
                ${article.user.orgName }
              </td>
              <%-- <td class="tc" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.submitAt }' pattern="yyyy-MM-dd   HH:mm:ss" />
              </td> --%>
              <td class="tc" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.publishedAt }' pattern="yyyy-MM-dd HH:mm:ss" />
              </td>
              <td class="tl" onclick="view('${article.id }')">${article.articleType.name }</td>
              <td class="tc">
                <c:if test="${article.status=='1' }">
                  <input type="hidden" name="status" value="${article.status }">待发布
                </c:if>
                <c:if test="${article.status=='2' }">
                  <input type="hidden" name="status" value="${article.status }">已发布
                </c:if>
                <c:if test="${article.status=='3' }">
                  <input type="hidden" name="status" value="${article.status }">已退回
                </c:if>
                <c:if test="${article.status=='4' }">
                  <input type="hidden" name="status" value="${article.status }">已取消发布
                </c:if>
              </td>
              <td class="release">
                <u:show showId="${article.groupShow}" groups="${article.groupsUploadId}" delete="false" businessId="${article.id}" sysKey="${sysKey}" typeId="${secretTypeId }" />
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>