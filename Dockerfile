FROM node:24-alpine
WORKDIR /app
COPY package.json ./
COPY backend ./backend
COPY frontend ./frontend
EXPOSE 4173
CMD ["node", "server.js"]

