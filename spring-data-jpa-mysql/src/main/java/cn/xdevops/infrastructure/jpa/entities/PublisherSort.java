package cn.xdevops.infrastructure.jpa.entities;

import java.util.Optional;

public enum PublisherSort {

    CREATED("created", "createTime"),
    UPDATED("updated", "updateTime"),
    NAME("name", "publisherName"),
    ONBOARD("onboard", "onboardDate");

    private final String value;
    private final String field;

    PublisherSort(String value, String field) {
        this.value = value;
        this.field = field;
    }

    public String value() {
        return this.value;
    }

    public String getField() {
        return this.field;
    }

    private static final PublisherSort[] VALUES;

    static {
        VALUES = values();
    }

    public static PublisherSort valueOfSort(String sort) {
        PublisherSort publisherSort = resolve(sort);
        if (publisherSort == null) {
            throw new IllegalArgumentException("No matching constant for [" + sort + "]");
        }
        return publisherSort;
    }

    public static Optional<PublisherSort> valueOfSortOptional(String sort) {
        PublisherSort publisherSort = resolve(sort);
        if (publisherSort == null) {
            return Optional.empty();
        }
        return Optional.of(publisherSort);
    }


    public static PublisherSort resolve(String sort) {
        for (PublisherSort publisherSort : VALUES) {
            if (publisherSort.value.equals(sort)) {
                return publisherSort;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
