<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';


const title = ref("")
const content = ref("")

const router = useRouter()

const write = function() {
axios.post("/api/posts", {
title: title.value,
content: content.value
})
.then(() => {
    router.replace({name: "home"});
});
};
</script>

<template>
<div>
<el-input v-model="title" placeholder="제목을 입력해주세요."/>
</div>

<div class="mt-2">
  <el-input type="textarea" v-model="content" :rows="15" placeholder="내용을 입력하세요"/>
</div>

<div class="mt-2">
<el-button type="primary" @click="write()">작성완료</el-button>
</div>

</template>

<style scoped>
</style>