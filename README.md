Clone the repo: $git clone https://github.com/lillialexis/weather-day-android.git

Open Android Studio

File->New->Import Project

Navigate to folder and select it.

Make sure you have all of the latest Android SDK Tools and other stuff installed, including the following found under
"Android 6.0 (API 23)":
SDK Platform
Google APIs
Maybe some others...

If you need to install them, you can do so through the Android SDK Manager:
Tools->Android->SDK Manager

To build the app, select the "app" run configuration and hit the green "play button".

There are also some tests under the tests directory.

A note about libraries:
I used Gson for my json deserialization. I don't know if Gson counts as a third-party library, as it is from Google; it's in default Maven repos pointed to by Android Studio, so I didn't have to search the internet for it and include the jar manually; and regardless of whether I used Gson or Android's JSON library (from json.org), I wasn't going to do the json-deserialization manually. That is, I can either deserialize my json string into an object by calling "" or by calling "JSONObject jObj = new JSONObject(data);". Also, I did everything else manually, such as async http requests and parsing bytes into strings, which I hope demonstrates that I do have a deeper understanding of how the underlying bits and pieces work. Anyway, one of the biggest advantages that Gson has over JSONObject stuff is that it automatically deserializes the string into a POJO without any extra work, at all, whatsoever. I can use a Java object to represent my data, and I don't have to go through the intermediate step of taking keys/values out of a HashMap, etc. It gives me a solid interface that people can read and see exactly what's there; I can document all the fields; testing the object takes less time and is more streamlined; and it helps keep my code more loosely-coupled and encapsulated. In this case, my fields stay read-only, preventing accidental corruption of data, and I can do fancier stuff with validation in setters or custom deserializers if I wanted to. Anyway, if you want me to go back and deserialize with the org.json stuff, I will, but hopefully I've convinced you that my decision to use Gson was alright.


Attribution of Creative Commons images used:
https://flic.kr/p/bjY59d
https://flic.kr/p/8KyfXA


