import com.rabbitmq.client.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Publisher {
    private final static String QUEUE_NAME = "cola1";
    private static final int INTERVAL = 5; // Intervalo en segundos

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        
        // Crea un ScheduledExecutorService para enviar mensajes periódicamente
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Configura la tarea de enviar mensajes periódicamente
        Runnable sendMessageTask = () -> {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Mensaje Enviado desde Java";
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
