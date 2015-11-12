package com.swanson.octodroid;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ListActivity implements OnClickListener
{

    private RepositoryListAdapter mAdapter;
    private EditText text;
    private Button button;
    private GitHub api;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_layout);

        button = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText);
        text.setOnClickListener(this);

        mAdapter = new RepositoryListAdapter(this);
        setListAdapter(mAdapter);

        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = text.getText().toString();
                getApi().repositories(name, new Callback<List<Repository>>()
                {

                    @Override
                    public void success(List<Repository> repositories, Response response)
                    {
                        if (repositories.isEmpty())
                        {
                            displaySadMessage();
                        }

                        mAdapter.setRepositories(repositories);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError)
                    {
                        displayErrorMessage();
                    }
                });
            }
        });
    }

    public GitHub getApi()
    {
        if (api == null)
        {
            api = GitHubService.getService();
        }
        return api;
    }

    public void setApi(GitHub githubApi)
    {
        api = githubApi;
    }

    private void displaySadMessage()
    {
        Toast.makeText(MainActivity.this, "No repos :(",
                Toast.LENGTH_LONG).show();
    }

    private void displayErrorMessage()
    {
        Toast.makeText(MainActivity.this, "Failed to retrieve the user's repos.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v)
    {
        text.setText("");
    }
}
