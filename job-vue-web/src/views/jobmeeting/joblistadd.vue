<template>
    <el-dialog title="新增" :visible.sync="param.visible" @close="param.close" :close-on-click-modal="false">
        <r-form ref="myForm" :rules="rules" :form="form" :items="items" :save="save"></r-form>
    </el-dialog>
</template>

<script>
    import {create} from "@/api/experience";
    import {query} from "@/api/resume";
    import RForm from "@/components/RForm";
    import {dict_query} from "@/api/dict";

    export default {
        name: "Add",
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
                    {type: 'text', label: '公司名称', prop: 'resumeId', name: 'resumeId', placeholder: '招聘会名称', options: []},
                    {type: 'date', label: '招聘岗位', prop: 'company', name: 'company', placeholder: '举办时间'},
                    {type: 'text', label: '招聘人数', prop: 'post', name: 'post', placeholder: '举办地点'},
                ]
            }
        },
        mounted() {
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
                    create(this.form).then(res => {
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