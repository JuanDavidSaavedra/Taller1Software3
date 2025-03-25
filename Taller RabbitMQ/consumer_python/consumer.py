import pika

# Conexión a RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
channel = connection.channel()

# Declarar la cola
queue = 'hello'
channel.queue_declare(queue=queue, durable=False)

print("Esperando Mensajes desde Python. Para salir, presione CTRL+C")

# Callback para procesar los mensajes recibidos
def callback(ch, method, properties, body):
    print(f"Consumer Python: {body.decode()}")

# Consumir mensajes de la cola
channel.basic_consume(queue=queue,
                      on_message_callback=callback,
                      auto_ack=True)

try:
    channel.start_consuming()
except KeyboardInterrupt:
    print("Consumer Python detenido.")
finally:
    connection.close()
