package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.Professor
import kotlinx.coroutines.flow.Flow

/**
 * 教授信息数据访问对象
 */
@Dao
interface ProfessorDao {
    /**
     * 插入一条教授信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(professor: Professor)

    /**
     * 批量插入教授信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(professors: List<Professor>)

    /**
     * 删除一条教授信息
     */
    @Delete
    suspend fun delete(professor: Professor)

    /**
     * 获取所有教授信息
     */
    @Query("SELECT * FROM professor ORDER BY name")
    fun getAllProfessors(): Flow<List<Professor>>

    /**
     * 根据姓名搜索教授
     */
    @Query("SELECT * FROM professor WHERE name LIKE '%' || :name || '%'")
    fun searchProfessorsByName(name: String): Flow<List<Professor>>

    /**
     * 根据课程搜索教授
     */
    @Query("SELECT * FROM professor WHERE courses LIKE '%' || :course || '%'")
    fun searchProfessorsByCourse(course: String): Flow<List<Professor>>
}
