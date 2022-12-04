import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte()],
  build: {
    sourcemap: true,
  },
  server: {
    proxy: {
      '^/user-api/.*': {
        target: 'http://comic-social-user-api:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/user-api/, '')
      },
    }
  }
})
