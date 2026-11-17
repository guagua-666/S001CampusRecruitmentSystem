<template>
    <div>
        <div>
        <r-query-form :form="form" :items="items" @search="search"></r-query-form>
        <div class="btn-panel">
            <el-button size="small" type="primary" icon="el-icon-plus" @click="create">新增</el-button>
            <el-button size="small" type="primary" icon="el-icon-edit" @click="update">修改</el-button>
            <el-button size="small" type="danger" icon="el-icon-delete" @click="del">删除</el-button>
            <el-button size="small" type="primary" icon="el-icon-plus" @click="open">查看详情</el-button>
        </div>
        <r-table ref="mutipleTable" :tableData="tableData" :tableCols="tableCols">
            <template slot="slot_resume" slot-scope="scope">
                {{scope.data.name}}
            </template>
        </r-table>
        <r-pagination :page="page" :total="total" @handleCurrentChange="handleCurrentChange"></r-pagination>
        <Add v-if="add.visible" :param="add"></Add>
        <Edit v-if="edit.visible" :param="edit"></Edit>
        <JobList v-if="joblist.visible" :param="joblist"></JobList>
    </div>

    <div>
        <el-dialog
            :visible.sync="joblist.visible"
            width="60%"
            title="详情"
            @close="closeDialog"
        >
          <div style="height:300px;text-align:center">
            <el-descriptions class="margin-top"  :column="2" border :labelStyle="{width: '100px'}">
              <el-descriptions-item>
                <template slot="label">
                  招聘会名称
                </template>
                {{ joblistData.name }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  举办时间
                </template>
                {{ joblistData.jobDate }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  举办地点
                </template>
                {{ joblistData.jobPlace }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  参会单位
                </template>
                {{ joblistData.jobCompany }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-dialog>
    </div>

</div>
   
</template>

<script>
    import {query} from "@/api/joblist";
    import Add from './Add';
    import Edit from "./Edit";
    import JobList from "./joblist";
    import {message} from "@/utils/message";
    import {del} from '@/api/joblist'
    import RTable from "@/components/RTable";
    import RPagination from "@/components/RPagination";
    import RQueryForm from "../../components/RQueryForm";
    import {getByIdQuery} from "@/api/joblist";

    export default {
        name: "Jobmeeting",
        components: {RQueryForm, RPagination, Edit, Add, RTable,JobList},
        data() {
            return {
                total: 0,
                page: 1,
                joblistData:[],
                jobCols: [
                    {prop: 'id', label: 'ID', width: 80},
                    {prop: 'name', label: '公司名称', slot: 'slot_resume'},
                    {prop: 'jobPlace', label: '招聘岗位'},
                    {prop: 'jobCompany', label: '招聘人数'},
                    // {prop: 'jobUrl', label: '招聘地址'},
                ],
                tableData: [],
                tableCols: [
                    {prop: 'id', label: 'ID', width: 80},
                    {prop: 'name', label: '招聘会名称', slot: 'slot_resume'},
                    {prop: 'jobDate', label: '举办时间'},
                    {prop: 'jobPlace', label: '举办地点'},
                    {prop: 'jobCompany', label: '参会单位'},
                ],
                form: {
                    name: '',
                },
                items: [
                    {type: 'text', label: '招聘会名称', name: 'name', placeholder: '按招聘会名称查询'},
                ],
                add: {
                    visible: false,
                    close: this.close,
                    callback: this.search
                },
                joblistadd: {
                    visible: false,
                    close: this.close,
                    callback: this.search
                },
                edit: {
                    visible: false,
                    close: this.close,
                    callback: this.search,
                    form: null,
                },
                joblist: {
                    visible: false,
                    close: this.close
                },
            }
        },
        mounted() {
            this.list({})
        },
        methods: {
            closeDialog() {
              this.list({})
            },
            /*展示具体类型项目*/
            open() {
                let selection = this.$refs['mutipleTable'].selection;
                if (selection.length == 1) {
                   this.joblist.visible =true
                    this.joblistData = selection[0];
                } else {
                    message.warning('请选择一条数据查看')
                }
            },
            handleCurrentChange(val) {
                this.page = val;
                let params = this.form;
                params.page = this.page
                console.log(params);
                this.list(params);
            },
            search() {
                this.list(this.form);
            },
            list(params) {
                query(params).then(res => {
                    this.tableData = res.data;
                    this.total = res.total;
                    console.log(this.tableData)
                })
            },
            close() {
                this.add.visible = false;
                this.edit.visible = false;
                this.joblist.visible = false;
            },
            jobcreate(){
                this.joblistadd.visible=true
            },
            create() {
                this.add.visible = true;
            },
            update() {
                let selection = this.$refs['mutipleTable'].selection;
                if (selection.length == 1) {
                    this.edit.visible = true;
                    this.edit.form = selection[0]
                } else {
                    message.warning('请选择一条数据修改')
                }
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