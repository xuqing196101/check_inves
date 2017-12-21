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
                 window.location.href = "${ pageContext.request.contextPath }/article/auditlist.html?page=" + e.curr + "&articleTypeId=" + articleTypeId + "&range=" + range + "&status=" + status + "&name=" + name +"&publishStartDate="+startDate+"&publishEndDate="+endDate+"&secondArticleTypeId="+secondArticleTypeId;
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
   		var authType = "${authType}";
   		if(authType != '4'){
   			layer.msg("只有资源服务中心才能操作");
   			return;
   		}
      	var status = $("#status").val();
      	var curpage = "${list.pageNum}";
      	var articleTypeId = $("#articleTypes").val();
      	var range = $("#range").val();
      	var title = $("#name").val();  
      	var endDate = $("#endDate").val();
      	var startDate = $("#startDate").val();
      	var secondArticleTypeId = $("#secondType").val();
        window.location.href = "${pageContext.request.contextPath }/article/showaudit.html?id="+id+"&status="+status+"&curpage="+curpage+"&articleTypeId="+articleTypeId+"&range="+range+"&title="+title+"&startDate="+startDate+"&endDate="+endDate+"&secondArticleTypeId="+secondArticleTypeId;
      }

      function audit() {
   		var authType = "${authType}";
   		if(authType != '4'){
   			layer.msg("只有资源服务中心才能操作");
   			return;
   		}
        var id = [];
        var status = "";
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          status=$(this).parent().next().text();
        });
        if(id.length == 1) {
          if(status=='1' || status=='4') {
            window.location.href = "${pageContext.request.contextPath }/article/auditInfo.html?id=" + id;
          } else if(status=='2'){
            layer.alert("请选择待发布或取消发布的信息", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });
          }
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要审核的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      $(function() {
   		var authType = "${authType}";
   		if(authType != '4'){
   			return;
   		}
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
            	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "采购公告") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "中标公示") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "单一来源公示") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "商城竞价公告") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "网上竞价公告") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "采购法规") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else if(typeId == "处罚公告") {
              	$("#second").show();
              	//$("#publish_status").attr("class","mt5");
            } else {
				//$("#publish_status").attr("class","");
	  			//$("#audit_date").attr("class","mt5");
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
	  	//$("#publish_status").attr("class","mt5");
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
         	  //$("#publish_status").attr("class","");
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
        $("#status").val("1");
      }

      $(function() {
        $("#articleTypes").select2("val", "${articlesArticleTypeId}");
        /* $("#range").val("${articlesRange}");
        $("#status").val("${articlesStatus}"); */
      })

      function edit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath }/article/auditEdit.html?id=" + id;
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要修改的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }
      
      function quxiaoaudit() {
   		var authType = "${authType}";
   		if(authType != '4'){
   			layer.msg("只有资源服务中心才能操作");
   			return;
   		}
          var applys = [];
          var ids = [];
          var flag = true;
          $('input[name="chkItem"]:checked').each(function() {
            ids.push($(this).val());
            applys.push($(this).parent().next().text());
          });
          if(ids.length > 0) {
        	for(var i=0;i<applys.length;i++){
        		if(applys[i]!='2'){
        			flag=false;
        		}
        	}
        	if(flag){
            layer.confirm('您确定要取消发布吗?', {
              title: '提示',
              offset: ['222px', '360px'],
              shade: 0.01
            }, function(index) {
              layer.close(index);
              window.location.href = "${pageContext.request.contextPath }/article/withdraw.html?ids=" + ids;
            });
        	}else{
        		layer.alert("只可取消已发布的信息", {
                    offset: ['222px', '390px'],
                    shade: 0.01
                  });
        	}

          } else {
            layer.alert("请选择要取消发布的信息", {
              offset: '222px',
              shade: 0.01
            });
          }
        }
        
        
        function del(){
        	var authType = "${authType}";
        	if(authType != '4'){
        		layer.msg("只有资源服务中心才能操作");
        		return;
        	}
        	var ids = [];
	        var status = [];
	        var flag = true;
	        $('input[name="chkItem"]:checked').each(function() {
	          ids.push($(this).val());
	          status.push($(this).parent().next().text());
	        });
	        if(ids.length > 0) {
	        for(var i=0;i<status.length;i++){
	        	if(status[i]=='2'){
	        		flag=false;
	        	}
	        }
	        if(flag){
	          layer.confirm('您确定要删除吗?', {
	            title: '提示',
	            offset: ['222px', '360px'],
	            shade: 0.01
	          }, function(index) {
	            layer.close(index);
	            window.location.href = "${ pageContext.request.contextPath }/article/auditDelete.html?ids=" + ids;
	          });
	        }else{
	        	layer.alert("不可删除已发布的信息", {
	                offset: ['180px', '200px'],
	                shade: 0.01,
	              });
	        }
	        } else {
	          layer.alert("请选择要删除的信息", {
	            offset: ['222px', '390px'],
	            shade: 0.01
	          });
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
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li>
            <a  href="javascript:void(0)">门户管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/article/auditlist.html?status=1')">信息审核</a>
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
      <form id="form1" action="${pageContext.request.contextPath }/article/auditlist.html" method="post" class="mb0">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">信息标题：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" id="name" name="name" maxlength="200" value="${articleName}" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">信息栏目：</div>
            <div class="col-xs-8 f0 lh0">
              <select id="articleTypes" name="articleTypeId" class="w100p h32 f14" onchange="typeInfo()"></select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">发布范围：</div>
            <div class="col-xs-8 f0 lh0">
              <select id ="range" name="range" class="w100p h32 f14">
                <option value="" <c:if test="${articlesRange == ''}">selected</c:if>>全部</option>
                <option value="0" <c:if test="${articlesRange == '0'}">selected</c:if>>内网</option>
                <option value="2" <c:if test="${articlesRange == '2'}">selected</c:if>>内外网</option>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
            <div class="col-xs-8 f0 lh0">
              <select id ="status" name="status" class="w100p h32 f14">
                <option value="" <c:if test="${articlesStatus == ''}">selected</c:if>>全部</option>
                <option value="1" <c:if test="${articlesStatus == '1'}">selected</c:if>>待发布</option>
                <option value="2" <c:if test="${articlesStatus == '2'}">selected</c:if>>已发布</option>
                <option value="4" <c:if test="${articlesStatus == '4'}">selected</c:if>>已取消发布</option>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10" id="audit_date">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核开始时间：</div>
            <div class="col-xs-8 f0 lh0">
              <input id="startDate" name="publishStartDate" class="Wdate w100p h32 f14 mb0" type="text"  value='<fmt:formatDate value="${publishStartDate}" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})">
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10" id="audit_date">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">审核结束时间：</div>
            <div class="col-xs-8 f0 lh0">
              <input id="endDate" name="publishEndDate" value='<fmt:formatDate value="${publishEndDate}" pattern="YYYY-MM-dd"/>' class="Wdate w100p h32 f14 mb0" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
            </div>
          </div>
        </div>
        
        <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10 hide" id="second">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">栏目属性：</div>
            <div class="col-xs-8 f0 lh0">
              <select id="secondType" name="secondArticleTypeId" class="w100p h32 f14"></select>
            </div>
          </div>
        </div>
      </div>
      </div>
      
      <div class="tc">
        <button type="submit" class="btn mb0">查询</button>
        <button type="button" class="btn mb0 mr0" onclick="resetQuery()">重置</button>
      </div>
      </form>
      </div>

      <input type="hidden" id="depid" name="depid">

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows check" type="button" onclick="audit()">审核</button>
        <button class="btn btn-windows edit" type="button" onclick="del()">删除</button>
        <button class="btn" type="button" onclick="quxiaoaudit()">取消发布</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="w50">序号</th>
              <th>信息标题</th>
              <th class="w80">发布范围</th>
              <th class="w80">发布人</th>
              <th class="w80">发布单位</th>
              <!-- <th class="info">提交时间</th> -->
              <th class="w80">审核时间</th>
              <th class="w80">信息栏目</th>
              <th class="w80">发布状态</th>
              <th class="w80">发布依据</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="article" varStatus="vs">
            <tr class="pointer">
              <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
              <td class="tnone">${article.status }</td>
              <td class="tc" onclick="view('${article.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <c:if test="${fn:length(article.name)>26}">
                <td class="tl text-nowrapEl" onclick="view('${article.id }')" <%-- onmouseover="titleMouseOver('${article.name}',this)" onmouseout="titleMouseOut()" --%> title="${article.name}">${fn:substring(article.name,0,26)}...</td>
              </c:if>
              <c:if test="${fn:length(article.name)<=26}">
                <td class="tl text-nowrapEl" onclick="view('${article.id }')" title="${article.name}">${article.name }</td>
              </c:if>
              <td class="tc text-nowrapEl" onclick="view('${article.id }')">
                <c:if test="${article.range=='0' }">
                  内网
                </c:if>
                <c:if test="${article.range=='2' }">
                  内外网
                </c:if>
              </td>
              <td class="tl text-nowrapEl">
                 ${article.user.relName }
              </td>
              <td class="tl text-nowrapEl" >
                <c:if test="${user.org != null && user.org.shortName != null }">
                    ${article.user.org.shortName}
                </c:if>
                <c:if test="${user.org == null }">${article.user.orgName}</c:if>
              </td>
              <%-- <td class="tc" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.submitAt }' pattern="yyyy-MM-dd   HH:mm:ss" />
              </td> --%>
              <td class="tc text-nowrapEl" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.publishedAt }' pattern="yyyy-MM-dd HH:mm:ss" />
              </td>
              <td class="tc text-nowrapEl" onclick="view('${article.id }')">${article.articleType.name }</td>
              <td class="tc text-nowrapEl">
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