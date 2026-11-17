<template>
    <div class="header">
        <span class="collapse">
            <i class="el-icon-s-fold"></i>
        </span>
        <div class="user-info">
            <i class="el-icon-chat-line-square"></i>
            <i class="el-icon-full-screen" @click="handleFullScreen"></i>
            <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
                {{account}}<i class="el-icon-caret-bottom"></i>
            </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="home">
                        <i class="el-icon-s-home"></i>
                        网站首页
                    </el-dropdown-item>
                    <el-dropdown-item command="password">
                        <i class="el-icon-edit"></i>
                        修改密码
                    </el-dropdown-item>
                    <el-dropdown-item command="logout">
                        <i class="el-icon-back"></i>
                        退出登录
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>

      <el-dialog title="密码修改" :visible.sync="passwordParam.visible" @close="passwordParam.close" :close-on-click-modal="false">
        <r-form ref="myPasswordForm" :rules="passwordRules" :form="passwordForm" :items="passwordItems" :save="save"></r-form>
      </el-dialog>
    </div>
</template>

<script>
    import screenfull from "screenfull";
    import local from "../store/local";
    import {updatePassword} from "../api/user";
    import RForm from "@/components/RForm";

    export default {
        name: "Navbar",
        components: {RForm},
        data() {
            return {
                account: local.getUserInfo().account,
                passwordParam: {
                  visible: false,
                  close: this.close
                },
                passwordForm: {
                  password: '',
                  repassword: ''
                },
                passwordRules: {
                  password: [{required: true, message: "必填项不能为空"}],
                  repassword: [{required: true, message: "必填项不能为空"}],
                },
                passwordItems: [
                  {type: 'password', label: '密码', prop: 'password', name: 'password', placeholder: '请录入密码'},
                  {type: 'password', label: '重复密码', prop: 'repassword', name: 'repassword', placeholder: '请录入密码'},
                ]
            }
        },
        methods: {
            handleFullScreen() {
                screenfull.toggle();
            },
            handleCommand(type) {
                if (type === 'home') {
                    this.$router.push('/');
                }
                if (type === 'logout') {
                    this.$confirm('确定要退出吗？', '退出提示').then(() => {
                        //清除token和用户信息
                        local.remove();
                        this.$router.push('/');
                    })
                }
                if (type === 'password') {
                    this.openPassword();
                }
            },
            close() {
              this.passwordParam.visible = false;
            },
            openPassword() {
              this.passwordParam.visible =true
            },
            save() {
              let flag = this.$refs['myPasswordForm'].validateForm();
              if (flag) {
                if (this.passwordForm.password !== this.passwordForm.repassword) {
                  this.$message.error("两次输入的密码不一致");
                  return;
                }
                //执行保存
                updatePassword(this.passwordForm).then(res => {
                  this.$message.success(res.msg);
                  this.param.close();
                  this.param.callback();
                })
              }
            }
        }
    }
</script>

<style scoped>
    .header {
        height: 60px;
        background-color: #009399;
        width: 100%;
        padding: 0;
        line-height: 60px;
        display: flex;
    }

    .collapse {
        cursor: pointer;
        padding: 0 20px;
        color: #ffffff;
    }

    .user-info {
        height: 60px;
        margin-left: auto;
        width: 300px;
        padding-right: 20px;
        text-align: right;
    }

    .el-dropdown {
        margin-left: 10px;
    }

    .el-dropdown-link {
        color: #ffffff;
        cursor: pointer;
    }

    .user-info > i {
        cursor: pointer;
        padding: 10px 15px;
        color: #ffffff;
    }
</style>