package dev.attara.stockify.domain.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface BaseRepository<Model> {
    AtomicLong atl = new AtomicLong(0);

    default long nextId() {
        return atl.addAndGet(1L);
    }

    List<Model> findAll();
    void save(Model model);
    void delete(Model model);
}
