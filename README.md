# weather-forcast
Weather Forcast App using MVVM Architecture with openweathermap API

A. Application's functionalities and Architecture:

1. Retrieve the weather information from OpenWeatherMaps API
2. Allow user to input the searching term
3. Proceed searching with a condition of the search term length must be from 3 characters or above
4. Render the searched results as a list of weather items, app will show weather infomation in today and next 6 days.
5. Handle failures by: 
  - Input invalid search term length less than 3 characters
  - Input invalid search term (query)
  - Network issue
6. Give user an experiences that App run smoothly by displaying progress bar when fetching data.

Software development principle was applied in the app:
 - Keep It Simple Simon (KISS)
 - You Aren't Gonna Need It (YAGNI)
 - Single Responsibility Principle (SRP)
 - Code reusability       
 
As a user, I want an application which just has these functionalities and run smoothly. So the app should be as simple as possible but still has enough features.
This app is totally statisfy this condition because it was implemented MVVM architecture in the simplest way, the code is very simple and the app has light weight.
There are not any redundant features that user may not use (KISS & YAGNI).

With MVVM architure, Live Data, Retrofit & View binding, every class has a specific role for single responsibility (SRP).
"Model" class for handle data, "viewmodel" class observe data from "model" class and the "view" class reference to the "viewmodel" class. 
In MVVM, Viewmodel stands separately from views class so many activity & fragment can share the viewmodel instance without creating new one (Code reusability).

B. Code folder structure:

The application was written by Kotlin & designed MVVM architecture with some addtional libraries:
  - Scale size unit
  - Retrofit
  - OkHttp
  - buildFeature View binding

The main (weatherforcast) package has 5 subfolders
1. adapter: Include Adapter class which customs the way data displays as a list on UI. (WeatherAdapter)
2. service: Include necessary class & interface for handling data get by REST API from server. (interface WeatherApi & class WeatherApiService)
3. model: Include Kotlin data class which represent JSON data get from server (WeatherModel)
4. view: Include the Main activity represent for main screen of the app (MainActivity)
5. viewmodel: Include the mediate class which communicate between data & UI of the app (MainViewModel). 
The Resource folder contains layout of MainActivity (activity_main) & layout of item (weather_item_container) in the result list (Recyclerview)

C. Setup & Installation
After cloning this repository ( git@github.com:tuyen123khac/weather-forcast.git for SSH or https://github.com/tuyen123khac/weather-forcast.git for HTTPS) to
current folder
Open Android Studio, choosing File -> New -> Import project. Choose the weather-forcast folder which is the subfolder of current folder cloning repository, open and
waiting for Gradle building
Connect Real device or prepare virtual device then hit "Run" button to run the app.

D. Which item this app has done
1. Programming language Kotlin
2. Design app's architecture MVVM
3. Apply LiveData
4. UI as required in assigment file
5. Exception handling by displaying Error message and Log.
6. Readme file includes

What this app still not have:
1. Unit test
2. Secure from decomple APK (App get null exception while applying pro-guard by default minifyEnabled = true) 
3. Local data

