// ============  主页路由MODEL  ==================
import Layout from '@/compoents/layouts/layout_main'
import Home from '@/pages/home/index'
import Article from '@/pages/article/index'
import Search from '@/pages/search/index'
import Login from '@/pages/login/index'
import Screen from '@/pages/load_screen/index'
import SearchResult from '@/pages/search_result/index'
import UserInfo from "@/pages/user/userInfo.vue";
import LoginC from "@/compoents/login/loginC.vue";
import Register from "@/compoents/login/register.vue";

let routes = [
    {
        path: '/',
        component: Layout,
        children:[
            {
                path:'/home',
                name:'Home',
                component: Home
            },
            {
                path: '/user',
                name: 'User-Home',
                component: UserInfo,
                props: true
            }

        ]
    },
    {
        path:'/screen',
        name: 'screen',
        component:Screen
    },
	{
        path:'/login',
        name: 'login',
        component:Login,
        redirect:'/login/user',
        children: [
            {
                path:'/login/user',
                name:'LoginC',
                component: LoginC
            },
            {
                path:'/login/register',
                name:'Register',
                component: Register

            }
        ]
    },
	{
        path:'/article',
        name: 'article-info',
        component:Article,
        props:true
    },{
        path:'/search',
        name: 'search',
        component:Search
    },{
        path:'/search_result',
        name: 'search_result',
        component:SearchResult,
        props:true
    },

]

export default routes;
