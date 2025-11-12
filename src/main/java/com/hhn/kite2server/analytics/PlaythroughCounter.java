package com.hhn.kite2server.analytics;

import jakarta.persistence.*;

@Entity
@Table(name = "playthrough_counter", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class PlaythroughCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String slug;

    @Column(nullable = false)
    private long count = 0;

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}
