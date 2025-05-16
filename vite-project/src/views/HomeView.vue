<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const posts = ref<any[]>([])

onMounted(async () => {
  try {
    const response = await axios.get("/api/posts?page=1&size=5")
    posts.value = response.data
  } catch (error) {
    console.error("API 호출 오류:", error)
  }
})
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
     <router-link :to="{name:'read', params: {postId: post.id}}">{{ post.title }}  </router-link>
      </div>

      <div class="content">
      {{ post.content }}
      </div>

      <div class="sub d-flex">
      <div class="category">개발</div>
      <div class="regDate">2025-05</div>
      <div>

      </div>
      </div>
    </li>
  </ul>
</template>


<style scoped>
ul{
list-style: none;
padding: 0;
}
li {
  margin-bottom: 1.6rem;
}

.title {
a{
 text-decoration: none;
font-size: 1.2rem;
color: #383838;
}

&:hover{
text-decoration: underline;
}
}

li.content{
font-size: 0.85rem;
margin-top: 8px;
color: #5d5d5d;
}

li:last-child {
  margin-bottom: 0;
}
.sub {
margin-top: 7px;
font-size: 0.78rem;

.regDate{
margin-left: 10px;
color: #6b6b6b;
}
}
</style>
