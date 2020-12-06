package ym.batch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableWebMvc
@MapperScan(basePackages = "ym.batch.job.*")
@EnableBatchProcessing  //배치기능 활성화
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
      /*
      //boot.pid를 생성 하는 방법(kill -9 `cat boot.pid`로 셧다운 가능)
          SpringApplication application = new SpringApplication(DemoApplication.class);
          application.addListeners(new ApplicationPidFileWriter());
          application.run(args);
      */

      ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
      System.exit(SpringApplication.exit(context)); //완료시 자동 shutdown
    }

}
