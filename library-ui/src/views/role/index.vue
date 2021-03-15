<template>
  <div>
    <!-- 搜索框 -->
    <el-row :gutter="20">
      <el-form :model="queryParams" ref="queryParams" :inline="true" label-width="68px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="权限字符" prop="resources">
          <el-input
            v-model="queryParams.resources"
            placeholder="请输入手机号码"
            clearable
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

    <!-- 角色列表 -->
    <el-row :gutter="20">
      <el-table :data="roleList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="id" label="角色编号" width="110">
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" width="110">
        </el-table-column>
        <el-table-column prop="description" label="角色描述" width="100">
        </el-table-column>
        <el-table-column prop="resourceNamesStr" label="权限详情" width="180">
        </el-table-column>
        <el-table-column label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
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
          <el-form-item label="角色名" prop="roleName">
            <el-input v-model="form.roleName" placeholder="请输入角色名" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="权限列表" prop="resources">
            <el-input v-model="form.resources" placeholder="请选择权限" maxlength="11" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色描述">
              <el-input v-model="form.desc" type="textarea" placeholder="请输入角色描述"></el-input>
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

<script>
  import {listRole, getRole, addRole, updateRole, deleteRole} from '@/network/role';
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
        //表单内容
        form: {},
        roleList: [],
        // 查询参数
        queryParams: {
          roleName: '',
          resources: '',
        },
        // 表单校验
        rules: {
          roleName: [
            { required: true, message: "角色名不能为空", trigger: "blur" }
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
        listRole(this.queryParams).then(response => {
          if(response != null) {
            this.roleList = response;
          }
        });
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
          RoleName: undefined,
          desc: undefined,
          resources: undefined,
          status: "1",
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
        this.title = "添加角色";
      },
      /** 修改按钮操作 */
      handleUpdate() {
        this.reset();
        const roleId = this.ids[0];
        getUser(roleId).then(response => {
          this.form = response;
          this.form.gender='';
          this.open = true;
          this.title = "修改角色";
        });
      },
      /** 删除按钮操作 */
      handleDelete() {
        const roleIds = this.ids;
        this.$confirm('是否确认删除角户编号为"' + roleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return deleteRole(roleIds);
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
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              this.typeChange();
              updateRole(this.form).then(response => {
                this.open = false;
                this.getList();
              });
            } else {
              this.typeChange();
              console.log(this.form);
              addRole(this.form).then(response => {
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
