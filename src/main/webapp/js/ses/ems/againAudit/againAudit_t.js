(function($) {
  $.fn.listConstructor_t = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      data: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        unselect_ids = [];
        
        $('#selected_content').html('');
        for (var i in list_content) {
          if (typeof(list_content[i].orgName) === 'undefined') {
            list_content[i].orgName = '';
          }
          if (typeof(list_content[i].relName) === 'undefined') {
            list_content[i].relName = '';
          }
          if (typeof(list_content[i].sex) === 'undefined') {
            list_content[i].sex = '';
          }
          if (typeof(list_content[i].workUnit) === 'undefined') {
            list_content[i].workUnit = '';
          }
          if (typeof(list_content[i].professTechTitles) === 'undefined') {
            list_content[i].professTechTitles = '';
          }
          if (typeof(list_content[i].updateTime) === 'undefined') {
            list_content[i].updateTime = '';
          }
          if (typeof(list_content[i].expertsTypeId) === 'undefined') {
            list_content[i].expertsTypeId = '';
          }
          if (typeof(list_content[i].expertsFrom) === 'undefined') {
            list_content[i].expertsFrom = '';
          }
          
          $('#selected_content').append('<tr>'
              +'  <td class="text-center"><input name="id" type="checkbox" value="'+ list_content[i].id +'" class="select_item"></td>'
              +'  <td class="text-center">'+ (parseInt(i) + 1) +'</td>'
              +'  <td>'+ list_content[i].orgName +'</td>'
              +'  <td>'+ list_content[i].relName +'</td>'
              +'  <td class="text-center">'+ list_content[i].sex +'</td>'
              +'  <td>'+ list_content[i].expertsTypeId +'</td>'
              +'  <td class="text-center">'+ list_content[i].expertsFrom +'</td>'
              +'  <td>'+ list_content[i].workUnit +'</td>'
              +'  <td>'+ list_content[i].professTechTitles +'</td>'
              +'  <td class="text-center">'+ list_content[i].updateTime +'</td>'
          +'</tr>');
        }
        
        // 绑定列表框点击事件，获取选中id集合
        var select_checkbox = $('#selected_content').find('.select_item');
        var sum = 0;
        var show_nums = 0;
        
        if (select_checkbox.length > 0) {
          select_checkbox.bind('click', function () {
            var this_val = $(this).val().toString();
            var is_has = 0;
            
            if ($(this).is(':checked')) {
              for (var i in unselect_ids) {
                if (unselect_ids[i] == this_val) {
                  is_has = 1;
                  break;
                }
              }
              if (is_has == 0) {
                unselect_ids.push(this_val);
              }
            } else {
              for (var i in unselect_ids) {
                if (unselect_ids[i] == this_val) {
                  unselect_ids.splice(i, 1);
                  break;
                }
              }
            }
            
            sum = $(this).parents('tbody').find('.select_item:checked').length;
            show_nums = parseInt($(this).parents('tbody').find('.select_item').length) - parseInt($(this).parents('tr.hide').length);  // 显示出来的数据条数
            if (sum === show_nums) {
              $('.selected_checkAll').prop('checked', true);
            } else {
              $('.selected_checkAll').prop('checked', false);
            }
          });
        }
        
        select_total();  // 统计专家人数总数
      }
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;

    var start = function() {
      el.each(function() {
        $.ajax({
          type: opts.type,
          dataType: opts.dataType,
          url: opts.url,
          data: opts.data,
          success: opts.success
        });
      });
    }

    return start();
  }
})(jQuery);