package controller;

import model.Book;
import service.BookService;

import java.util.List;
import java.util.UUID;

public class BookController {

    private final BookService bookService;

    public BookController() {
        this.bookService = new BookService();
    }

    public void saveBook(Book book) {
        bookService.saveBook(book);
    }

    public Book getBookById(UUID book_id) {
        return bookService.getBookById(book_id);
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public void updateBook(UUID book_id, Book updatedBook) {
        bookService.updateBook(book_id, updatedBook);
    }

    public void deleteBook(UUID book_id) {
        bookService.deleteBook(book_id);
    }
    
    public int countBooksInRoom(String roomCode) {
        return bookService.countBooksInRoom(roomCode);  
    }
   
}
