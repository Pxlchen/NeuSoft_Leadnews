import axios from 'axios';

function Api(){
    this.vue;
}
Api.prototype = {
    setVue : function(vue){
        this.vue = vue;
    },
    // 登录
    login: function(data){
        let url = this.vue.$config.urls.get('user_login')
        return this.vue.$request.postByEquipmentId(url,data)
    },
    //登录成功获取个人信息
    // loginInfo: function(data){
    //     let url = this.vue.$config.urls.get('user_information')
    //
    //     axios.post(url,data).then().then(response => {
    //         //console.log(response.data)
    //     }).catch(error => {
    //         console.error('POST 请求失败:', error);
    //     });
    //     //return this.vue.$request.postByEquipmentId(url,data)
    // },



}

export default new Api()
