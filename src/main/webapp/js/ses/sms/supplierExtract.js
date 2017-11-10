var successCount = 1;
var proError = 0;
var formData = "";

/**
 * 定义供应商等级
 */
var salesLevel = [{id: "一级", pid: 0, name: "一级"},
                  {id: "二级", pid: 0, name: "二级"},
                  {id: "三级", pid: 0, name: "三级"},
                  {id: "四级", pid: 0, name: "四级"},
                  {id: "五级", pid: 0, name: "五级"}];

var productLevel = [{id: "一级", pid: 0, name: "一级"},
                    {id: "二级", pid: 0, name: "二级"},
                    {id: "三级", pid: 0, name: "三级"},
                    {id: "四级", pid: 0, name: "四级"},
                    {id: "四级", pid: 0, name: "五级"},
                    {id: "四级", pid: 0, name: "六级"},
                    {id: "四级", pid: 0, name: "七级"},
                    {id: "五级", pid: 0, name: "八级"}];
/**
 * 预加载函数
 */
$(function () {
    loadAreaZtree();
    loadSupplierType();
    
	addPerson($("#eu"));
    addPerson($("#su"));
});


/**
 * 按名称搜索资质
 */
function selectQua(){
	if(event.keyCode==32){
		var name = $("#quaName").val();
		if(""!=name && null!=name){
			$.ajax({
				type: "POST",
				url: globalPath+"/SupplierCondition_new/qualificationList.do",
				data:  {name:name,type:4},
				dataType: "json",
				success: function (msg) {
					if(null !=msg){
						loadQuaList(msg.obj);
					}
				}
			});
		}else{
			$("#quaId").val("");
			$("[name='level']").val("");
			$("#level").val("全部等级");
			$("#quaName").val("全部资质");
		}
	}
}

/**
 * 生成UUID
 */ 
function uuid() {
  var s = [];
  var hexDigits = "0123456789abcdef";
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the
  var uuid = s.join("");
  return uuid;
}

/**
 * 项目信息地区选择
 * 
 * @returns
 */
function selectArea(obj){
	var city = "";
	var provinceId = $(obj).val();
	var provinceName = obj.selectedOptions[0].innerText;
	$("[name='businessScope']").val(provinceId);
	$("#businessScope").val(provinceName);
	var typeCode = $("#projectType").val();
	if("PROJECT" == typeCode && obj.id == "constructionPro"){
		selectLikeSupplier();
	}
	if(provinceId == ''){
		$(obj).next().empty();
		$(obj).next().append("<option value='0'>全部</option>");
        return;
	}
	$.ajax({
        type: "POST",
        url: globalPath+"/area/find_by_parent_id.do",
        data: {id: provinceId},
        dataType: "json",
        success: function (data) {
        	$(obj).next().empty();
        	$(obj).next().append("<option value='0'>全部</option>");
        	for(var i=0;i<data.length;i++){
        		city += "<option value="+data[i].id+">"+data[i].name+"</option>";
        	}
        	$(obj).next().append(city);
        }
    });
}


/**
 *  比较售领时间是否输入合理
 */
function checkTime(){
	if(null != $("#sellEnd").val()){
		var startTime = new Date(Date.parse($("#sellBegin").val()));
		var endTime = new Date(Date.parse($("#sellEnd").val()));
		$("#sellBeginTime").val(startTime.getTime());
		$("#sellEndTime").val(endTime.getTime());
		if(startTime>=endTime){
			layer.msg("结束时间不能小于起始时间");
			$("#sellEnd").val("");
		}
	}else{
		layer.msg("请选择售领起始时间");
	}
	
	if(null != $("#sellBegin").val()){
		var startTime = new Date(Date.parse($("#sellBegin").val()));
		var endTime = new Date(Date.parse($("#sellEnd").val()));
		if(startTime>=endTime){
			layer.msg("结束时间不能小于起始时间");
			$("#sellEnd").val("");
		}
	}else{
		layer.msg("请选择售领起始时间");
	}
}


/**
 * 人员信息操作
 */
// 增加
function addPerson(obj){
	var index = $(obj).parents("form").find("tr:last").find("td:eq(1)").html();
	var input = $(obj).parents("form").find("tr:last").find("td:eq(2)").find("input").prop("name");// .substring(4,6);//.attr("req");
	var req ;
	if(null==input ||''==input || "undefined"== input){
		req=0;
	}else{
		req = parseInt(input.substring(5,6)) + 1;
	}
	if(null==index ||''==index || "undefined"== index){
		index=0;
	}
	var id = uuid();// 生成id
	var tr = "<tr class='inp'><td class='tc'><input type='checkbox' name='list["+req+"].id' value='"+id+"'><input type='hidden' name='list["+req+"].id' value='"+id+"'></td><td class='tc'> "+(parseInt(index)+1)+" </td><td class='tc'> <input type='text' name='list["+req+"].name' maxlength='5' class='w100p'> </td><td class='tc'> <input type='text' class='w100p' name='list["+req+"].compary' ></td><td class='tc'> <input type='text' name='list["+req+"].duty' class='w100p'></td><td class='tc'> <input type='text' name='list["+req+"].rank' class='w100p'></td></tr>";
	$(obj).parents("form").find("tbody").append(tr);
}

// 引用历史人员
function selectHistory(obj){
	// 当前是抽取人员还是监督人员
	var personType = $(obj).parents("form").attr("id");
	// 弹窗加载人员列表
	var iframeWin;
    layer.open({
        type: 2,
        title: "引用历史人员",
        shadeClose: true,
        shade: 0.01,
        area: ['600px', '400px'],
        offset: '20px',
        content: globalPath+'/'+personType+'/toPeronList.do?personType='+personType, // iframe的url
        success: function (layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
        },
        btn: ['保存']
        , yes: function () {
            iframeWin.chosePerson(obj);
        }
    });
	
}

// 删除
function delPerson(obj){
	$(obj).parents("form").find("tbody").find(":checked").each(function(){
		$(this).parents("tr").remove();
	});
	// 更新序号
	var i=1;
	$(obj).parents("form").find("tbody").find("tr").each(function(){
		var o = $(this).find("td").eq(1).html(i++);
	});
	
	// 修改inputlist序号
	var j = 0;
	$(obj).parents("form").find("tr").each(function(){
		if($(this).find("input[type='text']").length>0){
			$(this).find("td:eq(0) input[type=hidden]").prop('name', 'list['+j+'].id');
			$(this).find("td:eq(0) input[type=checkbox]").prop('name', 'list['+j+'].id');
			$(this).find("td:eq(2) input[type=text]").prop('name', 'list['+j+'].name');
			$(this).find("td:eq(3) input[type=text]").prop('name', 'list['+j+'].compary');
			$(this).find("td:eq(4) input[type=text]").prop('name', 'list['+j+'].duty');
			$(this).find("td:eq(5) input[type=text]").prop('name', 'list['+j+'].rank');
			j++;
		}
	});
}

// 全选全不选
function checkAll(obj){
	$(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
}


/** 供应商地区 */
function areas() {
    var areas = $("#area").find("option:selected").val();
    $.ajax({
        type: "POST",
        url: globalPath+"/SupplierExtracts_new/city.do",
        data: {area: areas},
        dataType: "json",
        success: function (data) {
            var list = data;
            $("#city").empty();

            var html = "";
            var areas = $("#area").find("option:selected").text();
            if (areas == '全国') {
                html = "<option value='' selected='selected' >所有地区</option>";
            } else {
                layer.prompt({
                    formType: 2,
                    shade: 0.01,
                    skin: 'layer-default',
                    title: '限制地区原因',
                    btn: ['确定', '取消'],
                    cancel: function (index, layero) {
                        $("#area option:first").prop("selected", 'selected');
                        $("#city").empty();
                        $("#city").append("<option value=''>所有省市</option>");
                        selectLikeSupplier();
                        layer.close(index);
                    }
                }, function (value, ix, elem) {
                    $("#addressReason").val(value);
                    selectLikeSupplier();
                    layer.close(ix);
                });
                html = "<option value=''>所有市</option>";

            }
            for (var i = 0; i < list.length; i++) {
                html += "<option value=" + list[i].id + ">" + list[i].name + "</option>";
            }
            $("#city").append(html);
            selectLikeSupplier();
        }
    });

}


/** 满足条件供应商人数查询 */
function selectLikeSupplier() {
    var area = document.getElementById("area").value;
    var typeCode = $("#supplierType").val();
    code = typeCode.toLowerCase();
    $.ajax({
        cache: true,
        type: "POST",
        dataType: "json",
        url: globalPath+'/SupplierCondition_new/selectLikeSupplierCount.do',
        data: $('#form1').serialize(),// 你的formid
        async: false,
        success: function (data) {
        	$("#count").parents("button").prop("style","background-color: red;");
        	if(null!=data && data.count !=null){
        		if(parseInt(data.count)!=0){
        			$("#count").parents("button").removeAttr("style");
        		}
				$("#count").html(data.count);
        	}else{
        		
        	}
        }
    });
    compareExtractNum();
    return false;
}

function checkEmptyAndspace(ele,count){
	if(!ele.value){
		count++;
		$(ele).parents("li").find("#"+ele.name+"Error").html("不能为空");
	}else{
		if(ele.value.split(" ").length>1 && $(ele).attr("id")!="sellBegin" && $(ele).attr("id")!="sellEnd"){
			$(ele).parents("li").find("#"+ele.name+"Error").html("不能包含空格");
			count ++ ;
		}else{
			$(ele).parents("li").find("#"+ele.name+"Error").html("");
		}
	}
	return count;
}


/** 点击抽取--对参数进行校验 */
function checkEmpty(){
	$("#areaError").empty();
	
	var count = 0;
	$(".star_red").each(function(){
		$($(this).parents("li").find("input")).each(function(index, ele){
			$(ele).parents("li").find("#"+ele.name+"Error").html("");
			count = checkEmptyAndspace(ele,count);
		});
		
		$($(this).parents("li").find("select")).each(function(index, ele){
			count = checkEmptyAndspace(ele,count);
		});
	});
	// 限制地区理由是否填写
	if("0"!=$("#province").val() && (null ==$("#areaReson").val() ||""==$("#areaReson").val().replace(/(^\s*)|(\s*$)/g, ""))){
		$("#areaError").html("不能为空");
		count++;
	}else{
		if($("#areaReson").val().split(" ").length>1){
			$("#areaError").html("不能包含空格");
			count++;
		}else{
			$("#areaError").val("");
		}
	}
	
	if(count>0){
		layer.msg("存在错误信息，检查及修改");
	}
	
	// 校验人员信息
	var count1 = 0;
	var count2 = 0;
	var count3 = 0;
	$("#eError").html("");
	$("#sError").html("");
	$("#extractUser").find("input").each(function(){
		if($(this).val().length<1){
			count1++;
		}
	});
	
	
	$("#supervise").find("input").each(function(){
		if($(this).val().length<1){
			count2++;
		}
	});
	
	if($("#extractUser").find("tr").length<2){
		count1++;
	}
	if($("#supervise").find("tr").length<2){
		count2++;
	}
	if(count1>0){
		$("#eError").html("抽取人员必须两人以上且信息必须填写完整");
		layer.msg("抽取人员必须两人以上且信息必须填写完整");
	}
	if(count2>0){
		$("#sError").html("监督人员必填且必须填写完整");
		layer.msg("监督人员必填且必须填写完整");
	}
	
	$("#contactNumError").html("");  
	// 校验手机
	var phone = $("[name='contactNum']").val();
    if(!phone){ 
        $("#contactNumError").html("不能为空");  
        count3++;
    }
	
	return count+count1+count2+count3;
}

/**
 * 实时比较满足条件供应商是否满足抽取人数
 */
function compareExtractNum(){
	var ExtractNum = $("#extractNum").val();
	if(!ExtractNum){
		ExtractNum = 0;
	}
	var str = /^\d+$/;
	if(str.test(ExtractNum)){
		$("#supplierType").find(".cue").html("");
		if(ExtractNum>parseInt($("#count").html())){
			$("#ExtractNumError").html("家数不足，无法抽取");
			$("#count").parents("button").prop("style","background-color: red;");
		}else if($("#count").html()!="0"){
			$("#result").find("tbody").empty();
			$("#count").parents("button").removeAttr("style");
			$("#ExtractNumError").html("");
			return true;
		}
	}else{
		$("#ExtractNumError").html("仅能输入正整数");
		layer.msg("抽取数量仅能输入正整数");
	}
}


/**
 * 校验抽取数量
 */
function checkExtractNum(){
	if($("#extractNum").val()){
		return compareExtractNum();
	}else{
		layer.msg("请输入抽取数量，仅能输入正整数");
		$("#ExtractNumError").html("请输入抽取数量，仅能输入正整数");
	}
}


/**
 * 点击抽取，校验抽取条件
 * @param status
 * @returns {Boolean}
 */
function extractVerify(status) {
	// 清空错误提示
	$("#extractUser").find("span").remove();
	$("#supervise").find("span").remove();
	
	// 所有的必填项写一个class 验证必填 输入框要验证长度
	if(checkEmpty()+proError>0){
		if(proError>0){
			layer.msg("项目编号已存在");
		}
		return false;
	}
	$("#status").val(1);// 修改状态为抽取中
	if(checkExtractNum()){
		extractSupplier(code,status);
	}
	
}

function extractSupplier(code,status) {
	
	var flag = 0;
	// 存储项目信息
	$.ajax({
		type: "POST",
		url: $("#projectForm").attr('action'),
		data:$("#projectForm").serialize(),
		dataType: "json",
		async:false,
		success: function (msg) {
			
			if(null!=msg && "" !=msg){
				flag ++;
				for ( var k in msg) {
					$("#"+k+"Error").html(msg[k]);
				}
			}
		}
	});
	// 存储人员信息
	$.ajax({
		type: "POST",
		url: $("#supervise").attr('action'),
		data:  $("#supervise").serialize(),
		dataType: "json",
		async:false,
		success: function (msg) {
			$("#supervise").find("span").empty();
			if(null !=msg){
				flag++;
				for ( var k in msg) {
					if("All"!=k){
						$("#supervise").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
					}else{
						$("#sError").html(msg[k]);
					}
				}
			}else{
				$("#sError").empty();
			}
		}
	});
	
	$.ajax({
		type: "POST",
		url: $("#extractUser").attr('action'),
		data:  $("#extractUser").serialize(),
		dataType: "json",
		async:false,
		success: function (msg) {
			$("#extractUser").find("span").empty();
			if(null !=msg){
				flag++;
				for ( var k in msg) {
					if("All"==k){
						$("#eError").html(msg[k]);
					}else{
						$("#extractUser").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
					}
				}
			}else{
				$("#eError").empty();
			}
		}
	});	
	
	formData = $('#form1').serialize();
	// 存储条件
	$.ajax({
		type: "POST",
		url: globalPath+"/SupplierCondition_new/saveCondition.html",
		data:  $('#form1').serialize(),
		dataType: "json",
		async:false,
		success: function (msg) {
			if(msg==0){
				layer.alert("条件存储失败");
				flag++;
			}
		}
	});	
    if(flag!=0){
    	layer.msg("存在错误信息，检查及修改");
    	return false;
	}
    
    
    if(0==status){
    	// 自动抽取
    	$.ajax({
    		type: "POST",
    		url: globalPath+'/SupplierCondition_new/autoExtract.do?projectInfo'+projectType,//测试用
    		data: formData ,
    		//url: globalPath+'/autoExtract/exportExtractInfo.do?projectInfo'+projectType,// 真实
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			
    		}
		});
    }else{
    	// 显示抽取结果表
    	$("#result").removeClass("dnone");
    	// 追加抽取结果
    	appendTd(0,$("#result").find("tbody"),null);
    }
    
   
	
	// 输入框设置只读
	$('.extractVerify_disabled input,.extractVerify_disabled select').each(function() {
		$(this).prop('disabled', true);
	});
	// 按钮置灰
	$(".bu").each(function(){
		$(this).attr("disabled",true);
	});
	
	//设置抽取状态为抽取中
	var recordId = $("#recordId").val();
	$.ajax({
		type: "POST",
		url: globalPath+'/SupplierExtracts_new/updateExtractStatus.do',
		data: {"id":recordId,"status":2,extractTheWay:status} ,
		dataType: "json",
		async:false,
		success: function (msg) {
			
		}
	});
}

// 显示结果 obj 是当前操作的行所在的tbody
function appendTd(num,obj,result){
	document.getElementById('end').scrollIntoView();
	var flag = false;
	
	// 只能有一个请选择
	$(obj).find("select").each(function(){
		if($(this).val()==0){
			flag = true;
			return ;
		}
	});
	
	if(flag){
		return;
	}
	
	// 需要判断能参加，满足后不再追加
	var agreeCount=0;
	agreeCount = parseInt($(obj).parent().prev().find("span:first").html());
	if($("#extractNum").val()==agreeCount){
		$("#end").removeClass("dnone");
		return false;
	}
	if($("#extractNum").val()==$(obj).find("tr").length ){
		// $("#end").removeClass("dnone");
		return false;
	}
	
	var data = "";
	// 去后台请求一条数据
	$.ajax({
		type: "POST",
		url: globalPath+'/SupplierCondition_new/selectLikeSupplier.do',
		data: formData ,
		dataType: "json",
		async:false,
		success: function (msg) {
			if(null!= msg && null !=msg.error){
    			$("#"+msg.error).html("不能为空");
    		}
			if(null != msg && null != msg.list && msg.list.length>0){
				flag = false;
				data = msg.list;
			}else{
				flag = true;
			}
			
		}
	});
	
	if(agreeCount==$(obj).find("tr").length && flag){
		$("#end").removeClass("dnone");
		return ;
	}
	
	if(flag){
		return;
	}
	
	var typeName = data[0].supplierType;
	if(null ==data || "undefined" ==data || data.length<1){
		return false;
	}
	
	var i = 0;
	var armyBuinessTelephone = "";
	if(data[i].armyBuinessTelephone){
		armyBuinessTelephone = data[i].armyBuinessTelephone;
	}
	var armyBuinessMobile = "";
	if(data[i].armyBuinessMobile){
		armyBuinessMobile = data[i].armyBuinessMobile;
	}
	var armyBusinessName = "";
	if(data[i].armyBusinessName){
		armyBusinessName = data[i].armyBusinessName;
	}
	var tex = "<tr class='cursor' typeCode='"+$("#supplierType").val()+"' sid='"+data[i].id+"' index='"+i+"' level='"+data[i].supplierLevel+"'>" +
   	 "<td  >"+(parseInt(num)+1)+"</td>" +
	 "<td   >"+data[i].supplierName+"</td>" +
     "<td  >"+typeName+"</td>" +
     "<td >"+armyBusinessName+"</td>" +
     "<td  >"+armyBuinessMobile+"</td>" +
     "<td  >"+armyBuinessTelephone+"</td>" +
     "<td  class='res'><select onchange='operation(this)'> <option value='0'>请选择</option> <option value='1'>能参加</option> <option value='2'>待定</option> <option value='3'>不能参加</option> </td>" +
     "</tr>";
	$(obj).append(tex);
	// 更新序号
	var i=1;
	$(obj).find("tr").each(function(){
		var o = $(this).find("td").eq(0).html(i++);
	});
	
	return true;
}


/**
 * 追加结果到项目实施页面
 */
function appendParent(obj){
	var tbody=window.opener.document.getElementById("supplierList");
	var index = $(tbody).find("tr:last").find("td:first").html();
	if(index){
		index ++;
	}else{
		index = 1;
	}
	$(obj).find("td:first").html(index);
	$(obj).find("td:first").after("<td>"+packageName+"</td>");
	$(tbody).append(obj);
}

/** 暂存 */
function temporary(status) {
	// 存储项目信息
	 $.ajax({
         cache: true,
         type: "POST",
         dataType: "json",
         url: globalPath+'/SupplierCondition_new/saveSupplierCondition_new.do',
         data: $('#form').serialize(),// 你的formid
         success: function (data) {
        	 if(status == 1){
        		 layer.msg("暂存成功");
        	 }
         }
	 });
	// 存储查询条件
}

/** 展示品目 */
function opens(cate) {
	// 获取类别
	var typeCode = $("#supplierType").val();
	$(cate).attr("typeCode",typeCode);
    var iframeWin;
    var categoryId = $("#categoryIds").val();
    var parentId = $(cate).prev(".parentId");
    if(parentId.length<=0){
    	$(cate).before("<input class='parentId' type='hidden' value='' name='parentId'>");
    }else if($(parentId).val()){
    	categoryId += ","+ $(parentId).val();
    }
    sessionStorage.setItem("categoryId",categoryId);
    layer.open({
        type: 2,
        title: "选择条件",
        shadeClose: true,
        shade: 0.01,
        area: ['440px', '400px'],
        skin: 'layer-default',
        offset: '20px',
        content: globalPath+'/SupplierExtracts_new/addHeading.do?supplierTypeCode='+typeCode,
        success: function (layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
        },
        btn: ['保存', '重置']
        , yes: function () {
            iframeWin.getChildren(cate);
            if(typeCode == "PROJECT"){
            	initTypeLevelId(null);
            	emptyQuaInfo();
            	loadQuaList(null);
            }
            selectLikeSupplier();
        }
        , btn2: function () {
        	initCategoryAndQua();
        	 if(typeCode == "PROJECT"){
             	initTypeLevelId(null);
             }
            opens(cate);
        }
    });
}

 function showSupplierType() {
    var cityObj = $("#supplierType");
    var cityOffset = $("#supplierType").offset();
    $("#supplierTypeContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownSupplierType);
} 

 // 加载供应商类型下拉框
 function loadSupplierType(obj){ 
	 var typeCode = $("#projectType").val();
	 // 项目实施地区
	 if("GOODS"==typeCode){
		 $("#xmss").html("");
		 $("#xmss").removeClass("star_red");
	 }else{
		 $("#xmss").html("*");
		 $("#xmss").addClass("star_red");
	 }
	 
	 if("PROJECT" == typeCode){
		 $(".projectOwn").removeClass("dnone");
		 $("#buildCompany").removeClass("dnone");
		 $(".buildCompany").addClass("star_red");
		 
	 }else{
		 $(".projectOwn").addClass("dnone");
		 $("#buildCompany").addClass("dnone");
		 $(".buildCompany").removeClass("star_red");
	 }
	 if(null!=typeCode&&''!=typeCode){
		 $.ajax({
            type: "POST",
            async: false,
            url: globalPath+"/SupplierExtracts_new/supplieType2.do",
            dataType: "json",
            data:{typeCode:typeCode},
            success: function (data) {
            	$("#supplierType").empty();
            	initCategoryAndQua(null);
            	//初始化等级
            	initTypeLevelId(typeCode);
            	if(data.length>1){
    				$("#supplierType").append("<option value='GOODS' selected> 不限 </option>");
    			}
            	
        		for(var i=0;i<data.length;i++){
        			$("#supplierType").append("<option value='"+data[i].code+"'> "+data[i].name+" </option>");
        		}
        		selectLikeSupplier();
            }
        });
	 }
 }
 
 //重置品目，资质，等级
 function initCategoryAndLevel(obj){
	 var typeCode = $(obj).val();
	 initCategoryAndQua(obj);
	 initTypeLevelId(typeCode);
	 selectLikeSupplier();
 }
 
 // 根据初始化 品目 等级div
 function initCategoryAndQua(obj){
	//清空品目
	$(".category").find("input").each(function(){
		$(this).val("");
	});
	$("#isSatisfy").val(1);
	//清空资质
	emptyQuaInfo();
 }
 

// 加载地区树形结构
function loadAreaZtree(){
	var treeNodes; 
	 var setting = {
      async: {
        autoParam: ["id=area"],
        enable: true, 
        url: globalPath+"/SupplierExtracts_new/city.do",
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
            onCheck: choseArea,
            onAsyncSuccess:selectAllArea
      },
      view: {
            dblClickExpand: false
      }        
    };
    treeArea = $.fn.zTree.init($("#treeArea"), setting, treeNodes);
}

// 显示地区树
function showTree(){
	var areaObj = $("#area");
    var areaOffset = $("#area").offset();
    $("#areaContent").css({
        left: areaOffset.left + "px",
        top: areaOffset.top + areaObj.outerHeight() + "px"
    }).slideDown("fast");
     $("body").bind("mousedown", onBodyDownArea);
}

// 默认选中全国
function selectAllArea(){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	treeObj.checkAllNodes(true);
	showCheckArea(treeObj);
	
}

// 地区树选中处理
function showCheckArea(treeObj){
	var areas=treeObj.getCheckedNodes(true);
    // 省，直辖市
   	var pids = "";
   	// 二级 市 区
   	var ids = "";
   	var idArr = new Array();
   	var names = "";
   	
   	for(var i=0; i<areas.length;i++){
   		if(areas[i].isParent){
			pids += areas[i].id + ",";
			names += areas[i].name + ",";
			idArr.push(areas[i].id);
			if(areas[i].id == "0"){
				// 隐藏地区限制理由
				$("#areaReson").parents("li").addClass("dnone");
				break;
			}else{
				$("#areaReson").parents("li").removeClass("dnone");
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
	$("#province").val(pids.substring(0,pids.lastIndexOf(",")));
	$("#addressId").val(ids.substring(0,ids.lastIndexOf(",")));
	$("#area").val(names.substring(0,names.lastIndexOf(",")));
}

// 递归取消父节点选中状态
function dischecked(treeNode,treeObj){
	var node = treeNode.getParentNode();
	if(null !=node){
		treeObj.checkNode(node, false);
		dischecked(node,treeObj);
	}
}
// 获取选中节点地区
function choseArea(event,treeId,treeNode){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	dischecked(treeNode,treeObj);
	
	if(treeNode.checked){
		checkAllChildCheckParent(treeNode,treeObj);
	}
	
	showCheckArea(treeObj);
}



/**
 * 子节点全部选中，选中父节点
 * 
 * @param node
 * @returns
 */
// 递归父节点
function checkAllChildCheckParent(node,treeObj){
	var flag = preIsCheck(node) && nextIsCheck(node);
	var parentNode = node.getParentNode();
	if(flag){
		if(parentNode){
			treeObj.checkNode(parentNode, true,false,true);
			checkAllChildCheckParent(parentNode,treeObj);
		}
	}
}


// 判断前一个节点是否选中
function preIsCheck(treeNode){
 	var pre = treeNode.getPreNode();
 	var flag = treeNode.checked;
	if(pre){
		flag &=  preIsCheck(pre) ;
	}
	return flag;
}

// 判断后一个节点是否选中
function nextIsCheck(treeNode){
	var next = treeNode.getNextNode();
	var flag = treeNode.checked;
	if(next){
		flag &=  nextIsCheck(next) ;
	}
	return	flag;
}


// 地区树绑定事件
function onBodyDownArea(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#areaContent").length > 0)) {
        hideArea();
    }
}

function hideArea() {
    $("#areaContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownArea);
    selectLikeSupplier();
}

function onBodyDownSupplierType(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
        hideSupplierType();
    }
}

function hideSupplierType() {
    $("#supplierTypeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownSupplierType);
    selectLikeSupplier();
}

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("supplierTypeTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}


// 查询类别数量
function selectTypeCount(code){
	$.ajax({
		url:globalPath+'/SupplierCondition_new/selectLikeSupplier.do',
         type: "POST",
         dataType: "json",
         data:{supplierTypeCode:code,type:"count"},
         success:function(count){
        	 $("#"+code.toLowerCase()+"Result").find("span:first").html(count);
         }
	});
}

/** 供应商类别----eng---- */
/** 抽取级别----begin---- */
function initTypeLevelId(typeCode){
	$(".level").find("input").each(function(){
		$(this).val("");
	});
	
	if("GOODS"==typeCode){
		$("#level").parents("div").prev("span").text("生产类供应商等级");
		$("#salesLevel").parents("li").removeClass("dnone");
		$("#salesLevel").val("所有级别");
	}else{
		$("#level").parents("div").prev("span").text("供应商等级");
		$("#salesLevel").parents("li").addClass("dnone");
	}
	//重新加载等级树
	loadLevelTree(typeCode);
	$("#level").val("所有级别");
}

// 清空资质显示
function emptyQuaInfo(){
	$(".level").find("input").each(function(){
		$(this).val("");
	});
}


// 加载资质信息
function loadQuaList(nodes){
	// 获取当前供应商code
	var code = $("#supplierType").val().toLowerCase();
	
	if(nodes==null){
		var cateId = $("#categoryIds").val();
		var parentId = $("[name='parentId']").val();
		
		$.ajax({
			url:globalPath+"/SupplierCondition_new/getQuaByCid.do",
			type: "POST",
			dataType: "json",
			async:false,
			data:{categoryId:cateId,supplierTypeCode:code,parentId:parentId},
			success:function(data){
				nodes = data;
			}
		});
	}
	
	nodes = ajaxDataFilter(nodes);
	var setting = {
     check: {
    	 autoCheckTrigger: true,
       enable: true,
       chkboxType: {
         "Y": "s",
         "N": "p"
       },
       chkStyle : "checkbox" 
     },
     data: {
       simpleData: {
         enable: true,
         idKey: "id",
         pIdKey: "parentId",
       },
       key: {
			children: "nodes"
		}
     },
     callback: {
         onCheck: choseQua
     }
   };

	var quaTree = $.fn.zTree.init($("#quaTree"), setting,nodes);
}

// 加载资质树时回显
function ajaxDataFilter(responseData) {
	// 获取要选中的数据
	var checkedNode = "";
	if($("#quaId").val()){
		checkedNode = $("#quaId").val().split(",");
	}else{
		return responseData;
	}
	if(responseData) {
		// 判断如果
		for(var i = 0; i < responseData.length; i++) {
			for ( var k in checkedNode) {
				if(responseData[i].id == checkedNode[k]){
					responseData[i].checked = true;
				}
			}
		}
	}
	return responseData;
}


// 加载工程等级树
function loadprojectLevelTree(){
	var qid = $("#projectQuaId").val();
	var setting = {
        check: {
            enable: true,
            chkboxType: {
                "Y": "",
                "N": ""
            }
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            }
        },
        callback: {
            beforeClick: beforeClickLevel,
            onCheck: onCheckLevel
        }
    };
	if(null==qid || ''==qid){
		qid =$("#categoryIds").val();
		if(null!=qid&&""!=qid){
			$.ajax({
				url:globalPath+"/SupplierCondition_new/getEngLevelByCid.do",// 根据品目ID
				data:{categoryId:qid},
				async:false,
				dataType:"json",
				success:function(datas){
					if(null != datas && "undefind"!= datas && ''!=datas){
						var treeLevelType = $.fn.zTree.init($("#levelTree"), setting, datas);
					}else{
						layer.msg("未能查询出结果");
					}
				}
			});
		}
	}else{
		$.ajax({
			url:globalPath+"/SupplierCondition_new/getLevelByQid.do",// 根据资质编号ID
			data:{qid:qid},
			async:false,
			dataType:"json",
			success:function(datas){
					if(null != datas && "undefind"!= datas && ''!=datas){
					
						var treeLevelType = $.fn.zTree.init($("#levelTree"), setting, datas);
				}else{
					layer.msg("未能查询出结果");
				}
			}
		});
	}
}
// 加载等级树
function loadLevelTree(typeCode){
		var zNodes ;
        var setting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "",
                    "N": ""
                }
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                }
            },
            callback: {
                beforeClick: beforeClickLevel,
                onCheck: onCheckLevel
            }
        };
        
        switch (typeCode) {
		case "GOODS":
			zNodes= productLevel;
			var treeLevelType2 = $.fn.zTree.init($("#salesLevelTree"), setting, salesLevel);
			break;
		case "PRODUCT":
			zNodes= productLevel;
			break;
		case "SALES":
			zNodes= salesLevel;
			break;
		case "SERVICE":
			zNodes= salesLevel;
			break;
		}
        var treeLevelType = $.fn.zTree.init($("#levelTree"), setting, zNodes);
       
}

// 等级树加载完成后全选等级
function checkAllNodes(treeName){
	var treeObj = $.fn.zTree.getZTreeObj(treeName);
	treeObj.checkAllNodes(true);
	onCheckLevel(treeName);// 处理选中节点
}

// 展示等级树
function showLevel(obj){
	var typeCode = $("#supplierType").val();
    var levelOffset = $(obj).offset();
    
    var quaId = $("#quaId").val();
    if(typeCode == "PROJECT"){
    	if(null==quaId&& ""==quaId){
    		layer.msg("请选择工程资质");
    	}
    }
    
    //若是goods 则会有salesLevel 需要加载两颗等级树
    if(obj.id=="salesLevel"){
    	if(null == $.fn.zTree.getZTreeObj("salesLevelTree")){
        	loadLevelTree(typeCode);
    	}
    	
    	$("#salesLevelContent").css({
            left: levelOffset.left + "px",
            top: levelOffset.top + $(obj).outerHeight() + "px"
        }).slideDown("fast");
    	
    	$("body").bind("mousedown", onBodyDownSalesLevel);
    }else{
    	
    	if(null == $.fn.zTree.getZTreeObj("levelTree")){
    		loadLevelTree(typeCode);
    	}
    	
    	$("#levelContent").css({
            left: levelOffset.left + "px",
            top: levelOffset.top + $(obj).outerHeight() + "px"
        }).slideDown("fast");
    	
    	$("body").bind("mousedown", onBodyDownLevel);
    }
}

// 显示资质信息
function showQua(obj){
	var levelType = $(obj);
	var levelOffset = $(obj).offset();
	$("#quaContent").css({
		left: levelOffset.left + "px",
		top: levelOffset.top + levelType.outerHeight() + "px"
	}).slideDown("fast");
	$("body").bind("mousedown", onBodyDownQua);
}



function onBodyDownLevel(event) {
	if (!(event.target.nodeName == "SPAN")) {
		hideLevelType("levelContent");
	}
}

function onBodyDownSalesLevel(event) {
	if (!(event.target.nodeName == "SPAN")) {
		hideLevelType("salesLevelContent");
	}
}

//资质树显示/隐藏
function onBodyDownQua(event){
	if (!(event.target.nodeName == "SPAN")) {
		hideLevelType("quaContent");
	}
}
// 隐藏等级树
function hideLevelType(obj) {
    $("#"+obj).fadeOut("fast");
    
    if("levelContent"==obj){
    	$("body").unbind("mousedown", onBodyDownLevel);
    	selectLikeSupplier();
    }else if("quaContent"==obj){
    	$("body").unbind("mousedown", onBodyDownQua);
    	selectLikeSupplier();
    }else if("salesLevelContent"==obj){
    	$("body").unbind("mousedown", onBodyDownSalesLevel);
    	selectLikeSupplier();
    }
}

function beforeClickLevel(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeLevelType");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function beforeClickQua(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("quaTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}
// 工程等级树被选中后
function onCheckLevel(obj) {
	var zTree = $.fn.zTree.getZTreeObj(obj);
	if(null == zTree){
		zTree = $.fn.zTree.getZTreeObj(obj.target.id);
		obj = obj.target.id;
	}
	
	var input = obj.substring(0,obj.lastIndexOf("T"));
	
	var nodes = zTree.getCheckedNodes(true);
	v = "";
	var rid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		rid += nodes[i].id + ",";
	}
	if (v.length > 0) v = v.substring(0, v.length - 1);
	if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
	var levelTypeObj = $("#"+input);
	levelTypeObj.val(v);
	levelTypeObj.attr("title", v);
	$(levelTypeObj).parents("li").find("[name='"+input+"TypeId']").val(rid);
}

// 工程资质被选中后
function choseQua(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if(null != zTree){
		var nodes = zTree.getCheckedNodes(true);
		var v = "";
		var rid = "";
		var pv = "";
		var pid = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			
			if(nodes[i].isParent){
				pv += nodes[i].name + ",";
				pid += nodes[i].id + ",";
			}else{
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
		}
		
		
		if (v.length > 0) v = v.substring(0, v.length - 1);
		if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
		// 加载资质
		$("#quaId").val(rid);
		$("#quaName").val(v);
		if("project"==code){
			loadprojectLevelTree();
		}
	}
}

function operation(select) {
    var x, y;
    var oRect = select.getBoundingClientRect();
    x = oRect.left - 450;
    y = oRect.top - 150;
    layer.confirm('确定本次操作吗？', {
        btn: ['确定', '取消'], shade: 0.01, skin: 'layer-default'
    }, function (index) {
        var strs = new Array();
        var v = select.value;
        var obj = $(select).parents("tbody");
        var objTr = $(select).parents("tr");
        var req = $(obj).find("tr:last").find("td:first").html();
        if (objTr.next().length > 0) {
        	req = obj.find('tr').last().index();
        }
        layer.close(index);
        if (v == "3") {
            layer.prompt({
            	type: 1,
                formType: 2,
                shade: 0.01,
                offset: [y, x],
                title: ['<span class="red">*<span>  不参加理由'],
                btn:'确定',
                closeBtn: 0
            }, function (value, index, elem) {
            	if(!checkResonContainSpace(value)){
            		layer.msg("不参加理由不能包含空格");
            	}else{
            		
            		saveResult(objTr, value,0);
            		var notJoin = $(obj).parents("table").prev().find(".notJoin").html();
            		$(obj).parents("table").prev().find(".notJoin").html(parseInt(notJoin)+1);
            		if(value){
            			layer.close(index);
            		}
            		$(objTr).remove();
            		if(!appendTd(parseInt(req)-1,obj,"不能参加")){
            			var i=1;
            			$(obj).find("tr").each(function(){
            				var o = $(this).find("td").eq(0).html(i++);
            			});
            		}
            	}
            });
        } else if(v == "1"){
        	var parentsTr = objTr;
        	saveResult(parentsTr, '',1);
        	var yes = $(obj).parents("table").prev().find("span:first").html();
        	$(obj).parents("table").prev().find("span:first").html(parseInt(yes)+1);
        	$(select).parents("td").html("能参加");
        	appendTd(req,obj,"能参加");
        }else{
        	$(select).find("[value='0']").remove();
        	saveResult(objTr, '',2);
			appendTd(req,obj,"待定");
        }
    }, function (index) {
        layer.close(index);
        select.options[0].selected = true;
    });
}


// 存储成功
var successCount = 0;
/**
 * 存储抽取结果
 */
function saveResult(objTr, reason,join) {// obj:当前处理完成供应商信息、行 v:不能参加理由
	// 成功通知次数
	var successCount = 0;
	var supplierType = objTr.attr("typeCode");
	var conditionId = $("#conditionId").val();
	var recordId = $("#recordId").val();
	var packageId = $("#packageId").val();
	var level = objTr.attr("level");
	var sid = objTr.attr("sid");
	$.ajax({
        type: "POST",
        url: globalPath+"/SupplierExtracts_new/saveResult.do",
        data: {reason: reason, conditionId: conditionId,supplierId:sid,supplierType:supplierType,join:join,recordId:recordId,projectType:projectType,packageId:packageId,supplierLevel:level},
        dataType: "json",
        async:false,
        success: function (msg) {
        	if(msg==0){
        		layer.msg("抽取结果保存异常，请重新抽取");
        	}
        	successCount++;
        }
	});
	
	// 追加到项目实施页面
	if(projectType && join==1){
		var parentsTr = $(objTr).clone();
		$(parentsTr).find("td:last").remove();
		appendParent(parentsTr);
	}
	
}

// 点击结束
function alterEndInfo(obj){
	
	var flag = 0;
	var recordId = $("#recordId").val();
	// 修改抽取状态
	$.ajax({
		type: "POST",
		url: globalPath+"/SupplierExtracts_new/updateExtractStatus.do",
		data:{id:recordId,status:1},
		dataType: "json",
		async:false,
		success: function (msg) {
    		if(msg<1){
    			layer.alert("结束状态异常");
    			flag++;
    		}
		}
	});
	
	var index_1 = layer.alert("是否需要发送短信至确认参加供应商",function(){
		layer.close(index_1);
	});
	
	if(flag>0){
		return;
	}
	
	var index = layer.alert("完成抽取,打印记录表",function(){
		//window.open(globalPath+"/SupplierExtracts_new/printRecord.html?id="+$("[name='recordId']").val()+"&projectInto="+projectType);
		
		//window.location.href = globalPath+"/SupplierExtracts_new/printRecord.html?id="+$("[name='recordId']").val()+"&projectInto="+projectType;
		
		 try{ 
            var elemIF = document.createElement("iframe");   
            elemIF.src = globalPath+"/SupplierExtracts_new/printRecord.html?id="+$("[name='recordId']").val()+"&projectInto="+projectType;   
            elemIF.style.display = "none";   
            document.body.appendChild(elemIF);   
	        }catch(e){ 
	 
	    } 
		setTimeout(function(){
			$(obj).prop("disabled",true);
			if(projectType){
				window.open("","_self").close();
			}else{
				window.location.href = globalPath+"/SupplierExtracts_new/projectList.html";
			}
		}, 1000);
	        
		layer.close(index);
		/*var a = document.getElementById("down");  
        a.href=globalPath+"/SupplierExtracts_new/printRecord.html?id="+$("[name='recordId']").val()+"&projectInto="+projectType;  
        a.click();  */
		
		
		// 
	});
}


/**
 * 重置抽取条件
 * @param obj
 */
function resetCondition(obj){
	document.getElementById("form1").reset();
	selectLikeSupplier();
}

/**
 * 项目编号唯一校验
 * 
 * @param obj
 */
function checkSole(obj){
	var projectCode = $(obj).val();
	$.ajax({
		type: "POST",
		url: globalPath+"/SupplierExtracts_new/checkSole.do",
		data:  {projectCode:projectCode},
		dataType: "json",
		success: function (msg) {
			if(msg>0){
				$("#projectCodeError").html("该项目编号已存在");
				proError ++;
			}else{
				$("#projectCodeError").html("");
    				proError = 0;
    			}
    		}
    	});
	}
    
    
   /**
* 校验输入空格
*/ 
function checkSpase(obj){
	
	$("input").each(function(){
		if($(this).prop("readonly")){
			$(this).attr("");
		}
	});
	
	$(obj).val();
}

function checkResonContainSpace(value){
	if(value.split(" ").length>1){
		return false;
	}
	return true;
}


/**
 * 文本编译器计数
 */ 
function size(par) { 
	var max = 500; 
	var str = 0;
	if (par.value.length < max) 
	str = max - par.value.length;
	$("#textCount").html(str.toString()); 
} 
