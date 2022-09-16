package cn.xdevops.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private List<T> content;
    private CustomPage page;

    public PageDto(Page<T> originalPage) {
        this.content = originalPage.getContent();
        this.page = CustomPage
                .builder()
                .totalElements(originalPage.getTotalElements())
                .totalPages(originalPage.getTotalPages())
                .pageNumber(originalPage.getNumber() + 1) // page number starts from 1
                .pageSize(originalPage.getSize())
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class CustomPage {
        private Long totalElements;
        private Integer pageNumber;
        private Integer pageSize;
        private Integer totalPages;
    }
}
