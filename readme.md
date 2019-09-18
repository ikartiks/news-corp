# News corp readme

Dear reviewer, kindly go through this file before reviewing this project.

I have made certain assumptions and completed the project. These assumptions are
  -  The app has been coded for a happy flow when it comes to network request, i.e assuming the server always responds with proper 200OK status and appropriate data. Other states need to be handled.
  -  I have one screen which shows questions, answer options, skip and read more link as required. It has a pop up showing points & correct answer if required.
  -  Focus is not on design, I would have ideally kept the read article section in a In App WebView but I am not available from tomorrow and needed to get this done today.
  -  User session is maintained on device rotation and user doesn't see same question twice. That is not the case on app kill though, the whole flow restarts.
  -  User session can be maintained on app close and restart as well, I just have to save last question in shared preferences. however it is currently not done.
  
### Things to note

  - Using MVVM pattern using android Jetpack's view model class
  - The app is backward compatible but tested on android 7.0.
  - Uses RX Java with retrofit.

### Other

[My library](https://github.com/ikartiks/kartiksCustomViewsGradle/) - I would encourage you to go through this one as well, since it supports gifs and custom fonts by default (although custom font support has been added in support library for android, I had written this code much prior to that)

### Installation

```sh
use the git clone hhttps://github.com/ikartiks/news-corp.git in Android Studio
This should get it done
```

### Todos
 - Optimise if required, as per comments above.
 - Modules can be further separated out
 - I generally use Dagger for injecting dependencies, but not in this case

