$(function() {
  /*if (!$.browser.msie || $.browser.version > 6){
    $(".select2").select2({width: 'resolve', dropdownAutoWidth: 'true'});
  }*/

  // 判断空 $.isBlank($(this).val())
  $.isBlank = function(obj) {
    return(!obj || $.trim(obj) === "");
  };

  /* 表单input后面提示 */
  $("form input").on('click', function(){
    var i_btn = $(this).siblings('.add-on')[0];
    var tip_info = $(this).siblings('.input-tip')[0];
    var input_val = $(this).val();
    if(i_btn && tip_info && input_val == ""){
      layer.tips($(tip_info).text(), $(i_btn), {tips: [3, '#000']});
    }
  });

  $("form span.add-on").on('click', function(){
    var tip_info = $(this).siblings('.input-tip')[0];
    if(tip_info){
      layer.tips($(tip_info).text(), $(this), {tips: [3, '#000']});
    }
  });

  // 提示
  $('.itips').on('click', function(){
    var d = dialog({
      align: 'top',
      content: $(this).find(".itips_content").html(),
      quickClose: true
    });
    d.show(this);
  });

  $('.itips_hover').on('mouseover', function () {
    $(".itips_content").hide();
    var d = dialog({
      align: 'top',
      content: $(this).find(".itips_content").html(),
      quickClose: true
    });
    d.show(this);
  });

  // 通用dialog提示
  $(document).on('click', '.art_tip', function(){
    if(isEmpty($(this).attr("title"))){
      var title = "提示"
    }else{
      var title = $(this).attr("title");
    }
    var d = dialog({
      title: title,
      quickClose: true,
      content: $(this).next().clone()
    }).show(this).title("提示");
  });

  // checkbox全选 不包括 disabled
  $("#all_check_box").on("click", function() {
    if ($(this).prop("checked") == false){
      $(".check_box_item:enabled").prop("checked", false);
    }else{
      $(".check_box_item:enabled").prop("checked", true);
    }
  });

  $(".check_box_item").on("click", function() {
    if ($(this).prop("checked") == false){
      $("#all_check_box").prop("checked", false);
    }else{
      if ($(".check_box_item:checkbox:checked").length == $(".check_box_item").length){
        $("#all_check_box").prop("checked", true);
      }
    }
  });

  // 级联下拉框
  $(document).on('change', 'select.multi-level', function() {
    $selector = $(this);

    $('#' + $selector.attr("aim_id")).val($selector.val());

    if (!$.isBlank($selector.attr("text_id"))) {
      $('#' + $selector.attr("text_id")).val($selector.find("option:selected").text());
    }

    $.ajax({
      type: "get",
      url: '/dynamic_selects?id=' + $selector.val() + '&otype=' + $selector.attr("otype") + '&aim_id=' + $selector.attr("aim_id") + '&other=' + $selector.attr("other") + '&text_id=' + $selector.attr("text_id")+ '&prompt=' + $selector.attr("prompt"),
      beforeSend: function(XMLHttpRequest) {

      },
      success: function(data, textStatus) {
        if (data.length > 0) {
          $selector.nextAll('select').remove();
          // $selector.parent().append(data);
          $selector.after(data);
        }
      },
      complete: function(XMLHttpRequest, textStatus) {
          //HideLoading();
        },
        error: function() {
          //请求出错处理
        }
      });
  });

  // 日期时间控件
  $('.my97_date').click(function() {
    WdatePicker({dateFmt:'yyyy-MM-dd', readOnly:true});
  });

  $('.my97_time').click(function() {
    WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', readOnly:true});
  });

});

// 自动适应高度textarea
function textarea_auto_height(textarea){
  textarea.style.height = "1px";
  textarea.style.height = (25+textarea.scrollHeight)+"px";
}

// 加入收藏
function addToFavorite() {
  var ctrl = (navigator.userAgent.toLowerCase()).indexOf('mac') != -1 ? 'Command/Cmd' : 'CTRL';
  if (document.all) {
    window.external.addFavorite('http://www.sinopr.org', '公共资源交易网')
  } else if (window.sidebar) {
    window.sidebar.addPanel('公共资源交易网', 'http://www.sinopr.org', "")
  } else {//添加收藏的快捷键
    alert('添加失败\n您可以尝试通过快捷键' + ctrl + ' + D 加入到收藏夹~')
  }
}

// 设为首页
function addHomePage() {
  var url = this.href;
  try {
    this.style.behavior = "url(#default#homepage)";
    this.setHomePage(url);
  } catch (e) {
    if (document.all) {
      document.body.style.behavior="url(#default#homepage)";
      document.body.setHomePage(url);
    } else if (window.sidebar) {
      if (window.netscape) {
        try {
          netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
          alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
          return false;
        }
      }
      var prefs = Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);
      prefs.setCharPref('browser.startup.homepage',url);
    } else {
      //alert('您的浏览器不支持自动设置首页, 使用浏览器菜单或在浏览器地址栏输入“chrome://settings/browser”手动设置!');
      $("#sethomepage").href();
    }
  }
  return false;
}

//日期时间格式化
Date.prototype.Format = function (fmt) { //author: meizz
  var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
    }

/**
 * 判断是否是空
 * @param value
 */
 function isEmpty(value){
  if (!value || $.trim(value) === ""){
    return true
  }else if(value == null || value == "" || value == "undefined" || value == undefined || value == "null"){
    return true;
  }
  else{
    value = value.toString();
    value = value.replace(/\s/g,"");
    if(value == ""){
      return true;
    }
    return false;
  }
}

//正则
function trimTxt(txt){
 return txt.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
 function is_forbid(temp_str){
  temp_str=trimTxt(temp_str);
  temp_str = temp_str.replace('*',"@");
  temp_str = temp_str.replace('--',"@");
  temp_str = temp_str.replace('/',"@");
  temp_str = temp_str.replace('+',"@");
  temp_str = temp_str.replace('\'',"@");
  temp_str = temp_str.replace('\\',"@");
  temp_str = temp_str.replace('$',"@");
  temp_str = temp_str.replace('^',"@");
  temp_str = temp_str.replace('.',"@");
  temp_str = temp_str.replace(';',"@");
  temp_str = temp_str.replace('<',"@");
  temp_str = temp_str.replace('>',"@");
  temp_str = temp_str.replace('"',"@");
  temp_str = temp_str.replace('=',"@");
  temp_str = temp_str.replace('{',"@");
  temp_str = temp_str.replace('}',"@");
  var forbid_str=new String('@,%,~,&');
  var forbid_array=new Array();
  forbid_array=forbid_str.split(',');
  for(i=0;i<forbid_array.length;i++){
    if(temp_str.search(new RegExp(forbid_array[i])) != -1)
      return false;
  }
  return true;
}

/**
 * 检查手机号码
 * @param mobile
 * @returns {Boolean}
 */
 function check_mobile(mobile){
  var regu = /^\d{11}$/;
  var re = new RegExp(regu);
  if(!re.test(mobile)){
   return  false;
 }
 return true;
}

/**
 * 验证电话号码，带"(,),-"字符和数字其他不通过
 * @param str
 * @returns {Boolean}
 */
 function checkPhone(str){
   if(str.length > 20){
    return false;
  }
  var patternStr = "(0123456789-)";
  var  strlength=str.length;
  for(var i=0;i<strlength;i++){
    var tempchar=str.substring(i,i+1);
    if(patternStr.indexOf(tempchar)<0){
      return false;
    }
  }
  return true ;
}

// 等待一个元素加载完成
// $("#btn_comment_submit").wait(function() { //等待#btn_comment_submit元素的加载
// this.removeClass("comment_btn").addClass("btn"); //提交按钮
// //这里的 this 就是 $("#btn_comment_submit")
// });
jQuery.fn.wait = function (func, times, interval) {
  var _times = times || -1, //100次
  _interval = interval || 20, //20毫秒每次
  _self = this,
  _selector = this.selector, //选择器
  _iIntervalID; //定时器id
  if( this.length ){ //如果已经获取到了，就直接执行函数
    func && func.call(this);
  } else {
    _iIntervalID = setInterval(function() {
  if(!_times) { //是0就退出
    clearInterval(_iIntervalID);
  }
  _times <= 0 || _times--; //如果是正数就 --

  _self = $(_selector); //再次选择
  if( _self.length ) { //判断是否取到
    func && func.call(_self);
    clearInterval(_iIntervalID);
  }
}, _interval);
  }
  return this;
}

// 截取字符串
// $(this).text(cutstr(s, 40));
function cutstr(str, len) {
  var str_length = 0;
  var str_len = 0;
  str_cut = new String();
  str_len = str.length;
  for (var i = 0; i < str_len; i++) {
    a = str.charAt(i);
    str_length++;
    if (escape(a).length > 4) {
      //中文字符的长度经编码之后大于4
      str_length++;
    }
    str_cut = str_cut.concat(a);
    if (str_length >= len) {
      str_cut = str_cut.concat("...");
      return str_cut;
    }
  }
  //如果给定字符串小于指定长度，则返回源字符串；
  if (str_length < len) {
    return str + "&nbsp;&nbsp;&nbsp;&nbsp;";
  }
}

// 全选、取消全选的事件
function selectAll(){
    if ($("#check_all").attr("checked")) {
        $(":checkbox").attr("checked", true);
    } else {
        $(":checkbox").attr("checked", false);
    }
};
// 子复选框的事件
function setSelectAll(){
    //当没有选中某个子复选框时，SelectAll取消选中
    if (!$(this).checked) {
        $("#check_all").attr("checked", false);
    }
    var chsub = $(".list_table tbody input[type='checkbox']").length; //获取checkbox的个数
    var checkedsub = $(".list_table tbody input[type='checkbox']:checked").length; //获取选中的checkbox的个数
    if (checkedsub == chsub) {
        $("#check_all").attr("checked", true);
    }else {
        $("#check_all").attr("checked", false);
    }
};
function art_alert(msg){
  var d = dialog({
    title: "提示",
    content: msg,
    quickClose: true
  });
  d.show();
};
//正整数正则判断
function positiveRegular(str){
    var pattern = /^[1-9]\d*$/;
    if(pattern.test(str)){
        return true;
    }
    return false;
}
