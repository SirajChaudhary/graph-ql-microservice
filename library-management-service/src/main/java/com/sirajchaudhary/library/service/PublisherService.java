package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.request.PublisherInput;
import com.sirajchaudhary.library.entity.Publisher;

import java.util.List;

public interface PublisherService {

    Publisher getPublisher(Long id);

    List<Publisher> getPublishers();

    Publisher createPublisher(PublisherInput input);

    Publisher updatePublisher(Long id, PublisherInput input);

    boolean deletePublisher(Long id);
}