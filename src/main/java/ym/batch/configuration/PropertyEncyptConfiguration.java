package ym.batch.configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 설정파일 암호화 - 암호화에 사용할 빈 생성
 * cf) https://goateedev.tistory.com/131

  setPassword에 해당하는 값을 그대로 코드에 담기에는 이부분도 보안상 문제가 되기때문에
  System property값을 입력하여 가져오도록 처리함. *
  application을 jar파일로 실행할 때 -Djasypt.encryptor.password=batchpwd 입력 vm options을 줘서 실행해준다.
  그러면 코드에 passsword 그대로 노출되는게 아니라 코드에서 받아서 password값을 받아서 세팅할 수 있다.

 */
@Configuration
@EnableEncryptableProperties
public class PropertyEncyptConfiguration {

    @Bean("encryptorBean")
    public PooledPBEStringEncryptor stringEncryptor() {

        //vm option에서 -Djasypt.encrypor.password=평문자열(암호화x)로 설정하여 실행
        String mypassword = System.getProperty("jasypt.encryptor.password");
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        encryptor.setPassword(mypassword);
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");
        return encryptor;
    }
}
