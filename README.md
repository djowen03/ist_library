need database mysql library.sql, kafka, and java 21

how to install
1. git clone the code
2. create table ist_library
3. import the ist_library.sql
4. run kafka
5. create topic library
6. run the code (using intellij)

endpoint
1. get http://localhost:8080/api/v1/book
2. post http://localhost:8080/api/v1/book/add
   request json:
   "bookName" : "",
   "author" : "",
   "publicationYear" : ""

example response
{
"code": 200,
"data": "",
"message": "Success",
"timestamp": ""
}
