const amqp = require('amqplib/callback_api');

// Configura el intervalo de envio.
const interval = 5000;

amqp.connect('amqp://rabbitmq', function(error0, connection) {
  if (error0) {
    throw error0;
  }

  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }

    const queue = 'cola1';
    const msg = 'Mensaje Enviado desde Node';

    channel.assertQueue(queue, {
      durable: false
    });

    // Enviar el mensaje cada cierto intervalo
    setInterval(() => {
      channel.sendToQueue(queue, Buffer.from(msg));
      console.log("Publisher Node: %s", msg);
    }, interval);
  });
});
