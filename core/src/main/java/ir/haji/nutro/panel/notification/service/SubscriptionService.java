package ir.haji.nutro.panel.notification.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.haji.nutro.panel.notification.dto.PushMessage;
import ir.haji.nutro.panel.notification.dto.Subscription;
import ir.haji.nutro.panel.notification.entity.SubscriptionEntity;
import ir.haji.nutro.panel.notification.repo.SubscriptionRepo;
import ir.haji.nutro.panel.um.service.UserService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class SubscriptionService {
    @Autowired
    private CryptoService cryptoService;
    @Autowired
    SubscriptionRepo subscriptionRepo;

    private Algorithm jwtAlgorithm;
    @Autowired
    private ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(CryptoService.class);
    OkHttpClient client = new OkHttpClient.Builder().build();
    @Autowired
    private UserService userService;
    @Value("${panel.notification.failLimit:3}")
    private Long sendNotificationFailLimit;

    @PostConstruct
    public void postConstruct() {
        this.jwtAlgorithm = Algorithm.ECDSA256(cryptoService.getPublicKey(),
                cryptoService.getPrivateKey());
    }

    public String getPublicKeyBase64() {
        return new String(Base64.encode(cryptoService.getPublicKeyUncompressed()));
    }

    public void subscribe(Subscription subscription) {
        List<SubscriptionEntity> preSubs = subscriptionRepo.findByEndpoint(subscription.getEndpoint());
        if (preSubs == null || preSubs.size() == 0) {
            try {
                SubscriptionEntity entity = new SubscriptionEntity();
                entity.setEndpoint(subscription.getEndpoint());
                entity.setFailNumber(0L);
                entity.setId(null);
                entity.setUserId(userService.getCurrentUser().getId());
                entity.setSubscription(subscription);
                subscriptionRepo.save(entity);
            } catch (Exception ignored) {
            }
        }
    }


    public SubscriptionEntity getSubscription(String endpoint) {
        List<SubscriptionEntity> subs = subscriptionRepo.findByEndpoint(endpoint);
        if (subs.size() > 0)
            return subs.get(0);
        return null;
    }

    public void unsubscribe(String endpoint) {
        subscriptionRepo.deleteByEndpoint(endpoint);
    }

    public void sendPushMessageToAllSubscribers(PushMessage message)
            throws JsonProcessingException {
        for (SubscriptionEntity subscriptionEntity : getAllSubscriptions()) {
            sendPushMessageToSubscription(message, subscriptionEntity);
        }
    }

    public Boolean sendPushMessageToUser(PushMessage message, Long userId) {
        Boolean sent = false;
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepo.findByUserId(userId);
        for (SubscriptionEntity subscriptionEntity : subscriptionEntities) {
            if (sendPushMessageToSubscription(message, subscriptionEntity))
                sent = true;
        }
        return sent;
    }

    public Boolean sendPushMessageToSubscription(PushMessage message, SubscriptionEntity subscriptionEntity) {
        Integer result = -1;
        try {
            Subscription subscription = subscriptionEntity.getSubscription();
            byte[] body = cryptoService.encrypt(objectMapper.writeValueAsString(message), subscription.getKeys().getP256dh(), subscription.getKeys().getAuth(), 0);
            result = sendPushMessage(subscriptionEntity, body);
        } catch (Exception e) {
            logger.error("Encrypting message", e);
        }
        switch (result) {
            case 200:
            case 201:
            case 202:
                return true;
            case 404:
            case 410:
                goneSubscription(subscriptionEntity);
                break;
            case 429:
                logger.error("Too many requests to notification");
                break;
            case 400:
                logger.error("Invalid request to notification");
                break;
            case 413:
                logger.error("Payload size too large (notification)");
                break;
            default:
                logger.error("Unhandled status code: {} ", result);
        }
        if (subscriptionEntity.getFailNumber() > sendNotificationFailLimit)
            subscriptionRepo.delete(subscriptionEntity);
        return false;
    }

    private List<SubscriptionEntity> getAllSubscriptions() {
        return subscriptionRepo.findAll();
    }


    /**
     * @return true if the subscription is no longer valid and can be removed, false if
     * everything is okay
     */
    private Integer sendPushMessage(SubscriptionEntity entity, byte[] body) {
        String origin = null;
        try {
            URL url = new URL(entity.getEndpoint());
            origin = url.getProtocol() + "://" + url.getHost();
        } catch (MalformedURLException e) {
            logger.error("Url prob", e);
            goneSubscription(entity);
            return -2;
        }

        Date expires = new Date(new Date().getTime() + 12 * 60 * 60 * 1000);

        String token = JWT.create().withAudience(origin).withExpiresAt(expires)
                .withSubject("mailto:example@example.com").sign(this.jwtAlgorithm);

        URI endpointURI = URI.create(entity.getEndpoint());

        Request.Builder request = null;
        try {
            request = new Request.Builder().url(endpointURI.toURL()).header("Authorization",
                    "vapid t=" + token + ", k=" + cryptoService.getPublicKeyBase64()).header("TTL", "180");
        } catch (MalformedURLException e) {
            logger.error("Url2 prob", e);
            goneSubscription(entity);
            return -2;
        }

        request.post(okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"), body))
                .header("Content-Type", "application/octet-stream")
                .header("Content-Encoding", "aes128gcm");

        try {
            Response response = client.newCall(request.build()).execute();
            response.body().close();
            return response.code();
        } catch (IOException e) {
            logger.error("Error! ", e);
            return -2;
        }
    }

    private void goneSubscription(SubscriptionEntity entity) {
        entity.setFailNumber(entity.getFailNumber() + 1);
        subscriptionRepo.save(entity);
    }

}


