## General ##

Adding testing results as I add new feature. 

## Testing ##

1. Validating stat API with no input data entered.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats"

Response:

{"inputs":{},"most_popular":"","longest_input_received":""}%

```

2. Entering some initial data using GET method with input 'Tesst-1' string.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=Tesst-1"

Response:

{"input":"Tesst-1","length":7,"most_frequent_character":"s","most_frequent_character_occurrence_count":2}%


```

3.  Stats call after operation 2.


```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats"

Resposne:

{"inputs":{"Tesst-1":1},"most_popular":"Tesst-1","longest_input_received":"Tesst-1"}
```

4. Entering duplicate data using GET method with input 'Tesst-1' string.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=Tesst-1"

Response:

{"input":"Tesst-1","length":7,"most_frequent_character":"s","most_frequent_character_occurrence_count":2}
```

5. Stats call after duplicate string.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats"

Response:

{"inputs":{"Tesst-1":2},"most_popular":"Tesst-1","longest_input_received":"Tesst-1"}
```

6. Entering uniqe string with no duplicate characters.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=Test-2"

Response:

{"input":"Test-2","length":6,"most_frequent_character":"2","most_frequent_character_occurrence_count":1}

Note: Here last character '2' scanned will appear in most_frequent_character as all of them has same frequency.
```

7. Stats call after unique string input.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats

Response:

{"inputs":{"Tesst-1":2,"Test-2":1},"most_popular":"Tesst-1","longest_input_received":"Tesst-1"}
```

8. Stringinate call with POST method.

```
Request:

curl -id '{"input":"Test-1-with-post-method"}' -H 'Content-Type: application/json' http://localhost:8080/stringinate

Response:

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 15 Feb 2023 00:37:16 GMT

{"input":"Test-1-with-post-method","length":23,"most_frequent_character":"-","most_frequent_character_occurrence_count":4}
```

9. Stats call after POST method call.

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats"

Response:

{"inputs":{"Test-1-with-post-method":1,"Tesst-1":2,"Test-2":1},"most_popular":"Tesst-1","longest_input_received":"Test-1-with-post-method"}
 
```
10. Input validation with empty string passed as value for input using GET method.

```
Request:
curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input="

Response:
Input string length should be greater than 0.
```

11. Input validation with null passed as value for input using POST method.

```
Request:

curl -id '{"input":null}' -H 'Content-Type: application/json' http://localhost:8080/stringinate

Response:

HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 15 Feb 2023 02:25:10 GMT
Connection: close

{"timestamp":"2023-02-15T02:25:10.516+00:00","status":400,"error":"Bad Request","path":"/stringinate"}%
```

12. Input validation with emptt string passed as value for input using POST method.

```
Request:

curl -id '{"input":""}' -H 'Content-Type: application/json' http://localhost:8080/stringinate

Response:

HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 15 Feb 2023 02:26:20 GMT
Connection: close

{"timestamp":"2023-02-15T02:26:20.603+00:00","status":400,"error":"Bad Request","path":"/stringinate"}
```