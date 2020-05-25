package com.aklysoft.fantasyf1.service.players;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

  @Id
  private String userName;

  private String firstName;
  private String lastName;

  private Long points;

  private LocalDateTime created;
  private String createdBy;

  private LocalDateTime modified;
  private String modifiedBy;

  @Transient
  public String getFullName() {
    return firstName + (lastName != null ? ", " + lastName : "");
  }


}
