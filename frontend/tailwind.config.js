/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{js,jsx}',
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        background: '#0f172a',
        neon: {
          cyan: '#22d3ee',
          green: '#4ade80',
        },
      },
    },
  },
  plugins: [],
}