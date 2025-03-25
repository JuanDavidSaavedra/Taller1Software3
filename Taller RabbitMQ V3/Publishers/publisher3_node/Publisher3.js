const amqp = require('amqplib/callback_api');

// Configura el intervalo de envío.
const interval = 5000;

// Obtén la dirección IP y el puerto de RabbitMQ desde las variables de entorno
const rabbitmqHost = process.env.RABBITMQ_HOST || 'rabbitmq'; // Valor predeterminado: 'rabbitmq'
const rabbitmqPort = process.env.RABBITMQ_PORT || 5672;       // Valor predeterminado: 5672

const connectionUrl = `amqp://${rabbitmqHost}:${rabbitmqPort}`;

let counter = 0; // Contador de mensajes

amqp.connect(connectionUrl, function(error0, connection) {
  if (error0) {
    throw error0;
  }

  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }

    const queue = 'cola3';

    channel.assertQueue(queue, {
      durable: false
    });

    // Enviar el mensaje cada cierto intervalo
    setInterval(() => {
      counter++; // Incrementa el contador
      const msg = `Mensaje Enviado desde Node - Número: ${counter}`; // Formato: "Mensaje Enviado desde Node - Número: x"
      channel.sendToQueue(queue, Buffer.from(msg));
      console.log("Publisher Node: '%s'", msg);
    }, interval);
  });
});
