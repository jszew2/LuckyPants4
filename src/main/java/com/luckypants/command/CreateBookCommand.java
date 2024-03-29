package com.luckypants.command;

import org.codehaus.jackson.map.ObjectMapper;

import com.luckypants.model.Book;
import com.luckypants.mongo.BooksConnectionProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class CreateBookCommand {

	public boolean execute(Book book) {
		BooksConnectionProvider booksConn = new BooksConnectionProvider();
		DBCollection booksCollection = booksConn.getCollection();

		ObjectMapper mapper = new ObjectMapper();
		try {
			DBObject dbObject = (DBObject) JSON.parse(mapper
					.writeValueAsString(book));
			booksCollection.insert(dbObject);
		} catch (Exception e) {
			System.out.println("ERROR during mapping book to Mongo Object");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		CreateBookCommand create = new CreateBookCommand();
		Book book = new Book();
		book.setAuthor("Gula");
		book.setTitle("Book2");
		book.setISBN("123");
		book.setGenre("Kids");
		book.setPages("paper");
		
		Book book1 = new Book();
		book1.setAuthor("Tom");
		book1.setTitle("Book1");
		book1.setISBN("456");
		book1.setGenre("family");
		book1.setPages("cardboard");
		
		if (create.execute(book)) {
			System.out.println("SUCCESS:Book Created");
		} else {
			System.out.println("ERROR:Failed to create book");
		}

		if (create.execute(book1)) {
			System.out.println("SUCCESS:Book Created");
		} else {
			System.out.println("ERROR:Failed to create book");
		}
	}
}
