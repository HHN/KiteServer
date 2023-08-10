package com.hhn.kite2server.novels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class VisualNovel {

    @SequenceGenerator(name = "novel_sequence", sequenceName = "novel_sequence", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "novel_sequence")

    private Long id;
    private String title;
    private String description;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private AppUser creator;

    @JsonProperty("creator")
    public Long getCreatorId() {
        return (creator != null) ? creator.getId() : null;
    }
}
