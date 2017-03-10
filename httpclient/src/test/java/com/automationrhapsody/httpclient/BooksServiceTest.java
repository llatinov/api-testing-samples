package com.automationrhapsody.httpclient;

import com.automationrhapsody.httpclient.model.Book;
import com.automationrhapsody.httpclient.model.Catalog;
import com.automationrhapsody.httpclient.utils.BooksServiceHttpClient;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BooksServiceTest {

    private BooksServiceHttpClient booksServiceHttpClient;

    @Before
    public void setUp() {
        booksServiceHttpClient = new BooksServiceHttpClient();
    }

    @Test
    public void testGetCatalog() {
        Catalog catalog = booksServiceHttpClient.getCatalog();

        assertThat(catalog.getBooks().size(), is(3));
        assertThat(catalog.getBooks().get(0).getId(), is("bk101"));
        assertThat(catalog.getBooks().get(1).getGenre(), is("Fantasy"));
        assertThat(catalog.getBooks().get(2).getAuthor(), is("Corets, Eva"));
    }

    @Test
    public void testGetCatalogSoap() {
        Catalog catalog = booksServiceHttpClient.getCatalogSoap();

        assertThat(catalog.getBooks().size(), is(3));
        assertThat(catalog.getBooks().get(0).getId(), is("bk101"));
        assertThat(catalog.getBooks().get(1).getGenre(), is("Fantasy"));
        assertThat(catalog.getBooks().get(2).getAuthor(), is("Corets, Eva"));
    }

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setId("12345");
        book.setAuthor("Author");

        String response = booksServiceHttpClient.saveBook(book);

        assertThat(response, is("Book with id=12345 has been saved."));
    }
}
