<script setup lang="ts">
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';

const router = useRouter();

const props = defineProps<{
  postId: string | number
}>()

const post = ref({
  id: 0,
  title: "",
  content: ""
})

// 게시글 불러오기
onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data
  })
})

// 수정 함수 정의
const edit = () => {
axios.patch(`/api/posts/${props.postId}`, {
  title: post.value.title,
  content: post.value.content
}).then(() => {
    router.replace({ name: 'read', params: { postId: props.postId } })
  })
}
</script>

<template>
<div>
<el-input v-model="post.title"/>
</div>

<div class="mt-2">
  <el-input type="textarea" v-model="post.content" :rows="15" placeholder="내용을 입력하세요"/>
</div>

<div class="mt-2">
<el-button type="warning" @click="edit()">수정완료</el-button>
</div>

</template>

<style scoped>
</style>