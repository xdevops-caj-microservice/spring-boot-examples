package cn.xdevops.infrastructure.jpa.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

class PublisherSortTest {

    @Test
    @DisplayName("should transform sort string to field")
    void shouldTransformSortStringToField() {
        assertThat(PublisherSort.valueOfSortOptional("created").get().getField()).isEqualTo("createTime");
        assertThat(PublisherSort.valueOfSortOptional("updated").get().getField()).isEqualTo("updateTime");
        assertThat(PublisherSort.valueOfSortOptional("name").get().getField()).isEqualTo("publisherName");
    }

    @Test
    @DisplayName("should transform unknown sort string to optional empty")
    void shouldTransformUnknownSortStringToOptionalEmpty() {
        assertThat(PublisherSort.valueOfSortOptional(null)).isEmpty();
        assertThat(PublisherSort.valueOfSortOptional("")).isEmpty();
        assertThat(PublisherSort.valueOfSortOptional(" ")).isEmpty();
        assertThat(PublisherSort.valueOfSortOptional("unknown")).isEmpty();

        assertThat(PublisherSort.valueOfSortOptional("unknown")
                .orElse(PublisherSort.CREATED).getField()).isEqualTo("createTime");
    }

    @Test
    @DisplayName("should transform sort direction")
    void shouldTransformSortDirection() {
        assertThat(Sort.Direction.fromString("asc")).isEqualTo(Sort.Direction.ASC);
    }

}