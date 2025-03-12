// vite.config.ts
import { dirname, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'
import { defineConfig } from 'vite'


const __dirname = dirname(fileURLToPath(import.meta.url))

export default defineConfig({
    build: {
      outDir: '../resources/',
      assetsDir:'static',
      rollupOptions: {
        input: {
          main: resolve(__dirname, 'templates/flight.html'),
        },
      },
    },

  });