package tech.soulcoder.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.beat.BeatReactor;
import com.alibaba.nacos.client.naming.net.NamingProxy;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static tech.soulcoder.nacos.NacosSpringbootApplication.DATA_ID;
import static tech.soulcoder.nacos.NacosSpringbootApplication.GROUP_ID;

@SpringBootApplication
@EnableAutoConfiguration
@NacosPropertySource(dataId = DATA_ID)
@NacosPropertySource(dataId = "spring-mysql.yml")
public class NacosSpringbootApplication {

    public static final String DATA_ID = "nacos-config-example.properties";

    public static final String GROUP_ID = "DEFAULT_GROUP";


    public static void main(String[] args) {
        SpringApplication.run(NacosSpringbootApplication.class, args);
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public CommandLineRunner firstCommandLineRunner() {
        return new FirstCommandLineRunner();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public CommandLineRunner secondCommandLineRunner() {
        return new SecondCommandLineRunner();
    }

    @Bean
    public CommandLineRunner thirdCommandLineRunner() {
        return new ThirdCommandLineRunner();
    }

    public static class FirstCommandLineRunner implements CommandLineRunner {

        @NacosInjected
        private NamingService namingService;

        @Override
        public void run(String... args) throws Exception {
            System.out.println("start to register");
            namingService.registerInstance("test-service", "127.0.0.1", 8080);

        }
    }

    public static class SecondCommandLineRunner implements CommandLineRunner {

        @NacosInjected
        private NamingService namingService;

        @Override
        public void run(String... args) throws Exception {
            List<Instance> instanceList = namingService.getAllInstances("test-service");
            System.out.println("found instance: " + instanceList.size());
            instanceList.forEach(System.out::println);
        }
    }

    public static class ThirdCommandLineRunner implements CommandLineRunner {

        @NacosInjected
        private ConfigService configService;

        @Override
        public void run(String... args) throws Exception {
            String res = configService.getConfig(DATA_ID, GROUP_ID, 300);
            String res2 = configService.getConfig("spring-mysql.yml", GROUP_ID, 300);
            System.out.println(res);
            System.out.println(res2);
        }
    }
}


