<template>
    <r-table ref="mutipleTable" :tableData="tableData" :tableCols="tableCols">
            <template slot="slot_resume" slot-scope="scope">
                {{scope.data.name}}
            </template>
        </r-table>
    
    
</template>

<script>
    import {update} from "@/api/joblist";
    import {getByIdQuery} from "@/api/joblist";
    import {dict_query} from "@/api/dict";


    export default {
        name: "joblist",
        props: {
            param: {
                type: Object,
                default: () => {
                }
            }
        },
        data() {
            return {
                total: 0,
                page: 1,
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
                edit: {
                    visible: false,
                    close: this.close,
                    callback: this.search,
                    form: null,
                },
                joblist: {
                    visible: false,
                    close: this.close,
                    callback: this.search,
                    form: null,
                },
            }
        },
        mounted() {
            this.form = JSON.parse(JSON.stringify(this.param.form));
            this.getDictList1(3);

            getByIdQuery({}).then(res => {
                let arr = [];
                res.data.forEach(item => {
                    let param = {value: item.id, label: item.name}
                    arr.push(param)
                })
                this.items[0].options = arr;
            });
        },
        methods: {
            getDictList1(typeId) {
                dict_query({typeId: typeId}).then(res => {
                    let arr = [];
                    res.data.forEach(item => {
                        let p = {value: item.dictKey, label: item.dictVal};
                        arr.push(p);
                    });
                    this.items.forEach(item => {
                        if (item.type == 'select' && item.name == 'salary') {
                            item.options = arr;
                        }
                    })
                })
            },
            save() {
                let flag = this.$refs['myForm'].validateForm();
                if (flag) {
                    update(this.form).then(res => {
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

</style>