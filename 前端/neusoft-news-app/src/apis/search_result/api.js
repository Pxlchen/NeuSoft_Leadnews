function Api(){
    this.vue;
}
Api.prototype = {
    setVue : function(vue){
        this.vue = vue;
    },
    // 加载
    article_search: function(parms){
        let url = this.vue.$config.urls.get('article_search')
        return this.vue.$request.postByEquipmentId(url,{
			minBehotTime: new Date().getTime(),
            searchWords:parms.keyword,
            pageNum:parms.pageNum,
            tag:parms.tag,
            pageSize:20
        })
    }
}

export default new Api()
