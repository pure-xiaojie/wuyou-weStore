$(function () {
        $.ajax({
            type: 'get',
            url: '/alipay/createNative.do',
            success: function (data) {
                console.log('success');
                console.log(data);
                $('body').append(data);
            },
            error: function (data) {
                console.log('error');
                console.log(data);
            }
            
        })
});
