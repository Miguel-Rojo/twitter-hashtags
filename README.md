This is a web application that fetches tweets based on given keyword (eg: #Trump)

To run the app,

1) Update your twitter access token in the application.properties file

2) Then simply run :
-> gradle bootRun

3) Then query the /tweets endpoint
eg : curl 'localhost:8080/tweets?source=@obama&result_type=popular' | json_pp

You can also query based on the result_type:  mixed(default), popular, recent

IMPORTANT NOTE : This app uses twitter standard search api which retrieves tweets
from a sample set from past 30 days. Hence not all search terms may give back results.

PS : This code project shares a lot of code with the other project that could've been
abstracted in a library, but I could not find the time to refactor it.