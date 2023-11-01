package eu.ananaskirsche.pokerbackend.dto.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "player")
public class Player {
     @Id
     @Column(name = "id",nullable = false)
     UUID id;

     @Column(name = "name", nullable = false)
     String name;
}
