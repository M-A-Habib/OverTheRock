package com.otr.repositories;

import com.otr.configurations.DynamoConfiguration;
import com.otr.models.NewsArticle;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.validation.constraints.NotBlank;
import java.awt.print.Book;
import java.util.Map;
import java.util.Optional;


@Singleton
public class NewsArticleRepoitoryImpl extends DynamoRepository<NewsArticle> implements NewsArticleRepository{
    public NewsArticleRepoitoryImpl(DynamoDbClient dynamoDbClient,
                                    DynamoConfiguration dynamoConfiguration) {
        super(dynamoDbClient, dynamoConfiguration);
    }
    @Override
    @NonNull
    public Optional<NewsArticle> findById(@NonNull @NotBlank String id, @lombok.NonNull @NotBlank String topic) {
        return super.findById(NewsArticle.class, id, topic)
                .map(this::articleOf);
    }

    @NonNull
    private NewsArticle articleOf(@NonNull Map<String, AttributeValue> item) {
        return new NewsArticle(item.get("articleId").s(),
                item.get("articleTopic").s(),
                item.get("description").s(),
                item.get("source").s(),
                item.get("title").s(),
                item.get("trendingQuery").s(),
                item.get("url").s());
    }
}
