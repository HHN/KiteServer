package com.hhn.kite2server.analytics;

import jakarta.persistence.*;

@Entity
@Table(name = "scene_hit", uniqueConstraints = @UniqueConstraint(columnNames = "scene"))
public class SceneHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SceneType scene;

    @Column(nullable = false)
    private long count = 0;

    // Getter/Setter
    public Long getId() { return id; }
    public SceneType getScene() { return scene; }
    public void setScene(SceneType scene) { this.scene = scene; }
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}
