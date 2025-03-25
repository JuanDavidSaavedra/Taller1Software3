FROM python:3.9-slim
RUN apt-get update && apt-get install -y netcat
COPY rabbitmq_test.py /app/rabbitmq_test.py
WORKDIR /app
RUN pip install pika
CMD ["python", "rabbitmq_test.py"]1~FROM python:3.9-slim
RUN apt-get update && apt-get install -y netcat
COPY rabbitmq_test.py /app/rabbitmq_test.py
WORKDIR /app
RUN pip install pika
CMD ["python", "rabbitmq_test.py"]
