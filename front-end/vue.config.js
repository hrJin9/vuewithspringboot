module.exports = {
  devServer: {
    proxy: {
      '/api' : {
        target: 'http://localhost:9090',
        changeOrigin: true,
        ws: false,
      }
    }
  }
}
