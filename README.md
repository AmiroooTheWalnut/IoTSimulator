This repository is an IoT simulator.
Dependancies are:
https://github.com/AmiroooTheWalnut/FastCSV
https://github.com/AmiroooTheWalnut/WekaForecasterAMES
https://github.com/AmiroooTheWalnut/WekaRevisedForIoTProject
And the testing environment is:
https://github.com/AmiroooTheWalnut/WekaForecastingTests


Limitations:
-Frequency difference between metrics should not exceed the size of "interpolationBuffer" variable. For instance, if 
we have metric one signaling every 1 second and metric two every hour, then if size of "interpolationBuffer" be 10,
we have 10 seconds signals for metric one and 10 hours for metric two. In this case the overlap between two metrics is
either zero (10 second signals are between two hourly signals) or one. Therefore, no correlation can be calculated.
One solution to this is to increase the size of "interpolationBuffer" to have at least 2 overlaps which is not enough
for calculating correlation but the minimum value to calculate the value.
-Although simulation time can go until "9,223,372,036,854,775,808" (long) but interpolation connection to Matlab uses double.
"Double" can go to 1.79769E+308 but it's not accurate. This may introduce minor error.
-The messages (signal floating number) are stored in string. On calculations it is converted to double in runtime.
This will degrade the performance.


**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).