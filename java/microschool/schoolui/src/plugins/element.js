import Vue from 'vue'
import {
    Card,
    Form,
    FormItem,
    Input,
    Button,
    Row,
    Col,
    Container,
    Header,
    Aside,
    Main,
    Link,
    Menu,
    Submenu,
    MenuItem,
    MenuItemGroup,
    Breadcrumb,
    BreadcrumbItem,
    Table,
    TableColumn,
    Message,
    Upload,
    Avatar,
    Dialog,
    MessageBox,
    Pagination
} from 'element-ui'

Vue.use(Card)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Button)
Vue.use(Row)
Vue.use(Col)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Link)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Upload)
Vue.use(Avatar)
Vue.use(Dialog)
Vue.use(Pagination)
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm
