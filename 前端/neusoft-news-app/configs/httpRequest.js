//封装请求
axios.defaults.baseURL = "http://localhost:51601/user";    /*基地址*/
axios.defaults.timeout = 5000000    /*超时*/

import axios from "axios";
import qs from 'qs';
import store from "@/stores/store";

//get请求方法
function doGet(url, params) {
    return axios({
        url: url,
        method: "get",
        params: params
    });
}

/*传递json数据，在请求报文中是json格式*/
function doPostJson(url, params) {
    return axios({
        url: url,
        method: "post",
        data: params
    });
}

//请求的是键值对的格式
function doPost(url, params) {
    let requestData = qs.stringify(params)
    return axios.post(url, requestData)
}


//创建请求拦截器
axios.interceptors.request.use(function (config) {

    //在需要用户登录后的操作，在请求的url中加入token

    let token;
    store.getToken().then(resp =>{
        console.log(resp)
        console.log("url====="+config.url);
        token = resp;
        if (config.url == '/api/v1/user/information') {
            config.headers['token'] = token;
        }
        return config;
    });
}, function (err) {
    console.log("请求错误" + err)
})


export {doGet, doPostJson, doPost}
