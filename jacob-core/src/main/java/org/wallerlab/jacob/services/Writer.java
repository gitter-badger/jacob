package org.wallerlab.jacob.services;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.jacob.data.BenchmarkRepository;
import org.wallerlab.jacob.domain.Benchmark;

@Service
public class Writer implements ItemWriter<Benchmark> {

    @Autowired
    BenchmarkRepository repo;

    @Override
    public void write(List<? extends Benchmark> items) throws Exception {
        for(Benchmark benchmark: items)
            repo.save(benchmark);
    }
}
