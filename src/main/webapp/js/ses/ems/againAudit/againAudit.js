(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      data: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        
        if (is_init === 0) {
          var str = '';
          for (var orgName_i in list_content.allOrg) {
            str += '<option value="'+ list_content.allOrg[orgName_i].shortName +'">'+ list_content.allOrg[orgName_i].shortName +'</option>';
          }
          $('[name=orgName]').html('<option value="">请选择</option>' + str);
          str = '';
          for (var expertsFrom_i in list_content.lyTypeList) {
            str += '<option value="'+ list_content.lyTypeList[expertsFrom_i].id +'">'+ list_content.lyTypeList[expertsFrom_i].name +'</option>';
          }
          $('[name=expertsFrom]').html('<option value="">全部</option>' + str);
          str = '';
          for (var expertsTypeId_i in list_content.expTypeList) {
            str += '<option value="'+ list_content.expTypeList[expertsTypeId_i].id +'">'+ list_content.expTypeList[expertsTypeId_i].name +'</option>';
          }
          $('[name=expertsTypeId]').html(str);
          str = '';
          $('[name=expertsTypeId]').select2({
            placeholder: '全部',
            closeOnSelect: false,
            minimumResultsForSearch: -1
          });
        }
        is_init ++;
        
        if (typeof(list_content) != 'undefined') {
          $('#list_content').html('');
          for (var i in list_content.expertList) {
            if (typeof(list_content.expertList[i].orgName) === 'undefined') {
              list_content.expertList[i].orgName = '';
            }
            if (typeof(list_content.expertList[i].relName) === 'undefined') {
              list_content.expertList[i].relName = '';
            }
            if (typeof(list_content.expertList[i].sex) === 'undefined') {
              list_content.expertList[i].sex = '';
            }
            if (typeof(list_content.expertList[i].workUnit) === 'undefined') {
              list_content.expertList[i].workUnit = '';
            }
            if (typeof(list_content.expertList[i].professTechTitles) === 'undefined') {
              list_content.expertList[i].professTechTitles = '';
            }
            if (typeof(list_content.expertList[i].updateTime) === 'undefined') {
              list_content.expertList[i].updateTime = '';
            }
            if (typeof(list_content.expertList[i].expertsTypeId) === 'undefined') {
              list_content.expertList[i].expertsTypeId = '';
            }
            if (typeof(list_content.expertList[i].expertsFrom) === 'undefined') {
              list_content.expertList[i].expertsFrom = '';
            }
            
            // 判断是否为重新复审状态
            if (typeof(list_content.expertList[i].reviewStatus) != null || typeof(list_content.expertList[i].reviewStatus) != 'null' || typeof(list_content.expertList[i].reviewStatus) != 'undefined') {
              $('#list_content').append('<tr class="red">'
                +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.expertList[i].id +'" class="select_item"></td>'
                +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
                +'<td>'+ list_content.expertList[i].orgName +'</td>'
                +'<td>'+ list_content.expertList[i].relName +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].sex +'</td>'
                +'<td>'+ list_content.expertList[i].expertsTypeId +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].expertsFrom +'</td>'
                +'<td>'+ list_content.expertList[i].workUnit +'</td>'
                +'<td>'+ list_content.expertList[i].professTechTitles +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].updateTime +'</td>'
              +'</tr>');
            } else {
              $('#list_content').append('<tr class="red">'
                +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.expertList[i].id +'" class="select_item"></td>'
                +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
                +'<td>'+ list_content.expertList[i].orgName +'</td>'
                +'<td>'+ list_content.expertList[i].relName +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].sex +'</td>'
                +'<td>'+ list_content.expertList[i].expertsTypeId +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].expertsFrom +'</td>'
                +'<td>'+ list_content.expertList[i].workUnit +'</td>'
                +'<td>'+ list_content.expertList[i].professTechTitles +'</td>'
                +'<td class="text-center">'+ list_content.expertList[i].updateTime +'</td>'
              +'</tr>');
            }
          }
          
          // 处理未选数据
          $('#list_content tr').each(function () {
            var _this = $(this);
            $('#selected_content tr').each(function () {
              if (_this.find('input[type="checkbox"]').val() == $(this).find('input[type="checkbox"]').val()) {
                _this.find('.select_item').prop('checked', false);
                _this.addClass('hide');
                return false;
              }
            });
          });
          
          // 绑定列表框点击事件，获取选中id集合
          var select_checkbox = $('#list_content').find('.select_item');
          var sum = 0;
          var show_nums = 0;
          
          if (select_checkbox.length > 0) {
            select_checkbox.bind('click', function () {
              var this_val = $(this).val().toString();
              var is_has = 0;
              
              if ($(this).is(':checked') && !$(this).parents('tr').hasClass('hide')) {
                for (var i in select_ids) {
                  if (select_ids[i] == this_val) {
                    is_has = 1;
                    break;
                  }
                }
                if (is_has == 0) {
                  select_ids.push(this_val);
                }
              } else {
                for (var i in select_ids) {
                  if (select_ids[i] == this_val) {
                    select_ids.splice(i, 1);
                    break;
                  }
                }
              }
              
              sum = $(this).parents('tbody').find('.select_item:checked').length;
              show_nums = parseInt($(this).parents('tbody').find('.select_item').length) - parseInt($(this).parents('tr.hide').length);  // 显示出来的数据条数
              if (sum === show_nums) {
                $('.unselected_checkAll').prop('checked', true);
              } else {
                $('.unselected_checkAll').prop('checked', false);
              }
            });
          } else {
            $('.unselected_checkAll').prop('checked', false);
          }
          
          select_total();  // 统计专家人数总数
          unselected_sort();  // 未选序号重新排序
        }
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