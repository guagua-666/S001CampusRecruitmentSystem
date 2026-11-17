<template>
    <div id="app"  v-title data-title="高校毕业生就业管理系统">
        <router-view></router-view>
    </div>
</template>

<script>

    export default {
        name: 'App',
        components: {}
    }
</script>

<style>
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }

    #app {
        height: 100%;
    }
</style>
