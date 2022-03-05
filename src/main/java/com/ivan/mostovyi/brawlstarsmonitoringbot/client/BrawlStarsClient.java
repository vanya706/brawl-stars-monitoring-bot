package com.ivan.mostovyi.brawlstarsmonitoringbot.client;

import brawljars.Api;
import brawljars.model.ClubMember;
import brawljars.request.GetClubMembersRequest;
import brawljars.response.GetClubMembersResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BrawlStarsClient {

  private final Api api;

  public BrawlStarsClient(Api api) {
    this.api = api;
  }


  public Collection<ClubMember> getClubMembers(String clubTag) {
    GetClubMembersRequest request = GetClubMembersRequest.builder(clubTag).build();
    GetClubMembersResponse response = api.getClubMembers(request);
    return response.getItems();
  }

}
