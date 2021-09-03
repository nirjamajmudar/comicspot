The application is written in Kotlin, RxJava and follows MVVM architecture. It uses Marvel APIs to show the list of comics and the details.
The user, when launching the app after fresh install will be prompted for public and private key for authorization purpose. After successful authorization, the user will see the list of comics with a thumbnail and title. Currently the limit is set to 20. On clicking any of the thumbnails, the user will be able to see the cover image, title and description of the clicked comic.

Libraries used:

Retrofit for network communication

Dagger for dependency injection

Room for caching the credentials. It is used to store timestamp, public key and hash to the  local database. Once a user is authorized he/she doesn't have to enter the credentials in subsequent launches. 

Coroutines to trigger database operations

Glide for image caching

JUnit and Mockito for unit testing and UI testing


