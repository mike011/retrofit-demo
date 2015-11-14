package com.swanson.octodroid.test;

import android.support.test.espresso.IdlingResource;

import com.swanson.octodroid.MainActivity;

/**
 * Sourced from: http://stackoverflow.com/questions/30733718/how-to-use-espresso-idling-resource-for-network-calls
 */
public class ProgressIdlingResource
        implements IdlingResource
{

    private IdlingResource.ResourceCallback resourceCallback;
    private MainActivity mainActivity;
    private MainActivity.ProgressListener progressListener;

    public ProgressIdlingResource(MainActivity activity)
    {
        mainActivity = activity;

        progressListener = new MainActivity.ProgressListener()
        {
            @Override
            public void onProgressShown()
            {
            }

            @Override
            public void onProgressDismissed()
            {
                if (resourceCallback == null)
                {
                    return;
                }
                //Called when the resource goes from busy to idle.
                resourceCallback.onTransitionToIdle();
            }
        };

        mainActivity.setProgressListener(progressListener);
    }

    @Override
    public String getName()
    {
        return "My idling resource";
    }

    @Override
    public boolean isIdleNow()
    {
        // the resource becomes idle when the progress has been dismissed
        boolean idle = mainActivity.isInProgress();
        if (!idle)
        {
            resourceCallback.onTransitionToIdle();
        }
        return !idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback)
    {
        this.resourceCallback = resourceCallback;
    }
}
