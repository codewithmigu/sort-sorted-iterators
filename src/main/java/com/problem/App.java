package com.problem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        final var input = List.of(
                List.of(6, 8, 19, 21, 32, 66, 67, 77, 89),
                List.of(1, 3, 5, 24, 33, 45, 57, 59, 89),
                List.of(2, 4, 9, 18, 22, 44, 46, 89, 89)
        );

        if (!isValid(input)) {
            System.out.println("Input does not meet the requirements (lists have to be sorted)");
        } else {
            final var result = getSortedIterator(toIteratorStream(input));

            System.out.println(toString(input, result));
        }
    }

    public static <T> Iterator<T> getSortedIterator(Stream<Iterator<T>> sortedIterators) {
        if (sortedIterators == null) {
            return Collections.emptyIterator();
        }

        final var treeMap = sortedIterators
                .filter(Iterator::hasNext)
                .collect(Collectors.toMap(
                        Iterator::next,
                        List::of,
                        App::concat,
                        TreeMap::new
                ));

        final var sortedList = new LinkedList<T>();

        while (!treeMap.isEmpty()) {
            final var entry = treeMap.pollFirstEntry();

            IntStream.range(0, entry.getValue().size())
                    .forEach(i -> sortedList.add(entry.getKey()));

            entry.getValue()
                    .stream()
                    .filter(Iterator::hasNext)
                    .forEach(it -> treeMap.compute(it.next(), (key, iterators) ->
                            iterators == null ? List.of(it) : concat(iterators, it)));
        }

        return sortedList.iterator();
    }

    private static <T> boolean isValid(List<List<T>> data) {
        return data.stream()
                .filter(list -> list == null || !isOrdered(list))
                .findAny()
                .isEmpty();
    }

    private static <T> boolean isOrdered(List<T> list) {
        return list.stream()
                .sorted()
                .toList()
                .equals(list);
    }

    private static <T> Stream<Iterator<T>> toIteratorStream(List<List<T>> list) {
        return list.stream()
                .map(List::iterator);
    }

    private static <T> List<T> concat(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream()).toList();
    }

    private static <T> List<T> concat(List<T> list, T value) {
        return Stream.of(list, List.of(value))
                .flatMap(Collection::stream)
                .toList();
    }

    private static <T> String toString(List<List<T>> input, Iterator<T> result) {
        final var sb = new StringBuilder();

        sb.append("Input data is:\n");
        input.forEach(list -> sb.append(toStringBuilder(list.iterator())));
        sb.append("\nResult is: ");
        sb.append(toStringBuilder(result));

        return sb.toString();
    }

    private static <T> StringBuilder toStringBuilder(Iterator<T> iterator) {
        final var sb = new StringBuilder();

        sb.append("[ ");
        iterator.forEachRemaining(value -> sb.append(value).append(" "));
        sb.append("]\n");

        return sb;
    }
}
