package com.otr.repositories;

import com.otr.awsClasses.CIAwsCredentialsProviderChainCondition;
import com.otr.awsClasses.CIAwsRegionProviderChainCondition;
import com.otr.configurations.DynamoConfiguration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;


@Requires(condition = CIAwsRegionProviderChainCondition.class)
@Requires(condition = CIAwsCredentialsProviderChainCondition.class)
public class DynamoRepository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(DynamoRepository.class);
    protected static final String HASH = "#";
    protected static final String ATTRIBUTE_PK = "articleId";
    protected static final String ATTRIBUTE_SK = "articleTopic";

    protected final DynamoDbClient dynamoDbClient;
    protected final DynamoConfiguration dynamoConfiguration;

    public DynamoRepository(DynamoDbClient dynamoDbClient,
                            DynamoConfiguration dynamoConfiguration) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoConfiguration = dynamoConfiguration;
    }

    protected Optional<Map<String, AttributeValue>> findById(@NonNull @NotNull Class<?> cls, @NonNull @NotBlank String id, String topic) {
        AttributeValue pk = AttributeValue.builder()
                .s(id).build();
        AttributeValue sk = AttributeValue.builder()
                .s(topic).build();
        GetItemResponse getItemResponse = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName(dynamoConfiguration.getTableName())
                .key(Map.of(ATTRIBUTE_PK, pk, ATTRIBUTE_SK, sk))
                .build());
        return !getItemResponse.hasItem() ? Optional.empty() : Optional.of(getItemResponse.item());
    }
}
