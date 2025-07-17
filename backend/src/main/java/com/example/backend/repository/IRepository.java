package com.example.backend.repository;

import java.util.List;

public interface IRepository<T> {
    void save(T entity);
    void deleteById(Long id);
    T findById(Long id);
    List<T> findAll();
}