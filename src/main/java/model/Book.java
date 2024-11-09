package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	UUID book_id;

	@Column(name = "ISBNCode")
	String ISBNCode;

	@Column(name = "title")
	String title;

	@Column(name = "publisher_name")
	String publisher_name;

	@Column(name = "publication_year")
	Date publication_year;

	@Column(name = "edition")
	int edition;

	@Enumerated(EnumType.STRING)
	@Column(name = "book_status")
	Book_status book_status;

	@ManyToOne
	@JoinColumn(name = "shelf_id")
	Shelf shelf;

	@OneToMany(mappedBy = "book")
	List<Borrower> borrower;

	public UUID getBook_id() {
		return book_id;
	}

	public void setBook_id(UUID book_id) {
		this.book_id = book_id;
	}

	public String getISBNCode() {
		return ISBNCode;
	}

	public void setISBNCode(String iSBNCode) {
		ISBNCode = iSBNCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public Date getPublication_year() {
		return publication_year;
	}

	public void setPublication_year(Date publication_year) {
		this.publication_year = publication_year;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	

	public Book_status getBook_status() {
		return book_status;
	}

	public void setBook_status(Book_status book_status) {
		this.book_status = book_status;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public List<Borrower> getBorrower() {
		return borrower;
	}

	public void setBorrower(List<Borrower> borrower) {
		this.borrower = borrower;
	}
	
	public String getFormattedPublicationYear() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return publication_year != null ? dateFormat.format(publication_year) : "";
    }
}
