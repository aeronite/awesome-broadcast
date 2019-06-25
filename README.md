# awesome-broadcast
Shares messages with multiple service endpoints in an awesome way

## implementation

This service is currently a simple master slave model.<br>
One instance is designated (via env var) as the master <br>
All instances (including the master itself) send actually requests <br>
to this ip.<br>
Future improvements include caching results at the slaves and <br>
allowing the master itself to access the data directly.

## deployment
**MASTER_IP** environment variable is required for running
* git clone https://github.com/aeronite/awesome-broadcast.git
* mvn clean install
* docker build -t awesome1 .
* sudo docker run -d -it -e MASTER_IP='<your_server_ip>' -p 8080:8080 --rm awesome


### sample usage
__add stuff by key__<br>
`curl -X POST -H "Content-Type: application/json" -d '{"something": "blabla"}' http://0.0.0.0:8080/awesome/api/stuff/hello
`

__view stuff by key__<br>
`wget http://0.0.0.0:8080/awesome/api/stuff/hello
`