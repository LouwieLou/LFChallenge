# LFChallenge
This repo is for the LightFeather FullStack Assignment! It contains both the frontend (React) and the backend (Spring). The app validates and receives data from users which is then sent to the backend. Get started building the app by cloning this repo to your local machine.



# Instructions 
1. Clone the repo to your local repository.
### Backend
2. Using the terminal, cd into the springBackend directory and install Gradle.
3. Once finished, build and run the Docker image:
```
docker-compose build
docker-compose up
```
The server should be up and running now, time for the frontend!

### Frontend 
4. Using the terminal, cd into the reactFrontend and install all node modules:

```
npm install
```
5. Start the environment with: 
```
npm start
```
If a browser isn't automatically opened, open your browser of choice and enter the url:
```
http://localhost:3000
```

This form, when submitted, will "populate" the server and return the info back to the user in the backend terminal! It also validates the email and phone number fields and won't post to the server if either field is invalid.

The react frontend directory can be run as a container but, as of now, cannot connect to the proxy while containerized. It can, however, connect and run properly to the containerized backend when started locally with "npm start".

### User Input
5. Type in your info to the fields provided (NOTE: you MUST provide first and last names as well as a supervisor)
6. Phone and email are optional, but the form will show an error if either of these receive input that is considered incorrect (e.g. phone number: "qwerty").  

<br>

And now you're done! Thank you for the opportunity to interview with you, I really appreciate it.


