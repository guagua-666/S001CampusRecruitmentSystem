<template>
    <el-dialog title="修改" :visible.sync="param.visible" @close="param.close" :close-on-click-modal="false">
        <r-form ref="myForm" :rules="rules" :form="form" :items="items" :save="save"></r-form>
    </el-dialog>
</template>

<script>
    import {update} from "@/api/joblist";
    import {query} from "@/api/resume";
    import RForm from "@/components/RForm";
    import {dict_query} from "@/api/dict";

    export default {
        name: "Edit",
        components: {RForm},
        props: {
            param: {
                type: Object,
                default: () => {
                }
            }
        },
        data() {
            return {
                form: {
                    id: '',
                    resumeId: '',
                    company: '',
                    post: '',
                    salary: '',
                    description: '',
                    joinDate: '',
                    leaveDate: '',
                },
                rules: {
                    resumeId: [{required: true, message: '必填项不能为空'}],
                    company: [{required: true, message: '必填项不能为空'}],
                    post: [{required: true, message: '必填项不能为空'}],
                },
                items: [
                    {type: 'text', label: '招聘会名称', prop: 'name', name: 'name', placeholder: '招聘会名称', options: []},
                    {type: 'date', label: '举办时间', prop: 'jobDate', name: 'jobDate', placeholder: '举办时间'},
                    {type: 'text', label: '举办地点', prop: 'jobPlace', name: 'jobPlace', placeholder: '举办地点'},
                    {type: 'text', label: '参会单位', prop: 'jobCompany', name: 'jobCompany', placeholder: '参会单位'},
                    
                ]
            }
        },
        mounted() {
            this.form = JSON.parse(JSON.stringify(this.param.form));
            this.getDictList1(3);

            query({}).then(res => {
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