/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.news;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Kostya
 */
@Named
@Stateless
public class ArticleEJB {

    private Article article;
    private String urlString = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=b5966847ed8b49198371e58c1337c0ab";

    @Inject
    public List<Article> findAllArticles() throws MalformedURLException {
        URL url = new URL(urlString);
        List<Article> articles = new ArrayList<>();

        try (InputStream is = url.openStream();
                JsonReader rdr = Json.createReader(is)) {

            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("articles");

            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                article = new Article();
                article.setAuthor(result.getString("author"));
                article.setTitle(result.getString("title"));
                article.setDescription(result.getString("description"));
                article.setUrl(result.getString("url"));
                article.setUrlToImage(result.getString("urlToImage"));
                article.setPublishedAt(result.getString("publishedAt"));

                articles.add(article);
            }

        } catch (IOException ex) {
            Logger.getLogger(ArticleEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return articles;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
