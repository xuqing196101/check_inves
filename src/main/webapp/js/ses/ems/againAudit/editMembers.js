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
        
        if (list_content.groupStatus == '1') {
          $('.btn_group_t').html('<button type="button" class="btn btn-windows add" onclick="add_members()">添加</button>'
            +'<button type="button" class="btn btn-windows delete" onclick="del_members()">删除</button>'
            +'<button type="button" class="btn btn-windows setPwd" onclick="set_newPassword()">设置密码</button>');
          $('.btn_group_b').html('<button type="button" class="btn btn-windows save" onclick="save_editMembers()">保存</button>'
            +'<button type="button" class="btn btn-windows back " onclick="javascript:history.back()">返回</button>');
        } else {
          $('.btn_group_t').html('<button type="button" class="btn btn-windows setPwd" onclick="set_password()">设置密码</button>');
          $('.btn_group_b').html('<button type="button" class="btn btn-windows back " onclick="javascript:history.back()">返回</button>');
        }
        
        if (typeof(list_content) != 'undefined') {
          $('#list_content').html('');
          for (var i in list_content.list.list) {
            if (typeof(list_content.list.list[i].loginName) === 'undefined') {
              list_content.list.list[i].loginName = '';
            }
            if (typeof(list_content.list.list[i].relName) === 'undefined') {
              list_content.list.list[i].relName = '';
            }
            if (typeof(list_content.list.list[i].orgName) === 'undefined') {
              list_content.list.list[i].orgName = '';
            }
            if (typeof(list_content.list.list[i].duties) === 'undefined') {
              list_content.list.list[i].duties = '';
            }
            if (typeof(list_content.list.list[i].passWord) === 'undefined') {
              list_content.list.list[i].passWord = '';
            } else if (list_content.list.list[i].passWord === '1') {
              list_content.list.list[i].passWord = '已设置密码';
            } else if (list_content.list.list[i].passWord === '0') {
              list_content.list.list[i].passWord = '未设置密码';
            }
            
            $('#list_content').append('<tr>'
              +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.list.list[i].id +'" class="select_item"></td>'
              +'<td class="text-center">'+ list_content.list.list[i].loginName +'</td>'
              +'<td class="text-center">'+ list_content.list.list[i].relName +'</td>'
              +'<td>'+ list_content.list.list[i].orgName +'</td>'
              +'<td>'+ list_content.list.list[i].duties +'</td>'
              +'<td>'+ list_content.list.list[i].passWord +'</td>'
            +'</tr>');
          }
          
          // 勾选翻页之前选中的项
          var allchecked = 0;
          for (var i in select_ids) {
            $('.select_item').each(function () {
              if ($(this).val() === select_ids[i]) {
                allchecked++;
                $(this).prop('checked', true);
                return false;
              }
            });
            if (allchecked === $('.select_item').length) {
              $('[name=checkAll]').prop('checked', true);
            } else {
              $('[name=checkAll]').prop('checked', false);
            }
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
              
              var sum = 0;
              $('.againAudit_table').find('.select_item').each(function () {
                if ($(this).is(':checked')) {
                  sum++;
                }
              });
              
              if (sum === $('.againAudit_table').find('.select_item').length) {
                $('[name=checkAll]').prop('checked', true);
              } else {
                $('[name=checkAll]').prop('checked', false);
              }
            });
          }
          
          // 构造分页
          laypageConstructor();
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
    
    // 分页
    function laypageConstructor() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: list_content.list.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.list.total,
        startRow: list_content.list.startRow,
        endRow: list_content.list.endRow,
        groups: list_content.list.pages >= 3 ? 3 : list_content.list.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.list.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            $.ajax({
              type: opts.type,
              dataType: opts.dataType,
              url: opts.url,
              data: {
                pageNum: e.curr,
                groupId: getUrlParam('groupId')
              },
              success: opts.success
            });
          }
        }
      });
    }

    return start();
  }
})(jQuery);