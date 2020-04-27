package com.vasylyev.hometasks.google;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.vasylyev.hometasks.repository.GoogleCredentialRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JPADataStoreFactory extends AbstractDataStoreFactory {

    private final GoogleCredentialRepository repository;

    public JPADataStoreFactory(GoogleCredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JPADataStore createDataStore(String id) throws IOException {
        return new JPADataStore(this, id, repository);
    }

}
