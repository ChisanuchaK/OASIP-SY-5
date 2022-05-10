import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: "/sy5/",
  // server: {
  //   proxy: {
  //     "/api": {
  //       target: "http://intproj21.sit.kmutt.ac.th",
  //       // target: "http://ip21sy5.sit.kmutt.ac.th/",
  //       changeOrigin: true,
  //       secure: false,
  //       // rewrite: (path) => path.replace(/^\/api/, ""),
  //     },
  //   },
  // },
});
