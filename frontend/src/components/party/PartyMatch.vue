<template>
    <v-container fluid class="pa-1">
        <v-card>
            <v-tabs v-model="tab" bg-color="#CBD0D8" show-arrows slider-color="primary" class="tabs">
                <v-tab value="one">실시간 정보</v-tab>
                <v-tab value="two">경기 내역</v-tab>
                <v-tab value="three">팀 플레이어들</v-tab>
                <v-tab value="four">응원하기</v-tab>
                <!-- 새로고침 버튼 -->
                <v-spacer></v-spacer>
                <v-btn :loading="loading" icon @click="refreshData" variant="text" class="loading-btn">
                    <v-tooltip activator="parent" location="top" theme="dark">새로고침</v-tooltip>
                    <v-icon>mdi-refresh</v-icon>
                </v-btn>
            </v-tabs>
            <v-card-text>
                <v-window v-model="tab">
                    <h1 v-if="loadingText[0]">{{ loadingText[1] }}</h1>
                    <v-window-item value="one">
                        <PartyRealTimeInfo/>
                    </v-window-item>
                    <v-window-item value="two">
                        <PartyMatchHistory/>
                    </v-window-item>
                    <v-window-item value="three">
                        <PartyTeamPlayers/>
                    </v-window-item>
                    <v-window-item value="four">
                        <PartyCheer/>
                    </v-window-item>
                </v-window>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import PartyMatchHistory from '@/components/party/PartyMatchHistory.vue'
import PartyRealTimeInfo from '@/components/party/PartyRealTimeInfo.vue'
import PartyTeamPlayers from '@/components/party/PartyTeamPlayers.vue'
import PartyCheer from '@/components/party/PartyCheer.vue'

import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const fixtureId = ref(null)

watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
}, { immediate: true })

const tab = ref(null) // 현재 선택된 탭을 관리

const loading = ref(false) // 새로고침 버튼 로딩 상태
const loadingText = ref([false,"새로고침 중..."])
const refreshData = () => {
    // 여기에 데이터 새로고침 로직 구현
    loading.value = true
    loadingText.value[0] = true
    const tempFixtureId = fixtureId.value
    footballStore.fixtureIdForParty = null
    // console.log(footballStore.matchRealTimeData)
    // footballStore.matchRealTimeData = ["로딩 중"]
    // console.log(footballStore.matchRealTimeData)
    // footballStore.getMatchRealTimeData(fixtureId.value)
    setTimeout(() => (loading.value = false, loadingText.value[0] = false, 
    footballStore.fixtureIdForParty = tempFixtureId), 2000)
}

// footballStore.matchRealTimeData = ["우측 버튼을 눌러 새로고침"]
</script>

<style scoped>
h1 {
    color : black;
    padding : 10px;
}

.v-tab {
    /* 개별 탭 스타일 */
    width: 20%;
    text-transform: none;
    font-size: 1.1em;
    &:hover {
        color: mediumblue;
        font-size: larger;
    }
}

.v-btn {
    /* 버튼 스타일 */
    margin-right: 10px;
}
@keyframes rotate {
  from {
    transform: rotate(0deg) scale(1.05);
  }
  to {
    transform: rotate(360deg) scale(1.1);
  }
}

.loading-btn {
    /* 새로고침 버튼 스타일 */
    color: #121212;

    &:hover {
        animation: rotate 0.5s linear infinite;
    }
}


</style>
