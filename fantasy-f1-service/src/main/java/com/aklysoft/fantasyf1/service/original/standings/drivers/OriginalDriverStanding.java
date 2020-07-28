package com.aklysoft.fantasyf1.service.original.standings.drivers;

import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
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
@IdClass(OriginalDriverStandingPK.class)
public class OriginalDriverStanding {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private String driverId;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "driverId", referencedColumnName = "id", insertable = false, updatable = false)
  })
  private OriginalDriver driver;
  
  private int position;
  private int points;
  private int wins;

}
