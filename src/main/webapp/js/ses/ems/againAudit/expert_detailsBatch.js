(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      audit_url: '',
      data: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        $('#list_content').html('');
        for (var i in list_content.list) {
          if (list_content.list[i].status === '-3') {
            list_content.list[i].status = '公示中';
          } else if (list_content.list[i].status === '-2') {
            list_content.list[i].status = '预复审结束';
          } else if (list_content.list[i].status === '-1') {
            list_content.list[i].status = '暂存';
          } else if (list_content.list[i].status === '0') {
            list_content.list[i].status = '待初审';
          } else if (list_content.list[i].status === '4' && list_content.list[i].auditTemporary === '4') {
            list_content.list[i].status = '复审中';
          } else if (list_content.list[i].status === '4' && list_content.list[i].auditTemporary === '0') {
            list_content.list[i].status = '待复审';
          } else if (list_content.list[i].status === '5') {
            list_content.list[i].status = '复审不合格';
          } else if (list_content.list[i].status === '6') {
            list_content.list[i].status = '待复查';
          } else if (list_content.list[i].status === '7') {
            list_content.list[i].status = '复查合格';
          } else if (list_content.list[i].status === '8') {
            list_content.list[i].status = '复查不合格';
          } else if (list_content.list[i].status === '10') {
            list_content.list[i].status = '复审退回修改';
          } else if (list_content.list[i].status === '11') {
            list_content.list[i].status = '待分配';
          } else if (list_content.list[i].status === '12') {
            list_content.list[i].status = '处罚中';
          } else if (list_content.list[i].status === '13') {
            list_content.list[i].status = '无产品专家';
          } else if (list_content.list[i].status === '14') {
            list_content.list[i].status = '复审待分组专家';
          }
          
          if (typeof(list_content.list[i].batchDetailsNumber) === 'undefined') {
            list_content.list[i].batchDetailsNumber = '';
          }
          if (typeof(list_content.list[i].realName) === 'undefined') {
            list_content.list[i].realName = '';
          }
          if (typeof(list_content.list[i].gender) === 'undefined') {
            list_content.list[i].gender = '';
          }
          if (typeof(list_content.list[i].workUnit) === 'undefined') {
            list_content.list[i].workUnit = '';
          }
          if (typeof(list_content.list[i].professTechTitles) === 'undefined') {
            list_content.list[i].professTechTitles = '';
          }
          if (typeof(list_content.list[i].orgName) === 'undefined') {
            list_content.list[i].orgName = '';
          }
          if (typeof(list_content.list[i].updateTime) === 'undefined') {
            list_content.list[i].updateTime = '';
          }
          if (typeof(list_content.list[i].groupName) === 'undefined') {
            list_content.list[i].groupName = '';
          }
          if (typeof(list_content.list[i].auditor) === 'undefined') {
            list_content.list[i].auditor = '';
          }
          if (typeof(list_content.list[i].status) === 'undefined') {
            list_content.list[i].status = '';
          }
          if (typeof(list_content.list[i].auditAt) === 'undefined') {
            list_content.list[i].auditAt = '';
          }
          
          $('#list_content').append('<tr>'
            +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.list[i].expertId +'" class="select_item"></td>'
            +'<td>'+ list_content.list[i].batchDetailsNumber +'</td>'
            +'<td>'+ list_content.list[i].realName +'</td>'
            +'<td>'+ list_content.list[i].gender +'</td>'
            +'<td>'+ list_content.list[i].workUnit +'</td>'
            +'<td>'+ list_content.list[i].professTechTitles +'</td>'
            +'<td>'+ list_content.list[i].orgName +'</td>'
            +'<td>'+ list_content.list[i].updateTime +'</td>'
            +'<td>'+ list_content.list[i].groupName +'</td>'
            +'<td>'+ list_content.list[i].auditor +'</td>'
            +'<td>'+ list_content.list[i].status +'</td>'
            +'<td>'+ list_content.list[i].auditAt +'</td>'
          +'</tr>');
        }
        
        // 勾选翻页之前选中的项
        for (var i in select_ids) {
          $('.select_item').each(function () {
            if ($(this).val() === select_ids[i]) {
              $(this).prop('checked', true);
              return false;
            }
          });
        }
        
        // 绑定列表框点击事件，获取选中id集合
        var select_checkbox = $('.againAudit_table').find('.select_item');
        if (select_checkbox.length > 0) {
          select_checkbox.bind('click', function () {
            var this_val = $(this).val().toString();
            if ($(this).is(':checked')) {
              select_ids.push(this_val);
            } else {
              for (var i in select_ids) {
                if (select_ids[i] == this_val) {
                  select_ids.splice(i, 1);
                  break;
                }
              }
            }
          });
        }
        
        // 构造分页
        laypageConstructor();
      },
      error: function (data) {
        layer.msg(data.message, {
          offset: '100px'
        });
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
    
    // 分页
    function laypageConstructor() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: list_content.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.total,
        startRow: list_content.startRow,
        endRow: list_content.endRow,
        groups: list_content.pages >= 3 ? 3 : list_content.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            opts.data.pageNum = e.curr;
            $.ajax({
              type: opts.type,
              dataType: opts.dataType,
              url: opts.url,
              data: opts.data,
              success: opts.success
            });
          }
        }
      });
    }

    return start();
  }
})(jQuery);