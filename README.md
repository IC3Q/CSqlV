# CSqlV

Mini-project for querying CSV file.

Requirements:
  - Gradle (at least 2.3, recommended 2.14)
  - Java 1.8

Build:
```sh
$ gradle build
```

Usage:
  - by raw jar: 
```sh
$ java -jar build/libs/CSqlV.jar --query="YOUR_QUERY"
$ java -jar build/libs/CSqlV.jar --help
```
  - by run.sh: 
```sh
$ ./run.sh -q "YOUR_QUERY"
$ ./run.sh -h
```

Query example: 
> "SELECT * FROM \"FILEPATH\" WHERE a < 2 and b LIKE %a% ORDER BY c DESC LIMIT 3"

Limitations:
  - Usage of Count(col) applies WHERE statement and then prints number of result rows,
  - WHERE statements are limited to <, >, <=, >=, LIKE, =. Those can be stacked on each others using parenthesis, AND or OR,
  - If <, >, <= or >= operator will be used, columns has to have number values,
  - File path provided in query has to be in escaped quotes (e.g. \"filepath\").
