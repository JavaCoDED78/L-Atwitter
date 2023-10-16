# L-Atwitter

> L-Atwitter is a social media application clone that allows users to connect and share their thoughts and experiences with
> others. With a robust system design and a reliable technology stack, Twitter provides a seamless and secure user
> experience.</br>
> L-Atwitter developed with a microservice approach using the Spring Framework and React.js.</br>
> See more demo screenshots below.

![Home page](demo/images/griting.png)

# Technology stack
## Back-end:
>- Java 17
>- Spring Boot 2.7.15
>- Spring Web
>- Spring Security
>- Spring Data Jpa
>- Spring Cloud
>- PostgreSQL
>- Gradle
>- JUnit

## Front-end:
>- React.js
>- TypeScript
>- Redux-Saga
>- Material-UI
>- Jest
>- Enzyme

## Additional tools
>- Apache Kafka
>- AWS S3 bucket
>- OpenFeign
>- Discovery Server
>- Spring Api Gateway
>- Distributed tracing with Zipkin
> Spring cloud config server
>- Model mapper
>- Testcontainers
>- LiquiBase
>- Mockito
>- Docker

## Features
>* Authentication with JWT and Email validation. Password change.
>* Users can Add tweets, Like, Retweet, Reply, Quote tweets, Schedule tweets.
>* Users can Delete tweets, Send tweet via Direct Message, Add tweet to Bookmarks.
>* Users can Create Lists, Edit Lists, Add other users to Lists, Follow List, Pin Lists.
>* Users get notifications when someone subscribed, retweet or liked tweet.
>* Users can add Images to tweet, Create Poll and vote, Post tweets with link preview, Posts tweets with YouTube video link.
>* Websocket online chats.
>* Private user profile and lists.
>* Account Settings.
>* Users can subscribe to each other.
>* User can edit profile.
>* User can block and mute other users.
>* Users can customize site color scheme and color background.
>* Users can search tweets by hashtags and search other users and users tweets.
>* All images downloads on Amazon S3 bucket.

## Work in progress

>* Front-end refactoring
>* Back-end refactoring

## How to run application locally
>1. Install Java: [link](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html)
>2. Install Gradle: [link](https://docs.gradle.org/current/userguide/installation.html)
>3. Install Intellij IDEA Ultimate: [link](https://www.jetbrains.com/idea/)
>4. Install Docker and Docker Desktop [link](https://docs.docker.com/engine/install/)
>5. Add Lombok plugin to the Intellij IDEA: [link](https://i.ibb.co/Gtwcw0t/6-lombok.jpg)
>6. Make sure Java 17 is selected: [link](https://i.ibb.co/8PVGDdm/7-Java-17.png)
>7. In the docker-compose file [link](https://github.com/JavaCoDED78/L-Atwitter/blob/working-microservice/docker-compose.yml) run 3 services: `postgres`,`zipkin`, `rabbitmq`
>8. Create DBs: `user`, `tweet`, `chat`, `lists`, `notification`, `tag`, `topic`
>9. Sign up for a new AWS account: [link](https://portal.aws.amazon.com/billing/signup#/start) and create a new AWS S3 bucket: [link](https://docs.aws.amazon.com/AmazonS3/latest/userguide/create-bucket-overview.html)
>10. Change access from private to public in the AWS S3 bucket and add a public access policy to the AWS S3 bucket [doc](https://docs.aws.amazon.com/AmazonS3/latest/userguide/access-policy-language-overview.html),
>11. Get AWS keys [link](https://supsystic.com/documentation/id-secret-access-key-amazon-s3/) and add to the [image-service.yml config file](https://github.com/JavaCoDED78/L-Atwitter/blob/working-microservice/config-server/src/main/resources/config/image-service.yml) add bucket, access-key, secret-key properties
>12. Sign up for gmail and create google API keys: [link](https://developers.google.com/youtube/v3/getting-started#before-you-start)
>13. Add google API key to the [tweet-service.yml config file](https://github.com/JavaCoDED78/L-Atwitter/blob/working-microservice/config-server/src/main/resources/config/tweet-service.yml)
>14. Add gmail account and password to the [email-service.yml config file](https://github.com/JavaCoDED78/L-Atwitter/blob/working-microservice/config-server/src/main/resources/config/email-service.yml)
>15. Go to [link](https://myaccount.google.com/u/2/lesssecureapps) (important) and change to: “Allow less secure apps: ON”
>16. Install node.js and npm: [link](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
>17. Open terminal in frontend-client directory [link](https://github.com/JavaCoDED78/L-Atwitter-frontend) and type: `npm install` (or `yarn install`)
>18. Run services in this order:
>    - eureka-server
>    - config-server
>    - api-gateway
>    - user-service
>    - tweet-service
>    - user-service
>    - topic-service
>    - tag-service
>    - lists-service
>    - chat-service
>    - notification-service
>    - email-service
>    - image-service
>    - websocket-service
>19. Open terminal in frontend-client directory [link](https://github.com/JavaCoDED78/L-Atwitter-frontend) and type: `npm start` or run via [package.json](https://github.com/JavaCoDED78/L-Atwitter-frontend/blob/main/package.json)
>20. Navigate to http://localhost:3000/home
>21. To enter the application you can register or login:
>    - login: `androsor2026@gmail.com`
>    - password: `qwerty123`

## Screenshots
#### Sign In
![SignIn](demo/images/signin.png)
#### Sign Up
![SignUp](demo/images/signup.png)
#### Send registration code
![SendRegistrationCode](demo/images/sent_code.png)
#### Home
![Home](demo/images/home.png)
#### Home Tweet
![HomeTweet](demo/images/home_tweet.png)
#### Home Tweet Big Page
![HomeTweetBigPage](demo/images/home_tweet_big.png)
#### Add Tweet
![AddTweet](demo/images/add_tweet.png)
#### Explore Top
![ExploreTop](demo/images/explore_top.png)
#### Explore People
![ExplorePeople](demo/images/explore_people.png)
#### Bookmarks
![Bookmarks](demo/images/bookmarks.png)
#### Notification
![Notification](demo/images/notification_all.png)
#### Chat Messages
![ChatMessages](demo/images/messages_chat.png)
#### New Message
![NewMessage](demo/images/messages_new.png)
#### Lists
![Lists](demo/images/lists.png)
#### Current Lists
![CurrentLists](demo/images/lists_cur.png)
#### New Lists
![NewLists](demo/images/lists_new.png)
#### Profile
![Profile](demo/images/profile_edit.png)
#### Another Profile
![AnotherProfile](demo/images/profile_diff.png)
#### Profile Tweets
![ProfileTweets](demo/images/profile_tweets.png)
#### Profile Replies
![ProfileReplies](demo/images/profile_replies.png)
#### Profile Likes
![ProfileLikes](demo/images/profile_likes.png)
#### Profile Media
![ProfileMedia](demo/images/profile_media.png)
#### More
![More](demo/images/more.png)
#### More Setting
![MoreSetting](demo/images/more_setting.png)
#### More Setting Accessibility
![MoreSettingAccessibility](demo/images/more_setting_acces.png)
#### More Setting Account
![MoreSettingAccount](demo/images/more_setting_account.png)
#### More Setting Notification
![MoreSettingNotification](demo/images/more_setting_notification.png)
#### More Setting Privacy
![MoreSettingPrivacy](demo/images/more_setting_privacy.png)
#### More Setting Security
![MoreSettingSecurity](demo/images/more_setting_security.png)
#### More Setting Additional
![MoreSettingAdditional](demo/images/more_setting_additional.png)
#### More Custom
![MoreSettingCustom](demo/images/more_cust.png)
#### More Topic Followed
![MoreTopicFollowed](demo/images/topic_foll.png)
#### More Topic Suggested
![MoreTopicSuggested](demo/images/topic_sug.png)
#### Log Out
![LogOut](demo/images/logout.png)
