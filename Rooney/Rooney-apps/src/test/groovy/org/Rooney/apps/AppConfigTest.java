package org.Rooney.apps;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class AppConfigTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Instant now=Instant.now();
		Duration duration = Duration.ofDays(1);
		Instant instant=now.minus(duration);
		Long s=instant.until(now,ChronoUnit.HOURS);
		System.out.println(s);
		System.out.println(now);
		System.out.println(now.getEpochSecond());
		assertNotNull(s);
		/*ExecutorService es = Executors.newCachedThreadPool();
		try {
			Runnable r = () -> {
				for (int i = 0; i < 10; i++) {
					try {
						TimeUnit.SECONDS.sleep(1);
						System.out.println("sss" + i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			es.submit(r);
			es.shutdown();
			es.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			es.shutdownNow();
		}
		assertTrue(es.isShutdown());*/
	}
}
