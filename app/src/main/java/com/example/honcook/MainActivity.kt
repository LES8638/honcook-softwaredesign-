package com.example.honcook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.honcook.databinding.ActivityMainBinding
import com.example.honcook.databinding.MainpageBinding
import com.example.honcook.databinding.RegisterpageBinding

@Entity(tableName = "table_user")
data class UserEntity(
    @PrimaryKey val pkId:Int?,
    @ColumnInfo val Email: String = "",
    @ColumnInfo val Password: String = "",
    @ColumnInfo val name: String = ""
)
@Entity(tableName="table_recipe")
data class RecipeEntity(
    @PrimaryKey val pkId:Int?,
    @ColumnInfo var Title:String?,
    @ColumnInfo var Content:String?,
    @ColumnInfo var WriterId:Int?
)
@Dao
interface UserDAO {
    @Query("SELECT * FROM table_user")
    fun getAll(): List<UserEntity>
    @Query("select Email from table_user")
    fun getEmail():List<String>
    @Query("select Password from table_user where Email=:email")
    fun getPwByEmail(email:String):String
    @Query("select name from table_user where Email=:email")
    fun getNameByEmail(email:String):String
    @Query("select pkId from table_user where Email=:email")
    fun getpkIdByEmail(email:String):Int
    @Insert
    fun insertUser(user_entity: UserEntity)
    @Delete
    fun deleteUser(user_entity: UserEntity)
}
@Dao
interface RecipeDao{
    @Query("Select * FROM table_recipe")
    fun getAll():List<RecipeEntity>
    @Query("Select * from table_recipe where WriterId=:id")
    fun getMyRecipes(id:Int):List<RecipeEntity>
    @Insert
    fun insertRecipe(recipe_entity:RecipeEntity)
    @Delete
    fun deleteRecipe(recipe_entity: RecipeEntity)
}
@Database(entities=[UserEntity::class],version=1)
abstract class UserDB:RoomDatabase(){
    abstract fun getDao():UserDAO

}
class MainActivity : AppCompatActivity() {
    private lateinit var loginbinding: ActivityMainBinding
    private lateinit var mainbinding: MainpageBinding
    private lateinit var registerbinding: RegisterpageBinding
    private lateinit var db: UserDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginbinding = ActivityMainBinding.inflate(layoutInflater)
        mainbinding = MainpageBinding.inflate(layoutInflater)
        registerbinding= RegisterpageBinding.inflate(layoutInflater)
        val loginview = loginbinding.root
        val mainview = mainbinding.root
        val registerview=registerbinding.root
        db = Room.databaseBuilder(
            applicationContext,
            UserDB::class.java, "UserDB"
        ).allowMainThreadQueries().build()
        var username=""
        var userpkid=0
        setContentView(loginview)
        loginbinding.loginbutton.setOnClickListener{
            if(loginbinding.etEmail.text.isNotEmpty()&&loginbinding.etPassword.text.isNotEmpty()){
                if(db.getDao().getEmail().isNotEmpty()){
                    if(db.getDao().getPwByEmail(loginbinding.etEmail.text.toString())==(loginbinding.etPassword.text.toString())){
                        username=db.getDao().getNameByEmail(loginbinding.etEmail.text.toString())
                        userpkid=db.getDao().getpkIdByEmail(loginbinding.etEmail.text.toString())
                        mainbinding.myprofile.text=username
                        setContentView(mainview)
                    }else{
                        Toast.makeText(this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "가입된 계정이 없습니다", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"모든 정보를 입력해 주십시오",Toast.LENGTH_SHORT).show()
            }
        }
        loginbinding.registerbutton.setOnClickListener {
            setContentView(registerview)
        }
        registerbinding.registerbutton.setOnClickListener {
            if(registerbinding.etEmail.text.isNotEmpty()&&registerbinding.etName.text.isNotEmpty()&&registerbinding.etPassword.text.isNotEmpty())
            {
                val pkId = db.getDao().getEmail().size+1
                db.getDao().insertUser(UserEntity(pkId,registerbinding.etEmail.text.toString(),registerbinding.etPassword.text.toString(),registerbinding.etName.text.toString()))
                setContentView(loginview)
            } else {
                Toast.makeText(this,"모든 정보를 입력해 주십시오",Toast.LENGTH_SHORT).show()
            }
        }
        mainbinding.logoutbutton.setOnClickListener{
            setContentView(loginview)
        }
    }
}