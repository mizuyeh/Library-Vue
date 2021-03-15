import request from "./request";

//查询所用角色
export function listRole(query){
    return request({
        url: '/role/list',
        method: 'get',
        params: query
    })
}

//查单个角色
export function getRole(id){
    return request({
        url: '/role/id/' + id,
        method: 'get'
    })
}

//新增角色
export function addRole(data){
    return request({
        url: '/role/save',
        method: 'post',
        data: data
    })
}

//修改角色
export function updateRole(data) {
    return request({
        url: '/role/update',
        method: 'put',
        data: data
    })
}

//删除角色
export function deleteRole(ids) {
    return request({
        url: '/role/delete/' + ids,
        method: 'delete'
    })
}