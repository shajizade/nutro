package ir.haji.nutro.panel.research.entity;

import ir.haji.nutro.panel.research.constant.Gender;
import ir.haji.nutro.util.Doubler;
import ir.haji.nutro.util.HashUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "`case`")
public class Case implements Serializable {
    public static final long FREQUENCY_24_ID = 2l;
    public static String STATUS_CREATED = "CREATED";
    public static String STATUS_ACCEPTED = "ACCEPTED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long researchId;
    private String name;
    private String sickness;
    private String code;
    private Integer age;
    private Integer activity;
    private Float height;
    private Float weight;
    private Float waist;
    private Float hip;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;
    private String status;
    public String getHashCode() {
        return HashUtil.getCaseCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResearchId() {
        return researchId;
    }

    public void setResearchId(Long researchId) {
        this.researchId = researchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getWaist() {
        return waist;
    }

    public void setWaist(Float waist) {
        this.waist = waist;
    }

    public Float getHip() {
        return hip;
    }

    public void setHip(Float hip) {
        this.hip = hip;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Transient
    public Double getBmi() {
        if (height != null && height > 0 && waist != null && weight > 0)
            return new Doubler(weight).divide(
                    new Doubler(height).multiply(height).divide(10000)
            ).toDouble();
        return null;
    }

    @Transient
    public Boolean getFoodFree() {
        return id.equals(FREQUENCY_24_ID);
    }

    @Transient
    public Double getWaistToHip() {
        if (waist != null && waist > 0 && hip != null && hip > 0)
            return new Doubler(waist).divide(hip).toDouble();
        return null;
    }
}
