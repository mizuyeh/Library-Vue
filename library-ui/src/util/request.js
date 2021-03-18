import axios from 'axios'
import {Message} from 'element-ui'
import router from '../router'
import {getToken} from '../util/auth'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: "http://localhost:8181",
  // 超时
  timeout: 10000
})
// request拦截器
service.interceptors.request.use(config => {
  //如果存在token，请求中携带token
  if (getToken()) {
    console.log(getToken());
    config.headers['Authorization'] = getToken();
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?';
    for (const propName of Object.keys(config.params)) {
      const value = config.params[propName];
      var part = encodeURIComponent(propName) + "=";
      if (value !== null && typeof(value) !== "undefined") {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            let params = propName + '[' + key + ']';
            var subPart = encodeURIComponent(params) + "=";
            url += subPart + encodeURIComponent(value[key]) + "&";
          }
        } else {
          url += part + encodeURIComponent(value) + "&";
        }
      }
    }
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    if(res.data.code){
      // 未设置状态码则默认成功状态
      const code = res.data.code || 200;
      
      if(code == 500 || code == 401 || code == 403) {
        Message({
          message: res.data.msg,
          type: 'error'
        });
        return;
      }
    }
    return res.data
  },
  error => {
    // if(error.data.code) {
    //   const code = error.response.code;
    //   if(code == 504 || code == 404) {
    //     Message({
    //       message: '服务器被吃了o(╯□╰)o',
    //       type: 'error'
    //     });
    //   } else if (code == 403) {
    //     Message({
    //       message: '权限不足，请联系管理员',
    //       type: 'error'
    //     });
    //   } else if (code == 401) {
    //     Message({
    //       message: '尚未登录，请登录',
    //       type: 'error'
    //     });
    //     router.replace('/');
    //   } else {
    //     if(error.response.msg) {
    //       Message({
    //         message: error.response.msg,
    //         type: 'error'
    //       });
    //     } else {
    //       Message({
    //         message: '未知错误！',
    //         type: 'error'
    //       });
    //     }
    //   }
    // }
    
    return;
  }
)

export default service