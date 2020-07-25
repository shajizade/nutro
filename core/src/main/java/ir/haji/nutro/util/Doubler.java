package ir.haji.nutro.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by Saeed on 7/21/2020.
 */
public class Doubler extends JsonSerializable.Base {
    NumberFormat instance = NumberFormat.getInstance();

    BigDecimal value;

    public Doubler() {
        this(0d);
    }

    public Doubler(Double dub) {
        instance.setMaximumFractionDigits(2);
        this.value = new BigDecimal(dub == null ? 0 : dub);
    }

    public Doubler(Doubler duber) {
        this(duber.toDouble());
    }

    public Doubler(BigDecimal bgd) {
        this(bgd.doubleValue());
    }

    public Doubler add(Doubler dber) {
        if (dber == null)
            return this;
        return add(dber.toDouble());
    }

    public Doubler add(BigDecimal bgd) {
        if (bgd == null)
            return this;
        return add(bgd.doubleValue());
    }

    public Doubler add(Long lng) {
        if (lng == null)
            return this;
        return add(lng.doubleValue());
    }

    public Doubler add(Float flt) {
        if (flt == null)
            return this;

        return add(flt.doubleValue());
    }

    public Doubler add(Integer ing) {
        if (ing == null)
            return this;

        return add(ing.doubleValue());
    }

    public Doubler add(Double dub) {
        if (dub == null)
            return this;

        value = value.add(new BigDecimal(dub));
        return this;
    }


    public Doubler multiply(Doubler dber) {
        if (dber == null)
            return multiply(0L);
        return multiply(dber.toDouble());
    }

    public Doubler multiply(BigDecimal bgd) {
        if (bgd == null)
            return multiply(0L);

        return multiply(bgd.doubleValue());
    }

    public Doubler multiply(Integer ing) {
        if (ing == null)
            return multiply(0L);

        return multiply(ing.doubleValue());
    }

    public Doubler multiply(Long lng) {
        if (lng == null)
            return multiply(0L);

        return multiply(lng.doubleValue());
    }

    public Doubler multiply(Float flt) {
        if (flt == null)
            return multiply(0L);

        return multiply(flt.doubleValue());
    }

    public Doubler multiply(Double dub) {
        if (dub == null)
            return multiply(0L);

        value = value.multiply(new BigDecimal(dub));
        return this;
    }


    public Doubler divide(Doubler dber) {
        return divide(dber.toDouble());
    }

    public Doubler divide(BigDecimal bgd) {
        return divide(bgd.doubleValue());
    }

    public Doubler divide(Integer ing) {
        return divide(ing.doubleValue());
    }

    public Doubler divide(Long lng) {
        return divide(lng.doubleValue());
    }

    public Doubler divide(Float flt) {
        return divide(flt.doubleValue());
    }

    public Doubler divide(Double dub) {
        value = value.divide(new BigDecimal(dub), 5, BigDecimal.ROUND_HALF_UP);
        return this;
    }

/*
    public Doubler beDivided(Double dub) {
        value=new Doubler(dub).divide(value).toBigDecimal();
        return this;
    }
*/

    public Double toDouble() {
        return value.doubleValue();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(value.doubleValue());
    }

    public Integer toInteger() {
        return value.intValue();
    }

    public Long toLong() {
        return value.longValue();
    }

    public Doubler fractions(int fractionDigits) {
        instance.setMaximumFractionDigits(fractionDigits);
        return this;
    }

    public String toString() {
        return instance.format(value);
    }

    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(toString());
    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
