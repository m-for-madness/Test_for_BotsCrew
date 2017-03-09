package bbb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Main {
	
	public static void addBook(Session session){
	
		System.out.print("If you want to add book input title: ");
		Scanner sc =  new Scanner(System.in);
		String title = sc.nextLine();
		Book book = new Book(title);
		System.out.print("Also add author: ");
		
		String name = sc.nextLine();
		Author author = new Author(name);
		author.setBook(book);
		book.setAuthor(author);
		session.persist(author);
		
	}
	
	public static void removeBook(Session session){
		System.out.println("Type title of the book which you want to remove");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		// i could use map but i think it's easier 
		List<Book>books = new ArrayList<Book>();
		for (Book book : (List<Book>)session.createQuery("from Book").list()) {
			if(book.getTitle().equalsIgnoreCase(s)){
				books.add(book);
			}
		}
		int i =1;
		if(books.size()==1){
			session.delete(books.get(0));
		}
		else{
			System.out.println("There is several books with such titles, input number of exact books: ");
			for (Book book : books) {
				System.out.println(i+". " + book.getTitle());
				i++;
			}
			int choice = sc.nextInt();
			session.delete(books.get(choice-1));
		}
	}
	
	public static void editBook(Session session){
		System.out.println("Type title of the book which you want to edit");
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		String s = sc.nextLine();
		System.out.println(s);
		List<Book>books = new ArrayList<Book>();
		for (Book book : (List<Book>)session.createQuery("from Book").list()) {
			if(book.getTitle().equalsIgnoreCase(s)){
				books.add(book);
			}
		}
		int i =1;
		if(books.size()==1){
			System.out.println("Type new title for this book");
			String new_title = sc.nextLine();
			books.get(0).setTitle(new_title);
			session.update(books.get(0));
		}
		else if(books.size()==0){
			System.out.println("There isn't such book in db");
		}
		else  {
			System.out.println("There are several books with such titles, input number of exact books: ");
			for (Book book : books) {
				System.out.println(i+". " + book.getTitle());
				i++;
			}
			int choice = sc.nextInt();
			System.out.println("Type new title for this book");
			String new_title = sc2.nextLine();
			System.out.println(new_title);
			books.get(choice-1).setTitle(new_title);
			session.update(books.get(choice-1));
		}
		
		
	}
	
	
	public static void main(String[] args) {

		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.getTransaction().begin();
		int choice=0;
		do{
			Scanner scanner = new Scanner(System.in);
			System.out.println("If you want to add some books enter - 1");
			System.out.println("If you want to edit some books enter - 2");
			System.out.println("If you want to remove some books enter - 3");
			System.out.println("If you want to see the whole list of the books enter - 4");
			System.out.println("If you want to exit enter - 5");
			
			choice = scanner.nextInt();
			// i could use switch but i think it's not the best variant
			if(choice==1){
				addBook(session);
			}
			else if(choice ==2){
				editBook(session);
			}
			else if(choice==3){
				removeBook(session);
			}
			else if(choice==4){
				List<Book> books = (List<Book>)session.createQuery("from Book order by title").list();
				/*List<Book> books = (List<Book>)session.createQuery("from Book").list();
				Collections.sort(books);
				Collections.sort(books, new Comparator<Book>(){
				    public int compare(Book d1, Book d2){
				         return d1.getTitle().compareTo(d2.getTitle());
				    }    
				});*/
				for (Book book : books) {
					System.out.println(book.toString());
				}
			}
		}while(choice!=5);
	
		

		session.getTransaction().commit();

		session.close();

		factory.close();

	}
}
