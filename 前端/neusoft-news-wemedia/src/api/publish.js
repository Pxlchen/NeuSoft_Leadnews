import Request from  '@/utils/request'
import {API_USERIMAGES_LIST,API_USERIMAGES_ADD ,
  API_CHANNELS , API_ARTICLES , API_MODIFYIMAGE_COL,
  API_MODIFYIMAGE_COL_CANCEL,API_MODIFYIMAGE_DELETE} from '@/constants/api'
//拉取全部的素材图片
export function getAllImgData (data) {
	
    return Request({
        url:API_USERIMAGES_LIST,
        method:'post',
        params:{},
        data:data
    })
	
}

//上传图片
export function  uploadImg (data) {
    console.log(data);
    return Request({
        url:API_USERIMAGES_ADD,
        method:'post',
        data
    })
}


// export function uploadImg(data) {
//     const formData = new FormData();
//     formData.append('multipartFile', data);
  
//     return Request({
//       url: API_USERIMAGES_ADD,
//       method: 'post',
//       data: formData
//     });
//   }
  
//拉取文章
export function getChannels () {
    return Request({
        url:API_CHANNELS,
        method:'get',
    })
}
//发表文章
export function publishArticles (params,data) {
  delete data['id']
   return  Request({
       url:API_ARTICLES,
       method:'post',
       params,
       data
   })
}
//编辑文章
export function modifyArticles (articleId,params,data) {
    return  Request({
        url:API_ARTICLES,
        method:'post',
        params,
        data
    })
 }
//删除图片素材
export function delImg (id) {
   return Request({
       url:API_MODIFYIMAGE_DELETE,
       method:'post',
       params:{},
       data:{collectId:id}
   })
}
//收藏或取消收藏方法
export function  collectOrCancel (id,data) {
    let collect = data.isCollected
  console.log(collect)
    let url = API_MODIFYIMAGE_COL
	console.log(url)
    if(collect==0){
      url = API_MODIFYIMAGE_COL_CANCEL
	  console.log(url)
    }
    return Request({
        url:url,
        method:'post',
        params:{},
        data:{collectId:id}
    })
}
