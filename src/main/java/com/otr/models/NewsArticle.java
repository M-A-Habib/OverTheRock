package com.otr.models;

import lombok.NonNull;

import javax.validation.constraints.NotBlank;

public record NewsArticle (
        String articleId,
        String articleTopic,
        String description,
        String source,
        String title,
        String trendingQuery,
        String url
){ }
