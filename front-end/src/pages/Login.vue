<template>
  <div class="form-signin w-100 m-auto">
    <form onsubmit="return false">
      <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

      <div class="form-floating">
        <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
               v-model="state.form.email" @keyup.enter="submit()">
        <label for="floatingInput">Email address</label>
      </div>
      <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
               v-model="state.form.password" @keyup.enter="submit()">
        <label for="floatingPassword">Password</label>
      </div>

      <div class="form-check text-start my-3">
        <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
        <label class="form-check-label" for="flexCheckDefault">
          Remember me
        </label>
      </div>
      <button class="btn btn-primary w-100 py-2" @click="submit()">Sign in</button>
      <p class="mt-5 mb-3 text-body-secondary">&copy; 2017–2024</p>
    </form>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import store from "@/scripts/store";
import router from "@/scripts/router";

export default {
  name: 'Login',
  setup() {
    const state = reactive({
      form: {
        email: '',
        password: ''
      }
    })

    const submit = () => {
      axios.post("/api/account/login", state.form).then((res) => {
        // store에 로그인 정보 저장
        store.commit('setAccount', res.data);
        // 새로고침시 로그인이 풀림 => session에 저장
        sessionStorage.setItem('id', res.data);
        router.push({
          path: '/'
        })
        alert('로그인하였습니다.');
      }).catch(() => {
        alert('로그인 정보가 존재하지 않습니다.')
      })
    }


    return {state, submit};
  }
}
</script>

<style>
.form-signin {
  max-width: 330px;
  padding: 1rem;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>