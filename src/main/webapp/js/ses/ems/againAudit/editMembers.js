(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/findExpertReviewTeam.do',
      data: {
        groupId: getUrlParam('groupId')
      },
      success: function (data) {
        if (data.status) {
          list_content = data.object;  // 储存所需数据到变量
          
          if (list_content.groupStatus === '3') {
            $('#set_password').show();
          } else {
            $('#set_password').hide();
          }
          
          if (list_content.list.list.length > 0) {
            // 判断是否显示密码已设置信息
            if (list_content.password === 0) {
              $('#pwd_msg').html('密码未设置');
            }
            
            // 判断用户名是否已有
            if (list_content.userName != '') {
              $('[name=loginName]').prop('disabled', true);
              $('[name=loginName]').val(list_content.userName);
            }
            
            if (typeof(list_content) != 'undefined') {
              for (var i in list_content.list.list) {
                $('#list_content tr').eq(i).find('input[type="text"]').eq(0).val(list_content.list.list[i].relName);
                $('#list_content tr').eq(i).find('input[type="text"]').eq(1).val(list_content.list.list[i].orgName);
                $('#list_content tr').eq(i).find('input[type="text"]').eq(2).val(list_content.list.list[i].duties);
              }
            }
          }
        } else {
          layer.msg(data.message);
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