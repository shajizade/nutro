package ir.haji.nutro.cache;

import java.util.Date;

/**
 * Created by Saeed Hajizade on 10/1/2019.
 */
public class CacheElementWrapper {
    Object element;
    Date insertedTime;

    public CacheElementWrapper(Object element) {
        this.element = element;
        insertedTime = new Date();
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Date getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }
}
