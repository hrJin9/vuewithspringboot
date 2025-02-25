<template>
  <div class="cart">
    <div class="container">
      <div class="py-5 text-center">
        <h2>주문하기</h2>
      </div>

      <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">구입 목록</span>
            <span class="badge badge-secondary badge-pill">
              {{ state.items.length }}
            </span>
          </h4>
          <ul class="list-group mb-3">
            <li v-for="(i, idx) in state.items" :key="idx"  class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <h6 class="my-0">{{i.name}}</h6>
                <small class="text-muted">Brief description</small>
              </div>
              <span class="text-muted">{{ lib.getNumberFormatted(i.price - i.price * i.discountPer / 100) }}원</span>
            </li>
          </ul>
          <div class="text-center total-price">
            {{  lib.getNumberFormatted(computedPrice) }}원
          </div>
        </div>
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">주문자 정보</h4>
          <div class="needs-validation">
            <div class="mb-3">
              <label for="username">이름</label>
              <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text">@</span>
                </div>
                <input type="text" class="form-control" id="username" v-model="state.form.name">
              </div>
            </div>
            <div class="mb-3">
              <label for="address">주소</label>
              <input type="text" class="form-control" id="address" v-model="state.form.address">
            </div>
            <hr class="mb-4">
            <h4 class="mb-3">결제수단</h4>
            <div class="d-block my-3">
              <div class="custom-control custom-radio">
                <input id="card" name="paymentMethod" type="radio" class="custom-control-input" checked
                       value="card" v-model="state.form.payment"/>
                <label class="custom-control-label" for="card">신용 카드</label>
              </div>
              <div class="custom-control custom-radio">
                <input id="bank" name="paymentMethod" type="radio" class="custom-control-input"
                       value="bank" v-model="state.form.payment">
                <label class="custom-control-label" for="bank">무통장 입금</label>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="cc-number">카드 번호</label>
                <input type="text" class="form-control" id="cc-number" placeholder=""
                       v-model="state.form.cardNumber">
              </div>
            </div>
            <hr class="mb-4">
            <button class="btn btn-primary btn-lg btn-block" @click="submit">결제하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";
import {computed, reactive} from "vue";
import lib from "@/scripts/lib";

export default {
  name: 'Order',
  setup() {
    const state = reactive({
      items: [],
      form: {
        name: '',
        address: '',
        payment: '',
        cardNumber: '',
        items: ''
      }
    })

    const load = () => axios.get('/api/cart/items').then(({data}) => {
      state.items = data;
    })

    const computedPrice = computed(() => {
      let result = 0;
      for(let i of state.items) {
        result += i.price - i.price * i.discountPer / 100;
      }
      return result;
    })

    const submit = () => {
      const args = JSON.parse(JSON.stringify(state.form)); // 참조값 연결고리 끊기 위해
      args.items = JSON.stringify(state.items);

      axios.post('/api/orders', args).then(() => {
        console.log('성공')
      })
    }

    load();

    return {state, lib, computedPrice, submit}
  }
}
</script>

<style>


</style>