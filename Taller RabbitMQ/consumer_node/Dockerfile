FROM node:14
WORKDIR /app
COPY . .
RUN npm install amqplib
CMD ["node", "consumer.js"]