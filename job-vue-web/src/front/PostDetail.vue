<template>
    <div v-title>
        <Header></Header>
        <div class="main mar-20">
            <el-breadcrumb>
                <el-breadcrumb-item :to="{path:'/'}">首页</el-breadcrumb-item>
                <el-breadcrumb-item>
                    <router-link :to="`/post_list`">职位</router-link>
                </el-breadcrumb-item>
                <el-breadcrumb-item>详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!-- 焦点区 -->
        <div class="main mar-20">
            <el-row :gutter="15">
                <el-col :span="16">
                  <el-descriptions class="margin-top" :column="1" border :labelStyle="{width: '100px'}">
                    <el-descriptions-item>
                      <template slot="label">
                        职位名称
                      </template>
                      {{ post.name }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        学历要求
                      </template>
                      {{ post.education }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        学历要求
                      </template>
                      {{ post.salary }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template slot="label">
                        职位描述
                      </template>
                      <mavon-editor :value="post.description"
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
                </el-col>
                <el-col :span="8">
                    <r-card :channelId="18"></r-card>
                </el-col>
            </el-row>
        </div>
        <Footer></Footer>
    </div>
</template>

<script>
    import '../assets/css/styles.css'
    import Header from "./Header";
    import Footer from "./Footer";
    import {post_detail} from "../api/front";
    import RCard from "./RCard";

    import Vue from 'vue'
    import mavonEditor from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    // use
    Vue.use(mavonEditor)

    export default {
        name: "Index",
        components: {Footer, Header, RCard},
        data() {
            return {
                post: {},
            }
        },
        mounted() {
            this.getPost(this.$route.params.id);
        },
        methods: {
          getPost(id) {
            post_detail(id).then(res => {
              this.post = res.data;
            })
          }
        }
    }
</script>

<style scoped>

</style>