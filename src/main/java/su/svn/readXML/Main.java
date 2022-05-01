package su.svn.readXML;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class Main implements ApplicationContextAware, CommandLineRunner {

	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String[] beans = context.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String bean : beans) {
			log.info(bean);
			((ConfigurableApplicationContext) context).close();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.debug("setApplicationContext({})", applicationContext);
		this.context = applicationContext;
	}
}
