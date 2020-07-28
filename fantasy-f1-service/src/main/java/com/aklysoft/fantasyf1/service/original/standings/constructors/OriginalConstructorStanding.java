package com.aklysoft.fantasyf1.service.original.standings.constructors;

import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OriginalConstructorStandingPK.class)
public class OriginalConstructorStanding {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private String constructorId;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "constructorId", referencedColumnName = "id", insertable = false, updatable = false)
  })
  private OriginalConstructor constructor;

  private int position;
  private int points;
  private int wins;

}
