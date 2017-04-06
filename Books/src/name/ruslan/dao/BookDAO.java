package name.ruslan.dao;

import java.util.List;

import name.ruslan.model.Book;
import name.ruslan.model.Category;

public interface BookDAO {
	
	public List<Book> findAllBooks();

	public List<Book> searchBooksByKeyword(String keyWord);

	public List<Category> findAllCategories();

	public void insert(Book book);

	public void update(Book book);

	public void delete(Long bookId);
}
