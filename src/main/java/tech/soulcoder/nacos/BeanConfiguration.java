package tech.soulcoder.nacos;

import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.registry.NacosRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static tech.soulcoder.nacos.NacosSpringbootApplication.DATA_ID;
import static tech.soulcoder.nacos.NacosSpringbootApplication.GROUP_ID;

/**
 * @author yunfeng.lu
 * @create 2019/1/30.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    @Order(-1)
    public NacosDiscoveryProperties nacosDiscoveryProperties() {
        return new NacosDiscoveryProperties();
    }

    @Bean
    @Order(0)
    public CommandLineRunner thirdCommandLineRunner() {
        return new ThirdCommandLineRunner();
    }


    public static class ThirdCommandLineRunner implements CommandLineRunner {

        @Autowired
        private NacosConfigProperties nacosConfigProperties;

        @Override
        public void run(String... args) throws Exception {
            ConfigService configService = nacosConfigProperties.configServiceInstance();
            String res = configService.getConfig(DATA_ID, GROUP_ID, 300);
            System.out.println(res);
        }
    }

}
