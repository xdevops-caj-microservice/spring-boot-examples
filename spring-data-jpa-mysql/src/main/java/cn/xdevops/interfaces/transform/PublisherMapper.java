package cn.xdevops.interfaces.transform;

import cn.xdevops.domain.model.entities.Publisher;
import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PublisherMapper {
    PublisherMapper MAPPER = Mappers.getMapper(PublisherMapper.class);

    PublisherJpaEntity toJpaEntity(Publisher publisher);

    Publisher toPublisher(PublisherJpaEntity publisherJpaEntity);

    List<Publisher> toPublisherList(List<PublisherJpaEntity> publisherJpaEntityList);
}
