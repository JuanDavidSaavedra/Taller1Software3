import pika
import time

# Configura el intervalo de envío.
interval = 5

# Conexión a RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
channel = connection.channel()

# Declarar la cola
queue = 'hello'
channel.queue_declare(queue=queue, durable=False)

# Mensaje a enviar
msg = 'Mensaje Enviado desde Python'

# Enviar el mensaje cada cierto intervalo
try:
    while True:
        channel.basic_publish(exchange='',
                              routing_key=queue,
                              body=msg)
        print(f"Publisher Python: {msg}")
        time.sleep(interval)
except KeyboardInterrupt:
    print("Publisher Python detenido.")
finally:
    connection.close()
