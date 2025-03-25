<?php

require_once __DIR__ . '/vendor/autoload.php';

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

// Obtén la dirección IP y el puerto de RabbitMQ desde las variables de entorno
$host = getenv('RABBITMQ_HOST') ?: 'rabbitmq'; // Valor predeterminado: 'rabbitmq'
$port = getenv('RABBITMQ_PORT') ?: 5672;       // Valor predeterminado: 5672
$user = 'guest';
$pass = 'guest';
$queue = 'cola3';

// Crear conexión
$connection = new AMQPStreamConnection($host, $port, $user, $pass);
$channel = $connection->channel();

// Declarar la cola
$channel->queue_declare($queue, false, false, false, false);

// Contador de mensajes
$counter = 0;

// Enviar mensajes en un bucle infinito
while (true) {
    $counter++;
    $msgText = "Mensaje Enviado desde PHP - Número: $counter"; // Formato: "Mensaje Enviado desde PHP - Número: x"
    $msg = new AMQPMessage($msgText);

    // Enviar mensaje
    $channel->basic_publish($msg, '', $queue);
    echo "Publisher PHP: Mensaje enviado -> '$msgText'\n";

    // Esperar 5 segundos antes de enviar el siguiente mensaje
    sleep(5);
}

// Cerrar conexión (esto no se ejecutará a menos que se detenga manualmente)
$channel->close();
$connection->close();
