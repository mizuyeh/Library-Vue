<template>
  <div>
    <!-- 搜索框 -->
    <el-row :gutter="20">
      <el-form :model="queryParams" ref="queryParams" :inline="true" label-width="68px">
        <el-form-item label="用户名称" prop="userName">
          <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input
              v-model="queryParams.mobile"
              placeholder="请输入手机号码"
              clearable
              :mode="queryParams.mobile"
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
        </el-form-item>
        <el-form-item>
            <el-button @click="handleQuery" type="primary" icon="el-icon-search" size="mini" >搜索</el-button>
            <el-button @click="resetForm('queryParams')" icon="el-icon-refresh" size="mini">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <!-- 新增、修改、删除按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <!-- 用户列表 -->
    <el-row :gutter="20">
      <el-table :data="userList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="id" label="用户编号" width="110">
        </el-table-column>
        <el-table-column prop="userName" label="用户名称" width="110">
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="100">
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="180">
        </el-table-column>
        <el-table-column prop="mobile" label="手机号码" width="160">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
        </el-table-column>
          <el-table-column prop="updateTime" label="上次修改时间" width="180">
        </el-table-column>
      </el-table>
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="identityCode">
              <el-input v-model="form.identityCode" placeholder="请输入身份证号码" 
                        maxlength="18" minlength="18"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="mobile">
              <el-input v-model="form.mobile" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.id == undefined" label="登录名" prop="loginName">
              <el-input v-model="form.loginName" placeholder="请输入登录名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.id == undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.gender" placeholder="请选择">
                <el-option
                  v-for="item in genderOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="item in statusOptions"
                  :key="item"
                  :label="item"
                >{{item}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  
</style>

<script>
  import {listUser, getUser, addUser, updateUser, deleteUser} from '@/network/user';
  export default {
    name: 'User',
    data() {
      return {
        //列表选中数组
        ids: [],
        //对话框titile
        title: '',
        //是否隐藏按钮
        single: false,
        //对话框是否打开,默认不打开
        open: false,
        //性别选项
        genderOptions: ['男', '女'],
        //状态选项
        statusOptions: ['正常', '停用'],
        //表单内容
        form: {},
        userList: null,
        // 查询参数
        queryParams: {
          userName: '',
          mobile: '',
        },
        // 表单校验
        rules: {
          userName: [
            { required: true, message: "用户名称不能为空", trigger: "blur" }
          ],
          loginName: [
            { required: true, message: "登录名不能为空", trigger: "blur" }
          ],
          password: [
            { required: true, message: "用户密码不能为空", trigger: "blur" }
          ],
          email: [
            {
              type: "email",
              message: "'请输入正确的邮箱地址",
              trigger: ["blur", "change"]
            }
          ],
          mobile: [
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: "请输入正确的手机号码",
              trigger: "blur"
            }
          ]
        }
      };
    },
    created() {
     this.getList();
    },
    methods: {
      /** 查询用户列表 */
      getList() {
        listUser(this.queryParams).then(response => {
            // console.log(response);
            this.userList = response;
          }
        );
      },
      /* 重置表单 */
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.getList();
      },
      /* 搜索框事件 */
      handleQuery() {
        this.getList();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          identityCode: undefined,
          userName: undefined,
          loginName: undefined,
          password: undefined,
          mobile: undefined,
          email: undefined,
          gender: undefined,
          status: "0",
          remark: undefined
        }
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        // console.log(this.ids.length);
        this.single = (this.ids.length==1?false:true);
        // console.log("single:" + this.single);
      },
       /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加用户";
      },
       /** 修改按钮操作 */
      handleUpdate() {
        this.reset();
        const userId = this.ids[0];
        getUser(userId).then(response => {
          this.form = response;
          this.form.gender='';
          this.open = true;
          this.title = "修改用户";
        });
      },
       /** 删除按钮操作 */
      handleDelete() {
        const userIds = this.ids;
        this.$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return deleteUser(userIds);
          }).then(() => {
            this.getList();
            this.$message("删除成功");
          })
      },
       // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
       //性别和状态类型转换
      typeChange: function() {
        var gender = this.form.gender;
        this.form.gender=(gender=='男'?1:0);
        var status = this.form.status;
        this.form.status=(status=='正常'?1:0);
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              this.typeChange();
              updateUser(this.form).then(response => {
                this.open = false;
                this.getList();
              });
            } else {
              this.typeChange();
              console.log(this.form);
              addUser(this.form).then(response => {
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
    },
  };
</script>