# Microservice Architecture

This project show how to build and deploy a microservice-based project

Spring 3, Java 17

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
