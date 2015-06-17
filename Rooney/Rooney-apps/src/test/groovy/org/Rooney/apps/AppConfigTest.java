package org.Rooney.apps;

import static org.junit.Assert.*;

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
		ExecutorService es = Executors.newCachedThreadPool();
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
		assertTrue(es.isShutdown());
	}
}
