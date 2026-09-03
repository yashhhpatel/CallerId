package com.phonecalltrue.app.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.phonecalltrue.app.data.model.BlockedNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Entity(tableName = "blocked_numbers")
data class BlockedNumberEntity(
    @PrimaryKey val id: String,
    val phoneNumber: String,
    val name: String?,
    val reason: String
)

fun BlockedNumberEntity.toModel() = BlockedNumber(id, phoneNumber, name, reason)
fun BlockedNumber.toEntity() = BlockedNumberEntity(id, phoneNumber, name, reason)

@Dao
interface BlockedNumberDao {
    @Query("SELECT * FROM blocked_numbers ORDER BY phoneNumber")
    fun observeAll(): Flow<List<BlockedNumberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BlockedNumberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<BlockedNumberEntity>)

    @Query("DELETE FROM blocked_numbers WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM blocked_numbers")
    suspend fun count(): Int
}

@Database(entities = [BlockedNumberEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun blockedNumberDao(): BlockedNumberDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "phone_call_true.db"
                ).build().also { INSTANCE = it }
            }
    }
}

fun BlockedNumberDao.observeAllModels(): Flow<List<BlockedNumber>> =
    observeAll().map { list -> list.map { it.toModel() } }
