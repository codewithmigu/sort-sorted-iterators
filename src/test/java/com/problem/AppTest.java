package com.problem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    @ParameterizedTest
    @MethodSource("provideData")
    void getSortedIterator(List<List<Integer>> input) {
        final var result = App.getSortedIterator(toIteratorStream(input));

        assertNotNull(result);

        final var resultList = from(result);

        assertTrue(isOrdered(resultList));
        assertTrue(containsAll(input, resultList));
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(
                        // problem example
                        List.of(
                                List.of(6, 8, 19, 21, 32, 66, 67, 77, 89),
                                List.of(1, 3, 5, 24, 33, 45, 57, 59, 89),
                                List.of(2, 4, 9, 18, 22, 44, 46, 89, 89)
                        )),
                Arguments.of(
                        // unequal number of elements
                        List.of(
                                List.of(6, 8, 19, 21, 32, 66),
                                List.of(1, 3, 5, 24),
                                List.of(2, 4, 9, 18, 22, 44, 46, 89, 89)
                        )),
                Arguments.of(
                        // a lot of duplicates between lists
                        List.of(
                                List.of(6, 8, 19, 21, 24, 33, 45, 77, 89),
                                List.of(1, 2, 3, 5, 24, 33, 45, 57, 59, 89),
                                List.of(2, 4, 5, 24, 33, 44, 46, 89, 89)
                        )),
                Arguments.of(
                        // a lot of duplicates in lists
                        List.of(
                                List.of(6, 6, 6, 8, 19, 19, 19, 21, 32, 66, 67, 77, 89),
                                List.of(1, 3, 5, 24, 33, 33, 33, 45, 57, 59, 89, 89, 89),
                                List.of(2, 2, 2, 4, 9, 9, 9, 18, 22, 44, 46, 89, 89)
                        )
                ),
                Arguments.of(
                        // single list
                        List.of(
                                List.of(6, 6, 6, 8, 19, 19, 19, 21, 32, 66, 67, 77, 89)
                        )
                ),
                Arguments.of(
                        // one empty list
                        List.of(
                                Collections.emptyList(),
                                List.of(1, 2, 3, 5, 24, 33, 45, 57, 59, 89),
                                List.of(2, 4, 5, 24, 33, 44, 46, 89, 89)
                        ))
        );
    }

    private <T> boolean containsAll(List<List<T>> input, List<T> result) {
        final var inputList = input.stream()
                .flatMap(Collection::stream)
                .toList();

        return result.containsAll(inputList);
    }

    private static <T> Stream<Iterator<T>> toIteratorStream(List<List<T>> list) {
        return list.stream()
                .map(List::iterator);
    }

    private static <T> boolean isOrdered(List<T> list) {
        var actual = list.stream()
                .sorted()
                .toList();

        return list.equals(actual);
    }

    private static <T> List<T> from(Iterator<T> iterator) {
        final Iterable<T> iterable = () -> iterator;

        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }
}
