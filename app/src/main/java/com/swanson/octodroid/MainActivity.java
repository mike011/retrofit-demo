package com.swanson.octodroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements OnClickListener
{
    private RepositoryListAdapter mAdapter;
    private EditText text;
    private Button button;
    private GitHub api;
    private ProgressListener mListener;

    public interface ProgressListener
    {
        public void onProgressShown();

        public void onProgressDismissed();
    }

    public void setProgressListener(ProgressListener progressListener)
    {
        mListener = progressListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_layout);

        button = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText);
        text.setOnClickListener(this);

        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showProgress();
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
                        else
                        {
                            String out = "";
                            for (Repository r : repositories)
                            {
                                out += r.fullName() + "\n";
                            }
                            TextView tv = (TextView) findViewById(R.id.textView);
                            tv.setText(out);
                        }
                        dismissProgress();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError)
                    {
                        displayErrorMessage();
                        dismissProgress();
                    }
                });
            }
        });
    }

    boolean inProgress = false;

    private void showProgress()
    {
        // show the progress and notify the listener
        notifyListener(mListener);
        inProgress = true;
    }

    private void dismissProgress()
    {
        // hide the progress and notify the listener
        notifyListener(mListener);
        inProgress = false;
    }

    public boolean isInProgress()
    {
        // return true if progress is visible
        return inProgress;
    }

    private void notifyListener(ProgressListener listener)
    {
        if (listener == null)
        {
            return;
        }
        if (isInProgress())
        {
            listener.onProgressShown();
        }
        else
        {
            listener.onProgressDismissed();
        }
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
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("No repos :(");
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
