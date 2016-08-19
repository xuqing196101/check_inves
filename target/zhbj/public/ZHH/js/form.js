








//
function upload_files(form_id){
	var url = $("#" + form_id).prop("action");
  if (url.lastIndexOf("master_id") > 0){
    $.getJSON(url, function(files){
      var fu = $("#" + form_id).data('blueimpFileupload'), template;
      fu._adjustMaxNumberOfFiles(-files.length);
      console.log(files);
      template = fu._renderDownload(files).appendTo($('#'+ form_id +' .files'));
      fu._reflow = fu._transition && template.length && template[0].offsetWidth;
      template.addClass('in');
      $('#loading').remove();
    });
  }

}

$(function() {
  // Masking.initMasking();
  // 日期选择
  Datepicker.initDatepicker();
  // 上传附件
  $('form.fileupload_form').each(function(){
  	upload_files($(this).attr("id"));
	});

  // 验证时间类型
  $.validator.addMethod("time", function (value, element) {
      return this.optional(element) || /^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/i.test(value);
  }, "请输入有效的时间");

  $.validator.addMethod("datetime", function (value, element) {
      return this.optional(element) || /^\d{4}[\/\-]\d{1,2}[\/\-]\d{1,2} (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/i.test(value);
  }, "请输入有效的时间(YYYY-MM-DD HH:mm:ss)");

  // 验证 通过类来验证
  jQuery.validator.addClassRules({
    required: {required: true},
    email: {email: true},
    url: {url: true},
    date: {date: true},
    dateISO: {dateISO: true},
    number: {number: true},
    digits: {digits: true},
    minlength_6: {minlength: 6},
    maxlength_800: {maxlength: 800},
    rangelength_6_20: {rangelength: [6,20]},
    min_1: {min: 1},
    max_100: {max: 100},
    range_1_1000: {range: [1,1000]}
  });

    // $('.特殊类').each(function() {
    //   $(this).rules('add', {
    //       required: true,
    //       number: true,
    //       messages: {
    //           required:  "必填项",
    //           number:  "必须是数字"
    //       }
    //   });
    // });
});

//  输入框有变动
function input_blur(me,master_table_names,slave_table_names){
    var id = me.attr("id").split("_").pop();
    var price = $("#"+slave_table_names+"_price_" + id).val();
    var quantity = $("#"+slave_table_names+"_quantity_" + id).val();
    if (isEmpty(quantity)){
      var quantity = $("#"+slave_table_names+"_num_" + id).val();
    }
    // 如果有入围价格 输入的成交价格不能大于入围价格
    var bid_price = $("#"+slave_table_names+"_bid_price_" + id).val();
    if (!isEmpty(bid_price) && parseFloat(price) > parseFloat(bid_price)) {
      $("#"+slave_table_names+"_price_" + id).val(bid_price);
    }

    if ( !isNaN(price) && (price != '') && (quantity != '') && !isNaN(quantity) ) {
        $("#"+slave_table_names+"_total_" + id).val(parseFloat(price) * parseFloat(quantity));
    }
    sum_calc_total(master_table_names,slave_table_names);

    // 提示成交价格不能大于入围价格
    if (!isEmpty(bid_price) && parseFloat(price) > parseFloat(bid_price)) {
      flash_dialog("成交价格不能大于入围价格！");
    }
}
//  计算总金额
function sum_calc_total(master_table_names,slave_table_names) {
    var total = 0;
    $("input[name^='"+slave_table_names+"[total]']").each(function () {
        var thisValue = $(this).val();
        if ( (thisValue != '') ) {
            total += formatFloat(parseFloat(thisValue),2);
            $(this).val(formatFloat(parseFloat(thisValue),2));
        };
    });
    var deliver_fee = $("input#" + master_table_names + "_deliver_fee");
    var other_fee = $("input#" + master_table_names + "_other_fee");

    if(!isEmpty(deliver_fee.val())){
      total += formatFloat(parseFloat(deliver_fee.val()),2);
      $(deliver_fee).val(formatFloat(parseFloat(deliver_fee.val()),2));
    }

    if(!isEmpty(other_fee.val())){
      total += formatFloat(parseFloat(other_fee.val()),2);
      $(other_fee).val(formatFloat(parseFloat(other_fee.val()),2));
    }

    total = formatFloat(total,2);
    $("#"+master_table_names+"_total").val(total);
    $(".show_total #form_sum_total").text(total);
};

// 根据数组计算单表的金额
function sum_total_by_array(table_names, arr) {
  var total = 0;
  $.each(arr, function(index, value){
    var me = $("input[name^='" + table_names + "[" + value + "]']")
    var thisValue = me.val();
    if ( !isEmpty(thisValue) ) {
      total += formatFloat(parseFloat(thisValue),2);
      me.val(formatFloat(parseFloat(thisValue),2));
    };
  });

  total = formatFloat(total,2);
  $("#"+ table_names + "_total").val(total);
  $(".show_total #form_sum_total").text(total);
}
;
