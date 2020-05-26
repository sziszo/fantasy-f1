package com.aklysoft.fantasyf1.service.core.utils;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtils {

  public static <T> Stream<T> repeat(int start, int limit, int total, Function<Integer, T> func) {
    return IntStream.range(start, total)
            .filter(i -> i % limit == 0)
            .boxed()
            .map(func);
  }
}
