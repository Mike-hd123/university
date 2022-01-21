import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/login'
import main from '../views/main'
import Welcome from '../components/Welcome'
import teacher from '../views/admin/teacher'
import student from '../views/admin/student'
import course from '../views/admin/course'
import me from '../views/admin/me'
import studentme from '../views/student/me'
import teacherme from '../views/teacher/me'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    component: main,
    redirect: '/welcome',
}, {
    path: '/main',
    component: main,
    redirect: '/welcome',
    children: [
        { path: '/welcome', component: Welcome },
        { path: '/teacher', component: teacher },
        { path: '/student', component: student },
        { path: '/course', component: course },
        { path: '/me', component: me },
        { path: '/teacherme', component: teacherme },
        { path: '/studentme', component: studentme },
    ]
},
{
    path: '/login',
    component: login
}]

const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    // 登录页面直接放行
    if (to.path == '/login') return next();
    // 非登录页面先获取token查看是否在线
    const token = window.localStorage.getItem('token');
    // 获得空token
    if (!token) return next('/login');
    next()
})

export default router