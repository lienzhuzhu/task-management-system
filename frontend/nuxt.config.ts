// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  devtools: { enabled: true },

  modules: ['@nuxtjs/tailwindcss'],

  runtimeConfig: {
    public: {
      // Backend API runs on port 8080
      apiBaseUrl: 'http://localhost:8080'
    }
  },

  typescript: {
    strict: false
  }
})
