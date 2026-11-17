<template>
    <div>
        <r-query-form :form="form" :items="items" @search="search"></r-query-form>
        <div class="btn-panel">
            <el-button size="small" type="danger" icon="el-icon-delete" @click="del">删除</el-button>
        </div>

        <r-table ref="mutipleTable" :tableData="tableData" :tableCols="tableCols">
            <template slot="sex_slot" slot-scope="scope">
                <span v-if="scope.data.sex==0">女</span>
                <span v-else>男</span>
            </template>
        </r-table>
        <r-pagination :page="page" :total="total" @handleCurrentChange="handleCurrentChange"></r-pagination>
    </div>
</template>

<script>
    import {query, del} from "@/api/stuJobInfo";
    import RTable from "../../components/RTable";
    import RPagination from "../../components/RPagination";
    import RQueryForm from "../../components/RQueryForm";
    import {dict_query} from "@/api/dict";

    export default {
        name: "StuJobList",
        components: {RQueryForm, RPagination, RTable},
        data() {
            return {
                tableData: [],
                tableCols: [
                    {prop: 'id', label: 'ID', width: 80},
                    {prop: 'name', label: '姓名'},
                    {prop: 'grade', label: '学级'},
                    {prop: 'stuNo', label: '学号'},
                    {prop: 'employment', label: '就业与否'},
                    {prop: 'companyName', label: '就业公司名称'},
                    {prop: 'companyType', label: '单位性质'},
                    {prop: 'post', label: '工作岗位', slot: 'sex_slot'},
                    {prop: 'salary', label: '工资数'},
                ],
                total: 0,
                page: 1,
                form: {
                    name: ''
                },
                items: [
                    {type: 'text', label: '姓名', name: 'name', placeholder: '按姓名查询'},
                    {type: 'text', label: '学号', name: 'stuNo', placeholder: '按学号查询'},
                    {type: 'select', label: '单位性质', prop: 'companyType', name: 'companyType', placeholder: '国企或私企', options: []},
                    {type: 'text', label: '工资', name: 'salary', placeholder: '按工资查询'},
                ],
            }
        },
        mounted() {
            this.getDictList1(7);
            this.list({});
        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                let params = this.form;
                params.page = this.page;
                this.list(params);
            },
            search() {
                this.list(this.form);
            },
            list(params) {
                query(params).then(res => {
                    this.tableData = res.data;
                    this.total = res.total;
                })
            },
            getDictList1(typeId) {
              dict_query({typeId: typeId}).then(res => {
                let arr = [];
                res.data.forEach(item => {
                  let p = {value: item.dictKey, label: item.dictVal};
                  arr.push(p);
                });
                this.items.forEach(item => {
                  if (item.type == 'select' && item.name == 'companyType') {
                    item.options = arr;
                  }
                })
              })
            },
            del() {
                let selections = this.$refs['mutipleTable'].selection;
                if (selections.length > 0) {
                    this.$confirm("确定有删除吗？", "删除提示").then(() => {
                        let arr = selections.map(item => item.id);
                        let ids = arr.join(',');
                        del(ids).then(res => {
                            this.$message.success(res.msg);
                            this.search();
                        })
                    }).catch(() => {
                    })
                } else {
                    this.$message.warning("请选择要删除的数据");
                }
            }
        }
    }
</script>

<style scoped>

</style>