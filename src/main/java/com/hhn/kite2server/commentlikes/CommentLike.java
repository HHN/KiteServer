package com.hhn.kite2server.commentlikes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hhn.kite2server.comment.Comment;
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
public class CommentLike {
    @SequenceGenerator(name = "comment_like_sequence", sequenceName = "comment_like_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_like_sequence")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Comment comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private AppUser user;
}
