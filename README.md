# Next Bus Course Scheduling

# Members
Corentin (Corey) Rejaud
Patrick Bargoud
Kevin Horlavadi

# Description

A next bus app that is personalized for your course schedule.

After inputting your course schedule, you will get notifications 40 minutes before your courses
start.

When opening the app, your location will be recorded and it will figure out what campus
you're on at that instant.

Then it will find the campus of your next course and determine which bus you should take
to get there.

Once it has the bus you will be taking, it will call the Rutgers next bus API and get
the predictions, filtered by stops for the campus you're on.

# Libraries

Retrofit - Easy API Call

Serializable - Saving courses

Services (for alarm notifications)
