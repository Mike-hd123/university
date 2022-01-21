import { createRouter, createWebHashHistory } from 'vue-router'
import login from '../views/login.vue'
import main from '../views/main.vue'
import Welcome from '../components/Welcome.vue'
import teacher from '../views/admin/teacher.vue'
import student from '../views/admin/student.vue'
import course from '../views/admin/course.vue'
import me from '../views/admin/me.vue'
import studentme from '../views/student/me.vue'
import teacherme from '../views/teacher/me.vue'


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

const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes: routes,
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