package ir.haji.nutro.panel.research.entity;

import ir.haji.nutro.panel.research.constant.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "`case`")
public class Case implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long researchId;
    private String name;
    private String code;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

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
}
