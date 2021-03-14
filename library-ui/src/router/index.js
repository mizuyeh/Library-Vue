import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '首页',
    component: () => import("@/views/home.vue"),
    children:[
      {
        path: '/user/list',
        name: '用户列表',
        component: () => import('@/views/user/list.vue')
      },
      {
        path: '/user/add',
        name: '新增用户',
        component: () => import('@/views/user/add.vue')
      }
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
