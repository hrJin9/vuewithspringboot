<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}"></span>
    <div class="card-body">
      <p class="card-text">
        {{ item.name }}
        <small class="discount badge bg-danger">
          {{ item.discountPer }}%
        </small>
      </p>

      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary" @click="addToCart(item.id)">
            <i class="fa-solid fa-cart-plus"></i>
        </button>
        <small class="price text-body-secondary">
          {{ lib.getNumberFormatted(item.price) }}원
        </small>
        <small class="real text-danger">
          {{ lib.getNumberFormatted(item.price - (item.price * item.discountPer) / 100) }}원
        </small>
      </div>
    </div>
  </div>
</template>

<script>
import lib from "@/scripts/lib";
import axios from "axios";

export default {
  name: 'Card',
  props: {
    item: Object
  },
  setup() {
    const addToCart = (itemId) => {
      axios.post(`/api/cart/items/${itemId}`).then(({data}) => {
        console.log(data);
      })
    }

    return {addToCart, lib}
  }
}
</script>

<style>
.card .img {
  display: inline-block;
  width: 100%;
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body .price {
  text-decoration: line-through;
}

.card .card-text .discount {
  color: white;
}
</style>