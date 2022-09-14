package cn.xdevops.application.services;

import cn.xdevops.domain.model.entities.Publisher;
import cn.xdevops.exception.PublisherNotFoundException;
import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import cn.xdevops.infrastructure.jpa.repositories.PublisherRepository;
import cn.xdevops.interfaces.transform.PublisherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PublisherService {
    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher createPublisher(Publisher publisher) {
        PublisherJpaEntity newPublisherJpaEntity = PublisherMapper.MAPPER.toJpaEntity(publisher);
        newPublisherJpaEntity.setCreateTime(LocalDateTime.now());
        newPublisherJpaEntity.setUpdateTime(LocalDateTime.now());

        PublisherJpaEntity savedPublisherJpaEntity = publisherRepository.save(newPublisherJpaEntity);
        return PublisherMapper.MAPPER.toPublisher(savedPublisherJpaEntity);
    }

    public Publisher findPublisherById(Long id) {
        PublisherJpaEntity publisherJpaEntity = publisherRepository.findPublisherById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
        return PublisherMapper.MAPPER.toPublisher(publisherJpaEntity);
    }

    public Publisher updatePublisher(Publisher newPublisher, Long id) {
        return publisherRepository.findPublisherById(id)
                .map(p -> {
                    p.setPublisherName(newPublisher.getPublisherName());
                    p.setUpdateTime(LocalDateTime.now());
                    return PublisherMapper.MAPPER.toPublisher(publisherRepository.save(p));
                })
                .orElseGet(() -> {
                    newPublisher.setId(id);
                    PublisherJpaEntity newPublisherJpaEntity = PublisherMapper.MAPPER.toJpaEntity(newPublisher);
                    newPublisherJpaEntity.setCreateTime(LocalDateTime.now());
                    newPublisherJpaEntity.setUpdateTime(LocalDateTime.now());
                    return PublisherMapper.MAPPER.toPublisher(publisherRepository.save(newPublisherJpaEntity));
                });
    }

    public void deletePublisherById(Long id) {
        // soft delete
        publisherRepository.deletePublisherById(id);
    }

    public List<Publisher> findAllPublishers() {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findAllPublishers());
    }
}
