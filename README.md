# awesome-broadcast
Shares messages with multiple service endpoints in an awesome way

MASTER_IP environment variable required for running


### sample usage
__add stuff by key__<br>
`curl -X POST -H "Content-Type: application/json" -d '{"something": "blabla"}' http://0.0.0.0:8080/awesome/api/stuff/hello
`

__view stuff by key__<br>
`wget http://0.0.0.0:8080/awesome/api/stuff/hello
`