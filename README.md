Once you have this running with "mvn clean spring-boot:run" you can try one of these to test this out...

*** route Get to httpbin
curl http://localhost:8080/get

*** circuit breaker
curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8080/delay/3

*** route to my blog (with URL rewrite
curl http://localhost:8080/myblog
