const DEFAULT_TIMES = 3 * 1000;//默认消失时间

/**
 * 提示通用方法
 * @param msg 提示信息
 * @param tipClass 提示样式
 * @param tipIconClass 提示图标样式
 * @private
 */
function _commonTip(msg, tipClass, tipIconClass) {
    if (tipIconClass) {
        tipIconClass = "bi-" + tipIconClass;
    }
    let div = '<div class="container-fluid">' +
        '    <div class="alert ' + tipClass + ' alert-dismissible d-flex align-items-center">' +
        '        <i class="bi ' + tipIconClass + ' me-2"></i>' +
        '        <div>' + msg + '</div>' +
        '        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>' +
        '    </div>' +
        '</div>';
    let node = $(div);
    $(document.body).prepend(node);
    setTimeout(function () {
        node.remove();
    }, 3000);
}

/**
 * 默认提示
 * @param msg
 */
function defaultTip(msg) {
    _commonTip(msg, "alert-info");
}

/**
 * 错误提示
 * @param msg
 */
function errorTip(msg) {
    _commonTip(msg, "alert-danger","exclamation-circle");
}

/**
 * 成功提示
 * @param msg
 */
function successTip(msg) {
    _commonTip(msg, "alert-success","check-circle");
}