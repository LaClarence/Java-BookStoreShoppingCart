package com.pluralsight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public final class BookDAO {

	private final Connection jdbcConnection;

	public BookDAO(Connection connection) {
		jdbcConnection = connection;
	}

	public Book getBook(int id) {
		String sql = "SELECT * FROM book WHERE id = ?";

		try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {
					String title = resultSet.getString("title");
					String author = resultSet.getString("author");
					float price = resultSet.getFloat("price");

					return new Book(id, title, author, price);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Fail to get book from id '" + id + "'");
	}

	public ArrayList<Book> listAllBooks() {

		String sql = "SELECT * FROM book";

		try ( Statement statement = jdbcConnection.createStatement();
			  ResultSet resultSet = statement.executeQuery(sql);) {

			ArrayList<Book> listBook = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				float price = resultSet.getFloat("price");

				listBook.add(new Book(id, title, author, price));
			}
			return listBook;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Fail to list all books!");
	}

	public boolean insertBook(Book book) {
		String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";

		try (PreparedStatement statement = jdbcConnection.prepareStatement(sql); ) {
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthor());
			statement.setFloat(3, book.getPrice());

			boolean rowInserted = statement.executeUpdate() > 0;
			return rowInserted;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Fail to insert a book!");
	}

	public void deleteBook(int id) {
		String sql = "DELETE FROM book WHERE id = ?";

		try (PreparedStatement statement = jdbcConnection.prepareStatement(sql);) {
			statement.setInt(1, id);
			statement.executeUpdate();
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Fail to delete a book!");
	}

	public void updateBook(Book book) {
		String sql = "UPDATE book SET title = ?, author = ?, price = ?" + " WHERE id = ?";

		try ( PreparedStatement statement = jdbcConnection.prepareStatement(sql); ) {
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthor());
			statement.setFloat(3, book.getPrice());
			statement.setInt(4, book.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Fail to update a book!");
	}
}
