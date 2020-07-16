package ir.haji.nutro.panel.notification.entity;

import ir.haji.nutro.panel.notification.dto.Subscription;

import javax.persistence.*;
import java.io.*;

/**
 * Created by User on 7/17/2018.
 */
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String endpoint;
    private byte[] serializedData;
    private Long failNumber = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public byte[] getSerializedData() {
        return serializedData;
    }

    public void setSerializedData(byte[] serializedData) {
        this.serializedData = serializedData;
    }

    public Long getFailNumber() {
        return failNumber == null ? 0L : failNumber;
    }

    public void setFailNumber(Long failNumber) {
        this.failNumber = failNumber;
    }

    @Transient
    public Subscription getSubscription() throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        ObjectInputStream inputStream = new ObjectInputStream(bais);
        Subscription subscription = (Subscription) inputStream.readObject();
        inputStream.close();
        bais.close();
        return subscription;
    }

    @Transient
    public void setSubscription(Subscription subscription) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(baos);
        outputStream.writeObject(subscription);
        outputStream.flush();
        baos.flush();
        serializedData = baos.toByteArray();
    }

}
