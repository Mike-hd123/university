import 'element-plus/dist/index.css'
import Elementplus from 'element-plus'
import * as Icons from '@element-plus/icons'

export default (app) => {
    app.use(Elementplus)
    Object.keys(Icons).forEach((key) => {
        app.component(key,Icons[key])
    })
}