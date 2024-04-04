package com.hhn.kite2server.data;

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
public class DataObject {
    @SequenceGenerator(name = "data_object_sequence", sequenceName = "data_object_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_object_sequence")
    private Long id;

    @Lob
    private String prompt;

    @Lob
    private String completion;
}
