import com.rabbitmq.client.*;

public class Consumer2 {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // Obtén la dirección IP y el puerto de RabbitMQ desde las variables de entorno
        String rabbitmqHost = System.getenv("RABBITMQ_HOST");
        String rabbitmqPortStr = System.getenv("RABBITMQ_PORT");

        // Verifica que las variables de entorno estén configuradas
        if (rabbitmqHost == null || rabbitmqPortStr == null) {
            System.err.println("Error: Las variables de entorno RABBITMQ_HOST y RABBITMQ_PORT deben estar configuradas.");
            System.exit(1);
        }

        int rabbitmqPort = Integer.parseInt(rabbitmqPortStr); // Convierte el puerto a entero

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqHost); // Usa la IP proporcionada
        factory.setPort(rabbitmqPort); // Usa el puerto proporcionado

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Esperando Mensajes desde Java. Para salir, presione CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Consumer Java: '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
