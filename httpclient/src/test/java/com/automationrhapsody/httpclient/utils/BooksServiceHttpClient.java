package com.automationrhapsody.httpclient.utils;

import com.automationrhapsody.httpclient.model.Book;
import com.automationrhapsody.httpclient.model.Catalog;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BooksServiceHttpClient {

    private static final String BOOKS = "http://automationrhapsody.com/examples/api-testing-books-endpoint/books.xml";
    private static final String BOOKS_SOAP = "http://automationrhapsody.com/examples/api-testing-books-endpoint/books-soap.xml";
    private static final String BOOKS_SERVICE = "http://automationrhapsody.com/examples/api-testing-books-endpoint/books-service.php";

    private CloseableHttpClient personServiceClient;

    public BooksServiceHttpClient() {
        this.personServiceClient = HttpClients.createDefault();
    }

    public Catalog getCatalog() {
        return get(BOOKS, Catalog.class);
    }

    public Catalog getCatalogSoap() {
        return get(BOOKS_SOAP, Catalog.class);
    }

    public String saveBook(Book book) {
        try {
            HttpPost httpPost = new HttpPost(BOOKS_SERVICE);
            httpPost.setEntity(new StringEntity(JaxbUtils.toXmlString(book), ContentType.APPLICATION_XML));

            HttpResponse response = personServiceClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return IOUtils.toString(entity.getContent());
            }
        } catch (IOException ioe) {
            Log.logError(ioe.getMessage());
        }
        return StringUtils.EMPTY;
    }

    private <T> T get(String url, Class<T> clazz) {
        try {
            HttpGet httpget = new HttpGet(url);

            HttpResponse response = personServiceClient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return JaxbUtils.read(entity.getContent(), clazz);
            }
        } catch (IOException ioe) {
            Log.logError(ioe.getMessage());
        }
        return null;
    }
}
