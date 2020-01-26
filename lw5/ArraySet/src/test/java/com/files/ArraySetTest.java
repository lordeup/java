package com.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArraySetTest {
  private List<Integer> numbers;

  @BeforeEach
  void init() {
    numbers = Arrays.asList(1, 2, 3, 4);
  }

  @Test
  void size() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertEquals(4, arraySet.size(), "ArraySet size");
  }

  @Test
  void first() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertEquals(1, arraySet.first(), "ArraySet first");
  }

  @Test
  void firstEmptySet() {
    List<Integer> numbers = Collections.emptyList();
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertThrows(NoSuchElementException.class, arraySet::first, "ArraySet first empty set");
  }

  @Test
  void last() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertEquals(4, arraySet.last(), "ArraySet last");
  }

  @Test
  void lastEmptySet() {
    List<Integer> numbers = Collections.emptyList();
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertThrows(NoSuchElementException.class, arraySet::last, "ArraySet last empty set");
  }

  @Test
  void pollFirst() {
    List<String> strings = Collections.emptyList();
    ArraySet<String> arraySet = new ArraySet<>(strings);

    assertThrows(UnsupportedOperationException.class, arraySet::pollFirst, "ArraySet pollFirst immutable set");
  }

  @Test
  void pollLast() {
    List<String> strings = Collections.emptyList();
    ArraySet<String> arraySet = new ArraySet<>(strings);

    assertThrows(UnsupportedOperationException.class, arraySet::pollLast, "ArraySet pollLast immutable set");
  }

  @Test
  void comparator() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertNotNull(arraySet.comparator(), "ArraySet comparator");
  }

  @Test
  void iterator() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    Iterator<Integer> iterator = arraySet.iterator();

    assertAll("iterator",
            () -> assertEquals(1, iterator.next()),
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void lower() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("lower",
            () -> assertEquals(1, arraySet.lower(2), "ArraySet lower"),
            () -> assertNull(arraySet.lower(1), "ArraySet lower first element"),
            () -> assertNull(arraySet.lower(10), "ArraySet lower not exist")
    );
  }

  @Test
  void floor() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("floor",
            () -> assertEquals(1, arraySet.floor(1), "ArraySet floor"),
            () -> assertNull(arraySet.floor(10), "ArraySet floor not exist")
    );
  }

  @Test
  void ceiling() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("ceiling",
            () -> assertEquals(3, arraySet.ceiling(2), "ArraySet ceiling"),
            () -> assertEquals(4, arraySet.ceiling(4), "ArraySet ceiling last element"),
            () -> assertNull(arraySet.ceiling(10), "ArraySet ceiling not exist")
    );
  }

  @Test
  void higher() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("higher",
            () -> assertEquals(2, arraySet.higher(1), "ArraySet higher"),
            () -> assertNull(arraySet.higher(4), "ArraySet higher last element"),
            () -> assertNull(arraySet.higher(10), "ArraySet higher not exist")
    );
  }

  @Test
  void descendingSet() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> arrayDescendingSet = arraySet.descendingSet();
    Iterator<Integer> iterator = arrayDescendingSet.iterator();

    assertAll("descendingSet",
            () -> assertEquals(4, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(1, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void descendingIterator() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    Iterator<Integer> iterator = arraySet.descendingIterator();

    assertAll("descendingIterator",
            () -> assertEquals(4, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(1, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void headSetIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.headSet(2, true);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("headSetIncludeElement",
            () -> assertEquals(1, iterator.next()),
            () -> assertEquals(2, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void headSetNotIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.headSet(2, false);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("headSetNotIncludeElement",
            () -> assertEquals(1, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void headSetThrows() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("headSetThrows",
            () -> assertThrows(NullPointerException.class, () -> arraySet.headSet(10, false), "ArraySet headSet not exist"),
            () -> assertThrows(NullPointerException.class, () -> arraySet.headSet(1, true), "ArraySet headSet first element and inclusive = true")
    );
  }

  @Test
  void headSettSorted() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    SortedSet<Integer> sortedSet = arraySet.headSet(2);
    Iterator<Integer> iterator = sortedSet.iterator();

    assertAll("headSettSorted",
            () -> assertEquals(1, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void tailSetIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.tailSet(3, true);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("tailSetIncludeElement",
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void tailSetNotIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.tailSet(3, false);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("tailSetNotIncludeElement",
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void tailSetThrows() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);

    assertAll("headSetThrows",
            () -> assertThrows(NullPointerException.class, () -> arraySet.tailSet(10, false), "ArraySet tailSet not exist"),
            () -> assertThrows(NullPointerException.class, () -> arraySet.tailSet(4, false), "ArraySet tailSet last element and inclusive = true")
    );
  }

  @Test
  void tailSetSorted() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    SortedSet<Integer> sortedSet = arraySet.tailSet(3);
    Iterator<Integer> iterator = sortedSet.iterator();

    assertAll("tailSetSorted",
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void subSetBothIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.subSet(2, true, 4, true);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("subSetBothIncludeElement",
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void subSetBothNotIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.subSet(2, false, 4, false);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("subSetBothNotIncludeElement",
            () -> assertEquals(3, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void subSetFirstElementIncludeSecondNotIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.subSet(2, true, 4, false);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("subSetFirstElementIncludeSecondNotIncludeElement",
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void subSetFirstElementNotIncludeSecondIncludeElement() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    NavigableSet<Integer> navigableSet = arraySet.subSet(2, false, 4, true);
    Iterator<Integer> iterator = navigableSet.iterator();

    assertAll("subSetFirstElementNotIncludeSecondIncludeElement",
            () -> assertEquals(3, iterator.next()),
            () -> assertEquals(4, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

  @Test
  void subSetSorted() {
    ArraySet<Integer> arraySet = new ArraySet<>(numbers);
    SortedSet<Integer> sortedSet = arraySet.subSet(2, 4);
    Iterator<Integer> iterator = sortedSet.iterator();

    assertAll("subSetSorted",
            () -> assertEquals(2, iterator.next()),
            () -> assertEquals(3, iterator.next()),
            () -> assertFalse(iterator.hasNext())
    );
  }

}