package com.swanson.octodroid.test;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.swanson.octodroid.MainActivity;
import com.swanson.octodroid.Owner;
import com.swanson.octodroid.R;
import com.swanson.octodroid.Repository;
import com.swanson.octodroid.RepositoryListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.ANDROID.assertThat;


@Config(emulateSdk = 18)
@RunWith(CustomTestRunner.class)
public class RepositoryListAdapterTest
{
    private RepositoryListAdapter adapter;

    @Before
    public void setUp() throws Exception
    {
        Activity context = Robolectric.buildActivity(MainActivity.class).create().get();
        adapter = new RepositoryListAdapter(context);

        List<Repository> repos = new ArrayList<Repository>();
        repos.add(new Repository("test-repo", "java", new Owner("user")));
        adapter.setRepositories(repos);
    }

    @Test
    public void testShouldDisplayRepositoryName()
    {
        View view = adapter.getView(0, null, null);

        TextView nameView = (TextView) view.findViewById(R.id.repo_name);
        assertThat(nameView).hasText("user/test-repo");
    }

    @Test
    public void testShouldDisplayRepositoryLanguage()
    {
        View view = adapter.getView(0, null, null);

        TextView languageView = (TextView) view.findViewById(R.id.repo_language);
        assertThat(languageView).hasText("java");
    }
}
