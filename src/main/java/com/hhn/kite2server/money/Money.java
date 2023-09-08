package com.hhn.kite2server.money;

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
public class Money {
    @SequenceGenerator(name = "money_sequence", sequenceName = "money_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_sequence")
    private Long id;

    private Long value;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private AppUser user;
}
