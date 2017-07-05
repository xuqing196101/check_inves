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
		      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		      pages : "${info.pages}", //总页数
		      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		      skip : true, //是否开启跳页
		      total : "${info.total}",
		      startRow : "${info.startRow}",
		      endRow : "${info.endRow}",
		      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
		      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
		        return "${info.pageNum}";
		      }(),
		      jump : function(e, first) { //触发分页后的回调
		    	if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		location.href = "${pageContext.request.contextPath}/cacheManage/cachemanage.html?page=" + e.curr;
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
      
      
   // 清空选中的索引库
  	function clearSignalIndex(){
  		var id = [];
  		var flag;
  		var num;
  		$('input[name="chkItem"]:checked').each(function() {
  			var arr = $(this).val().split(",");
  			if(arr[1] != '1'){
  				flag = true;
  				num = arr[2];
  				return;
  			}
  			id.push(arr[0]);
  		});
  		if(flag){
				layer.alert("对不起，序号"+num+"未建立索引不能够清除");
  			return;
  		}
  		var ids = id.toString();
  		if(id.length > 0) {
  			layer.confirm('您确定要清空选中的索引库吗?', {
  				title: '提示',
  				shade: 0.01
  			}, function(index) {
  				layer.close(index);
  				$.ajax({
  					url: "${pageContext.request.contextPath}/index/deleteSignalIndex.do",
  					type: "POST",
  					data: {
  						id: ids
  					},
  					success: function(data) {
  						layer.confirm(data.data,{
  							btn:['确定']
  						},function(){
  								$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
  								$("#queryForm").submit();
  							}
  						)
  					},
  					error: function() {

  					}
  				});
  			});
  		} else {
  			layer.alert("请选择要清除索引库的版块", {
  				offset: ['222px', '255px'],
  				shade: 0.01
  			});
  		}
  	}
	      
      //清除首页缓存
      function audit() {
        var id = [];
        var status = "";
        var data = "";
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          data = id[0].split(",");
          status=$(this).parent().next().text();
        });
        if(id.length == 1) {
   			layer.confirm('确认要清除缓存吗？', {
   				btn : [ '是', '否' ]
   			//按钮
   			}, function() {
   				$.ajax({
   				    url: "${pageContext.request.contextPath}/cacheManage/clearStringCache.do?cacheKey="+data[0]+"&&cacheType="+data[1],
   				    type: "POST",
   				    dataType: "json",
   				    success: function(data) {
   				    	// 成功后提示
   				    	layer.confirm(data.data,{
							btn:['确定']
						},function(){
							location.reload();
							}
						)
   				    }
   				});
   			}, function() {
   				layer.close();
   			});
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要删除缓存的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }
      
      // 时间倒计时
      var addTimer = function () {
          var list = [],
              interval;
    
          return function (id, time) {
              if (!interval)
                  interval = setInterval(go, 1000);
              list.push({ ele: document.getElementById(id), time: time });
          }
    
          function go() {
              for (var i = 0; i < list.length; i++) {
                  list[i].ele.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0);
                  if (!list[i].time)
                      list.splice(i--, 1);
              }
          }
    
          function getTimerString(time) {
              var not0 = !!time,
                  d = Math.floor(time / 86400),
                  h = Math.floor((time %= 86400) / 3600),
                  m = Math.floor((time %= 3600) / 60),
                  s = time % 60;
              if (not0)
                  return "<font style='color:red'>还有" + d + "天" + h + "小时" + m + "分" + s + "秒</font>";
              else return "时间到";
          }
      } ();
    
      // 获取对应的值
      function detail(obj){
    	  var types = obj.split(",");
    	  var key = types[0];
    	  var type = types[1];
    		layer.open({
    			type: 2,
    			title: '详情',
    			area: [$(document).width() - 100 +'px', '400px'],
    			content: globalPath+'/cacheManage/getValueByKey.html?cacheKey=' + key +"&cacheType=" + type
    		});
    	}
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
            <li><a>信息服务</a></li>
            <li><a>门户管理</a></li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/cacheManage/cachemanage.html')">缓存管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>缓存列表</h2>
      </div>


      <input type="hidden" id="depid" name="depid">

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows edit" type="button" onclick="audit()">清空缓存</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="info w50">序号</th>
              <th class="info" width="40%">缓存名称</th>
              <th class="info" width="30%">缓存类型</th>
              <th class="info">有效时长</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="cache" varStatus="vs">
           	<tr>
		        <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${cache.name},${cache.type}" /></td>
           		<td class="tc">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
	          	<td class="tl">
	          		<a href="javascript:;" onclick="detail('${cache.name},${ cache.type }')">${cache.name}</a>
	          	</td>
	          	<td class="tl">${cache.type}</td>
	          	<td class="tc" id=${ cache.name }>
	          		<c:if test="${cache.time == -1}">
	          			无
	          		</c:if>
	          		<c:if test="${cache.time != -1}">
	          			<script type="text/javascript">
	          			 	addTimer('${cache.name}','${ cache.time }');
	          			</script>
	          		</c:if>
	          	</td>
           	</tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>