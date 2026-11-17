<template>
    <div>
        <r-form ref="myForm" :rules="rules" :form="form" :items="items" :save="save"></r-form>
    </div>
</template>

<script>
    import RForm from "@/components/RForm";
    import {stuJobInfoInfo, create, update} from '@/api/stuJobInfo'
    import {dict_query} from "@/api/dict";

    export default {
        name: "JobInfo",
        components: {RForm},
        data() {
            return {
                form: {
                    id: '',
                    name: '',
                    birthday: '',
                    college: '',
                    education: '',
                    phone: '',
                    sex: '',
                    photo: '',
                    graduateDate: ''
                },
                rules: {
                    name: [{required: true, message: '必填项不能为空'}]
                },
                items: [
                    {type: 'text', label: '学级', prop: 'grade', name: 'grade', placeholder: '请输入学级'},
                    {type: 'text', label: '学号', prop: 'stuNo', name: 'stuNo', placeholder: '请输入学号'},
                    {type: 'select', label: '就业与否', prop: 'employment', name: 'employment', placeholder: '就业与否', options: [{label: '是',value: '是'},{label: '否',value: '否'}]},
                    {type: 'text', label: '就业公司名称', prop: 'companyName', name: 'companyName', placeholder: '就业公司名称'},
                    {type: 'select', label: '单位性质', prop: 'companyType', name: 'companyType', placeholder: '国企或私企', options: []},
                    {type: 'text', label: '工作岗位', prop: 'post', name: 'post', placeholder: '工作岗位'},
                    {type: 'text', label: '工资数', prop: 'salary', name: 'salary', placeholder: '工资数'},
                ]
            }
        },
        mounted() {
            this.getDictList1(7);
            this.getInfo();
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
                        if (item.type == 'select' && item.name == 'companyType') {
                            item.options = arr;
                        }
                    })
                })
            },
            getInfo() {
              stuJobInfoInfo().then(res => {
                if (res.data) {
                  this.form = res.data;
                }
              })
            },
            save() {
                let flag = this.$refs['myForm'].validateForm();
                if (flag) {
                  console.log(this.form.id, 'id');
                  if (this.form.id) {
                    update(this.form).then(res => {
                      this.$message.success(res.msg);
                      this.param.close();
                      this.param.callback();
                    })
                  } else {
                    create(this.form).then(res => {
                      this.$message.success(res.msg);
                      this.param.close();
                      this.param.callback();
                    })
                  }
                }
            }
        }
    }
</script>

<style scoped>

</style>