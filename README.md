# 🐇 Taller de RabbitMQ - Sistema de Colas Distribuido

**Objetivo**: Implementar un sistema de mensajería con RabbitMQ usando diferentes arquitecturas (monolítica, distribuida y escalada), con productores y consumidores en múltiples lenguajes.


## 🛠️ Tecnologías Utilizadas
- **Middleware**: RabbitMQ
- **Lenguajes**: 
  - PHP (`php-amqplib`)
  - Java (`amqp-client`)
  - JavaScript (`amqplib`)
- **Entorno**: Docker + Docker Compose

## 🌟 Características Clave
- ✅ **3 versiones progresivas** (de monolítico a distribuido).
- ✅ **6 contenedores**: 3 productores + 3 consumidores (uno por lenguaje).
- ✅ **Comunicación vía colas**, con salida por consola.
- ✅ Uso de **variables de entorno** para configuración dinámica.

## Pasos para la ejecucción:
Versión 1: entramos al archivo docker-compose.yml y en lo siguiente modificamos los puertos de tu RabbitMQ, por ejemplo, mis puertos son:
```yaml
services:
  rabbitmq:
    image: rabbitmq:3.13.7-management
    ports:
      - "5673:5672"
      - "15673:15672"
```

Después de hacer estas modificaciones, ejecutamos el compose con el siguiente comando:
docker-compose up --build

Versión 2: en este caso, no hace falta modificar el yml, ya que los puertos se van a especificar directamente en el comando para ejecutar, por ejemplo:
RABBITMQ_HOST = 10.6.101.97 RABBITMQ_PORT = 5674 docker-compose up --build

El Host y puerto especificado del RabbitMQ anterior es de nuestra tercera máquina virtual. Los publicadores y consumidores van a estar en una misma máquina virtual, diferente de la que está el RabbitMQ.

Versión 3: Al igual que la versión 2, no hace falta modificar el yml, sólo que este código hay que colocarlo en 2 máquinas, una que tiene los publicadores y otra donde están los consumidores, por ejemplo:
RABBITMQ_HOST = 10.6.101.97 RABBITMQ_PORT = 5674 docker-compose up --build

El Host y puerto especificado del RabbitMQ anterior es de nuestra tercera máquina virtual. 

## 📽️ Demostración & ejecucción en Video de cada versión
[Taller Publish-Subscribe](https://www.youtube.com/watch?v=KvYHccsxUxY)
