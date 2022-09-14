package cn.xdevops.interfaces.rest;

import cn.xdevops.application.services.PublisherService;
import cn.xdevops.domain.model.entities.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @GetMapping("/{id}")
    public Publisher findPublisher(@PathVariable Long id) {
        return publisherService.findPublisherById(id);
    }

    @PutMapping("/{id}")
    public Publisher updatePublisher(@RequestBody Publisher newPublisher, @PathVariable Long id) {
        return publisherService.updatePublisher(newPublisher, id);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisherById(id);
    }

    @GetMapping("")
    public List<Publisher> findAllPublishers() {
        return publisherService.findAllPublishers();
    }
}
