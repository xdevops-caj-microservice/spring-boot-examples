package cn.xdevops.application.services;

import cn.xdevops.domain.model.Publisher;
import cn.xdevops.exception.PublisherNotFoundException;
import cn.xdevops.infrastructure.jpa.entities.PublisherJpaEntity;
import cn.xdevops.infrastructure.jpa.entities.PublisherSort;
import cn.xdevops.infrastructure.jpa.repositories.PublisherRepository;
import cn.xdevops.interfaces.transform.PublisherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        PublisherJpaEntity publisherJpaEntity = publisherRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
        return PublisherMapper.MAPPER.toPublisher(publisherJpaEntity);
    }

    @Transactional
    public Publisher updatePublisher(Publisher newPublisher, Long id) {
        return publisherRepository.findByIdAndDeletedFalse(id)
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
        deletePublisher(id);
    }

    public List<Publisher> findAllPublishers() {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByDeletedFalseOrderByPublisherNameAsc());
    }

    public List<Publisher> findPublisherByCity(String city) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByCity(city));
    }

    public List<Publisher> findPublisherByCityLike(String city) {
        // must provide "%"
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByCityLike(city + "%"));
    }

    public List<Publisher> findPublisherByCityContaining(String city) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByCityContaining(city));
    }

    public List<Publisher> findPublisherByCityIn(List<String> cityList) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByCityIn(cityList));
    }

    public List<Publisher> findPublisherByOnboardDateGreaterThan(LocalDate onboardDate) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByOnboardDateGreaterThan(onboardDate));
    }

    public List<Publisher> findPublisherByOnboardDateLessThan(LocalDate onboardDate) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByOnboardDateLessThan(onboardDate));
    }

    public List<Publisher> findPublisherByOnboardDateBetween(LocalDate startDate, LocalDate endDate) {
        return PublisherMapper.MAPPER.toPublisherList(publisherRepository.findByOnboardDateBetween(startDate, endDate));
    }

//    private Optional<PublisherJpaEntity> findValidPublisher(Long id) {
//        return publisherRepository.findByIdAndDeleted(id, false);
//    }

//    private List<PublisherJpaEntity> findAllValidPublishers() {
//        return publisherRepository.findByDeletedOrderByPublisherNameAsc(false);
//    }

    private void deletePublisher(Long id) {
        publisherRepository.updateDeletedAndUpdateTimeById(false, LocalDateTime.now(), id);
    }

    public List<Publisher> findAllPublishersSorted(String sort, String direction) {
        Sort dynamicSort = Sort.by(Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC),
                PublisherSort.valueOfSortOptional(sort).orElse(PublisherSort.CREATED).getField());
        return PublisherMapper.MAPPER.toPublisherList(
                publisherRepository.findByDeletedFalse(dynamicSort));
    }

    public Page<Publisher> findAllPublishersPageable(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.min(size, 10),
                Sort.by(PublisherSort.ONBOARD.getField()));
        return PublisherMapper.MAPPER.toPublisherPage(
                publisherRepository.findByDeletedFalse(pageable));
    }
}
