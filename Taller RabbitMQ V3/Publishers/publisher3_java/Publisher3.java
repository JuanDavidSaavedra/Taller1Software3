import com.rabbitmq.client.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Publisher3 {
    private final static String QUEUE_NAME = "cola3";
    private static final int INTERVAL = 5; // Intervalo en segundos
    private static int counter = 0; // Contador para los mensajes

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

        // Crea un ScheduledExecutorService para enviar mensajes periódicamente
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Configura la tarea de enviar mensajes periódicamente
        Runnable sendMessageTask = () -> {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                // Incrementa el contador y construye el mensaje
                counter++;
                String message = "Mensaje Enviado desde Java - Número: " + counter;

                // Publica el mensaje en la cola
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("Publisher Java: '" + message + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Programa la tarea para que se ejecute cada INTERVAL segundos
        scheduler.scheduleAtFixedRate(sendMessageTask, 0, INTERVAL, TimeUnit.SECONDS);

        // Mantiene el programa en ejecución para permitir el envío de mensajes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown initiated");
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }));
    }
}
