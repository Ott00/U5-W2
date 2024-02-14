package com.otmankarim.U5W2D2.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "blogs")
@NoArgsConstructor
public class Blog {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String category;
    private String title;
    private String cover;
    private String content;
    private int timeOfLecture;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Blog(String category, String title, String cover, String content, int timeOfLecture) {
        this.category = category;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.timeOfLecture = timeOfLecture;
    }
}
