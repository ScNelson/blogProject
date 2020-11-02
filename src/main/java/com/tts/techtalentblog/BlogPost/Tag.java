package com.tts.techtalentblog.BlogPost;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    private String phrase;

    @ManyToMany(mappedBy = "tags")
    private List<BlogPost> posts;


    public Tag() {
    }


    public Tag(Long id, String phrase, List<BlogPost> posts) {
        this.id = id;
        this.phrase = phrase;
        this.posts = posts;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public List<BlogPost> getPosts() {
        return this.posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", phrase='" + getPhrase() + "'" +
            ", posts='" + getPosts() + "'" +
            "}";
    }    
}
