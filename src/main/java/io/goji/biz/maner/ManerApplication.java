package io.goji.biz.maner;

import com.gitee.sunchenbin.mybatis.actable.manager.handler.StartUpHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*", "io.goji.biz.maner"})
@ComponentScan(basePackages = {"com.gitee.sunchenbin.mybatis.actable.manager.*"})
@SpringBootApplication
public class ManerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(ManerApplication.class, args);
        StartUpHandler bean = app.getBean(StartUpHandler.class);
        bean.startHandler();

    }

}
