package com.example.SpringBoot_PetProject1_WedApplication.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //аннотация говорит о том, что это 'сущность', которую необходимо сохранять в БД
public class Message {
    @Id //id в таблице postgre
    @GeneratedValue(strategy= GenerationType.AUTO) //как будут генерироваться идентификаторы
    private Integer id;

    private String text;
    private String tag;

    public Message() {
    }

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
