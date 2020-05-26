package com.aklysoft.fantasyf1.service.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EResponse<T> {

  @JsonProperty("MRData")
  private T data;
}
