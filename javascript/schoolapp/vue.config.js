module.exports = {
    transpileDependencies: ['@dcloudio/uni-ui'],
    devServer: {
        port: 2294,
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '/api': '/api'
                }
            }
        },
        disableHostCheck: true, // 这是由于新版的webpack-dev-server出于安全考虑，默认检查hostname，如果hostname 不是配置内的，将中断访问。
    },
}