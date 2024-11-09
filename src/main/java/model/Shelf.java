package model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Shelf")
public class Shelf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Use AUTO if you want to generate UUID automatically
	@Column(name = "shelf_id", columnDefinition = "UUID")
	private UUID shelf_id;

	@Column(name = "book_category")
	private String book_category;

	@Column(name = "available_stock")
	int available_stock;

	@Column(name = "borrowed_number")
	int borrowed_number;

	@Column(name = "initial_stock")
	int initial_stock;

	
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@OneToMany(mappedBy = "shelf")
	private List<Book> book;
	
	public Shelf() {
		
	}
	
	public Shelf(UUID shelf_id, String book_category, Room room) {
	    this.shelf_id = shelf_id;
	    this.book_category = book_category;
	    this.room = room;
	}
	
	public Shelf(UUID shelf_id, String book_category, int available_stock, int borrowed_number, int initial_stock,
			Room room, List<Book> book) {
		super();
		this.shelf_id = shelf_id;
		this.book_category = book_category;
		this.available_stock = available_stock;
		this.borrowed_number = borrowed_number;
		this.initial_stock = initial_stock;
		this.room = room;
		this.book = book;
	}



	public UUID getShelf_id() {
		return shelf_id;
	}

	public void setShelf_id(UUID shelf_id) {
		this.shelf_id = shelf_id;
	}

	public String getBook_category() {
		return book_category;
	}

	public void setBook_category(String book_category) {
		this.book_category = book_category;
	}
	
	public int getAvailable_stock() {
		return available_stock;
	}

	public void setAvailable_stock(int available_stock) {
		this.available_stock = available_stock;
	}

	public int getBorrowed_number() {
		return borrowed_number;
	}

	public void setBorrowed_number(int borrowed_number) {
		this.borrowed_number = borrowed_number;
	}

	public int getInitial_stock() {
		return initial_stock;
	}

	public void setInitial_stock(int initial_stock) {
		this.initial_stock = initial_stock;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

}
