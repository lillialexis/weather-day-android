## Importing the Project

Clone the repo: `$ git clone https://github.com/lillialexis/weather-day-android.git`

Open Android Studio.

You will need to import the project into Android Studio
Go to "File->New->Import Project", and navigate your file system to where you download/cloned the project.

Once the project is imported it, you will need to sync the Gradle build files. Android Studio will do this automatically, but you can manually sync from the "Tools->Android->Sync Project with Gradle Files" menu option in Android Studio.

I wrote this project in Android Studio 2.0 Preview 7 version of Android Studio, using Android SDK 23. You may need to upgrade things to get everything running correctly.

Make sure you have all of the latest Android SDK Tools and other stuff installed, including the following found under the "Tools" heading or "Android 6.0 (API 23)" heading:

- Android SDK Build-Tools (build-tools;23.0.2)
- Android SDK Platform 6.0 Rev 2
- Google APIs, Android API 23, Rev 1
- Anything else you are prompted to install.

If you need to install them, you can do so through the Android SDK Manager:
"Tools->Android->SDK Manager" in Android Studio and then clicking the "Launch Standalone SDK Manager" link from the "Appearance & Behavior->System Settings->Android SDK" preference pane in the Android Studio preferences.

You can also launch the SDK Manager manually from your computer.

Once you've installed any required tools, you will need to re-sync your project. Sometimes, AS is funny about finding the newly installed things, so you may need to restart Android Studio. Upgrading Android SDK stuff can be a total bitch, so if it's still not working, please don't hesitate to email me and we can work out why.

Once the project has been successfully synced, you can build the project from the "Build->Make Project" menu option.

## Running the Project

Android Studio should have automatically created a Run Configuration for the "app" module. To build/run the app, select the "app" Run Configuration from the drop down menu in the tool bar and hit the green "play button" next to it.

When the Android Device Manager dialog pops up, chose your device from the options and click "OK". The app should install and launch on your device.

## Running the Tests

In the Android Studio Project browser pane, navigate to the folder containing the tests, which probably appears as "app/java/com.daoofdev.weatherday (androidTest)", right click the folder, and select the option "Run 'Tests in 'weatherday''". Or you can click each file individually and run the tests just in that file.

## A Few Things

While I certainly could have, I did not take the time to make my UI glamorous. Adding some animations would have been nice, and maybe making a more visually pleasing layout, but it is what it is.

I also could have added way more tests. I tried to, at least, stub out some/most of the things I knew needed to be tested. I just ran out of time.

If you're curious about seeing my methodology/thought process, reading my git history should probably be just as revealing as reading my comments and code.


### A note about libraries:
I used Gson for my json deserialization. I don't know if Gson counts as a "third-party library", as it is from Google; it's in the default Maven repos pointed to by Android Studio (so I didn't have to search the internet for it and include the jar manually); and regardless of whether I used Gson or Android's JSON library (from json.org), I wasn't going to do the json-deserialization manually. That is, the handling of my json strings doesn't really change; I can either deserialize my json string into an object by calling `CurrentWeatherData cwd = gson.fromJson(jsonString, CurrentWeatherData.class);` or by calling `JSONObject jObj = new JSONObject(data);`. Also, I did everything else manually, such as async http requests and parsing bytes into strings, which I hope demonstrates that I do have a deeper understanding of how the underlying bits and pieces work. Anyway, one of the biggest advantages that Gson has over JSONObject stuff is that it automatically deserializes the string into a POJO without any extra work, at all, whatsoever. I can use a Java object to represent my data, and I don't have to go through the intermediate step of taking keys/values out of a HashMap, etc. It gives me a solid interface that people can read and see exactly what's there; I can document all the fields; testing the object takes less time and is more streamlined; and it helps keep my code more loosely-coupled and encapsulated. In this case, my fields stay read-only, preventing accidental corruption of data, and I can do fancier stuff with validation in setters or custom deserializers if I wanted to. You can see just how much I use this to my advantage by writing object-specific string formatters right into my OpenWeatherMap data objects, such as "getPrettyTempMax(Constants.TemperatureUnits units)". There are no using 'magic strings' to pull fields out of a complex json object. All my other classes can explore the public methods of my data model, and see exactly what a parsed json object will yield, and will this method, I have all the formatting stuff encapsulated in one place. Anyway, if you want me to go back and deserialize with the org.json stuff, I will, but hopefully I've convinced you that my decision to use Gson was alright.


## Attribution of Creative Commons images used:
- https://flic.kr/p/bjY59d
- https://flic.kr/p/8KyfXA


