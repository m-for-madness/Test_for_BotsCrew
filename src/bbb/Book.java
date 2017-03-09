package bbb;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Book implements Comparable<Book>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBook;
	private String title;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Author author;

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(String title) {
		this.title = title;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return author.getName() +"  "+ title;
		//return title;
	}

	@Override
	public int compareTo(Book o) {
		int res = this.title.compareTo(o.getTitle());
		return res;
	}

	
	

}
