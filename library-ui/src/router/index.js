import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: '首页',
    component: () => import("@/views/home.vue"),
    children:[
      {
        path: '/user/list',
        name: '用户列表',
        component: () => import('@/views/user/index')
      },
      {
        path: '/role/list',
        name: '角色列表',
        component: () => import('@/views/role/index')
      }
    ]
  },
  {
    path: '/',
    name: '登录界面',
    component: () => import('@/views/login.vue')
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
