package com.fpoon.jaybb.wrapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageWrapper<T> implements Page<T> {

    public static final int MAX_PAGES = 5;

    protected Page<T> page;

    public PageWrapper(Page<T> page) {
        this.page = page;
    }

    public Integer getPreviousMaxPages() {
        return Math.max(0, getNumber()-MAX_PAGES);
    }

    public Integer getNextMaxPages() {
        return Math.min(getNumber()+MAX_PAGES+1, getTotalPages());
    }

    public List<Integer> getPreviousPagesNumbers() {
        return IntStream.range(getPreviousMaxPages(), getNumber()).boxed().collect(Collectors.toList());
    }

    public List<Integer> getNextPagesNumbers() {
        return IntStream.range(getNumber()+1, getNextMaxPages()).boxed().collect(Collectors.toList());
    }

    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return page.map(converter);
    }

    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        page.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return page.spliterator();
    }
}
