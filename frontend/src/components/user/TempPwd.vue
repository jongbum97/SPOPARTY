<template>
  <v-dialog 
    v-model="modalVisible" 
    max-width="400px"
    @click:outside="closeModal"
    persistent    >
    <v-card>
      <v-card-title class="text-center"><h5>비밀번호 찾기</h5></v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitForm">
          <v-text-field
            label="아이디"
            v-model="loginId"
            outlined
            dense
          ></v-text-field>

          <v-text-field
            label="이메일"
            v-model="email"
            outlined
            dense
          ></v-text-field>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="grey" text @click="closeModal">이전</v-btn>
            <v-btn color="primary" type="submit">임시 비밀번호 요청</v-btn>
          </v-card-actions>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>

  <v-dialog v-model="isMessageModalVisible" max-width="300px">
    <v-card>
      <v-card-text class="text-center">
        <p>임시 비밀번호가 발급되었습니다. 5분 동안 유효합니다.</p>
        <v-btn color="primary" text @click="isMessageModalVisible=false">확인</v-btn>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from 'vue';
import { useManagementStore } from '@/stores/member/managements';

const store = useManagementStore();

const modalVisible = ref(true); // 메인 모달 보이게 할지말지
const loginId = ref('');
const email = ref('');
const isMessageModalVisible = ref(false); // 메시지 모달 보이게 할지말지
const emit = defineEmits(['close']);

function submitForm() {
  // console.log(`임시 비밀번호 발급 요청: 아이디=${loginId.value}, 이메일=${email.value}`);
  const data = {
      loginId : loginId.value,
      email : email.value
  }
  const result = store.tempPassword(data);
  if (result) isMessageModalVisible.value = true;
}

function closeModal() {
  modalVisible.value = false;
  emit('close');
}


</script>

<style scoped>

</style>