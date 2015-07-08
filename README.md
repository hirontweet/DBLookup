# DBLookup
Simple Android app that can retrieve data by typing the SQL directly.

## Prerequisites
* Android Studio 1.2 or above
* Gradle 1.2.2 or above
* You own SQLite file

## Setting your SQLite Database file first.
Current DBLookup project does not come with the SQLite file.
You have to include your SQLite file, in any format of the name an locate them in **assets** under **app/src/main/assets**

DBOpenHelper.java will copy the SQLite file in the assets to the built in app database. **Be careful** it will remove the built in Database if it exists.
