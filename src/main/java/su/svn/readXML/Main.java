/*
 * This file was last modified at 2022.05.01 18:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Main.java
 * $Id$
 */

package su.svn.readXML;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import su.svn.readXML.utils.IO;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class Main implements ApplicationContextAware, CommandLineRunner, Closeable {

	public static final String MAIN_PID = "./main.pid";
	public static final String SPACES = "([ \t\\r\\n])";
	private ApplicationContext context;

	public static void main(String[] args) {
		log.info("STARTING THE APPLICATION");
		SpringApplication application = new SpringApplication(Main.class);
		application.addListeners(new ApplicationPidFileWriter(MAIN_PID));
		application.run();
		log.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");
		String[] beans = context.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String bean : beans) {
			log.info(bean);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.debug("setApplicationContext({})", applicationContext);
		this.context = applicationContext;
	}

	@PreDestroy
	public void onShutDown() throws IOException {
		log.info("closing application context..let's do the final resource cleanup");
		close();
		kill();
	}

	@Override
	public void close() throws IOException {
		((ConfigurableApplicationContext) context).close();
	}

	private void kill() throws IOException {
		final File pidFile = new File(MAIN_PID);
		final InputStream pidStream = new FileInputStream(pidFile);
		final String pidString = IO.Util
				.readFromInputStream(pidStream)
				.replaceAll(SPACES, "");
		int pid = Integer.parseInt(pidString);
		log.info("killing pid {}", pid);
		Runtime.getRuntime().exec("kill -SIGINT " + pid);
	}
}