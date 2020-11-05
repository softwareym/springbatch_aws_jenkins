package ym.batch.configuration;

import junit.framework.TestCase;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyEncyptConfigurationTest extends TestCase {

    @Test
    public void EncyptConfiguration_확인() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);

        //테스트 코드를 실행해서 아래 xoxo문자열로 batchpwd란 문자열을 암호화한 데이터를 콘솔에서 확인할 수 있다.
        //이렇게 확인한 키를 application.properties에 ENC(암호화된키)로 저장하여 사용할 수 있다.
        encryptor.setPassword("qfkFclseeogGMHMInM5T9naoiRhtjxGK6feqrIz4WqK4Nw68DkyuSLQlwVghnRLKg0HFCIGughqC7f3WFMHKgQ%3D%3D");                                      //암호화에 사용할 키 -> 중요
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");

        String plainText = "qfkFclseeogGMHMInM5T9naoiRhtjxGK6feqrIz4WqK4Nw68DkyuSLQlwVghnRLKg0HFCIGughqC7f3WFMHKgQ%3D%3D";                                      //암호화 할 문자열
        String encryptedText = encryptor.encrypt(plainText);                //암호화된 키
        String decryptedText = encryptor.decrypt(encryptedText);            //복호화된 키=plainText

        System.out.println(encryptedText);
        System.out.println(decryptedText);

   //   assertTrue(plainText.equals(decryptedText));
    }
}