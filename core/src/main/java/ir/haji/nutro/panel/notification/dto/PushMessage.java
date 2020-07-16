package ir.haji.nutro.panel.notification.dto;

/**
 * Created by jalil on 1/8/2020.
 */

public class PushMessage {
    private String title;
    private String body;
    private String url;
    private String image;
    private String icon;

    public String getImage() {
        return image;
    }

    public String getIcon() {
        return icon;
    }

    public PushMessage(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return "PushMessage [title=" + this.title + ", body=" + this.body + "]";
    }

    public PushMessage title(String title) {
        this.title = title;
        return this;
    }

    public PushMessage body(String body) {
        this.body = body;
        return this;
    }

    public PushMessage url(String url) {
        this.url = url;
        return this;
    }

    public PushMessage image(String image) {
        this.image = image;
        return this;
    }

    public PushMessage icon(String icon) {
        this.icon = icon;
        return this;
    }
}