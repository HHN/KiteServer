package com.hhn.kite2server.analytics;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "scene_hit", uniqueConstraints = @UniqueConstraint(columnNames = "scene"))
public class SceneHit {

    // Getter/Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SceneType scene;

    @Setter
    @Column(nullable = false)
    private long count = 0;

}
