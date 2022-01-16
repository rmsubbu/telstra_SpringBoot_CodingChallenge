package com.telstra.codechallenge.hottestRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.constants.CommonConstants;
import com.telstra.codechallenge.exceptions.SearchResultExceededException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitHubSearchService {

	@Value("${git.base.url}")
	private String gitBaseUrl;

	private RestTemplate restTemplate;

	private SimpleDateFormat searchDateFormat = new SimpleDateFormat("yyyy-MM-DD");

	public GitHubSearchService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * 
	 * @param offset
	 * @return
	 */
	public ArrayList<Repo> getWeeklyHottestRepo(Integer offset) {
		log.info(String.format(CommonConstants.METHOD_LOG, CommonConstants.ENTERED, "getWeeklyHottestRepo",
				"GitHubSearchService", CommonConstants.SERVICE, offset));
		if(offset>30) {
			throw new SearchResultExceededException();
		}
		String searchQuery = getLastWeekCreatedSearchQuery();
		RepoBase gitResponse = restTemplate.getForObject(
				gitBaseUrl + "/search/repositories?q=" + searchQuery + "&sort=watchers_count&order=desc",
				RepoBase.class);
		if (gitResponse.getItems().size() < offset) {
			log.info(String.format(CommonConstants.METHOD_LOG, CommonConstants.EXITING, "getWeeklyHottestRepo",
					"GitHubSearchService", CommonConstants.SERVICE, gitResponse.getItems()));
			return gitResponse.getItems();
		}
		ArrayList<Repo> hottestRepos = new ArrayList<Repo>(gitResponse.getItems().subList(0, offset));
		log.info(String.format(CommonConstants.METHOD_LOG, CommonConstants.EXITING, "getWeeklyHottestRepo",
				"GitHubSearchService", CommonConstants.SERVICE, hottestRepos));
		return hottestRepos;
	}

	/**
	 * 
	 * @return
	 */
	private String getLastWeekCreatedSearchQuery() {
		log.info(String.format(CommonConstants.METHOD_LOG, CommonConstants.ENTERED, "getWeeklyHottestRepo",
				"GitHubSearchService", CommonConstants.SERVICE, Calendar.getInstance().getTime()));
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DAY_OF_YEAR, -7);
		String format = searchDateFormat.format(currentDate.getTime());
		log.info(String.format(CommonConstants.METHOD_LOG, CommonConstants.EXITING, "getWeeklyHottestRepo",
				"GitHubSearchService", CommonConstants.SERVICE, format));
		return "created:>" + format;
	}
}
