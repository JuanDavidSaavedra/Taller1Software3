const amqp = require('amqplib/callback_api');

// Obtén la dirección IP y el puerto de RabbitMQ desde las variables de entorno
const rabbitmqHost = process.env.RABBITMQ_HOST || 'rabbitmq'; // Valor predeterminado: 'rabbitmq'
const rabbitmqPort = process.env.RABBITMQ_PORT || 5672;       // Valor predeterminado: 5672

const connectionUrl = `amqp://${rabbitmqHost}:${rabbitmqPort}`;

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

    console.log("Esperando Mensajes desde Node. Para salir, presione CTRL+C");

    channel.consume(queue, function(msg) {
      console.log("Consumer Node: '%s'", msg.content.toString());
    }, {
      noAck: true
    });
  });
});
