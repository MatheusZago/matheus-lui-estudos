# Activity 4

This project is the activity 4 requested by the compass team. 

## Requirements
Make sure you have the following tools installed:
- Java Development Kit (JDK) 
- Spring Tool Suit 4 or Eclipse.
- Apache Maven.
- MongoDB
- Postman

## Instalation
Siga os passos abaixo para configurar o projeto no seu ambien
1) Create a folder to contain the project.
2) Open Git Bash in the desired folder.
3) Type:

```bash
   git clone https://github.com/MatheusZago/matheus-lui-estudos
 ```
 
2. **Import the Project**

1) Open your IDE of choice (Recommended: STS or Eclipse) in the workspace of the cloned project.
2) Import the project as "Existing Projects into Workspace".
3) Navigate to the project, select it, and click "Finish".

3. **Create the Database** 

1) On your CMD start MongoDB using the command 
```bash
   mongod
 ```
2) Open your mongoDB and connect to Local connection.
3) Create a 'library' database.
4) Create a 'book' colection.
 
4. **Executing the project**
1) Run the project as a spring boot app.
2) Open postman and click on new API request

5. **Testing the project**
1) Execute the request (POST) http://localhost:8080/books/insertAll to insert the books in the database.
2) Execute the request (GET) http://localhost:8080/books to get all the books to confirm the operation after every step.
3) Execute the request (DELETE) http://localhost:8080/books/removeByTitle?title=Dom Casmurro to delete a book
 Obs: You can change the Title of the deleted book to delete another one.
4) Execute the Request (PUT) http://localhost:8080/books/1984 to update the book with the body:
*{
        "title": "1984",
        "author": "George Orwell",
        "year": "1950",
        "genre": "Ficção Cientifica"
 }*

You can change the body to update another field, or you can change the book name after books to update another book.
5) Execute the Request (GET) http://localhost:8080/books/findByAuthor/George Orwell to find all books with that author name.
   *Obs: You can change the author name to find another one in the list.*
6) Execute the Request (GET) http://localhost:8080/books/after/1900 to find all books made after 1900 
   *Obs: You can change the year to find another result, the same can happen changing after to before.*

 

### Contact
Any questions, enter in contact with: 
* Nome: Matheus Lui Zago
* Email: matheuszago134l@gmail.com
* GitHub: github.com/MatheusZago
