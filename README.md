How to play the game using API endpoints:

Start the game:
curl.exe -X POST http://localhost:8080/start -H "Accept: application/json" -c cookie.txt

Player hits:
curl.exe -X POST http://localhost:8080/hit   -H "Accept: application/json" -b cookie.txt -c cookie.txt

Player passes:
curl.exe -X POST http://localhost:8080/pass  -H "Accept: application/json" -b cookie.txt

After every post you will get an output of the game status.