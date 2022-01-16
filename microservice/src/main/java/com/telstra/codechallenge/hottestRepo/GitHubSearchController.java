package com.telstra.codechallenge.hottestRepo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubSearchController {

  private GitHubSearchService gitHubSearchService;

  public GitHubSearchController(
		  GitHubSearchService gitHubSearchService) {
    this.gitHubSearchService = gitHubSearchService;
  }

  @GetMapping("/hottestRepo")
  public List<Repo> quotes(@RequestParam(required = true) Integer size) {
    return gitHubSearchService.getWeeklyHottestRepo(size);
  }
}
