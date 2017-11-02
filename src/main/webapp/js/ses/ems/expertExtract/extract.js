$(function() {

    //加载地区树
    loadAreaZtree();
    //动态加载专家类别
    loadExpertKind();

    //加载审核人员
    for ( var i = 0; i < 2; i++) {
        addPerson($("#eu"));
    }
    addPerson($("#su"));
});

(function($){  
    $.fn.serializeJson=function(){
        var serializeObj={};  
        var array = this.serializeArray();  
        //var str = this.serialize();  
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
    if(parentId == '0'){
        $("#city").empty();
        $("#city").append("<option value='0'>选择地区</option>");
        return;
    }
    $.ajax({
        url : globalPath + "/area/find_by_parent_id.do",
        data : {
            "id" : parentId
        },
        dataType : "json",
        async : false,
        success : function(response) {
            $("#city").empty();
            $("#city").append("<option value='0'>选择地区</option>");
            $.each(response, function(i, result) {
                $("#city").append("<option value='" + result.id + "'>" + result.name + "</option>");
            });
        }
    });
}

//人工抽取
function artificial_extracting(isAuto){
	//加载菊花图标
	var ae_load = layer.load();
    getCount();
    $("#projectName").attr("disabled",false);
    $("#projectCode").attr("disabled",false);
    $("#purchaseWay").attr("disabled",false);
    $("#packageName").attr("disabled",false);
    $("#projectType").attr("disabled",false);
    var code = $("#expertKind option:selected").val();
    if(!validationIsNull(code)){
    	layer.close(ae_load);
        return;
    }
    savePerson();
    $("#isAuto").val(isAuto);
    //项目信息
    var proRuestl_1 = $("#form").serializeJson();//数据序列化
    var param1 = $("#condition_form").serializeJson();
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
        	if(isAuto == 0){
        		//人工抽取
        		for(var key in data){
                    if(key != "conditionId" && data[key] != null && data[key].length > 0){
                        $("#"+key+"_h").removeClass("display-none");
                        addTr(key,data[key][0]);
                    }else if(key == "conditionId"){
                        $("#conditionId").val(data[key]);
                    }
                }
        		$("#result").removeClass("display-none");
        	}else if(isAuto == 1){
        		//自动抽取
        		if(data == "OK"){
        			layer.alert("信息同步至外网状态成功，正在抽取中，请稍后查看结果。");
        		}else{
        			layer.alert("信息同步至外网状态失败。");
        		}
        	}
        	layer.close(ae_load);
        },
        error: function () {
            layer.msg("操作失败", {offset: '100px'});
        }
    });
}

//验证信息非空
function validationIsNull(code){
    var flag = true;
    //项目名称
    var projectName = $("#projectName").val();
    if(projectName == null || projectName == ""){
        $("#err_projectName").html("项目名称不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else if(strTrim(projectName).length > 100){
        $("#err_projectName").html("不能超过100字");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_projectName").html("");
    }
    
    //项目编号
    var xmProjectId = $("#xmProjectId").val();
    if(xmProjectId == null || xmProjectId == ''){
    	vaCode();
    }
    // 评审时间
    var reviewTime = $("#reviewTime").val();
    if(reviewTime == null || reviewTime == ""){
        $("#err_reviewTime").html("评审时间不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_reviewTime").html("");
    }
    //评审地点
    var province = $("#province option:selected").val();
    var city = $("#city option:selected").val();
    if(province == '0' || city == '0'){
        $("#err_aaa").html("请选择评审地点");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_aaa").html("");
    }
    //抽取地址
    var extractAddress = $("#extractAddress").val();
    if(extractAddress == null || extractAddress == ""){
        $("#err_extractAddress").html("抽取地址不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else if(strTrim(extractAddress).length > 100){
        $("#err_extractAddress").html("不能超过100字");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_extractAddress").html("");
    }
    //评审详细地址
    var reviewSite = $("#reviewSite").val();
    if(reviewSite == null || reviewSite == ""){
        $("#err_reviewSite").html("评审详细地址不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else if(strTrim(reviewSite).length > 100){
        $("#err_reviewSite").html("不能超过100字");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_reviewSite").html("");
    }
    
    //联系人
    var contactPerson = $("#contactPerson").val();
    if(contactPerson == null || contactPerson == ""){
        $("#err_contactPerson").html("联系人不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else if(strTrim(contactPerson).length > 30){
        $("#err_contactPerson").html("不能超过30字");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_contactPerson").html("");
    }
    //联系电话
    var contactNum = $("#contactNum").val();
    if(contactNum == null || contactNum == ""){
        $("#err_contactNum").html("联系电话不能为空");
        flag = false;
        layer.msg("请完善项目信息");
    }else{
        $("#err_contactNum").html("");
    }
    //每个品目的人数
    var strs = new Array(); //定义一数组 
    strs = code.split(",");
    var num = 0;
    for(var i=0; i<strs.length; i++){
        if($("#"+strs[i]+"_count").text() == 0){
            layer.msg("人数不足，无法抽取");
            flag = false;
        }
        var v = $("#"+strs[i].toLowerCase()+"_i_count").val();
        if(v == null || v == ""){
            $("#err_"+strs[i].toLowerCase()+"_i_count").html("人数不能为空");
            flag = false;
        }else if(parseInt(v) <= 0){
            $("#err_"+strs[i].toLowerCase()+"_i_count").html("人数必须大于0");
            flag = false;
        }else if(parseInt(v) > parseInt($("#"+strs[i]+"_count").text())){
            flag = false;
            $("#err_"+strs[i].toLowerCase()+"_i_count").html("当前符合条件人数不足");
            layer.msg("人数不足，无法抽取");
        }else{
            num += parseInt(coUndifined(v));
            $("#err_"+strs[i].toLowerCase()+"_i_count").html("");
        }
        //工程特有的工程专业信息验证
        if(code.indexOf("PROJECT") >= 0){
            var engInfo =  $("#"+strs[i].toLowerCase()+"_eng_info").val();
            if(engInfo == null || engInfo == ""){
                $("#err_"+strs[i].toLowerCase()+"_eng_info").html("工程专业信息不能为空");
                flag = false;
            }else{
                $("#err_"+strs[i].toLowerCase()+"_eng_info").html("");
            }
        }
    }
    //区域要求
    var provincesel = coUndifined($("#provincesel").val());
    if(provincesel == null || provincesel == ""){
        $("#err_provincesel").html("区域要求不能为空");
        flag = false;
    }else{
        $("#err_provincesel").html("");
    }
    //抽取总人数
    var extractNum = $("#extractNum").val();
    if(extractNum == null || extractNum == ""){
        $("#err_extractNum").html("抽取总人数不能为空");
        flag = false;
    }else if(parseInt(coUndifined(extractNum)) <= 0){
        $("#err_extractNum").html("抽取总人数必须大于0");
        flag = false;
    }else if(num > parseInt(coUndifined(extractNum))){
        layer.msg("不能大于抽取总人数");
        flag = false;
    }else{
        $("#err_extractNum").html("");
    }
    //人员校验
    var count1 = 0;
    var count2 = 0;
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
    if($("#extractUser").find("tr").length<3){
        count1++;
    }
    if($("#supervise").find("tr").length<2){
        count2++;
    }
    if(count1 > 0){
        flag = false;
        $("#eError").html("抽取人员信息必须填写完整");
        layer.msg("请完善人员信息");
    }else{
        $("#eError").html("");
    }
    if(count2 > 0){
        flag = false;
        $("#sError").html("监督人员信息必须填写完整");
        layer.msg("请完善人员信息");
    }else{
        $("#sError").html("");
    }
    //区域限制理由
    var provincesel = $("#provincesel").val();
    if(provincesel != '0'){
        var xzReason = $("#xzReason").val();
        if(xzReason == null || xzReason == ""){
            flag = false;
            $("#err_addressReason").html("区域限制理由不能为空");
        }
    }
    return flag;
}

//去除字符串前后的空格
function strTrim(str){
    return str.replace(/(^\s+)|(\s+$)/g, "");
}

//追加显示抽取结果
function addTr(code,data){
	var remark = "";
	//判断候补专家的数量
	var isExtractAlternate = $("#isExtractAlternate  option:selected").val();
	var vv = false;
	if(isExtractAlternate == "1"){
		vv = true;
	}
    var count = $("#"+code+"_result").find("tr:last").find("td:eq(0)").html();
    if (typeof (count) == "undefined"){
		count = 0;
	}else{
		count = parseInt(count);
	}
    var codeCount = parseInt(coUndifined($("#"+code.toLowerCase()+"_i_count").val()));
    if(vv && count >= codeCount){
    	remark = "候补";
    }
    var info = "<tr>" +
    "<td class='w50 tc'>"+1+"</td>" +
    "<input value='"+coUndifined(data.id)+"'type='hidden'>" +
    "<td>"+coUndifined(data.relName)+"</td>" +
    "<td>"+coUndifined(data.mobile)+"</td>" +
    "<td>"+coUndifined(data.expertsTypeId)+"</td>" +
    "<td>"+coUndifined(data.workUnit)+"</td>" +
    "<td>"+coUndifined(data.professTechTitles)+"</td>" +
    "<td>"+coUndifined(data.professional)+"</td>" +
    "<td class='tc'>"+remark+"</td>" +
    "<td class='tc res'><select class='col-md-12 col-sm-12 col-xs-12 p0' onchange='isJoin(this)'>" +
        "<option value='0'>请选择</option>" +
        "<option value='1'>能参加</option>" +
        "<option value='2'>待定</option>" +
        "<option value='3'>不能参加</option>" +
        "</select>" +
    "</td></tr>";
    $("#"+code+"_result").find("tbody").append(info);
    var i=0;
    $("#"+code+"_result").find("tr").each(function(){
        $(this).find("td").eq(0).html(i++);
    });
}

/**
 * 追加结果到项目实施页面name,type,tel
 */
function appendParent(name,type,tel){
	var packageId = $("#packageId").val();
	if(packageId != null && packageId != ""){
		$("#packageName").attr("disabled",false);
	    var packageName = $("#packageName").val();
	    $("#packageName").attr("disabled",true);
	    var tbody=window.opener.document.getElementById("supplierList");
	    var index = $(tbody).find("tr:last").find("td:first").html();
	    if(index){
	        index ++;
	    }else{
	        index = 1;
	    }
	    var info = "<tr>" +
	    "<td class='tc'>"+index+"</td>" +
	    "<td class='tc'>"+packageName+"</td>" +
	    "<td class='tc'>"+name+"</td>" +
	    "<td class='tc'>"+type+"</td>" +
	    "<td class='tc'>"+tel+"</td>" +
	    "</tr>";
	    $(tbody).append(info);
	}
}

//是否参加
function isJoin(select){
	//判断候补专家的数量
	var isExtractAlternate = $("#isExtractAlternate option:selected").val();
	var vv = false;
	if(isExtractAlternate == "1"){
		vv = true;
	}
	var vvcode = $("#expertKind option:selected").val();
    var vvstrs = new Array(); //定义一数组 
    vvstrs = vvcode.split(",");
    var hb = 0;
    if(vvstrs.length == 1 && vv){
    	hb = 2;
    }else if(vvstrs.length == 2 && vv){
    	hb = 1;
    }
    //获取table的ID
    var id = $(select).parent().parent().parent().parent().attr("id");
    //获取当前专家类别
    var code = id.substring(0,id.indexOf("_result"));
    var s = $("#"+id).children("tbody").find("select");
    var flag = true;
    for (var i = 0; i < s.length; i++) {
        if(s[i].value == '0'){
            flag = false;
        }
    }
    var count = parseInt($("#"+code+"_result_count").text());
    var no = parseInt($("#"+code+"_result_no").text());
    //当前类别输入的总人数
    var codeCount = parseInt(coUndifined($("#"+code.toLowerCase()+"_i_count").val()));
    var x, y;
    var oRect = select.getBoundingClientRect();
    x = oRect.left - 450;
    y = oRect.top - 150;
    layer.confirm('确定本次操作吗？', {
        btn: ['确定', '取消'], shade: 0.01
    }, function (index) {
        layer.close(index);
        var v = select.value;
        if (v == "3") {
            layer.prompt({
                formType: 2,
                shade: 0.01,
                offset: [y, x],
                btn: ['确定'],
                closeBtn: 0,
                title: '<span class="red">*</span>不参加理由'
            }, function (value, index, elem) {
                layer.close(index);
                saveResult($(select).parents("tr").find("input").first().val(),value,v,code,select);
                $(select).parent().parent().remove();
                $("#"+code+"_result_no").text(no + 1);
                if(flag){
                    getExpert(code);
                    //判断是否要显示结束按钮
                    displayEnd();
                }else{
                    var i=0;
                    $("#"+code+"_result").find("tr").each(function(){
                        $(this).find("td").eq(0).html(i++);
                    });
                }
            });
        }else if(v == "1"){
            saveResult($(select).parents("tr").find("input").first().val(),"",v,code,select);
            var name = $(select).parents("tr").find("td:eq(1)").html();
            var tel = $(select).parents("tr").find("td:eq(2)").html();
            var type = $(select).parents("tr").find("td:eq(3)").html();
            appendParent(name,type,tel);
            $(select).parents("td").html("能参加");
            $(select).remove();
            $("#"+code+"_result_count").text(count + 1);
            if(flag){
                //验证如果人数满足条件  就不在追加显示了
                var ww = parseInt(coUndifined($("#"+id).children("tbody").find("tr").length));
                if(ww < codeCount + hb){
                    getExpert(code);
                }
                displayEnd();
            }else{
                var i=0;
                $("#"+code+"_result").find("tr").each(function(){
                    $(this).find("td").eq(0).html(i++);
                });
            }
        }else if(v == "2"){
            saveResult($(select).parents("tr").find("input").first().val(),"",v,code,select);
            if(flag){
                //验证如果人数满足条件  就不在追加显示了
                var ww = parseInt(coUndifined($("#"+id).children("tbody").find("tr").length));
                if(ww < codeCount + hb){
                    getExpert(code);
                }
                //判断是否要显示结束按钮
                displayEnd();
            }else{
                var i=0;
                $("#"+code+"_result").find("tr").each(function(){
                    $(this).find("td").eq(0).html(i++);
                });
            }
        }
    }, function (index) {
        layer.close(index);
        select.options[0].selected = true;
    });
}


//保存抽取结果
function saveResult(expertId,value,join,code,select){
	var isAlternate = null;
	//判断候补专家的数量
	var isExtractAlternate = $("#isExtractAlternate  option:selected").val();
	var vv = false;
	if(isExtractAlternate == "1"){
		vv = true;
	}
	var count = $("#"+code+"_result").find("tr:last").find("td:eq(0)").html();
	if (typeof (count) == "undefined"){
		count = 0;
	}else{
		count = parseInt(count);
	}
    var codeCount = parseInt(coUndifined($("#"+code.toLowerCase()+"_i_count").val()));
    if(vv && count > codeCount){
    	isAlternate = 1;
    }
    var las = coUndifined($(select).parent().prev().html());
    if(las != "候补"){
    	isAlternate = null;
    }
    var conditionId = $("#conditionId").val();
    var projectId = $("#projectId").val();
    var reviewTime = $("#reviewTime").val();
    $.ajax({
        url : globalPath + "/extractExpertResult/saveResult.do",
        data : {
            "conditionId" : conditionId,
            "projectId" : projectId,
            "reason" : value,
            "isJoin" : join,
            "reviewTime" : reviewTime,
            "expertId" : expertId,
            "expertCode" : code,
            "isAlternate" : isAlternate
        },
        dataType : "json",
        async : false,
        type : "POST",
        success : function() {
            /*var packageId = $("#packageId").val();*/
        }
    });
}

//追加显示专家
function getExpert(resultCode){
    $("#div_1").find("input").attr("disabled",false);
    $("#div_1 select").attr("disabled",false);
    $("#div_2 ").find("input").attr("disabled",false);
    $("#div_2 select").attr("disabled",false);
    $("#div_3").find("input").attr("disabled",false);
    $("#div_3 select").attr("disabled",false);
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
    $("#div_1").find("input").attr("disabled",true);
    $("#div_1 select").attr("disabled",true);
    $("#div_2 ").find("input").attr("disabled",true);
    $("#div_2 select").attr("disabled",true);
    $("#div_3").find("input").attr("disabled",true);
    $("#div_3 select").attr("disabled",true);
    $.ajax({
        url : globalPath + "/extractExpert/getExpert.do",
        data : result,
        dataType : "json",
        async : false,
        type : "POST",
        success : function(data) {
            for(var key in data){
                if(key == resultCode && data[key] != null && data[key].length > 0){
                    addTr(key,data[key][0]);
                }
            }
        },
    });
}

// 将undefined转换为空字符串
function coUndifined(v){
    if (typeof (v) == "undefined") {
        return "";
    } else {
        return v;
    }
}

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
                if(data[i].code != "GOODS_PROJECT" && data[i].code != "GOODS_SERVER"){
                    $("#expertKind").append("<option value="+data[i].code+">"+data[i].name+"</option>");
                }
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
function getCount(){
    //项目信息
    var proRuestl_1 = $("#form").serializeJson();//数据序列化
    var param1 = $("#condition_form").serializeJson();
    var code = $("#expertKind option:selected").val();
    if(code.indexOf(",") >= 0){
        //cateCode = $(cate).attr("typeCode");
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
        url : globalPath + "/extractExpert/getCount.do",
        data : result,
        dataType : "json",
        type : "POST",
        async : false,
        success : function(data) {
            for(var key in data){
                if(key != "conditionId"){
                    $("#"+key+"_count").text(data[key].length);
                    if(data[key].length == 0){
                        $("#"+key+"_count").parent().css({backgroundColor: 'red'});
                    }else{
                        $("#"+key+"_count").parent().css({backgroundColor: '#2c9fa6'});
                    }
                }
            }
        },
        error: function () {
            
        }
    });
}

//抽取人数限制
function vaCount(cate){
    var count = $("#extractNum").val();
    if(coUndifined(count) == ""){
        layer.msg("请先输入抽取总人数");
        return;
    }
    var v = $(cate).val();
    if(coUndifined(v) == ""){
        return;
    }
    if(parseInt(v) > parseInt(count)){
        layer.msg("不能大于抽取总人数");
        return;
    }
    var code = $("#expertKind option:selected").val();
    var strs = new Array(); //定义一数组 
    strs = code.split(",");
    var num = 0;
    for (var i = 0; i < strs.length; i++) {
        num += parseInt(coUndifined($("#"+strs[i].toLowerCase()+"_i_count").val()));
    }
    if(num > parseInt(count)){
        layer.msg("不能大于抽取总人数");
        return;
    }
    changeKind();
}
/**展示品目*/
function opens(cate) {
    var typeCode = $(cate).attr("typeCode");
    var ids = coUndifined($("#"+typeCode.toLowerCase()+"_type").val());
    var isSatisfy = coUndifined($("#"+typeCode.toLowerCase()+"_isSatisfy").val());
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
        skin: "aabbcc",
        content: globalPath+'/extractExpert/addHeading.do?type='+typeCode+'&&id='+ids+'&&isSatisfy='+isSatisfy, //iframe的url
        success: function (layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
        },
        btn: ['保存', '重置']
        , yes: function () {
            iframeWin.getChildren(cate);
            getCount();
        }
        , btn2: function () {
            opens(cate);
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
        $("#xzReason").val("");
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
    if(treeNode.checked){
		checkAllChildCheckParent(treeNode,treeObj);
	}
    showCheckArea(treeObj);
}

/**
 * 子节点全部选中，选中父节点
 * @param node
 * @returns
 */
//递归根节点
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

//判断前一个节点是否选中
function preIsCheck(treeNode){
	 	var pre = treeNode.getPreNode();
	 	var flag = treeNode.checked;
	if(pre){
		flag &=  preIsCheck(pre) ;
	}
	return flag;
}

//判断后一个节点是否选中
	function nextIsCheck(treeNode){
		var next = treeNode.getNextNode();
		var flag = treeNode.checked;
		if(next){
			flag &=  nextIsCheck(next) ;
	}
	return	flag;
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
    var tr = "<tr class='inp'><td class='tc'><input type='checkbox' name='list["+req+"].id'  value='"+id+"'><input type='hidden' name='list["+req+"].id'  value='"+id+"'></td><td class='tc'> "+(parseInt(index)+1)+" </td><td class='tc'> <input type='text' name='list["+req+"].name' maxlength='5'> </td><td class='tc'> <input type='text' class='w100p' name='list["+req+"].compary' ></td><td class='tc'> <input type='text' name='list["+req+"].duty'></td><td class='tc'> <input type='text' name='list["+req+"].rank'></td></tr>";
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
        area: ['600px', '400px'],
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
    
    //修改inputlist序号
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

//全选全不选
function checkAll(obj){
    $(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
}


function savePerson(){
    var flag = 0;
    //存储人员信息
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
    return true;
}

//重置抽取条件
function extractReset(){
    $("#div_3").find("input").val("");
    var SelectArr = $("#div_3").find("select");
    for (var i = 0; i < SelectArr.length; i++) {
        SelectArr[i].options[0].selected = true; 
    }
    //清空校验提示信息
    $("#err_provincesel").html("");
    $("#err_extractNum").html("");
    $("#err_project_i_count").html("");
    $("#err_project_eng_info").html("");
    $("#err_goods_project_i_count").html("");
    $("#err_goods_project_eng_info").html("");
    $("#err_goods_i_count").html("");
    $("#err_goods_server_i_count").html("");
    $("#err_service_i_count").html("");
    changeKind();
    loadAreaZtree();
}

//显示结束按钮
function displayEnd(){
	var isExtractAlternate = $("#isExtractAlternate  option:selected").val();
	var vv = false;
	if(isExtractAlternate == "1"){
		vv = true;
	}
	//判断专家抽取结果是否满足条件
    //能参加的人数等于选择的人数
    var code = $("#expertKind option:selected").val();
    var strs = new Array(); //定义一数组 
    strs = code.split(",");
    var flag = true;
    for (var i = 0; i < strs.length; i++) {
        //选择的人数
    	var count = 0;
    	if(vv && strs.length == 1){
    		count = parseInt(coUndifined($("#"+strs[i].toLowerCase()+"_i_count").val())) + 2;
    	}else if(vv && strs.length == 2){
    		count = parseInt(coUndifined($("#"+strs[i].toLowerCase()+"_i_count").val())) + 1;
    	}else{
    		count = parseInt(coUndifined($("#"+strs[i].toLowerCase()+"_i_count").val()));
    	}
        //确认参加的人数
        var join = parseInt(coUndifined($("#"+strs[i]+"_result_count").text()));
        if(count != join){
            if(isHaveExpert(strs[i])){
                flag &= false;
            }
        }
        //判断列表里面是否还有待定人员
        var s = $("#"+strs[i]+"_result").children("tbody").find("select");
        for (var j = 0; j < s.length; j++) {
            if(s[j].value == '2'){
                flag &= false;
            }
        }
    }
    if(flag){
        $("#endButton").removeClass("display-none");
    }else{
        $("#endButton").addClass("display-none");
    }
}

//判断是否还有满足条件的专家
function isHaveExpert(resultCode){
    $("#div_1").find("input").attr("disabled",false);
    $("#div_1 select").attr("disabled",false);
    $("#div_2 ").find("input").attr("disabled",false);
    $("#div_2 select").attr("disabled",false);
    $("#div_3").find("input").attr("disabled",false);
    $("#div_3 select").attr("disabled",false);
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
    $("#div_1").find("input").attr("disabled",true);
    $("#div_1 select").attr("disabled",true);
    $("#div_2 ").find("input").attr("disabled",true);
    $("#div_2 select").attr("disabled",true);
    $("#div_3").find("input").attr("disabled",true);
    $("#div_3 select").attr("disabled",true);
    var flag = true;
    $.ajax({
        url : globalPath + "/extractExpert/getExpert.do",
        data : result,
        dataType : "json",
        async : false,
        type : "POST",
        success : function(data) {
            for(var key in data){
                if(key == resultCode){
                    if(data[key] != null && data[key].length > 0){
                        flag = true;
                    }else{
                        flag = false;
                    }
                }
            }
        },
    });
    return flag;
}

/**
 * 抽取结束
 */
function extract_end(){
    $("#div_1").find("input").attr("disabled",false);
    $("#div_1 select").attr("disabled",false);
    $("#div_2 ").find("input").attr("disabled",false);
    $("#div_2 select").attr("disabled",false);
    $("#div_3").find("input").attr("disabled",false);
    $("#div_3 select").attr("disabled",false);
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
    $("#div_1").find("input").attr("disabled",true);
    $("#div_1 select").attr("disabled",true);
    $("#div_2 ").find("input").attr("disabled",true);
    $("#div_2 select").attr("disabled",true);
    $("#div_3").find("input").attr("disabled",true);
    $("#div_3 select").attr("disabled",true);
    $.ajax({
        url : globalPath + "/extractExpert/extractEnd.do",
        data : result,
        dataType : "json",
        async : false,
        type : "POST",
        success : function(data) {
            if(data == "success"){
                alterEndInfo();
            }
        }
    });
}

//打印结果表
function alterEndInfo(){
    var packageId = $("#packageId").val();
    var projectId = $("#projectId").val();
    layer.alert("是否需要发送短信至确认参加供应商");
    var index = layer.alert("完成抽取,打印记录表",function(){
        window.open(globalPath+"/extractExpertRecord/printRecord.html?id="+projectId,"下载抽取表");
        $("#extractEnd").prop("disabled",true);
        if(packageId == null || packageId == ""){
            window.location.href=globalPath+"/extractExpertRecord/getRecordList.html";
        }else{
            window.open("","_self").close();
        }
        layer.close(index);
    });
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

//验证项目编号
function vaCode(){
    var projectCode = $("#projectCode").val();
    var xmProjectId = $("#xmProjectId").val();
    if(projectCode == null || projectCode == ""){
        $("#err_code").html("项目编号不能为空");
    }else{
        // 验证项目编号重复校验
        $.ajax({
            url : globalPath + "/extractExpert/vaProjectCode.do",
            data : {
                "code" : projectCode,
                "xmProjectId" : xmProjectId
            },
            dataType : "json",
            async : false,
            type : "POST",
            success : function(data) {
                if(data.status == "no"){
                    $("#err_code").html("项目编号已被使用");
                    flag = false;
                    layer.msg("请完善项目信息");
                }else{
                    $("#err_code").html("");
                }
            }
        });
    }
}

//文本编译器计数
function size(par) { 
    var max = 500;
    var str = 0;
    if (par.value.length < max) 
    str = max - par.value.length;
    $("#textCount").html(str.toString()); 
} 
