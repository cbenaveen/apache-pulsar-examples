Creating a POD and Connecting to Proxy to produce messages:
Open a new terminal, and execute all of these commands:
1) eval $(minikube docker-env)
2) git pull
3) mvn clean package
4) cp target/apache-pulsar-examples-1.0-SNAPSHOT-jar-with-dependencies.jar docker/simple-message-producer/
5) cd apache-pulsar-examples/docker/simple-message-producer
6) docker build -t apache-pulsar-examples/simple-message-producer:0.0.1 .
7) Connect to toolset pod and create required tenants, namespace, and topics
   7.1) Connect to toolset container: kubectl -n pulsar exec -it pulsar-mini-toolset-0 /bin/bash
   7.2) bin/pulsar-admin tenants create research-and-development
   7.3) bin/pulsar-admin namespaces create research-and-development/research/
   7.4) bin/pulsar-admin topics create research-and-development/research/greeting
   7.5) exit
8) Create simple-message-producer POD:
   kubectl -n pulsar run \
   simple-message-producer \
   --image=apache-pulsar-examples/simple-message-producer:0.0.1 \
   --env="PULSAR_SERVICE_URL=pulsar://pulsar-mini-proxy:6650"
9) (optional) Check the simple-message-producer pod status: kubectl -n pulsar get pods
    * wait for simple-message-producer status to turn into Running or Crashloop
10) check the log of simple-message-producer pod log: kubectl -n pulsar logs -f simple-message-producer
11) In case if you want to delete the pod: kubectl -n pulsar delete pod simple-message-producer
12) Create Container using yaml file: kubectl -n pulsar create -f simple-message-producer.yaml 
