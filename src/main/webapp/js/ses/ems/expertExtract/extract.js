$(function() {
	
	//加载地区树
	loadAreaZtree();
	//动态加载专家类别
	loadExpertKind();
	
	//加载审核人员
	for ( var i = 0; i < 2; i++) {
		addPerson($("#eu"));
		addPerson($("#su"));
	}
});

(function($){  
    $.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    };  
})(jQuery); 

// 加载地区
function functionArea() {
	var parentId = $("#province").val();
	$.ajax({
		url : globalPath + "/area/find_by_parent_id.do",
		data : {
			"id" : parentId
		},
		dataType : "json",
		async : false,
		success : function(response) {
			$("#city").empty();
			$("#city").append("<option value=''>选择地区</option>");
			$.each(response, function(i, result) {
				$("#city").append("<option value='" + result.id + "'>" + result.name + "</option>");
			});
		}
	});
}


//人工抽取
function artificial_extracting(){
	//项目信息
	var proRuestl_1 = $("#form").serializeJson();//数据序列化
	var param1 = $("#condition_form").serializeJson();
	var code = $("#expertKind option:selected").val();
	if(code.indexOf(",") >= 0){
		var strs = new Array(); //定义一数组 
		strs = code.split(","); //字符分割
		if(strs.length == 2){
			var param2 = $("#"+strs[0]+"_form").serializeJson();
			var param3 = $("#"+strs[1]+"_form").serializeJson();
			result = $.extend({},param2,param3,param1,proRuestl_1);
		}
	}else{
		var param2 = $("#"+code+"_form").serializeJson();
		result = $.extend({},param2,param1,proRuestl_1);
	}
	$.ajax({
		url : globalPath + "/extractExpert/saveProjectInfo.do",
		data : result,
		dataType : "json",
		async : false,
		type : "POST",
		success : function(data) {
			for(var key in data){
				if(data[key] != null && data[key].length > 0){
					addTr(key,data[key][1]);
				}
			}
			//点击抽取之后设置条件页面不可再操作
			$("#artificial").attr("disabled",true);
			$("#auto").attr("disabled",true);
			$("#reset").attr("disabled",true);
			$("#div_1").find("input").attr("disabled",true);
			$("#div_1 select").attr("disabled",true);
			$("#div_2 ").find("input").attr("disabled",true);
			$("#div_2 select").attr("disabled",true);
			$("#div_3").find("input").attr("disabled",true);
			$("#div_3 select").attr("disabled",true);
			$("#result").removeClass("display-none");
		},
		error: function () {
            layer.msg("操作失败", {offset: '100px'});
        }
	});
}
var index = 1;
function addTr(code,data){
	/*$("#"+code+"_result").find("tbody").find("tr").each(function(){
		$(this).remove();
	});*/
	var info = "<tr>" +
	"<td class='w50 tc'>"+index+"</td>" +
	"<input value='"+coUndifined(data.id)+"'type='hidden'>" +
	"<td>"+coUndifined(data.relName)+"</td>" +
	"<td>"+coUndifined(data.mobile)+"</td>" +
	"<td>"+coUndifined(data.expertsTypeId)+"</td>" +
	"<td>"+coUndifined(data.workUnit)+"</td>" +
	"<td>"+coUndifined(data.professTechTitles)+"</td>" +
	"<td>"+coUndifined(data.professional)+"</td>" +
	"<td>"+coUndifined(data.remarks)+"</td>" +
	"<td><select class='col-md-12 col-sm-12 col-xs-12 p0'>" +
		"<option value='0'>请选择</option>" +
		"<option value='1'>参加</option>" +
		"<option value='2'>待定</option>" +
		"<option value='3'>不能参加</option>" +
		"</select>" +
	"</td></tr>";
	$("#"+code+"_result").find("tbody").append(info);
	index ++;
}

function coUndifined(v){
	if (typeof (v) == "undefined") {
		return "";
	} else {
		return v;
	}
}
//添加人员信息
/*function addPerson(k){
	if(k == 1){
		//添加抽取人员
		var i = parseInt($("#extractPerson").find('tr').last().children("td").eq(1).text()) + 1;
		var info = "<tr>"+
		"<td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem1' value='' /></td>"+
		"<td class='tc'>"+i+"</td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"</tr>";
		$("#extractPerson").append(info);
	}else if(k == 2){
		//添加监督人员
		var i = parseInt($("#supervisesPerson").find('tr').last().children("td").eq(1).text()) + 1;
		var info = "<tr>"+
		"<td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem2' value='' /></td>"+
		"<td class='tc'>"+i+"</td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"</tr>";
		$("#supervisesPerson").append(info);
	}
}

//删除人员信息
function deletePerson(k){
	layer.confirm('您确定要删除吗?', {
		title: '提示',
		offset: ['222px', '360px'],
		shade: 0.01
	}, function(index) {
		layer.close(index);
		var count = 0;
		var value = 0;
		$('input:checkbox[name=chkItem'+k+']:checked').each(function(){
			if(count == 0){
				value = parseInt($(this).parent().next().text());
			}
			count ++;
			$(this).parent().parent().remove();
		});
		if(count != 0){
			var id = "extractPerson";
			if(k == 2){
				id = "supervisesPerson";
			}
			$("#"+id).find('tr').each(function(t){
				if(parseInt($("#"+id).find('tr').eq(t).children("td").eq(1).text()) > value){
					$("#"+id).find('tr').eq(t).children("td").eq(1).text(value);
					value ++;
				}
			});
		}else{
			layer.msg("请选择人员");
		}
	});
}*/


//加载专家类别
function loadExpertKind(){
	var id = $("#projectType option:selected").val();
	$.ajax({
		url : globalPath + "/extractExpert/loadExpertKind.do",
		data : {
			id : id
		},
		dataType : "json",
		async : false,
		success : function(data) {
			$("#GOODS").addClass("display-none");
			$("#GOODS_SERVER").addClass("display-none");
			$("#PROJECT").addClass("display-none");
			$("#GOODS_PROJECT").addClass("display-none");
			$("#SERVICE").addClass("display-none");
			$("#expertKind").empty();
			var va = "";
			for(var i = 0; i < data.length; i++){
				if(i == 0){
					va += data[i].code;
				}else{
					va += ","+data[i].code;
				}
        	}
			$("#expertKind").append("<option value='"+va+"'>不限</option>");
			for(var i = 0; i < data.length; i++){
				$("#expertKind").append("<option value="+data[i].code+">"+data[i].name+"</option>");
				$("#"+data[i].code).removeClass("display-none");
        	}
		},
		error: function () {
            
        }
	});
	//计算符合条件的人数
	getCount();
}


//加载抽取条件产品类别
function changeKind(){
	var code = $("#expertKind option:selected").val();
	if(code.indexOf(",") >= 0){
		loadExpertKind();
	}else{
		$("#GOODS").addClass("display-none");
		$("#GOODS_SERVER").addClass("display-none");
		$("#PROJECT").addClass("display-none");
		$("#GOODS_PROJECT").addClass("display-none");
		$("#SERVICE").addClass("display-none");
		$("#"+code).removeClass("display-none");
	}
	getCount();
}

//查询符合条件的专家数量
function getCount(cate){
	var param1 = $("#condition_form").serializeJson();
	var code = $("#expertKind option:selected").val();
	if(code.indexOf(",") >= 0){
		cateCode = $(cate).attr("typeCode");
		var strs = new Array(); //定义一数组 
		strs = code.split(","); //字符分割
		if(strs.length == 2){
			var param2 = $("#"+strs[0]+"_form").serializeJson();
			var param3 = $("#"+strs[1]+"_form").serializeJson();
			result = $.extend({},param2,param3,param1);
		}
	}else{
		var param2 = $("#"+code+"_form").serializeJson();
		result = $.extend({},param2,param1);
	}
	$.ajax({
		url : globalPath + "/extractExpert/getCount.do",
		data : result,
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			for(var key in data){
				$("#"+key+"_count").text(data[key].length);
			}
		},
		error: function () {
            
        }
	});
}


/**展示品目*/
function opens(cate) {
	var typeCode = $(cate).attr("typeCode");
	//获取类别
	cate.value = "";
    //  iframe层
    var iframeWin;
    layer.open({
        type: 2,
        title: "选择条件",
        shadeClose: true,
        shade: 0.01,
        area: ['430px', '400px'],
        offset: '20px',
        content: globalPath+'/extractExpert/addHeading.do?type='+typeCode, //iframe的url
        //content: globalPath+'/supplier/category_type.do?code='+supplierCode, 
        success: function (layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
        },
        btn: ['保存', '重置']
        , yes: function () {
            iframeWin.getChildren(cate);
            getCount(cate);
            //initTypeLevelId();
            //selectLikeSupplier();
        }
        , btn2: function () {
            opens();
        }
    });
}


/**展示地区*/
//加载地区树形结构
function loadAreaZtree(){
	var treeNodes; 
	 var setting = {
      async: {
        autoParam: ["id=area"],
        enable: true, 
        url: globalPath+"/SupplierExtracts/city.do",
        dataType: "json",
        type: "post",
      },
      check: {
        enable: true,
        chkboxType: {
          "Y": "s",
          "N": "ps"
        },
        chkStyle : "checkbox" 
       // autoCheckTrigger: true
      },
      data: {
        simpleData: {
          enable: true,
          idKey: "id",
          pIdKey: "parentId"
        },
        key: {
			children: "nodes"
		}
      },
      callback: {
           // beforeCheck: beforeClickArea,
            onCheck: choseArea,
            onAsyncSuccess:selectAllArea
      },
      view: {
            dblClickExpand: false
      }        
    };
    treeArea = $.fn.zTree.init($("#treeArea"), setting, treeNodes);
}

//显示地区树
function showTree(){
	var areaObj = $("#area");
    var areaOffset = $("#area").offset();
    $("#areaContent").css({
        left: areaOffset.left + "px",
        top: areaOffset.top + areaObj.outerHeight() + "px"
    }).slideDown("fast");
     $("body").bind("mousedown", onBodyDownArea);
}

//默认选中全国
function selectAllArea(){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	treeObj.checkAllNodes(true);
	showCheckArea(treeObj);
	
}

//地区树选中处理
function showCheckArea(treeObj){
	var areas=treeObj.getCheckedNodes(true);
    //省，直辖市
   	var pids = "";
   	//二级 市 区
   	var ids = "";
   	var idArr = new Array();
   	var names = "";
   	
   	for(var i=0; i<areas.length;i++){
   		if(areas[i].isParent){
			pids += areas[i].id + ",";
			names += areas[i].name + ",";
			idArr.push(areas[i].id);
			if(areas[i].id == "0"){
				break;
			}
   		}else{
   			var flag = true;
   			
   			for(var v=0;v<idArr.length;v++){
   				if(areas[i].parentId == idArr[v]){
   					flag = false;
   					break;
   				}
			}
   			
   			if(flag){
   				ids += areas[i].id + ",";
   				names += areas[i].name + ",";
   			}
   		}
   	}
	$("#provincesel").val(pids.substring(0,pids.lastIndexOf(",")));
	$("#addressId").val(ids.substring(0,ids.lastIndexOf(",")));
	$("#area").val(names.substring(0,names.lastIndexOf(",")));
	//判断全国  隐藏限制理由输入框
	if($("#provincesel").val() == 0){
		$("#addressReason").val("");
		$("#addressReason").addClass("display-none");
	}else{
		$("#addressReason").removeClass("display-none");
	}
}

//递归取消父节点选中状态
function dischecked(treeNode,treeObj){
	var node = treeNode.getParentNode();
	if(null !=node){
		treeObj.checkNode(node, false);
		dischecked(node,treeObj);
	}
}
//获取选中节点地区
function choseArea(event,treeId,treeNode){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	dischecked(treeNode,treeObj);
	showCheckArea(treeObj);
}

//地区树绑定事件
function onBodyDownArea(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#areaContent").length > 0)) {
        hideArea();
    }
}

function hideArea() {
    $("#areaContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownArea);
    getCount();
//    selectLikeSupplier();
}


/**
 * 人员信息操作
 */

//增加
function addPerson(obj){
	var index = $(obj).parents("form").find("tr:last").find("td:eq(1)").html();
	var input = $(obj).parents("form").find("tr:last").find("td:first").find("input").prop("name");//.substring(4,6);//.attr("req");
	var req ;
	if(null==input ||''==input || "undefined"== input){
		req=0;
	}else{
		req = parseInt(input.substring(5,6)) + 1;
	}
	if(null==index ||''==index || "undefined"== index){
		index=0;
	}
	var id = uuid();//生成id
	var tr = "<tr class='inp'><td class='tc'><input type='checkbox' name='list["+req+"].id'  value='"+id+"'><input type='hidden' name='list["+req+"].id'  value='"+id+"'></td><td class='tc'> "+(parseInt(index)+1)+" </td><td class='tc'> <input type='text' name='list["+req+"].name' > </td><td class='tc'> <input type='text' class='w100p' name='list["+req+"].compary' ></td><td class='tc'> <input type='text' name='list["+req+"].duty'></td><td class='tc'> <input type='text' name='list["+req+"].rank'></td></tr>";
	$(obj).parents("form").find("tbody").append(tr);
}

//引用历史人员
function selectHistory(obj){
	//当前是抽取人员还是监督人员
	var personType = $(obj).parents("form").attr("id");
	//弹窗加载人员列表
	var iframeWin;
    layer.open({
        type: 2,
        title: "引用历史人员",
        shadeClose: true,
        shade: 0.01,
        area: ['430px', '400px'],
        offset: '20px',
        content: globalPath+'/'+personType+'/toPeronList.do?personType='+personType, //iframe的url
        success: function (layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
        },
        btn: ['保存']
        , yes: function () {
            iframeWin.chosePerson(obj);
        }
    });
	
}

//删除
function delPerson(obj){
	$(obj).parents("form").find("tbody").find(":checked").each(function(){
		$(this).parents("tr").remove();
	});
	//更新序号
	var i=1;
	$(obj).parents("form").find("tbody").find("tr").each(function(){
		var o = $(this).find("td").eq(1).html(i++);
	});
	/*for ( var i = 1; i <= trs.length; i++) {
		$(trs[i-1]).find("td :eq(1)").html(i);
	}*/
}

//全选全不选
function checkAll(obj){
	$(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
}

//生成uuid
function uuid() {
	  var s = [];
	  var hexDigits = "0123456789abcdef";
	  for (var i = 0; i < 36; i++) {
	    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	  }
	  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	  var uuid = s.join("");
	  return uuid;
	}
