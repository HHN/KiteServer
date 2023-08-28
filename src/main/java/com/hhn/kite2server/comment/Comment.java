package com.hhn.kite2server.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhn.kite2server.appuser.AppUser;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Comment {

    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")

    private Long id;
    private String comment;
    private Long visualNovelId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private AppUser author;
}
