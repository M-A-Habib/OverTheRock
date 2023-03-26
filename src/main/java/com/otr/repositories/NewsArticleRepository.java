package com.otr.repositories;

import com.otr.models.NewsArticle;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface NewsArticleRepository {
    @NonNull Optional<NewsArticle> findById(@NonNull @NotBlank String id, @NonNull @NotBlank String topic);
}
