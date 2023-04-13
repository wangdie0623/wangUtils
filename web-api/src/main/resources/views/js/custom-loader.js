const CUSTOM_VERSION="1.0.0";//版本号
const CUSTOM_ENV="pro";//环境标识
/**
 * 导入js资源
 * @param env
 */
function loaderCommonJs(){
    let jsList=[
        'https://code.jquery.com/jquery-1.12.4.min.js',
        'https://cdn.jsdelivr.net/npm/vue@2.7.14',
        'js/const-pros.js'+"?v="+CUSTOM_VERSION,
        'js/http-util.js'+"?v="+CUSTOM_VERSION,
        'js/custom-tip.js'+"?v="+CUSTOM_VERSION,
        'js/account-common.js'+"?v="+CUSTOM_VERSION,
    ];
    if (CUSTOM_ENV=="pro"){
        jsList.push('https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js');
    }else{
        jsList.push('js/bootstrap.min.js');
    }
    let nodes = getNodes(jsList,true);
    appendAll(document.head,nodes);
}

/**
 * 导入css资源
 * @param env
 */
function loaderCommonCss(env){
    let cssList=[];
    if (CUSTOM_ENV=="pro"){
        cssList.push('https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css');
    }else{
        cssList.push('css/bootstrap.min.css');
    }
    cssList.push('http://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css');
    cssList.push('css/custom-css.css');
    let nodes = getNodes(cssList);
    appendAll(document.head,nodes);
}

/**
 * 得到nodes
 * @param nodeUrlArr
 * @param isScript
 * @returns {[]}
 */
function getNodes(nodeUrlArr,isScript){
    let result=[];
    if (!nodeUrlArr||nodeUrlArr.length==0){
        return result;
    }
    for (let i = 0; i <nodeUrlArr.length ; i++) {
        let item=nodeUrlArr[i];
        let node = isScript?getScriptNode(item):getCssNode(item);
        result.push(node);
    }
    return result;
}

/**
 * 创建js node
 * @param url
 * @returns {HTMLScriptElement}
 */
function getScriptNode(url){
    let node = document.createElement("script");
    node.src=url;
    return node;
}

/**
 * 创建css node
 * @param url
 * @returns {HTMLLinkElement}
 */
function getCssNode(url){
    let node = document.createElement("link");
    node.href=url;
    node.rel="stylesheet";
    return node;
}

/**
 * 往指定节点追加节点集合
 * @param ele
 * @param list
 */
function appendAll(ele,list){
    if (ele){
        for (let i = 0; i < list.length; i++) {
            let item=list[i];
            ele.appendChild(item);
        }
    }
}