package cn.xdevops.interfaces.transform;

import cn.xdevops.domain.model.Publisher;
import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import cn.xdevops.interfaces.dto.PageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface PublisherMapper {
    PublisherMapper MAPPER = Mappers.getMapper(PublisherMapper.class);

    PublisherJpaEntity toJpaEntity(Publisher publisher);

    Publisher toPublisher(PublisherJpaEntity publisherJpaEntity);

    List<Publisher> toPublisherList(List<PublisherJpaEntity> publisherJpaEntityList);

    /**
     * Transform `Page<PublisherJpaEntity>` to `Page<Publisher>`
     * https://github.com/mapstruct/mapstruct/issues/607
     * @param publisherJpaEntityPage
     * @return
     */
    default Page<Publisher> toPublisherPage(Page<PublisherJpaEntity> publisherJpaEntityPage) {
        return publisherJpaEntityPage.map(this::toPublisher);
    }

    default PageDto<Publisher> toPublisherPageDto(Page<PublisherJpaEntity> publisherJpaEntityPage) {
        return new PageDto<>(toPublisherPage(publisherJpaEntityPage));
    }
}
