package com.telstra.codechallenge.hottestRepo;

import lombok.Data;

@Data
public class Repo {

  private String html_url;
  private Integer watchers_count;
  private String language;
  private String description;
  private String name;

}
