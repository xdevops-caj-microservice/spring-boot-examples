package cn.xdevops.interfaces.rest;

import cn.xdevops.application.services.PublisherService;
import cn.xdevops.domain.model.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/search/city")
    public List<Publisher> findPublisherByCity(@RequestParam String city) {
        return publisherService.findPublisherByCity(city);
    }

    @GetMapping("/search/citylike")
    public List<Publisher> findPublisherByCityLike(@RequestParam String city) {
        return publisherService.findPublisherByCityLike(city);
    }

    @GetMapping("/search/citycontaining")
    public List<Publisher> findPublisherByCityContaining(@RequestParam String city) {
        return publisherService.findPublisherByCityContaining(city);
    }

    @GetMapping("/search/cityin")
    public List<Publisher> findPublisherByCityIn(@RequestParam String city1, @RequestParam String city2) {
        return publisherService.findPublisherByCityIn(List.of(city1, city2));
    }

    @GetMapping("/search/onboardgreaterthan")
    public List<Publisher> findPublisherByOnboardDateGreaterThan(@RequestParam("onboard") String onboard) {
        LocalDate onboardDate = LocalDate.parse(onboard);
        return publisherService.findPublisherByOnboardDateGreaterThan(onboardDate);
    }

    @GetMapping("/search/onboardlessthan")
    public List<Publisher> findPublisherByOnboardDateLessThan(@RequestParam("onboard") String onboard) {
        LocalDate onboardDate = LocalDate.parse(onboard);
        return publisherService.findPublisherByOnboardDateLessThan(onboardDate);
    }

    @GetMapping("/search/onboardbetween")
    public List<Publisher> findPublisherByOnboardDateBetween(@RequestParam("start") String start,
                                                             @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return publisherService.findPublisherByOnboardDateBetween(startDate, endDate);
    }

    /**
     *
     * @param sort What to sort results by.
     *             Default: `created`
     *             Can be one of: `created`, `updated`, `name`
     * @param direction The direction to sort the results by.
     *                  Default: `desc`
     *                  Can be one of: `asc`, `desc`
     * @return
     */
    @GetMapping("/search/sorting")
    public List<Publisher> findAllPublishersSorted(@RequestParam String sort, @RequestParam String direction) {
        return publisherService.findAllPublishersSorted(sort, direction);
    }


}
