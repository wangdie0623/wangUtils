/**
 * 注册账号
 * @param accountName
 * @param pwd
 */
function register(accountName, pwd, pwd2) {
    if (!accountName) {
        errorTip("用户名不能为空!");
        return;
    }
    if (!pwd) {
        errorTip("密码不能为空!");
        return;
    }
    if (!pwd2) {
        errorTip("确认密码不能为空!");
        return;
    }
    let params={
        name:accountName,
        pwd:pwd
    }
    reqPost(API_PATHS.account.register,params).then(function (r){
       if (r.success){
           successTip("注册成功!")
           return;
       }
       errorTip(r.msg);
    });
}

/**
 * 账号登录
 * @param accountName 用户名
 * @param pwd 密码
 */
function login(accountName, pwd) {
    if (!accountName) {
        errorTip("用户名不能为空!");
        return;
    }
    if (!pwd) {
        errorTip("密码不能为空!");
        return;
    }
    let params={
        name:accountName,
        pwd:$.md5(pwd+'|'+accountName)
    }
    reqPost(API_PATHS.account.login,params).then(function (r){
       if (r.success){
           sessionStorage.setItem(CUSTOM_TOKEN,r.detail);
           successTip("登录成功!")
           return;
       }
       errorTip(r.msg);
    });
}