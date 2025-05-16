<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

// props 정의 (string으로 받음)
const props = defineProps<{
  postId: string
}>()

const post = ref<any>(null)

const router = useRouter()

const moveToEdit = () => {
  console.log("수정 페이지 이동 시도 - postId:", props.postId)
  router.push({ name: "edit", params: { postId: props.postId } })
}

onMounted(async () => {
  try {
    const response = await axios.get(`/api/posts/${props.postId}`)
    post.value = response.data
  } catch (error) {
    console.error("API 호출 오류:", error)
  }
})
</script>

<template>
  <div v-if="post">
    <el-row>
      <el-col>
    <h2 class="title">{{ post.title }}</h2>

    <div calss="sub d-flex">
    <div class="catrgory">개발</div>
    <div class="regDate">2025-05</div>
    </div>
    </el-col>
    </el-row>

    <el-row class="mt-3">
    <el-col>
    <div class="content">{{ post.content }}</div>
</el-col>
</el-row>
</div>
 <el-row class="mt-3">
<el-col>
<div class="d-flex justify-content-end">
  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</div>
</el-col>
</el-row>
</template>

<style scoped lang="scss">
.title{
font-size: 1.6rem;
font-weight: 600;
color: #383838;
margin: 0;
}

.sub{
margin-top: 10px;
font-size: 0.78rem;

.regDate{
margin-left: 10px;
color: #6b6b6b;
}
}

.content{
font-size: 0.95rem;
margin-top: 8px;
color: #7e7e7e;
white-space: break-spaces;
line-height: 1.5;
}
</style>
