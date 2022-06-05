# NewsApp
This app consumes the News API - https://newsapi.org/ . The user can fetch breaking news , search for news depending on a topic, view news and save/delete in the local storage.

Features -
  - Users can search for breaking news (it is not custom, it will always search for indian breaking news)
  - Users can click on articles, it will open the article on a webview . You can save the articles in room DB which can be accessed in the Saved News Fragment
  - Users can search for any news by topic , it will show a list of news related to the topic.
  - It uses pagination to optimize network calls
  - It also checks for no internet connectivity, will show a snackbar in that case
  - You can swipe to delete articles from saved article list(Local DB). You can also undo that action , it will restore the article to the DB.
  - It is using article Url to uniquely identify between different articles.

This repository demonstrates MVVM and consumes free news api .
 - Dependency Injection - Dagger 2
 - Networking - Retrofit & Coroutines
 - View - Navigation Graph with View Binding 
 - Local DB - Room


