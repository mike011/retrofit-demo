package com.swanson.octodroid;

import retrofit.RestAdapter;

public class GitHubService {

    public static GitHub getService() {
        return new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.API_URL)
                    .build()
                    .create(GitHub.class);
    }
}
