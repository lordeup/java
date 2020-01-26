package com.files;

import java.util.*;

public class ArraySet<T extends Comparable<T>> extends AbstractSet<T> implements NavigableSet<T> {
  private final List<T> _list;

  ArraySet(List<T> data) {
    _list = data;
  }

  @Override
  public T lower(T t) {
    int index = this.indexOfList(t);
    return (index == 0 || index == -1) ? null : _list.get(index - 1);
  }

  @Override
  public T floor(T t) {
    int index = this.indexOfList(t);
    return index == -1 ? null : (index == 0 ? _list.get(index) : _list.get(index - 1));
  }

  @Override
  public T ceiling(T t) {
    int index = this.indexOfList(t);
    return index == -1 ? null : (index == this.size() - 1 ? _list.get(index) : _list.get(index + 1));
  }

  @Override
  public T higher(T t) {
    int index = this.indexOfList(t);
    return (index == -1 || index == this.size() - 1) ? null : _list.get(index + 1);
  }

  @Override
  public T pollFirst() {
    throw new UnsupportedOperationException("pollFirst on immutable set");
  }

  @Override
  public T pollLast() {
    throw new UnsupportedOperationException("pollLast on immutable set");
  }

  @Override
  public Iterator<T> iterator() {
    return _list.iterator();
  }

  @Override
  public NavigableSet<T> descendingSet() {
    List<T> copyList = _list;
    Collections.reverse(copyList);
    return new ArraySet<>(copyList);
  }

  @Override
  public Iterator<T> descendingIterator() {
    return this.descendingSet().iterator();
  }

  @Override
  public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
    return tailSet(fromElement, fromInclusive).headSet(toElement, toInclusive);
  }

  @Override
  public NavigableSet<T> headSet(T toElement, boolean inclusive) {
    int index = this.indexOfList(toElement);
    if (index == -1 || (index == 0 && inclusive)) {
      throw new NullPointerException("toElement is null");
    }

    return new ArraySet<>(_list.subList(0, (inclusive ? index + 1 : index)));
  }

  @Override
  public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
    int index = this.indexOfList(fromElement);
    if (index == -1 || (index == this.size() - 1 && !inclusive)) {
      throw new NullPointerException("fromElement is null");
    }

    return new ArraySet<>(_list.subList((inclusive ? index : index + 1), this.size()));
  }

  @Override
  public Comparator<? super T> comparator() {
    return T::compareTo;
  }

  @Override
  public SortedSet<T> subSet(T fromElement, T toElement) {
    return this.subSet(fromElement, true, toElement, false);
  }

  @Override
  public SortedSet<T> headSet(T toElement) {
    return this.headSet(toElement, false);
  }

  @Override
  public SortedSet<T> tailSet(T fromElement) {
    return this.tailSet(fromElement, true);
  }

  @Override
  public T first() {
    return _list.stream().reduce((first, second) -> first).orElseThrow(() -> new NoSuchElementException("Set is empty"));
  }

  @Override
  public T last() {
    return _list.stream().reduce((first, second) -> second).orElseThrow(() -> new NoSuchElementException("Set is empty"));
  }

  @Override
  public int size() {
    return _list.size();
  }

  private int indexOfList(T t) {
    return _list.indexOf(t);
  }
}
