# Microservice Architecture

This project show how to build and deploy a microservice-based project

Spring 3, Java 17

### ORDER-SERVICE / PAYMENT-SERVICE
Basic Microservices with Controllers, Services, Entities nad Repositories.  
The database used is an in-memory database H2. 
The Payment service and Order service runs on port 9191 and 9192 respectively.

Both of the services need to have an application.properties to add this particular property because it would not work as a yaml: 
```
spring.config.import=configserver:http://localhost:9196
```

### SERVICE-REGISTRY
The Service Registry application is the place where the microservices need to be registered in order to communicate between one another.
This is possible thank to Spring Eureka. 
You need to annotate the main class with @EnableEurekaServer and the microservices main class with @EnableDiscoveryClient.
With Spring 3 only version 2022.0.0 works.
The Service Registry runs on port 8761.

### CLOUD-GATEWAY
It is like an orchestrator for the endpoints. You just hit the gateway endpoint, and it is able to redirect to the correct microservice based of the endpoint you are trying to hit. 
It also needs to be annotated with @EnableDiscoveryClient as it is a microservice that needs to be registered on Eureka Server and needs the application.properties as well.
We create a Fallback Controller to tell the gateway what to do when one or more of the microservices are out, or broke. And we do that by using Resilience4j and adding the following to the application.yml
```
resilience4j:
  circuitbreaker:
    configs:
      order-service:
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
      payment-service:
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s


management:
  endpoints:
    web:
      exposure:
        include: resilience4j.circuitbreakers.stream
```
The Cloud-Gateway service runs on port 8989. Basically all of your endpoints should point to this port, it is then the gateways job to redirect to the correct microservice at the correct location and port.

### CLOUD-CONFIG-SERVER
It is used to centralize all of the common properties of the microservices in a single place. In this case, this cloud-config-server is fetching the properties from a GitHub repository (https://github.com/JasonShuyinta/cloud-config-server). In this way you don't need to repeat yourself. 
This as well needs to be annotated with @EnableDiscoveryClient and also @EnableConfigServer to let know Spring this is a configuration service.
The Cloud-Config-Server runs on port 9196.

### ELK Stack
To store and visualize logs of the microservices, the ELK (ElasticSearch, LogStash, Kibana) Stack was used, specifically version 8.6.0.
After you download and unzip all the softwares, there are some files to modify to adapt them to your system:
- in elastisearch-8.6.0/config/elasticsearch.yml you should uncomment path.data and path.logs and make them correspond to the actual data and logs folder on your file system.
- in the same file you need to disable the SSL security which by default is disabled: everywhere where there is "enabled: true" set it to false. For development purposes this is perfectly ok.
- in kibana-8.6.0/config/kibana.yml you need to uncomment where it says elasticsearch.hosts: ["http://localhost:9200"]
- in logastash-8.6.0 you need to add a logstash.conf file with the following content: 

```
input {
  file {
    path => "path/to/your/log/file.log"
  }
}

output {
  elasticsearch {
    hosts => ["localhost:9200"]
    index => "index_name-%{+YYYY.MM.dd}"
  }
}
```
- To start the logstash bat you need to enter the command 
```
.\logstash.bat -f logstash.conf
```

Default Ports
- ElasticSearch: 9200
- Kibana: 5601

Libraries:

- spring-cloud-starter-actuator
- spring-cloud-starter-gateway
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-netflix-eureka-server
- resilience4j
- spring-cloud-starter-config
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-cloud-config-server

This project was made following the steps of https://www.youtube.com/@Javatechie and with some small changes adapting it to 2023 and it is purely for personal learning purposes. 
