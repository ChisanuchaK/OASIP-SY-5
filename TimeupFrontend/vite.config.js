import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      "/api": {
        target: "http://ip21sy5.sit.kmutt.ac.th/",
        changeOrigin: true,
        secure: false,
        // rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
