<template>
  <div>
    <table>
      <tr>
        <td>用户名</td>
        <td>邮箱</td>
        <td>手机号</td>
      </tr>
      <tr v-for="(item,index) in users" :key="index">
        <td >{{item.userName}}</td>
        <td >{{item.email}}</td>
        <td >{{item.mobile}}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "User-static",
  data() {
    return {
      users:[
        {
          name: 'admin',
          email: 'admin@kickshaw.com',
          phoneNum: '18888888888'
        },
        {
          name: '张三',
          email: 'zhangsan@kickshaw.com',
          phoneNum: '18888888888'
        },
        {
          name: '李四',
          email: 'lisi@kickshaw.com',
          phoneNum: '18888888888'
        }
      ]
    }
  },
  created() {
    const _this = this;
    axios.get("http://localhost:8181/user/list").then(function(response){
      console.log(response);
      _this.users = response.data
    })
  },  
}
</script>