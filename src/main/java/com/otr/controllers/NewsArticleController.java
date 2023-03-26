package com.otr.controllers;

import com.otr.models.NewsArticle;
import com.otr.repositories.NewsArticleRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class NewsArticleController {
    private final NewsArticleRepository newsArticleRepository;

    @Get("/api/articles/test")
    public HttpResponse<String> test(){
        return HttpResponse.ok().body("Hello from the other side");
    }

    @Get("/api/{articleTopic}/{articleId}")
    public HttpResponse<NewsArticle> test1(@PathVariable("articleTopic") String articleTopic, @PathVariable("articleId") String articleId){
        return HttpResponse.ok().body(
                newsArticleRepository.findById(articleId, articleTopic).get()
        );
    }
}
