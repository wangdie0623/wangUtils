/**
 * get请求
 * @param url 地址
 * @param data 附加数据
 */
function reqGet(url, data) {
    let token = sessionStorage.getItem(CUSTOM_TOKEN);
    let ajax = $.ajax({
        type: 'GET',
        url:url,
        contentType:'application/x-www-form-urlencoded',
        dataType: 'json',
        data:data,
        beforeSend: function (XMLHttpRequest) {
            if (token){
                XMLHttpRequest.setRequestHeader(CUSTOM_TOKEN, token);
            }
        },
        error:function (r){
            console.error("ajax error:"+url+","+JSON.stringify(data));
            alert("请求异常");
        }
    });
    return ajax;
}

/**
 * post请求
 * @param url 地址
 * @param data 附加数据
 */
function reqPost(url, data) {
    let token = sessionStorage.getItem(CUSTOM_TOKEN);
    let ajax = $.ajax({
        type: 'POST',
        url:url,
        contentType:'application/x-www-form-urlencoded',
        dataType: 'json',
        data:data,
        beforeSend: function (XMLHttpRequest) {
            if (token){
                XMLHttpRequest.setRequestHeader(CUSTOM_TOKEN, token);
            }
        },
        error:function (r){
            console.error("ajax error:"+url+","+JSON.stringify(data));
            alert("请求异常");
        }
    });
    return ajax;
}
