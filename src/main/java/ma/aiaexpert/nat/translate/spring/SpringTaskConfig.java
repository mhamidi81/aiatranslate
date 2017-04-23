package ma.aiaexpert.nat.translate.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "ma.aiaexpert.nat.translate.task" })
public class SpringTaskConfig {

}
