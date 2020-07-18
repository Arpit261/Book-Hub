package database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BookEntity::class] , version = 1)

 abstract class Bookdatabase :RoomDatabase(){

     abstract fun bookDao():BookDao

}