<template>
    <v-container>
        <v-toolbar flat class="preview-toolbar">
            <v-toolbar-title class="group-title">그룹 게시판</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <RouterLink :to="`/club/${clubId}/board`">
                    <v-btn icon @click="goToDetail">
                        <v-icon class="plus-btn">mdi-plus</v-icon>
                    </v-btn>
                </RouterLink>
            </v-toolbar-items>
        </v-toolbar>
        <v-row>
            <v-col
                cols="12" sm="6" md="4" lg="4" 
                v-for="(post, index) in posts.slice(0,6)"
                :key="index"
                >
                <BoardDetail
                        v-if="isDetailVisible && currentPost.id === post.id" 
                        :post="currentPost"
                        @detail-close="isDetailVisible = false"
                        @delete-post="deletePost"
                />
                <v-card class="card" @click="showBoardDetail(post)">
                    <v-card-title class="text-center">{{ post.title }}</v-card-title>
                    <v-card-subtitle class=text-right>{{ post.member.nickname }}</v-card-subtitle>
                    <v-card-text class="text-right">{{formatDateTime(post.updatedTime)}}</v-card-text>
                    <v-img v-if="post.file && post.file.type ==='image'" class="thumbnail" :src="post.file.url" :alt="post.title" cover height="200px"/>
                    <video v-if="post.file && post.file.type === 'video'" :src="post.file.url" cover height="200px"></video>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import {ref,onMounted,watch} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {useBoardStore} from '@/stores/club/boards';
import {formatDateTime} from "@/util/tools.js"

import BoardDetail from '@/components/board/BoardDetail.vue';

const boardStore = useBoardStore();

const routes = useRoute();
const router = useRouter();
const clubId = ref(routes.params.clubId)

onMounted(() => {
    boardStore.getBoardList(clubId.value);
})

const posts = ref([]);
watch(() => boardStore.boardList,(newBoardList) => {
    posts.value = newBoardList;
})

watch(() => routes.params.clubId, (newClubId) => {
    clubId.value = newClubId;
    boardStore.getBoardList(clubId.value);
});

 // 현재 게시글을 담을 변수 -> store로직 추가하면서 바꾸자
 const currentPost = ref(null);

// 게시글 상세 on/off
const isDetailVisible = ref(false);

// 게시글 상세 on
const showBoardDetail = (post) => {
    currentPost.value = post 
    isDetailVisible.value  = true;
}

const deletePost = (postId) => {
    posts.value = posts.value.filter(p => p.id != postId)
}

</script>


<style>
.v-container {
    padding-left:0px;
}

h1{
    color:black
}
.group-title{
    color:white;
    margin-left:0;
}

.preview-toolbar{
    background:white
}

.card {
    min-height: 300px; /* 예시 높이, 필요에 따라 조정 */
    display: flex;
    flex-direction: column;
    background-color: #F4F3EA ;
}

.thumbnail {
    width : 100%;
    height: 100%;
    object-fit: cover;
}

.thumb_img{
    width:100%;
}
</style>