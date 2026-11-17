<template>
    <div>
        <div class="btn-panel">
            <el-button size="small" type="primary" icon="el-icon-plus" @click="detail">详情</el-button>
            <el-button size="small" type="danger" icon="el-icon-delete" @click="del">删除</el-button>
        </div>
        <r-table ref="mutipleTable" :tableData="tableData" :tableCols="tableCols">
            <template slot="slot_post" slot-scope="scope">
                {{scope.data.post.name}}
            </template>
            <template slot="slot_company" slot-scope="scope">
                {{scope.data.company.name}}
            </template>
            <template slot="slot_student" slot-scope="scope">
                {{scope.data.student.name}}
            </template>
            <template slot="slot_resume" slot-scope="scope">
                {{scope.data.resume.name}}
            </template>
            <template slot="slot_status" slot-scope="scope">
                <!--0待查看|1已查看|2有意向|3不合适-->
                <el-tag v-if="scope.data.status == 0">待查看</el-tag>
                <el-tag type="info" v-if="scope.data.status == 1">已查看</el-tag>
                <el-tag type="success" v-if="scope.data.status == 2">有意向</el-tag>
                <el-tag type="danger" v-if="scope.data.status == 3">不合适</el-tag>
            </template>
        </r-table>
        <r-pagination :page="page" :total="total" @handleCurrentChange="handleCurrentChange"></r-pagination>

      <el-dialog
          :visible.sync="sendlist.visible"
          width="80%"
          title="简历详情"
          @close="closeDialog"
      >
        <div style="height:800px;text-align:center">
          <el-descriptions class="margin-top" :title="sendData.student.name + '的个人简历'" :column="1" border :labelStyle="{width: '100px'}">
            <template slot="extra">
              <span><el-input type="text" style="width: 400px;margin-right:10px;" v-model="form.reply" placeholder="请输入回复内容"></el-input></span>
              <el-button type="success" size="small" @click="update('2')">有意向</el-button>
              <el-button type="danger" size="small" @click="update('3')">不合适</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                简历名称
              </template>
              {{ sendData.resume.name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                求职状态
              </template>
              {{ sendData.resume.jobStatus }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                自我评价
              </template>
              <mavon-editor :value="sendData.resume.evaluate"
                            :toolbarsFlag="false"
                            :subfield="false"
                            :shortCut="false"
                            :autofocus="false"
                            :ishljs="true"
                            :boxShadow="false"
                            fontSize="16px"
                            previewBackground="#FFFFFF"
                            defaultOpen="preview" />
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                技能描述
              </template>
              <mavon-editor :value="sendData.resume.skill"
                            :toolbarsFlag="false"
                            :subfield="false"
                            :shortCut="false"
                            :autofocus="false"
                            :ishljs="true"
                            :boxShadow="false"
                            fontSize="16px"
                            previewBackground="#FFFFFF"
                            defaultOpen="preview" />
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    import {query, update} from "@/api/send";
    import {message} from "@/utils/message";
    import {del} from '@/api/send'
    import RTable from "@/components/RTable";
    import RPagination from "@/components/RPagination";

    export default {
        name: "Send",
        components: {RPagination, RTable},
        data() {
            return {
                total: 0,
                page: 1,
                tableData: [],
                tableCols: [
                    {prop: 'id', label: 'ID', width: 80},
                    {prop: 'postId', label: '职位', slot: 'slot_post'},
                    {prop: 'companyId', label: '公司', slot: 'slot_company'},
                    {prop: 'studentId', label: '学生姓名', slot: 'slot_student'},
                    {prop: 'resumeId', label: '简历名称', slot: 'slot_resume'},
                    {prop: 'status', label: '状态', slot: 'slot_status'},
                    {prop: 'sendDate', label: '投递日期'},
                ],
                form: {
                  id: '',
                  status: '',
                  reply: ''
                },
                items: [
                    {type: 'text', label: '职位名称', name: 'postId', placeholder: '按关键字查询'},
                ],
                sendlist: {
                  visible: false,
                  close: this.close
                },
                sendData: {
                  student: {},
                  resume: {},
                },
            }
        },
        mounted() {
            this.list({})
        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                let params = this.form;
                params.page = this.page
                console.log(params);
                this.list(params);
            },
            list(params) {
                query(params).then(res => {
                    this.tableData = res.data;
                    this.total = res.total;
                })
            },
            detail() {
              let selection = this.$refs['mutipleTable'].selection;
              if (selection.length == 1) {
                this.sendlist.visible =true
                this.sendData = selection[0];
                this.form.id = this.sendData.id
                this.form.reply = this.sendData.reply
                if (this.sendData.status == 0) {
                  this.update(1);
                }
              } else {
                message.warning('请选择一条数据查看')
              }
            },
            closeDialog() {
              this.sendlist.visible = false;
              this.list({})
            },
            update(status) {
              this.form.status = status
              update(this.form).then(res => {
                if (status != 1) {
                  this.$message.success(res.msg);
                }
                this.param.close();
                this.param.callback();
              })
            },
            del() {
                let selection = this.$refs['mutipleTable'].selection;
                if (selection.length > 0) {
                    this.$confirm('确定要删除吗', '删除提示').then(() => {
                        let arr = selection.map(item => item.id);
                        let ids = arr.join(',');
                        del(ids).then(res => {
                            message.success(res.msg);
                            this.search()
                        })
                    }).catch(() => {
                    })
                } else {
                    message.warning('请选择要删除的数据')
                }
            }
        }
    }
</script>

<style scoped>

</style>