package cn.steateam.lets_team_server.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss的配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("aliyun")
@Data
@Component
public class AliyunConfig {
    private String uid;
    private Oss oss = new Oss();

    @Data
    public static class Oss {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private Object object = new Object();
        private Signature signature = new Signature();
        private Bucket bucket = new Bucket();

        @Data
        public static class Object {
            private int maxSize;
        }

        @Data
        public static class Signature {
            private int expireTime;
        }

        @Data
        public static class Bucket {
            private PublicBucket publicBucket = new PublicBucket();
            private PrivateBucket privateBucket = new PrivateBucket();

            @Data
            public static class PublicBucket {
                private String name;
            }

            @Data
            public static class PrivateBucket {
                private String name;
            }
        }
    }

    @Bean
    public OSS oss() {
        return new OSSClientBuilder().build(oss.endpoint, oss.accessKeyId, oss.accessKeySecret);
    }
}