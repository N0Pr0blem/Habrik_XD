package com.example.user_service.repository.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SimpleListRepositoryImpl<T, I> implements SimpleListRepository<T, I> {

    private List<T> tList = new ArrayList<>();
    private final Function<T, I> idExtractor;

    public SimpleListRepositoryImpl(Function<T, I> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public Optional<T> findById(I i) {
        return tList.stream().
                filter(item -> idExtractor.apply(item).equals(i))
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        return tList;
    }

    @Override
    public T save(T t) {
        tList.add(t);
        return t;
    }

    @Override
    public void deleteById(I i) {
        findById(i)
                .map(t -> tList.remove(t))
                .orElseThrow(() -> new RuntimeException("No such element exception"));
    }

    @Override
    public boolean existsById(I i) {
        return  findById(i).isPresent();
    }
}
