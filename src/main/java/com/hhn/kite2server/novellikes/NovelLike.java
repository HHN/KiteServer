package com.hhn.kite2server.novellikes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.comment.Comment;
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
public class NovelLike {
    @SequenceGenerator(name = "novel_like_sequence", sequenceName = "novel_like_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "novel_like_sequence")
    private Long id;

    private Long visualNovelId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private AppUser user;
}
