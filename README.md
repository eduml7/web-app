# web-app

Java Web Application. 

* I have used persistence in memory with a ConcurrentHashMap, applying singleton pattern.
* I have extracted the properties file from the jar file, because I think its neccesary not to compile again to change only a param.
* The app has a factory to handle the web server context creation.
* The app has four different layers:
** Persistence -> the model layer
** Service -> abstracts the controller layer to the persistence of the info
** Handler -> controller layer
** resources/html -> view layer
* Content-negotiation: web-app uses json media type
* The app increases the session cookie life reactively with an observer pattern. When the logged user interacts with the app, its life resets to 5 minutes.
* The app uses various HTTP status for the required situations.
* The user api is RestFul and versioned

## Steps

* The server starts in __http://localhost:9010__
* The available web pages are:
```
 __http://localhost:9010/login__
 __http://localhost:9010/page_1__
 __http://localhost:9010/page_2__
 __http://localhost:9010/page_3__
```
* The app pre-generates 4 users (username/pass/role):
``` 
 "admin", "admin", Role.ADMIN
 "user_page_1", "user_page_1", Role.PAGE_1 and Role.PAGE_2
 "user_page_2", "user_page_2", Role.PAGE_2
 "user_page_3", "user_page_3", Role.PAGE_3 and Role.PAGE_2
```
* You are allowed to create more users whith ADMIN
* If you access to a page path without session, it redirects to /login
* In /login if your credentials are ok, a page told you. If you have accessed from a private page, instead of the login successful page it redirects to the previous page.
* If you access is ok but you dont have the role neccesary to access, a forbidden page is shown
* If you access is ko an Unauthorized page is shown
* To navigate between pages, you must set the appropiate url in the navigator
* In the api, only admin users can execute post put or delete. All roles are allowed to execute get.
* The available user endpoints are:
```
 __http://localhost:9010/v1/api/users__ POST/PUT creates/updates the user
 __http://localhost:9010/v1/api/users/<username>__ GET/DELETE gets/deletes the user whith this username
```
* An example of a user POST body is: __{"username":"a","password\":"b","roles":["PAGE_1"]}__
## Run the application

(If you run it under windows, you must use gradlew.bat)

1. Download the source code
2. Go to the base path /web-app and execute __./gradlew runJar__ .This compiles the app, generates the distribution package (jar with properties file) and runs it.

You can run the app without the gradle task, creating the distribution package  __./gradlew dist__ under the /web-app folder and executing __java -jar web-app-0.0.1-SNAPSHOT.jar__

## Run the application test: jacoco report

1. You have to build the application: __./gradlew clean build__ 
2. There is a gradle task to generate the jacoco coverage report. __./gradlew jacocoTestReport__ The report is under __/web-app/build/reports/jacoco/test/html__ directory.
3. Double click on __index.html__ to see all the converage info
