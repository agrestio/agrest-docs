package io.agrest.docs.framework.value;

import com.fasterxml.jackson.databind.JsonNode;
import io.agrest.converter.jsonvalue.JsonValueConverter;

public class MoneyConverter implements JsonValueConverter<Money> {

    @Override
    public Money value(JsonNode jsonNode) {
        return new Money();
    }
}
