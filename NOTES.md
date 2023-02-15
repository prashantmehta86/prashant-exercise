## General ##

For the requested task, I have added the changes in the `StringinatorServiceImpl` file with the implementation details discussed below.

* To find frequently occurring character in the string, I have traveresed through each character of the string and maintained their character count mappings in `characterOccurrence` HashMap.
* Once we have all the string character to occurrence count mappings created in HashMap, I have traversed through the HashMap to get an entry with highest chracter count occurence and return that from `findFrequentlyOccurringCharacter` method.
   * To track most frequently occurring character along with its count, I decided to add `most_frequent_character` and `most_frequent_character_occurrence_count` as class fields so that it is easier for an end user to parse them from the response and get the data. 
* For stats API, computing most seen string I have used similar HashMap traversal approach to find the key with maximum value. For getting the mostPopularKey entered by the user I have traversed through the `seenStrings` HashMap and compared the values to return the key with highest value.
* For `longest_input_received` stats, I have traversed through the HashMap keyset to get the keys with longest length and return that from the API.

### Refactoring ###

* I have used Lombok Utils in model package to add Annotation processing, which can easily crate complex with minimum code.
    * I have added Lombok `@NonNull` validation check in `StringinatorServiceImpl` class methods with the intention to validate them via unit tests. To keep the exercise time restrictions to a max of 2 hours, I have opted for alternate developer choice feature.
    * Additionally some of the input validation done via Spring boot validation accomodates for `StringinatorServiceImpl` method validations. 
* When I was building the code, I had to add print statement to test my code. So instead of adding them at multiple places I decided to use logging which can help user troubleshoot the issue and with no throw away work.

### Developer Choice Feature ###

* I have added input validation as it was required as a part of performing testing of the service (as shown below). 
* Logging was organically added to in-corporate faster development as it ensured that there would not be any throw away work from it.

### Challenges ###

* We have a MVC framework (similar to Spring boot) in my company with different semantics and layout, so initially it took some time to add get accustomed to this framework.



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

13. Performing String validation tests with ignoring spaces, punctuation and invalid strings.

* Invalid input

```
Request:
curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=

Response:
{"timestamp":"2023-02-15T04:30:36.454+00:00","status":400,"error":"Bad Request","path":"/stringinate"}

```

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=''"

Response:
{"timestamp":"2023-02-15T04:30:26.942+00:00","status":400,"error":"Bad Request","path":"/stringinate"}

```

* Sanitizing string with removing spaces and puntuation.

```
Request:
curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=test45

Response:
{"input":"test45","length":6,"most_frequent_character":"t","most_frequent_character_occurrence_count":2
```

```
Request:

curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=test%2045"

Response:

{"input":"test45","length":6,"most_frequent_character":"t","most_frequent_character_occurrence_count":2}

```

```
Request:
curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stringinate?input=test-45"

Resposne:

{"input":"test45","length":6,"most_frequent_character":"t","most_frequent_character_occurrence_count":2}

```

```
Request:
curl -id '{"input":"test       45"}' -H 'Content-Type: application/json' http://localhost:8080/stringinate

Response:
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 15 Feb 2023 04:38:07 GMT

{"input":"test45","length":6,"most_frequent_character":"t","most_frequent_character_occurrence_count":2}%
```

* Stats API
```
Request:
curl -X GET -H 'Content-Type: application/json' "http://localhost:8080/stats

Response:
{"inputs":{"test45":4},"most_popular":"test45","longest_input_received":"test45"}
```