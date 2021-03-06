package jdk.java.util.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

/**
 * Stream 클래스 테스트
 * 
 * required jdk8 or higher
 * 
 * - https://futurecreator.github.io/2018/08/26/java-8-streams/ - https://futurecreator.github.io/2018/08/26/java-8-streams-advanced/
 * 
 * @since 2018-07-16
 * @author fixalot
 */
public class StreamTest {
	private static final Logger logger = LoggerFactory.getLogger(StreamTest.class);

	/**
	 * Stream.forEach() 테스트<br>
	 * forEach로 한 바퀴 돌면 스트림이 닫혔거나 작업이 끝난걸로 간주되어 다시 스트림을 사용할 수 없나보다.
	 * 
	 * @throws IOException
	 * @author fixalot
	 */
	@Test
	public void testForEach() throws IOException {
		List<Integer> list = Arrays.asList(new Integer[] { 1, 3, 7 });
		Stream<Integer> stream = list.stream();
		stream.forEach(System.out::println);
		stream.close(); // 생략 가능

		Stream<Integer> stream2 = list.stream();
		List<Integer> basket = new ArrayList<Integer>();
		stream2.forEach((k) -> {
			logger.debug("ele: {}", k.toString());
			basket.add(k);
		});
		stream2.close();
		Assert.assertArrayEquals(new Integer[] { 1, 3, 7 }, basket.toArray(new Integer[list.size()]));
		Assert.assertEquals(3, basket.size());
	}

	@Test
	public void testFilter() {
		List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });

		// 짝수 찾기
		Predicate<Integer> predicate = new Predicate<Integer>() {
			@Override
			public boolean apply(@Nullable Integer input) {
				if (input % 2 == 0) {
					return true;
				}
				return false;
			}
		};

		// 필터링 결과 모두 출력
		Stream<Integer> stream = list.stream();
		List<Integer> result1 = stream.filter(predicate).collect(Collectors.toList());
		Assert.assertEquals(Arrays.asList(new Integer[] { 2, 4, 6, 8 }), result1);

		// 필터링 결과 중 첫 번째
		stream = list.stream();
		Integer result2 = stream.filter(predicate).findFirst().get();
		Assert.assertEquals(2, result2.intValue());

		// 필터링 결과 중 아무거나?? 그냥 첫 번째꺼 나오는거 같은데...
		stream = list.stream();
		Integer result3 = stream.filter(predicate).findAny().get();
		logger.debug("{}", result3);
	}

	@Test
	public void testAnyMatch() {
		List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		Stream<Integer> stream = list.stream();
		boolean result = stream.anyMatch(e -> {
			if (e > 10) {
				return true;
			}
			return false;
		});
		Assert.assertFalse(result); // list에는 10보다 큰게 없음.
	}
}
