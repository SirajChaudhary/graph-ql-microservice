package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.request.PublisherInput;
import com.sirajchaudhary.library.entity.Publisher;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.PublisherRepository;
import com.sirajchaudhary.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;

    @Override
    public Publisher getPublisher(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));
    }

    @Override
    public List<Publisher> getPublishers() {
        return repository.findAll();
    }

    @Override
    public Publisher createPublisher(PublisherInput input) {

        log.info("Creating publisher: {}", input.getName());

        if (input.getEmail() != null
                && !input.getEmail().isBlank()
                && repository.existsByEmail(input.getEmail())) {

            throw new IllegalArgumentException(
                    "Publisher with email '" + input.getEmail() + "' already exists.");
        }

        Publisher publisher = Publisher.builder()
                .name(input.getName())
                .email(input.getEmail())
                .website(input.getWebsite())
                .build();

        return repository.save(publisher);
    }

    @Override
    public Publisher updatePublisher(Long id, PublisherInput input) {

        Publisher publisher = getPublisher(id);

        publisher.setName(input.getName());
        publisher.setEmail(input.getEmail());
        publisher.setWebsite(input.getWebsite());

        return repository.save(publisher);
    }

    @Override
    public boolean deletePublisher(Long id) {
        repository.deleteById(id);
        return true;
    }
}