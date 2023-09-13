<template>
	<div class="siderbarBox">

		<div class="siderbarBox-left">

			<div class="siderbarBox-top">
				<div class="saoMa">
					<image src="../../static/index/erweima2.png" mode=""></image>
				</div>
			</div>

			<!-- 头像区域 -->
			<div class="information">
				<div class="avatr">
					<image :src="this.User.uphoto" mode=""></image>
				</div>
				<div class="person-information">
					<div class="person-information-a" @click="gologin()">
						<h2>{{this.User.uname}}</h2>
					</div>
					<div class="person-information-b">
						<p>xx关注&nbsp&nbspxx粉丝&nbsp&nbspLv.7</p>
					</div>
				</div>
			</div>

			<div class="vip">
				<div class="vip-text-a">
					<h2>超级VIP</h2>
					<div class="dengjitiao">
						<view class="now"/>
					</div>
					<p>V2</p>
					<div class="vip-center">
						<h5>会员中心</h5>
					</div>
				</div>
				<div class="vip-text-b">
					<h4>礼品卡 | 吃糖啦小朋友🍭 </h4>
					<image src="../../static/index/shuiyu.jpg" mode=""></image>
				</div>
				<div class="vip-wave">
					<wave></wave>
				</div>
			</div>

			<div class="menu">
				<!-- ：style -->
				<!-- <view class="menu-item"  v-for="(item,index) in siderbarMenu" :key="index"> -->
				<div class="menu-item"  v-for="(item,index) in siderbarMenu" :key="index" :style="late(index)">
					<image :src="item.image" mode=""></image>
					<p>{{item.text}}</p>
					<image :src="item.more" mode=""></image>
				</div>
			</div>


			<div class="again-login" @click="breakLogin(),silder()">
				<h2>退出登录/关闭</h2>
			</div>


		</div>
		<div class="siderbarBox-right" @click="silder">
		</div>
	</div>
</template>

<script>
	import {mapState} from 'vuex'
	import wave from '../../components/user/wave.vue'
	export default {
		components:{wave},
		data() {
			return {
				siderbarMenu:[
					{
						image:require('@/static/index/a.png'),
						text:'我的信息',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/b.png'),
						text:'创作者中心',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/c.png'),
						text:'C村有票',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/d.png'),
						text:'商城',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/e.png'),
						text:'设置',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/f.png'),
						text:'夜间模式',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/g.png'),
						text:'定时关闭',
						more:require('@/static/index/more.png')
					},
					{
						image:require('@/static/index/h.png'),
						text:'关于',
						more:require('@/static/index/more.png')
					},
				]
			};
		},
		methods: {
			silder() {
				this.$store.dispatch('silder',false)
			},
			// 退出
			breakLogin(){
				let user={
					uid:'',
					upassword:'',
					uname:'请登录',
					umail:'',
					uphoto:require('@/static/index/changpian.png')
				};
				this.$store.commit('isUsering',false)
				this.$store.commit('sureLogin',user)
				uni.reLaunch({
					url: '/pages/login/login'
				});
			},
			//未登录状态跳转
			gologin(){
				if(!this.isUser){
					uni.reLaunch({
						url: '/pages/login/login'
					});
				}else{
					uni.switchTab({
						url:'/pages/user/user'
					})
				}

			},
			late(time){
				return `--tm:${time * 0.05}s`
			}
		},
		computed:{
			...mapState(['User','isUser']),
		}

	}
</script>

<style scoped lang="scss">
	.siderbarBox{
		  position: fixed;
		  top: 0;
		  left: 0;
		  height: 100%;
		  width: 100%; /* 假设宽度为80% */
		  z-index: 10; /* 设置层级，保证在其他元素之上 */
		  display: flex;
		  flex-direction: row;
		  border-radius: 0 20rpx 20rpx 0;
	}

	.siderbarBox-left{
		position: relative;
		width: 150%;
		height: 100%;
		background-color: #f0f0f2;
		border-radius: 0 20rpx 20rpx 0;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.information{
		position: relative;
		width: 320px;
		height: 150px;
		margin-top: 20px;
	}
	.avatr{
		position: absolute;
		height: 80px;
		width: 80px;
		z-index: 2;
		top: 21%;
		left: 50%;
		transform: translate(-50%, -50%);
		border-radius: 50px;
	}
	.avatr image{
		width: 90px;
		height: 90px;
		border-radius: 50px;
	}
	.person-information{
		position: relative;
		width: 100%;
		height: 100px;
		background-color: white;
		border-radius: 20px;
		top: 50px;
		z-index: 1;
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}
	.person-information-a{
		position: relative;
		width: 140px;
		height: 40px;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-top: 20px;
	}
	.person-information-b{
		position: relative;
		width: 150px;
		height: 20px;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.person-information-b p{
		font-size: 10px;
		color:#a19f9d  ;
	}

	.siderbarBox-top{
		position: relative;
		width: 95%;
		height: 40px;
		margin-top: 10px;
		align-items: center;
	}
	.saoMa{
		position: relative;
		width: 40px;
		height: 40px;
		border-radius: 15px;
		float: right;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.saoMa image{
		position: relative;
		width: 30px;
		height: 30px;
	}

	.vip{
		position: relative;
		width: 320px;
		height: 122px;
		background-color: #3e3d3f;
		margin-top: 10px;
		border-radius: 20px;
		display: flex;
		flex-direction: column;
		overflow: hidden;
	}
	.vip-text-a{
		position: relative;
		width: 100%;
		height: 32px;
		margin-top: 15px;
		display: flex;
		flex-direction: row;
		align-items: center;
		border-radius: 20px 20px 0 0 ;
	}
	.vip-text-a h2{
		font-size: 15px;
		font-weight: bold;
		margin-left: 12px;
		color: #f1d8da;
	}
	.vip-text-a p{
		font-size: 10px;
		font-weight: bold;
		margin-left: 5px;
		color: #f1d8da;
	}
	.vip-center{
		position: relative;
		width: 60px;
		height: 22px;
		margin-left: 50px;
		border-radius: 20px;
		border:solid 1px #f1d8da;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.vip-center h5{
		color: #f1d8da;
		font-size: 10rpx;
		transform: scale(0.8); /* 缩放为原来的 80% */
	}
	.dengjitiao{
		position: relative;
		width: 100px;
		height: 8px;
		margin-left: 10px;
		background-color: black;
		border-radius: 20px;
	}
	.now{
		position: relative;
		border-radius: 20px;
		width: 40%;
		height: 100%;
		background-color: white;
	}
	.vip-text-b{
		position: relative;
		width: 100%;
		height: 12px;
		display: flex;
		align-items: center;
	}
	.vip-text-b h4{
		color: #f1d8da;
		font-size: 10rpx;
		transform: scale(0.8); /* 缩放为原来的 80% */
	}
	.vip-text-b image{
		width: 20px;
		height: 20px;
	}
	.vip-wave{
		position: absolute;
		bottom: -50px;
		width: 100%;
		height: 550px;
		border-radius: 0 0 20px 20px;
	}

	.menu{
		position: relative;
		width: 320px;
		height: 412px;
		background-color: white;
		margin-top: 12px;
		border-radius: 20px;
		display: flex;
		flex-direction: column;
		align-items: center;
		overflow: hidden;
	}
	.menu-item{
		position: relative;
		width: 290px;
		height: 40px;
		margin-top: 10px;
		// background-color: #f1d8da;
		border-bottom: 1px solid #c8c6c4 ;
		display: flex;
		align-items: center;
		flex-direction: row;

		transform: translate(100%);
		animation: move 1.2s forwards;
		animation-delay: var(--tm);
	}
	.menu-item image{
		width: 22px;
		height: 22px;
		margin-left: 10px;
	}
	.menu-item p{
		font-size: 10px;
		margin-left: 10px;
		margin-right:50px;
		width: 150px;
	}
	@keyframes move {
		to{
			transform: translate(0);
		}

	}


	.again-login{
		position: relative;
		width: 80%;
    margin-left: 10%;
		height: 85px;
		background-color: white;
		margin-top: 30px;
		border-radius: 10px;
		display: flex;
		align-items: center;
		justify-content: center;
    background-color: #00B4FF;
	}
	.again-login h2{
		font-size: 14px;
		color: #6bb8fe;
	}

	.siderbarBox-right{
		position: relative;
		width: calc(100% - 240px - 40px); /* 减去child-1的宽度和两侧padding */
		height: 100%;
	}
</style>
