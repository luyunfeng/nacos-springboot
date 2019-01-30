package tech.soulcoder.nacos;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;


@SpringBootApplication
public class NacosSpringbootApplication {

    public static final String DATA_ID = "spring-mysql.yml";

    public static final String GROUP_ID = "DEFAULT_GROUP";

    public static void main(String[] args) {
        SpringApplication.run(NacosSpringbootApplication.class, args);
    }


    @Bean
    @Order
    public CommandLineRunner firstCommandLineRunner() {
        return new FirstCommandLineRunner();
    }

    @Bean
    @Order
    public CommandLineRunner secondCommandLineRunner() {
        return new SecondCommandLineRunner();
    }


    public static class FirstCommandLineRunner implements CommandLineRunner {

        @Autowired
        private NacosDiscoveryProperties nacosDiscoveryProperties;

        @Override
        public void run(String... args) throws Exception {
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            namingService.registerInstance(nacosDiscoveryProperties.getService(), "127.0.0.1", 8080);
        }
    }

    public static class SecondCommandLineRunner implements CommandLineRunner {

        @Autowired
        private NacosDiscoveryProperties nacosDiscoveryProperties;

        @Override
        public void run(String... args) throws Exception {
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instanceList = namingService.getAllInstances("test-service");
            System.out.println("found instance: " + instanceList.size());
            instanceList.forEach(System.out::println);
        }
    }


}


