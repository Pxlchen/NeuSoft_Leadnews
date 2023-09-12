<template>
    <div class="art-page">
        <div class="art-top"><TopBar :text="title"/></div>
		<div class="iframediv">
			<iframe class="iframe"  :src="iframesrc"  width="100%" frameborder="0" scrolling="auto"  :style="{height:'100vh'}"></iframe>
		</div>
    </div>
</template>

<script>
    import TopBar from '@/compoents/bars/article_top_bar'
    import BottomBar from '@/compoents/bars/article_bottom_bar'
    import Button from '@/compoents/buttons/button'
    import { WxcButton ,Utils } from 'weex-ui'
    import Api from '@/apis/article/api'

    const modal = weex.requireModule("modal")

    export default {
        name: "index",
        components:{TopBar,BottomBar,WxcButton,Button},
        props:['id','title','date','comment','type','source','authorId',"staticUrl"],
        data(){
            return {
				iframesrc: '',
                scrollerHeight:'500px',
                //关系
                time : {
                    timer:null,//定时器
                    timerStep:100,//定时器步长
                    readDuration:0,//阅读时长
                    percentage:0,//阅读比例
                    loadDuration:0,//加载时长
                    loadOff:true//加载完成控制
                },//时间相关属性
                test : {
                    isforward : false
                }
            }
        },
        created(){
			// alert(this.token);
            Api.setVue(this);
            let _this = this;
            this.time.timer = setInterval(function(){
                _this.time.readDuration+=_this.time.timerStep
                if(_this.time.loadOff){
                    _this.time.loadDuration+=_this.time.timerStep
                }
            },this.time.timerStep);
        },
        destroyed(){
            this.read();
        },
        mounted(){
            this.scrollerHeight=(Utils.env.getPageHeight()-180)+'px';
			this.$store.getToken().then(token=>{
			    this.iframesrc = this.staticUrl+"?articleId="+this.id+"&authorId="+this.authorId+"&token=" + token;
				// alert(this.iframesrc);
			})
        },
        methods : {
        }
    }
</script>

<style scoped>
    .art-page{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        width: 750px;
        flex-direction: column;
		
    }
    .art-top{
        top: 0;
        height: 90px;
        position: fixed;
        z-index: 999;
    }
	.iframediv{
		height: 1000rpx;
		width: auto;
	}
	#iframe{
		height: 100%;
	}
</style>
