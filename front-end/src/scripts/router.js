import Home from "@/pages/Home";
import Login from "@/pages/Login";
import Cart from "@/pages/Cart";
import {createRouter, createWebHistory} from "vue-router";
import Order from "@/pages/Order.vue";
import Orders from "@/pages/Orders.vue";

const routes = [
    {
        path: '/',
        component: Home
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/cart',
        component: Cart
    },
    {
        path: '/order',
        component: Order
    },
    {
        path: '/orders',
        component: Orders
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;