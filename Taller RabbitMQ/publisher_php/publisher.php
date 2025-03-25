<?php

require_once __DIR__ . '/vendor/autoload.php';

use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

// Configuración de la conexión
$host = 'rabbitmq'; // Cambia 'localhost' por 'rabbitmq'
$port = 5672;
$user = 'guest';
$pass = 'guest';
$queue = 'cola1';

// Crear conexión
$connection = new AMQPStreamConnection($host, $port, $user, $pass);
$channel = $connection->channel();

// Declarar la cola
$channel->queue_declare($queue, false, false, false, false);

// Mensaje a enviar
$msgText = 'Mensaje Enviado desde PHP';
$msg = new AMQPMessage($msgText);

// Enviar mensaje
$channel->basic_publish($msg, '', $queue);
echo "Publisher PHP: Mensaje enviado -> '$msgText'\n";

// Cerrar conexión
$channel->close();
$connection->close();
