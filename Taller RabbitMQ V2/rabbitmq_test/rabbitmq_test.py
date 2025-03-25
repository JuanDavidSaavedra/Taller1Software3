import pika

# Reemplaza con la IP de la máquina virtual
connection = pika.BlockingConnection(pika.ConnectionParameters('10.6.101.97', 5672))
channel = connection.channel()

# Ahora puedes usar el canal para publicar/consumir mensajes
channel.queue_declare(queue='hello')

channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')

print(" [x] Sent 'Hello World!'")

connection.close()
