# News corp readme

Dear reviewer, kindly go through this file before reviewing this project.

I had made an clarification request to the recruiter, however I did not receive it by EOD.Because I am shifting to Australia from India on 10th Jan itself(I am also on short vacation after 2 days), I have made certain assumptions and completed the project.I have also received and completed the assignment on the same day. These assumptions are
  -  Assuming that data in json is not null 
  -  I have one screen which shows questions, answer options, skip and read more link as required and another one as a pop up showing correct answer if required.
  -  focus is not on design
  -  user session is maintined on device rotation and user doesn't see same question twice
  -  user session can be maintained on app close and restart as well, I just have to save last question in shared preferences. however it is currently not done. I received the assignment today morning and submitting it by EOD.
  
### Things to note

  - Using mvvm pattern usign android jetpacks view model class
  - The app is backward compatible but tested on android 7.0.
  - familiar use RX Java as well
  - buttons are not very clickable, but that can be taken care of
  
### Libraries used
  - **android X libraries for backward compatibility** 
  - **android lifecycle libraries for live data and view model support** 
  - **gson for parsing data from server** version :2.4.0
  - **Picasso for downloading and using images** version:2.5.2
  

### Other

[My library](https://github.com/ikartiks/kartiksCustomViewsGradle/) - I would encourage you to go through this one as well, since it supports gifs and custom fonts by default (although custom font support has been added in support library for android, I had wirttten this code much prior to that)

### Installation

```sh
use the git clone https://github.com/ikartiks/newscorp.git in Android Studio
This should get it done
```

### Todos
 - Optimise if required

