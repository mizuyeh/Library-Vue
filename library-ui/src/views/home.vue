<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>王小虎</span>
      </el-header>
      
      
      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu>
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-user"></i>用户管理</template>
                <el-menu-item index="1-1">
                  <router-link to="/user/list" tag="div">用户列表</router-link>
                </el-menu-item>
                <el-menu-item index="1-2">
                  <router-link to="/user/add" tag="div">新增用户</router-link>
                </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }

  .router-link-active{
    color: rgb(150, 223, 67);
  }
</style>

<script>
  export default {
   
  };
</script>