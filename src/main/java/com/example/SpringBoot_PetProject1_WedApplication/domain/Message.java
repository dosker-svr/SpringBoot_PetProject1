package com.example.SpringBoot_PetProject1_WedApplication.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity //аннотация говорит о том, что это 'сущность', которую необходимо сохранять в БД ???
public class Message {
    @Id //id в таблице postgre
    @GeneratedValue(strategy= GenerationType.AUTO) //как будут генерироваться идентификаторы
    private Long id;

    private String text;
    private String tag;
    private String filename;

// указываем как пользователь должен сохраняться в DB
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    /* указываем что, одному пользователю соответсвует множество 'Message's'
    fetch - указываем что при получении сообщения, хотим получать инфу об авторе*/
    /* @JoinColumn - тут указываем название колонки, как она должна быть записана в db
    чтобы в db это поле значилось как user_id, а не как author_id (по-умолчанию) */

// обязательно нужно делать пустой конструктор ???
    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<lol:none>";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
