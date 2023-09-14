import Request from  '@/utils/request'
import {API_SENSITIVE_SAVE,API_CHANNELS_SAVE,API_SENSITIVE_LIST,API_ARTICLES_INFO,API_CHANNELS,API_COMMON_LIST,API_COMMON_UPDATE,API_COMMON_DELETE} from "@/constants/api";
import {API_CHANNELS_DELETE,API_SENSITIVE_DELETE,API_SENSITIVE_UPDATE,API_CHANNELS_UPDATE,API_NEWS_AUTH_FAIL, API_NEWS_AUTH_PASS } from '../constants/api';

export function loadList(data) {
	var index1 = data.name.indexOf( '_' );
	var index2 = data.name.indexOf( '_', index1 + 1 ); 
	var type  = data.name.substring(0,index2);
	data.name=data.name.substring(index2+1);
	// alert(type)
	if(type == "AD_CHANNEL"){
		// alert(data.name+"。。。。。。。")
		return new Request({
		  url: API_CHANNELS,
		  method: 'post',
		  data
		})
	}
	 else if(type == "WM_NEWS"){
		 data.title = data.name;
		 data.status = 3;
		 // alert(data1)
		 return new Request({
		   url: API_ARTICLES_INFO,
		   method: 'post',
		   data
		 })
	 }
	 else if(type == "AD_SENSITIVE"){
	 		 // alert(data1)
	 		 return new Request({
	 		   url: API_SENSITIVE_LIST,
	 		   method: 'post',
	 		   data
	 		 })
	 }
	
  
}

export function updateData(data) {
	var index1 = data.name.indexOf( '_' );
	var index2 = data.name.indexOf( '_', index1 + 1 ); 
	var type  = data.name.substring(0,index2);
	
	// alert(type)
	if(type == "AD_CHANNEL"){
		// alert(data.model)
		var operateType = data.model 
		data = JSON.parse(data.data)
		if(operateType == "add"){
			return new Request({
				url: API_CHANNELS_SAVE,
				method: 'post',
				data
			})	
		}
		else if(operateType == "edit"){
			return new Request({
				url: API_CHANNELS_UPDATE,
				method: 'post',
				data
			})
		}
		
	}
	else if(type == "AD_SENSITIVE"){
		var operateType = data.model
		data = JSON.parse(data.data)
		if(operateType == "add"){
			return new Request({
				url: API_SENSITIVE_SAVE,
				method: 'post',
				data
			})	
		}
		if(operateType == "edit"){
			return new Request({
				url: API_SENSITIVE_UPDATE,
				method: 'post',
				data
			})	
		}
	}

}


export function updateData2(data) {
	var index1 = data.name.indexOf( '_' );
	var index2 = data.name.indexOf( '_', index1 + 1 ); 
	var type  = data.name.substring(0,index2);
	// alert(type)
	if(type == "AD_CHANNEL"){
		data.name=''
		// alert(data.name)
		return new Request({
		  url: API_CHANNELS_UPDATE,
		  method: 'post',
		  data
		})
	}
	else if(type == "WM_NEWS"){
			 // alert(data.status)
			 if(data.status==2){
				 return new Request({
				   url: API_NEWS_AUTH_FAIL,
				   method: 'post',
				   data
				 })
			 }else if(data.status==4){
				 return new Request({
				   url: API_NEWS_AUTH_PASS,
				   method: 'post',
				   data
				 })
			 }
	}
	else if(type == "AD_SENSITIVE"){
			 // alert(data1)
			 return new Request({
			   url: API_SENSITIVE_SAVE,
			   method: 'post',
			   data
			 })
	}

}


export function deleteData(data) {
	var index1 = data.name.indexOf( '_' );
	var index2 = data.name.indexOf( '_', index1 + 1 ); 
	var type  = data.name.substring(0,index2);
	// alert(type)
	if(type == "AD_CHANNEL"){
		data.name=''
		// alert(data.name)
		return new Request({
		  url: API_CHANNELS_DELETE+data.id,
		  method: 'delete',
		})
	}
	if(type == "AD_SENSITIVE"){
		return new Request({
		  url: API_SENSITIVE_DELETE+data.id,
		  method: 'delete'
		})
	}
	
	
 
}
