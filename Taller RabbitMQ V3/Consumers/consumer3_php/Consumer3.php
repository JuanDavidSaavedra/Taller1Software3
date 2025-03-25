<?php

require_once __DIR__ . '/vendor/autoload.php';

use PhpAmqpLib\Connection\AMQPStreamConnection;

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

echo "Esperando mensajes desde PHP. Para salir, presiona CTRL+C\n";

// Callback para procesar los mensajes
$callback = function ($msg) {
    echo "Consumer PHP: Mensaje recibido -> '" . $msg->body . "'\n";
};

// Consumir mensajes de la cola
$channel->basic_consume($queue, '', false, true, false, false, $callback);

// Mantener el script en ejecución
while ($channel->is_consuming()) {
    $channel->wait();
}

// Cerrar conexión (esto no se ejecutará a menos que se detenga manualmente)
$channel->close();
$connection->close();
