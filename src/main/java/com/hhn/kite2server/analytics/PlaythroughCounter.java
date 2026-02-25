package com.hhn.kite2server.analytics;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "playthrough_counter", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class PlaythroughCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 64)
    private String slug;

    @Setter
    @Column(nullable = false)
    private long count = 0;

}
