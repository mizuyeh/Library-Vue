import request from "./request";

//查询所用用户
export function listUser(query){
  return request({
    url: '/user/list',
    method: 'get',
    params: query
  })
}

//查单个用户
export function getUser(id){
  return request({
    url: '/user/id/' + id,
    method: 'get'
  })
}



//新增用户
export function addUser(data){
  return request({
    url: '/user/save',
    method: 'post',
    data: data
  })
}

//修改用户
export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data: data
  })
}

//删除用户
export function deleteUser(ids) {
  return request({
    url: '/user/delete/' + ids,
    method: 'delete'
  })
}