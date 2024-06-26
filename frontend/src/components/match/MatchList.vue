<template>
    <v-container class="contents-section list-match">
        <v-row>
            <v-col cols="12" class="text-center">
                <span class="list-title">경기 목록</span>
            </v-col>
            <v-col cols="12" align="center" v-for="match in matches" :key="match.id">
                <v-card class="card-match">
                    <v-card-subtitle>{{ match.league.nameKr }} <v-icon>mdi-slash-forward</v-icon> {{ match.round
                    }}</v-card-subtitle>
                    <span>
                        <v-img :src="match.league.logo" class="league-logo" 
                        @click="toLDP(match.league.leagueId)"></v-img>
                        {{ setStartTime(match.startTime) }}
                        <v-icon>mdi-circle-small</v-icon>
                        {{ getMatchStatus(match.startTime) }}</span>
                    <v-card-title class="pb-6">
                        <v-row class="card-content" align="center" justify="center">
                            <v-col cols="4" class="text-end team-name" @click="toTDP(match.homeTeam.seasonLeagueTeamId)">
                                {{ match.homeTeam.nameKr }}
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                <v-img :src="match.homeTeam.logo" contain class="team-logo team-name"
                                    @click="toTDP(match.homeTeam.seasonLeagueTeamId)"></v-img>
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                VS
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                <v-img :src="match.awayTeam.logo" contain class="team-logo team-name"
                                    @click="toTDP(match.awayTeam.seasonLeagueTeamId)"></v-img>
                            </v-col>
                            <v-col cols="4" class="text-start team-name" @click="toTDP(match.awayTeam.seasonLeagueTeamId)">
                                {{ match.awayTeam.nameKr }}
                            </v-col>
                        </v-row>
                    </v-card-title>
                    <v-card-text>
                        <!-- checkStatus : 인풋이 not started이면 false, 그 외엔 true -->
                        <div v-if="checkStatus(match.status)" class="score">
                            <p class="score-title">스코어</p>
                            {{ match.homeTeamGoal }} : {{ match.awayTeamGoal }}
                            <!-- <br>{{ match.status }} -->
                        </div>
                        <div v-else class="score-title"></div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-row v-if="matches == null || matches?.length === 0">
            <v-col cols="12" align="center">
                <v-card class="card-match mt-6">
                    <span>예정된 경기가 없습니다!</span>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
  
<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { format, set, parseISO, differenceInHours, differenceInMinutes } from 'date-fns';

import { useFootballStore } from '@/stores/football/football';

// 라우터링크를 위해 사용
const router = useRouter();

const footballStore = useFootballStore();

const { getDateMatches } = footballStore;

// selectedDate가 부모 컴포넌트로부터 props로 넘어온다.
// "2024-01-31T05:46:09.320Z" 형태로 넘어온다.
const props = defineProps({
    selectedDate: {
        type: Date,
        required: true,
    },
});
const selectedDate = computed(() => {
    return props.selectedDate;
});

// console.log(selectedDate.value);
const date = format(selectedDate.value, 'yyyy-MM-dd');

getDateMatches(date);

const matches = ref([]);

watch(() => selectedDate.value, (newVal) => {
    getDateMatches(format(newVal, 'yyyy-MM-dd'));
    matches.value = [];
}, { immediate: true });


watch(() => footballStore.dateMatches, (newVal) => {
    matches.value = newVal;
    // console.warn(matches.value);
}, { immediate: true });

const toTDP = (seasonLeagueTeamId) => {
    router.push(`/team/${seasonLeagueTeamId}`);
};

const toLDP = (leagueId) => {
  // console.log("leagueId=", leagueId)
  router.push(`/league/${leagueId}`);
};

// 예시 데이터 (특정 날자의 경기 데이터)
// matches
// {
//     "fixtureId": 13,
//     "startTime": "2024-01-31T18:00:00",
//     "round": "6차전",
//     "status": "not start",
//     "homeTeamGoal": 0,
//     "awayTeamGoal": 0,
//     "league": {
//         "leagueId": 1,
//         "nameKr": "챔피언십",
//         "logo": "https://media.api-sports.io/football/leagues/40.png"
//     },
//     "homeTeam": {
//         "seasonLeagueTeamId": 4,
//         "teamId": 4,
//         "nameKr": "멍뭉",
//         "nameEng": "cccc",
//         "logo": "https://source.unsplash.com/random/300x300?emblem"
//     },
//     "awayTeam": {
//         "seasonLeagueTeamId": 1,
//         "teamId": 1,
//         "nameKr": "마루쉐",
//         "nameEng": "maroche",
//         "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg"
//     }
// }

function setStartTime(startTime) {
    return format(parseISO(startTime), 'yyyy-MM-dd HH:mm');
}

function getMatchStatus(startTime) {
    const now = new Date();
    const start = parseISO(startTime);
    const diffHours = differenceInHours(start, now);
    // console.log(diffHours)

    if (diffHours >= 24) {
        return "경기 예정";
    } else if (diffHours < 0) {
        return "경기 종료";
    } else if (24 > diffHours && diffHours >= 0) {
        // console.log("경기 시작까지 남은 시간", startTime, diffHours);
        // 경기까지 남은 시간을 hh:mm 분 남았다고 표시
        const diffMinutes = differenceInMinutes(start, now);
        if (diffMinutes < 0) {
            return "진행 중";
        }
        // 시간과 분으로 변환
        const hoursLeft = Math.floor(diffMinutes / 60);
        if (hoursLeft <= 0) {
            return `곧 시작! ${diffMinutes+1}분 남았습니다`;
        }
        const minutesLeft = diffMinutes % 60;
        return `${hoursLeft}시간 ${minutesLeft+1}분 남았습니다`;
    } else if (diffHours <= 0 && diffHours > -2) {
        // 진행 중인 경우 현재 스코어 표시 필요
        return "진행 중";
    }
}

function checkStatus(status) {
    const check = status.toLowerCase();
    // console.log(check)
    if (check == "not started" || check == "time to be defined") {
        return false;
    } else {
        return true;
    }
    // const status = ref("");
    //  // 소문자화
    //  const statusEng = item.status.toLowerCase();
    //  if (statusEng === "not started") {
    //       status.value = "예정";
    //  } else if (statusEng === "match finished") {
    //       status.value = "경기 종료";
    //  } else if (statusEng === "time to be defined") {
    //       status.value = "시간 미정";
    //  } else {
    //       status.value = "진행중";
    //  }
}

</script>
  
<style scoped>
.list-title {
    color: white;
    font-size: 2rem;
}


.list-match {
    /* background-color: #292646; */
    /* overflow-x: auto; */
    /* 내용이 넘칠 경우 수평 스크롤 가능 */
    white-space: nowrap;
    /* 내용을 한 줄로 유지 */
    min-width: 800px;
}

.league-logo {
    width: 80px;
    height: 80px;
    min-width: 50px;
    margin: 10px 0px 10px 0px;
    cursor: pointer;
}

.team-logo {
    width: 50px;
    height: 50px;
    min-width: 50px;
}

.card-match {
    white-space: nowrap;
    border-color: black;
    border-radius: 8px;
    padding: 16px;
    display: inline-block;
    /* 카드를 인라인 블록 요소로 설정 */
    white-space: normal;
    /* 카드 내부의 텍스트는 정상적으로 줄바꿈 */
    width: 60vw;
    /* 카드의 너비를 고정하지 않고 유연하게 조정 */
    min-width: 600px;
    /* 카드의 최소 너비 설정 */
    margin-right: 16px;
    /* 카드 간의 간격 조정 */
    align-self: center;
}

.card-content {
    white-space: nowrap;
}

.team-name {
    cursor: pointer;
    font-size: 1.25rem;
    /* font-weight: bold; */
}

.score {
    font-size: 1.6rem;
    /* 글자 사이 위아래 간격 */
    line-height: 2;
    margin-top: 10px;
    font-weight: bold;
    color: #292646;
}

.score-title {
    font-size: 1.2rem;
    font-weight: bold;
    color: #292646;
}
.team-logo:hover, .team-name:hover, .league-logo:hover {
    transform: scale(1.15);
}
.card-match:hover {
    transform: scale(1.05);
    border : 4px solid #D3AC2B;
}
</style>
  